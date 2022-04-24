package br.com.diegochueri.inspect.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import br.com.diegochueri.inspect.model.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long>{

	@Query ("SELECT t.dataDaTransacao FROM Transacao t")
    List<LocalDate> findByDataDaTransacao();
}