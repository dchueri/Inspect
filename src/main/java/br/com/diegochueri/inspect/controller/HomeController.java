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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
public class HomeController {
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@GetMapping("/formulario")
	public String home(Model model){	
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
		model.addAttribute("informacoesDoArquivo", informacoesDoArquivo);
		
		CSVReader reader = new CSVReader(new FileReader(upload + arquivo));
		List<Transacao> transacoes = new ArrayList<Transacao>();

		// read line by line
		String[] record = null;

		while ((record = reader.readNext()) != null) {
			Transacao transacao = new Transacao();
			transacao.setBancoDeOrigem(record[0]);
			transacao.setAgenciaDeOrigem(record[1]);
			transacao.setContaDeOrigem(record[2]);
			transacao.setBancoDeDestino(record[3]);
			transacao.setAgenciaDeDestino(record[4]);
			transacao.setContaDeDestino(record[5]);
			transacao.setValor(Double.parseDouble(record[6]));
			transacao.setDataHora(LocalDateTime.parse(record[7]));
			LocalDate localDate = transacao.getDataHora().toLocalDate();
			System.out.println(localDate);
			transacoes.add(transacao);
			transacaoRepository.save(transacao);
		}
		
		transacoes.forEach(transacao -> System.out.println(transacao));
	
		reader.close();
		return "home";
    }
}
