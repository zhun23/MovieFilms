package com.example.dev.utilities;

import com.example.dev.model.Genre;

public class ConvGenre {

	public Genre convertirCadenaAGenre(String genreSuministrated) {
	    for (Genre genre : Genre.values()) {
	        if (genre.name().equalsIgnoreCase(genreSuministrated)) {
	            return genre;
	        }
	    }
	    return null;
	}
}
