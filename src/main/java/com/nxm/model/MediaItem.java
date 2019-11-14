package com.nxm.model;

import javax.persistence.*;

import lombok.Data;

import java.time.LocalDate;
@Data
@Entity(name = "tbl_madiaitem")
public class MediaItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private long id;

	@Column(name = "col_path")
	private String path;

	@Column(name = "col_createdate")
	private LocalDate createDate;

	private long userId;

	@Column(name = "col_mediatype")
	private long mediaType;

	@PrePersist
	public void prePersist() {
		createDate = LocalDate.now();
	}

	@ManyToOne
	@JoinColumn(name = "col_productid", nullable = false)
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "col_employeeid", nullable = false)
	private Employee employee;

	public MediaItem() {
		super();
	}

	public MediaItem(long id, String path, LocalDate createDate, long userId, long mediaType, Product product) {
		super();
		this.id = id;
		this.path = path;
		this.createDate = createDate;
		this.userId = userId;
		this.mediaType = mediaType;
		this.product = product;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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

	public long getMediaType() {
		return mediaType;
	}

	public void setMediaType(long mediaType) {
		this.mediaType = mediaType;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
