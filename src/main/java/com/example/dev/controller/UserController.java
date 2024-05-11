package com.example.dev.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.dev.dto.UserDto;
import com.example.dev.model.CreditHistory;
import com.example.dev.model.ErrorResponse;
import com.example.dev.model.Rol;
import com.example.dev.model.UserCt;
import com.example.dev.service.CreditHistoryService;
import com.example.dev.service.IUserCtService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	IUserCtService userCtService;
	
	@Autowired
	CreditHistoryService creditHistoryService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/listuser")
    public ResponseEntity<?> listUsers(@PageableDefault(size = 24) Pageable pageable) {
        Page<UserCt> users = userCtService.findAll(pageable);

        if (users.hasContent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("users", users.getContent());
            response.put("currentPage", users.getNumber());
            response.put("totalItems", users.getTotalElements());
            response.put("totalPages", users.getTotalPages());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: There's no users in the database");
        }
    }
	
	@GetMapping("/user/userid/{id}")
	public ResponseEntity<?> findByUserid(@PathVariable int id){
		Optional<UserCt> users = userCtService.findByUserid(id);
		
		if (!users.isEmpty()) {
	        return ResponseEntity.ok(users);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No user matching with that ID were found in the database");
	    }
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
	
	@GetMapping("/user/search/{query}")
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
	
	@PostMapping("/checkDuplicate")
	public ResponseEntity<?> checkDuplicate(@RequestParam String nickname, @RequestParam String mail) {
	    boolean existsNickname = userCtService.existsByNickname(nickname);
	    boolean existsMail = userCtService.existsByMail(mail);

	    Map<String, Boolean> response = new HashMap<>();
	    response.put("existsNickname", existsNickname);
	    response.put("existsMail", existsMail);

	    return ResponseEntity.ok(response);
	}

	@PostMapping("/regUser")
	public ResponseEntity<?> regUser(@RequestBody UserCt userCt) {
//	    if (userCtService.existsByNickname(userCt.getNickname())) {
//	        return ResponseEntity
//	                .status(HttpStatus.BAD_REQUEST)
//	                .body(new ErrorResponse("Error al crear el usuario", "Nombre de usuario no disponible"));
//	    }
//	    if (userCtService.existsByMail(userCt.getMail())) {
//	        return ResponseEntity
//	                .status(HttpStatus.BAD_REQUEST)
//	                .body(new ErrorResponse("Error al crear el usuario", "Correo electrónico ya registrado"));
//	    }
//	    userCt.setPassword(passwordEncoder.encode(userCt.getPassword()));
//
//	    if (userCt.getRol() == null || userCt.getRol().isEmpty()) {
//	    	userCt.setRol("User");
//	    }
//	    
//	    UserCt savedUser = userCtService.save(userCt);
//	    return ResponseEntity.ok(savedUser);
	    return ResponseEntity.ok().build();
	}


	
	@PutMapping("/editUser/{id}")
	public ResponseEntity<?> editUser(@PathVariable int id, @RequestBody UserDto userDto) {
	    Optional<UserCt> optionalUser = userCtService.findByUserid(id);
	    
	    if (optionalUser.isPresent()) {
	    	UserCt existingUser = optionalUser.get();
	        
	        existingUser.setNickname(userDto.getNickname());
	        existingUser.setFirstname(userDto.getFirstName());
	        existingUser.setLastname(userDto.getLastName());
	        existingUser.setMail(userDto.getMail());
	        existingUser.setCredit(userDto.getCredit());

	        UserCt updatedUser = userCtService.save(existingUser);

	        return ResponseEntity.ok(updatedUser);
	    } else {
	        ErrorResponse errorResponse = new ErrorResponse("Usuario no encontrado", "No se encontró ningún usuario con el ID: " + id);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	    }
	}


	
	
	@PutMapping("/creditUser/{id}")
	public ResponseEntity<?> creditUser(@PathVariable Integer id, @RequestBody UserCt userInsert) {
	    Optional<UserCt> existingUser = userCtService.findByUserid(id);
	    if (!existingUser.isPresent()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Not Found", "User not found."));
	    }

	    UserCt newUser = existingUser.get();
	    int originalCredit = newUser.getCredit();
	    newUser.setCredit(userInsert.getCredit());

	    try {
	        UserCt updatedUser = userCtService.save(newUser);
	        CreditHistory creditHistory = new CreditHistory();
	        creditHistory.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
	        creditHistory.setAmount(userInsert.getCredit() - originalCredit);
	        creditHistory.setUserCt(updatedUser);
	        creditHistory.setUsernickname(updatedUser.getNickname());
	        creditHistory.setTotalCredit(updatedUser.getCredit());
	        creditHistory.setRecharge("Recarga");
	        creditHistory.setRent(0);

	        creditHistoryService.save(creditHistory);

	        return ResponseEntity.ok(updatedUser);
	    } catch (Exception e) {
	        ErrorResponse errorResponse = new ErrorResponse("Internal Server Error", "An unexpected error occurred.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}

	
	
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable int id) {
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
}
