package br.ufal.ic.p2.bikeshare.exception.pagamento;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class UsuarioNaoUsaCartaoException extends BikeshareException {

    public UsuarioNaoUsaCartaoException() {
        super("Usuario nao usa cartao.");
    }
}
