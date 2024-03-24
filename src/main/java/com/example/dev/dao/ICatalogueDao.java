package com.example.dev.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dev.model.Movie;

@Repository
public interface ICatalogueDao extends JpaRepository<Movie, Integer> {
	
	List<Movie> findMovieByTitle(String title);
	List<Movie> findMovieByGenre(Genre genre);
	List<Movie> findMovieByDirector(String director);
	List<Movie> findMovieByNewRelease(boolean newrelease);
}
