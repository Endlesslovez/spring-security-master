package com.nxm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nxm.model.StockTotal;
import com.nxm.model.StockTotalDetail;

public interface StockTotalService {
    StockTotal findNow();
    StockTotal findAvaiableRecord();
}
