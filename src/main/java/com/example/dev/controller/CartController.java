package com.example.dev.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dev.dao.IMoviesCartDao;
import com.example.dev.dao.IUserCtDao;
import com.example.dev.dto.CartDetailDto;
import com.example.dev.dto.MovieOrderDto;
import com.example.dev.dto.MoviesCartDTO;
import com.example.dev.dto.PreOrderDto;
import com.example.dev.model.CartDetail;
import com.example.dev.model.MoviesCart;
import com.example.dev.model.UserCt;
import com.example.dev.service.IMoviesCartService;
import com.example.dev.service.IUserCtService;

@RestController
@RequestMapping("/api")
public class CartController {

	private IMoviesCartService moviesCartService;

	private IUserCtService userCtService;
	
	private IUserCtDao userCtDao;
	
	private IMoviesCartDao moviesCartDao;

	public CartController(IMoviesCartService moviesCartService, IUserCtService userCtService, IUserCtDao userCtDao, IMoviesCartDao moviesCartDao) {
		this.moviesCartService = moviesCartService;
		this.userCtService = userCtService;
		this.userCtDao = userCtDao;
		this.moviesCartDao = moviesCartDao;
	}

	@GetMapping("/getCarts/{userid}")
	public ResponseEntity<MoviesCartDTO> getCartsByUserId(@PathVariable Integer userid) {
		MoviesCart cart = moviesCartService.getCartsByUserId(userid);
		MoviesCartDTO dto = MoviesCartDTO.toDto(cart);
		return ResponseEntity.ok(dto);
    }

	@GetMapping("/getTotalPriceCart/{userid}")
	public ResponseEntity<Integer> getTotalPriceCartByUserId(@PathVariable Integer userid) {
	    MoviesCart cart = moviesCartService.getCartsByUserId(userid);
	    MoviesCartDTO dto = MoviesCartDTO.toDto(cart);
	    int totalPrice = dto.getTotalPrice();
	    return ResponseEntity.ok(totalPrice);
	}

	@GetMapping("/getCartsMovieid/{nickname}/{id}")
	public ResponseEntity<Map<String, Boolean>> movieIdExists(
			@PathVariable String nickname,
			@PathVariable Integer id) {
		Boolean exists = moviesCartService.movieIdExists(nickname, id);
		Map<String, Boolean> body = Map.of("exists", exists);
		return ResponseEntity.ok(body);
	}

	@GetMapping("/getPreOrder/{nickname}")
	public ResponseEntity<?> getPreOrderByNickname(@PathVariable String nickname) {
		UserCt user = userCtService.findEspecificUserByNickname(nickname);

		List<CartDetailDto> cartDetailsDto = user.getCart().getCartDetails().stream()
				.map(cartDetail -> new CartDetailDto(
						new MovieOrderDto(cartDetail.getCatalogue().getTitle(), cartDetail.getCatalogue().getPrice()
								
						), cartDetail.getQuantity())).collect(Collectors.toList());

		PreOrderDto preOrderDto = new PreOrderDto(cartDetailsDto, user.getAddress().getAddressLine1(),
				user.getAddress().getAddressLine2(), user.getAddress().getZipcode(), user.getAddress().getLocation(),
				user.getAddress().getProvince(), user.getAddress().getCountry());

		return ResponseEntity.ok(preOrderDto);
	}


	@PostMapping("/addCart")
    public ResponseEntity<MoviesCartDTO> addItemToCart(
            @RequestParam String  nickname,
            @RequestParam Integer catalogueid,
            @RequestParam Integer quantity) {
		MoviesCart cart = moviesCartService.addItemToMoviesCart(nickname, catalogueid, quantity);
		MoviesCartDTO dto = MoviesCartDTO.toDto(cart);
        return ResponseEntity.ok(dto);
    }

	@PostMapping("/updateCart")
	public ResponseEntity<MoviesCartDTO> updateItemInMoviesCart (
            @RequestParam String  nickname,
            @RequestParam Integer catalogueid,
            @RequestParam Integer quantity) {
		MoviesCart cart = moviesCartService.updateItemInMoviesCart(nickname, catalogueid, quantity);
		MoviesCartDTO dto = MoviesCartDTO.toDto(cart);
        return ResponseEntity.ok(dto);
    }

	@DeleteMapping("/deleteCatalogue/{userid}/{catalogueId}")
	public ResponseEntity<String> deleteCatalogueFromCart(@PathVariable Integer userid, @PathVariable Integer catalogueId) {

	    MoviesCart cart = moviesCartService.getCartsByUserId(userid);

	    List<CartDetail> details = cart.getCartDetails();

	    Optional<CartDetail> detailOptional = details.stream()
	            .filter(detail -> detail.getCatalogue().getId().equals(catalogueId))
	            .findFirst();

	    details.remove(detailOptional.get());
	    moviesCartService.save(cart);

	    return ResponseEntity.ok("Catalogue deleted from cart successfully.");
	}
	
	
}
