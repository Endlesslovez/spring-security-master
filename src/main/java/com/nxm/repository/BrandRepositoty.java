package com.nxm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nxm.model.Brand;


@Repository
public interface BrandRepositoty  extends JpaRepository<Brand, Long>{
	
	final String SELECT_BY_ID = "SELECT b FROM tbl_brand b WHERE b.id=:id";
	
	@Query(SELECT_BY_ID)
	public Brand findByBrandById(@Param("id") Long id);
}
