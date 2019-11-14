package com.nxm.model;


import javax.persistence.*;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
@Data
@Entity(name = "tbl_product_type")
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Long id;

    @Column(name = "col_type_name")
    private  String typeName;

    @Column(name = "col_create_date")
    private LocalDate createDate;

    @Column(name = "col_status")
    private String status;

    @PrePersist
    public void prePersist(){
        createDate=LocalDate.now();
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "productType")
    private Set<Product> products;
}
