package br.com.diegochueri.inspect.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.*;

@Entity
public class Transacao {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String bancoDeOrigem;
	private String agenciaDeOrigem;
	private String contaDeOrigem;
	private String bancoDeDestino;
	private String agenciaDeDestino;
	private String contaDeDestino;
	private double valor;
	private LocalDateTime dataHora;
	
	public void pegaArquivo() {
		
	}
	
	public String getBancoDeOrigem() {
		return bancoDeOrigem;
	}
	public void setBancoDeOrigem(String bancoDeOrigem) {
		this.bancoDeOrigem = bancoDeOrigem;
	}
	public String getAgenciaDeOrigem() {
		return agenciaDeOrigem;
	}
	public void setAgenciaDeOrigem(String record) {
		this.agenciaDeOrigem = record;
	}
	public String getContaDeOrigem() {
		return contaDeOrigem;
	}
	public void setContaDeOrigem(String record) {
		this.contaDeOrigem = record;
	}
	public String getBancoDeDestino() {
		return bancoDeDestino;
	}
	public void setBancoDeDestino(String bancoDeDestino) {
		this.bancoDeDestino = bancoDeDestino;
	}
	public String getAgenciaDeDestino() {
		return agenciaDeDestino;
	}
	public void setAgenciaDeDestino(String record) {
		this.agenciaDeDestino = record;
	}
	public String getContaDeDestino() {
		return contaDeDestino;
	}
	public void setContaDeDestino(String record) {
		this.contaDeDestino = record;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public LocalDateTime getDataHora() {
		return dataHora;
	}
	public void setDataHora(LocalDateTime record) {
		this.dataHora = record;
	}
}
