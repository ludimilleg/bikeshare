package br.ufal.ic.p2.bikeshare.bicicleta;

import br.ufal.ic.p2.bikeshare.exception.bicicleta.NivelBateriaForaIntervaloException;
import br.ufal.ic.p2.bikeshare.exception.bicicleta.NivelBateriaNaoNumericoException;
import br.ufal.ic.p2.bikeshare.exception.bicicleta.NivelBateriaNuloException;

public class BicicletaEletrica extends Bicicleta {

    private static final long serialVersionUID = 1L;

    private final int nivelBateria;

    public BicicletaEletrica(String id, String estacaoId, String nivelBateria) {
        super(id, estacaoId);
        this.nivelBateria = validarNivelBateria(nivelBateria);
    }

    private static int validarNivelBateria(String nivelBateria) {
        if (nivelBateria == null || nivelBateria.isBlank()) {
            throw new NivelBateriaNuloException();
        }
        int valor;
        try {
            valor = Integer.parseInt(nivelBateria.trim());
        } catch (NumberFormatException e) {
            throw new NivelBateriaNaoNumericoException();
        }
        if (valor < 0 || valor > 100) {
            throw new NivelBateriaForaIntervaloException();
        }
        return valor;
    }

    @Override
    public String getTipo() {
        return "eletrica";
    }

    @Override
    public int getNivelBateria() {
        return nivelBateria;
    }

    public boolean estaDisponivel() {
        return super.estaDisponivel() && nivelBateria >= 15;
    }
}
