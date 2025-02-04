package com.example.dev.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.dev.model.MoviesCart;
import com.example.dev.model.UserCt;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class MoviesCartDao implements IMoviesCartDao {

	@PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<MoviesCart> findByUser_Userid(Integer userid) {
        String query = "SELECT mc FROM MoviesCart mc WHERE mc.user.id = :userid";
        TypedQuery<MoviesCart> typedQuery = entityManager.createQuery(query, MoviesCart.class);
        typedQuery.setParameter("userid", userid);
        return typedQuery.getResultList().stream().findFirst();
    }

    @Override
    public MoviesCart findByUserNickname(String nickname) {
        String query = "SELECT mc FROM MoviesCart mc WHERE mc.user.nickname = :nickname";
        TypedQuery<MoviesCart> typedQuery = entityManager.createQuery(query, MoviesCart.class);
        return typedQuery.setParameter("nickname", nickname).getSingleResult();
    }

    @Override
    public MoviesCart findByUser(UserCt user) {
        String query = "SELECT mc FROM MoviesCart mc WHERE mc.user = :user";
        TypedQuery<MoviesCart> typedQuery = entityManager.createQuery(query, MoviesCart.class);
        return typedQuery.setParameter("user", user).getSingleResult();
    }
    
    @Override
    @Transactional
    public MoviesCart save(MoviesCart moviesCart) {
        if (moviesCart.getCartid() == null) {
            entityManager.persist(moviesCart);
            return moviesCart;
        } else {
            return entityManager.merge(moviesCart);
        }
    }

    @Override
    public List<MoviesCart> findAll() {
        String query = "SELECT mc FROM MoviesCart mc";
        TypedQuery<MoviesCart> typedQuery = entityManager.createQuery(query, MoviesCart.class);
        return typedQuery.getResultList();
    }
    
    @Override
    @Transactional
    public void delete(MoviesCart cart) {
        if (cart != null) {
            entityManager.remove(entityManager.contains(cart) ? cart : entityManager.merge(cart));
        }
    }
}
