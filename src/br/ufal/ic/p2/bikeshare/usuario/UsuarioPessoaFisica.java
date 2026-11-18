package br.ufal.ic.p2.bikeshare.usuario;

import br.ufal.ic.p2.bikeshare.exception.usuario.CpfNuloException;
import br.ufal.ic.p2.bikeshare.plano.Plano;
import br.ufal.ic.p2.bikeshare.util.Validador;

public class UsuarioPessoaFisica extends Usuario {

    private final String cpf;

    public UsuarioPessoaFisica(String id, String nome, String email, String senha,
                               Plano plano, String cpf) {
        super(id, nome, email, senha, plano);
        this.cpf = Validador.exigirTexto(cpf, CpfNuloException::new);
    }

    @Override
    public TipoUsuario getTipo() {
        return TipoUsuario.PESSOA_FISICA;
    }

    @Override
    public String getCpf() {
        return cpf;
    }
}