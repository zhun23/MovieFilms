package com.example.dev.service;

import java.util.List;
import java.util.Optional;

import com.example.dev.model.Genre;
import com.example.dev.model.Movie;

public interface ICatalogueService {

	public List<Movie> findAll();
	public Optional<Movie> findById(int id);
	public Movie save(Movie movie);
	public void deleteById(int id);
	public List<Movie> findMovieByTitle(String title);
	public List<Movie> findMovieByGenre(Genre genre);
	public List<Movie> findMovieByDirector(String director);
	public List<Movie> findMovieByNewRelease(boolean newrelease);
}
