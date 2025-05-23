package br.edu.senaisp.colegio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.senaisp.colegio.model.dto.UsuarioEntradaDTO;
import br.edu.senaisp.colegio.service.LoginService;


@RestController
@RequestMapping("/api")
public class LoginController {
	
	@Autowired
	LoginService loginService; 
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public String login(@RequestBody UsuarioEntradaDTO dto) {
		System.err.println(dto.login() + dto.senha());
		return loginService.autenticar(dto.toUsuario(), 
									authenticationManager);  
		
	}

}
