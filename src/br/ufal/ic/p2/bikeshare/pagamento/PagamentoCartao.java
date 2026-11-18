package br.ufal.ic.p2.bikeshare.pagamento;

import br.ufal.ic.p2.bikeshare.exception.pagamento.UsuarioNaoUsaSaldoPreCarregadoException;
import br.ufal.ic.p2.bikeshare.exception.pagamento.UsuarioNaoUsaPixException;
import br.ufal.ic.p2.bikeshare.exception.validacao.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.exception.pagamento.NumeroCartaoNuloException;
import br.ufal.ic.p2.bikeshare.exception.pagamento.ValidadeCartaoInvalidaException;
import br.ufal.ic.p2.bikeshare.exception.pagamento.ValidadeCartaoNulaException;
import br.ufal.ic.p2.bikeshare.util.Validador;

import java.util.regex.Pattern;

public class PagamentoCartao extends MetodoPagamento {

    private static final long serialVersionUID = 1L;
    private static final Pattern PADRAO_VALIDADE = Pattern.compile("^(0[1-9]|1[0-2])/\\d{4}$");

    private final String numero;
    private final String validade;

    public PagamentoCartao(String numero, String validade) {
        this.numero = Validador.exigirTexto(numero, NumeroCartaoNuloException::new);
        Validador.exigirTexto(validade, ValidadeCartaoNulaException::new);
        this.validade = Validador.exigirFormato(validade, PADRAO_VALIDADE,
                ValidadeCartaoInvalidaException::new);
    }

    @Override
    public String getNome() {
        return "cartao";
    }

    @Override
    public String getAtributo(String atributo) {
        switch (atributo) {
            case "numeroCartao":
                return numero;
            case "validadeCartao":
                return validade;
            case "chavePix":
                throw new UsuarioNaoUsaPixException();
            case "saldo":
                throw new UsuarioNaoUsaSaldoPreCarregadoException();
            default:
                throw new AtributoNaoExisteException();
        }
    }
}
