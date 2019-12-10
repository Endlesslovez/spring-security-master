package com.nxm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nxm.model.ProductType;
import com.nxm.repository.ProductTypeRepository;
@Service
public class ProductTypeServiceImpl implements ProductTypeService {

	@Autowired
	private ProductTypeRepository repositoty;
	
	@Override
	public List<ProductType> getAll() {
	
		return  repositoty.findAll();
	}

	@Override
	public ProductType findProductById(Long id) {

		return 	repositoty.findOne(id);
	}

}
