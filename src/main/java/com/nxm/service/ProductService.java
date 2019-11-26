package com.nxm.service;

import java.util.List;

import com.nxm.model.Product;

public interface ProductService  {
	
	public boolean create(Product product);
	
	public boolean edit(long id);

	public List<Product> findAll();
}
