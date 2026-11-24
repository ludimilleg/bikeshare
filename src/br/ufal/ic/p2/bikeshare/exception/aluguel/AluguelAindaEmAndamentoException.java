package br.ufal.ic.p2.bikeshare.exception.aluguel;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class AluguelAindaEmAndamentoException extends BikeshareException {

    public AluguelAindaEmAndamentoException() {
        super("Aluguel ainda esta em andamento.");
    }
}
