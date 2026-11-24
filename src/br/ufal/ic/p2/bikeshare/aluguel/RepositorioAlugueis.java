package br.ufal.ic.p2.bikeshare.aluguel;

import br.ufal.ic.p2.bikeshare.exception.aluguel.AluguelNaoExisteException;
import br.ufal.ic.p2.bikeshare.exception.aluguel.IdentificacaoAluguelNulaException;
import br.ufal.ic.p2.bikeshare.util.GeradorId;
import br.ufal.ic.p2.bikeshare.util.Validador;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class RepositorioAlugueis implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<String, Aluguel> alugueis = new LinkedHashMap<>();
    private final GeradorId geradorId = new GeradorId("A");

    Aluguel cadastrar(String usuarioId, String bicicletaId,
                      String estacaoOrigemId, String data) {
        Aluguel aluguel = new Aluguel(
                geradorId.proximo(), usuarioId, bicicletaId, estacaoOrigemId, data);
        alugueis.put(aluguel.getId(), aluguel);
        geradorId.confirmarUso();
        return aluguel;
    }

    Aluguel buscar(String id) {
        Validador.exigirTexto(id, IdentificacaoAluguelNulaException::new);
        Aluguel aluguel = alugueis.get(id);
        if (aluguel == null) {
            throw new AluguelNaoExisteException();
        }
        return aluguel;
    }
}
