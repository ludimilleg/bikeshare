package br.ufal.ic.p2.bikeshare.exception.validacao;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class AtributoNaoPodeSerAlteradoException extends BikeshareException {

    public AtributoNaoPodeSerAlteradoException() {
        super("Atributo nao pode ser alterado.");
    }
}
