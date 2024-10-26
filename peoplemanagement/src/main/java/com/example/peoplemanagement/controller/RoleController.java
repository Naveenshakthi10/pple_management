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

import com.example.peoplemanagement.model.Role;
import com.example.peoplemanagement.service.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping("/save")
	@Operation(summary = "This API is used to Save Role")
	public Role saveRole(
			@Parameter(name = "Authorization", description = "Access Token", required = true, in = ParameterIn.HEADER) @RequestHeader("Authorization") String authorizationHeader,
			HttpServletRequest request, @RequestBody Role role) {
		 return roleService.saveRole(role);
	}

	@GetMapping("/get")
	@Operation(summary = "This API is used to Get Role")
	public List<Role> getRole(
			@Parameter(name = "Authorization", description = "Access Token", required = true, in = ParameterIn.HEADER) @RequestHeader("Authorization") String authorizationHeader,
			HttpServletRequest request) {
		return roleService.getRoles();
	}

}
