package com.example.dev.dto;

import com.example.dev.model.Genre;

public class MovieDto {

	private int id;
    private String title;
    private String description;
    private String releaseDate;
    private Genre genre;
    private String director;
    private boolean newRelease;

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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public boolean isNewRelease() {
		return newRelease;
	}
	public void setNewRelease(boolean newRelease) {
		this.newRelease = newRelease;
	}

}
