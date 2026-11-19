package br.ufal.ic.p2.bikeshare.exception.estacao;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class CapacidadeEstacaoNaoNumericaException extends BikeshareException {

    public CapacidadeEstacaoNaoNumericaException() {
        super("Capacidade deve ser numerica.");
    }
}
