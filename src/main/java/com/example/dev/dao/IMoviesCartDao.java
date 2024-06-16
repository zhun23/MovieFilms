package com.example.dev.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dev.model.MoviesCart;

@Repository
public interface IMoviesCartDao extends JpaRepository<MoviesCart, Integer> {

	Optional<MoviesCart> findByUser_Userid(Integer userid);


}
