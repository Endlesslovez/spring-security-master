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

import lombok.Data;
@Data
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
	@Column(name = "col_updateDate")
<<<<<<< HEAD
	private LocalDate updateDate;

	@Column(name = "col_quantity_change")
	private long quantityChange;

	@ManyToOne
	@JoinColumn(name = "col_product", nullable = false)
	private Product product;

	/*@ManyToOne
	@JoinColumn(name = "col_stocktotal", nullable = false)*/
	@Column(name = "stock_total_id")
	private long stockTotalId;
	
	
=======
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
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "stockChange")
	private Set<StockTotal> stockTotals;
>>>>>>> h
}
