package br.ufal.ic.p2.bikeshare.usuario;

import br.ufal.ic.p2.bikeshare.exception.usuario.EmailInvalidoException;
import br.ufal.ic.p2.bikeshare.exception.usuario.EmailNuloException;
import br.ufal.ic.p2.bikeshare.exception.usuario.NomeNuloException;
import br.ufal.ic.p2.bikeshare.exception.usuario.SenhaNulaException;
import br.ufal.ic.p2.bikeshare.exception.usuario.UsuarioNaoEhPessoaFisicaException;
import br.ufal.ic.p2.bikeshare.exception.usuario.UsuarioNaoEhPessoaJuridicaException;
import br.ufal.ic.p2.bikeshare.exception.validacao.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.pagamento.MetodoPagamento;
import br.ufal.ic.p2.bikeshare.pagamento.SemMetodoPagamento;
import br.ufal.ic.p2.bikeshare.plano.Plano;
import br.ufal.ic.p2.bikeshare.util.Dinheiro;
import br.ufal.ic.p2.bikeshare.util.Validador;

import java.io.Serializable;
import java.util.regex.Pattern;

public abstract class Usuario implements Serializable {

    private static final Pattern PADRAO_EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+$");

    private final String id;
    private final String nome;
    private final String email;
    private final String senha;
    private final Plano plano;
    private final MetodoPagamento metodoPagamento;
    private final EstadoUsuario estado;

    protected Usuario(String id, String nome, String email, String senha, Plano plano) {
        this.id = id;
        this.nome = Validador.exigirTexto(nome, NomeNuloException::new);
        this.email = validarEmail(email);
        this.senha = Validador.exigirTexto(senha, SenhaNulaException::new);
        this.plano = plano;
        this.metodoPagamento = new SemMetodoPagamento();
        this.estado = EstadoUsuario.ATIVO;
    }

    private static String validarEmail(String email) {
        Validador.exigirTexto(email, EmailNuloException::new);
        return Validador.exigirFormato(email, PADRAO_EMAIL, EmailInvalidoException::new);
    }

    public abstract TipoUsuario getTipo();

    public String getCpf() {
        throw new UsuarioNaoEhPessoaFisicaException();
    }

    public String getCnpj() {
        throw new UsuarioNaoEhPessoaJuridicaException();
    }

    public String getRazaoSocial() {
        throw new UsuarioNaoEhPessoaJuridicaException();
    }

    public String getAtributo(String atributo) {
        if (atributo == null) {
            throw new AtributoNaoExisteException();
        }
        switch (atributo) {
            case "nome":
                return nome;
            case "email":
                return email;
            case "senha":
                return senha;
            case "tipo":
                return getTipo().getNome();
            case "estado":
                return estado.getNome();
            case "plano":
                return plano.getNome();
            case "mensalidade":
                return Dinheiro.formatar(plano.getMensalidade());
            case "metodoPagamento":
                return metodoPagamento.getNome();
            case "cpf":
                return getCpf();
            case "cnpj":
                return getCnpj();
            case "razaoSocial":
                return getRazaoSocial();
            default:
                throw new AtributoNaoExisteException();
        }
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}