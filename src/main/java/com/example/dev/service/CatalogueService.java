package com.example.dev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dev.dao.CatalogueDao;
import com.example.dev.model.Genre;
import com.example.dev.model.Movie;

import org.springframework.transaction.annotation.Transactional;

@Service
public class CatalogueService implements ICatalogueService {

    private final CatalogueDao catalogueDao;

    @Autowired
    public CatalogueService(CatalogueDao catalogueDao) {
        this.catalogueDao = catalogueDao;
    }

    @Transactional(readOnly = true)
    public List<Movie> findAll() {
        return catalogueDao.findAll();
    }

    @Transactional(readOnly = true)
    public Movie findById(int id) {
        return catalogueDao.findById(id);
    }

    @Transactional
    public Movie save(Movie movie) {
        return catalogueDao.save(movie);
    }

    @Transactional
    public void deleteById(int id) {
        catalogueDao.deleteById(id);
    }

    @Transactional
	public List<Movie> findMovieByTitle(String title) {
		return catalogueDao.findMovieByTitle(title);
	}

    @Transactional
	public List<Movie> findMovieByReleaseDate(String releasedate) {
		return catalogueDao.findMovieByReleaseDate(releasedate);
	}

    @Transactional
	public List<Movie> findMovieByGenre(Genre genre) {
		return catalogueDao.findMovieByGenre(genre);
	}

    @Transactional
	public List<Movie> findMovieByDirector(String director) {
		return catalogueDao.findMovieByDirector(director);
	}

    @Transactional
	public List<Movie> findMovieByNewRelease(boolean newrelease) {
		return catalogueDao.findMovieByNewRelease(newrelease);
	}

    @Transactional
	public void deleteMovieByTitle(String title) {
    	catalogueDao.deleteMovieByTitle(title);
	}
    
    @Transactional
    public Movie update(Movie movie) {
        return catalogueDao.update(movie);
    }
}


/*
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
*/
