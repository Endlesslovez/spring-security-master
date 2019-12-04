package com.nxm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nxm.model.Brand;
import com.nxm.model.Product;
import com.nxm.model.ProductType;
import com.nxm.model.StockTotal;
import com.nxm.model.StockTotalDetail;

@Repository
public interface StockTotalDetailRepository extends CrudRepository<StockTotalDetail, Long> {

	Page<StockTotalDetail> findAll(Pageable pageable);

	@Query(value = "select u.* from tbl_stocktotaldetail u where u.col_stocktotal = ?1", nativeQuery = true)
	List<StockTotalDetail> findByStockTotal(StockTotal stockTotal);

	
@Query(value = "select tsl.id,p.col_name,b.col_name,tp.col_type_name,tsl.col_expireddate from tbl_stocktotaldetail tsl join tbl_product p on tsl.col_product=p.id join tbl_brand b on b.id = p.col_brandid join tbl_product_type tp on p.product_type = tp.id where b.col_name = :namebrand and p.col_name =:nameproduct and tp.col_type_name = :typename and tsl.col_expireddate = :expireddate",nativeQuery = true )
public List<StockTotalDetail> getStockDetail(@Param("namebrand") String namebrand,@Param("nameproduct") String nameproduct,@Param("typename") String typename,@Param("expireddate") String expireddate);

}
