package com.example.dev.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

@Embeddable
public class CartDetail {

	@ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Movie catalogue;

	@Column(nullable = false)
	private Integer quantity;


	public CartDetail() {
	}

	public Movie getCatalogue() {
		return this.catalogue;
	}

	public void setCatalogue(Movie cartDetailId) {
		this.catalogue = cartDetailId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
