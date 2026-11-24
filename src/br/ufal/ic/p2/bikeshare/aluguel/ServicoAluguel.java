package br.ufal.ic.p2.bikeshare.aluguel;

import br.ufal.ic.p2.bikeshare.bicicleta.Bicicleta;
import br.ufal.ic.p2.bikeshare.bicicleta.RepositorioBicicletas;
import br.ufal.ic.p2.bikeshare.exception.aluguel.AluguelNaoEstaEmAndamentoException;
import br.ufal.ic.p2.bikeshare.exception.aluguel.DataDevolucaoAnteriorException;
import br.ufal.ic.p2.bikeshare.exception.aluguel.UsuarioJaPossuiAluguelException;
import br.ufal.ic.p2.bikeshare.exception.bicicleta.BicicletaNaoEstaNestaEstacaoException;
import br.ufal.ic.p2.bikeshare.estacao.RepositorioEstacoes;
import br.ufal.ic.p2.bikeshare.usuario.RepositorioUsuarios;
import br.ufal.ic.p2.bikeshare.usuario.Usuario;
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

    public ServicoAluguel(RepositorioUsuarios usuarios,
                          RepositorioEstacoes estacoes,
                          RepositorioBicicletas bicicletas,
                          RepositorioAlugueis alugueis) {
        this.usuarios = usuarios;
        this.estacoes = estacoes;
        this.bicicletas = bicicletas;
        this.alugueis = alugueis;
    }

    public String retirar(String usuarioId, String estacaoId,
                          String bicicletaId, String data) {
        Usuario usuario = usuarios.buscar(usuarioId);
        var estacao = estacoes.buscar(estacaoId);
        var bicicleta = bicicletas.buscar(bicicletaId);
        Aluguel.lerData(data);
        if (usuario.getAluguelAtivoId() != null) {
            throw new UsuarioJaPossuiAluguelException();
        }
        if (bicicleta.getEstacaoId() == null
                || !bicicleta.getEstacaoId().equals(estacao.getId())) {
            throw new BicicletaNaoEstaNestaEstacaoException();
        }
        bicicleta.exigirNivelParaRetirada();
        usuario.validarMetodoPagamentoParaRetirada();

        Aluguel aluguel = alugueis.cadastrar(
                usuarioId, bicicletaId, estacaoId, data);
        bicicletas.retirar(estacaoId, bicicletaId);
        usuario.iniciarAluguel(aluguel.getId());
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

        Usuario usuario = usuarios.buscar(aluguel.getUsuarioId());
        Bicicleta bicicleta = bicicletas.buscar(aluguel.getBicicletaId());
        long minutos = Duration.between(aluguel.getInicio(), fim).toMinutes();

        if (minutos > ConfiguracaoBikeshare.TEMPO_MAXIMO_ALUGUEL_MINUTOS) {
            aluguel.expirar(estacaoId, fim);
            bicicletas.enviarParaManutencao(bicicleta.getId());
            usuario.marcarInadimplente();
            return;
        }
        if (minutos <= ConfiguracaoBikeshare.PRAZO_CARENCIA_MINUTOS
                && estacaoId.equals(aluguel.getEstacaoOrigemId())) {
            aluguel.cancelar(estacaoId, fim);
            bicicletas.devolver(estacaoId, bicicleta.getId());
            usuario.finalizarAluguel();
            return;
        }

        BigDecimal valor = usuario.getPlano().calcularValor(minutos)
                .add(bicicleta.getAdicionalPorMinuto()
                        .multiply(BigDecimal.valueOf(minutos)));

        usuario.validarSaldo(valor);
        usuario.cobrar(valor);
        aluguel.finalizar(estacaoId, fim, valor);
        bicicletas.devolver(estacaoId, bicicleta.getId());
        usuario.finalizarAluguel();
    }

    public String getAtributoAluguel(String aluguelId, String atributo) {
        return alugueis.buscar(aluguelId).getAtributo(atributo);
    }
}
