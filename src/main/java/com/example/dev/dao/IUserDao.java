package com.example.dev.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dev.model.User;

@Repository
public interface IUserDao extends JpaRepository<User, Integer> {

	List<User> findUserByNickname(String nickname);
	List<User> findUserByFirstName(String firstName);
	List<User> findUserByLastName(String lastName);
	List<User> findUserByMail(String mail);
}
