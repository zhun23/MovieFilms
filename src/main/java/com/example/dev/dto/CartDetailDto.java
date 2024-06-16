package com.example.dev.dto;

public class CartDetailDto {

	private MovieOrderDto movieOrderDto;
    private int quantity;

    public CartDetailDto(MovieOrderDto movieOrderDto, int quantity) {
        this.movieOrderDto = movieOrderDto;
        this.quantity = quantity;
    }

    public MovieOrderDto getCatalogue() {
        return movieOrderDto;
    }

    public void setCatalogue(MovieOrderDto movieOrderDto) {
        this.movieOrderDto = movieOrderDto;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}