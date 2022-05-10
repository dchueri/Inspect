package br.com.diegochueri.inspect.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Transacao implements Comparable<Transacao> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	@ManyToOne
	private Users user;

	public Transacao() {}

	public Transacao(String bancoDeOrigem, String agenciaDeOrigem, String contaDeOrigem, String bancoDeDestino,
	String agenciaDeDestino, String contaDeDestino, String valor, String dataDaTransacao, Users user) {
		LocalDateTime data = LocalDateTime.parse(dataDaTransacao);
		this.bancoDeOrigem = bancoDeOrigem;
		this.agenciaDeOrigem = agenciaDeOrigem;
		this.contaDeOrigem = contaDeOrigem;
		this.bancoDeDestino = bancoDeDestino;
		this.agenciaDeDestino = agenciaDeDestino;
		this.contaDeDestino = contaDeDestino;
		this.valor = valor;
		this.dataDaTransacao = data.toLocalDate();
		this.dataHoraDaInclusao = LocalDateTime.now();
		this.user = user;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

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
		this.dataDaTransacao = localDate.toLocalDate();
	}

	public LocalDateTime getDataHoraDaInclusao() {
		return dataHoraDaInclusao;
	}

	public void setDataHoraDaInclusao(LocalDateTime dataHoraDaInclusao) {
		this.dataHoraDaInclusao = dataHoraDaInclusao;
	}

	public int compareTo(Transacao transacao) {
		return this.getDataDaTransacao().compareTo(transacao.dataDaTransacao);
	}

	public Object getId() {
		return id;
	}

	public boolean possuiVazio() {
		
		if(this.agenciaDeDestino == null || this.agenciaDeOrigem == null || this.bancoDeDestino.isEmpty() || this.bancoDeOrigem.isEmpty() || this.contaDeDestino.isEmpty() || this.contaDeOrigem.isEmpty() || this.dataDaTransacao == null || this.valor == null) {
			return true;
		}		
		return false;
	}

}
