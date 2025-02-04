package com.example.dev.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.dev.dto.MovieStockDto;
import com.example.dev.model.CatalogueParameters;
import com.example.dev.model.Genre;
import com.example.dev.model.Movie;

public interface ICatalogueDao {

	Page<Movie> findAll(Pageable pageable);
	
    Optional<Movie> findById(int id);
    
    List<Movie> findMovieByTitle(String title);
    
    List<Movie> findMovieByReleaseDate(String releaseDate);
    
    List<Movie> findMovieByGenre(Genre genre);
    
    List<Movie> findMovieByDirector(String director);
    
    List<Movie> findMovieByNewRelease(boolean newRelease);
    
    int deleteMovieByTitle(String title);
    
    List<Movie> findWithParameters(CatalogueParameters params);
    
    void deleteById(Integer catalogueId);
    
    List<Movie> findDirectorWithParameters(CatalogueParameters params);
    
    Page<MovieStockDto> findAllMovieStockOrderedByStock(Pageable pageable);
    
    Movie save(Movie movie);
    
	/*
	@Query("SELECT m FROM Movie m")
	Page<Movie> findAll(Pageable pageable);
	
	@Query("SELECT m FROM Movie m WHERE m.id = :id")
	Optional<Movie> findById(@Param("id") int id);
	
	//List<Movie> findMovieByTitle(String title);
	@Query("SELECT M FROM Movie M WHERE LOWER(M.title) = LOWER(:title)")
    List<Movie> findMovieByTitle(@Param("title") String title);
	
	
	//List<Movie> findMovieByReleaseDate(String releaseDate);
	@Query("SELECT M FROM Movie M WHERE M.releaseDate = :releaseDate")
	List<Movie> findMovieByReleaseDate(@Param("releaseDate") String releaseDate);

	//List<Movie> findMovieByGenre(Genre genre);
	@Query("SELECT M FROM Movie M WHERE M.genre = :genre")
	List<Movie> findMovieByGenre(@Param("genre") Genre genre);
	
	//List<Movie> findMovieByDirector(String director);
	@Query("SELECT M FROM Movie M WHERE LOWER(M.director) = LOWER(:director)")
	List<Movie> findMovieByDirector(@Param("director") String director);
	
	//List<Movie> findMovieByNewRelease(boolean newRelease);
	@Query("SELECT M FROM Movie M WHERE M.newRelease = :newRelease")
	List<Movie> findMovieByNewRelease(@Param("newRelease") boolean newRelease);
	
	//List<Movie> deleteMovieByTitle(String title);
	@Transactional
	@Modifying
	@Query("DELETE FROM Movie M WHERE LOWER(M.title) = LOWER(:title)")
	int deleteMovieByTitle(@Param("title") String title);

	@Query(value = """
			SELECT M FROM Movie M
			WHERE
				(:#{#params.title} IS NULL OR :#{#params.title} = ''   OR M.title LIKE %:#{#params.title}%)
			""")
	List<Movie> findWithParameters(@Param("params") CatalogueParameters params);

	@Transactional
	@Modifying
	@Query("DELETE FROM Movie m WHERE m.id = :catalogueId")
	void deleteById(@Param("catalogueId") Integer catalogueId);
	
	@Query(value = """
			SELECT M FROM Movie M
			WHERE
				(:#{#params.director} IS NULL OR :#{#params.director} = ''   OR M.director LIKE %:#{#params.director}%)
			""")
	List<Movie> findDirectorWithParameters(@Param("params") CatalogueParameters params);

	@Query("SELECT new com.example.dev.dto.MovieStockDto(m.id, m.title, m.stock) FROM Movie m ORDER BY m.stock ASC")
    Page<MovieStockDto> findAllMovieStockOrderedByStock(Pageable pageable);
    */
}
