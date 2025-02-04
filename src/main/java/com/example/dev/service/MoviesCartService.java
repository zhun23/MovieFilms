package com.example.dev.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dev.dao.ICatalogueDao;
import com.example.dev.dao.IMoviesCartDao;
import com.example.dev.dao.IUserCtDao;
//import com.example.dev.model.Cart;
import com.example.dev.model.CartDetail;
import com.example.dev.model.Movie;
import com.example.dev.model.MoviesCart;
import com.example.dev.model.UserCt;

import jakarta.transaction.Transactional;

@Service
public class MoviesCartService implements IMoviesCartService {

	private final IMoviesCartDao moviesCartDao;

	private final IUserCtDao userCtDao;

	private final ICatalogueDao catalogueDao;

	public MoviesCartService(IMoviesCartDao moviesCartDao, IUserCtDao userCtDao, ICatalogueDao catalogueDao) {
		this.moviesCartDao = moviesCartDao;
		this.userCtDao = userCtDao;
		this.catalogueDao = catalogueDao;
	}

	@Override
	public MoviesCart getCartsByUserId(Integer userId) {
		return this.moviesCartDao.findByUser_Userid(userId).get();
	}

	@Override
	public MoviesCart addItemToMoviesCart(String nickname, Integer catalogueid, Integer quantity) {
		UserCt user = userCtDao.findByNickname(nickname);

        Movie movie = catalogueDao.findById(catalogueid).orElseThrow(() -> new RuntimeException("Película no encontrada"));

        MoviesCart cart = user.getCart();

        if (cart == null) {
			throw new RuntimeException();
		}

        CartDetail newDetail = new CartDetail();
        newDetail.setCatalogue(movie);
        newDetail.setQuantity(quantity);

        List<CartDetail> details = cart.getCartDetails();
        details.add(newDetail);

        return moviesCartDao.save(cart);
	}

	@Override
	public MoviesCart updateItemInMoviesCart(String nickname, Integer catalogueid, Integer quantity) {
		UserCt user = userCtDao.findByNickname(nickname);

        Movie movie = catalogueDao.findById(catalogueid).orElseThrow(() -> new RuntimeException("Película no encontrada"));

        MoviesCart cart = user.getCart();

        if (cart == null) {
			throw new RuntimeException();
		}

        for (CartDetail cartDetail : cart.getCartDetails()) {
    		if (movie.equals( cartDetail.getCatalogue() )) {
    			cartDetail.setQuantity(quantity);
    		}
        }

        return moviesCartDao.save(cart);
	}

	@Override
    @Transactional
    public void deleteCartDetailByMovieId(int movieId) {
        List<MoviesCart> carts = moviesCartDao.findAll();
        for (MoviesCart cart : carts) {
            List<CartDetail> cartDetails = cart.getCartDetails();
            cartDetails.removeIf(cartDetail -> cartDetail.getCatalogue().getId().equals(movieId));
            moviesCartDao.save(cart);
        }
    }

	@Override
	public Boolean movieIdExists(String nickname, Integer catalogueid) {
		UserCt user = userCtDao.findByNickname(nickname);
        Movie movie = catalogueDao.findById(catalogueid).orElseThrow(() -> new RuntimeException("Película no encontrada"));

        MoviesCart cart = user.getCart();

        for (CartDetail cartDetail : cart.getCartDetails()) {
    		if (movie.equals( cartDetail.getCatalogue() )) {
    			return true;
    		}
        }

		return false;
	}

	@Override
    public MoviesCart save(MoviesCart cart) {
        return moviesCartDao.save(cart);
    }
}
