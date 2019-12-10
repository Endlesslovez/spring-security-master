package com.nxm.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nxm.model.Brand;
import com.nxm.repository.BrandRepositoty;

@Service
public class BrandServiceImpl implements BrandService{

	
	@Autowired
	private BrandRepositoty repository;
	
	
	@Override
	public List<Brand> getAll() {
	
		return repository.findAll();
	}


	@Override
	public Brand findBrandById(Long id ) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

}
