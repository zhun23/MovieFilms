package com.example.dev.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dev.dao.IUserCtDao;
import com.example.dev.dto.UserDto;
import com.example.dev.model.UserCt;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Service
public class UserCtService implements IUserCtService {

	@Autowired
	private IUserCtDao userCtDao;
	
	@Override
	public Page<UserCt> findAll(Pageable pageable) {
        return userCtDao.findAll(pageable);
    }
	
	public Optional<UserCt> findByUserid(int userid) {
		return userCtDao.findByUserid(userid);
	}
	
	public List<UserCt> findUserByNickname(String nickname) {
		return userCtDao.findUserByNickname(nickname);
	}
	
	public UserCt findUserByNickname2(String nickname) {
		return userCtDao.findUserByNicknameUnique(nickname);
	}
	
	public List<UserCt> findUserByFirstname(String firstname) {
		return userCtDao.findUserByFirstname(firstname);
	}
	
	public List<UserCt> findUserByLastname(String lastname) {
		return userCtDao.findUserByLastname(lastname);
	}
	
	public List<UserCt> findUserByMail(String mail) {
		return userCtDao.findUserctByMail(mail);
	}
	
	public List<UserCt> findByFirstnameAndLastname(String firstname, String lastname) {
		return userCtDao.findByFirstNameAndLastName(firstname, lastname);
	}
	
	public List<UserCt> findByFirstnameContaining(String firstname) {
	    return userCtDao.findByFirstnameContaining(firstname);
	}
	
	public List<UserCt> findByLastnameContaining(String lastname) {
	    return userCtDao.findByLastnameContaining(lastname);
	}
	
	public boolean existsByNickname(String nickname) {
        return userCtDao.existsByNickname(nickname);
    }

    public boolean existsByMail(String mail) {
        return userCtDao.existsByMail(mail);
    }
	
	public UserCt save(UserCt userCt) {
		UserCt savedUser = userCtDao.save(userCt);
		return savedUser;
	}
	
	public UserCt save(UserDto userEditDTO) {
		UserCt userCt = userCtDao.findByUserid(userEditDTO.getUserid()).orElseThrow(() -> new RuntimeException("User not found"));
		userCt.setNickname(userEditDTO.getNickname());
		userCt.setFirstname(userEditDTO.getFirstName());
		userCt.setLastname(userEditDTO.getLastName());
		userCt.setMail(userEditDTO.getMail());
		userCt.setCredit(userEditDTO.getCredit());

	    return userCtDao.save(userCt);
	}
	
	public UserCt register(UserCt userCt) {
		UserCt registeredUser = userCtDao.save(userCt);
		return registeredUser;
	}
	
	public void deleteUserById(int id) {
		userCtDao.deleteById(id);
	}
	
	@Autowired
    private EntityManager entityManager;

    @Transactional
    public void deleteUserByNickname(String nickname) {
        Query query = entityManager.createQuery("DELETE FROM User u WHERE u.nickname = :nickname");
        query.setParameter("nickname", nickname);
        query.executeUpdate();
    }
    
    //Añadido de autentificación a partir de aquí
    
    /*
    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByNickname(username);
		if(user == null) {
			throw new UsernameNotFoundException("Usuario o password inválidos");
		}
		return new User(user.getNickname(),userCt.getPassword(), mapearAutoridadesRol(userCt.getRol()));
	}

	private Collection<? extends GrantedAuthority> mapearAutoridadesRol(Collection<Rol> roles){
		return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getRolname())).collect(Collectors.toList());
	}
	*/
    
    /*
    @Override
    public boolean nicknameExists(String nickname, Integer id) {
        User user = userDao.findUserByNicknameUnique(nickname);
        if (user != null) {
            return user.getId() != id;
        }
        return false;
    }
    
    public boolean emailExists(String email, Integer id) {
        User user = userDao.findUserByMailUnique(email);
        if (user != null) {
            return user.getId() != id;
        }
        return false;
    }
	*/
}
