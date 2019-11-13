package com.nxm.model;

import javax.persistence.*;

import lombok.Data;

import java.time.LocalDate;
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
    private LocalDate createDate;

    @OneToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    private long userId;

    @PrePersist
    public void prePersist(){
        createDate=LocalDate.now();
    }

	public Brand() {
		super();
	}

	public Brand(long id, String name, long status, LocalDate createDate, long userId) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.createDate = createDate;
		this.userId = userId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
   
}
