package br.ufal.ic.p2.bikeshare.plano;

import br.ufal.ic.p2.bikeshare.exception.plano.MensalidadeNaoNumericaException;
import br.ufal.ic.p2.bikeshare.exception.plano.MensalidadeNegativaException;
import br.ufal.ic.p2.bikeshare.exception.plano.MensalidadeNulaException;
import br.ufal.ic.p2.bikeshare.util.Dinheiro;
import br.ufal.ic.p2.bikeshare.util.Validador;

import java.math.BigDecimal;

public class PlanoAssinante extends Plano {

    private final BigDecimal mensalidade;

    private PlanoAssinante(BigDecimal mensalidade) {
        this.mensalidade = mensalidade;
    }

    public static PlanoAssinante criar(String mensalidade) {
        Validador.exigirTexto(mensalidade, MensalidadeNulaException::new);
        BigDecimal valor = Dinheiro.ler(mensalidade, MensalidadeNaoNumericaException::new);
        return new PlanoAssinante(Validador.exigirNaoNegativo(valor, MensalidadeNegativaException::new));
    }

    @Override
    public String getNome() {
        return "assinante";
    }

    @Override
    public BigDecimal getMensalidade() {
        return mensalidade;
    }

    @Override
    public BigDecimal calcularValor(long minutos) {
        long excedente = Math.max(0, minutos - 60);
        return new BigDecimal("0.30").multiply(BigDecimal.valueOf(excedente));
    }
}