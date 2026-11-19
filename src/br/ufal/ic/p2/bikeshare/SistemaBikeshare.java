package br.ufal.ic.p2.bikeshare;

import br.ufal.ic.p2.bikeshare.exception.validacao.AtributoNaoPodeSerAlteradoException;
import br.ufal.ic.p2.bikeshare.exception.pagamento.ValorMetodoPagamentoInvalidoException;
import br.ufal.ic.p2.bikeshare.estacao.RepositorioEstacoes;
import br.ufal.ic.p2.bikeshare.pagamento.PagamentoCartao;
import br.ufal.ic.p2.bikeshare.pagamento.PagamentoPix;
import br.ufal.ic.p2.bikeshare.plano.FabricaPlano;
import br.ufal.ic.p2.bikeshare.usuario.RepositorioUsuarios;
import br.ufal.ic.p2.bikeshare.usuario.TipoUsuario;
import br.ufal.ic.p2.bikeshare.usuario.Usuario;
import br.ufal.ic.p2.bikeshare.usuario.UsuarioPessoaFisica;
import br.ufal.ic.p2.bikeshare.usuario.UsuarioPessoaJuridica;

import java.io.Serializable;

public class SistemaBikeshare implements Serializable {

    private static final long serialVersionUID = 2656971213220932395L;

    private final RepositorioUsuarios usuarios = new RepositorioUsuarios();
    private RepositorioEstacoes estacoes;

    private RepositorioEstacoes getEstacoes() {
        if (estacoes == null) {
            estacoes = new RepositorioEstacoes();
        }
        return estacoes;
    }

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
        usuario.alterarMetodoPagamento(new PagamentoCartao(numero, validade));
    }

    public void alterarUsuario(String id, String atributo, String metodo, String chave) {
        Usuario usuario = usuarios.buscar(id);
        if (!"metodoPagamento".equals(atributo)) {
            throw new AtributoNaoPodeSerAlteradoException();
        }
        if (!"pix".equals(metodo)) {
            throw new ValorMetodoPagamentoInvalidoException();
        }
        usuario.alterarMetodoPagamento(new PagamentoPix(chave));
    }

    public String cadastrarEstacao(String nome, String endereco, String capacidade) {
        return getEstacoes().cadastrar(nome, endereco, capacidade).getId();
    }

    public int getVagasDisponiveis(String estacao) {
        return getEstacoes().buscar(estacao).getVagasDisponiveis();
    }

    public int getBicicletasDisponiveis(String estacao) {
        return getEstacoes().buscar(estacao).getBicicletasDisponiveis();
    }
}