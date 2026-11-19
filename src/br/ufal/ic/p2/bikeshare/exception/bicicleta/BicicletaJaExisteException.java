package br.ufal.ic.p2.bikeshare.exception.bicicleta;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class BicicletaJaExisteException extends BikeshareException {

    public BicicletaJaExisteException() {
        super("Bicicleta ja existe.");
    }
}
