package com.nxm.model;


import javax.persistence.*;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
@Entity(name = "tbl_product_type")
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Long id;

    @Column(name = "col_type_name")
    private  String typeName;


    @Column(name = "col_createdate")
    private String createDate;


    @Column(name = "col_status")
    private String status;

    @PrePersist
    public void prePersist(){
       LocalDate formattedString=LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        createDate = formattedString.format(formatter);
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "productType")
    private Set<Product> products;
}
