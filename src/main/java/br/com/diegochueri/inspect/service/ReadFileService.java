package br.com.diegochueri.inspect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import br.com.diegochueri.exception.ArquivoVazio;
import br.com.diegochueri.exception.NaoSuportado;
import br.com.diegochueri.inspect.model.Transacao;
import br.com.diegochueri.inspect.model.Users;
import br.com.diegochueri.inspect.repository.TransacaoRepository;

public class ReadFileService {

	public static Map Ready(MultipartFile file, String path, Model model, TransacaoRepository transacaoRepository,
			String informacoesDoArquivo, Users user) throws Exception {
		List<Transacao> lista = new ArrayList<>();
		List<Transacao> duplicadas = new ArrayList<>();

		if (file.isEmpty())
			throw new ArquivoVazio("O arquivo selecionado está vazio !");

		if (file.getContentType().equals("text/csv"))
			LeitorCSV.read(file, path, model, transacaoRepository, informacoesDoArquivo, user);

		else if (file.getContentType().equals("text/xml"))
			LeitorXML.read(file, path, model, transacaoRepository, informacoesDoArquivo, user);

		else
			throw new NaoSuportado("Tipo do arquivo não suportado.");

		List<Transacao> listaErroNull = Check.findErroNull(lista);

		List<Transacao> listaErroDate = Check.findErroByDate(lista);

		List<Transacao> novalista = endList(lista, listaErroNull, listaErroDate);

		Map map = Map.of("lista", novalista, "duplicidades", duplicadas, "erroNull", listaErroNull, "erroDate",
				listaErroDate);
		return map;
	}

	public static List<Transacao> endList(List<Transacao> lista, List<Transacao> ErroNull, List<Transacao> ErroDate) {
		lista.removeAll(ErroNull);
		lista.removeAll(ErroDate);
		return lista;
	}

}
