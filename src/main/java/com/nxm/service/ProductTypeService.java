package com.nxm.service;

import java.util.List;

import com.nxm.model.ProductType;

public interface ProductTypeService {

	public  List<ProductType> getAll();
	
	public ProductType findProductById(Long id);
}
