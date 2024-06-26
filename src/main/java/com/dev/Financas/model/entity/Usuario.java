package com.dev.Financas.model.entity;

import org.junit.Ignore;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "Usuario")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Usuario {

	@Id
	@Column(name = "id")
	@jakarta.persistence.GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "email")
	private String email;

	@JsonIgnore
	@Column(name = "senha")
	private String senha;

}
