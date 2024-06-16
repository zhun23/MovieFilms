package com.example.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	@PostMapping("/login")
    public RedirectView login(String nickname, String password, HttpSession session) {
    	Authentication authToCheck = new UsernamePasswordAuthenticationToken(nickname, password);
    	Authentication validAuth = this.authenticationManager.authenticate(authToCheck);

//    	session.setAttribute("authentication", validAuth);
    	String savedRequest = session.getAttribute("SPRING_SECURITY_SAVED_REQUEST").toString();
//        return new RedirectView(savedRequest);
    	return new RedirectView("index");

    }

	@PostMapping("/logout")
	public RedirectView logout(HttpServletRequest request) {
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }
	    return new RedirectView("/index");
	}


}
