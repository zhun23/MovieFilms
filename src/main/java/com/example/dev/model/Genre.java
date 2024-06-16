package com.example.dev.model;

public enum Genre {

	Action("Acción"),
	Adventures("Aventuras"),
	ScienceFiction("Ciencia ficción"),
	Fantasy("Fantasía"),
	Crime("Delito"),
	Comedy("Comedia"),
	Romance("Romance"),
	Horror("Terror"),
	Drama("Drama"),
	Musical("Musical"),
	Thriller("Suspense"),
	Animation("Animación"),
	Kids("Infantil");

	private final String genre;

	private Genre(String genre) {
		this.genre = genre;
	}

	public String getGenre() {
		return genre;
	}
}
