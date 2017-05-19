/**
 * 
 */
package pqrs_reporting_web;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.geojson.Geometry;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.archsystemsinc.pqrs.PersistenceConfig;
import com.archsystemsinc.pqrs.core.LoadGeoJSONApp;
import com.archsystemsinc.pqrs.model.StateGeoJson;
import com.archsystemsinc.pqrs.model.StateZipCodeRef;
import com.archsystemsinc.pqrs.model.ZipCodeGeoJSON;
import com.archsystemsinc.pqrs.repository.StateGeoJsonRepository;
import com.archsystemsinc.pqrs.repository.StateZipCodeRefRepository;
import com.archsystemsinc.pqrs.repository.ZipCodeGeoJsonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class })
@Transactional
@TransactionConfiguration
public class LoadStateGeoJSONTest {

    @Autowired
    StateGeoJsonRepository stateGeoJsonRepository;

    @Autowired
    ZipCodeGeoJsonRepository ZipCodeGeoJsonRepository;
    
    @Autowired
    StateZipCodeRefRepository stateZipCodeRefRepository;
    
    @Before
    public void init() { }
    
    //@Test
    public void storeStateJSONObject() {

        InputStream inStream = null; 
        BufferedInputStream bis = null;
        StateGeoJson stateGeoJson = null;
        try {
            // open input stream test.txt for reading purpose
            //inStream = new FileInputStream("state.geo.json");
            inStream = LoadGeoJSONApp.class.getClassLoader().getResourceAsStream("2state.geo.json");
            // input stream is converted to buffered input stream
            bis = new BufferedInputStream(inStream);
            
            FeatureCollection featureCollection =  new ObjectMapper().readValue(bis, FeatureCollection.class);
            
            for (Feature feature : featureCollection.getFeatures()) {
                String stateName = feature.getProperties().get("NAME10").toString();
                System.out.println("Property::NAME10(StateName)-->"+stateName);

                GeoJsonObject stateGeoJsonObject =  feature.getGeometry();
                String jsonValue = new ObjectMapper().writeValueAsString(stateGeoJsonObject);
                Blob fileBlob = new javax.sql.rowset.serial.SerialBlob(jsonValue.getBytes());
                
                stateGeoJson = new StateGeoJson(stateName, fileBlob);
                stateGeoJsonRepository.save(stateGeoJson);
            }
            
            assert(true);
            
        }catch(Exception e){
            e.printStackTrace();
            assert(false);
        }
        
    }
    
    
    //@Test
    public void storeZipCodeJSONObject() {
    	
        InputStream inStream = null; 
        BufferedInputStream bis = null;
        ZipCodeGeoJSON zipCodeGeoJSON = null;
        try {
            // open input stream test.txt for reading purpose
            //inStream = new FileInputStream("state.geo.json");
            inStream = LoadGeoJSONApp.class.getClassLoader().getResourceAsStream("split22.zcta5.geo.json");
            // input stream is converted to buffered input stream
            bis = new BufferedInputStream(inStream);
            Map<String, String> zipCodeMap = getZipCodeByState("PA");
            
            FeatureCollection featureCollection =  new ObjectMapper().readValue(bis, FeatureCollection.class);
            
            for (Feature feature : featureCollection.getFeatures()) {
                String zipCode = feature.getProperties().get("ZCTA5CE10").toString();
                System.out.println("Property::ZCTA5CE10(zipCode)-->"+zipCode);

                if(!zipCodeMap.containsKey(zipCode)) continue;
                
                GeoJsonObject zipCodeGeoJsonObject =  feature.getGeometry();
                String jsonValue = new ObjectMapper().writeValueAsString(zipCodeGeoJsonObject);
                Blob jsonBlob = new javax.sql.rowset.serial.SerialBlob(jsonValue.getBytes());
                
                zipCodeGeoJSON = new ZipCodeGeoJSON(zipCode, jsonBlob);
                ZipCodeGeoJsonRepository.save(zipCodeGeoJSON);
            } 
            
            assert(true);
            
        }catch(Exception e){
            e.printStackTrace();
            assert(false);
        }
        
    }
    
    
    private Map<String, String> getZipCodeByState(String stateCode) {
    	
    	Map<String, String> zipCodeMap = new HashMap<String, String>();
    	
    	List<StateZipCodeRef> stateZipCodeRefList = (List<StateZipCodeRef>) stateZipCodeRefRepository.findAll();
    	
    	for (StateZipCodeRef stateZipCodeRef : stateZipCodeRefList) {
    		if (stateZipCodeRef.getStateCode().trim().equalsIgnoreCase(stateCode)) {
    			zipCodeMap.put(stateZipCodeRef.getZipCode(), stateCode);
    		}
    	}
    	
    	return zipCodeMap;
    }
    
    
    //@Test
    public void storeStateGeometry() {

        InputStream inStream = null; 
        BufferedInputStream bis = null;
        try {
            // open input stream test.txt for reading purpose
            //inStream = new FileInputStream("state.geo.json");
            inStream = LoadGeoJSONApp.class.getClassLoader().getResourceAsStream("state.geo.json");
            // input stream is converted to buffered input stream
            bis = new BufferedInputStream(inStream);
            FeatureCollection featureCollection =  new ObjectMapper().readValue(bis, FeatureCollection.class);

            List<Feature> featureList = featureCollection.getFeatures();

            List<StateGeoJson> stateGeoJsons = new ArrayList<StateGeoJson>();
            StateGeoJson stateGeoJson = null;
            for (Feature feature : featureList) {
                String stateName = feature.getProperties().get("NAME10").toString();
                System.out.println("Property::NAME10-->"+stateName);

                Geometry stateGeometry = (Geometry) feature.getGeometry();
                System.out.println("feature.getGeometry()::"+stateGeometry.toString());

                LngLatAlt latAlt = new LngLatAlt();
                List<Object> coordinates = stateGeometry.getCoordinates();
                
                Object lngLatAlts = coordinates.get(0);
                
                ArrayList<LngLatAlt> lngLatAltList = null;
                
                if (lngLatAlts instanceof java.util.ArrayList) {
                	lngLatAltList = new ArrayList<LngLatAlt>();
                	
                	lngLatAltList = (java.util.ArrayList<LngLatAlt>) lngLatAlts;
                }
                
                Point point  = null;
                for (LngLatAlt lngLatAlt: lngLatAltList ) {
                	lngLatAlt.getLatitude();
                	
                	point = new org.geojson.Point(lngLatAlt);
                	//stateGeoJson = new StateGeoJson(stateName, point);
                	stateGeoJsonRepository.save(stateGeoJson);
                }
            } 

            assert(true);
            
        }catch(Exception e){
            e.printStackTrace();
            assert(false);
        }
        
    }
    
}
