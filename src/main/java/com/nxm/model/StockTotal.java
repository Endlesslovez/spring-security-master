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


    private long userId;

    @Column(name = "col_datecount")
    private long dateCount;

    @PrePersist
    public void prePersist(){
        createDate=LocalDate.now();
    }

    @Column(name = "col_status")
    private int status;
    
    @ManyToOne
    @JoinColumn(name = "col_stockchange",nullable = false)
    private StockChange stockChange;
    
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "stockTotal")
    private Set<StockTotalDetail> stockTotalDetails;
}
