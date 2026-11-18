package br.ufal.ic.p2.bikeshare.exception.usuario;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class CnpjNuloException extends BikeshareException {

    public CnpjNuloException() {
        super("CNPJ nao pode ser nulo.");
    }
}
