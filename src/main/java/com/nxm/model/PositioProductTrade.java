package com.nxm.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import lombok.Data;
@Data
@Entity(name = "tbl_positionproducttrade")
public class PositioProductTrade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "col_stocktradedetail",nullable = false)
	private StockTradeDetail stockTradeDetail;
	
	@ManyToOne
	@JoinColumn(name = "col_palletposition")
	private PalletPosition palletPosition;
	

	
	@Column(name = "col_quantity")
	private long quantity;
	
	@Column(name = "col_createdate")
	private String createDate;
	
	@PrePersist
    public void prePersist(){
       LocalDate formattedString=LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        createDate = formattedString.format(formatter);
    }
	
	
	
	

}
