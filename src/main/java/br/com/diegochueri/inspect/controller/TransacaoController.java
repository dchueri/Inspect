package br.com.diegochueri.inspect.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.exceptions.CsvValidationException;

import br.com.diegochueri.inspect.model.Transacao;
import br.com.diegochueri.inspect.repository.TransacaoRepository;
import br.com.diegochueri.inspect.service.TransacaoService;

@Controller
public class TransacaoController {
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@GetMapping("/importacoes")
	public String historico(Model model){	
		List<Transacao> transacoes = transacaoRepository.findAll();
		List<LocalDate> dataDaTransacao = transacaoRepository.findByDataDaTransacao();
		
		List<Transacao> objetosComDatasCorretas = new ArrayList<Transacao>();
		List<LocalDate> datasDiferentes = new ArrayList<LocalDate>();
		for (int i = 0; i < transacoes.size(); i++) {
			LocalDate data = dataDaTransacao.get(i);
			if (!datasDiferentes.contains(data)) {
				datasDiferentes.add(data);
				objetosComDatasCorretas.add(transacoes.get(i));
			}
		}
		Collections.sort(objetosComDatasCorretas, Collections.reverseOrder());
		model.addAttribute("objetosComDatasCorretas", objetosComDatasCorretas);
		return "importacoes";
	}
	
	@GetMapping("/home")
	public String upload(Model model){	
		List<Transacao> transacoes = transacaoRepository.findAll();
		model.addAttribute("transacoes", transacoes);
		return "home";
	}
	
	@PostMapping("/importarArquivo")
    public String uploadFile(@RequestParam("arquivo") MultipartFile file, Model model) throws IOException, CsvValidationException, NumberFormatException{
		TransacaoService.importarArquivo(file);
		String pastaDoUpload = "D:\\Users\\diego\\eclipse-workspace\\inspect\\src\\main\\resources\\uploads\\";
		File nomeDoArquivo = new File(file.getOriginalFilename());
		String path = (pastaDoUpload + nomeDoArquivo);
		String informacoesDoArquivo = TransacaoService.informacoesDoArquivo(file);
		TransacaoService.cadastraAsTransacoesDoArquivo(file, path, model, transacaoRepository, informacoesDoArquivo);
		
		return "home";
    }
	
	
}
