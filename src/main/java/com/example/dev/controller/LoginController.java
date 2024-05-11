package com.example.dev.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final AuthenticationManager authenticationManager;
	
	public LoginController(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

    @PostMapping("/process-login")
    public String login(
    		@RequestParam String nickname,
    		@RequestParam String password
    ) {
    	Authentication authToCheck = new UsernamePasswordAuthenticationToken(nickname, password);
    	// Si la autenticacion falla tirará AuthenticationException
    	Authentication validAuth = this.authenticationManager.authenticate(authToCheck);
    	return "Login successful";
    }

}
