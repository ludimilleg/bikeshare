package br.ufal.ic.p2.bikeshare.exception.plano;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class PlanoInvalidoException extends BikeshareException {

    public PlanoInvalidoException() {
        super("Plano invalido.");
    }
}
