package br.com.diegochueri.inspect.service;

import br.com.diegochueri.inspect.model.Users;
import br.com.diegochueri.inspect.repository.UsuarioRepository;

public class EditarCadastroService {

    private String nome;
    private String senha;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Users editar(String email, UsuarioRepository usuarioRepository){
        Users usuario = usuarioRepository.findByEmail(email);
        usuario.setNome(nome);
        usuario.setPassword(senha);
        return usuario;
    }

}
