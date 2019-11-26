package com.nxm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nxm.model.Brand;
import com.nxm.model.Product;
import com.nxm.repository.BrandRepositoty;
import com.nxm.repository.ProductRepository;
import com.nxm.repository.ProductTypeRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository respository;

	@Autowired
	public BrandRepositoty brandRepository;

	@Autowired
	public ProductTypeRepository typeRepository;

	@Override
	public boolean create(Product product) {
		if (product != null) {
			respository.save(product);
			return true;
		}
		return false;
	}

	@Override
	public boolean edit(long id) {

		return false;
	}

	@Override
	public List<Product> findAll() {
		
		return respository.findAll();
	}

}
