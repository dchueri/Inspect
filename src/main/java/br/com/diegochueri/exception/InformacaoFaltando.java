package br.com.diegochueri.exception;

public class InformacaoFaltando extends Exception {
    private static final long serialVersionUID = 1L;

    public InformacaoFaltando(String msg) {
        super(msg);
    }
}
