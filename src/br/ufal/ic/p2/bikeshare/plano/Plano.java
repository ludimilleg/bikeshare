package br.ufal.ic.p2.bikeshare.plano;

import java.io.Serializable;
import java.math.BigDecimal;

public abstract class Plano implements Serializable {

    public abstract String getNome();

    public abstract BigDecimal getMensalidade();
}