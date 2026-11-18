package br.ufal.ic.p2.bikeshare.exception.plano;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class MensalidadeNaoNumericaException extends BikeshareException {

    public MensalidadeNaoNumericaException() {
        super("Mensalidade deve ser numerica.");
    }
}
