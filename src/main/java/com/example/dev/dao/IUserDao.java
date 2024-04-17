package com.example.dev.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.dev.model.User;

@Repository
public interface IUserDao extends JpaRepository<User, Integer> {
	List<User> findUserByNickname(String nickname);
	
	List<User> findUserByFirstName(String firstName);
	
	List<User> findUserByLastName(String lastName);
	
	List<User> findUserByMail(String mail);
	
	List<User> findByFirstNameContaining(String firstName);
	
	List<User> findByLastNameContaining(String lastName);
	
	@Query("SELECT u FROM User u WHERE (u.firstName LIKE %?1% OR u.lastName LIKE %?1% OR u.nickname LIKE %?1% OR u.mail LIKE %?1%) AND (u.firstName LIKE %?2% OR u.lastName LIKE %?2% OR u.nickname LIKE %?2% OR u.mail LIKE %?2%)")
    List<User> findByFirstNameAndLastName(String word1, String word2);
}
