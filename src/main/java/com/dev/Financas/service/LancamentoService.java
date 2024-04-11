package com.dev.Financas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.dev.Financas.api.DTO.LancamentoDTO;
import com.dev.Financas.model.entity.Lancamento;
import com.dev.Financas.model.enums.StatusLancamento;

public interface LancamentoService {

	Lancamento salvar(Lancamento lancamento);

	Lancamento atualizar(Lancamento lancamento);

	void deletar(Lancamento lancamento);

	List<Lancamento> buscar(Lancamento lancamentoFiltro);

	void atulizarStatus(Lancamento lancamento, StatusLancamento status);

	void validar(Lancamento lancamento);

	Optional<Lancamento> obterPorId(Long id);

	BigDecimal obterSaldoPorUsuario(Long id);

	BigDecimal obterReceitaPorUsuario(Long id);

	BigDecimal obterDespesaPorUsuario(Long id);

	BigDecimal obterSaldoPorUsuarioEmail(String email);

	BigDecimal obterReceitaPorUsuarioEmail(String email);

	BigDecimal obterDespesaPorUsuarioEmail(String email);

	List<Lancamento> buscarLancamentosPorUsuario(Long idUsuario);

	List<Lancamento> buscarLancamentosPorUsuariEmail(String emailUsuario);
}
