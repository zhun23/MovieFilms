package com.example.dev.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.example.dev.dao.UserDao;
import com.example.dev.exceptions.UserEmailExistsException;
import com.example.dev.exceptions.UserNicknameExistsException;
import com.example.dev.model.ErrorResponse;
import com.example.dev.model.User;
import com.example.dev.service.IUserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

	@Autowired IUserService userService;
	
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
	public ResponseEntity<?> findById(@PathVariable int id) {
	    User user = userService.findById(id);
	    
	    if (user != null) {
	        return ResponseEntity.ok(user);
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
	//NO BORRAR - FALTA GESTIONARLO EN EL CONTROLLER!
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
    */
	
	@PostMapping("/user")
	public ResponseEntity<?> saveUser(@RequestBody User user) {
	    List<String> errors = new ArrayList<>();
	    if (user.getNickname() == null || user.getNickname().trim().isEmpty()) {
	        errors.add("El apodo no puede estar vacío.");
	    }
	    if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
	        errors.add("El nombre no puede estar vacío.");
	    }
	    if (user.getLastName() == null || user.getLastName().trim().isEmpty()) {
	        errors.add("El apellido no puede estar vacío.");
	    }
	    if (user.getMail() == null || user.getMail().trim().isEmpty()) {
	        errors.add("El email no puede estar vacío.");
	    }
	    if (user.getCredit() < 0) {
	        errors.add("El crédito no puede ser negativo.");
	    }

	    if (!errors.isEmpty()) {
	        return ResponseEntity.badRequest().body(new ErrorResponse("Validation Error", String.join(", ", errors)));
	    }

	    try {
	        User result = userService.save(user);
	        URI location = ServletUriComponentsBuilder
	                .fromCurrentRequest().path("/user/id/{id}")
	                .buildAndExpand(result.getId())
	                .toUri();
	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "User created successfully");
	        response.put("id", result.getId());
	        return ResponseEntity.created(location).body(response);
	    } catch (DataIntegrityViolationException ex) {
	        ErrorResponse errorResponse = new ErrorResponse("Error al crear el usuario", "Ya existe un usuario con esos datos");
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	    } catch (Exception ex) {
	        ErrorResponse errorResponse = new ErrorResponse("Internal Server Error", "Se produjo un error mientras se guardaba el usuario.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
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
	    try {
	        User updatedUser = userService.update(userInsert);
	        return ResponseEntity.ok(updatedUser);
	    } catch (UserEmailExistsException | UserNicknameExistsException ex) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Conflict", ex.getMessage()));
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("P-500", "Apodo o email ya en uso"));
	    }
	}




	
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable int id) {
	    User user = userService.findById(id);
	    
	    if (user != null) {
	        userService.deleteUserById(id);
	        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User with id: " + id + " deleted");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Can't delete, there are no user with that id");
	    }
	}

	
	@DeleteMapping("/deleteUserByNickname/{nickname}")
	public ResponseEntity<?> deleteUserByNickname(@PathVariable String nickname) {
		List<User> users = userService.findUserByNickname(nickname);
		
		if (!users.isEmpty()) {
			userService.deleteUserByNickname(nickname);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("User with nickname: "+ nickname + " deleted");
		} else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Can't delete, there are no user with that nickname");
		}
	}
}
