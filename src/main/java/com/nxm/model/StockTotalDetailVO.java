package com.nxm.model;

import java.time.LocalDate;

public class StockTotalDetailVO {

	private Integer id;

	private Integer palletPositionId;

	private long quantity;

	private String createDate;

	private long userCreateId;
	
	private long userUpdateId;

	private String updateDate;

	private String expiredDate;
	 
	private Integer stockTotalId;

	private Integer productId;

	private String error;
	
	private String productName;
	private Integer brandId;
	private String brandName;
	private Integer typeOfProduct;
	
	private Long stockTotalDetailId;
	
	private Integer palletPoisitionId;
	
	
	public Integer getPalletPoisitionId() {
		return palletPoisitionId;
	}

	public void setPalletPoisitionId(Integer palletPoisitionId) {
		this.palletPoisitionId = palletPoisitionId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public void setStockTotalDetailId(Long stockTotalDetailId) {
		this.stockTotalDetailId = stockTotalDetailId;
	}

	public Long getStockTotalDetailId() {
		return stockTotalDetailId;
	}

	public void setStockTotalDetailId(long stockTotalDetailId) {
		this.stockTotalDetailId = stockTotalDetailId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getTypeOfProduct() {
		return typeOfProduct;
	}

	public void setTypeOfProduct(Integer typeOfProduct) {
		this.typeOfProduct = typeOfProduct;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPalletPositionId() {
		return palletPositionId;
	}

	public void setPalletPositionId(Integer palletPositionId) {
		this.palletPositionId = palletPositionId;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public long getUserCreateId() {
		return userCreateId;
	}

	public void setUserCreateId(long userCreateId) {
		this.userCreateId = userCreateId;
	}

	public long getUserUpdateId() {
		return userUpdateId;
	}

	public void setUserUpdateId(long userUpdateId) {
		this.userUpdateId = userUpdateId;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	public Integer getStockTotalId() {
		return stockTotalId;
	}

	public void setStockTotalId(Integer stockTotalId) {
		this.stockTotalId = stockTotalId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	
}
