package com.example.peoplemanagement.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.peoplemanagement.model.UserInformation;
import com.example.peoplemanagement.service.UserInformationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	private UserInformationService userService;

	@PostMapping("/save")
	@Operation(summary = "This API is used to save users")
	public UserInformation saveUser(
			@Parameter(name = "Authorization", description = "Access Token", required = true, in = ParameterIn.HEADER) @RequestHeader("Authorization") String authorizationHeader,
			HttpServletRequest request, @RequestBody UserInformation user) {
		return userService.saveUser(user);
	}

	@GetMapping("/get")
	@Operation(summary = "This API is used to Get Users")
	public List<UserInformation> getUser(
			@Parameter(name = "Authorization", description = "Access Token", required = true, in = ParameterIn.HEADER) @RequestHeader("Authorization") String authorizationHeader,
			HttpServletRequest request) {
		return userService.getUser();
	}

}
