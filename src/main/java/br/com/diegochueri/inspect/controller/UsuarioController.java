package br.com.diegochueri.inspect.controller;

import java.security.Principal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.diegochueri.inspect.model.Users;
import br.com.diegochueri.inspect.repository.UsuarioRepository;
import br.com.diegochueri.inspect.service.CadastroService;
import br.com.diegochueri.inspect.service.EditarCadastroService;
import br.com.diegochueri.inspect.service.EmailService;

@Controller
@RequestMapping("usuario")
public class UsuarioController {
	private static String senha;
	private static String email;
	private static String nome;

	public static void setSenha(String senhaRecebida) {
		senha = senhaRecebida;
	}

	public static void setEmail(String email) {
		UsuarioController.email = email;
	}

	public static void setNome(String nome) {
		UsuarioController.nome = nome;
	}

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("lista-de-usuarios")
	public String lista(Model model) {
		List<Users> usuarios = usuarioRepository.findByNomeNotAndEnabled("Admin", true);
		model.addAttribute("usuarios", usuarios);
		return "lista-de-usuarios";
	}

	@GetMapping("registro")
	public String cadastro() {
		return "registro";
	}

	@PostMapping("cria-usuario")
	public String criaUsuario(CadastroService cadastro) throws SQLIntegrityConstraintViolationException {
		Users usuario = cadastro.novo();
		EmailService emailService = new EmailService();
		usuarioRepository.save(usuario);
		emailService.enviaEmail(mailSender, senha, email, nome);
		return "redirect:/home";
	}

	@PostMapping("editar")
	public String editar(EditarCadastroService cadastro, @RequestParam("email") String email, Model model) {
		Users usuario = usuarioRepository.findByEmail(email);
		model.addAttribute("usuario", usuario);
		return "editar";
	}

	@PostMapping("editado")
	public String editado(EditarCadastroService cadastro, @RequestParam("email") String email) {
		Users usuario = cadastro.editar(email, usuarioRepository);
		usuarioRepository.save(usuario);
		return "redirect:/usuario/lista-de-usuarios";
	}

	@PostMapping("deleta-usuario/{email}")
	public String deletaUsuario(@PathVariable String email, Principal principal) {
		Users usuarioDeletado = usuarioRepository.findByEmail(email);
		if (principal.getName().equals(email)) {
			System.out.println("Mesmo usuario.");
		} else {
			usuarioDeletado.setEnabled(false);
			usuarioRepository.save(usuarioDeletado);
		}
		return "redirect:/usuario/lista-de-usuarios";
	}
}
