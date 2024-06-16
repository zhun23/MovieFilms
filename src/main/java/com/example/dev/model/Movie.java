package com.example.dev.model;

import java.util.Objects;

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
import lombok.NonNull;

@Table(name=References.CATALOGUE_TABLE_NAME)
@Entity
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="catalogueid")
	private Integer id;

	@NonNull
	@NotBlank(message = "El título no puede estar vacío")
	@Column(name="title")
	private String title;

	@NonNull
	@NotBlank(message = "La descripción no puede estar vacía")
	@Column(name="description", columnDefinition = "TEXT")
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
	private Boolean newRelease;

	@Column(name = "img_url")
    private String imgUrl;

	@Column(name = "stock")
	private Integer stock;

	@Column(name = "price")
	private Integer price;

	public Movie() {
		super();
	}

	public Movie(String title, String description, String releaseDate, Genre genre, String director,
			Boolean newRelease, String imgUrl, Integer stock) {
		super();
		this.title = title;
		this.description = description;
		this.releaseDate = releaseDate;
		this.genre = genre;
		this.director = director;
		this.newRelease = newRelease;
		this.imgUrl = imgUrl;
		this.stock = stock;
	}

	public Movie(Integer id, String title, String description, String releaseDate, Genre genre, String director, Boolean newRelease, String imgUrl, Integer stock) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.releaseDate = releaseDate;
		this.genre = genre;
		this.director = director;
		this.newRelease = newRelease;
		this.imgUrl = imgUrl;
		this.stock = stock;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (this.getClass() != obj.getClass())) {
			return false;
		}
		Movie other = (Movie) obj;
		return Objects.equals(this.id, other.id);
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

	public Boolean isNewRelease() {
		return newRelease;
	}

	public void setNewRelease(Boolean newRelease) {
		this.newRelease = newRelease;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Boolean getNewRelease() {
		return newRelease;
	}
}
