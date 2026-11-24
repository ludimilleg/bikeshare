package br.ufal.ic.p2.bikeshare.exception.aluguel;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class DataNulaException extends BikeshareException {

    public DataNulaException() {
        super("Data nao pode ser nula.");
    }
}
