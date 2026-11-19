package br.ufal.ic.p2.bikeshare.exception.estacao;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class IdentificacaoEstacaoNulaException extends BikeshareException {

    public IdentificacaoEstacaoNulaException() {
        super("Identificacao da estacao nao pode ser nula.");
    }
}
