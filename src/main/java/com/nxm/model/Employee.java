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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


@Entity(name = "tbl_employee")
public class Employee {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY
    )
    @Column(name = "employee_id", nullable = false)
    private int id;

    @Column(name = "employee_name", nullable = false)
    private String name;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;
    
    @Column(name = "education_type", nullable = false)
    private String educationType;
    
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<MediaItem> media;
    
    @Column(name = "employee_type", nullable = false)
    private String employeeType;
    
    @Column(name = "province_code", nullable = false)
    private String provinceCode;
    
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
    
    @ManyToMany(mappedBy = "employees")
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
    public Employee() {
    }

    public Employee(String name) {
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getBirthday() {
		return birthday;
	}
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	public String getEducationType() {
		return educationType;
	}
	public void setEducationType(String educationType) {
		this.educationType = educationType;
	}
	public Set<MediaItem> getMedia() {
		return media;
	}
	public void setMedia(Set<MediaItem> media) {
		this.media = media;
	}
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

}

