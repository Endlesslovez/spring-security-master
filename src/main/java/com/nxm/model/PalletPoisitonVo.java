package com.nxm.model;

public class PalletPoisitonVo {
	private String areaId;
	private long palletNumber;
	private long emptyPercent;
	private long id;
	private String product;
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public long getPalletNumber() {
		return palletNumber;
	}
	public void setPalletNumber(long palletNumber) {
		this.palletNumber = palletNumber;
	}
	
	public long getEmptyPercent() {
		return emptyPercent;
	}
	public void setEmptyPercent(long emptyPercent) {
		this.emptyPercent = emptyPercent;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	
	
	
}
