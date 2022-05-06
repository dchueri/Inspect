package br.com.diegochueri.inspect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.diegochueri.inspect.model.Transacao;
import br.com.diegochueri.inspect.model.Users;

@Repository
public interface UsuarioRepository extends JpaRepository<Users, Long>{

	List<Users> findByNomeNot(String string);

    Users findByEmail(String name);

    List<Users> findByNomeNotAndEnabled(String string, boolean b);

}
