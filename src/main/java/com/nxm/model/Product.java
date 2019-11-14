package com.nxm.model;

import javax.persistence.*;



import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Entity(name = "tbl_product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private long id;

	@Column(name = "col_name")
	private String name;

	@OneToOne
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

	private long userId;


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
	@JoinColumn(name = "product_type",nullable = false)
	private ProductType productType;


	
	
}
