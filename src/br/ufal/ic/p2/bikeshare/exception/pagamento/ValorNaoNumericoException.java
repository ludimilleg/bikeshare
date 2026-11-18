package br.ufal.ic.p2.bikeshare.exception.pagamento;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class ValorNaoNumericoException extends BikeshareException {

    public ValorNaoNumericoException() {
        super("Valor deve ser numerico.");
    }
}
