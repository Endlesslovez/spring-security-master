package com.nxm.model;



import javax.persistence.*;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
@Entity(name = "tbl_stocktotal")
public class StockTotal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private long id;

    @Column(name = "createdate")
    private String createDate;

    @Column(name = "userCreateId")
    private long userCreateId;

    @Column(name = "col_datecount")
    private String dateCount;
    
    @PrePersist
    public void prePersist(){
       LocalDate formattedString=LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        createDate = formattedString.format(formatter);

      
    }
    @Column(name = "col_total_count")
    private long totalCount;
    @Column(name = "col_status")
    private int status;

   /* @OneToMany(fetch = FetchType.LAZY,mappedBy = "stockTotal")
    private Set<StockChange> stockChanges;*/
    
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "stockTotal")
    private Set<StockTotalDetail> stockTotalDetails;

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {

		this.totalCount = totalCount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getDateCount() {
		return dateCount;
	}

	public void setDateCount(String dateCount) {
		this.dateCount = dateCount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Set<StockTotalDetail> getStockTotalDetails() {
		return stockTotalDetails;
	}

	public void setStockTotalDetails(Set<StockTotalDetail> stockTotalDetails) {
		this.stockTotalDetails = stockTotalDetails;
	}

    
    
}
