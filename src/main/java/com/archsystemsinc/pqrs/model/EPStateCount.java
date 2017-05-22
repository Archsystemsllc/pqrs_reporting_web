package com.archsystemsinc.pqrs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BY_OY3_EP_STATE")
public class EPStateCount {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="state_code")
	private String stateCode;
	
	@Column(name="count_of_eps")
	private Integer numberOfEPs;
	
	@Column(name="area_type")
	private String areaType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public Integer getNumberOfEPs() {
		return numberOfEPs;
	}

	public void setNumberOfEPs(Integer numberOfEPs) {
		this.numberOfEPs = numberOfEPs;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}
}
