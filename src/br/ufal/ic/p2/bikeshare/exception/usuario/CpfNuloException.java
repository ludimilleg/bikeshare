package br.ufal.ic.p2.bikeshare.exception.usuario;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class CpfNuloException extends BikeshareException {

    public CpfNuloException() {
        super("CPF nao pode ser nulo.");
    }
}
