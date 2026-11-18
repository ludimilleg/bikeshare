package br.ufal.ic.p2.bikeshare.exception.pagamento;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class UsuarioNaoUsaSaldoPreCarregadoException extends BikeshareException {

    public UsuarioNaoUsaSaldoPreCarregadoException() {
        super("Usuario nao usa saldo pre-carregado.");
    }
}
