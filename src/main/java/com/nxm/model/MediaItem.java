package com.nxm.model;

import javax.persistence.*;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

}
