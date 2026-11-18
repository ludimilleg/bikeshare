package br.ufal.ic.p2.bikeshare.exception.usuario;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class IdentificacaoUsuarioNulaException extends BikeshareException {

    public IdentificacaoUsuarioNulaException() {
        super("Identificacao do usuario nao pode ser nula.");
    }
}
