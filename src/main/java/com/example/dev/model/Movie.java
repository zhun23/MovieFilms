package com.example.dev.model;

import com.example.dev.utilities.References;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Table(name=References.CATALOGUE_TABLE_NAME)
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Movie {

	@Id
	@Column(name="id")
	private int id;
	
	@NonNull
	@Column(name="title")
	private String title;
	
	@NonNull
	@Column(name="description")
	private String description;
	
	@NonNull
	@Column(name="release_date")
	private String releaseDate;
	
	@NonNull
	@Enumerated(EnumType.STRING)
	@Column(name="genre")
	private Genre genre;
	
	@NonNull
	@Column(name="director")
	private String director;
	
	@NonNull
	@Column(name="new_release")
	private boolean newRelease;
}
