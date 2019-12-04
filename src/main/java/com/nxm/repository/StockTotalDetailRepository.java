package com.nxm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nxm.model.StockTotal;
import com.nxm.model.StockTotalDetail;

@Repository
public interface StockTotalDetailRepository extends CrudRepository<StockTotalDetail, Long> {

	Page<StockTotalDetail> findAll(Pageable pageable);

	@Query(value = "select u.* from tbl_stocktotaldetail u where u.col_stocktotal = ?1", nativeQuery = true)
	List<StockTotalDetail> findByStockTotal(StockTotal stockTotal);

	@Query(value = "select u.* from tbl_stocktotaldetail u where u.col_productstatus = 1", nativeQuery = true)
	List<StockTotalDetail> findRecord();
}
