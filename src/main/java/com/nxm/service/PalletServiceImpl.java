package com.nxm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nxm.model.Pallet;
import com.nxm.repository.PalletRepository;

@Service
public class PalletServiceImpl implements PalletService {

	@Autowired
	private PalletRepository palletRepository;


	@Override
	public Pallet findById(Integer id) {
		
		return palletRepository.findOne(id);
	}

}
