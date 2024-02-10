package com.dev.Financas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.Financas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
