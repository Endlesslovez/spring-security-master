package com.nxm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nxm.model.Pallet;
import com.nxm.model.PalletPosition;

@Repository(value = "palletPoisitionRepository")
public interface PalletPoisitionRepository extends CrudRepository<PalletPosition, Integer> {
	
	Page<PalletPosition> findAll(Pageable pageable);
	PalletPosition findOne(Integer id);
}
