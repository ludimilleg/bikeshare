package br.ufal.ic.p2.bikeshare.exception.usuario;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class RazaoSocialNulaException extends BikeshareException {

    public RazaoSocialNulaException() {
        super("Razao social nao pode ser nula.");
    }
}
