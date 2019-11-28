package com.nxm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nxm.model.Pallet;

@Repository(value = "palletRepository")
public interface PalletRepository extends CrudRepository<Pallet, Integer> {
	
	List<Pallet> findById(Integer id);
}
