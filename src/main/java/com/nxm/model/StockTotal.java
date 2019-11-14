package com.nxm.model;



import javax.persistence.*;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
@Data
@Entity(name = "tbl_stocktotal")
public class StockTotal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private long id;

    @Column(name = "createdate")
    private LocalDate createDate;

    @Column(name = "userCreateId")
    private long userCreateId;

    @Column(name = "col_datecount")
    private long dateCount;

    @PrePersist
    public void prePersist(){
        createDate=LocalDate.now();
    }

    @Column(name = "col_status")
    private int status;
    
   /* @OneToMany(fetch = FetchType.LAZY,mappedBy = "stockTotal")
    private Set<StockChange> stockChanges;*/
    
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "stockTotal")
    private Set<StockTotalDetail> stockTotalDetails;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public long getUserCreateId() {
		return userCreateId;
	}

	public void setUserCreateId(long userCreateId) {
		this.userCreateId = userCreateId;
	}

	public long getDateCount() {
		return dateCount;
	}

	public void setDateCount(long dateCount) {
		this.dateCount = dateCount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}



	/*public Set<StockChange> getStockChanges() {
		return stockChanges;
	}

	public void setStockChanges(Set<StockChange> stockChanges) {
		this.stockChanges = stockChanges;
	}*/

	public Set<StockTotalDetail> getStockTotalDetails() {
		return stockTotalDetails;
	}

	public void setStockTotalDetails(Set<StockTotalDetail> stockTotalDetails) {
		this.stockTotalDetails = stockTotalDetails;
	}
    
    
}
