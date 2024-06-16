package com.example.dev.dto;

import com.example.dev.annotation.ExistInDB;
import com.example.dev.model.UserCt;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserDto(
		@NotBlank
		@ExistInDB(message = "Usuario no disponible")
		@Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]*$", message = "Nombre de usuario debe empezar por una letra")
        @Size(min = 4, max = 16, message = "Nombre de usuario debe tener entre 4 y 16 carácteres")
        String nickname,

		@NotBlank
		@Pattern(regexp = "^[a-zA-Z]+( [a-zA-Z]+)*$", message = "El nombre solo puede contener letras")
		@Size(max = 16, message = "El nombre no puede tener más de 20 dígitos")
		String firstname,

		@NotBlank
		@Pattern(regexp = "^[a-zA-Z]+( [a-zA-Z]+)*$", message = "Los apellidos solo pueden contener letras")
		@Size(max = 30, message = "Los apellidos no pueden tener más de 30 dígitos")
		String lastname,

		@NotBlank
		@ExistInDB(message = "Correo electrónico ya registrado")
		@Email(message = "Correo electrónico debe tener un formato correcto")
		String mail,

		@NotBlank
//		@Pattern(regexp = "")
		String password
) {

	public UserCt toEntity() {
		UserCt user = new UserCt();

		user.setNickname(this.nickname);
		user.setFirstname(this.firstname);
		user.setLastname(this.lastname);
		user.setMail(this.mail);
		user.setPassword(this.password);

		return user;
	}

}
