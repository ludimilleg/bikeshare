package br.ufal.ic.p2.bikeshare.exception.bicicleta;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class IdentificacaoBicicletaNulaException extends BikeshareException {

    public IdentificacaoBicicletaNulaException() {
        super("Identificacao da bicicleta nao pode ser nula.");
    }
}
