package com.nxm.model;

import javax.persistence.*;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
@Data
@Entity(name = "tbl_palletposition")
public class PalletPosition {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "palletid",nullable = false)
	private Pallet pallet;
	
	@Column(name = "col_position")
	private String position;
	
	@Column(name = "col_emtypercent")
	private long emptyPercent;

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "palletPosition")
	private Set<StockTotalDetail> stockTotalDetails;

	@OneToMany(fetch = FetchType.EAGER,mappedBy = "palletPosition")
	private Set<PositioProductTrade> productTrades;

}
