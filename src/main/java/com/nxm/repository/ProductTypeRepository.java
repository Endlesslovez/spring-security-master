package com.nxm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nxm.model.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {

}
