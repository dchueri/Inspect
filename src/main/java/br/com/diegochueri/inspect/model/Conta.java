package br.com.diegochueri.inspect.model;

public class Conta {

	private String banco;
	private Integer agencia;
	private String conta;
	private double valor;
	private String tipo;

	public Conta() {
	};

	public Conta(String banco, Integer agencia, String conta, Double valor, String tipo) {
		this.banco = banco;
		this.agencia = agencia;
		this.conta = conta;
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

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void sum(double sum) {
		this.valor += sum;
	}

}
