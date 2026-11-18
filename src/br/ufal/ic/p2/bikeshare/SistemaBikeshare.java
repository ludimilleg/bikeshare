package br.ufal.ic.p2.bikeshare;

import br.ufal.ic.p2.bikeshare.exception.validacao.AtributoNaoPodeSerAlteradoException;
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
            default:
                throw new AtributoNaoPodeSerAlteradoException();
        }
    }
}