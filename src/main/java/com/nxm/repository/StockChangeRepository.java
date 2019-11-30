package com.nxm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nxm.model.StockChange;
@Repository(value = "stockChangeRepository")
public interface StockChangeRepository extends CrudRepository<StockChange, Long> {
	
	@Query(value ="SELECT u.* FROM tbl_stockchange u WHERE u.stock_total_id = ?1",nativeQuery = true)
	List<StockChange> findByStockTotalId(Long stock_total_id);
}
