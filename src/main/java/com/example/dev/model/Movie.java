package com.example.dev.model;



import com.example.dev.utilities.References;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="catalogueid")
	private int id;
	
	@NonNull
	@NotBlank(message = "El título no puede estar vacío")
	@Column(name="title")
	private String title;
	
	@NonNull
	@NotBlank(message = "La descripción no puede estar vacía")
	@Column(name="description")
	private String description;
	
	@NotBlank(message = "La fecha de lanzamiento no puede estar vacía")
	@Column(name="release_date")
	private String releaseDate;
	
	@NonNull
	@Enumerated(EnumType.STRING)
	@Column(name="genre")
	private Genre genre;
	
	@NonNull
	@NotBlank(message = "El director no puede estar vacío")
	@Column(name="director")
	private String director;
	
	@Column(name="new_release")
	private boolean newRelease;
	
	@Column(name = "img_url")
    private String imgUrl;
	
	@Column(name = "stock")
	private int stock;
}
