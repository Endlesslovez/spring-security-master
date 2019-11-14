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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private Set<StockChange> stockChanges;
	@ManyToOne
	@JoinColumn(name = "product_type",nullable = false)
	private ProductType productType;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Set<MediaItem> getMedia() {
		return media;
	}

	public void setMedia(Set<MediaItem> media) {
		this.media = media;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public LocalDate getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getPackageType() {
		return packageType;
	}

	public void setPackageType(long packageType) {
		this.packageType = packageType;
	}

	public Set<StockTradeDetail> getStockTradeDetails() {
		return stockTradeDetails;
	}

	public void setStockTradeDetails(Set<StockTradeDetail> stockTradeDetails) {
		this.stockTradeDetails = stockTradeDetails;
	}

	public Set<StockTotalDetail> getStockTotalDetails() {
		return stockTotalDetails;
	}

	public void setStockTotalDetails(Set<StockTotalDetail> stockTotalDetails) {
		this.stockTotalDetails = stockTotalDetails;
	}

	public Set<WareHouseDay> getWareHouseDays() {
		return wareHouseDays;
	}

	public void setWareHouseDays(Set<WareHouseDay> wareHouseDays) {
		this.wareHouseDays = wareHouseDays;
	}

	public Set<MediaItem> getMediaItems() {
		return mediaItems;
	}

	public void setMediaItems(Set<MediaItem> mediaItems) {
		this.mediaItems = mediaItems;
	}

	

	public Set<StockChange> getStockChanges() {
		return stockChanges;
	}

	public void setStockChanges(Set<StockChange> stockChanges) {
		this.stockChanges = stockChanges;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	
	
	
}
