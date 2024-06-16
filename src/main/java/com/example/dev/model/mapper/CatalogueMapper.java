package com.example.dev.model.mapper;

import com.example.dev.dto.MovieDto;
import com.example.dev.model.Movie;

public class CatalogueMapper {

	public static Movie toEntity(MovieDto movieDto) {
		Movie movie = new Movie();

		movie.setId   		 ( movieDto.getId()			);
		movie.setTitle		 (movieDto.getTitle()		);
		movie.setDescription (movieDto.getDescription()	);
		movie.setReleaseDate (movieDto.getReleaseDate()	);
		movie.setGenre		 (movieDto.getGenre()		);
		movie.setDirector	 (movieDto.getDirector()	);
		movie.setNewRelease	 (movieDto.isNewRelease()	);

		return movie;
	}
}
