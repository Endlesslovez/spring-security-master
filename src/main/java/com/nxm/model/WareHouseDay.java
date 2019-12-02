package com.nxm.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
@Entity(name = "tbl_warehouseday")
public class WareHouseDay {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false,unique = true)
	private long id;
	
	
	@Column(name = "col_openstock")
	private long openStock;

	@Column(name = "col_importware")
	private long importWare;

	@Column(name = "col_export")
	private long export;

	@Column(name = "col_currentstock")
	private long currentStock;

	//sau khi kiểm kho thừa thiếu sẽ được lưu ở đây
	@Column(name = "col_changestock")
	private long changeStock;
	
	@Column(name = "col_createdate")
	private String createDate;

	private long userId;

	@Column(name = "col_updatedate")
	private String updateDate;

	@ManyToOne
	@JoinColumn(name = "col_product",nullable = false)
	private Product product;



	public long getChangeStock() {
		return changeStock;
	}

	public void setChangeStock(long changeStock) {
		this.changeStock = changeStock;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOpenStock() {
		return openStock;
	}

	public void setOpenStock(long openStock) {
		this.openStock = openStock;
	}

	public long getImportWare() {
		return importWare;
	}

	public void setImportWare(long importWare) {
		this.importWare = importWare;
	}

	public long getExport() {
		return export;
	}

	public void setExport(long export) {
		this.export = export;
	}

	public long getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(long currentStock) {
		this.currentStock = currentStock;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	

	 
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

}
