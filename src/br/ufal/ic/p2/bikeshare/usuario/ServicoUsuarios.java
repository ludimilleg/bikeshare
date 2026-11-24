package br.ufal.ic.p2.bikeshare.usuario;

import br.ufal.ic.p2.bikeshare.exception.pagamento.ValorMetodoPagamentoInvalidoException;
import br.ufal.ic.p2.bikeshare.exception.validacao.AtributoNaoPodeSerAlteradoException;
import br.ufal.ic.p2.bikeshare.pagamento.FabricaMetodoPagamento;
import br.ufal.ic.p2.bikeshare.plano.FabricaPlano;

import java.io.Serializable;
import java.math.BigDecimal;

public class ServicoUsuarios implements Serializable {

    private static final long serialVersionUID = 1L;

    private final RepositorioUsuarios usuarios;

    public ServicoUsuarios(RepositorioUsuarios usuarios) {
        this.usuarios = usuarios;
    }

    public String cadastrarPessoaFisica(String nome, String email, String senha,
                                        String tipo, String cpf) {
        return cadastrarPessoaFisica(nome, email, senha, tipo, cpf,
                FabricaPlano.PLANO_PADRAO, null);
    }

    public String cadastrarPessoaFisica(String nome, String email, String senha,
                                        String tipo, String cpf, String plano, String mensalidade) {
        TipoUsuario.PESSOA_FISICA.exigirIgual(tipo);
        return usuarios.cadastrar(id -> new UsuarioPessoaFisica(
                id, nome, email, senha, FabricaPlano.criar(plano, mensalidade), cpf));
    }

    public String cadastrarPessoaJuridica(String nome, String email, String senha,
                                          String tipo, String cnpj, String razaoSocial) {
        return cadastrarPessoaJuridica(nome, email, senha, tipo, cnpj, razaoSocial,
                FabricaPlano.PLANO_PADRAO, null);
    }

    public String cadastrarPessoaJuridica(String nome, String email, String senha,
                                          String tipo, String cnpj, String razaoSocial,
                                          String plano, String mensalidade) {
        TipoUsuario.PESSOA_JURIDICA.exigirIgual(tipo);
        return usuarios.cadastrar(id -> new UsuarioPessoaJuridica(
                id, nome, email, senha, FabricaPlano.criar(plano, mensalidade), cnpj, razaoSocial));
    }

    public String getAtributo(String usuarioId, String atributo) {
        return usuarios.buscar(usuarioId).getAtributo(atributo);
    }

    public String getIdPorEmail(String email) {
        return usuarios.buscarIdPorEmail(email);
    }

    public void remover(String usuarioId) {
        usuarios.remover(usuarioId);
    }

    public void alterar(String id, String atributo, String valor) {
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

    public void alterarMetodoPagamento(String id, String atributo, String metodo,
                                       String chave) {
        Usuario usuario = usuarios.buscar(id);
        if (!"metodoPagamento".equals(atributo)) {
            throw new AtributoNaoPodeSerAlteradoException();
        }
        if (!"pix".equals(metodo)) {
            throw new ValorMetodoPagamentoInvalidoException();
        }
        usuario.alterarMetodoPagamento(FabricaMetodoPagamento.criarPix(chave));
    }

    public void alterarMetodoPagamento(String id, String atributo, String metodo,
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

    public String getAluguelAtivoId(String usuarioId) {
        return usuarios.buscar(usuarioId).getAluguelAtivoId();
    }

    public void validarMetodoPagamentoParaRetirada(String usuarioId) {
        usuarios.buscar(usuarioId).validarMetodoPagamentoParaRetirada();
    }

    public void iniciarAluguel(String usuarioId, String aluguelId) {
        usuarios.buscar(usuarioId).iniciarAluguel(aluguelId);
    }

    public void finalizarAluguel(String usuarioId) {
        usuarios.buscar(usuarioId).finalizarAluguel();
    }

    public void marcarInadimplente(String usuarioId) {
        usuarios.buscar(usuarioId).marcarInadimplente();
    }

    public BigDecimal calcularValorPlano(String usuarioId, long minutos) {
        return usuarios.buscar(usuarioId).getPlano().calcularValor(minutos);
    }

    public void validarSaldo(String usuarioId, BigDecimal valor) {
        usuarios.buscar(usuarioId).validarSaldo(valor);
    }

    public void cobrar(String usuarioId, BigDecimal valor) {
        usuarios.buscar(usuarioId).cobrar(valor);
    }
}
