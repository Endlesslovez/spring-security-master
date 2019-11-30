package com.nxm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nxm.model.StockTotal;
import com.nxm.model.StockTotalDetail;
import com.nxm.repository.StockTotalDetailRepository;
import com.nxm.repository.StockTotalRepository;

@Service("stockTotalService")
public class StockTotalServiceImpl implements StockTotalService {


	@Autowired
	private StockTotalRepository stockTotalRepository;

	public StockTotal findNow() {
		return stockTotalRepository.findNow();
	}
	
	public StockTotal findAvaiableRecord() {
		return stockTotalRepository.findAvaiableRecord();
	}
}
