package br.ufal.ic.p2.bikeshare.pagamento;

import br.ufal.ic.p2.bikeshare.exception.pagamento.ChavePixNulaException;
import br.ufal.ic.p2.bikeshare.exception.pagamento.UsuarioNaoUsaCartaoException;
import br.ufal.ic.p2.bikeshare.exception.pagamento.UsuarioNaoUsaSaldoPreCarregadoException;
import br.ufal.ic.p2.bikeshare.exception.validacao.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.util.Validador;

public class PagamentoPix extends MetodoPagamento {

    private static final long serialVersionUID = 1L;

    private final String chave;

    public PagamentoPix(String chave) {
        this.chave = Validador.exigirTexto(chave, ChavePixNulaException::new);
    }

    @Override
    public String getNome() {
        return "pix";
    }

    @Override
    public String getAtributo(String atributo) {
        switch (atributo) {
            case "chavePix":
                return chave;
            case "numeroCartao":
            case "validadeCartao":
                throw new UsuarioNaoUsaCartaoException();
            case "saldo":
                throw new UsuarioNaoUsaSaldoPreCarregadoException();
            default:
                throw new AtributoNaoExisteException();
        }
    }
}
