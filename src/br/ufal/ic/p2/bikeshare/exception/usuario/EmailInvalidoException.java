package br.ufal.ic.p2.bikeshare.exception.usuario;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class EmailInvalidoException extends BikeshareException {

    public EmailInvalidoException() {
        super("Email invalido.");
    }
}
