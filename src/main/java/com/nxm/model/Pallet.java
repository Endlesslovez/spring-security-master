package com.nxm.model;


import java.time.LocalDate;
import java.util.Set;

import javax.persistence.*;

import lombok.Data;
@Data
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
    
}
