package com.nxm.model;

import javax.persistence.*;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
@Data
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

	
}
