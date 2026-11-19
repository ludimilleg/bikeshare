package br.ufal.ic.p2.bikeshare.exception.estacao;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class EnderecoEstacaoNuloException extends BikeshareException {

    public EnderecoEstacaoNuloException() {
        super("Endereco nao pode ser nulo.");
    }
}
