package com.nxm.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

@Entity(name = "tbl_stockchange")
public class StockChange {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "col_createdate")
	private String createDate;

	@Column(name = "col_userCreateId")
	private long userCreateId;
	@Column(name = "col_userUpdateId")
	private long userUpdateId;

	@Column(name = "col_quantity_change")
	private long quantityChange;

	@ManyToOne
	@JoinColumn(name = "col_product", nullable = false)
	private Product product;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "col_stocktotal", nullable = false)
	 */
	@Column(name = "stock_total_id")
	private long stockTotalId;

	@Column(name = "stock_total_detail_id")
	private long stockTotalDetailId;

	private String updateDate;

	public long getStockTotalDetailId() {
		return stockTotalDetailId;
	}

	public void setStockTotalDetailId(long stockTotalDetailId) {
		this.stockTotalDetailId = stockTotalDetailId;
	}

	@PrePersist
	public void prePersist() {
		LocalDate formattedString = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
		createDate = formattedString.format(formatter);

		LocalDate formattedString1 = LocalDate.now();
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
		updateDate = formattedString1.format(formatter2);
	}

//	@OneToMany(fetch = FetchType.LAZY,mappedBy = "stockChange")
//	private Set<StockTotal> stockTotals;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public long getQuantityChange() {
		return quantityChange;
	}

	public void setQuantityChange(long quantityChange) {
		this.quantityChange = quantityChange;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public long getStockTotalId() {
		return stockTotalId;
	}

	public void setStockTotalId(long stockTotalId) {
		this.stockTotalId = stockTotalId;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

}
