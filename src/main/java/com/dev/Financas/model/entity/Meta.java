package com.dev.Financas.model.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "metaDeGastos")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Meta{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
	private Long id;
    
    @Column(name ="metaGastos")
    private BigDecimal metaGastos;

    @JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
}