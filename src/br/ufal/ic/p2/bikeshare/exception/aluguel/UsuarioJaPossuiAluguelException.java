package br.ufal.ic.p2.bikeshare.exception.aluguel;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class UsuarioJaPossuiAluguelException extends BikeshareException {

    public UsuarioJaPossuiAluguelException() {
        super("Usuario ja possui um aluguel em andamento.");
    }
}
