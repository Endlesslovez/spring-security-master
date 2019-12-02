package com.nxm.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nxm.model.StockTotal;
@Repository
public interface StockTotalRepository extends CrudRepository<StockTotal, Long> {
	
	@Query(value ="SELECT u.* FROM tbl_stocktotal u WHERE u.col_status = 2",nativeQuery = true)
	public StockTotal  findNow();
	@Query(value ="SELECT u.* FROM tbl_stocktotal u WHERE u.col_status = 1",nativeQuery = true)
	public StockTotal  findAvaiableRecord();
}
