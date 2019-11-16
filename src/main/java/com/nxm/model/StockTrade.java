package com.nxm.model;

import javax.persistence.*;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
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
	private String createDate;

	@Column(name = "col_updatedate")
	private String updateDate;

	@Column(name = "col_typeoftrade")
	private long typeOfTrade;

	private long employeeId;

	@Column(name = "col_approved")
	private long approved;

	@Column(name = "col_delverydate")
	private String delveryDate;
	 
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

	private long userUpdate;
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stockTrade")
	private Set<StockTradeDetail> stockTradeDetails;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
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
	public long getTypeOfTrade() {
		return typeOfTrade;
	}
	public void setTypeOfTrade(long typeOfTrade) {
		this.typeOfTrade = typeOfTrade;
	}
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	public long getApproved() {
		return approved;
	}
	public void setApproved(long approved) {
		this.approved = approved;
	}
	public String getDelveryDate() {
		return delveryDate;
	}
	public void setDelveryDate(String delveryDate) {
		this.delveryDate = delveryDate;
	}
	public long getUserUpdate() {
		return userUpdate;
	}
	public void setUserUpdate(long userUpdate) {
		this.userUpdate = userUpdate;
	}
	public Set<StockTradeDetail> getStockTradeDetails() {
		return stockTradeDetails;
	}
	public void setStockTradeDetails(Set<StockTradeDetail> stockTradeDetails) {
		this.stockTradeDetails = stockTradeDetails;
	}

	
}
