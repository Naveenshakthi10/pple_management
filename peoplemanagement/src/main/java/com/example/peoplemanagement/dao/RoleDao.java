package com.example.peoplemanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.peoplemanagement.model.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {


}
