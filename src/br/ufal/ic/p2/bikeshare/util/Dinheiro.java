package br.ufal.ic.p2.bikeshare.util;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Supplier;

public final class Dinheiro {

    private static final int CASAS_DECIMAIS = 2;

    private Dinheiro() {
    }

    /**
     * Lê um valor com vírgula ou ponto decimal. O texto deve estar preenchido.
     * @param erro fábrica da exceção lançada se o texto não for numérico
     */
    
    public static BigDecimal ler(String texto, Supplier<? extends BikeshareException> erro) {
        try {
            return new BigDecimal(texto.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw erro.get();
        }
    }

    public static String formatar(BigDecimal valor) {
        return valor.setScale(CASAS_DECIMAIS, RoundingMode.HALF_UP)
                .toPlainString()
                .replace('.', ',');
    }
}