package com.nxm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nxm.model.PalletPoisitonVo;
import com.nxm.model.PalletPosition;
import com.nxm.repository.PalletPoisitionRepository;

@Service
public class PalletPoisitionServiceImpl implements PalletPoisitionService {

	@Autowired
	private PalletPoisitionRepository palletPoisitionRepository;

	@Override
	public Page<PalletPosition> getAllPalletPoisitions(Pageable pageable) {
		// TODO Auto-generated method stub
		return palletPoisitionRepository.findAll(pageable);
	}

	@Override
	public List<PalletPosition> findRecord() {
		// TODO Auto-generated method stub
		return palletPoisitionRepository.findRecord();
	}

	/*@Override
	public Page<PalletPoisitonVo> findPalletpositonAndAreaIdAndEmptypercent(String emptypercent, String areaId,
			String palletPoisitionId, Pageable pageable) {
		// TODO Auto-generated method stub
		return palletPoisitionRepository.findPalletpositonAndAreaIdAndEmptypercent(emptypercent, areaId,
				palletPoisitionId, pageable);
	}

	@Override
	public Page<PalletPoisitonVo> findByProduct(String producctId, String emptypercent, String areaId,
			String palletPoisitionId, String producctId2, Pageable pageable) {
		// TODO Auto-generated method stub
		return palletPoisitionRepository.findByProduct(producctId, emptypercent, areaId, palletPoisitionId, producctId2,
				pageable);
	}*/

}
