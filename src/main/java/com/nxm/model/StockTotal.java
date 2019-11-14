package com.nxm.model;



import javax.persistence.*;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
@Data
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
    private long dateCount;
    
    @PrePersist
    public void prePersist(){
       LocalDate formattedString=LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        createDate = formattedString.format(formatter);
        
      
    }

    @Column(name = "col_status")
    private int status;
    
   /* @OneToMany(fetch = FetchType.LAZY,mappedBy = "stockTotal")
    private Set<StockChange> stockChanges;*/
    
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "stockTotal")
    private Set<StockTotalDetail> stockTotalDetails;

    
}
