package com.nxm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nxm.model.PalletPoisitonVo;
import com.nxm.model.PalletPosition;
import com.nxm.model.StockTotal;
import com.nxm.model.StockTotalDetail;

@Repository(value = "palletPoisitionRepository")

public interface PalletPoisitionRepository extends PagingAndSortingRepository<PalletPosition, Long> {
	@Query(value = "select u.* from tbl_pallet_position u ", nativeQuery = true)
	List<PalletPosition> findRecord();
	/*@Query(value = "select  tp.col_areaid as areaId,tp.col_palletnumber as palletNumber,pp.col_emtypercent as emptypercent,pp.id as id, sanpham.data as product\r\n" + 
			"	from tbl_pallet_position pp \r\n" + 
			"	join tbl_pallet tp on pp.palletid=tp.id \r\n" + 
			"	left join(select distinct std2.col_pallet_position id, STUFF((SELECT distinct ',' + QUOTENAME(std1.col_product, '')\r\n" + 
			"        from  tbl_stocktotaldetail std1 \r\n" + 
			"        where std1.col_pallet_position = std2.col_pallet_position\r\n" + 
			"          FOR XML PATH(''), TYPE\r\n" + 
			"           ).value('.', 'NVARCHAR(MAX)') \r\n" + 
			"      ,1,1,'') data  from tbl_stocktotaldetail std2 ) sanpham  on sanpham.id = pp.id\r\n" + 
			"	where 1=1 and (:emptypercent1 is null or col_emtypercent like ':emptypercent1%' ) and (:areaId1 is null or tp.col_areaid like ':areaId1%')  \r\n" + 
			"	 and (:palletPoisitionId1 is null or pp.id like ':palletPoisitionId1%') \r\n" + 
			"	 group by tp.col_areaid,tp.col_palletnumber,pp.col_emtypercent,pp.id,sanpham.data ",
			countQuery ="select  count(*) \r\n" + 
					"	from tbl_pallet_position pp \r\n" + 
					"	join tbl_pallet tp on pp.palletid=tp.id \r\n" + 
					"	left join(select distinct std2.col_pallet_position id, STUFF((SELECT distinct ',' + QUOTENAME(std1.col_product, '')\r\n" + 
					"        from  tbl_stocktotaldetail std1 \r\n" + 
					"        where std1.col_pallet_position = std2.col_pallet_position\r\n" + 
					"          FOR XML PATH(''), TYPE\r\n" + 
					"           ).value('.', 'NVARCHAR(MAX)') \r\n" + 
					"      ,1,1,'') data  from tbl_stocktotaldetail std2 ) sanpham  on sanpham.id = pp.id\r\n" + 
					"	where 1=1 and ( :emptypercent1 is null or col_emtypercent like ':emptypercent1%' ) and (:areaId1 is null or tp.col_areaid like ':areaId1%') \r\n" + 
					"	 and (:palletPoisitionId1 is null or pp.id like ':palletPoisitionId1%') \r\n" + 
					"	 group by tp.col_areaid,tp.col_palletnumber,pp.col_emtypercent,pp.id,sanpham.data")
	Page<PalletPoisitonVo> findPalletpositonAndAreaIdAndEmptypercent(@Param("emptypercent1") String emptypercent,@Param("areaId1") String areaId,@Param("palletPoisitionId1")String palletPoisitionId ,Pageable pageable);
	
	@Query(value = "select  tp.col_areaid as  areaId,tp.col_palletnumber as palletNumber,pp.col_emtypercent as  emptypercent,pp.id as id, sanpham.data as product\r\n" + 
			"	from tbl_pallet_position pp \r\n" + 
			"	join tbl_pallet tp on pp.palletid=tp.id \r\n" + 
			"	left join(select distinct std2.col_pallet_position id, STUFF((SELECT distinct ',' + QUOTENAME(std1.col_product, '')\r\n" + 
			"        from  tbl_stocktotaldetail std1 \r\n" + 
			"        where std1.col_pallet_position = std2.col_pallet_position\r\n" + 
			"			and (:producctId is null or std1.col_product like ':producctId%') \r\n" + 
			"          FOR XML PATH(''), TYPE\r\n" + 
			"           ).value('.', 'NVARCHAR(MAX)') \r\n" + 
			"      ,1,1,'') data  from tbl_stocktotaldetail std2 ) sanpham  on sanpham.id = pp.id\r\n" + 
			"	  join tbl_stocktotaldetail std1  on pp.id = std1.col_pallet_position\r\n" + 
			"	where 1=1 and ( :emptypercent1 is null or col_emtypercent like ':emptypercent1%' ) and (:areaId1 is null or tp.col_areaid like ':areaId1%') \r\n" + 
			"	  and (:palletPoisitionId1 is null or pp.id like ':palletPoisitionId1%') and and (:producctId2 is null or std1.col_product like ':producctId2%')\r\n" + 
			"	 group by tp.col_areaid,tp.col_palletnumber,pp.col_emtypercent,pp.id,sanpham.data ",
			countQuery="select  count(*)\r\n" + 
					"	from tbl_pallet_position pp \r\n" + 
					"	join tbl_pallet tp on pp.palletid=tp.id \r\n" + 
					"	left join(select distinct std2.col_pallet_position id, STUFF((SELECT distinct ',' + QUOTENAME(std1.col_product, '')\r\n" + 
					"        from  tbl_stocktotaldetail std1 \r\n" + 
					"        where 1=1 and std1.col_pallet_position = std2.col_pallet_position\r\n" + 
					"			and (:producctId is null or std1.col_product like ':producctId%')\r\n" + 
					"          FOR XML PATH(''), TYPE\r\n" + 
					"           ).value('.', 'NVARCHAR(MAX)') \r\n" + 
					"      ,1,1,'') data  from tbl_stocktotaldetail std2 ) sanpham  on sanpham.id = pp.id\r\n" + 
					"	  join tbl_stocktotaldetail std1  on pp.id = std1.col_pallet_position\r\n" + 
					"	where ( :emptypercent1 is null or col_emtypercent1 like ':emptypercent1%' ) and (:areaId1 is null or tp.col_areaid like ':areaId1%') \r\n" + 
					"	 and (:palletPoisitionId1 is null or pp.id like ':palletPoisitionId1%') and and (:producctId2 is null or std1.col_product like ':producctId2%')\r\n" + 
					"	 group by tp.col_areaid,tp.col_palletnumber,pp.col_emtypercent,pp.id,sanpham.data ")
	Page<PalletPoisitonVo> findByProduct(@Param("producctId1")String producctId,@Param("emptypercent1")String emptypercent,@Param("areaId1") String areaId,@Param("palletPoisitionId1")String palletPoisitionId ,@Param("producctId2")String producctId2,Pageable pageable);
	*/

}
