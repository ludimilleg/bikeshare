package br.ufal.ic.p2.bikeshare.exception.validacao;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class TipoInvalidoException extends BikeshareException {

    public TipoInvalidoException() {
        super("Tipo invalido.");
    }
}
