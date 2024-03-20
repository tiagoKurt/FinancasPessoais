package com.dev.Financas.api.DTO;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
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
