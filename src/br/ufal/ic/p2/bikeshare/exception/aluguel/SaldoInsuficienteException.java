package br.ufal.ic.p2.bikeshare.exception.aluguel;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class SaldoInsuficienteException extends BikeshareException {

    public SaldoInsuficienteException() {
        super("Saldo insuficiente.");
    }
}
