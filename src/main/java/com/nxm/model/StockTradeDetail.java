package com.nxm.model;

import javax.persistence.*;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
@Entity(name = "tbl_stock_trade_detail")
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
	private String createDate;

	private long userCreateId;

	private long userUpdateId;

	@Column(name = "col_updatedate")
	private String updateDate;

	 
    @PrePersist
    public void prePersist(){
       LocalDate formattedString=LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        createDate = formattedString.format(formatter);
        
        LocalDate formattedString1=LocalDate.now();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        updateDate = formattedString1.format(formatter2);
    }
    @PreUpdate
    public void preUpdate() {
    	   LocalDate formattedString1=LocalDate.now();
           DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
           updateDate = formattedString1.format(formatter2);
    }

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stockTradeDetail")
	private Set<PositioProductTrade> positioProductTrades;

	@ManyToOne
	@JoinColumn(name = "col_product", nullable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "col_stocktrade", nullable = false)
	private StockTrade stockTrade;


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
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
	public Set<PositioProductTrade> getPositioProductTrades() {
		return positioProductTrades;
	}
	public void setPositioProductTrades(Set<PositioProductTrade> positioProductTrades) {
		this.positioProductTrades = positioProductTrades;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public StockTrade getStockTrade() {
		return stockTrade;
	}
	public void setStockTrade(StockTrade stockTrade) {
		this.stockTrade = stockTrade;
	}

	
}
