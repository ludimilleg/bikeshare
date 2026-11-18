package br.ufal.ic.p2.bikeshare.pagamento;

import br.ufal.ic.p2.bikeshare.exception.pagamento.UsuarioNaoUsaSaldoPreCarregadoException;
import br.ufal.ic.p2.bikeshare.exception.validacao.AtributoNaoExisteException;

import java.io.Serializable;

public abstract class MetodoPagamento implements Serializable {

    private static final long serialVersionUID = 5911717227349826372L;

    public abstract String getNome();

    public String getAtributo(String atributo) {
        throw new AtributoNaoExisteException();
    }

    public void adicionarSaldo(String valor) {
        throw new UsuarioNaoUsaSaldoPreCarregadoException();
    }
}