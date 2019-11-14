package com.nxm.model;

import javax.persistence.*;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
@Data
@Entity(name = "tbl_brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private long id;

    @Column(name = "col_name")
    private String name;

    @Column(name = "col_status")
    private long status;

    @Column(name = "col_createdate")
    private String createDate;

    @OneToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    private long userId;

    @PrePersist
    public void prePersist(){
       LocalDate formattedString=LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        createDate = formattedString.format(formatter);
    }

	public Brand() {
		super();
	}

   
}
