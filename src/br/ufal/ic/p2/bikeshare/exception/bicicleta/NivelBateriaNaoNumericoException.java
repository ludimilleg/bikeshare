package br.ufal.ic.p2.bikeshare.exception.bicicleta;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class NivelBateriaNaoNumericoException extends BikeshareException {

    public NivelBateriaNaoNumericoException() {
        super("Nivel de bateria deve ser numerico.");
    }
}
