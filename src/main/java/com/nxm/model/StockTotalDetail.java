package com.nxm.model;

import javax.persistence.*;



import java.time.LocalDate;

@Entity(name = "tbl_stocktotaldetail")
public class StockTotalDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private long id;

	@ManyToOne
	@JoinColumn(name = "col_palletposition", nullable = false)
	private PalletPosition palletPosition;

	@Column(name = "col_quantity")
	private long quantity;

	@Column(name = "col_createdate")
	private LocalDate createDate;

	private long userCreateId;

	private long userUpdateId;

	@Column(name = "col_updatedate")
	private LocalDate updateDate;

	@Column(name = "col_avaiablequantity")
	private String avaiableQuantity;

	@Column(name = "col_expireddate")
	private String expiredDate;

	@Column(name = "col_productstatus")
	private int productStatus;
	
	@PrePersist
	public void prePersist() {
		createDate = LocalDate.now();
	}

	@ManyToOne
	@JoinColumn(name = "col_stocktotal", nullable = false)
	private StockTotal stockTotal;

	@ManyToOne
	@JoinColumn(name = "col_product", nullable = false)
	private Product product;
}
