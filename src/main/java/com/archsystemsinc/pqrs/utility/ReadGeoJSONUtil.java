package com.archsystemsinc.pqrs.utility;

import java.io.InputStream;

import org.geojson.GeoJsonObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.archsystemsinc.pqrs.model.StateGeoJson;
import com.archsystemsinc.pqrs.model.ZipCodeGeoJSON;
import com.archsystemsinc.pqrs.repository.StateGeoJsonRepository;
import com.archsystemsinc.pqrs.repository.ZipCodeGeoJsonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadGeoJSONUtil {

	@Autowired
	StateGeoJsonRepository stateGeoJsonRepository;

	@Autowired
	ZipCodeGeoJsonRepository zipCodeGeoJsonRepository;
	
	public GeoJsonObject findGeometryByState(String stateName) {
		GeoJsonObject geoJsonObject = null;
    	try {
    		StateGeoJson stateGeoJSON = stateGeoJsonRepository.findByStateName(stateName);
    		InputStream inputStream = stateGeoJSON.getStateGeoJSON().getBinaryStream();
    		String geoJSONString = CommonUtil.getStringFromInputStream(inputStream);
    		System.out.println("geoJSON(state:"+stateName+")::String-->"+geoJSONString);
    		
    		geoJsonObject = new ObjectMapper().readValue(geoJSONString, GeoJsonObject.class);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return geoJsonObject;
		
	}
	
	public GeoJsonObject findGeometryByZipCode(String zipCode) {
		GeoJsonObject geoJsonObject = null;
    	try {
    		ZipCodeGeoJSON zipCodeGeoJSON = zipCodeGeoJsonRepository.findByZipCode(zipCode);
    		InputStream inputStream = zipCodeGeoJSON.getZipCodeGeoJSON().getBinaryStream();
    		String geoJSONString = CommonUtil.getStringFromInputStream(inputStream);
    		System.out.println("geoJSON(ZipCode:"+zipCode+")::String-->"+geoJSONString);
    		
    		geoJsonObject = new ObjectMapper().readValue(geoJSONString, GeoJsonObject.class);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return geoJsonObject;
		
	}

	
}
