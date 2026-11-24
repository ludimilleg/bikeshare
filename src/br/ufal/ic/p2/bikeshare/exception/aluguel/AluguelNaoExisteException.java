package br.ufal.ic.p2.bikeshare.exception.aluguel;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class AluguelNaoExisteException extends BikeshareException {

    public AluguelNaoExisteException() {
        super("Aluguel nao existe.");
    }
}
