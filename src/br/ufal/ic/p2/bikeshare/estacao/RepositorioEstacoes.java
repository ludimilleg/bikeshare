package br.ufal.ic.p2.bikeshare.estacao;

import br.ufal.ic.p2.bikeshare.exception.estacao.EstacaoNaoExisteException;
import br.ufal.ic.p2.bikeshare.exception.estacao.IdentificacaoEstacaoNulaException;
import br.ufal.ic.p2.bikeshare.util.GeradorId;
import br.ufal.ic.p2.bikeshare.util.Validador;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class RepositorioEstacoes implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<String, Estacao> estacoes = new LinkedHashMap<>();
    private final GeradorId geradorId = new GeradorId("E");

    public Estacao cadastrar(String nome, String endereco, String capacidade) {
        Estacao estacao = new Estacao(geradorId.proximo(), nome, endereco, capacidade);
        estacoes.put(estacao.getId(), estacao);
        geradorId.confirmarUso();
        return estacao;
    }

    public Estacao buscar(String id) {
        Validador.exigirTexto(id, IdentificacaoEstacaoNulaException::new);
        Estacao estacao = estacoes.get(id);
        if (estacao == null) {
            throw new EstacaoNaoExisteException();
        }
        return estacao;
    }
}
