package br.ufal.ic.p2.bikeshare.exception.bicicleta;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class EstacaoSemVagasException extends BikeshareException {

    public EstacaoSemVagasException() {
        super("Estacao sem vagas disponiveis.");
    }
}
