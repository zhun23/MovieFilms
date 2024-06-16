package com.example.dev.dto;

public class MovieOrderDto {

	private String title;
	private Integer price;

	public MovieOrderDto() {
		super();
	}

	public MovieOrderDto(String title, Integer price) {
		super();
		this.title = title;
		this.price = price;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
