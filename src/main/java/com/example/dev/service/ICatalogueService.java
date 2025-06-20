package com.example.dev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.dev.dto.MovieStockDto;
import com.example.dev.model.CatalogueParameters;
import com.example.dev.model.Genre;
import com.example.dev.model.Movie;

public interface ICatalogueService {

	public Page<Movie> findAll(Pageable pageable);
	
	public Optional<Movie> findById(int id);
	
	public List<Movie> findMovieByTitle(String title);
	
	public List<Movie> findMovieByReleaseDate(String releasedate);
	
	public List<Movie> findMovieByGenre(Genre genre);
	
	public List<Movie> findMovieByDirector(String director);
	
	public List<Movie> findMovieByNewRelease(boolean newrelease);
	
	public Movie save(Movie movie);
	
	public void deleteById(Integer id);
	
	public void deleteMovieByTitle(String title);
	
	public List<Movie> getWithParameters(CatalogueParameters params);
	
	public List<Movie> findDirectorWithParameters(CatalogueParameters params);
	
	public Page<MovieStockDto> getAllMovieStock(Pageable pageable);
}
