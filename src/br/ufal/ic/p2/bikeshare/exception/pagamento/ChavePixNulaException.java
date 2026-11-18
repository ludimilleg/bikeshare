package br.ufal.ic.p2.bikeshare.exception.pagamento;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class ChavePixNulaException extends BikeshareException {

    public ChavePixNulaException() {
        super("Chave pix nao pode ser nula.");
    }
}
