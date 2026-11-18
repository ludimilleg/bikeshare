package br.ufal.ic.p2.bikeshare.util;

import java.io.Serializable;

/**
 * Gera identificadores sequenciais (U1, U2, ...).
 * O número só é consumido em {@link #confirmarUso()}, para que um cadastro
 * recusado não deixe buracos na sequência.
 */

public class GeradorId implements Serializable {

    private final String prefixo;
    private int ultimoUsado;

    public GeradorId(String prefixo) {
        this.prefixo = prefixo;
    }

    public String proximo() {
        return prefixo + (ultimoUsado + 1);
    }

    public void confirmarUso() {
        ultimoUsado++;
    }
}