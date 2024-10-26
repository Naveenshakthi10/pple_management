package com.example.peoplemanagement.service;

import com.example.peoplemanagement.model.UserInformation;

public interface GenerateTokenService {

	String generateJwtToken(UserInformation userDetails);

}
