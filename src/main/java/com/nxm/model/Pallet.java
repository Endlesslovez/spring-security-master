package com.nxm.model;


import java.time.LocalDate;
import java.util.Set;

import javax.persistence.*;

@Entity(name = "tbl_pallet")
public class Pallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private long id;

    @Column(name = "col_areaid")
    private String areaId;
    
    
	@Column(name = "col_palletnumber")
    private long palletNumber;
    
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "pallet")
    private Set<PalletPosition> palletPositions;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public long getPalletNumber() {
		return palletNumber;
	}

	public void setPalletNumber(long palletNumber) {
		this.palletNumber = palletNumber;
	}

	public Set<PalletPosition> getPalletPositions() {
		return palletPositions;
	}

	public void setPalletPositions(Set<PalletPosition> palletPositions) {
		this.palletPositions = palletPositions;
	}
    
    
}
