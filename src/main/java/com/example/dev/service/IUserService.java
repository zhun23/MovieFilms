package com.example.dev.service;

import java.util.List;
import java.util.Optional;

import com.example.dev.model.User;

public interface IUserService {

	public List<User> findAll();
	public Optional<User> findById(int id);
	public List<User> findUserByFirstName(String firstName);
	public List<User> findUserByLastName(String lastName);
	public List<User> findUserByMail(String mail);
	public User save(User user);
	public void deleteUserById(int id);
	public void deleteUserByNickname(String nickname);
}
