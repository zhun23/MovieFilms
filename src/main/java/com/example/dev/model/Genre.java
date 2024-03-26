package com.example.dev.model;

public enum Genre {

	ACTION("Acción"),
	ADVENTURES("Aventuras"),
	SCIENCEFICTION("Ciencia ficción"),
	FANTASY("Fantasía"),
	CRIME("Delito"),
	COMEDY("Comedia"),
	ROMANCE("Romance"),
	HORROR("Terror"),
	DRAMA("Drama"),
	MUSICAL("Musical"),
	THRILLER("Suspense"),
	ANIMATION("Animación"),
	KIDS("Infantil");
	
	private final String genre;
	
	private Genre(String genre) {
		this.genre = genre;
	}
	
	public String getGenre() {
		return genre;
	}
}
