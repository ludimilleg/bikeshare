package br.ufal.ic.p2.bikeshare.exception.validacao;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class NomeNuloException extends BikeshareException {

    public NomeNuloException() {
        super("Nome nao pode ser nulo.");
    }
}
