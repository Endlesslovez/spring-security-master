package com.nxm.service;

import java.util.List;

import com.nxm.model.StockChange;

public interface StockChangeService {
    public List<StockChange> findByStockTotalId(Long id); 
}
