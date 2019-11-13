package com.nxm.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;

import java.time.LocalDate;
@Data
@Entity(name = "tbl_warehouseday")
public class WareHouseDay {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false,unique = true)
	private long id;
	
	
	@Column(name = "col_openstock")
	private long openStock;

	@Column(name = "col_importware")
	private long importWare;

	@Column(name = "col_export")
	private long export;

	@Column(name = "col_currentstock")
	private long currentStock;

	@Column(name = "col_createdate")
	private LocalDate createDate;

	private long userId;

	@Column(name = "col_updatedate")
	private LocalDate updateDate;

	@ManyToOne
	@JoinColumn(name = "col_product",nullable = false)
	private Product product;

	@PrePersist
	public void prePersist() {
		createDate = LocalDate.now();
		updateDate = LocalDate.now();
	}

	@PreUpdate
	public void preUpdate() {
		updateDate = LocalDate.now();
	}
}
