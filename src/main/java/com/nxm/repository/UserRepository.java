package com.nxm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.nxm.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	@Query(value = "select u.* from tbl_user u", nativeQuery = true)
	List<User> findAllUser();

	User findByEmail(String email);

	User findByPassword(String password);
}
