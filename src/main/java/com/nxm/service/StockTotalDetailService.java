package com.nxm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nxm.model.StockTotalDetail;

public interface StockTotalDetailService {
    Page<StockTotalDetail> findAll(Pageable pageable);
    StockTotalDetail findOne(Long id);
    List<StockTotalDetail>  findByStockTotalId(Long id);
}
