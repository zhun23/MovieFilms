package com.example.dev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dev.dao.ICatalogueDao;
import com.example.dev.model.Genre;
import com.example.dev.model.Movie;

@Service
public class CatalogueService implements ICatalogueService {

	@Autowired
	private ICatalogueDao catalogueDao;
	
	public List<Movie> findAll() {
		return catalogueDao.findAll();
	}
	
	public Optional<Movie> findById(int id){
		return catalogueDao.findById(id);
	}
	
	public Movie save(Movie movie) {
		return catalogueDao.save(movie);
	}
	
	public void deleteById(int id) {
		catalogueDao.deleteById(id);
	}

	public List<Movie> findMovieByTitle(String title) {
		return catalogueDao.findMovieByTitle(title);
	}
	
	public List<Movie> findMovieByGenre(Genre genre) {
		return catalogueDao.findMovieByGenre(genre);
	}

	public List<Movie> findMovieByDirector(String director) {
		return catalogueDao.findMovieByDirector(director);
	}

	public List<Movie> findMovieByNewRelease(boolean newrelease) {
		return catalogueDao.findMovieByNewRelease(newrelease);
	}	
	
}
