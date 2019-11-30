package com.nxm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nxm.model.StockTotalDetail;
@Repository
public interface StockTotalDetailRepository extends JpaRepository<StockTotalDetail, Long> {
	
	
}
