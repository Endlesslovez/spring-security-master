package com.nxm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.nxm.model.StockTotalDetail;

public interface StockTotalDetailRepository extends JpaRepository<StockTotalDetail, Long> {
	
	
}
