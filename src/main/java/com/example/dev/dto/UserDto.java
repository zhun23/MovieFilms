package com.example.dev.dto;

public class UserDto {
	private Integer userid;
	private String nickname;
	private String firstname;
	private String lastname;
	private String mail;
	private Integer credit;

	public UserDto() {
		super();
	}

	public UserDto(Integer userid, String nickname, String firstname, String lastname, String mail, Integer credit) {
		super();
		this.userid = userid;
		this.nickname = nickname;
		this.firstname = firstname;
		this.lastname = lastname;
		this.mail = mail;
		this.credit = credit;
	}

	public UserDto(String nickname, String firstname, String lastname, String mail, Integer credit) {
		super();
		this.nickname = nickname;
		this.firstname = firstname;
		this.lastname = lastname;
		this.mail = mail;
		this.credit = credit;
	}

	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Integer getCredit() {
		return credit;
	}
	public void setCredit(Integer credit) {
		this.credit = credit;
	}
}
