package com.example.dev.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.dev.model.CreditHistory;
import com.example.dev.model.ErrorResponse;
import com.example.dev.model.User;
import com.example.dev.service.CreditHistoryService;
import com.example.dev.service.IUserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

	@Autowired
	IUserService userService;
	
	@Autowired
	private CreditHistoryService creditHistoryService;
	
	@GetMapping("/user/")
	public ResponseEntity<?> listUsers() {
		List<User> users = userService.findAll();
		
		if (!users.isEmpty()) {
			return ResponseEntity.ok(users);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: There's no users in the database");
		}
	}
	
	@GetMapping("/user/id/{id}")
	public ResponseEntity<?> findById(@PathVariable int id){
		Optional<User> users = userService.findById(id);
		
		if (!users.isEmpty()) {
	        return ResponseEntity.ok(users);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No user matching with that ID were found in the database");
	    }
	}
	
	@GetMapping("/user/nickname/{nickname}")
	public ResponseEntity<?> findUserByNickname(@PathVariable String nickname) {
		List<User> users = userService.findUserByNickname(nickname);

	    if (!users.isEmpty()) {
	        return ResponseEntity.ok(users);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No users matching that firstname were found in the database");
	    }
	}
	
	@GetMapping("/user/firstname/{firstname}")
	public ResponseEntity<?> findUserByFirstName(@PathVariable String firstname) {
		List<User> users = userService.findUserByFirstName(firstname);

	    if (!users.isEmpty()) {
	        return ResponseEntity.ok(users);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No users matching that firstname were found in the database");
	    }
	}
	
	@GetMapping("/user/lastname/{lastname}")
	public ResponseEntity<?> findUserByLastName(@PathVariable String lastname) {
		List<User> users = userService.findUserByLastName(lastname);

	    if (!users.isEmpty()) {
	        return ResponseEntity.ok(users);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No users matching that lastname were found in the database");
	    }
	}
	
	@GetMapping("/user/mail/{mail}")
	public ResponseEntity<?> findUserByMail(@PathVariable String mail) {
		List<User> users = userService.findUserByMail(mail);
		
		if (!users.isEmpty()) {
	        return ResponseEntity.ok(users);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No users matching that mail were found in the database");
	    }
	}
	
	/*
	@GetMapping("/user/search/{query}")
    public ResponseEntity<?> searchAll(@PathVariable String query) {
        Set<User> result = new HashSet<>();

        try {
            int id = Integer.parseInt(query);
            userService.findById(id).ifPresent(result::add);
        } catch (NumberFormatException ex) {

        }
        String[] parts = query.split("\\s+");
        
        for (int i = 0; i < parts.length; i++) {
            String firstName = String.join(" ", Arrays.copyOfRange(parts, 0, i + 1));
            String lastName = String.join(" ", Arrays.copyOfRange(parts, i + 1, parts.length));
            result.addAll(userService.findByFirstNameAndLastName(firstName, lastName));
        }
        result.addAll(userService.findUserByNickname(query));
        result.addAll(userService.findUserByFirstName(query));
        result.addAll(userService.findUserByLastName(query));
        result.addAll(userService.findUserByMail(query));

        if (!result.isEmpty()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No matching users were found in the database");
        }
    }
	*/
	
	@GetMapping("/user/search/{query}")
    public ResponseEntity<?> searchAll(@PathVariable String query) {
        Set<User> result = new HashSet<>();
        try {
            int id = Integer.parseInt(query);
            userService.findById(id).ifPresent(result::add);
        } catch (NumberFormatException ex) {

        }
        String[] parts = query.split("\\s+");
        if (parts.length > 1) {
            result.addAll(userService.findByFirstNameAndLastName(parts[0], parts[1]));        	
        } else {
        	result.addAll(userService.findByFirstNameContaining(query));
            result.addAll(userService.findByLastNameContaining(query));
            result.addAll(userService.findUserByNickname(query));
            result.addAll(userService.findUserByMail(query));
        }

        if (!result.isEmpty()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No matching users were found in the database");
        }
    }
	
	@PostMapping("/user")
	public ResponseEntity<?> saveUser(@RequestBody User user){
		User result = userService.save(user);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/user/id/{id}")
				.buildAndExpand(result.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PostMapping("/regUser")
	public ResponseEntity<?> regUser(@Valid @RequestBody User user) {
	    try {
	        User savedUser = userService.save(user);
	        return ResponseEntity.ok(savedUser);
	    } catch (DataIntegrityViolationException ex) {
	        Throwable rootCause = ex.getMostSpecificCause();
	        String message = rootCause.getMessage();

	        if (message != null) {
	            if (message.contains("Duplicate entry") && message.contains("for key 'nickname'")) {
	                return ResponseEntity
	                        .status(HttpStatus.BAD_REQUEST)
	                        .body(new ErrorResponse("Error al crear el usuario", "Nombre de usuario no disponible"));
	            } else if (message.contains("Duplicate entry") && message.contains("for key 'mail'")) {
	                return ResponseEntity
	                        .status(HttpStatus.BAD_REQUEST)
	                        .body(new ErrorResponse("Error al crear el usuario", "Correo electrónico ya registrado"));
	            }
	        }
	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ErrorResponse("Error al crear el usuario", "Error interno del servidor"));
	    }
	}
	
	@PutMapping("/editUser/{id}")
	public ResponseEntity<?> editUser(@PathVariable Integer id, @RequestBody User userInsert) {
	    if (userService.nicknameExists(userInsert.getNickname(), id)) {
	        ErrorResponse errorResponse = new ErrorResponse("Conflict", "Nickname '" + userInsert.getNickname() + "' ya está en uso por otro usuario");
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	    }
	    if (userService.emailExists(userInsert.getMail(), id)) {
	        ErrorResponse errorResponse = new ErrorResponse("Conflict", "Mail '" + userInsert.getMail() + "' ya está en uso por otro usuario");
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	    }

	    try {
	        User newUser = new User();
	        newUser.setId(id);
	        newUser.setNickname(userInsert.getNickname());
	        newUser.setFirstName(userInsert.getFirstName());
	        newUser.setLastName(userInsert.getLastName());
	        newUser.setMail(userInsert.getMail());
	        newUser.setCredit(userInsert.getCredit());

	        User updatedUser = userService.save(newUser);
	        return ResponseEntity.ok(updatedUser);
	    } catch (DataIntegrityViolationException e) {
	        ErrorResponse errorResponse = new ErrorResponse("Data Integrity Violation", "Cannot update user due to a data integrity issue.");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    } catch (Exception e) {
	        ErrorResponse errorResponse = new ErrorResponse("Internal Server Error", "An unexpected error occurred.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
	
	
	@PutMapping("/creditUser/{id}")
	public ResponseEntity<?> creditUser(@PathVariable Integer id, @RequestBody User userInsert) {
	    Optional<User> existingUser = userService.findById(id);
	    if (!existingUser.isPresent()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Not Found", "User not found."));
	    }

	    User newUser = existingUser.get();
	    int originalCredit = newUser.getCredit();
	    newUser.setCredit(userInsert.getCredit());

	    try {
	        User updatedUser = userService.save(newUser);
	        CreditHistory creditHistory = new CreditHistory();
	        creditHistory.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
	        creditHistory.setAmount(userInsert.getCredit() - originalCredit);
	        creditHistory.setUser(updatedUser);
	        creditHistory.setNickname(updatedUser.getNickname());
	        creditHistory.setTotalCredit(updatedUser.getCredit());
	        creditHistory.setRecharge("Recarga");

	        creditHistoryService.save(creditHistory);

	        return ResponseEntity.ok(updatedUser);
	    } catch (Exception e) {
	        ErrorResponse errorResponse = new ErrorResponse("Internal Server Error", "An unexpected error occurred.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}

	
	
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable int id) {
		Optional<User> users = userService.findById(id);
		
		if (!users.isEmpty()) {
			userService.deleteUserById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("User with id: "+ id + " deleted");
		} else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Can't delete, there are no user with that id");
		}
	}
	
	@DeleteMapping("/deleteUserByNickname/{nickname}")
	public ResponseEntity<?> deleteUserByNickname(@PathVariable String nickname) {
		List<User> users = userService.findUserByNickname(nickname);
		
		if (!users.isEmpty()) {
			userService.deleteUserByNickname(nickname);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("User with id: "+ nickname + " deleted");
		} else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Can't delete, there are no user with that nickname");
		}
	}
}
