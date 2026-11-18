package br.ufal.ic.p2.bikeshare.exception.pagamento;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class ValidadeCartaoInvalidaException extends BikeshareException {

    public ValidadeCartaoInvalidaException() {
        super("Validade do cartao invalida.");
    }
}
