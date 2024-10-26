package com.example.peoplemanagement.controller;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.peoplemanagement.Form.LoginForm;
import com.example.peoplemanagement.response.SignInResponse;
import com.example.peoplemanagement.service.SignInService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/people")
@Slf4j
public class SignInController {
	
	@Autowired
    private SignInService signInservice;

        @PostMapping("/signin")
        @Operation(summary = "This API is used to Login")
        public SignInResponse signIn(@RequestBody LoginForm loginForm) throws AuthenticationException {
			return signInservice.login(loginForm);

        }
    }



