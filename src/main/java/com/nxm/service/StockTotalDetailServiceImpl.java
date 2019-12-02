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

	@Override
	public Page<StockTotalDetail> findAll(Pageable pageable){
		 return stockTotalDetailRepository.findAll(pageable);
	 }

	@Override
	public StockTotalDetail findOne(Long id) {
		// TODO Auto-generated method stub
		return stockTotalDetailRepository.findOne(id);
	}

	@Override
	public List<StockTotalDetail> findByStockTotalId(Long id) {
		// TODO Auto-generated method stub
		return stockTotalDetailRepository.findByStockTotalId(id);
	}
}
