package com.example.dev.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.dev.model.Movie;
import com.example.dev.model.User;
import com.example.dev.service.IUserService;

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
	public ResponseEntity<?> findById(@PathVariable int id){
		Optional<User> users = userService.findById(id);
				
		if (!users.isEmpty()) {
	        return ResponseEntity.ok(users);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No user matching with that ID were found in the database");
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
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No users matching that lastname were found in the database");
	    }
	}
	
	
}
