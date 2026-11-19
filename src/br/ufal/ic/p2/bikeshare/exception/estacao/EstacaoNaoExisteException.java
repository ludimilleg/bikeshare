package br.ufal.ic.p2.bikeshare.exception.estacao;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class EstacaoNaoExisteException extends BikeshareException {

    public EstacaoNaoExisteException() {
        super("Estacao nao existe.");
    }
}
