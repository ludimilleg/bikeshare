package br.ufal.ic.p2.bikeshare.pagamento;

import java.io.Serializable;

public abstract class MetodoPagamento implements Serializable {

    public abstract String getNome();
}