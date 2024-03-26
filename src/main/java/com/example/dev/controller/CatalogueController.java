package com.example.dev.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.dev.service.ICatalogueService;
import com.example.dev.utilities.ConvGenre;
import com.example.dev.model.Genre;
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
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> findById(@PathVariable int id){
		Optional<Movie> movies = catalogueService.findById(id);
				
		if (!movies.isEmpty()) {
	        return ResponseEntity.ok(movies);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No movie matching with that ID were found in the database");
	    }
	}
	
	@GetMapping("/title/{title}")
	public ResponseEntity<?> findMovieByTitle(@PathVariable String title) {
		List<Movie> movies = catalogueService.findMovieByTitle(title);

	    if (!movies.isEmpty()) {
	        return ResponseEntity.ok(movies);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No movies matching that title were found in the database");
	    }
	}
	
	@GetMapping("/genre/{genre}")
	public ResponseEntity<?> findGameByGenre(@PathVariable String genre){
		
		ConvGenre convGenre = new ConvGenre();
	    Genre genreEnum = convGenre.convertirCadenaAGenre(genre);

	    if (genreEnum != null) {
	        List<Movie> movies = catalogueService.findMovieByGenre(genreEnum);
	        if (!movies.isEmpty()) {
	            return ResponseEntity.ok(movies);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR: There is no movie with the entered "+genreEnum.getGenre());
	        }
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR: Invalid Genre provided");
	    }
	}
	
	@GetMapping("/director/{director}")
	public ResponseEntity<?> findMovieByDirector(@PathVariable String director) {
	    List<Movie> movies = catalogueService.findMovieByDirector(director);

	    if (!movies.isEmpty()) {
	        return ResponseEntity.ok(movies);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No director matching that name was found in the database");
	    }
	}
	
	@GetMapping("/newRelease/{newrelease}")
	public ResponseEntity<?> findMovieByNewRelease(@PathVariable boolean newrelease) {
		List<Movie> movies = catalogueService.findMovieByNewRelease(newrelease);

		if (!movies.isEmpty()) {
	        return ResponseEntity.ok(movies);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Actually there are no new releases on MovieFilms");
	    }
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

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable int id) {
		Optional<Movie> movies = catalogueService.findById(id);
		
		if (!movies.isEmpty()) {
			catalogueService.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Movie with id: "+ id + " deleted");
		}else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Can't delete, there are no movies with that id");
		}
	}
	
	@DeleteMapping("/deleteTitle/{title}")
	public ResponseEntity<?> deleteMovieByTitle(@PathVariable String title) {
		List<Movie> movies = catalogueService.findMovieByTitle(title);
		
		if (!movies.isEmpty()) {
			catalogueService.deleteMovieByTitle(title);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Movie with title: "+ title + " deleted");
		}else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Can't delete, there are no movies with that title");
		}
	}
}
