package com.nxm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nxm.model.StockChange;
import com.nxm.repository.StockChangeRepository;

@Service("stockChangeService")
public class StockChangeServiceImpl implements StockChangeService {

	@Autowired
	private StockChangeRepository stockChangeRepository;

	public List<StockChange> findByStockTotalId(Long id) {
		return stockChangeRepository.findByStockTotalId(id);
	}
}
