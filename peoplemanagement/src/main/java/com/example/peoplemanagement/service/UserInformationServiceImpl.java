package com.example.peoplemanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.peoplemanagement.dao.UserDao;
import com.example.peoplemanagement.model.UserInformation;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Service
@Slf4j
public class UserInformationServiceImpl implements UserInformationService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserInformation saveUser(UserInformation user) {
		try {
			if(user!=null) {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				userDao.save(user);
			}else {
			}
		}catch (Exception e) {
		}
		return user;
		
	}

	@Override
	public List<UserInformation> getUser() {
		List<UserInformation> userList = new ArrayList<>();
		try {
			userList = userDao.findAll();
			if(userList!=null) {
				return userList;
			}else {
			}
		}catch (Exception e) {
		}
		return userList;
	}


}
