package br.ufal.ic.p2.bikeshare.exception.bicicleta;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class BicicletaNaoExisteException extends BikeshareException {

    public BicicletaNaoExisteException() {
        super("Bicicleta nao existe.");
    }
}
