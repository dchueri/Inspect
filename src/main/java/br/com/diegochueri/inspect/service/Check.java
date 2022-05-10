package br.com.diegochueri.inspect.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import br.com.diegochueri.inspect.model.Transacao;
import br.com.diegochueri.inspect.model.Users;

public class Check {
    public static List<Transacao> findErroByDate(List<Transacao> lista) {
        List<Transacao> listaDateErro = new ArrayList<>();
        LocalDate primeiraData = lista.get(0).getDataDaTransacao();

        for (Transacao transacao : lista) {

            if (transacao.getDataDaTransacao().getDayOfMonth() != primeiraData.getDayOfMonth()
                    || transacao.getDataDaTransacao().getMonth() != primeiraData.getMonth()
                    || transacao.getDataDaTransacao().getYear() != primeiraData.getYear()) {
                listaDateErro.add(transacao);
            }
        }
        return listaDateErro;
    }

    public static List<Transacao> findErroNull(List<Transacao> lista) {
        List<Transacao> listaErroNull = new ArrayList<>();

        for (Transacao transacao : lista) {
            if (transacao.possuiVazio()) {
                listaErroNull.add(transacao);
            }
        }
        return listaErroNull;
    }

    public static ModelAndView HasErro(Users UsernameExist, Users EmailExist) {
        ModelAndView mv = new ModelAndView("redirect:/login");
        if (UsernameExist != null || EmailExist != null) {
            mv = new ModelAndView("Autenticacao/cadastro.html");

            if (UsernameExist != null) {
                mv.addObject("erroUsername", true);
                System.out.println("erro de username");
            }
            if (EmailExist != null) {
                mv.addObject("erroEmail", true);
                System.out.println("erro de email");
            }
            return mv;
        }

        return mv;
    }
}
