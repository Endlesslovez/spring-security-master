package com.nxm.model;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
@Entity
@Table(name = "tbl_product_type")
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
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

	public ProductType() {
		super();
	}

	public ProductType(Long id, String typeName, String createDate, String status, Set<Product> products) {
		super();
		this.id = id;
		this.typeName = typeName;
		this.createDate = createDate;
		this.status = status;
		this.products = products;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
  
}
