package br.ufal.ic.p2.bikeshare.exception.estacao;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class NomeEstacaoNuloException extends BikeshareException {

    public NomeEstacaoNuloException() {
        super("Nome nao pode ser nulo.");
    }
}
