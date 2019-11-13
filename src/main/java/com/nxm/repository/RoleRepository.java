package com.nxm.repository;

import org.springframework.data.repository.CrudRepository;

import com.nxm.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

	Role findByName(String name);
	
}
