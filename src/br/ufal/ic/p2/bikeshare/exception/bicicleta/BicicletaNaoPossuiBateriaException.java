package br.ufal.ic.p2.bikeshare.exception.bicicleta;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class BicicletaNaoPossuiBateriaException extends BikeshareException {

    public BicicletaNaoPossuiBateriaException() {
        super("Bicicleta nao possui bateria.");
    }
}
