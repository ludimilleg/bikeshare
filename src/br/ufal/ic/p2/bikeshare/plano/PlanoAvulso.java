package br.ufal.ic.p2.bikeshare.plano;

import br.ufal.ic.p2.bikeshare.exception.plano.PlanoNaoPossuiMensalidadeException;

import java.math.BigDecimal;

public class PlanoAvulso extends Plano {

    public static PlanoAvulso criar(String mensalidade) {
        if (mensalidade != null && !mensalidade.isBlank()) {
            throw new PlanoNaoPossuiMensalidadeException();
        }
        return new PlanoAvulso();
    }

    @Override
    public String getNome() {
        return "avulso";
    }

    @Override
    public BigDecimal getMensalidade() {
        throw new PlanoNaoPossuiMensalidadeException();
    }
}