package br.com.diegochueri.inspect.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import br.com.diegochueri.inspect.model.Transacao;
import br.com.diegochueri.inspect.model.Users;
import br.com.diegochueri.inspect.repository.TransacaoRepository;

public class TransacaoService {
	public static MultipartFile importarArquivo(MultipartFile file) throws IllegalStateException, IOException {
		String pastaDoUpload = "D:\\Users\\diego\\eclipse-workspace\\inspect\\src\\main\\resources\\uploads\\";
		File nomeDoArquivo = new File(file.getOriginalFilename());

		file.transferTo(new File(pastaDoUpload + nomeDoArquivo));
		return file;
	}

	public static String informacoesDoArquivo(MultipartFile file) {
		BigDecimal tamanhoDoArquivo = new BigDecimal(file.getSize()).divide(new BigDecimal("1000000"));
		return ("Carregado com sucesso: "
				+ "Arquivo: " + file.getOriginalFilename() + " Tamanho: " + tamanhoDoArquivo + " Mb");

	}

	public static void cadastraAsTransacoesDoArquivo(MultipartFile file, String path, Model model,
			TransacaoRepository transacaoRepository, String informacoesDoArquivo, Users user)
			throws CsvValidationException, IOException {
		CSVReader reader = new CSVReader(new FileReader(path));
		List<Transacao> transacoes = new ArrayList<Transacao>();
		int contador = 0;
		List<Integer> linhasFaltandoDados = new ArrayList<Integer>();
		Boolean informacoesCompletas = true;

		try {
			String[] record = null;

			while ((record = reader.readNext()) != null) {
				informacoesCompletas = true;
				Transacao transacao = new Transacao();
				transacao.setBancoDeOrigem(record[0]);
				transacao.setAgenciaDeOrigem(record[1]);
				transacao.setContaDeOrigem(record[2]);
				transacao.setBancoDeDestino(record[3]);
				transacao.setAgenciaDeDestino(record[4]);
				transacao.setContaDeDestino(record[5]);
				transacao.setValor(record[6]);
				transacao.setDataDaTransacao(LocalDateTime.parse(record[7]));
				transacao.setDataHoraDaInclusao(LocalDateTime.now());
				transacao.setUser(user);
				contador++;
				for (int i = 0; i < 7; i++) {
					if (record[i].isBlank()) {
						informacoesCompletas = false;
						linhasFaltandoDados.add(contador);
						System.out.print("Informação nr " + (i + 1) + " está faltando na linha ");
					}
				}
				if (informacoesCompletas == false) {
					System.out.println(contador);
				} else {
					transacoes.add(transacao);
				}
			}
			if (linhasFaltandoDados.isEmpty() == false) {
				model.addAttribute("erroCampoVazio", Transacao.erroCampoVazio(linhasFaltandoDados));
			}
			LocalDate dataCorreta = transacoes.get(0).getDataDaTransacao();
			List<LocalDate> dataDaTransacao = transacaoRepository.findByDataDaTransacao();
			for (int i = 0; i < dataDaTransacao.size(); i++) {
				if (dataDaTransacao.get(i).isEqual(dataCorreta)) {
					throw new Error("O arquivo desse dia já foi adicionado");
				}
			}
			List<LocalDate> datasDiferentes = new ArrayList<LocalDate>();
			for (int i = 0; i < dataDaTransacao.size(); i++) {
				LocalDate data = dataDaTransacao.get(i);
				if (!datasDiferentes.contains(data)) {
					datasDiferentes.add(data);
				}
			}
			System.out.println(datasDiferentes + "" + dataDaTransacao);

			List<Integer> linhasComDatasDiferentes = new ArrayList<Integer>();
			int contadorDeLinhas = 0;
			for (int i = 0; i < transacoes.size(); i++) {
				contadorDeLinhas++;
				LocalDate dataComparada = transacoes.get(i).getDataDaTransacao();
				if (dataCorreta.equals(dataComparada) == false) {
					contadorDeLinhas++;
					linhasComDatasDiferentes.add(contadorDeLinhas);
					transacoes.remove(i);
				}
			}
			if (!linhasComDatasDiferentes.isEmpty()) {
				model.addAttribute("erroDatasDiferentes", Transacao.erroDatasDiferentes(linhasComDatasDiferentes));
			}

			transacoes.forEach(transacao -> transacaoRepository.save(transacao));
			model.addAttribute("informacoesDoArquivo", informacoesDoArquivo);
		} catch (ArrayIndexOutOfBoundsException e) {
			model.addAttribute("erroNoUpload", "O arquivo selecionado está vazio.");
		} catch (Error e) {
			model.addAttribute("erroNoUpload", "As transações da data deste arquivo já foram adicionadas.");
		}

		reader.close();
	}
}
