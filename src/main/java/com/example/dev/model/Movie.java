package com.example.dev.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Table(name="catalogue")
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
	@Column(name="releasedate")
	private String releasedate;
	
	@NonNull
	@Column(name="genre")
	private Genre genre;
	
	@NonNull
	@Column(name="director")
	private String director;
	
	@NonNull
	@Column(name="newrelease")
	private boolean newrelease;
}
