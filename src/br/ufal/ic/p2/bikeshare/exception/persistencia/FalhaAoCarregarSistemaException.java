package br.ufal.ic.p2.bikeshare.exception.persistencia;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class FalhaAoCarregarSistemaException extends BikeshareException {

    public FalhaAoCarregarSistemaException(Throwable causa) {
        super("Falha ao carregar o estado do sistema.", causa);
    }
}
