package br.com.diegochueri.inspect.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class Users implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Column(name = "username", unique = true)
	private String email;
	private String password;
	private Boolean enabled;
	@OneToMany(mappedBy = "id")
	private Set<Transacao> transacao;

	public Set<Transacao> getTransacao() {
		return transacao;
	}

	public void setTransacao(Set<Transacao> transacao) {
		this.transacao = transacao;
	}

	@ElementCollection
	@CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"))
	private final Set<Authority> authorities = new HashSet<>();

	public void addAuthority(Authority userAuthority) {
		authorities.add(userAuthority);
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		this.password = encoder.encode(password);
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Long getId() {
		return id;
	}

	public void setEnabled(int i) {
	}

}
