package br.com.diegochueri.inspect.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import org.springframework.util.StringUtils;
import br.com.diegochueri.inspect.model.Transacao;
import br.com.diegochueri.inspect.repository.TransacaoRepository;

@Controller
public class TransacaoController {
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@GetMapping("/historico")
	public String historico(Model model){	
		List<Transacao> transacoes = transacaoRepository.findAll(Sort.by("dataDaTransacao"));
		model.addAttribute("transacoes", transacoes);
		return "historico";
	}
	
	@GetMapping("/formulario")
	public String upload(Model model){	
		List<Transacao> transacoes = transacaoRepository.findAll();
		model.addAttribute("transacoes", transacoes);
		return "home";
	}
	
	@PostMapping("/formulario")
    public String uploadFile(@RequestParam("arquivo") MultipartFile file, Model model) throws IOException, CsvValidationException, NumberFormatException{
		String upload = "D:\\Users\\diego\\eclipse-workspace\\inspect\\src\\main\\resources\\uploads\\";
		File arquivo = new File(file.getOriginalFilename());
		file.transferTo(new File(upload + arquivo));
		BigDecimal tamanhoDoArquivo = new BigDecimal(file.getSize()).divide(new BigDecimal("1000000"));
		String informacoesDoArquivo = ("Carregado com sucesso: "
				+ "Arquivo: " + file.getOriginalFilename() + " Tamanho: " + tamanhoDoArquivo + " Mb");
		CSVReader reader = new CSVReader(new FileReader(upload + arquivo));
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
					contador++;
					for (int i = 0; i < 7; i++) {
						if(record[i].isBlank()) {
							informacoesCompletas = false;
							linhasFaltandoDados.add(contador);
							System.out.print("Informação nr " + (i+1) + " está faltando na linha ");
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
			System.out.println(transacoes.get(0).getDataHoraDaInclusao());

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
		
		return "home";
    }
	
	
}
