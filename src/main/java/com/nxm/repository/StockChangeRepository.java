package com.nxm.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nxm.model.StockChange;
import com.nxm.model.StockTotal;
@Repository
public interface StockChangeRepository extends CrudRepository<StockChange, Long> {
	
}
