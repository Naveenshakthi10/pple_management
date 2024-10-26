package com.example.peoplemanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.peoplemanagement.dao.RoleDao;
import com.example.peoplemanagement.model.Role;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDao roleDao;

	@Override
	public Role saveRole(Role role) {
		try {
			return roleDao.save(role);
		}catch (Exception e) {
		}
		return role;
		
	}

	@Override
	public List<Role> getRoles() {
		return roleDao.findAll();
	}

	

}
