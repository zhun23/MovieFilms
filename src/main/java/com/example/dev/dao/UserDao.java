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
import org.springframework.stereotype.Repository;

import com.example.dev.exceptions.UserEmailExistsException;
import com.example.dev.exceptions.UserNicknameExistsException;
import com.example.dev.model.User;
import com.example.dev.utilities.DataBaseConfig;

@Repository
public class UserDao {
	
	@Autowired
    private DataSource dataSource;
	
	public UserDao() {
        this.dataSource = DataBaseConfig.getDataSource();
    }
	
	public List<User> listUsers() {
        List<User> movies = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try (Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                movies.add(mapRowToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }
	
	public User findById(int id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        User user = null;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = mapRowToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
	
	public List<User> findUserByNickname(String nickname) {
		List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE nickname = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nickname);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
	
	public List<User> findUserByFirstName(String firstName) {
		List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE firstname = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, firstName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
	
	public List<User> findUserByLastName(String lastName) {
		List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE lastname = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, lastName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
	
	public List<User> findUserByMail(String mail) {
		List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE mail = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mail);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
	
	public User save(User user) {
        String sql = "INSERT INTO user (nickname, firstName, lastName, mail, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getNickname());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getMail());
            stmt.setString(5, user.getPassword());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
	
	public void deleteUserById(int id) {
		String sql = "DELETE FROM user WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public void deleteUserByNickname(String nickname) {
		String sql = "DELETE FROM user WHERE nickname = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nickname);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	//List<User> findByFirstNameContaining(String firstName);
	
	//List<User> findByLastNameContaining(String lastName);
	
	//@Query("SELECT u FROM User u WHERE (u.firstName LIKE %?1% OR u.lastName LIKE %?1% OR u.nickname LIKE %?1% OR u.mail LIKE %?1%) AND (u.firstName LIKE %?2% OR u.lastName LIKE %?2% OR u.nickname LIKE %?2% OR u.mail LIKE %?2%)")
    //List<User> findByFirstNameAndLastName(String word1, String word2);
	
	
	public User update(User user) throws SQLException, UserEmailExistsException, UserNicknameExistsException {
        if (emailExists(user.getMail(), user.getId())) {
            throw new UserEmailExistsException("Ya existe un usuario con ese correo electrónico");
        }
        if (nicknameExists(user.getNickname(), user.getId())) {
            throw new UserNicknameExistsException("Ya existe un usuario con ese apodo");
        }

        String sql = "UPDATE users SET nickname = ?, first_name = ?, last_name = ?, mail = ?, password = ?, credit = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getNickname());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getMail());
            stmt.setString(5, user.getPassword());
            stmt.setInt(6, user.getCredit());
            stmt.setInt(7, user.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Actualización fallida, no se modificaron filas.");
            }
            return user;
        }
    }

	public boolean nicknameExists(String nickname, int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE nickname = ? AND id != ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nickname);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public boolean emailExists(String email, int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE mail = ? AND id != ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }




	
	private User mapRowToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setNickname(rs.getString("nickname"));
        user.setFirstName(rs.getString("firstName"));
        user.setLastName(rs.getString("lastName"));
        user.setMail(rs.getString("mail"));
        user.setPassword(rs.getString("password"));
        user.setCredit(rs.getInt("credit"));
        return user;
    }
}
