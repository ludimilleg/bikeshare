package br.ufal.ic.p2.bikeshare.exception.bicicleta;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class NivelBateriaNuloException extends BikeshareException {

    public NivelBateriaNuloException() {
        super("Nivel de bateria nao pode ser nulo.");
    }
}
