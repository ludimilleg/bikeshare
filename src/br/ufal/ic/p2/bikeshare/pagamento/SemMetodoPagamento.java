package br.ufal.ic.p2.bikeshare.pagamento;

public class SemMetodoPagamento extends MetodoPagamento {

    @Override
    public String getNome() {
        return "nenhum";
    }
}