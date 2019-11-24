package com.nxm.model;

import javax.persistence.*;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Entity(name = "tbl_product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "col_name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "col_brandid", nullable = false)
	private Brand brand;

	@Column(name = "col_price")
	private int price;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	private Set<MediaItem> media;

	@Column(name = "col_createdate")
	private String createDate;

	@Column(name = "col_updatedate")
	private String updateDate;

	@Column(name = "col_create_id")
	private long userId;

	@Column(name = "col_image")
	private String image;

	
	@PrePersist
	public void prePersist() {
		LocalDate formattedString = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
		createDate = formattedString.format(formatter);

		LocalDate formattedString1 = LocalDate.now();
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
		updateDate = formattedString1.format(formatter2);
	}

	@PreUpdate
	public void preUpdate() {
		LocalDate formattedString1 = LocalDate.now();
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
		updateDate = formattedString1.format(formatter2);
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
	@JoinColumn(name = "product_type", nullable = false)
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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}


	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public Product(Long id, String name, Brand brand, int price, Set<MediaItem> media, String createDate,
			String updateDate, long userId, String image, long packageType,
			Set<StockTradeDetail> stockTradeDetails, Set<StockTotalDetail> stockTotalDetails,
			Set<WareHouseDay> wareHouseDays, Set<MediaItem> mediaItems, Set<StockChange> stockChanges,
			ProductType productType) {
		super();
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.media = media;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.userId = userId;
		this.image = image;
		this.packageType = packageType;
		this.stockTradeDetails = stockTradeDetails;
		this.stockTotalDetails = stockTotalDetails;
		this.wareHouseDays = wareHouseDays;
		this.mediaItems = mediaItems;
		this.stockChanges = stockChanges;
		this.productType = productType;
	}

	public Product() {
		super();
	}


	

}
