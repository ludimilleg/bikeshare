package br.ufal.ic.p2.bikeshare.exception.persistencia;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class FalhaAoSalvarSistemaException extends BikeshareException {

    public FalhaAoSalvarSistemaException(Throwable causa) {
        super("Falha ao salvar o estado do sistema.", causa);
    }
}
