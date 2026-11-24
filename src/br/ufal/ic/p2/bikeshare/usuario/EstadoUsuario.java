package br.ufal.ic.p2.bikeshare.usuario;

public enum EstadoUsuario {
    ATIVO("ativo"),
    INADIMPLENTE("inadimplente");

    private final String nome;

    EstadoUsuario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}