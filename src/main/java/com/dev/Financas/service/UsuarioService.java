package com.dev.Financas.service;

import java.util.Optional;

import com.dev.Financas.model.entity.Usuario;

public interface UsuarioService {
	
	Usuario autenticar (String email, String senha);
	
	Usuario salvarUsuario(Usuario usuario);
	
	void validarEmail(String email);
	
	Optional<Usuario> ObterPorId(Long id);
	
}
