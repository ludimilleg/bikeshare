package br.ufal.ic.p2.bikeshare.exception.pagamento;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class ValorNuloException extends BikeshareException {

    public ValorNuloException() {
        super("Valor nao pode ser nulo.");
    }
}
