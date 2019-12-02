package com.nxm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nxm.model.StockTotal;
import com.nxm.model.StockTotalDetail;
import com.nxm.repository.StockTotalDetailRepository;
import com.nxm.repository.StockTotalRepository;

@Service("stockTotalDetailService")
public class StockTotalDetailServiceImpl implements StockTotalDetailService {

	@Autowired
	private StockTotalDetailRepository stockTotalDetailRepository;

	public Page<StockTotalDetail> findAll(Pageable pageable) {
		return stockTotalDetailRepository.findAll(pageable);
	}

	@Override
	public List<StockTotalDetail> findByStockTotal(StockTotal id) {
		// TODO Auto-generated method stub
		return stockTotalDetailRepository.findByStockTotal(id);
	}

	@Override
	public StockTotalDetail findOne(long id) {
		// TODO Auto-generated method stub
		return stockTotalDetailRepository.findOne(id);
	}
}
