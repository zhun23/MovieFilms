package com.example.dev.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dev.dao.UserDao;
import com.example.dev.exceptions.UserEmailExistsException;
import com.example.dev.exceptions.UserNicknameExistsException;
import com.example.dev.model.User;

import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements IUserService {

	private final UserDao userDao;
	
	@Autowired
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return userDao.listUsers();
	}
	
	@Transactional(readOnly = true)
	public User findById(int id) {
		return userDao.findById(id);
	}
	
	@Transactional
	public List<User> findUserByNickname(String nickname) {
		return userDao.findUserByNickname(nickname);
	}
	
	@Transactional
	public List<User> findUserByFirstName(String firstname) {
		return userDao.findUserByFirstName(firstname);
	}
	
	@Transactional
	public List<User> findUserByLastName(String lastname) {
		return userDao.findUserByLastName(lastname);
	}
	
	@Transactional
	public List<User> findUserByMail(String mail) {
		return userDao.findUserByMail(mail);
	}
	
	@Transactional
	public User save(User user) {
		return userDao.save(user);
	}
	
	@Transactional
	public User register(User user) {
		User registeredUser = userDao.save(user);
		return registeredUser;
	}
	
	@Transactional
	public void deleteUserById(int id) {
		userDao.deleteUserById(id);
	}
	
    @Transactional
    public void deleteUserByNickname(String nickname) {
        userDao.deleteUserByNickname(nickname);
    }
    
    @Transactional
    public User update(User user) throws UserEmailExistsException, UserNicknameExistsException {
        try {
            return userDao.update(user);
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el usuario debido a un error en la base de datos", e);
        }
    }
}

	//@Autowired
	//private EntityManager entityManager;
	
	//public List<User> findByFirstNameAndLastName(String firstName, String lastName) {
	//	return userDao.findByFirstNameAndLastName(firstName, lastName);
	//}
	
	//public List<User> findByFirstNameContaining(String firstName) {
	//    return userDao.findByFirstNameContaining(firstName);
	//}
	
	//public List<User> findByLastNameContaining(String lastName) {
	//    return userDao.findByLastNameContaining(lastName);
	//}
