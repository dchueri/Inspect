package br.com.diegochueri.inspect.service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import br.com.diegochueri.inspect.model.Transacao;
import br.com.diegochueri.inspect.repository.TransacaoRepository;

public class InspetorService {
    
    public List<Transacao> buscaTransacoesPeloMes (YearMonth mesEAno, TransacaoRepository transacaoRepository) {
        int mes = mesEAno.getMonthValue();
        int ano = mesEAno.getYear();
        List<Transacao> transacoesDoMes = transacaoRepository.findByMesDaTransacao(mes, ano);
        return transacoesDoMes;
    }

    public List<Transacao> buscaTransacoesPeloMesEConta (YearMonth mesEAno, TransacaoRepository transacaoRepository) {
        int mes = mesEAno.getMonthValue();
        int ano = mesEAno.getYear();
        List<Transacao> transacoesDoMes = transacaoRepository.findByMesDaTransacao(mes, ano);
        return transacoesDoMes;
    }

    public List<Transacao> verificaSeHaTransacoesSuspeitas(YearMonth mesEAno, TransacaoRepository transacaoRepository) {
        List<Transacao> transacoesDoMes = buscaTransacoesPeloMes(mesEAno, transacaoRepository);
        List<Transacao> transacoesSuspeitas = new ArrayList<Transacao>();
        transacoesDoMes.forEach(t -> {
            Double valor = Double.parseDouble(t.getValor());
            if (valor >= 100000) {
                transacoesSuspeitas.add(t);
            }
        });
        return transacoesSuspeitas;
    }
}
