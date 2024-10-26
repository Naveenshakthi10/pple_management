package com.example.peoplemanagement.service;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.peoplemanagement.Form.LoginForm;
import com.example.peoplemanagement.dao.UserDao;
import com.example.peoplemanagement.model.UserInformation;
import com.example.peoplemanagement.response.SignInResponse;
import com.example.peoplemanagement.utils.GenerateTokenUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SignInServiceImpl implements SignInService {

	@Autowired
    private UserDao userRepository;
	@Autowired
	private GenerateTokenUtil generateJwtTokenUtil;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public SignInResponse login(LoginForm loginForm) throws AuthenticationException {
		SignInResponse signinResponse = new SignInResponse();
		try {
			UserInformation userEntity = userRepository.findByEmail(loginForm.getEmail());
			boolean isMatch = bCryptPasswordEncoder.matches(loginForm.getPassword(), userEntity.getPassword());
            if (isMatch) {
            	signinResponse.setEmail(userEntity.getEmail());
	            signinResponse.setUserName(userEntity.getName());
	            signinResponse.setToken(generateJwtTokenUtil.generateJwtToken(userEntity));
            } else {
            }
		}catch (Exception e) {
		}
		return signinResponse;
		
		 
	}


}
