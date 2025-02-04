package com.example.dev.service;

import java.util.List;

import com.example.dev.model.IncMovies;

public interface IIncMoviesService {

	public List<IncMovies> findAll();

	public void deleteById(Integer IncMoviesId);
	
	public IncMovies save(IncMovies incMovies);
	
	public List<IncMovies> findAllAsc();
	
	public List<IncMovies> findAllSortedByTitle();
}
