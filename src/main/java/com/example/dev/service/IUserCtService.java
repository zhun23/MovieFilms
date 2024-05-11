package com.example.dev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.dev.dto.UserDto;
import com.example.dev.model.UserCt;

public interface IUserCtService {
	public Page<UserCt> findAll(Pageable pageable);
	//public List<User> findAll();
	public Optional<UserCt> findByUserid(int userid);
	public List<UserCt> findUserByNickname(String nickname);
	public List<UserCt> findUserByFirstname(String firstname);
	public List<UserCt> findUserByLastname(String lastname);
	public List<UserCt> findUserByMail(String mail);
	public List<UserCt> findByFirstnameAndLastname(String firstname, String lastname);
	public List<UserCt> findByFirstnameContaining(String firstname);
	public List<UserCt> findByLastnameContaining(String lastname);
	public boolean existsByNickname(String nickname);
	public boolean existsByMail(String mail);
	public UserCt save(UserCt userCt);
	public UserCt save(UserDto userDto);
	public UserCt register(UserCt userCt);
	public void deleteUserById(int id);
	public void deleteUserByNickname(String nickname);
	// public boolean nicknameExists(String nickname, Integer id);
	// public boolean emailExists(String email, Integer id);
}
