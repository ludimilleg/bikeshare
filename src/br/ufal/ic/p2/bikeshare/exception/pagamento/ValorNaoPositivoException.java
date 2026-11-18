package br.ufal.ic.p2.bikeshare.exception.pagamento;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class ValorNaoPositivoException extends BikeshareException {

    public ValorNaoPositivoException() {
        super("Valor deve ser positivo.");
    }
}
