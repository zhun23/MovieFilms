package com.example.dev.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.dev.dto.MovieStockDto;
import com.example.dev.model.CatalogueParameters;
import com.example.dev.model.Genre;
import com.example.dev.model.Movie;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class CatalogueDao implements ICatalogueDao {
	
	@PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Movie> findAll(Pageable pageable) {
        String query = "SELECT m FROM Movie m";
        TypedQuery<Movie> typedQuery = entityManager.createQuery(query, Movie.class);
        int totalRows = typedQuery.getResultList().size();
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());
        return new PageImpl<>(typedQuery.getResultList(), pageable, totalRows);
    }

    @Override
    public Optional<Movie> findById(int id) {
        return Optional.ofNullable(entityManager.find(Movie.class, id));
    }

    @Override
    public List<Movie> findMovieByTitle(String title) {
        String query = "SELECT M FROM Movie M WHERE LOWER(M.title) = LOWER(:title)";
        return entityManager.createQuery(query, Movie.class)
                            .setParameter("title", title)
                            .getResultList();
    }

    @Override
    public List<Movie> findMovieByReleaseDate(String releaseDate) {
        String query = "SELECT M FROM Movie M WHERE M.releaseDate = :releaseDate";
        return entityManager.createQuery(query, Movie.class)
                            .setParameter("releaseDate", releaseDate)
                            .getResultList();
    }

    @Override
    public List<Movie> findMovieByGenre(Genre genre) {
        String query = "SELECT M FROM Movie M WHERE M.genre = :genre";
        return entityManager.createQuery(query, Movie.class)
                            .setParameter("genre", genre)
                            .getResultList();
    }

    @Override
    public List<Movie> findMovieByDirector(String director) {
        String query = "SELECT M FROM Movie M WHERE LOWER(M.director) = LOWER(:director)";
        return entityManager.createQuery(query, Movie.class)
                            .setParameter("director", director)
                            .getResultList();
    }

    @Override
    public List<Movie> findMovieByNewRelease(boolean newRelease) {
        String query = "SELECT M FROM Movie M WHERE M.newRelease = :newRelease";
        return entityManager.createQuery(query, Movie.class)
                            .setParameter("newRelease", newRelease)
                            .getResultList();
    }

    @Override
    @Transactional
    public int deleteMovieByTitle(String title) {
        String query = "DELETE FROM Movie M WHERE LOWER(M.title) = LOWER(:title)";
        return entityManager.createQuery(query)
                            .setParameter("title", title)
                            .executeUpdate();
    }
    
    @Override
    public List<Movie> findWithParameters(CatalogueParameters params) {
        StringBuilder queryBuilder = new StringBuilder("SELECT M FROM Movie M WHERE 1=1");
        
        if (params.getTitle() != null && !params.getTitle().isEmpty()) {
            queryBuilder.append(" AND M.title LIKE :title");
        }
        
        TypedQuery<Movie> query = entityManager.createQuery(queryBuilder.toString(), Movie.class);
        
        if (params.getTitle() != null && !params.getTitle().isEmpty()) {
            query.setParameter("title", "%" + params.getTitle() + "%");
        }

        return query.getResultList();
    }

    @Override
    @Transactional
    public void deleteById(Integer catalogueId) {
        String query = "DELETE FROM Movie m WHERE m.id = :catalogueId";
        entityManager.createQuery(query)
                     .setParameter("catalogueId", catalogueId)
                     .executeUpdate();
    }

    @Override
    public List<Movie> findDirectorWithParameters(CatalogueParameters params) {
        StringBuilder queryBuilder = new StringBuilder("SELECT M FROM Movie M WHERE 1=1");
        
        if (params.getDirector() != null && !params.getDirector().isEmpty()) {
            queryBuilder.append(" AND M.director LIKE :director");
        }
        
        TypedQuery<Movie> query = entityManager.createQuery(queryBuilder.toString(), Movie.class);
        
        if (params.getDirector() != null && !params.getDirector().isEmpty()) {
            query.setParameter("director", "%" + params.getDirector() + "%");
        }

        return query.getResultList();
    }

    @Override
    public Page<MovieStockDto> findAllMovieStockOrderedByStock(Pageable pageable) {
        String query = "SELECT new com.example.dev.dto.MovieStockDto(m.id, m.title, m.stock) FROM Movie m ORDER BY m.stock ASC";
        TypedQuery<MovieStockDto> typedQuery = entityManager.createQuery(query, MovieStockDto.class);
        int totalRows = typedQuery.getResultList().size();
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());
        return new PageImpl<>(typedQuery.getResultList(), pageable, totalRows);
    }
    
    @Override
    @Transactional
    public Movie save(Movie movie) {
        if (movie.getId() == null) {
            entityManager.persist(movie);
        } else {
            entityManager.merge(movie);
        }
        return movie;
    }

}
