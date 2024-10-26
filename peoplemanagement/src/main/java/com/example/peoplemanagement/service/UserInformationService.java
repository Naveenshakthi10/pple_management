package com.example.peoplemanagement.service;

import java.util.List;

import com.example.peoplemanagement.model.UserInformation;

public interface UserInformationService {

	UserInformation saveUser(UserInformation user);

	List<UserInformation> getUser();

}
