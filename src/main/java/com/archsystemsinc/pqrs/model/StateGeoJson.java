package com.archsystemsinc.pqrs.model;

import org.geojson.Geometry;
import org.geojson.Point;
import org.hibernate.annotations.Type;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Created by MurugarajKandaswam on 5/12/2017.
 */
@Entity
@Table(name = "STATE_GEO_JSON")
public class StateGeoJson {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "STATE_GEO_JSON_ID")
    private String Id;

    @Column(name = "STATE_NAME")
    private String stateName;
    
    @Lob
    @Column(name = "STATE_GEO_JSON_OBJECT")
    private Blob stateGeoJSON;

    public StateGeoJson(String stateName, Blob stateGeoJSON) {
    	this.stateName = stateName;
    	this.stateGeoJSON = stateGeoJSON;
    }
    
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

	public Blob getStateGeoJSON() {
		return stateGeoJSON;
	}

	public void setStateGeoJSON(Blob stateGeoJSON) {
		this.stateGeoJSON = stateGeoJSON;
	}

    
}
