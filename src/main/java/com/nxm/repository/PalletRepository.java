package com.nxm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nxm.model.Pallet;
import com.nxm.model.StockTotalDetail;

@Repository(value = "palletRepository")
public interface PalletRepository extends CrudRepository<Pallet, Long> {
	
	Optional<Pallet> findById(Long id);
	
	@Query(value = "select  u.* from tbl_pallet u ", nativeQuery = true)
	List<Pallet> findRecords();
}
