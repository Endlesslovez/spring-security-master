package com.nxm.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
@Data
@Entity(name = "tbl_stockchang")
public class StockChange {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@Column(name = "col_createdate")
	private LocalDate createDate;
	
	private long userCreateId;
	
	@Column(name = "col_updateDate")
	private LocalDate updateDate;


	@OneToMany(fetch = FetchType.LAZY,mappedBy = "stockChange")
	private Set<StockTotal> stockTotals;
}
