package br.com.diegochueri.inspect.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.diegochueri.inspect.model.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query("SELECT t.dataDaTransacao FROM Transacao t")
    List<LocalDate> findByDataDaTransacao();

    List<Transacao> findByDataDaTransacao(LocalDate data);

    @Query(nativeQuery = true, value = "SELECT id FROM Transacao t WHERE MONTH(data_da_transacao) = 'mesDaData' AND YEAR(data_da_transacao) = 'anoDaData'")
    List<Transacao> findByMesEAnoDaTransacao(@Param("mesDaData") int mesDaData, @Param("anoDaData") int anoDaData);

    @Query(nativeQuery = true, value = "select * from transacao where extract(month from data_da_transacao) = :mesDaData AND extract(year from data_da_transacao) = :anoDaData")
    List<Transacao> findByMesDaTransacao(@Param("mesDaData") int mesDaData, @Param("anoDaData") int anoDaData);

    @Query("SELECT t.bancoDeOrigem, t.agenciaDeOrigem, t.contaDeOrigem, SUM(t.valor) FROM Transacao t WHERE MONTH(t.dataDaTransacao) = :mes AND YEAR(t.dataDaTransacao) = :ano GROUP BY t.contaDeOrigem")
    List<String> findContaDeOrigemPeloMesEAno(int mes, int ano);

    @Query("SELECT t.bancoDeDestino, t.agenciaDeDestino, t.contaDeDestino, SUM(t.valor) FROM Transacao t WHERE MONTH(t.dataDaTransacao) = :mes AND YEAR(t.dataDaTransacao) = :ano GROUP BY t.contaDeDestino")
    List<String> findContaDeDestinoPeloMesEAno(int mes, int ano);

    @Query("SELECT t.bancoDeOrigem, t.agenciaDeOrigem, SUM(t.valor) FROM Transacao t WHERE MONTH(t.dataDaTransacao) = :mes AND YEAR(t.dataDaTransacao) = :ano GROUP BY t.agenciaDeOrigem")
    List<String> findAgenciaDeOrigemPeloMesEAno(int mes, int ano);

    @Query("SELECT t.bancoDeDestino, t.agenciaDeDestino, SUM(t.valor) FROM Transacao t WHERE MONTH(t.dataDaTransacao) = :mes AND YEAR(t.dataDaTransacao) = :ano GROUP BY t.agenciaDeDestino")
    List<String> findAgenciaDeDestinoPeloMesEAno(int mes, int ano);
}