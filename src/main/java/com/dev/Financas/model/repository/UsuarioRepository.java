package com.dev.Financas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.Financas.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
