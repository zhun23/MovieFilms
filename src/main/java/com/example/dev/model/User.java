package com.example.dev.model;

import com.example.dev.utilities.References;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Table(name=References.USER_TABLE_NAME)
@Entity
@Data
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@NonNull
	@Column(name="nickname")
	private String nickname;
	
	@NonNull
	@Column(name="first_name")
	private String firstName;
	
	@NonNull
	@Column(name="last_name")
	private String lastName;
	
	@NonNull
	@Column(name="mail")
	private String mail;
	
	@NonNull
	@Column(name="password")
	private String password;
	
	@Column(name="credit")
	private int credit;
}
