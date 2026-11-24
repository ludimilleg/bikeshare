package br.ufal.ic.p2.bikeshare.bicicleta;

import br.ufal.ic.p2.bikeshare.estacao.Estacao;
import br.ufal.ic.p2.bikeshare.estacao.RepositorioEstacoes;
import br.ufal.ic.p2.bikeshare.exception.bicicleta.BicicletaJaExisteException;
import br.ufal.ic.p2.bikeshare.exception.bicicleta.BicicletaNaoExisteException;
import br.ufal.ic.p2.bikeshare.exception.bicicleta.BicicletaNaoEstaNestaEstacaoException;
import br.ufal.ic.p2.bikeshare.exception.bicicleta.EstacaoSemVagasException;
import br.ufal.ic.p2.bikeshare.exception.aluguel.EstacaoCheiaException;
import br.ufal.ic.p2.bikeshare.exception.bicicleta.IdentificacaoBicicletaNulaException;
import br.ufal.ic.p2.bikeshare.util.Validador;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class RepositorioBicicletas implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<String, Bicicleta> bicicletas = new LinkedHashMap<>();
    private final RepositorioEstacoes estacoes;

    public RepositorioBicicletas(RepositorioEstacoes estacoes) {
        this.estacoes = estacoes;
    }

    public Bicicleta cadastrar(String estacaoId, String bicicletaId, String tipo) {
        return cadastrarConvencionalOuEletrica(estacaoId, bicicletaId, tipo, null);
    }

    public Bicicleta cadastrar(String estacaoId, String bicicletaId, String tipo, String nivelBateria) {
        return cadastrarConvencionalOuEletrica(estacaoId, bicicletaId, tipo, nivelBateria);
    }

    private Bicicleta cadastrarConvencionalOuEletrica(
            String estacaoId, String bicicletaId, String tipo, String nivelBateria) {
        Estacao estacao = estacoes.buscar(estacaoId);
        Validador.exigirTexto(bicicletaId, IdentificacaoBicicletaNulaException::new);
        if (bicicletas.containsKey(bicicletaId)) {
            throw new BicicletaJaExisteException();
        }

        Bicicleta bicicleta = FabricaBicicleta.criar(
                tipo, bicicletaId, estacaoId, nivelBateria);

        if (contarOcupadas(estacaoId) >= estacao.getCapacidade()) {
            throw new EstacaoSemVagasException();
        }

        bicicletas.put(bicicletaId, bicicleta);
        return bicicleta;
    }

    public Bicicleta buscar(String id) {
        Validador.exigirTexto(id, IdentificacaoBicicletaNulaException::new);
        Bicicleta bicicleta = bicicletas.get(id);
        if (bicicleta == null) {
            throw new BicicletaNaoExisteException();
        }
        return bicicleta;
    }

    public void remover(String estacaoId, String bicicletaId) {
        Estacao estacao = estacoes.buscar(estacaoId);
        Bicicleta bicicleta = buscar(bicicletaId);
        if (!bicicleta.getEstacaoId().equals(estacao.getId())) {
            throw new BicicletaNaoEstaNestaEstacaoException();
        }
        bicicletas.remove(bicicletaId);
    }

    public Bicicleta retirar(String estacaoId, String bicicletaId) {
        Estacao estacao = estacoes.buscar(estacaoId);
        Bicicleta bicicleta = buscar(bicicletaId);
        if (bicicleta.getEstacaoId() == null
                || !bicicleta.getEstacaoId().equals(estacao.getId())) {
            throw new BicicletaNaoEstaNestaEstacaoException();
        }
        bicicleta.exigirNivelParaRetirada();
        bicicleta.retirar();
        return bicicleta;
    }

    public void validarDevolucao(String estacaoId) {
        Estacao estacao = estacoes.buscar(estacaoId);
        if (contarOcupadas(estacaoId) >= estacao.getCapacidade()) {
            throw new EstacaoCheiaException();
        }
    }

    public void devolver(String estacaoId, String bicicletaId) {
        validarDevolucao(estacaoId);
        Bicicleta bicicleta = buscar(bicicletaId);
        bicicleta.devolver(estacaoId);
    }

    public void enviarParaManutencao(String bicicletaId) {
        buscar(bicicletaId).enviarParaManutencao();
    }

    public int getBicicletasDisponiveis(String estacaoId) {
        Estacao estacao = estacoes.buscar(estacaoId);
        return (int) bicicletas.values().stream()
                .filter(bicicleta -> estacao.getId().equals(bicicleta.getEstacaoId()))
                .filter(Bicicleta::estaDisponivel)
                .count();
    }

    public int getVagasDisponiveis(String estacaoId) {
        Estacao estacao = estacoes.buscar(estacaoId);
        return estacao.getCapacidade() - contarOcupadas(estacaoId);
    }

    private int contarOcupadas(String estacaoId) {
        return (int) bicicletas.values().stream()
                .filter(bicicleta -> estacaoId.equals(bicicleta.getEstacaoId()))
                .count();
    }
}
