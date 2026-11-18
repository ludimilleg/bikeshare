package br.ufal.ic.p2.bikeshare.exception.pagamento;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class ValidadeCartaoNulaException extends BikeshareException {

    public ValidadeCartaoNulaException() {
        super("Validade do cartao nao pode ser nula.");
    }
}
