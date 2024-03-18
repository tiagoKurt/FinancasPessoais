package com.dev.Financas.api.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.Financas.api.DTO.LancamentoDTO;
import com.dev.Financas.api.DTO.UsuarioDTO;
import com.dev.Financas.exception.RegraNegocioException;
import com.dev.Financas.model.entity.Lancamento;
import com.dev.Financas.model.entity.Usuario;
import com.dev.Financas.model.enums.StatusLancamento;
import com.dev.Financas.model.enums.TipoLancamento;
import com.dev.Financas.model.repository.IUsuarioProjection;
import com.dev.Financas.service.LancamentoService;
import com.dev.Financas.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/lancamentos")
@RequiredArgsConstructor
public class LancamentoResource {

	private final LancamentoService service;
	private final UsuarioService usuarioService;

	@CrossOrigin(origins = "*")
	@GetMapping
	public ResponseEntity buscar(
			@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "mes", required = false) Integer mes,
			@RequestParam(value = "ano", required = false) Integer ano,
			@RequestParam("usuario") Long idUsuario) {

		Lancamento lancamentoFiltro = new Lancamento();
		lancamentoFiltro.setDescricao(descricao);
		lancamentoFiltro.setMes(mes);
		lancamentoFiltro.setAno(ano);

		Optional<Usuario> usuario = usuarioService.ObterPorId(idUsuario);

		if (!usuario.isPresent()) {
			return ResponseEntity.badRequest().body("Usuário não encontrado para o ID informado");
		} else {

			lancamentoFiltro.setUsuario(usuario.get());
		}

		List<Lancamento> lancamentos = service.buscar(lancamentoFiltro);
		return ResponseEntity.ok(lancamentos);
	}

	// @CrossOrigin(origins = "*")
	// @GetMapping("/tudo")
	// public ResponseEntity getTudo(@RequestParam("usuario") Long idUsuario) {

	// Optional<IUsuarioProjection> usuario =
	// usuarioService.obterPorIdLimitado(idUsuario);

	// if (!usuario.isPresent()) {
	// return ResponseEntity.badRequest().body("Usuário não encontrado para o ID
	// informado");
	// } else {

	// lancamentoFiltro.setUsuario(usuario.get());
	// }
	// list
	// return ResponseEntity.ok(lancaento);
	// }

	@CrossOrigin(origins = "*")
	@PostMapping
	public ResponseEntity salvar(@RequestBody LancamentoDTO dto) {

		try {

			Lancamento entidade = converter(dto);
			entidade = service.salvar(entidade);
			return new ResponseEntity(entidade, HttpStatus.CREATED);

		} catch (RegraNegocioException erro) {
			return ResponseEntity.badRequest().body(erro.getMessage());
		}
	}

	@CrossOrigin(origins = "*")
	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDTO dto) {

		return service.obterPorId(id).map(entity -> {
			try {

				Lancamento lancamento = converter(dto);
				lancamento.setId(entity.getId());
				service.atualizar(lancamento);
				return ResponseEntity.ok(lancamento);

			} catch (RegraNegocioException erro) {
				return ResponseEntity.badRequest().body(erro.getMessage());
			}

		}).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de dados!", HttpStatus.BAD_REQUEST));
	}

	@PutMapping("{id}/atualiza-status")
	public ResponseEntity atualizarStatus(@PathVariable("id") Long id, @RequestBody LancamentoDTO dto) {
		Map<String, String> response = new HashMap<>();
		Map<String, String> response2 = new HashMap<>();
		response.put("status", "Nao foi possivel atualizar o status do lancamento");
		response2.put("status", "Lancamento não encontrado na base de dados");

		return service.obterPorId(id).map(entity -> {
			StatusLancamento statusSelecionado = StatusLancamento.valueOf(dto.getStatus());
			if (statusSelecionado == null) {

				return ResponseEntity.badRequest().body(response);
			}

			try {
				entity.setStatus(statusSelecionado);
				service.atualizar(entity);
				return ResponseEntity.ok(entity);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());

			}
		}).orElseGet(() ->

		new ResponseEntity(response2, HttpStatus.BAD_REQUEST));
	}

	@CrossOrigin(origins = "*")
	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) {
		return service.obterPorId(id).map(entidade -> {
			service.deletar(entidade);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de dados!", HttpStatus.BAD_REQUEST));
	}

	private Lancamento converter(LancamentoDTO dto) {

		Lancamento lancamento = new Lancamento();
		lancamento.setId(dto.getId());
		lancamento.setDescricao(dto.getDescricao());
		lancamento.setAno(dto.getAno());
		lancamento.setMes(dto.getMes());
		lancamento.setValor(dto.getValor());

		Usuario usuario = usuarioService
				.ObterPorEmail(dto.getUsuario())
				.orElseThrow(() -> new RegraNegocioException("Usuário não encontrado para o EMAIL informado"));

		lancamento.setUsuario(usuario);
		lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));

		if (dto.getStatus() != null) {
			lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
		}

		if (dto.getTipo() != null) {
			lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
		}

		return lancamento;
	}

}
