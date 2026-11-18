package br.ufal.ic.p2.bikeshare.exception.usuario;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class UsuarioNaoEhPessoaFisicaException extends BikeshareException {

    public UsuarioNaoEhPessoaFisicaException() {
        super("Usuario nao eh pessoa fisica.");
    }
}
