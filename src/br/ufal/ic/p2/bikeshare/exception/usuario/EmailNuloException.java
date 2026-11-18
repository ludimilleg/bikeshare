package br.ufal.ic.p2.bikeshare.exception.usuario;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class EmailNuloException extends BikeshareException {

    public EmailNuloException() {
        super("Email nao pode ser nulo.");
    }
}
