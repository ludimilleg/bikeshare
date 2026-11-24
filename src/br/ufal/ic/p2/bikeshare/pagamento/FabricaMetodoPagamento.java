package br.ufal.ic.p2.bikeshare.pagamento;

public final class FabricaMetodoPagamento {

    private FabricaMetodoPagamento() {
    }

    public static MetodoPagamento criarPix(String chave) {
        return new PagamentoPix(chave);
    }

    public static MetodoPagamento criarCartao(String numero, String validade) {
        return new PagamentoCartao(numero, validade);
    }

    public static MetodoPagamento criarSaldoPreCarregado() {
        return new PagamentoSaldoPreCarregado();
    }
}
