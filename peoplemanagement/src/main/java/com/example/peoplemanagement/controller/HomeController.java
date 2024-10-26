package com.example.peoplemanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/home")
public class HomeController {
	
	@GetMapping("/get")
	@Operation(summary = "This API is used to verify the controller is working")
	public String getHome() {
		return "Home Controller is Running";
		
	}

}
