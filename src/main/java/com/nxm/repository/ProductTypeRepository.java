package com.nxm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nxm.model.ProductType;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {

	/*final String SELECT_BY_ID = "SELECT b FROM tbl_product_type b WHERE b.id=:id";
	
	@Query(SELECT_BY_ID)
	public ProductType findProductTypeById(@Param("id") Long id);
	*/
}
