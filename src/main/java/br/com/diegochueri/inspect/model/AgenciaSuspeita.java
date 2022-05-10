package br.com.diegochueri.inspect.model;

public class AgenciaSuspeita {
    private String banco;
    private Integer agencia;
    private double valor;
    private String tipo;

    public AgenciaSuspeita() {
    }

    public AgenciaSuspeita(String banco, Integer agencia, double valor, String tipo) {
        this.banco = banco;
        this.agencia = agencia;
        this.valor = valor;
        this.tipo = tipo;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public Integer getAgencia() {
        return agencia;
    }

    public void setAgencia(Integer agencia) {
        this.agencia = agencia;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
