package com.example.dev.dao;

import java.util.List;
import java.util.Optional;

import com.example.dev.model.IncMovies;

public interface IIncMoviesDao { //extends JpaRepository<IncMovies, Integer> {

	List<IncMovies> findAll();
	
	Optional<IncMovies> findById(Integer incMoviesId);
	
	List<IncMovies> findAllOrderedByReleaseDate();
	
	List<IncMovies> findAllByOrderByTitleAsc();
	
	IncMovies save(IncMovies incMovies);
	
	void deleteById(Integer IncMoviesId);
	
	/*
	@Transactional
	@Modifying
	@Query("DELETE FROM IncMovies I WHERE I.id = :IncMoviesId")
	void deleteById(@Param("IncMoviesId") Integer IncMoviesId);
	
	@Query("SELECT I FROM IncMovies I ORDER BY I.releaseDateInc ASC")
    List<IncMovies> findAllOrderedByReleaseDate();
	
	List<IncMovies> findAllByOrderByTitleAsc();
	*/
}
