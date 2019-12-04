package com.nxm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nxm.model.PalletPoisitonVo;
import com.nxm.model.PalletPosition;

public interface PalletPoisitionService {

	public Page<PalletPosition> getAllPalletPoisitions(Pageable pageable);
	List<PalletPosition> findRecord();
	/*
	 * public Page<PalletPoisitonVo>
	 * findPalletpositonAndAreaIdAndEmptypercent(String emptypercent, String areaId,
	 * String palletPoisitionId, Pageable pageable);
	 * 
	 * public Page<PalletPoisitonVo> findByProduct(String producctId,String
	 * emptypercent, String areaId,String palletPoisitionId , String producctId2,
	 * Pageable pageable);
	 */
}
