package com.example.dev.dto;

import lombok.Data;

@Data
public class UserDto {
	private int userid;
	private String nickname;
	private String firstName;
	private String lastName;
	private String mail;
	private int credit;
}
