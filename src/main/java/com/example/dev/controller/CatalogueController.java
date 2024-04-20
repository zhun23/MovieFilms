package com.example.dev.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.dev.service.ICatalogueService;
import com.example.dev.utilities.ConvGenre;

import com.example.dev.exceptions.MovieTitleExistsException;
import com.example.dev.model.ErrorResponse;
import com.example.dev.model.Genre;
import com.example.dev.model.Movie;

@RestController
public class CatalogueController {
	
	@Autowired
	private ICatalogueService catalogueService;
	
	@GetMapping("/list")
	public ResponseEntity<?> listMovies() {
		List<Movie> movies = catalogueService.findAll();
		
		if (!movies.isEmpty()) {
			return ResponseEntity.ok(movies);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: There's no movies in the database");
		}
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> findById(@PathVariable int id) {
	    Movie movie = catalogueService.findById(id);
	    
	    if (movie != null) {
	        return ResponseEntity.ok(movie);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No movie matching that ID was found in the database");
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
	
	@PostMapping("/movie")
	public ResponseEntity<?> saveMovie(@RequestBody Movie movie) {
	    List<String> errors = new ArrayList<>();
	    if (movie.getTitle() == null || movie.getTitle().trim().isEmpty()) {
	        errors.add("El título no puede estar vacío.");
	    }
	    if (movie.getDescription() == null || movie.getDescription().trim().isEmpty()) {
	        errors.add("La descripción no puede estar vacía.");
	    }
	    if (movie.getReleaseDate() == null || movie.getReleaseDate().trim().isEmpty()) {
	        errors.add("La fecha de lanzamiento no puede estar vacía.");
	    }
	    if (movie.getDirector() == null || movie.getDirector().trim().isEmpty()) {
	        errors.add("El director no puede estar vacío.");
	    }
	    if (movie.getImgUrl() == null || movie.getImgUrl().trim().isEmpty()) {
	        errors.add("La URL de la imagen no puede estar vacía.");
	    }

	    if (!errors.isEmpty()) {
	        return ResponseEntity.badRequest().body(new ErrorResponse("Validation Error", String.join(", ", errors)));
	    }

	    try {
	        Movie result = catalogueService.save(movie);
	        URI location = ServletUriComponentsBuilder
	                .fromCurrentRequest().path("/id/{id}")
	                .buildAndExpand(result.getId())
	                .toUri();
	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Película creada exitosamente");
	        response.put("id", result.getId());
	        return ResponseEntity.created(location).body(response);
	    } catch (MovieTitleExistsException ex) {
	        ErrorResponse errorResponse = new ErrorResponse("Conflict", "Ya existe una película con ese título");
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	    } catch (Exception ex) {
	        ErrorResponse errorResponse = new ErrorResponse("Internal Server Error", "Se produjo un error mientras se guardaba la película.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}

	
	@PutMapping("/edit/{id}")
	public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Movie movieInsert) {
	    List<String> errors = new ArrayList<>();
	    if (movieInsert.getTitle() == null || movieInsert.getTitle().trim().isEmpty()) {
	        errors.add("El título no puede estar vacío.");
	    }
	    if (movieInsert.getDescription() == null || movieInsert.getDescription().trim().isEmpty()) {
	        errors.add("La descripción no puede estar vacía.");
	    }
	    if (movieInsert.getReleaseDate() == null || movieInsert.getReleaseDate().trim().isEmpty()) {
	        errors.add("La fecha de lanzamiento no puede estar vacía.");
	    }
	    if (movieInsert.getDirector() == null || movieInsert.getDirector().trim().isEmpty()) {
	        errors.add("El director no puede estar vacío.");
	    }
	    if (errors.size() > 0) {
	        return ResponseEntity.badRequest().body(new ErrorResponse("Validation Error", String.join(" ", errors)));
	    }

	    try {
	        Movie existingMovie = catalogueService.findById(id);
	        if (existingMovie == null) {
	            return ResponseEntity.notFound().build();
	        }

	        existingMovie.setTitle(movieInsert.getTitle());
	        existingMovie.setDescription(movieInsert.getDescription());
	        existingMovie.setReleaseDate(movieInsert.getReleaseDate());
	        existingMovie.setGenre(movieInsert.getGenre());
	        existingMovie.setDirector(movieInsert.getDirector());
	        existingMovie.setNewRelease(movieInsert.isNewRelease());
	        existingMovie.setStock(movieInsert.getStock());

	        Movie updatedMovie = catalogueService.update(existingMovie);
	        return ResponseEntity.ok(updatedMovie);
	    } catch (MovieTitleExistsException ex) {
	        ErrorResponse errorResponse = new ErrorResponse("Conflict", "Ya existe una película con ese título");
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	    } catch (Exception ex) {
	        ErrorResponse errorResponse = new ErrorResponse("Internal Server Error", "Error interno del servidor");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}



	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable int id) {
	    Movie movie = catalogueService.findById(id);
	    
	    if (movie != null) {
	        catalogueService.deleteById(id);
	        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Movie with id: " + id + " deleted");
	    } else {
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
