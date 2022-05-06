package br.com.diegochueri.inspect.repository;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.diegochueri.inspect.model.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long>{

	@Query ("SELECT t.dataDaTransacao FROM Transacao t")
    List<LocalDate> findByDataDaTransacao();

    List<Transacao> findByDataDaTransacao(LocalDate data);

    @Query(nativeQuery = true, value = "SELECT id FROM Transacao t WHERE MONTH(data_da_transacao) = '01' AND YEAR(data_da_transacao) = '2022'")
    List<Transacao> findByMesEAnoDaTransacao(@Param("mesDaData") int mesDaData,@Param("anoDaData") int anoDaData);

    @Query(nativeQuery = true, value = "select * from transacao where extract(month from data_da_transacao) = :mesDaData AND extract(year from data_da_transacao) = :anoDaData")
    List<Transacao> findByMesDaTransacao(@Param("mesDaData") int mesDaData, @Param("anoDaData") int anoDaData);
}