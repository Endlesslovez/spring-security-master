package com.nxm.service;


import java.util.List;

import com.nxm.model.Brand;



public interface BrandService {

	public List<Brand> getAll();
	
	public Brand findBrandById(Long id);
}
