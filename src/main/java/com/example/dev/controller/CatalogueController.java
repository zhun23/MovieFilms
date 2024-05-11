package com.example.dev.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.dev.service.ICatalogueService;
import com.example.dev.utilities.ConvGenre;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;

import com.example.dev.model.Genre;
import com.example.dev.model.Movie;

@Controller
//@RequestMapping("/catalogue")
public class CatalogueController {
	
	@Autowired
	private ICatalogueService catalogueService;
	
	@GetMapping("/list")
	public ResponseEntity<?> listMovies(@PageableDefault(size = 24) Pageable pageable) {
	    Page<Movie> movies = catalogueService.findAll(pageable);
	    
	    if (movies.hasContent()) {
	        Map<String, Object> response = new HashMap<>();
	        response.put("movies", movies.getContent());
	        response.put("currentPage", movies.getNumber());
	        response.put("totalItems", movies.getTotalElements());
	        response.put("totalPages", movies.getTotalPages());
	        
	        return ResponseEntity.ok(response);
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
	@PermitAll
	public ResponseEntity<?> findMovieByNewRelease(@PathVariable boolean newrelease) {
		List<Movie> movies = catalogueService.findMovieByNewRelease(newrelease);

		if (!movies.isEmpty()) {
	        return ResponseEntity.ok(movies);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Actually there are no new releases on MovieFilms");
	    }
	}
	
	/*
	@GetMapping("/year/{year}")
    public ResponseEntity<?> findMoviesByYear(@PathVariable String year) {
        List<Movie> movies = catalogueService.findMovieByReleaseDate(year);
        
        if (!movies.isEmpty()) {
            return ResponseEntity.ok(movies);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: There are no movies from the entered year");
        }
    }
    */
	
	@PostMapping("/movie")
	public ResponseEntity<?> saveMovie(@Valid @RequestBody Movie movie) {
//		try {
//	        Movie result = catalogueService.save(movie);
//	        URI location = ServletUriComponentsBuilder
//	                .fromCurrentRequest().path("/id/{id}")
//	                .buildAndExpand(result.getId())
//	                .toUri();
//	        Map<String, Object> response = new HashMap<>();
//	        response.put("message", "Movie created successfully");
//	        response.put("id", result.getId());
//	        return ResponseEntity.created(location).body(response);
//	    } catch (DataIntegrityViolationException ex) {
//	        Map<String, Object> response = new HashMap<>();
//	        response.put("error", "Error al crear la película");
//	        response.put("message", "Ya existe una película con ese título");
//	        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
//	    }
		return null;
	}
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<?> editMovie(@PathVariable Integer id, @Valid @RequestBody Movie movieInsert){
//		try {
//	        Optional<Movie> existingMovieOpt = catalogueService.findById(id);
//	        if (!existingMovieOpt.isPresent()) {
//	            return ResponseEntity.notFound().build();
//	        }
//
//	        Movie existingMovie = existingMovieOpt.get();
//
//	        existingMovie.setTitle(movieInsert.getTitle());
//	        existingMovie.setDescription(movieInsert.getDescription());
//	        existingMovie.setReleaseDate(movieInsert.getReleaseDate());
//	        existingMovie.setGenre(movieInsert.getGenre());
//	        existingMovie.setDirector(movieInsert.getDirector());
//	        existingMovie.setNewRelease(movieInsert.isNewRelease());
//	        existingMovie.setStock(movieInsert.getStock());
//
//	        Movie updatedMovie = catalogueService.save(existingMovie);
//
//	        return ResponseEntity.ok(updatedMovie);
//            
//        } catch (DataIntegrityViolationException ex) {
//            Map<String, Object> response = new HashMap<>();
//            response.put("error", "Error al editar la película");
//            response.put("message", "El título que ya existe.");
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
//        }
		return null;
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
