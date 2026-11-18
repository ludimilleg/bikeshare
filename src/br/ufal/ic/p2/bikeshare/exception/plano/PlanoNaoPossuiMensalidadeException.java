package br.ufal.ic.p2.bikeshare.exception.plano;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class PlanoNaoPossuiMensalidadeException extends BikeshareException {

    public PlanoNaoPossuiMensalidadeException() {
        super("Plano nao possui mensalidade.");
    }
}
