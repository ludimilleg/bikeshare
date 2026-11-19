package br.ufal.ic.p2.bikeshare.exception.estacao;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class CapacidadeEstacaoNaoPositivaException extends BikeshareException {

    public CapacidadeEstacaoNaoPositivaException() {
        super("Capacidade deve ser positiva.");
    }
}
