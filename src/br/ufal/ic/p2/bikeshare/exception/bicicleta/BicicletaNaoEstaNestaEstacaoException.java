package br.ufal.ic.p2.bikeshare.exception.bicicleta;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class BicicletaNaoEstaNestaEstacaoException extends BikeshareException {

    public BicicletaNaoEstaNestaEstacaoException() {
        super("Bicicleta nao esta nesta estacao.");
    }
}
