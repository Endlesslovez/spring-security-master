package com.nxm.service;

import com.nxm.model.StockTotal;

public interface StockTotalService {
    StockTotal findNow();
    StockTotal findAvaiableRecord();
}
