package com.example.dev.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.dev.model.IncMovies;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class IncMoviesDao implements IIncMoviesDao{

	@PersistenceContext
    private EntityManager entityManager;

	@Override
    public List<IncMovies> findAll() {
        String query = "SELECT I FROM IncMovies I";
        TypedQuery<IncMovies> typedQuery = entityManager.createQuery(query, IncMovies.class);
        return typedQuery.getResultList();
    }
	
	@Override
    public Optional<IncMovies> findById(Integer incMoviesId) {
        IncMovies incMovie = entityManager.find(IncMovies.class, incMoviesId);
        return Optional.ofNullable(incMovie);
    }
	
    @Override
    public List<IncMovies> findAllOrderedByReleaseDate() {
        String query = "SELECT I FROM IncMovies I ORDER BY I.releaseDateInc ASC";
        TypedQuery<IncMovies> typedQuery = entityManager.createQuery(query, IncMovies.class);
        return typedQuery.getResultList();
    }

    @Override
    public List<IncMovies> findAllByOrderByTitleAsc() {
        String query = "SELECT I FROM IncMovies I ORDER BY I.title ASC";
        TypedQuery<IncMovies> typedQuery = entityManager.createQuery(query, IncMovies.class);
        return typedQuery.getResultList();
    }

    @Override
    @Transactional
    public void deleteById(Integer IncMoviesId) {
        IncMovies incMovie = entityManager.find(IncMovies.class, IncMoviesId);
        if (incMovie != null) {
            entityManager.remove(incMovie);
        }
    }

    @Override
    @Transactional
    public IncMovies save(IncMovies incMovies) {
        if (incMovies.getId() == null) {
            entityManager.persist(incMovies);
            return incMovies;
        } else {
            return entityManager.merge(incMovies);
        }
    }
}
