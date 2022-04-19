package br.com.diegochueri.inspect.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.diegochueri.inspect.model.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long>{

	List<Transacao> findByData(LocalDate data);
}
