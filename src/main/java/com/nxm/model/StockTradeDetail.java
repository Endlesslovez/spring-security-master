package com.nxm.model;

import javax.persistence.*;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
@Data
@Entity(name = "tbl_stocktradedetail")
public class StockTradeDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private long id;

	@Column(name = "col_quantity")
	private long quantity;

	@Column(name = "col_amount")
	private String amount;

	@Column(name = "col_createdate")
	private LocalDate createDate;

	private long userCreateId;

	private long userUpdateId;

	@Column(name = "col_updatedate")
	private LocalDate updateDate;

	@PrePersist
	public void prePersist() {
		createDate = LocalDate.now();
		updateDate = LocalDate.now();
	}

	@PreUpdate
	public void preUpdate() {
		updateDate = LocalDate.now();
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stockTradeDetail")
	private Set<PositioProductTrade> positioProductTrades;

	@ManyToOne
	@JoinColumn(name = "col_product", nullable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "col_stocktrade", nullable = false)
	private StockTrade stockTrade;
}
