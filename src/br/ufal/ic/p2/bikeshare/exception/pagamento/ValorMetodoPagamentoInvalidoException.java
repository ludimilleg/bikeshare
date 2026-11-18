package br.ufal.ic.p2.bikeshare.exception.pagamento;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class ValorMetodoPagamentoInvalidoException extends BikeshareException {

    public ValorMetodoPagamentoInvalidoException() {
        super("Valor de metodo de pagamento invalido.");
    }
}
