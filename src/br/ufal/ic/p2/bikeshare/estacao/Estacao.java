package br.ufal.ic.p2.bikeshare.estacao;

import br.ufal.ic.p2.bikeshare.exception.estacao.CapacidadeEstacaoNaoNumericaException;
import br.ufal.ic.p2.bikeshare.exception.estacao.CapacidadeEstacaoNaoPositivaException;
import br.ufal.ic.p2.bikeshare.exception.estacao.CapacidadeEstacaoNulaException;
import br.ufal.ic.p2.bikeshare.exception.estacao.EnderecoEstacaoNuloException;
import br.ufal.ic.p2.bikeshare.exception.estacao.NomeEstacaoNuloException;
import br.ufal.ic.p2.bikeshare.util.Validador;

import java.io.Serializable;

public class Estacao implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String id;
    private final String nome;
    private final String endereco;
    private final int capacidade;

    public Estacao(String id, String nome, String endereco, String capacidade) {
        this.id = id;
        this.nome = Validador.exigirTexto(nome, NomeEstacaoNuloException::new);
        this.endereco = Validador.exigirTexto(endereco, EnderecoEstacaoNuloException::new);
        this.capacidade = validarCapacidade(capacidade);
    }

    private static int validarCapacidade(String capacidade) {
        Validador.exigirTexto(capacidade, CapacidadeEstacaoNulaException::new);
        int valor;
        try {
            valor = Integer.parseInt(capacidade.trim());
        } catch (NumberFormatException e) {
            throw new CapacidadeEstacaoNaoNumericaException();
        }
        if (valor <= 0) {
            throw new CapacidadeEstacaoNaoPositivaException();
        }
        return valor;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public int getVagasDisponiveis() {
        return capacidade;
    }

    public int getBicicletasDisponiveis() {
        return 0;
    }
}
