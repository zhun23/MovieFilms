package com.example.dev.model;

public enum Genre {

	ACTION("Acción"),
	ADVENTURES("Aventuras"),
	SCIENCEFICTION("Ciencia ficción"),
	COMEDY("Comedia"),
	HORROR("Terror"),
	DRAMA("Drama"),
	KIDS("Infantil");
	
	private final String genre;
	
	private Genre(String genre) {
		this.genre = genre;
	}
	
	public String getGenre() {
		return genre;
	}
}
