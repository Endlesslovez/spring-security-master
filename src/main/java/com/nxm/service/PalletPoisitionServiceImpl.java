package com.nxm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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



}
