package com.example.dev.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserDto {
	
	@Id
	private int id;
	private String nickname;
	private int credit;
	
	public UserDto() {
		
	}
	
	public UserDto(int id, String nickname, int credit) {
		this.id = id;
		this.nickname = nickname;
		this.credit = credit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", nickname=" + nickname + ", credit=" + credit + "]";
	}
}
