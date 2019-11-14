package com.nxm.repository;

import org.springframework.data.repository.CrudRepository;

import com.nxm.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

	
}
