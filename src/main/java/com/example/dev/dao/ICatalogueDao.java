package com.example.dev.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.dev.model.CatalogueParameters;
import com.example.dev.model.Genre;
import com.example.dev.model.Movie;

import jakarta.transaction.Transactional;

@Repository
public interface ICatalogueDao extends JpaRepository<Movie, Integer> {

	List<Movie> findMovieByTitle(String title);
	List<Movie> findMovieByReleaseDate(String releaseDate);
	List<Movie> findMovieByGenre(Genre genre);
	List<Movie> findMovieByDirector(String director);
	List<Movie> findMovieByNewRelease(boolean newRelease);
	List<Movie> deleteMovieByTitle(String title);

	@Query(value = """
			SELECT M FROM Movie M
			WHERE
				(:#{#params.title} IS NULL OR :#{#params.title} = ''   OR M.title LIKE %:#{#params.title}%)
			""")
	List<Movie> findWithParameters(@Param("params") CatalogueParameters params);

	@Override
	@Transactional
    void deleteById(Integer catalogueId);

	@Query(value = """
			SELECT M FROM Movie M
			WHERE
				(:#{#params.director} IS NULL OR :#{#params.director} = ''   OR M.director LIKE %:#{#params.director}%)
			""")
	List<Movie> findDirectorWithParameters(@Param("params") CatalogueParameters params);

}
