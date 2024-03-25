package com.example.dev.dto;

import java.io.Serializable;

import com.example.dev.model.Genre;

import lombok.Data;

@Data
public class MovieDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
    private String title;
    private String description;
    private String releaseDate;
    private Genre genre;
    private String director;
    private boolean newRelease;

}
