package br.com.diegochueri.exception;

public class NaoSuportado extends Exception {
    private static final long serialVersionUID = 1L;

    public NaoSuportado(String msg) {
        super(msg);
    }
}
