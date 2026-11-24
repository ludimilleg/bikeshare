package br.ufal.ic.p2.bikeshare;

import br.ufal.ic.p2.bikeshare.aluguel.Aluguel;
import br.ufal.ic.p2.bikeshare.aluguel.RepositorioAlugueis;
import br.ufal.ic.p2.bikeshare.bicicleta.RepositorioBicicletas;
import br.ufal.ic.p2.bikeshare.exception.aluguel.UsuarioJaPossuiAluguelException;
import br.ufal.ic.p2.bikeshare.exception.validacao.AtributoNaoPodeSerAlteradoException;
import br.ufal.ic.p2.bikeshare.exception.pagamento.ValorMetodoPagamentoInvalidoException;
import br.ufal.ic.p2.bikeshare.estacao.RepositorioEstacoes;
import br.ufal.ic.p2.bikeshare.pagamento.FabricaMetodoPagamento;
import br.ufal.ic.p2.bikeshare.plano.FabricaPlano;
import br.ufal.ic.p2.bikeshare.usuario.RepositorioUsuarios;
import br.ufal.ic.p2.bikeshare.usuario.TipoUsuario;
import br.ufal.ic.p2.bikeshare.usuario.Usuario;
import br.ufal.ic.p2.bikeshare.usuario.UsuarioPessoaFisica;
import br.ufal.ic.p2.bikeshare.usuario.UsuarioPessoaJuridica;
import br.ufal.ic.p2.bikeshare.exception.aluguel.AluguelNaoEstaEmAndamentoException;
import br.ufal.ic.p2.bikeshare.exception.aluguel.DataDevolucaoAnteriorException;
import br.ufal.ic.p2.bikeshare.bicicleta.Bicicleta;
import br.ufal.ic.p2.bikeshare.exception.bicicleta.BicicletaNaoEstaNestaEstacaoException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class SistemaBikeshare implements Serializable {

    private static final long serialVersionUID = 2656971213220932395L;

    private final RepositorioUsuarios usuarios = new RepositorioUsuarios();
    private final RepositorioEstacoes estacoes = new RepositorioEstacoes();
    private final RepositorioBicicletas bicicletas = new RepositorioBicicletas(estacoes);
    private final RepositorioAlugueis alugueis = new RepositorioAlugueis();

    public String cadastrarPessoaFisica(String nome, String email, String senha,
                                        String tipo, String cpf) {
        return cadastrarPessoaFisica(nome, email, senha, tipo, cpf, FabricaPlano.PLANO_PADRAO, null);
    }

    public String cadastrarPessoaFisica(String nome, String email, String senha,
                                        String tipo, String cpf, String plano, String mensalidade) {
        TipoUsuario.PESSOA_FISICA.exigirIgual(tipo);
        return usuarios.cadastrar(id -> new UsuarioPessoaFisica(
                id, nome, email, senha, FabricaPlano.criar(plano, mensalidade), cpf)).getId();
    }

    public String cadastrarPessoaJuridica(String nome, String email, String senha,
                                          String tipo, String cnpj, String razaoSocial) {
        return cadastrarPessoaJuridica(nome, email, senha, tipo, cnpj, razaoSocial,
                FabricaPlano.PLANO_PADRAO, null);
    }

    public String cadastrarPessoaJuridica(String nome, String email, String senha, String tipo,
                                          String cnpj, String razaoSocial,
                                          String plano, String mensalidade) {
        TipoUsuario.PESSOA_JURIDICA.exigirIgual(tipo);
        return usuarios.cadastrar(id -> new UsuarioPessoaJuridica(
                id, nome, email, senha, FabricaPlano.criar(plano, mensalidade), cnpj, razaoSocial)).getId();
    }

    public String getAtributoUsuario(String usuario, String atributo) {
        return usuarios.buscar(usuario).getAtributo(atributo);
    }

    public String getUsuarioPorEmail(String email) {
        return usuarios.buscarPorEmail(email).getId();
    }

    public void removerUsuario(String id) {
        usuarios.remover(id);
    }

    public void alterarUsuario(String id, String atributo, String valor) {
        Usuario usuario = usuarios.buscar(id);
        if (atributo == null) {
            throw new AtributoNaoPodeSerAlteradoException();
        }
        switch (atributo) {
            case "nome":
                usuario.alterarNome(valor);
                break;
            case "email":
                usuarios.alterarEmail(usuario, valor);
                break;
            case "senha":
                usuario.alterarSenha(valor);
                break;
            case "plano":
                usuario.alterarPlano(valor);
                break;
            case "metodoPagamento":
                if (!"saldoPreCarregado".equals(valor)) {
                    throw new ValorMetodoPagamentoInvalidoException();
                }
                usuario.ativarSaldoPreCarregado();
                break;
            case "saldo":
                usuario.adicionarSaldo(valor);
                break;
            default:
                throw new AtributoNaoPodeSerAlteradoException();
        }
    }

    public void alterarUsuario(String id, String atributo, String metodo,
                               String numero, String validade) {
        Usuario usuario = usuarios.buscar(id);
        if (!"metodoPagamento".equals(atributo)) {
            throw new AtributoNaoPodeSerAlteradoException();
        }
        if (!"cartao".equals(metodo)) {
            throw new ValorMetodoPagamentoInvalidoException();
        }
        usuario.alterarMetodoPagamento(
                FabricaMetodoPagamento.criarCartao(numero, validade));
    }

    public void alterarUsuario(String id, String atributo, String metodo, String chave) {
        Usuario usuario = usuarios.buscar(id);
        if (!"metodoPagamento".equals(atributo)) {
            throw new AtributoNaoPodeSerAlteradoException();
        }
        if (!"pix".equals(metodo)) {
            throw new ValorMetodoPagamentoInvalidoException();
        }
        usuario.alterarMetodoPagamento(
                FabricaMetodoPagamento.criarPix(chave));
    }

    public String cadastrarEstacao(String nome, String endereco, String capacidade) {
        return estacoes.cadastrar(nome, endereco, capacidade).getId();
    }

    public int getVagasDisponiveis(String estacao) {
        return bicicletas.getVagasDisponiveis(estacao);
    }

    public int getBicicletasDisponiveis(String estacao) {
        return bicicletas.getBicicletasDisponiveis(estacao);
    }

    public void adicionarBicicleta(String estacao, String bicicleta, String tipo) {
        bicicletas.cadastrar(estacao, bicicleta, tipo);
    }

    public void adicionarBicicleta(String estacao, String bicicleta, String tipo, String nivelBateria) {
        bicicletas.cadastrar(estacao, bicicleta, tipo, nivelBateria);
    }

    public void removerBicicleta(String estacao, String bicicleta) {
        bicicletas.remover(estacao, bicicleta);
    }

    public String getAtributoBicicleta(String bicicleta, String atributo) {
        return bicicletas.buscar(bicicleta).getAtributo(atributo);
    }

    public String getEstadoBicicleta(String bicicleta) {
        return bicicletas.buscar(bicicleta).getEstado();
    }

    public String retirarBicicleta(String usuarioId, String estacaoId,
                                   String bicicletaId, String data) {
        Usuario usuario = usuarios.buscar(usuarioId);
        var estacao = estacoes.buscar(estacaoId);
        var bicicleta = bicicletas.buscar(bicicletaId);
        Aluguel.exigirDataValida(data);
        if (usuario.getAluguelAtivoId() != null) {
            throw new UsuarioJaPossuiAluguelException();
        }

        if (bicicleta.getEstacaoId() == null
                || !bicicleta.getEstacaoId().equals(estacao.getId())) {
            throw new BicicletaNaoEstaNestaEstacaoException();
        }
        bicicleta.exigirNivelParaRetirada();
        usuario.validarMetodoPagamentoParaRetirada();
        bicicletas.retirar(estacaoId, bicicletaId);

        Aluguel aluguel = alugueis.cadastrar(
                usuarioId, bicicletaId, estacaoId, data);
        usuario.iniciarAluguel(aluguel.getId());
        return aluguel.getId();
    }

    public String getAtributoAluguel(String aluguelId, String atributo) {
        return alugueis.buscar(aluguelId).getAtributo(atributo);
    }

    public void devolverBicicleta(String aluguelId, String estacaoId, String data) {
        Aluguel aluguel = alugueis.buscar(aluguelId);
        estacoes.buscar(estacaoId);
        Aluguel.exigirDataValida(data);

        if (!"emAndamento".equals(aluguel.getEstado())) {
            throw new AluguelNaoEstaEmAndamentoException();
        }

        LocalDateTime fim = Aluguel.lerData(data);
        if (fim.isBefore(aluguel.getInicio())) {
            throw new DataDevolucaoAnteriorException();
        }

        bicicletas.validarDevolucao(estacaoId);

        Usuario usuario = usuarios.buscar(aluguel.getUsuarioId());
        Bicicleta bicicleta = bicicletas.buscar(aluguel.getBicicletaId());
        long minutos = Duration.between(aluguel.getInicio(), fim).toMinutes();

        if (minutos > 1440) {
            aluguel.expirar(estacaoId, fim);
            bicicletas.enviarParaManutencao(bicicleta.getId());
            usuario.marcarInadimplente();
            return;
        }

        if (minutos <= 2 && estacaoId.equals(aluguel.getEstacaoOrigemId())) {
            aluguel.cancelar(estacaoId, fim);
            bicicletas.devolver(estacaoId, bicicleta.getId());
            usuario.finalizarAluguel();
            return;
        }

        BigDecimal valor = usuario.getPlano().calcularValor(minutos)
                .add(bicicleta.getAdicionalPorMinuto().multiply(BigDecimal.valueOf(minutos)));

        usuario.validarSaldo(valor);
        usuario.cobrar(valor);
        aluguel.finalizar(estacaoId, fim, valor);
        bicicletas.devolver(estacaoId, bicicleta.getId());
        usuario.finalizarAluguel();
    }
}