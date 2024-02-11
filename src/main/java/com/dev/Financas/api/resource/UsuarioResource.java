package com.dev.Financas.api.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioResource {
	
	@GetMapping("/")
	public String helloWorld() {
		return "hellow world!";
	}
	
}
