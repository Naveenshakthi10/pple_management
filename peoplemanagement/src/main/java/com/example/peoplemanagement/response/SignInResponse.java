package com.example.peoplemanagement.response;

import lombok.Getter;
import lombok.Setter;

public class SignInResponse {
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	private String userName;
	private String email;
	private String token;

}
