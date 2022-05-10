package br.com.diegochueri.exception;

public class ArquivoVazio extends Exception {
    private static final long serialVersionUID = 1L;

    public ArquivoVazio(String msg) {
        super(msg);
    }
}
