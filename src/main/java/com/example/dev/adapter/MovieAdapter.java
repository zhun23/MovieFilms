package com.example.dev.adapter;

import org.springframework.stereotype.Component;

import com.example.dev.dto.MovieDto;
import com.example.dev.model.Movie;



@Component
public class MovieAdapter {

	public MovieDto adaptertoDto(Movie movie) {
		MovieDto movix = new MovieDto();
		
		movix.setId(movie.getId());
		movix.setTitle(movie.getTitle());
		movix.setDescription(movie.getDescription());
		movix.setReleaseDate(movie.getReleaseDate());
		movix.setGenre(movie.getGenre());
		movix.setDirector(movie.getDirector());
		movix.setNewRelease(movie.isNewRelease());
		
		return movix;
	}
	
}
