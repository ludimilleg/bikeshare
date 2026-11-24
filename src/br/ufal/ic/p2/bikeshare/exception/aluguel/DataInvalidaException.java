package br.ufal.ic.p2.bikeshare.exception.aluguel;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class DataInvalidaException extends BikeshareException {

    public DataInvalidaException() {
        super("Data invalida.");
    }
}
