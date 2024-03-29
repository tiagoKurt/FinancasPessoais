package com.dev.Financas.api.resource;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.Financas.api.DTO.UsuarioDTO;
import com.dev.Financas.exception.ErroAutenticacao;
import com.dev.Financas.model.entity.Lancamento;
import com.dev.Financas.model.entity.Usuario;
import com.dev.Financas.service.LancamentoService;
import com.dev.Financas.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioResource {

	private final UsuarioService service;
	private final LancamentoService lancamentoService;

	@PostMapping("/autenticar")
	public ResponseEntity autenticar(@RequestBody UsuarioDTO dto) {
		try {

			Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
			return ResponseEntity.ok(usuarioAutenticado);

		} catch (ErroAutenticacao erro) {
			Map<String, String> response = new HashMap<>();
			response.put("status", "Usuário ou senha não conferem");
			return ResponseEntity.badRequest().body(response);
		}
	}

	@PostMapping
	public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {

		Usuario usuario = Usuario.builder()
				.nome(dto.getNome())
				.email(dto.getEmail())
				.senha(dto.getSenha())
				.build();

		try {

			Usuario usuarioSalvo = service.salvarUsuario(usuario);
			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);

		} catch (Exception erro) {
			Map<String, String> response = new HashMap<>();
			response.put("status", "usuario ja cadastrado para o email informado");
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping("{id}/saldo")
	public ResponseEntity ObterSaldo(@PathVariable("id") Long id) {
		Optional<Usuario> usuario = service.ObterPorId(id);

		if (!usuario.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);
		return ResponseEntity.ok(saldo);
	}

	@GetMapping("{id}/receita")
	public ResponseEntity obterReceita(@PathVariable("id") Long id) {
		Optional<Usuario> usuario = service.ObterPorId(id);

		if (!usuario.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		BigDecimal receita = lancamentoService.obterReceitaPorUsuario(id);
		return ResponseEntity.ok(receita);
	}

	@GetMapping("{id}/despesa")
	public ResponseEntity obterDespesa(@PathVariable("id") Long id) {
		Optional<Usuario> usuario = service.ObterPorId(id);

		if (!usuario.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		BigDecimal despesa = lancamentoService.obterDespesaPorUsuario(id);
		return ResponseEntity.ok(despesa);
	}

}
