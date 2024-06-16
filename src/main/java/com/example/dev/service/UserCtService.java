package com.example.dev.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dev.dao.IAddressDao;
import com.example.dev.dao.IMoviesCartDao;
import com.example.dev.dao.IUserCtDao;
import com.example.dev.dto.UserDto;
import com.example.dev.model.Address;
import com.example.dev.model.MoviesCart;
import com.example.dev.model.Rol;
import com.example.dev.model.UserCt;
import com.example.dev.model.UserParameters;
import com.example.dev.model.provinces.Provinces;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Service
public class UserCtService implements IUserCtService {

	@Autowired
	private IUserCtDao userCtDao;

	@Autowired
	private IMoviesCartDao moviesCartDao;

	@Autowired
	private IAddressDao addressDao;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public Page<UserCt> findAll(Pageable pageable) {
        return userCtDao.findAll(pageable);
    }

	@Override
	public Optional<UserCt> findByUserid(Integer userid) {
		return userCtDao.findByUserid(userid);
	}

	@Override
	public boolean isUserCtAdmin(String nickname) {
		UserCt user = userCtDao.findByNickname(nickname);
        if (user != null && user.getRol().getRolid() == 1) {
            return true;
        }
        return false;
	}

	@Override
	public List<UserCt> findUserByNickname(String nickname) {
		return userCtDao.findUserByNickname(nickname);
	}

	@Override
	public UserCt findEspecificUserByNickname(String nickname) {
		return userCtDao.findEspecificUserByNickname(nickname);
	}

	@Override
	public UserDto getUserDtoByNickname(String nickname) {
        UserCt user = userCtDao.findByNickname(nickname);
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setUserid(null);

        return userDto;
    }

	@Override
	public UserCt findUserByNickname2(String nickname) {
		return userCtDao.findUserByNicknameUnique(nickname);
	}

	@Override
	public Integer getUserCreditByNickname(String nickname) {
	    return userCtDao.findUserCreditByNickname(nickname);
	}

	@Override
	public List<UserCt> findUserByFirstname(String firstname) {
		return userCtDao.findUserByFirstname(firstname);
	}

	@Override
	public List<UserCt> findUserByLastname(String lastname) {
		return userCtDao.findUserByLastname(lastname);
	}

	@Override
	public List<UserCt> findUserByMail(String mail) {
		return userCtDao.findUserctByMail(mail);
	}

	@Override
	public List<UserCt> findByFirstnameAndLastname(String firstname, String lastname) {
		return userCtDao.findByFirstNameAndLastName(firstname, lastname);
	}

	@Override
	public List<UserCt> findByFirstnameContaining(String firstname) {
	    return userCtDao.findByFirstnameContaining(firstname);
	}

	@Override
	public List<UserCt> findByLastnameContaining(String lastname) {
	    return userCtDao.findByLastnameContaining(lastname);
	}

	@Override
	public boolean existsByNickname(String nickname) {
        return userCtDao.existsByNickname(nickname);
    }

    @Override
	public boolean existsByMail(String mail) {
        return userCtDao.existsByMail(mail);
    }

//	public UserCt save(UserCt userCt) {
//	    Rol rol = new Rol();
//	    rol.setRolid(2);
//	    userCt.setRol(rol);
//	    userCt.setCredit(0);
//	    String encodedPass = this.encoder.encode(userCt.getPassword());
//	    userCt.setPassword(encodedPass);
//		UserCt savedUser = userCtDao.save(userCt);
//		return savedUser;
//	}

    @Override
	public UserCt save(UserCt userCt) {
	    Rol rol = new Rol();
	    rol.setRolid(2);
	    userCt.setRol(rol);
	    userCt.setCredit(0);
	    String encodedPass = this.encoder.encode(userCt.getPassword());
	    userCt.setPassword(encodedPass);
		UserCt savedUser = userCtDao.save(userCt);

		MoviesCart cart = new MoviesCart();
		    cart.setUser(savedUser);
		    savedUser.setCart(cart);

		    moviesCartDao.save(cart);

		Address address = new Address();
			address.setUser(savedUser);
			Provinces province = Provinces.valueOf("Alicante");
	        address.setProvince(province);
			savedUser.setAddress(address);


			addressDao.save(address);

		    userCtDao.save(savedUser);

		return savedUser;
	}

	@Override
	public UserCt save(UserDto userEditDTO) {
		UserCt userCt = userCtDao.findByUserid(userEditDTO.getUserid()).orElseThrow(() -> new RuntimeException("User not found"));
		userCt.setNickname(userEditDTO.getNickname());
		userCt.setFirstname(userEditDTO.getFirstname());
		userCt.setLastname(userEditDTO.getLastname());
		userCt.setMail(userEditDTO.getMail());
		userCt.setCredit(userEditDTO.getCredit());

	    return userCtDao.save(userCt);
	}

	@Override
	public UserCt register(UserCt userCt) {
		UserCt registeredUser = userCtDao.save(userCt);
		return registeredUser;
	}

	@Override
	@Transactional
    public void deleteUserById(Integer userid) {
        Optional<UserCt> userOpt = userCtDao.findById(userid);

        if (userOpt.isPresent()) {
            UserCt user = userOpt.get();

            MoviesCart cart = user.getCart();
            if (cart != null) {
                cart.getCartDetails().clear();
                moviesCartDao.delete(cart);
            }

            userCtDao.delete(user);
        }
    }

	@Autowired
    private EntityManager entityManager;

    @Override
	@Transactional
    public void deleteUserByNickname(String nickname) {
        Query query = entityManager.createQuery("DELETE FROM User u WHERE u.nickname = :nickname");
        query.setParameter("nickname", nickname);
        query.executeUpdate();
    }

	@Override
	public List<UserCt> getWithParameters(UserParameters params) {
		return this.userCtDao.findWithParameters(params);
	}

	@Override
	public UserCt create(UserCt userToCreate) {
	    Rol rol = new Rol();
	    rol.setRolid(2);
	    userToCreate.setRol(rol);
	    String encodedPass = this.encoder.encode(userToCreate.getPassword());
	    userToCreate.setPassword(encodedPass);
		return this.userCtDao.save(userToCreate);
	}

	@Override
	public UserCt update(UserCt userToUpdate) {
		Integer id = userToUpdate.getUserid();
		UserCt retrievedUser = this.userCtDao
			.findById(id)
			.orElseThrow(); // NoSuchElementException

		String newNickname = Optional.ofNullable(userToUpdate.getNickname())
				.orElse(retrievedUser.getNickname());
		String newFirstName = Optional.ofNullable(userToUpdate.getFirstname())
				.orElse(retrievedUser.getFirstname());
		String newLastName = Optional.ofNullable(userToUpdate.getLastname())
				.orElse(retrievedUser.getLastname());
		String newMail = Optional.ofNullable(userToUpdate.getMail())
				.orElse(retrievedUser.getMail());
		Integer newCredit = Optional.ofNullable(userToUpdate.getCredit())
				.orElse(retrievedUser.getCredit());

		retrievedUser.setNickname(newNickname);
		retrievedUser.setFirstname(newFirstName);
		retrievedUser.setLastname(newLastName);
		retrievedUser.setMail(newMail);
		retrievedUser.setCredit(newCredit);

		return this.userCtDao.save(retrievedUser);
	}

	@Override
	public Integer getUserIdByNickname(String nickname) {
	    List<UserCt> users = userCtDao.findUserByNickname(nickname);
	    if (users.isEmpty()) {
	        throw new NoSuchElementException("Usuario no encontrado");
	    }
	    return users.get(0).getUserid();
	}
}
