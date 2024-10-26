package com.example.peoplemanagement.service;

import javax.naming.AuthenticationException;

import com.example.peoplemanagement.Form.LoginForm;
import com.example.peoplemanagement.response.SignInResponse;

public interface SignInService {


	SignInResponse login(LoginForm loginForm) throws AuthenticationException;

}
