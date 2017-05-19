package com.archsystemsinc.pqrs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STATE_ZIP_CODE_REF")
public class StateZipCodeRef {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "STATE_ZIP_CODE_REF_ID")
	private String stateZipCodeRefId;
	
	@Column(name = "STATE_NAME")
	private String stateName;
	
	@Column(name = "STATE_CODE")
	private String stateCode;
	
	@Column(name = "ZIP_CODE")
	private String zipCode;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "COUNTY")
	private String county;

	public StateZipCodeRef(){}
	
	public StateZipCodeRef(String stateName, String stateCode, String zipCode, String city, String county){
		this.stateName = stateName;
		this.stateCode = stateCode;
		this.zipCode = zipCode;
		this.city = city;
		this.county = county;
	}
	
	public String getStateZipCodeRefId() {
		return stateZipCodeRefId;
	}

	public void setStateZipCodeRefId(String stateZipCodeRefId) {
		this.stateZipCodeRefId = stateZipCodeRefId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}
	
}
