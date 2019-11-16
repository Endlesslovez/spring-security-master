package com.nxm.model;

import javax.persistence.*;


import java.time.LocalDate;
import java.util.Set;
@Entity(name = "tbl_pallet_position")
public class PalletPosition {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "palletid",nullable = false)
	private Pallet pallet;
	
	@Column(name = "col_horizontal_axis")
	private String horizontalAxis;
	
	@Column(name = "col_vertical_axis")
	private String verticalAxis;
	
	@Column(name = "col_emtypercent")
	private long emptyPercent;

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "palletPosition")
	private Set<StockTotalDetail> stockTotalDetails;

	@OneToMany(fetch = FetchType.EAGER,mappedBy = "palletPosition")
	private Set<PositioProductTrade> productTrades;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Pallet getPallet() {
		return pallet;
	}

	public void setPallet(Pallet pallet) {
		this.pallet = pallet;
	}

	public String getHorizontalAxis() {
		return horizontalAxis;
	}

	public void setHorizontalAxis(String horizontalAxis) {
		this.horizontalAxis = horizontalAxis;
	}

	public String getVerticalAxis() {
		return verticalAxis;
	}

	public void setVerticalAxis(String verticalAxis) {
		this.verticalAxis = verticalAxis;
	}

	public long getEmptyPercent() {
		return emptyPercent;
	}

	public void setEmptyPercent(long emptyPercent) {
		this.emptyPercent = emptyPercent;
	}

	public Set<StockTotalDetail> getStockTotalDetails() {
		return stockTotalDetails;
	}

	public void setStockTotalDetails(Set<StockTotalDetail> stockTotalDetails) {
		this.stockTotalDetails = stockTotalDetails;
	}

	public Set<PositioProductTrade> getProductTrades() {
		return productTrades;
	}

	public void setProductTrades(Set<PositioProductTrade> productTrades) {
		this.productTrades = productTrades;
	}

	
}
