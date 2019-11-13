package com.nxm.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;

@Entity(name = "tbl_role")
public class Role {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY
    )
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;
    
    public LocalDate getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}
	public LocalDate getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "col_createdate")
    private LocalDate createDate;
    
    @Column(name = "col_updatedate")
    private LocalDate updateDate;
    
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    
    @PrePersist
    public void prePersist() {
    	createDate=LocalDate.now();
    	updateDate=LocalDate.now();
    }
    @PreUpdate
    public void preUpdate() {
    	updateDate=LocalDate.now();
    }
    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}

