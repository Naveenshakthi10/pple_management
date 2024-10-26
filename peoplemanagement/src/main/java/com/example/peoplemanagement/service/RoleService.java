package com.example.peoplemanagement.service;

import java.util.List;

import com.example.peoplemanagement.model.Role;

public interface RoleService {

	Role saveRole(Role role);

	List<Role> getRoles();

	

	
}
