package com.dev.Financas.api.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
public class UsuarioDTO {
	private String email;
	private String nome;
	private String senha;
}
