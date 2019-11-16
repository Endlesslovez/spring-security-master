package com.nxm.model;

import javax.persistence.*;



import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity(name = "tbl_stocktotaldetail")
public class StockTotalDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private long id;

	@ManyToOne
	@JoinColumn(name = "col_pallet_position", nullable = false)
	private PalletPosition palletPosition;

	@Column(name = "col_quantity")
	private long quantity;

	@Column(name = "col_createdate")
	private String createDate;

	@Column(name = "col_userCreateId")
	private long userCreateId;
	
	@Column(name = "col_userUpdateId")
	private long userUpdateId;

	@Column(name = "col_updatedate")
	private String updateDate;

	@Column(name = "col_avaiable_quantity")
	private String avaiableQuantity;

	@Column(name = "col_expireddate")
	private LocalDate expiredDate;

	@Column(name = "col_productstatus")
	private int productStatus;
	 
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

	@ManyToOne
	@JoinColumn(name = "col_stocktotal", nullable = false)
	private StockTotal stockTotal;

	@ManyToOne
	@JoinColumn(name = "col_product", nullable = false)
	private Product product;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public PalletPosition getPalletPosition() {
		return palletPosition;
	}
	public void setPalletPosition(PalletPosition palletPosition) {
		this.palletPosition = palletPosition;
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
	public String getAvaiableQuantity() {
		return avaiableQuantity;
	}
	public void setAvaiableQuantity(String avaiableQuantity) {
		this.avaiableQuantity = avaiableQuantity;
	}
	public LocalDate getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(LocalDate expiredDate) {
		this.expiredDate = expiredDate;
	}
	public int getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(int productStatus) {
		this.productStatus = productStatus;
	}
	public StockTotal getStockTotal() {
		return stockTotal;
	}
	public void setStockTotal(StockTotal stockTotal) {
		this.stockTotal = stockTotal;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	
}
