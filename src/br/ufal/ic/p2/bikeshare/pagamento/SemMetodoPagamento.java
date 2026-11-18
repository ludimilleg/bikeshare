package br.ufal.ic.p2.bikeshare.pagamento;

import br.ufal.ic.p2.bikeshare.exception.pagamento.UsuarioNaoUsaCartaoException;
import br.ufal.ic.p2.bikeshare.exception.pagamento.UsuarioNaoUsaPixException;
import br.ufal.ic.p2.bikeshare.exception.pagamento.UsuarioNaoUsaSaldoPreCarregadoException;
import br.ufal.ic.p2.bikeshare.exception.validacao.AtributoNaoExisteException;

public class SemMetodoPagamento extends MetodoPagamento {

    @Override
    public String getNome() {
        return "nenhum";
    }

    @Override
    public String getAtributo(String atributo) {
        switch (atributo) {
            case "numeroCartao":
            case "validadeCartao":
                throw new UsuarioNaoUsaCartaoException();
            case "chavePix":
                throw new UsuarioNaoUsaPixException();
            case "saldo":
                throw new UsuarioNaoUsaSaldoPreCarregadoException();
            default:
                throw new AtributoNaoExisteException();
        }
    }
}