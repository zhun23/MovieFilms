package com.example.dev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dev.service.ICatalogueService;
import com.example.dev.model.Movie;

@RestController
public class CatalogueController {

	@Autowired
	private ICatalogueService catalogueService;
	
	@GetMapping("/")
	public ResponseEntity<?> listMovies() {
		List<Movie> movies = catalogueService.findAll();
		
		if (!movies.isEmpty()) {
			return ResponseEntity.ok(movies);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: There's no movies in the database");
		}
		
		
	}
}
