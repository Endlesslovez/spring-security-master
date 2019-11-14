package com.nxm.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import lombok.Data;
@Data
@Entity(name = "tbl_position_product_trade")
public class PositionProductTrade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "col_stock_trade_detail",nullable = false)
	private StockTradeDetail stockTradeDetail;
	
	@ManyToOne
	@JoinColumn(name = "col_pallet_position")
	private PalletPosition palletPosition;
	

	
	@Column(name = "col_quantity")
	private long quantity;
	
	@Column(name = "col_createdate")
	private LocalDate createDate;
	
	@PrePersist
	public void prePersist() {
		createDate=LocalDate.now();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public StockTradeDetail getStockTradeDetail() {
		return stockTradeDetail;
	}

	public void setStockTradeDetail(StockTradeDetail stockTradeDetail) {
		this.stockTradeDetail = stockTradeDetail;
	}

	public PalletPosition getPalletPosition() {
		return palletPosition;
	}

	public void setPalletPosition(PalletPosition palletPosition) {
		this.palletPosition = palletPosition;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}
	
	
	
	

}
