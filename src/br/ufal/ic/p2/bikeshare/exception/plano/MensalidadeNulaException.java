package br.ufal.ic.p2.bikeshare.exception.plano;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class MensalidadeNulaException extends BikeshareException {

    public MensalidadeNulaException() {
        super("Mensalidade nao pode ser nula.");
    }
}
