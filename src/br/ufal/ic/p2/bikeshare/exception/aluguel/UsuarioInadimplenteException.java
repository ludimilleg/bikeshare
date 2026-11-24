package br.ufal.ic.p2.bikeshare.exception.aluguel;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class UsuarioInadimplenteException extends BikeshareException {

    public UsuarioInadimplenteException() {
        super("Usuario inadimplente.");
    }
}
