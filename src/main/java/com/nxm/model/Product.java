package com.nxm.model;

import javax.persistence.*;



import java.time.LocalDate;
import java.util.Set;

@Entity(name = "tbl_product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private long id;

	@Column(name = "col_name")
	private String name;

	@OneToOne
	@JoinColumn(name = "col_brandid", nullable = false)
	private Brand brand;

	@Column(name = "col_price")
	private int price;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	private Set<MediaItem> media;

	@Column(name = "col_createdate")
	private LocalDate createDate;

	@Column(name = "col_updatedate")
	private LocalDate updateDate;

	private long userId;


	@PrePersist
	public void prePersist() {
		createDate = LocalDate.now();
		updateDate = LocalDate.now();
	}

	@PreUpdate
	public void preUpdate() {
		updateDate = LocalDate.now();
	}

	private long packageType;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	private Set<StockTradeDetail> stockTradeDetails;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	private Set<StockTotalDetail> stockTotalDetails;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	private Set<WareHouseDay> wareHouseDays;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	private Set<MediaItem> mediaItems;

	@ManyToOne
	@JoinColumn(name = "producttype",nullable = false)
	private ProductType productType;
}
