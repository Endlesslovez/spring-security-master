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

	
}
