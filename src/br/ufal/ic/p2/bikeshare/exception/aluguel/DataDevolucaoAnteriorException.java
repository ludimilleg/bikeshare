package br.ufal.ic.p2.bikeshare.exception.aluguel;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class DataDevolucaoAnteriorException extends BikeshareException {

    public DataDevolucaoAnteriorException() {
        super("Data de devolucao anterior a data de retirada.");
    }
}
