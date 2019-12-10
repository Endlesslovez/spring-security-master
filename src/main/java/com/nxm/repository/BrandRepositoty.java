package com.nxm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nxm.model.Brand;


@Repository
public interface BrandRepositoty  extends JpaRepository<Brand, Long>{
	
	
}
