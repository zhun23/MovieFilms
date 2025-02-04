package com.example.dev.model;

import java.time.LocalDate;
import java.util.Date;

import com.example.dev.utilities.References;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name=References.INCNEWMOVIES_TABLE_NAME)
@Entity
public class IncMovies {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IncMoviesId")
	private Integer id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="releaseDate")
	private LocalDate releaseDateInc;
	
	
	public IncMovies() {
		super();
	}

	public IncMovies(String title, LocalDate releaseDateInc) {
		super();
		this.title = title;
		this.releaseDateInc = releaseDateInc;
	}

	public IncMovies(Integer id, String title, LocalDate releaseDateInc) {
		super();
		this.id = id;
		this.title = title;
		this.releaseDateInc = releaseDateInc;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getReleaseDateInc() {
		return releaseDateInc;
	}

	public void setReleaseDateInc(LocalDate releaseDateInc) {
		this.releaseDateInc = releaseDateInc;
	}
}
