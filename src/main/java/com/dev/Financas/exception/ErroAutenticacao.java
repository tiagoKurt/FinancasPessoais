package com.dev.Financas.exception;

import org.springframework.core.NestedRuntimeException;

public class ErroAutenticacao extends NestedRuntimeException{
	
	public ErroAutenticacao(String msg) {
		super (msg);
	}
	
}
