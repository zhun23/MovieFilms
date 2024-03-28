package com.example.dev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

	@GetMapping("/index")
    public String mostrarCatalogoCompleto() {
        return "index";
    }
	
	@GetMapping("/catalogoCompletoList")
    public String mostrarCatalogoCompletoJS() {
        return "catalogoCompletoList";
    }
}

