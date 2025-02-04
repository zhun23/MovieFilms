package com.example.dev.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.dev.dao.IIncMoviesDao;
import com.example.dev.model.IncMovies;

import jakarta.transaction.Transactional;

@Service
public class IncMoviesService implements IIncMoviesService {

	private final IIncMoviesDao incMoviesDao;

	public IncMoviesService(IIncMoviesDao incMoviesDao) {
		this.incMoviesDao = incMoviesDao;
	}
	
	public List<IncMovies> findAll() {
		return incMoviesDao.findAll();
	}
	
	public List<IncMovies> findAllAsc() {
        return incMoviesDao.findAllOrderedByReleaseDate();
    }
	
	public List<IncMovies> findAllSortedByTitle() {
        return incMoviesDao.findAllByOrderByTitleAsc();
    }
	
	@Override
	@Transactional
	public void deleteById(Integer IncMoviesId) {
	    Optional<IncMovies> incMovieOptional = incMoviesDao.findById(IncMoviesId);
	    if (incMovieOptional.isEmpty()) {
	        throw new NoSuchElementException("Movie with id " + IncMoviesId + " not found");
	    }
	    incMoviesDao.deleteById(IncMoviesId);
	}

	public IncMovies save(IncMovies incMovies) {
		return incMoviesDao.save(incMovies);
	}

	
}
