package com.example.dev.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dev.model.IncMovies;
import com.example.dev.service.IIncMoviesService;

@Controller
@RequestMapping("/api")
public class IncMoviesController {

	private final IIncMoviesService incMoviesService;

	public IncMoviesController(IIncMoviesService incMoviesService) {
		this.incMoviesService = incMoviesService;
	}
	
	@GetMapping("/listIncMovies")
	public ResponseEntity<?> listMovies() {
	    List<IncMovies> movies = incMoviesService.findAll();

	    if (movies.isEmpty()) {
	    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: There's no new releases incoming in the database");    
	    } else {
	    	Map<String, Object> response = new HashMap<>();
	        response.put("movies", movies);
	        return ResponseEntity.ok(response);
	    }
	}
	
	@GetMapping("/listIncMoviesAsc")
	public ResponseEntity<?> listMoviesAsc() {
	    List<IncMovies> movies = incMoviesService.findAllAsc();

	    if (movies.isEmpty()) {
	    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: There's no new releases incoming in the database");    
	    } else {
	    	Map<String, Object> response = new HashMap<>();
	        response.put("movies", movies);
	        return ResponseEntity.ok(response);
	    }
	}
	
	@GetMapping("/listIncMoviesSortedByTitle")
    public ResponseEntity<Map<String, List<IncMovies>>> listIncMoviesSortedByTitle() {
        List<IncMovies> movies = incMoviesService.findAllSortedByTitle();
        Map<String, List<IncMovies>> response = new HashMap<>();
        response.put("movies", movies);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@DeleteMapping("/incMovieDelete/{IncMoviesId}")
	public ResponseEntity<?> deleteById(@PathVariable Integer IncMoviesId) {
	    incMoviesService.deleteById(IncMoviesId);
	        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Incoming Movie with id: " + IncMoviesId + " deleted");
	}
	
	@PostMapping("/incMovieCreate")
    public ResponseEntity<?> createMovie(@RequestBody IncMovies incMovies) {
		IncMovies savedMovie = incMoviesService.save(incMovies);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }
}
