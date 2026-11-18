package br.ufal.ic.p2.bikeshare.exception.plano;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class MensalidadeNegativaException extends BikeshareException {

    public MensalidadeNegativaException() {
        super("Mensalidade deve ser nao-negativa.");
    }
}
