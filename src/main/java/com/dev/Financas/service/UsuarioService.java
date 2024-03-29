package com.dev.Financas.service;

import java.util.Optional;

import com.dev.Financas.model.entity.Usuario;
import com.dev.Financas.model.repository.IUsuarioProjection;

public interface UsuarioService {

	Usuario autenticar(String email, String senha);

	Usuario salvarUsuario(Usuario usuario);

	void validarEmail(String email);

	Optional<Usuario> ObterPorId(Long id);

	Optional<Usuario> ObterPorEmail(String email);

	Optional<IUsuarioProjection> obterPorIdLimitado(Long id);
}
