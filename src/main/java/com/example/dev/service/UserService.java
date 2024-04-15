package com.example.dev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dev.dao.IUserDao;
import com.example.dev.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserDao userDao;
	
	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}
	
	public Optional<User> findById(int id) {
		return userDao.findById(id);
	}
	
	public List<User> findUserByNickname(String nickname) {
		return userDao.findUserByNickname(nickname);
	}
	
	public List<User> findUserByFirstName(String firstname) {
		return userDao.findUserByFirstName(firstname);
	}
	
	public List<User> findUserByLastName(String lastname) {
		return userDao.findUserByLastName(lastname);
	}
	
	public List<User> findUserByMail(String mail) {
		return userDao.findUserByMail(mail);
	}
	
	public User save(User user) {
		User savedUser = userDao.save(user);
		return savedUser;
	}
	
	public User register(User user) {
		User registeredUser = userDao.save(user);
		return registeredUser;
	}
	
	public void deleteUserById(int id) {
		userDao.deleteById(id);
	}
	
	@Autowired
    private EntityManager entityManager;

    @Transactional
    public void deleteUserByNickname(String nickname) {
        Query query = entityManager.createQuery("DELETE FROM User u WHERE u.nickname = :nickname");
        query.setParameter("nickname", nickname);
        query.executeUpdate();
    }
	
	
	
}
