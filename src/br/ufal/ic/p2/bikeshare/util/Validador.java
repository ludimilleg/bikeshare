package br.ufal.ic.p2.bikeshare.util;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

import java.math.BigDecimal;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public final class Validador {

    private Validador() {
    }

    /**
     * Garante que o texto não é nulo nem composto só de espaços.
     *
     * @param valor texto recebido
     * @param erro  fábrica da exceção específica lançada se o texto for inválido
     * @return o próprio texto, para permitir uso em atribuições
     */


    public static String exigirTexto(String valor, Supplier<? extends BikeshareException> erro) {
        if (valor == null || valor.isBlank()) {
            throw erro.get();
        }
        return valor;
    }

    public static String exigirFormato(String valor, Pattern padrao,
                                       Supplier<? extends BikeshareException> erro) {
        if (!padrao.matcher(valor).matches()) {
            throw erro.get();
        }
        return valor;
    }

    public static BigDecimal exigirNaoNegativo(BigDecimal valor,
                                               Supplier<? extends BikeshareException> erro) {
        if (valor.signum() < 0) {
            throw erro.get();
        }
        return valor;
    }
}