package com.example.dev.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
//@Table(name = "")
public class MoviesCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cartid;

	@OneToOne(optional = false)
	@JoinColumn(name = "userid")
	@JsonBackReference
	private UserCt user;

	@ElementCollection
	@CollectionTable(name = "cart_details",
			joinColumns = @JoinColumn(name = "cartid"),
			foreignKey  = @ForeignKey(name = "FK_cart_details_cartid"))
	private List<CartDetail> cartDetails;

	public MoviesCart() {
		super();
	}

	public Integer getCartid() {
		return cartid;
	}

	public void setCartid(Integer cartid) {
		this.cartid = cartid;
	}

	public UserCt getUser() {
		return user;
	}

	public void setUser(UserCt user) {
		this.user = user;
	}

	public List<CartDetail> getCartDetails() {
		return cartDetails;
	}

	public void setCartDetails(List<CartDetail> cartDetails) {
		this.cartDetails = cartDetails;
	}

}
