package br.ufal.ic.p2.bikeshare.exception.estacao;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class CapacidadeEstacaoNulaException extends BikeshareException {

    public CapacidadeEstacaoNulaException() {
        super("Capacidade nao pode ser nula.");
    }
}
