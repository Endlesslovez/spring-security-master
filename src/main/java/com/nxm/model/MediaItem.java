package com.nxm.model;

import javax.persistence.*;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
@Entity(name = "tbl_madiaitem")
public class MediaItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private long id;

	@Column(name = "col_path")
	private String path;

	@Column(name = "col_createdate")
	private String createDate;

	private long userId;

	@Column(name = "col_mediatype")
	private long mediaType;

	@PrePersist
    public void prePersist(){
       LocalDate formattedString=LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        createDate = formattedString.format(formatter);
    }

	@ManyToOne
	@JoinColumn(name = "col_productid", nullable = false)
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "col_employeeid", nullable = false)
	private Employee employee;

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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	
}
