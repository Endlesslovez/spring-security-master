package com.nxm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.nxm.model.Brand;
import com.nxm.model.Product;
import com.nxm.model.ProductType;
import com.nxm.model.StockTotal;
import com.nxm.model.StockTotalDetail;

public interface StockTotalDetailService {

	Page<StockTotalDetail> findAll(Pageable pageable);

	List<StockTotalDetail> findByStockTotal(StockTotal id);

	StockTotalDetail findOne(long id);

	public List<StockTotalDetail> findRecord();

	List<StockTotalDetail> findByQuery(String productName, String brandName, String productType);
	List<StockTotalDetail> findAllNow();
}
