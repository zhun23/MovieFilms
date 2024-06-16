package com.example.dev.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.dev.dao.ICatalogueDao;
import com.example.dev.dao.IMoviesCartDao;
import com.example.dev.model.CatalogueParameters;
import com.example.dev.model.Genre;
import com.example.dev.model.Movie;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Service
public class CatalogueService implements ICatalogueService {

	@Autowired
	private ICatalogueDao catalogueDao;

	@Autowired
	private IMoviesCartDao moviesCartDao;

	@Autowired
    private EntityManager entityManager;

	@Override
	public Page<Movie> findAll(Pageable pageable) {
		return catalogueDao.findAll(pageable);
	}

	@Override
	public Optional<Movie> findById(int id) {
		return catalogueDao.findById(id);
	}

	@Override
	public List<Movie> findMovieByTitle(String title) {
		return catalogueDao.findMovieByTitle(title);
	}

	@Override
	public List<Movie> findMovieByReleaseDate(String releasedate) {
	 	return catalogueDao.findAll().stream()
                .filter(movie -> movie.getReleaseDate().endsWith(releasedate))
                .collect(Collectors.toList());
	}

	@Override
	public List<Movie> findMovieByGenre(Genre genre) {
		return catalogueDao.findMovieByGenre(genre);
	}

	@Override
	public List<Movie> findMovieByDirector(String director) {
		return catalogueDao.findMovieByDirector(director);
	}

	@Override
	public List<Movie> findMovieByNewRelease(boolean newrelease) {
		return catalogueDao.findMovieByNewRelease(newrelease);
	}

	@Override
	public Movie save(Movie movie) {
		Movie savedMovie = catalogueDao.save(movie);
		return savedMovie;
	}

	@Override
	@Transactional
	public void deleteById(Integer catalogueId) {
	    Optional<Movie> movieOptional = catalogueDao.findById(catalogueId);
	    if (movieOptional.isEmpty()) {
	        throw new NoSuchElementException("Movie with id " + catalogueId + " not found");
	    }
	    catalogueDao.deleteById(catalogueId);
	}

    @Override
	@Transactional
    public void deleteMovieByTitle(String title) {
        Query query = entityManager.createQuery("DELETE FROM Movie m WHERE m.title = :title");
        query.setParameter("title", title);
        query.executeUpdate();
    }

    @Override
	public List<Movie> getWithParameters(CatalogueParameters params) {
    	return this.catalogueDao.findWithParameters(params);
    }

    @Override
	public List<Movie> findDirectorWithParameters(CatalogueParameters params) {
    	return this.catalogueDao.findDirectorWithParameters(params);
    }
}
