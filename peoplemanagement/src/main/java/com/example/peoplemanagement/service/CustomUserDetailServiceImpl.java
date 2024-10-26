package com.example.peoplemanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.peoplemanagement.dao.UserDao;
import com.example.peoplemanagement.model.UserInformation;

@Service
public class CustomUserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInformation userInformation = userDao.findByEmail(username);
        if (userInformation == null) {
            throw new UsernameNotFoundException(username);
        }
        UserDetails user = User.withUsername(userInformation.getEmail()).password(userInformation.getPassword()).authorities("USER").build();
        return user;
    }


}
