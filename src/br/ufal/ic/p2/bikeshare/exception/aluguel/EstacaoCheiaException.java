package br.ufal.ic.p2.bikeshare.exception.aluguel;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class EstacaoCheiaException extends BikeshareException {

    public EstacaoCheiaException() {
        super("Estacao cheia.");
    }
}
