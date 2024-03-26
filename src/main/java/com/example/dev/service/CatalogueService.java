package com.example.dev.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dev.dao.ICatalogueDao;
import com.example.dev.model.Genre;
import com.example.dev.model.Movie;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Service
public class CatalogueService implements ICatalogueService {

	//@Autowired
	//private MovieAdapter adapter;
	
	@Autowired
	private ICatalogueDao catalogueDao;
	
	@Override
	public List<Movie> findAll() {
		return catalogueDao.findAll();
	}
	
	public Optional<Movie> findById(int id) {
		return catalogueDao.findById(id);
	}

	public List<Movie> findMovieByTitle(String title) {
		return catalogueDao.findMovieByTitle(title);
	}
	
	public List<Movie> findMovieByReleaseDate(String releasedate) {
		return catalogueDao.findAll().stream()
                .filter(movie -> movie.getReleaseDate().endsWith(releasedate))
                .collect(Collectors.toList());
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
	
	public Movie save(Movie movie) {
		Movie savedMovie = catalogueDao.save(movie);
		return savedMovie;
	}
	
	public void deleteById(int id) {
		catalogueDao.deleteById(id);
	}
	
	@Autowired
    private EntityManager entityManager;

    @Transactional
    public void deleteMovieByTitle(String title) {
        Query query = entityManager.createQuery("DELETE FROM Movie m WHERE m.title = :title");
        query.setParameter("title", title);
        query.executeUpdate();
    }
	
}
