package com.example.dev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dev.model.CatalogueParameters;
import com.example.dev.model.Movie;
import com.example.dev.service.CatalogueService;

@RestController
@RequestMapping("/api/catalogue")
public class CatalogueRestController {

	@Autowired
	private CatalogueService catalogueService;

//	@GetMapping("/titleSearch")
//	public ResponseEntity<List<Movie>> searchWithParams(CatalogueParameters params) {
//		List<Movie> movieList = this.catalogueService.getWithParameters(params);
//		return ResponseEntity.ok(movieList);
//	}

	@GetMapping("/titleSearch")
    public ResponseEntity<?> searchWithParams(CatalogueParameters params) {
        if (params.getTitle() == null || params.getTitle().length() < 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El parámetro de búsqueda debe tener al menos 3 caracteres.");
        }

        List<Movie> movieList = catalogueService.getWithParameters(params);
        return ResponseEntity.ok(movieList);
    }

	@GetMapping("/directorSearch")
    public ResponseEntity<?> getWithDirectorParameters(CatalogueParameters params) {
        if (params.getDirector() == null || params.getDirector().length() < 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El parámetro de búsqueda debe tener al menos 3 caracteres.");
        }

        List<Movie> movieList = catalogueService.findDirectorWithParameters(params);
        return ResponseEntity.ok(movieList);
    }

	@GetMapping("/year/{year}")
    public ResponseEntity<?> findMoviesByYear(@PathVariable String year) {
        List<Movie> movies = catalogueService.findMovieByReleaseDate(year);

        if (!movies.isEmpty()) {
            return ResponseEntity.ok(movies);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: There are no movies from the entered year");
        }
    }
}
