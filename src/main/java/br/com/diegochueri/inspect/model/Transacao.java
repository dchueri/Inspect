package br.com.diegochueri.inspect.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
	private String valor;
	private LocalDate dataDaTransacao;
	private LocalDateTime dataHoraDaInclusao;
	
	public static String erroCampoVazio(List<Integer> linhasFaltandoDados) {
		return ("As linhas " + linhasFaltandoDados + " não foram adicionadas por estarem campos incompletos.");
	}
	
	public static Object erroDatasDiferentes(List<Integer> linhasComDatasDiferentes) {
		return ("As linhas " + linhasComDatasDiferentes + " não foram adicionadas por estarem com datas divergentes.");
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
	public String getValor() {
		return valor;
	}
	public void setValor(String record) {
		this.valor = record;
	}
	public LocalDate getDataDaTransacao() {
		return dataDaTransacao;
	}
	public void setDataDaTransacao(LocalDateTime localDate) {
		this.dataDaTransacao = localDate.toLocalDate();;
	}

	public LocalDateTime getDataHoraDaInclusao() {
		return dataHoraDaInclusao;
	}

	public void setDataHoraDaInclusao(LocalDateTime dataHoraDaInclusao) {
		this.dataHoraDaInclusao = dataHoraDaInclusao;
	}


}
