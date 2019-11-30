package com.nxm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nxm.model.StockTotalDetail;
import com.nxm.repository.StockTotalDetailRepository;

@Service("stockTotalDetailService")
public class StockTotalDetailServiceImpl implements StockTotalDetailService {


	@Autowired
	private StockTotalDetailRepository stockTotalDetailRepository;


	public Page<StockTotalDetail> findAll(Pageable pageable){return  stockTotalDetailRepository.findAll(pageable);}
}
