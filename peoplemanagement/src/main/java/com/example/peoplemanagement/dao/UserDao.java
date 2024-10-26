package com.example.peoplemanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.peoplemanagement.model.UserInformation;


@Repository
public interface UserDao extends JpaRepository<UserInformation, Long> {
	
	UserInformation findByEmail(String email);

}
