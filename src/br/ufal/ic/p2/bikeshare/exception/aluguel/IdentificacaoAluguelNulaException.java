package br.ufal.ic.p2.bikeshare.exception.aluguel;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class IdentificacaoAluguelNulaException extends BikeshareException {

    public IdentificacaoAluguelNulaException() {
        super("Identificacao do aluguel nao pode ser nula.");
    }
}
