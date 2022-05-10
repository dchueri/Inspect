package br.com.diegochueri.inspect.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Convert;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.expression.ParseException;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.com.diegochueri.exception.InformacaoFaltando;
import br.com.diegochueri.inspect.model.Transacao;
import br.com.diegochueri.inspect.model.Users;
import br.com.diegochueri.inspect.repository.TransacaoRepository;

public class LeitorXML {
    public static void read(MultipartFile file, String path, Model model, TransacaoRepository transacaoRepository,
            String informacoesDoArquivo, Users user) throws Exception {

        Document document;
        List<Transacao> transacoes = new ArrayList<Transacao>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(path);

            hasException(document);

            NodeList nodeListTransacoes = document.getElementsByTagName("transacoes");

            NodeList nodeList = document.getElementsByTagName("transacao");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;

                    NodeList origemList = elem.getElementsByTagName("origem");

                    if (origemList.getLength() == 0)
                        throw new InformacaoFaltando("Any element don't have tag <origem>");

                    Node origemNode = origemList.item(0);
                    Element oElem = (Element) origemNode;

                    if (oElem.getElementsByTagName("banco").getLength() == 0)
                        throw new InformacaoFaltando("Inside of element <origem> in any local, don't have tag<banco>");

                    if (oElem.getElementsByTagName("agencia").getLength() == 0)
                        throw new InformacaoFaltando(
                                "Inside of element <origem> in any local, don't have tag<agencia>");

                    if (oElem.getElementsByTagName("conta").getLength() == 0)
                        throw new InformacaoFaltando("Inside of element <origem> in any local, don't have tag<conta>");

                    String bancoDeOrigem = oElem.getElementsByTagName("banco").item(0).getTextContent();
                    String agenciaDeOrigem = oElem.getElementsByTagName("agencia").item(0).getTextContent();
                    String contaDeOrigem = oElem.getElementsByTagName("conta").item(0).getTextContent();

                    System.out.println(bancoDeOrigem);
                    NodeList destinoList = elem.getElementsByTagName("destino");

                    if (destinoList.getLength() == 0)
                        throw new InformacaoFaltando("Any element don't have tag <destino>");

                    Node destinoNode = destinoList.item(0);
                    Element dElem = (Element) destinoNode;

                    if (dElem.getElementsByTagName("banco").getLength() == 0)
                        throw new InformacaoFaltando("Inside of element <destino> in any local, don't have tag<banco>");

                    if (dElem.getElementsByTagName("agencia").getLength() == 0)
                        throw new InformacaoFaltando(
                                "Inside of element <destino> in any local, don't have tag<agencia>");

                    if (dElem.getElementsByTagName("conta").getLength() == 0)
                        throw new InformacaoFaltando("Inside of element <destino> in any local, don't have tag<conta>");

                    String bancoDeDestino = dElem.getElementsByTagName("banco").item(0).getTextContent();
                    String agenciaDeDestino = dElem.getElementsByTagName("agencia").item(0).getTextContent();
                    String contaDeDestino = dElem.getElementsByTagName("conta").item(0).getTextContent();

                    if (elem.getElementsByTagName("valor").getLength() == 0)
                        throw new InformacaoFaltando("In any tag transacao we don't find tag <valor> ");

                    if (elem.getElementsByTagName("data").getLength() == 0)
                        throw new InformacaoFaltando("In any tag transacao we don't find tag <data> ");

                    String valor = elem.getElementsByTagName("valor").item(0).getTextContent();
                    String dataDaTransacao = elem.getElementsByTagName("data").item(0).getTextContent();

                    if (dataDaTransacao.isEmpty())
                        throw new InformacaoFaltando(
                                "More tags we identified that haven't data, nothing process identity without data !");

                    Transacao transacao = new Transacao(bancoDeOrigem, agenciaDeOrigem, contaDeOrigem, bancoDeDestino,
                            agenciaDeDestino, contaDeDestino, valor, dataDaTransacao, user);
                    transacoes.add(transacao);
                }
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
        } catch (ParseException e) {
            throw new Exception("any transaction's date can't be empty !");
        }

        catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    private static void hasException(Document document) throws InformacaoFaltando {
        if (document.getElementsByTagName("transacoes").getLength() == 0)
            throw new InformacaoFaltando("We see at this file haven't content a important tag called 'transacoes' !");

        if (!(document.getElementsByTagName("transacao").getLength() > 0))
            throw new InformacaoFaltando("we identify that your file is content empty, as: <transacao>");
    }
}
