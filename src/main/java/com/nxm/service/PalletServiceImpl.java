package com.nxm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nxm.model.Pallet;
import com.nxm.repository.PalletRepository;

@Service
public class PalletServiceImpl implements PalletService {

	@Autowired
	private PalletRepository palletRepository;


	@Override
	public Pallet findById(Long id) {
		Optional<Pallet> temp = palletRepository.findById(id);
		
		Pallet pallet =temp.get();
		return pallet;
	}

}
