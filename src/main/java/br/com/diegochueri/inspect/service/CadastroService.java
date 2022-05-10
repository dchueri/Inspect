package br.com.diegochueri.inspect.service;

import br.com.diegochueri.inspect.controller.UsuarioController;
import br.com.diegochueri.inspect.model.Authority;
import br.com.diegochueri.inspect.model.Users;
import br.com.diegochueri.inspect.repository.UsuarioRepository;

public class CadastroService {
	private String nome;
	private String email;
	private String senha;
	private Boolean enabled;

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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Users novo() {
		Users usuario = new Users();
		Authority authority = new Authority();
		String senha = EmailService.gerarSenha();
		UsuarioController.setSenha(senha);
		UsuarioController.setEmail(email);
		UsuarioController.setNome(nome);
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setEnabled(true);
		usuario.setPassword(senha);
		usuario.addAuthority(authority);
		return usuario;
	}

	public Users editar(String email, UsuarioRepository usuarioRepository) {
		Users usuario = usuarioRepository.findByEmail(email);
		usuario.setNome(nome);
		usuario.setPassword(senha);
		return usuario;
	}
}
