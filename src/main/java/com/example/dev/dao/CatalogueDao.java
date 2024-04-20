package com.example.dev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.example.dev.exceptions.MovieTitleExistsException;
import com.example.dev.exceptions.MySQLException;
import com.example.dev.model.Genre;
import com.example.dev.model.Movie;
import com.example.dev.utilities.DataBaseConfig;

@Repository
public class CatalogueDao {

	@Autowired
    private DataSource dataSource;

    public CatalogueDao() {
        this.dataSource = DataBaseConfig.getDataSource();
    }
    
    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM catalogue";
        try (Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                movies.add(mapRowToMovie(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }
    
    public Movie findById(int id) {
        String sql = "SELECT * FROM catalogue WHERE id = ?";
        Movie movie = null;
        try (Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                movie = mapRowToMovie(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movie;
    }

    public List<Movie> findMovieByTitle(String title) {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM catalogue WHERE title = ?";
        try (Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                movies.add(mapRowToMovie(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public List<Movie> findMovieByReleaseDate(String releaseDate) {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM catalogue WHERE release_date = ?";
        try (Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, releaseDate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                movies.add(mapRowToMovie(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public List<Movie> findMovieByGenre(Genre genre) {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM catalogue WHERE genre = ?";
        try (Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, genre.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                movies.add(mapRowToMovie(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public List<Movie> findMovieByDirector(String director) {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM catalogue WHERE director = ?";
        try (Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, director);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                movies.add(mapRowToMovie(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public List<Movie> findMovieByNewRelease(boolean newRelease) {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM catalogue WHERE new_release = ?";
        try (Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, newRelease);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                movies.add(mapRowToMovie(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public boolean deleteMovieByTitle(String title) {
        String sql = "DELETE FROM catalogue WHERE title = ?";
        try (Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Movie save(Movie movie) {
        String sql = "INSERT INTO catalogue (title, description, release_date, genre, director, new_release, img_url, stock) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getDescription());
            stmt.setString(3, movie.getReleaseDate());
            stmt.setString(4, movie.getGenre().toString());
            stmt.setString(5, movie.getDirector());
            stmt.setBoolean(6, movie.isNewRelease());
            stmt.setString(7, movie.getImgUrl());
            stmt.setInt(8, movie.getStock());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting movie failed, no rows affected.");
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    movie.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                throw new MovieTitleExistsException("Ya existe una película con ese título", e);
            }
            throw new RuntimeException("Failed to insert the movie due to a database error", e);
        }
        return movie;
    }
    
    public Movie update(Movie movie) {
        if (titleExists(movie.getTitle(), movie.getId())) {
            throw new MovieTitleExistsException("Ya existe una película con ese título");
        }

        String sql = "UPDATE catalogue SET title = ?, description = ?, release_date = ?, genre = ?, director = ?, new_release = ?, img_url = ?, stock = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getDescription());
            stmt.setString(3, movie.getReleaseDate());
            stmt.setString(4, movie.getGenre().toString());
            stmt.setString(5, movie.getDirector());
            stmt.setBoolean(6, movie.isNewRelease());
            stmt.setString(7, movie.getImgUrl());
            stmt.setInt(8, movie.getStock());
            stmt.setInt(9, movie.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating movie failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update the movie due to a database error", e);
        }
        return movie;
    }

    private boolean titleExists(String title, int currentMovieId) {
        String sql = "SELECT COUNT(*) FROM catalogue WHERE title = ? AND id != ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setInt(2, currentMovieId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check title existence", e);
        }
        return false;
    }


    
    public void deleteById(int id) {
        String sql = "DELETE FROM catalogue WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting movie failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Movie mapRowToMovie(ResultSet rs) throws SQLException {
        Movie movie = new Movie();
        movie.setId(rs.getInt("id"));
        movie.setTitle(rs.getString("title"));
        movie.setDescription(rs.getString("description"));
        movie.setReleaseDate(rs.getString("release_date"));
        movie.setGenre(Genre.valueOf(rs.getString("genre")));
        movie.setDirector(rs.getString("director"));
        movie.setNewRelease(rs.getBoolean("new_release"));
        movie.setImgUrl(rs.getString("img_url"));
        movie.setStock(rs.getInt("stock"));
        return movie;
    }
}

/*
@Repository
public interface ICatalogueDao extends JpaRepository<Movie, Integer> {
	
	List<Movie> findMovieByTitle(String title);
	List<Movie> findMovieByReleaseDate(String releaseDate);
	List<Movie> findMovieByGenre(Genre genre);
	List<Movie> findMovieByDirector(String director);
	List<Movie> findMovieByNewRelease(boolean newRelease);
	List<Movie> deleteMovieByTitle(String title);
}
*/
