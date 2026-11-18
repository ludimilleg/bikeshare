package br.ufal.ic.p2.bikeshare.exception;

public abstract class BikeshareException extends RuntimeException {

    protected BikeshareException(String mensagem) {
        super(mensagem);
    }

    protected BikeshareException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}