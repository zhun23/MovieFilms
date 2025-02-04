package com.example.dev.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.dev.dao.IMoviesCartDao;
import com.example.dev.dto.UserDto;
import com.example.dev.model.MoviesCart;
import com.example.dev.model.UserCt;
import com.example.dev.model.UserParameters;
import com.example.dev.model.mapper.UserMapper;
import com.example.dev.service.IUserCtService;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

	@Autowired
	private IUserCtService userCtService;

	@Autowired
	private IMoviesCartDao moviesCartDao;

	@GetMapping("/listuser")
    public ResponseEntity<?> listUsers(@PageableDefault(size = 24) Pageable pageable) {
        Page<UserCt> users = userCtService.findAll(pageable);

        if (users.hasContent()) {

        	List<UserCt> filteredUsers = users.getContent()
                    .stream()
                    .filter(user -> user.getRol().getRolid() != 1)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("users", filteredUsers);
            response.put("currentPage", users.getNumber());
            response.put("totalItems", users.getTotalElements());
            response.put("totalPages", users.getTotalPages());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay usuarios en la BBDD");
        }
    }

	@GetMapping("/user/userid/{id}")
	public ResponseEntity<?> findByUserid(@PathVariable Integer id){
		Optional<UserCt> users = userCtService.findByUserid(id);

		if (!users.isEmpty()) {
	        return ResponseEntity.ok(users);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No user matching with that ID were found in the database");
	    }
	}

	@GetMapping("/isAdmin/{nickname}")
    public boolean isUserAdmin(@PathVariable String nickname) {
        return userCtService.isUserCtAdmin(nickname);
    }

	@GetMapping("/user/nickname/{nickname}")
	public ResponseEntity<?> findUserByNickname(@PathVariable String nickname) {
		List<UserCt> users = userCtService.findUserByNickname(nickname);

	    if (!users.isEmpty()) {
	        return ResponseEntity.ok(users);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No users matching that firstname were found in the database");
	    }
	}

	@GetMapping("/nickname/{nickname}/id")
	public ResponseEntity<?> getUserIdByNickname(@PathVariable String nickname) {
	    try {
	        Integer userId = userCtService.getUserIdByNickname(nickname);
	        return ResponseEntity.ok(userId);
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No user with that nickname was found in the database");
	    }
	}

	@GetMapping("/user/nicknameDto/{nickname}")
    public ResponseEntity<UserDto> getUserDtoByNickname(@PathVariable String nickname) {
        UserDto userDto = userCtService.getUserDtoByNickname(nickname);
        if (userDto != null) {
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

	@GetMapping("/UserCredit/{nickname}")
	public ResponseEntity<Map<String, Integer>> getUserCredit(@PathVariable String nickname) {
	    int credit = userCtService.getUserCreditByNickname(nickname);
	    Map<String, Integer> response = new HashMap<>();
	    response.put("credit", credit);

	    return ResponseEntity.ok(response);
	}

	@GetMapping("/user/firstname/{firstname}")
	public ResponseEntity<?> findUserByFirstName(@PathVariable String firstname) {
		List<UserCt> users = userCtService.findUserByFirstname(firstname);

	    if (!users.isEmpty()) {
	        return ResponseEntity.ok(users);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No users matching that firstname were found in the database");
	    }
	}

	@GetMapping("/user/lastname/{lastname}")
	public ResponseEntity<?> findUserByLastName(@PathVariable String lastname) {
		List<UserCt> users = userCtService.findUserByLastname(lastname);

	    if (!users.isEmpty()) {
	        return ResponseEntity.ok(users);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No users matching that lastname were found in the database");
	    }
	}

	@GetMapping("/user/mail/{mail}")
	public ResponseEntity<?> findUserByMail(@PathVariable String mail) {
		List<UserCt> users = userCtService.findUserByMail(mail);

		if (!users.isEmpty()) {
	        return ResponseEntity.ok(users);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No users matching that mail were found in the database");
	    }
	}

	@GetMapping("/search")
	public ResponseEntity<List<UserCt>> searchWithParams(UserParameters params) {
		List<UserCt> userList = this.userCtService.getWithParameters(params);
		return ResponseEntity.ok(userList);
	}

	@GetMapping("/searchu/{query}")
    public ResponseEntity<?> searchAll(@PathVariable String query) {
        Set<UserCt> result = new HashSet<>();
        try {
        	int id = Integer.parseInt(query);
            userCtService.findByUserid(id).ifPresent(result::add);
        } catch (NumberFormatException ex) {

        }
        String[] parts = query.split("\\s+");
        if (parts.length > 1) {
            result.addAll(userCtService.findByFirstnameAndLastname(parts[0], parts[1]));
        } else {
        	result.addAll(userCtService.findByFirstnameContaining(query));
            result.addAll(userCtService.findByLastnameContaining(query));
            result.addAll(userCtService.findUserByNickname(query));
            result.addAll(userCtService.findUserByMail(query));
        }

        if (!result.isEmpty()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No matching users were found in the database");
        }
    }

	@PostMapping("/user")
	public ResponseEntity<?> saveUser(@RequestBody UserCt userCt){
		UserCt result = userCtService.save(userCt);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/user/id/{id}")
				.buildAndExpand(result.getUserid())
				.toUri();
		Map<String, Object> response = new HashMap<>();
        response.put("message", "User created successfully");
        response.put("id", result.getUserid());
        return ResponseEntity.created(location).body(response);
	}

//	@PostMapping("/checkDuplicate")
//	public ResponseEntity<?> checkDuplicate(@RequestParam String nickname, @RequestParam String mail) {
//	    boolean existsNickname = userCtService.existsByNickname(nickname);
//	    boolean existsMail = userCtService.existsByMail(mail);
//
//	    Map<String, Boolean> response = new HashMap<>();
//	    response.put("existsNickname", existsNickname);
//	    response.put("existsMail", existsMail);
//
//	    return ResponseEntity.ok(response);
//	}

	@PutMapping("/editUser/{id}")
	public ResponseEntity<?> editUser(@PathVariable Integer id, @RequestBody UserDto userDto) {
		userDto.setUserid(id);
		UserCt userToUpdate = UserMapper.toEntity(userDto);
		UserCt userUpdated = this.userCtService.update(userToUpdate);
		return ResponseEntity.ok(userUpdated);
	}

//	@PutMapping("/creditUser/{id}")
//	public ResponseEntity<?> creditUser(@PathVariable Integer id, @RequestBody UserCt userInsert) {
//	    Optional<UserCt> existingUser = userCtService.findByUserid(id);
//	    if (!existingUser.isPresent()) {
//	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Not Found", "User not found."));
//	    }
//
//	    UserCt newUser = existingUser.get();
//	    int originalCredit = newUser.getCredit();
//	    newUser.setCredit(userInsert.getCredit());
//
//	    try {
//	    	UserCt updatedUser = userCtService.save(newUser);
//	    	CreditHistory creditHistory = new CreditHistory();
//	    	creditHistory.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
//	    	int amount = userInsert.getCredit() - originalCredit;
//	    	creditHistory.setAmount(amount);
//	    	creditHistory.setUserCt(updatedUser);
//	    	creditHistory.setUsernickname(updatedUser.getNickname());
//	    	creditHistory.setTotalCredit(updatedUser.getCredit());
//
//	    	if (amount > 0) {
//	    	    creditHistory.setAction("Recarga");
//	    	    creditHistory.setBuy(false);
//	    	} else {
//	    	    creditHistory.setAction("Ajuste");
//	    	    creditHistory.setBuy(false);
//	    	}
//
//	    	creditHistoryService.save(creditHistory);
//
//	        return ResponseEntity.ok(updatedUser);
//	    } catch (Exception e) {
//	        ErrorResponse errorResponse = new ErrorResponse("Internal Server Error", "An unexpected error occurred.");
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//	    }
//	}

	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable Integer id) {
		Optional<UserCt> users = userCtService.findByUserid(id);

		if (!users.isEmpty()) {
			userCtService.deleteUserById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("User with id: "+ id + " deleted");
		} else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Can't delete, there are no user with that id");
		}
	}

	@DeleteMapping("/deleteUserByNickname/{nickname}")
	public ResponseEntity<?> deleteUserByNickname(@PathVariable String nickname) {
		List<UserCt> users = userCtService.findUserByNickname(nickname);

		if (!users.isEmpty()) {
			userCtService.deleteUserByNickname(nickname);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("User with id: "+ nickname + " deleted");
		} else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Can't delete, there are no user with that nickname");
		}
	}

	@PutMapping("/emptyCart/{nickname}")
    public ResponseEntity<String> emptyCart(@PathVariable String nickname) {
        UserCt user = userCtService.findUserNickname(nickname);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        MoviesCart moviesCart = moviesCartDao.findByUser(user);

        if (moviesCart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
        }

        moviesCart.getCartDetails().clear();

        moviesCartDao.save(moviesCart);

        return ResponseEntity.ok("Cart emptied successfully");
    }
}
