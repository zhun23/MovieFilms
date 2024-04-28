package com.example.dev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.dev.model.User;

public interface IUserService {
	public Page<User> findAll(Pageable pageable);
	//public List<User> findAll();
	public Optional<User> findById(int id);
	public List<User> findUserByNickname(String nickname);
	public List<User> findUserByFirstName(String firstName);
	public List<User> findUserByLastName(String lastName);
	public List<User> findUserByMail(String mail);
	public List<User> findByFirstNameAndLastName(String firstName, String lastName);
	public List<User> findByFirstNameContaining(String firstName);
	public List<User> findByLastNameContaining(String lastName);
	public User save(User user);
	public User register(User user);
	public void deleteUserById(int id);
	public void deleteUserByNickname(String nickname);
	boolean nicknameExists(String nickname, Integer id);
	public boolean emailExists(String email, Integer id);
}
