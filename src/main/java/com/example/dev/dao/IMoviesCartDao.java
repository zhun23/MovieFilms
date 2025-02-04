package com.example.dev.dao;

import java.util.List;
import java.util.Optional;

import com.example.dev.model.MoviesCart;
import com.example.dev.model.UserCt;


public interface IMoviesCartDao {

	Optional<MoviesCart> findByUser_Userid(Integer userid);

	MoviesCart findByUserNickname(String nickname);
	
	MoviesCart findByUser(UserCt user);
	
	MoviesCart save(MoviesCart moviesCart);
	
	List<MoviesCart> findAll();
	
	void delete(MoviesCart cart);
}
