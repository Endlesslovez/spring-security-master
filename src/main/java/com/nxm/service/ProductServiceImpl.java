package com.nxm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nxm.model.Product;
import com.nxm.repository.ProductRepository;

@Service
public class ProductServiceImpl  implements ProductService{

	@Autowired
	private ProductRepository respository;
	
	@Override
	public Product create(Product product) {
	Product product2= respository.save(product);
		return product2;
	}

	@Override
	public boolean edit(long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
