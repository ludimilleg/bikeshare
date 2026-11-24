package br.ufal.ic.p2.bikeshare.util;

import java.math.BigDecimal;

public final class ConfiguracaoBikeshare {

    public static final BigDecimal TARIFA_AVULSO_POR_MINUTO = new BigDecimal("0.50");
    public static final BigDecimal TARIFA_ASSINANTE_EXCEDENTE_POR_MINUTO = new BigDecimal("0.30");
    public static final BigDecimal ADICIONAL_ELETRICA_POR_MINUTO = new BigDecimal("0.20");
    public static final BigDecimal PENALIDADE_ATRASO = new BigDecimal("10.00");

    public static final long FRANQUIA_ASSINANTE_MINUTOS = 60;
    public static final long PRAZO_CARENCIA_MINUTOS = 2;
    public static final long TEMPO_MAXIMO_ALUGUEL_MINUTOS = 1440;
    public static final int BATERIA_MINIMA_DISPONIVEL = 15;

    private ConfiguracaoBikeshare() {
    }
}
