package br.ufal.ic.p2.bikeshare.exception.usuario;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class SenhaNulaException extends BikeshareException {

    public SenhaNulaException() {
        super("Senha nao pode ser nula.");
    }
}
