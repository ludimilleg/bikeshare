package br.ufal.ic.p2.bikeshare.usuario;

import br.ufal.ic.p2.bikeshare.exception.usuario.EmailJaCadastradoException;
import br.ufal.ic.p2.bikeshare.exception.usuario.EmailNuloException;
import br.ufal.ic.p2.bikeshare.exception.usuario.IdentificacaoUsuarioNulaException;
import br.ufal.ic.p2.bikeshare.exception.usuario.UsuarioNaoExisteException;
import br.ufal.ic.p2.bikeshare.util.GeradorId;
import br.ufal.ic.p2.bikeshare.util.Validador;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class RepositorioUsuarios implements Serializable {

    private static final long serialVersionUID = -1653459431383190735L;

    private final Map<String, Usuario> usuarios = new LinkedHashMap<>();
    private final GeradorId geradorId = new GeradorId("U");

    public Usuario cadastrar(Function<String, Usuario> criador) {
        Usuario usuario = criador.apply(geradorId.proximo());
        exigirEmailDisponivel(usuario.getEmail());
        usuarios.put(usuario.getId(), usuario);
        geradorId.confirmarUso();
        return usuario;
    }

    public Usuario buscar(String id) {
        Validador.exigirTexto(id, IdentificacaoUsuarioNulaException::new);
        Usuario usuario = usuarios.get(id);
        if (usuario == null) {
            throw new UsuarioNaoExisteException();
        }
        return usuario;
    }

    public Usuario buscarPorEmail(String email) {
        Validador.exigirTexto(email, EmailNuloException::new);
        return usuarios.values().stream()
                .filter(usuario -> usuario.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(UsuarioNaoExisteException::new);
    }

    private void exigirEmailDisponivel(String email) {
        boolean emUso = usuarios.values().stream()
                .anyMatch(existente -> existente.getEmail().equalsIgnoreCase(email));
        if (emUso) {
            throw new EmailJaCadastradoException();
        }
    }
}