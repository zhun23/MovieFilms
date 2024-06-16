package com.example.dev.service;

import com.example.dev.model.MoviesCart;

public interface IMoviesCartService {

	MoviesCart getCartsByUserId(Integer userId);

	MoviesCart addItemToMoviesCart(String nickname, Integer catalogueid, Integer quantity);

	MoviesCart updateItemInMoviesCart(String nickname, Integer catalogueid, Integer quantity);

	void deleteCartDetailByMovieId(int movieId);

	Boolean movieIdExists(String nickname, Integer id);

	MoviesCart save(MoviesCart cart);
}
