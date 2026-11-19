package br.ufal.ic.p2.bikeshare.exception.bicicleta;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class NivelBateriaForaIntervaloException extends BikeshareException {

    public NivelBateriaForaIntervaloException() {
        super("Nivel de bateria deve estar entre 0 e 100.");
    }
}
