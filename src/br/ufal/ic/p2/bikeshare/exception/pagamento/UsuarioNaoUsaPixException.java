package br.ufal.ic.p2.bikeshare.exception.pagamento;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class UsuarioNaoUsaPixException extends BikeshareException {

    public UsuarioNaoUsaPixException() {
        super("Usuario nao usa pix.");
    }
}
