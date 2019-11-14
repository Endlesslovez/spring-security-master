package com.nxm.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;
@Data
@Entity(name = "tbl_role")
public class Role {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY
    )
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;
   
	@Column(name = "col_createdate")
    private String createDate;
    
    @Column(name = "col_updatedate")
    private String updateDate;
    
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    
    @PrePersist
    public void prePersist(){
       LocalDate formattedString=LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        createDate = formattedString.format(formatter);
        
        LocalDate formattedString1=LocalDate.now();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        updateDate = formattedString1.format(formatter2);
    }
    @PreUpdate
    public void preUpdate() {
    	 LocalDate formattedString1=LocalDate.now();
         DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
         updateDate = formattedString1.format(formatter2);
    }

}

