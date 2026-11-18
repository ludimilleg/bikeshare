package br.ufal.ic.p2.bikeshare.exception.usuario;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class EmailJaCadastradoException extends BikeshareException {

    public EmailJaCadastradoException() {
        super("Email ja cadastrado.");
    }
}
