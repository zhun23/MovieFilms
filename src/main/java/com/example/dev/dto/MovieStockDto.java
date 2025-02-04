package com.example.dev.dto;

public class MovieStockDto {

	private int id;
    private String title;
    private Integer stock;
    
    
	public MovieStockDto() {
		super();
	}
	
	public MovieStockDto(int id, String title, Integer stock) {
		super();
		this.id = id;
		this.title = title;
		this.stock = stock;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
}
