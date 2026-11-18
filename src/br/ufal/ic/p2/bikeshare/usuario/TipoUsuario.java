package br.ufal.ic.p2.bikeshare.usuario;

import br.ufal.ic.p2.bikeshare.exception.validacao.TipoInvalidoException;

public enum TipoUsuario {
    PESSOA_FISICA("pessoaFisica"),
    PESSOA_JURIDICA("pessoaJuridica");

    private final String nome;

    TipoUsuario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void exigirIgual(String informado) {
        if (!nome.equals(informado)) {
            throw new TipoInvalidoException();
        }
    }
}