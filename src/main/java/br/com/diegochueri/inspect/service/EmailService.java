package br.com.diegochueri.inspect.service;

import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailService {
	public static String gerarSenha() {
		Random random = new Random();
		int numero = random.nextInt(999999);
		String senha = String.format("%06d", numero);
		return senha;
	}

	public String enviaEmail(JavaMailSender mailSender, String senha, String email, String nome) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setText("Olá " + nome + ", a sua senha do Inspect é " + senha);
		message.setSubject("Sua senha Inspect");
		message.setTo(email);
		message.setFrom("no-reply@inspect.com");
		try {
			mailSender.send(message);
			return "Email enviado com sucesso!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao enviar email.";
		}
	}
}
