package com.example.dev.adapter;

import org.springframework.stereotype.Component;

import com.example.dev.dto.MovieDto;
import com.example.dev.model.Movie;


@Component
public class MovieAdapter {

	public MovieDto adaptertDto(Movie movie) {
		MovieDto movieDto = new MovieDto();
		
		movieDto.setId(movie.getId());
		movieDto.setTitle(movie.getTitle());
		movieDto.setDescription(movie.getDescription());
		movieDto.setReleaseDate(movie.getReleaseDate());
		movieDto.setGenre(movie.getGenre());
		movieDto.setDirector(movie.getDirector());
		movieDto.setNewRelease(movie.isNewRelease());
		
		return movieDto;
	}
	
}
