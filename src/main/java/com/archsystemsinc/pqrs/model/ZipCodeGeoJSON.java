/**
 * 
 */
package com.archsystemsinc.pqrs.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * @author MurugarajKandaswamy
 *
 */
@Entity
@Table(name = "ZIP_CODE_GEO_JSON")
public class ZipCodeGeoJSON {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ZIP_CODE_GEO_JSON_ID")
    private String Id;

    @Column(name = "ZIP_CODE")
    private String zipCode; 
    
    @Lob
    @Column(name = "ZIP_CODE_GEO_JSON_OBJECT")
    private Blob zipCodeGeoJSON;

    
    public ZipCodeGeoJSON() { }
    
    public ZipCodeGeoJSON(String zipCode, Blob zipCodeGeoJSON) {
    	this.zipCode = zipCode;
    	this.zipCodeGeoJSON = zipCodeGeoJSON;
    }

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Blob getZipCodeGeoJSON() {
		return zipCodeGeoJSON;
	}

	public void setZipCodeGeoJSON(Blob zipCodeGeoJSON) {
		this.zipCodeGeoJSON = zipCodeGeoJSON;
	}
    

}
