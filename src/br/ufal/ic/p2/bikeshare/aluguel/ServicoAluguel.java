package br.ufal.ic.p2.bikeshare.aluguel;

import br.ufal.ic.p2.bikeshare.bicicleta.RepositorioBicicletas;
import br.ufal.ic.p2.bikeshare.exception.aluguel.AluguelNaoEstaEmAndamentoException;
import br.ufal.ic.p2.bikeshare.exception.aluguel.DataDevolucaoAnteriorException;
import br.ufal.ic.p2.bikeshare.exception.aluguel.UsuarioJaPossuiAluguelException;
import br.ufal.ic.p2.bikeshare.estacao.RepositorioEstacoes;
import br.ufal.ic.p2.bikeshare.usuario.RepositorioUsuarios;
import br.ufal.ic.p2.bikeshare.usuario.ServicoUsuarios;
import br.ufal.ic.p2.bikeshare.util.ConfiguracaoBikeshare;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class ServicoAluguel implements Serializable {

    private static final long serialVersionUID = 1L;

    private final RepositorioUsuarios usuarios;
    private final RepositorioEstacoes estacoes;
    private final RepositorioBicicletas bicicletas;
    private final RepositorioAlugueis alugueis;
    private ServicoUsuarios servicoUsuarios;

    public ServicoAluguel(RepositorioUsuarios usuarios,
                          RepositorioEstacoes estacoes,
                          RepositorioBicicletas bicicletas,
                          RepositorioAlugueis alugueis) {
        this.usuarios = usuarios;
        this.estacoes = estacoes;
        this.bicicletas = bicicletas;
        this.alugueis = alugueis;
    }

    private ServicoUsuarios getServicoUsuarios() {
        if (servicoUsuarios == null) {
            servicoUsuarios = new ServicoUsuarios(usuarios);
        }
        return servicoUsuarios;
    }

    public String retirar(String usuarioId, String estacaoId,
                          String bicicletaId, String data) {
        String aluguelAtivoId = getServicoUsuarios().getAluguelAtivoId(usuarioId);
        var estacao = estacoes.buscar(estacaoId);
        bicicletas.validarExiste(bicicletaId);
        Aluguel.lerData(data);
        if (aluguelAtivoId != null) {
            throw new UsuarioJaPossuiAluguelException();
        }
        bicicletas.validarNaEstacao(bicicletaId, estacao.getId());
        bicicletas.validarNivelParaRetirada(bicicletaId);
        getServicoUsuarios().validarMetodoPagamentoParaRetirada(usuarioId);

        Aluguel aluguel = alugueis.cadastrar(
                usuarioId, bicicletaId, estacaoId, data);
        bicicletas.retirar(estacaoId, bicicletaId);
        getServicoUsuarios().iniciarAluguel(usuarioId, aluguel.getId());
        return aluguel.getId();
    }

    public void devolver(String aluguelId, String estacaoId, String data) {
        Aluguel aluguel = alugueis.buscar(aluguelId);
        estacoes.buscar(estacaoId);
        LocalDateTime fim = Aluguel.lerData(data);

        if (!"emAndamento".equals(aluguel.getEstado())) {
            throw new AluguelNaoEstaEmAndamentoException();
        }
        if (fim.isBefore(aluguel.getInicio())) {
            throw new DataDevolucaoAnteriorException();
        }
        bicicletas.validarDevolucao(estacaoId);

        String usuarioId = aluguel.getUsuarioId();
        String bicicletaId = aluguel.getBicicletaId();
        getServicoUsuarios().getAluguelAtivoId(usuarioId);
        bicicletas.validarExiste(bicicletaId);
        long minutos = Duration.between(aluguel.getInicio(), fim).toMinutes();

        if (minutos > ConfiguracaoBikeshare.TEMPO_MAXIMO_ALUGUEL_MINUTOS) {
            aluguel.expirar(estacaoId, fim);
            bicicletas.enviarParaManutencao(bicicletaId);
            getServicoUsuarios().marcarInadimplente(usuarioId);
            return;
        }
        if (minutos <= ConfiguracaoBikeshare.PRAZO_CARENCIA_MINUTOS
                && estacaoId.equals(aluguel.getEstacaoOrigemId())) {
            aluguel.cancelar(estacaoId, fim);
            bicicletas.devolver(estacaoId, bicicletaId);
            getServicoUsuarios().finalizarAluguel(usuarioId);
            return;
        }

        BigDecimal valor = getServicoUsuarios()
                .calcularValorPlano(usuarioId, minutos)
                .add(bicicletas.getAdicionalPorMinuto(bicicletaId)
                        .multiply(BigDecimal.valueOf(minutos)));

        getServicoUsuarios().validarSaldo(usuarioId, valor);
        getServicoUsuarios().cobrar(usuarioId, valor);
        aluguel.finalizar(estacaoId, fim, valor);
        bicicletas.devolver(estacaoId, bicicletaId);
        getServicoUsuarios().finalizarAluguel(usuarioId);
    }

    public String getAtributoAluguel(String aluguelId, String atributo) {
        return alugueis.buscar(aluguelId).getAtributo(atributo);
    }
}
