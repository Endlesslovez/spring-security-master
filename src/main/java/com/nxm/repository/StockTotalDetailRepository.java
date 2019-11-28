package com.nxm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nxm.model.StockTotal;
import com.nxm.model.StockTotalDetail;
@Repository
public interface StockTotalDetailRepository extends CrudRepository<StockTotalDetail, Long> {
	
	public Page<StockTotalDetail>  findAll(Pageable pageable);
	public List<StockTotalDetail> findByStockTotalId(Long id);
}
