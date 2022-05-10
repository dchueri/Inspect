package br.com.diegochueri.inspect.service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.diegochueri.inspect.model.AgenciaSuspeita;
import br.com.diegochueri.inspect.model.Conta;
import br.com.diegochueri.inspect.model.Transacao;
import br.com.diegochueri.inspect.repository.TransacaoRepository;

public class InspetorService {

    public List<Transacao> buscaTransacoesPeloMes(YearMonth mesEAno, TransacaoRepository transacaoRepository) {
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

    public static List<Conta> buscaContasSuspeitas(TransacaoRepository transacaoRepository, int mes, int ano,
            double limite) {
        List<Conta> contasSuspeitas = new ArrayList<>();

        transacaoRepository.findContaDeOrigemPeloMesEAno(mes, ano).forEach(o -> {
            Scanner read = new Scanner(o);

            String fields[] = read.nextLine().split(",");
            if (Double.parseDouble(fields[3]) >= limite) {
                contasSuspeitas.add(new Conta(fields[0], Integer.parseInt(fields[1]), fields[2],
                        Double.valueOf(fields[3]), "Saída"));
            }
            read.close();
        });

        transacaoRepository.findContaDeDestinoPeloMesEAno(mes, ano).forEach(d -> {
            Scanner read = new Scanner(d);

            String fields[] = read.nextLine().split(",");
            if (Double.parseDouble(fields[3]) >= limite) {
                contasSuspeitas.add(new Conta(fields[0], Integer.parseInt(fields[1]), fields[2],
                        Double.valueOf(fields[3]), "Entrada"));
            }
            read.close();
        });

        return contasSuspeitas;
    }

    public static List<AgenciaSuspeita> buscaAgenciasSuspeitas(TransacaoRepository transacaoRepository, int mes,
            int ano, double limite) {
        List<AgenciaSuspeita> agenciasSuspeitas = new ArrayList<>();

        transacaoRepository.findAgenciaDeOrigemPeloMesEAno(mes, ano).forEach(o -> {
            Scanner read = new Scanner(o);

            String fields[] = read.nextLine().split(",");
            if (Double.parseDouble(fields[2]) >= limite) {
                agenciasSuspeitas.add(new AgenciaSuspeita(fields[0], Integer.parseInt(fields[1]),
                        Double.valueOf(fields[2]), "saída"));
            }
            read.close();
        });

        transacaoRepository.findAgenciaDeDestinoPeloMesEAno(mes, ano).forEach(d -> {
            Scanner read = new Scanner(d);

            String fields[] = read.nextLine().split(",");
            if (Double.parseDouble(fields[2]) >= limite) {
                agenciasSuspeitas.add(new AgenciaSuspeita(fields[0], Integer.parseInt(fields[1]),
                        Double.valueOf(fields[2]), "entrada"));
            }
            read.close();
        });

        return agenciasSuspeitas;
    }
}
