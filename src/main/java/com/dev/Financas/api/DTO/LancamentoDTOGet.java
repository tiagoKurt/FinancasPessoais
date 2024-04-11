package com.dev.Financas.api.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoDTOGet {
  private Long id;
  private String descricao;
  private Integer mes;
  private Integer ano;
  private BigDecimal valor;
  private String Usuario;
  private String tipo;
  private String status;
}
