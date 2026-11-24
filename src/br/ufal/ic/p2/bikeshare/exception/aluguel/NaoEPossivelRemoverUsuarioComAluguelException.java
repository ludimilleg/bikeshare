package br.ufal.ic.p2.bikeshare.exception.aluguel;

import br.ufal.ic.p2.bikeshare.exception.BikeshareException;

public class NaoEPossivelRemoverUsuarioComAluguelException extends BikeshareException {

    public NaoEPossivelRemoverUsuarioComAluguelException() {
        super("Nao e possivel remover usuario com aluguel em andamento.");
    }
}
