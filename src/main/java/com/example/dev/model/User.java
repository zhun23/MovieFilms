package com.example.dev.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.dev.utilities.References;

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
	
	@NotBlank(message = "El apodo no puede estar vacío")
	@Size(min=4, max=12, message="El nombre debe tener entre 4 y 12 carácteres")
    @Pattern(regexp="^[a-zA-Z0-9]+$", message="El Apodo solo puede contener letras y números")
	@Column(name="nickname", unique= true)
	private String nickname;
	
	@NotBlank(message = "El nombre no puede estar vacío")
	@Size(min=2, max=12, message="El nombre debe tener entre 2 y 12 carácteres")
	@Pattern(regexp="^[a-zA-Z]+(?:\\s[a-zA-Z]+)*$", message="El Nombre solo puede contener letras y espacios entre palabras")
	@Column(name="first_name")
	private String firstName;
	
	@NotBlank(message = "El apellido no puede estar vacío")
	@Size(min=2, max=12, message="El apellido debe tener entre 4 y 40 carácteres")
	@Pattern(regexp="^[a-zA-Z]+(?:\\s[a-zA-Z]+)*$", message="El Apellido solo puede contener letras y espacios entre palabras")
	@Column(name="last_name")
	private String lastName;
	
	@NonNull
    @NotBlank(message = "El campo de correo electrónico no puede estar vacío.")
    @Email(message = "El correo electrónico debe ser válido.")
    @Column(name="mail", unique = true)
    private String mail;
	
	@NotBlank(message = "El campo de contraseña no puede estar vacío.")
    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[A-Za-z\\d\\W_]{8,}$", 
             message="La contraseña debe tener al menos 8 caracteres, incluir una letra mayúscula, una letra minúscula, un número y un carácter especial.")
    @Column(name="password")
	private String password;
	
	@Column(name="credit")
	private int credit;
}
