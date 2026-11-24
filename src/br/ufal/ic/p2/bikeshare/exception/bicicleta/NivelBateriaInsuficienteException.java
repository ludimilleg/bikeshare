package br.ufal.ic.p2.bikeshare.exception.bicicleta;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class NivelBateriaInsuficienteException extends BikeshareException {

    public NivelBateriaInsuficienteException() {
        super("Nivel de bateria insuficiente.");
    }
}
