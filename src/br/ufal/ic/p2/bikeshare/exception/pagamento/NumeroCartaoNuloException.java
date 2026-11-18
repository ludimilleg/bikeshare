package br.ufal.ic.p2.bikeshare.exception.pagamento;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class NumeroCartaoNuloException extends BikeshareException {

    public NumeroCartaoNuloException() {
        super("Numero do cartao nao pode ser nulo.");
    }
}
