package br.ufal.ic.p2.bikeshare.exception.validacao;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class AtributoNaoExisteException extends BikeshareException {

    public AtributoNaoExisteException() {
        super("Atributo nao existe.");
    }
}
