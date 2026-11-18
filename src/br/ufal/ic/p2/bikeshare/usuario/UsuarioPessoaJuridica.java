package br.ufal.ic.p2.bikeshare.usuario;

import br.ufal.ic.p2.bikeshare.exception.usuario.CnpjNuloException;
import br.ufal.ic.p2.bikeshare.exception.usuario.RazaoSocialNulaException;
import br.ufal.ic.p2.bikeshare.plano.Plano;
import br.ufal.ic.p2.bikeshare.util.Validador;

public class UsuarioPessoaJuridica extends Usuario {

    private final String cnpj;
    private final String razaoSocial;

    public UsuarioPessoaJuridica(String id, String nome, String email, String senha,
                                 Plano plano, String cnpj, String razaoSocial) {
        super(id, nome, email, senha, plano);
        this.cnpj = Validador.exigirTexto(cnpj, CnpjNuloException::new);
        this.razaoSocial = Validador.exigirTexto(razaoSocial, RazaoSocialNulaException::new);
    }

    @Override
    public TipoUsuario getTipo() {
        return TipoUsuario.PESSOA_JURIDICA;
    }

    @Override
    public String getCnpj() {
        return cnpj;
    }

    @Override
    public String getRazaoSocial() {
        return razaoSocial;
    }
}