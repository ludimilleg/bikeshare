package br.ufal.ic.p2.bikeshare.persistencia;

import br.ufal.ic.p2.bikeshare.SistemaBikeshare;
import br.ufal.ic.p2.bikeshare.exception.persistencia.FalhaAoCarregarSistemaException;
import br.ufal.ic.p2.bikeshare.exception.persistencia.FalhaAoSalvarSistemaException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ArmazenamentoSistema {

    private final Path arquivo;

    public ArmazenamentoSistema(Path arquivo) {
        this.arquivo = arquivo;
    }

    public SistemaBikeshare carregar() {
        if (!Files.exists(arquivo)) {
            return new SistemaBikeshare();
        }
        try (ObjectInputStream entrada = new ObjectInputStream(Files.newInputStream(arquivo))) {
            return (SistemaBikeshare) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new FalhaAoCarregarSistemaException(e);
        }
    }

    public void salvar(SistemaBikeshare sistema) {
        try (ObjectOutputStream saida = new ObjectOutputStream(Files.newOutputStream(arquivo))) {
            saida.writeObject(sistema);
        } catch (IOException e) {
            throw new FalhaAoSalvarSistemaException(e);
        }
    }
}