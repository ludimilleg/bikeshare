package br.ufal.ic.p2.bikeshare.pagamento;

import br.ufal.ic.p2.bikeshare.exception.pagamento.ValorNaoNumericoException;
import br.ufal.ic.p2.bikeshare.exception.pagamento.ValorNaoPositivoException;
import br.ufal.ic.p2.bikeshare.exception.pagamento.ValorNuloException;
import br.ufal.ic.p2.bikeshare.exception.pagamento.UsuarioNaoUsaCartaoException;
import br.ufal.ic.p2.bikeshare.exception.pagamento.UsuarioNaoUsaPixException;
import br.ufal.ic.p2.bikeshare.exception.validacao.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.util.Dinheiro;
import br.ufal.ic.p2.bikeshare.util.Validador;

import java.math.BigDecimal;

public class PagamentoSaldoPreCarregado extends MetodoPagamento {

    private static final long serialVersionUID = 1L;

    private BigDecimal saldo = BigDecimal.ZERO;

    public boolean possuiSaldo() {
        return saldo.signum() > 0;
    }

    public boolean possuiSaldo(BigDecimal valor) {
        return saldo.compareTo(valor) >= 0;
    }

    public void debitarSaldo(BigDecimal valor) {
        if (!possuiSaldo(valor)) {
            throw new br.ufal.ic.p2.bikeshare.exception.aluguel.SaldoInsuficienteException();
        }
        saldo = saldo.subtract(valor);
    }

    @Override
    public String getNome() {
        return "saldoPreCarregado";
    }

    @Override
    public String getAtributo(String atributo) {
        switch (atributo) {
            case "saldo":
                return Dinheiro.formatar(saldo);
            case "numeroCartao":
            case "validadeCartao":
                throw new UsuarioNaoUsaCartaoException();
            case "chavePix":
                throw new UsuarioNaoUsaPixException();
            default:
                throw new AtributoNaoExisteException();
        }
    }

    @Override
    public void adicionarSaldo(String valor) {
        Validador.exigirTexto(valor, ValorNuloException::new);
        BigDecimal valorAdicionado = Dinheiro.ler(valor, ValorNaoNumericoException::new);
        if (valorAdicionado.signum() <= 0) {
            throw new ValorNaoPositivoException();
        }
        saldo = saldo.add(valorAdicionado);
    }
}
