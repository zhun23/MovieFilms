package com.example.dev.dto;

import java.util.List;

import com.example.dev.model.CartDetail;
import com.example.dev.model.MoviesCart;

public class MoviesCartDTO {

	private Integer cartid;
	private String nickname;
	private List<CartDetail> details;
	private int totalPrice;

	public static MoviesCartDTO toDto(MoviesCart cart) {
		MoviesCartDTO dto = new MoviesCartDTO();

		dto.setCartid(cart.getCartid());
		dto.setNickname(cart.getUser().getNickname());
		dto.setDetails(cart.getCartDetails());

		int totalPrice = cart.getCartDetails().stream()
	            .mapToInt(detail -> detail.getCatalogue().getPrice() * detail.getQuantity())
	            .sum();
	        dto.setTotalPrice(totalPrice);

		return dto;
	}

	public Integer getCartid() {
		return cartid;
	}
	public void setCartid(Integer cartid) {
		this.cartid = cartid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public List<CartDetail> getDetails() {
		return details;
	}
	public void setDetails(List<CartDetail> details) {
		this.details = details;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

}
