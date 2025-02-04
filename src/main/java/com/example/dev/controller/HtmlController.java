package com.example.dev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.example.dev.model.Movie;
import com.example.dev.model.UserCt;
import com.example.dev.service.IUserCtService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HtmlController {

	@Autowired
	private IUserCtService userService;

	@ModelAttribute
    public void setUser(HttpSession session, Model model) {
        Object o = session.getAttribute("SPRING_SECURITY_CONTEXT");
        if (o == null) {
            System.out.println("Security context is null");
            return;
        }
        SecurityContext context = (SecurityContext) o;
        Authentication auth = context.getAuthentication();
        UserCt user = (UserCt) auth.getPrincipal();
        UserCt retrievedUser = this.userService
	        .findByUserid( user.getUserid() )
	        .orElseThrow();
        model.addAttribute("username", retrievedUser.getNickname());
    }

	@GetMapping("/index")
    public String mostrarCatalogoCompleto() {
        return "index";
    }

	@GetMapping("")
    public String index() {
        return "index";
    }

	@GetMapping("/error")
    public String handleError() {
        return "error";
    }

	@GetMapping("/cart")
	public String cart(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
		if (username != null) {
        model.addAttribute("username", username);
    }
		return "cart";
	}

	@GetMapping("/movie")
	public String movie(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
		if (username != null) {
        model.addAttribute("username", username);
    }
		return "movie";
	}

	@GetMapping("/admin")
    public String admin(HttpSession session, Model model) {
        return "admin";
    }

	@GetMapping("/register")
	public String regUser() {
		return "register";
	}

	@GetMapping("/fullNewRelease")
	public String fullNewRelease(HttpSession session, Model model) {
	    String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }
	    return "fullNewRelease";
	}

	@GetMapping("/account")
	public String account(HttpSession session, Model model) {
	    String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }
	    return "account";
	}

	@GetMapping("/buy")
	public String buy(HttpSession session, Model model) {
	    String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }
	    return "buy";
	}

	@GetMapping("/modifyAddress")
	public String modifyAddress(HttpSession session, Model model) {
	    String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }
	    return "modifyAddress";
	}

	@GetMapping("/myOrders")
	public String myOrders(HttpSession session, Model model) {
	    String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }
	    return "myOrders";
	}

	@GetMapping("/addAddress")
	public String addAddress(HttpSession session, Model model) {
	    String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }
	    return "addAddress";
	}

	@GetMapping("/specMovie")
	public String specMovie() {
		return "specMovie";
	}

	@GetMapping("/fullUsers")
	public String fullUsers() {
		return "fullUsers";
	}

	@GetMapping("/addMovie")
	public String addMovie() {
		return "addMovie";
	}

	@GetMapping("/fullCatalogue")
    public String fullCatalogue() {
        return "fullCatalogue";
    }
	
	@GetMapping("/buyManagement")
    public String buyManagement() {
        return "buyManagement";
    }
	@GetMapping("/moviesStock")
    public String moviesStock() {
        return "moviesStock";
    }
	
	@GetMapping("/adminNewRelease")
    public String adminNewRelease() {
        return "adminNewRelease";
    }

	@GetMapping("favicon.ico")
    @ResponseBody
    public void dummyFavicon() {
    }

	@GetMapping("/sidebartest")
	public String sidebar() {
		return "sidebartest";
	}

	@GetMapping("/titleResults")
	public String titleResults() {
		return "titleResults";
	}

	@GetMapping("/directorResults")
	public String directorResults() {
		return "directorResults";
	}

	@GetMapping("/yearResults")
	public String yearResults() {
		return "yearResults";
	}

	@GetMapping("/newReleaseInc")
	public String newReleaseInc(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }
		return "newReleaseInc";
	}

	@PostMapping("/titleResults")
    public String getTitleResults(@RequestParam("titleQuery") String titleQuery, HttpSession session, Model model) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8090/api/catalogue/titleSearch?title=" + titleQuery;
        List<Movie> movieList = restTemplate.getForObject(url, List.class);

        String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }

        model.addAttribute("movies", movieList);
        model.addAttribute("titleQuery", titleQuery);
        return "titleResults";
    }

	@PostMapping("/directorResults")
    public String getDirectorResults(@RequestParam("directorQuery") String directorQuery, HttpSession session, Model model) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8090/api/catalogue/directorSearch?director=" + directorQuery;
        List<Movie> movieList = restTemplate.getForObject(url, List.class);

        String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }

        model.addAttribute("movies", movieList);
        model.addAttribute("directorQuery", directorQuery);
        return "directorResults";
    }

	@PostMapping("/yearResults")
    public String getYearResults(@RequestParam("yearQuery") String yearQuery, HttpSession session, Model model) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8090/api/catalogue/year/" + yearQuery;
        List<Movie> movieList = restTemplate.getForObject(url, List.class);

        String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }

        model.addAttribute("movies", movieList);
        model.addAttribute("yearQuery", yearQuery);
        return "yearResults";
    }

	@GetMapping("/adventures")
	public String getAdventuresMovies(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }
	    return "genre/adventures";
	}

	@GetMapping("/action")
	public String getActionMovies(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }
	    return "genre/action";
	}

	@GetMapping("/scienceFiction")
	public String getFictionMovies(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }
	    return "genre/scienceFiction";
	}

	@GetMapping("/fantasy")
	public String getFantasyMovies(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }
	    return "genre/fantasy";
	}

	@GetMapping("/crime")
	public String getCrimeMovies(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }
	    return "genre/crime";
	}

	@GetMapping("/comedy")
	public String getComedyMovies(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }
	    return "genre/comedy";
	}

	@GetMapping("/romance")
	public String getRomanceMovies(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }
	    return "genre/romance";
	}

	@GetMapping("/horror")
	public String getHorrorMovies(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }
	    return "genre/horror";
	}

	@GetMapping("/drama")
	public String getDramaMovies(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }
	    return "genre/drama";
	}

	@GetMapping("/musical")
	public String getMusicalMovies(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }
	    return "genre/musical";
	}

	@GetMapping("/thriller")
	public String getThrillerMovies(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }
	    return "genre/thriller";
	}

	@GetMapping("/animation")
	public String getAnimationMovies(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }
	    return "genre/animation";
	}

	@GetMapping("/kids")
	public String getKidsMovies(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }
	    return "genre/kids";
	}
	@GetMapping("/creditCard")
	public String creditCard(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
	    if (username != null) {
	        model.addAttribute("username", username);
	    }
	    return "creditCard";
	}
}

