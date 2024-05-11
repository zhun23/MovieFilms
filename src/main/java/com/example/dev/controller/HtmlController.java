package com.example.dev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HtmlController {
	
	//@GetMapping("/")
    //public String handleHome() {
    //    return "index";
    //}

	@GetMapping("/index")
    public String mostrarCatalogoCompleto() {
        return "index";
    }
	
	//@GetMapping ("/signup")
	//public String signup() {
	//	return "signup";
	//}

	@GetMapping("/error")
    public String handleError() {
        return "error";
    }
	
	@GetMapping("/register")
	public String regUser() {
		return "register";
	}
	
	@GetMapping("/fullNewRelease")
	public String fullNewRelease() {
		return "fullNewRelease";
	}
	
	@GetMapping("/specMovie")
	public String specMovie() {
		return "specMovie";
	}
	
	@GetMapping("/fullUsers")
	public String fullUsers() {
		return "fullUsers";
	}
	
	@GetMapping("/globalCreditHistory")
	public String globalCreditHistory() {
		return "globalCreditHistory";
	}
	
	@GetMapping("/addMovie")
	public String addMovie() {
		return "addMovie";
	}
	
	@GetMapping("/addCreditUser")
	public String addCreditUser() {
		return "addCreditUser";
	}
		
	@GetMapping("/fullCatalogue")
    public String fullCatalogue() {
        return "fullCatalogue";
    }
	
	@GetMapping("favicon.ico")
    @ResponseBody
    public void dummyFavicon() {
    }
}

