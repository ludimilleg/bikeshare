package br.ufal.ic.p2.bikeshare.exception.usuario;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class UsuarioNaoExisteException extends BikeshareException {

    public UsuarioNaoExisteException() {
        super("Usuario nao existe.");
    }
}
