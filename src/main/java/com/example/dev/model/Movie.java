package com.example.dev.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Movie {

	@Id
    private int id;
	
	@NotBlank(message = "El título no puede estar vacío")
    private String title;
	
	@NotBlank(message = "La descripción no puede estar vacía")
    private String description;

	@NotBlank(message = "La fecha de lanzamiento no puede estar vacía")
    private String releaseDate;
	
    private Genre genre;

    @NotBlank(message = "El director no puede estar vacío")
    private String director;
    private boolean newRelease;
    
    @NotBlank(message = "La URL no puede estar vacía")
    private String imgUrl;

    @NotNull(message = "El stock no puede estar vacío")
    private int stock;

    public Movie() {
    	
    }

    public Movie(int id, String title, String description, String releaseDate, Genre genre, String director, boolean newRelease, String imgUrl, int stock) {
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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", description=" + description + ", releaseDate=" + releaseDate
				+ ", genre=" + genre + ", director=" + director + ", newRelease=" + newRelease + ", imgUrl=" + imgUrl
				+ ", stock=" + stock + "]";
	}
}

/*
@Table(name=References.CATALOGUE_TABLE_NAME)
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
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
*/
