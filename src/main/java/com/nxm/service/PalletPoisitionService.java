package com.nxm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nxm.model.PalletPosition;

public interface PalletPoisitionService  {
	
	
	public Page<PalletPosition> getAllPalletPoisitions(Pageable pageable);
}
