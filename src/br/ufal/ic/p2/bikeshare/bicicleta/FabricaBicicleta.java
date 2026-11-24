package br.ufal.ic.p2.bikeshare.bicicleta;

import br.ufal.ic.p2.bikeshare.exception.bicicleta.BicicletaNaoPossuiBateriaException;
import br.ufal.ic.p2.bikeshare.exception.validacao.TipoInvalidoException;

import java.util.Map;
import java.util.Optional;

public final class FabricaBicicleta {

    private static final Map<String, CriadorBicicleta> CRIADORES = Map.of(
            "convencional", (id, estacaoId, nivelBateria) -> {
                if (nivelBateria != null) {
                    throw new BicicletaNaoPossuiBateriaException();
                }
                return new BicicletaConvencional(id, estacaoId);
            },
            "eletrica", BicicletaEletrica::new);

    private FabricaBicicleta() {
    }

    public static Bicicleta criar(String tipo, String id,
                                  String estacaoId, String nivelBateria) {
        return Optional.ofNullable(tipo)
                .map(CRIADORES::get)
                .orElseThrow(TipoInvalidoException::new)
                .criar(id, estacaoId, nivelBateria);
    }

    @FunctionalInterface
    private interface CriadorBicicleta {
        Bicicleta criar(String id, String estacaoId, String nivelBateria);
    }
}
