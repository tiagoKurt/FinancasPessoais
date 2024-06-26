package com.dev.Financas.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.Financas.exception.ErroAutenticacao;
import com.dev.Financas.exception.RegraNegocioException;
import com.dev.Financas.model.entity.Usuario;
import com.dev.Financas.model.repository.IUsuarioProjection;
import com.dev.Financas.model.repository.UsuarioRepository;
import com.dev.Financas.service.UsuarioService;
import java.util.Base64;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioRepository repository;

	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {

		Optional<Usuario> usuario = repository.findByEmail(email);
		String senhaEncrypting = Base64.getEncoder().encodeToString(senha.getBytes());

		if (!usuario.isPresent()) {

			throw new ErroAutenticacao("Usuário ou senha não conferem.");

		} else if (!usuario.get().getSenha().equals(senhaEncrypting)) {

			throw new ErroAutenticacao("Usuário ou senha não conferem.");
		}

		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {

		validarEmail(usuario.getEmail());
		String senha = Base64.getEncoder().encodeToString(usuario.getSenha().getBytes());
		usuario.setSenha(senha);
		return repository.save(usuario);

	}

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);

		if (existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este email!");
		}
	}

	@Override
	public Optional<Usuario> ObterPorId(Long id) {

		return repository.findById(id);
	}

	@Override
	public Optional<Usuario> ObterPorEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public Optional<IUsuarioProjection> obterPorIdLimitado(Long id) {
		return repository.findProjectedById(id);
	}

}
