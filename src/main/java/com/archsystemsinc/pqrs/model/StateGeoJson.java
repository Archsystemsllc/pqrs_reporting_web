package com.archsystemsinc.pqrs.model;

import org.geojson.Geometry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by MurugarajKandaswam on 5/12/2017.
 */
@Entity
@Table(name = "STATE_GEO_JSON")
public class StateGeoJson { 

	@javax.persistence.Id
    @Column(name = "STATE_GEO_JSON_ID")
    private String Id;

    @Column(name = "STATE_NAME")
    private String stateName;

    @Column(name = "STATE_GEOMETRY")
    private Geometry stateGeometry;

    public StateGeoJson(String stateName, Geometry stateGeometry) {
        this.stateName = stateName;
        this.stateGeometry = stateGeometry;
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

    public Geometry getStateGeometry() {
        return stateGeometry;
    }

    public void setStateGeometry(Geometry stateGeometry) {
        this.stateGeometry = stateGeometry;
    }
}
