package br.ufal.ic.p2.bikeshare.exception.aluguel;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class AluguelNaoEstaEmAndamentoException extends BikeshareException {

    public AluguelNaoEstaEmAndamentoException() {
        super("Aluguel nao esta em andamento.");
    }
}
