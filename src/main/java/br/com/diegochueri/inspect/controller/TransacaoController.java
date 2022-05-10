package br.com.diegochueri.inspect.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.opencsv.exceptions.CsvValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.diegochueri.inspect.model.AgenciaSuspeita;
import br.com.diegochueri.inspect.model.Conta;
import br.com.diegochueri.inspect.model.Transacao;
import br.com.diegochueri.inspect.model.Users;
import br.com.diegochueri.inspect.repository.TransacaoRepository;
import br.com.diegochueri.inspect.repository.UsuarioRepository;
import br.com.diegochueri.inspect.service.InspetorService;
import br.com.diegochueri.inspect.service.ReadFileService;
import br.com.diegochueri.inspect.service.TransacaoService;

@Controller
public class TransacaoController {

	@Autowired
	private TransacaoRepository transacaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("/importacoes")
	public String historico(Model model) {
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
	public String upload(Model model) {
		List<Transacao> transacoes = transacaoRepository.findAll();
		model.addAttribute("transacoes", transacoes);
		return "home";
	}

	@PostMapping("/importarArquivo")
	public String uploadFile(@RequestParam("arquivo") MultipartFile file, Model model, Principal principal)
			throws IOException, CsvValidationException, NumberFormatException {
		TransacaoService.importarArquivo(file);
		String pastaDoUpload = "D:\\Users\\diego\\eclipse-workspace\\inspect\\src\\main\\resources\\uploads\\";
		File nomeDoArquivo = new File(file.getOriginalFilename());
		String path = (pastaDoUpload + nomeDoArquivo);
		String informacoesDoArquivo = TransacaoService.informacoesDoArquivo(file);
		Users user = usuarioRepository.findByEmail(principal.getName());
		/* TransacaoService.cadastraAsTransacoesDoArquivo(file, path, model, transacaoRepository, informacoesDoArquivo,
				user); */
				ModelAndView mv = new ModelAndView("home.html");
				Map<String, List<Transacao>> mapa = null;
				System.out.println(nomeDoArquivo);
				try {
					mapa = ReadFileService.Ready(file, path, model, transacaoRepository, informacoesDoArquivo, user);
					System.out.println(nomeDoArquivo);
				}catch(Exception e) {
					
					mv.addObject("isInvalid", true);
					mv.addObject("erro", e.getMessage());
					return "home";
				}
		return "home";
	}

	@PostMapping("/importacoes/detalhar")
	public String detalhar(@RequestParam("data") String data, Model model) {
		LocalDate data2 = LocalDate.parse(data);
		List<Transacao> transacao = transacaoRepository.findByDataDaTransacao(data2);
		model.addAttribute("importacoes", transacao);
		ArrayList<Transacao> detalhes = new ArrayList<>();
		for (Transacao transacao2 : transacao) {
			if (transacao2.getId() != null) {
				detalhes.add(transacao2);
				model.addAttribute("detalhes", detalhes);
				break;
			}
		}

		return "detalhar";
	}

	@GetMapping("/analise")
	public String transacoes(Model model) {
		return "analise";
	}

	@PostMapping("/analisar")
	public String analisar(@RequestParam("mesEAno") String dataDoInput, Model model) {
		InspetorService inspetor = new InspetorService();
		YearMonth mesEAno = YearMonth.parse(dataDoInput);
		List<Transacao> transacoes = inspetor.verificaSeHaTransacoesSuspeitas(mesEAno, transacaoRepository);
		List<Conta> contasSuspeitas = InspetorService.buscaContasSuspeitas(transacaoRepository, mesEAno.getMonthValue(),
				mesEAno.getYear(), Double.valueOf("1000000.00"));
		List<AgenciaSuspeita> agenciasSuspeitas = InspetorService.buscaAgenciasSuspeitas(transacaoRepository,
				mesEAno.getMonthValue(), mesEAno.getYear(), Double.valueOf("1000000000.00"));
		model.addAttribute("transacoes", transacoes);
		model.addAttribute("contasSuspeitas", contasSuspeitas);
		model.addAttribute("agenciasSuspeitas", agenciasSuspeitas);
		ArrayList<Transacao> detalhes = new ArrayList<>();
		for (Transacao transacao : transacoes) {
			if (transacao.getId() != null) {
				detalhes.add(transacao);
				model.addAttribute("detalhes", detalhes);
				break;
			}
		}
		return "analise";
	}
}
