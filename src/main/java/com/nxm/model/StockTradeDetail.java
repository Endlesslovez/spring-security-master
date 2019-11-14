package com.nxm.model;

import javax.persistence.*;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
@Data
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
	private Set<PositionProductTrade> positioProductTrades;

	@ManyToOne
	@JoinColumn(name = "col_product", nullable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "col_stocktrade", nullable = false)
	private StockTrade stockTrade;

	
}
