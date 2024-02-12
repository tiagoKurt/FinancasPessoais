package com.dev.Financas.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.Financas.exception.ErroAutenticacao;
import com.dev.Financas.exception.RegraNegocioException;
import com.dev.Financas.model.entity.Usuario;
import com.dev.Financas.model.repository.UsuarioRepository;
import com.dev.Financas.service.UsuarioService;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	
	private UsuarioRepository repository;
	
	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		
		Optional<Usuario> usuario = repository.findByEmail(email);
		
		if(!usuario.isPresent()) {
			
			throw new ErroAutenticacao("Usuário não encontrado para o email informado.");
			
		}else if(!usuario.get().getSenha().equals(senha)) {
			
			throw new ErroAutenticacao("Senha inválida");
		}
		
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
		
	}

	@Override
	public void validarEmail( String email) {
		boolean existe = repository.existsByEmail(email);
		
		if(existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este email!");
		}
	}

}
