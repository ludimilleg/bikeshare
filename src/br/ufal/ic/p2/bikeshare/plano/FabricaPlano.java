package br.ufal.ic.p2.bikeshare.plano;

import br.ufal.ic.p2.bikeshare.exception.plano.PlanoInvalidoException;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public final class FabricaPlano {

    public static final String PLANO_PADRAO = "avulso";

    private static final String MENSALIDADE_PADRAO_ASSINANTE = "30";

    private static final Map<String, Function<String, Plano>> CRIADORES = Map.of(
            "avulso", PlanoAvulso::criar,
            "assinante", PlanoAssinante::criar);

    private FabricaPlano() {
    }

    public static Plano criar(String nome, String mensalidade) {
        return Optional.ofNullable(nome)
                .map(CRIADORES::get)
                .orElseThrow(PlanoInvalidoException::new)
                .apply(mensalidade);
    }

    public static Plano criarComMensalidadePadrao(String nome) {
        String mensalidade = "assinante".equals(nome) ? MENSALIDADE_PADRAO_ASSINANTE : null;
        return criar(nome, mensalidade);
    }
}