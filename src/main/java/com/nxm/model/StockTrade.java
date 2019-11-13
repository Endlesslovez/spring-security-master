package com.nxm.model;

import javax.persistence.*;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
@Data
@Entity(name = "tbl_stocktrade")
public class StockTrade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private long id;

	private long userId;

	@Column(name = "col_tradedate")
	private String tradeDate;

	@Column(name = "col_createdate")
	private LocalDate createDate;

	@Column(name = "col_updatedate")
	private LocalDate updateDate;

	@Column(name = "col_typeoftrade")
	private long typeOfTrade;

	private long employeeId;

	@Column(name = "col_approved")
	private long approved;

	@Column(name = "col_delverydate")
	private String delveryDate;

	@PrePersist
	public void prePersist() {
		createDate = LocalDate.now();
		updateDate = LocalDate.now();
	}

	@PreUpdate
	public void preUpdate() {
		updateDate = LocalDate.now();
	}


	private long userUpdate;
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stockTrade")
	private Set<StockTradeDetail> stockTradeDetails;
}
