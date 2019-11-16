package com.nxm.repository;

import org.springframework.data.repository.CrudRepository;

import com.nxm.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	
	User findByEmail(String email);
	User findByPassword(String password);
}
