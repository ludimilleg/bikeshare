package br.ufal.ic.p2.bikeshare.bicicleta;

import br.ufal.ic.p2.bikeshare.exception.bicicleta.BicicletaNaoPossuiBateriaException;
import br.ufal.ic.p2.bikeshare.exception.bicicleta.IdentificacaoBicicletaNulaException;
import br.ufal.ic.p2.bikeshare.exception.validacao.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.util.Validador;

import java.io.Serializable;

public abstract class Bicicleta implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String id;
    private String estacaoId;
    private String estado = "disponivel";

    protected Bicicleta(String id, String estacaoId) {
        this.id = Validador.exigirTexto(id, IdentificacaoBicicletaNulaException::new);
        this.estacaoId = estacaoId;
    }

    public String getId() {
        return id;
    }

    public String getEstacaoId() {
        return estacaoId;
    }

    public void retirar() {
        this.estado = "emUso";
        this.estacaoId = null;
    }

    public void devolver(String estacaoId) {
        this.estado = "disponivel";
        this.estacaoId = estacaoId;
    }

    public void enviarParaManutencao() {
        this.estado = "emManutencao";
        this.estacaoId = null;
    }

    public void exigirNivelParaRetirada() {
    }

    public String getEstado() {
        return estado;
    }

    public abstract String getTipo();

    public int getNivelBateria() {
        throw new BicicletaNaoPossuiBateriaException();
    }

    public String getAtributo(String atributo) {
        if (atributo == null) {
            throw new AtributoNaoExisteException();
        }
        switch (atributo) {
            case "tipo":
                return getTipo();
            case "estado":
                return getEstado();
            case "estacao":
                return getEstacaoId() == null ? "nenhuma" : getEstacaoId();
            case "nivelBateria":
                return String.valueOf(getNivelBateria());
            default:
                throw new AtributoNaoExisteException();
        }
    }

    public boolean estaDisponivel() {
        return "disponivel".equals(estado);
    }
}
