package com.nxm.service;

import com.nxm.model.Product;

public interface ProductService  {
	
	public Product create(Product product);
	
	public boolean edit(long id);

}
