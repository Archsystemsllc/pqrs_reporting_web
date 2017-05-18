/**
 * 
 */
package pqrs_reporting_web;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.geojson.Geometry;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.hibernate.Hibernate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.archsystemsinc.pqrs.PersistenceConfig;
import com.archsystemsinc.pqrs.core.LoadGeoJSONApp;
import com.archsystemsinc.pqrs.model.StateGeoJson;
import com.archsystemsinc.pqrs.queryprocessor.Database;
import com.archsystemsinc.pqrs.repository.StateGeoJsonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class })
@Transactional
@TransactionConfiguration
public class LoadStateGeoJSONTest {

    @Autowired
    StateGeoJsonRepository stateGeoJsonRepository;

    @Before
    public void init() {
    	
    }
    
    @Test
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
                
                //java.sql.Blob blob = org.hibernate.Hibernate.createBlob(json.getBytes());
                
                Blob fileBlob = new javax.sql.rowset.serial.SerialBlob(jsonValue.getBytes());
                
                stateGeoJson = new StateGeoJson(stateName, fileBlob);
                
                //stateGeoJson = new StateGeoJson(stateName, stateGeometry);

                stateGeoJsonRepository.save(stateGeoJson);
                
                System.out.println("Endddddddddddddddddddddddddddddddddddddddd");

            } 
            assert(true);
            
        }catch(Exception e){
            e.printStackTrace();
            assert(false);
        }
        
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

            System.out.println("featureList Size::"+featureList.size());

            List<StateGeoJson> stateGeoJsons = new ArrayList<StateGeoJson>();
            StateGeoJson stateGeoJson = null;
            for (Feature feature : featureList) {
                System.out.println("feature.getProperties()::"+feature.getProperties());
                String stateName = feature.getProperties().get("NAME10").toString();
                System.out.println("Property::NAME10-->"+stateName);

                Geometry stateGeometry = (Geometry) feature.getGeometry();
                System.out.println("feature.getGeometry()::"+stateGeometry.toString());

                LngLatAlt latAlt = new LngLatAlt();
                
                List<Object> coordinates = stateGeometry.getCoordinates();
                System.out.println("geometryList.size()::"+coordinates.size());
                
                Object lngLatAlts = coordinates.get(0);
                
                System.out.println("Object Type::"+lngLatAlts.getClass());
                System.out.println("lngLatAlts::"+lngLatAlts.toString());
                
                ArrayList<LngLatAlt> lngLatAltList = null;
                
                if (lngLatAlts instanceof java.util.ArrayList) {
                	lngLatAltList = new ArrayList<LngLatAlt>();
                	
                	lngLatAltList = (java.util.ArrayList<LngLatAlt>) lngLatAlts;
                }
                
                System.out.println("lngLatAltList::Size-->"+lngLatAltList.size());
                
                Point point  = null;
                
                for (LngLatAlt lngLatAlt: lngLatAltList ) {
                	System.out.println("LngLatAlt::"+lngLatAlt.toString());

                	lngLatAlt.getLatitude();
                	
                	point = new org.geojson.Point(lngLatAlt);
                	
                	//stateGeoJson = new StateGeoJson(stateName, point);
                	
                	stateGeoJsonRepository.save(stateGeoJson);
                	
                	System.out.println("Long");
                	
                }
                
                //stateGeoJson = new StateGeoJson(stateName, stateGeometry);

                //stateGeoJsonRepository.save(stateGeoJson);

            } 
            assert(true);
            
        }catch(Exception e){
            e.printStackTrace();
            assert(false);
        }
        
    }
    
    
   // @Test
    public void storeStateSpatialPoint() {

        InputStream inStream = null; 
        BufferedInputStream bis = null;
        
        String sqlInsert = null;// "INSERT INTO PCHPSA(ZIP_CODE,STATE) VALUES(?,?)";
        Connection connection = null;
        Statement statement = null;
        
        try {
            connection = Database.getDBConnection();
            statement = connection.createStatement();
        	
            // open input stream test.txt for reading purpose
            //inStream = new FileInputStream("state.geo.json");
            inStream = LoadGeoJSONApp.class.getClassLoader().getResourceAsStream("state.geo.json");
            // input stream is converted to buffered input stream
            bis = new BufferedInputStream(inStream);
            FeatureCollection featureCollection =  new ObjectMapper().readValue(bis, FeatureCollection.class);

            List<Feature> featureList = featureCollection.getFeatures();

            System.out.println("featureList Size::"+featureList.size());

            List<StateGeoJson> stateGeoJsons = new ArrayList<StateGeoJson>();
            StateGeoJson stateGeoJson = null;
            for (Feature feature : featureList) {
                System.out.println("feature.getProperties()::"+feature.getProperties());
                String stateName = feature.getProperties().get("NAME10").toString();
                System.out.println("Property::NAME10-->"+stateName);

                Geometry stateGeometry = (Geometry) feature.getGeometry();
                System.out.println("feature.getGeometry()::"+stateGeometry.toString());

                LngLatAlt latAlt = new LngLatAlt();
                
                List<Object> coordinates = stateGeometry.getCoordinates();
                System.out.println("geometryList.size()::"+coordinates.size());
                
                Object lngLatAlts = coordinates.get(0);
                
                System.out.println("Object Type::"+lngLatAlts.getClass());
                System.out.println("lngLatAlts::"+lngLatAlts.toString());
                
                ArrayList<LngLatAlt> lngLatAltList = null;
                
                if (lngLatAlts instanceof java.util.ArrayList) {
                	lngLatAltList = new ArrayList<LngLatAlt>();
                	
                	lngLatAltList = (java.util.ArrayList<LngLatAlt>) lngLatAlts;
                }
                
                System.out.println("lngLatAltList::Size-->"+lngLatAltList.size());
                
                Point point  = null;
                
                for (LngLatAlt lngLatAlt: lngLatAltList ) {
                	System.out.println("LngLatAlt::"+lngLatAlt.toString());

                	lngLatAlt.getLatitude();
                	
                	point = new org.geojson.Point(lngLatAlt);
                	
                	//stateGeoJson = new StateGeoJson(stateName, point);
                	
                	//stateGeoJsonRepository.save(stateGeoJson);
                	
                	//sqlInsert = 	"INSERT INTO pqrs_reporting_web.STATE_GEO_JSON(STATE_NAME, STATE_POINT) VALUES ('"+stateName+"',ST_GeomFromText('POINT(" + lngLatAlt.get;
                	
                	
                	System.out.println("Long");
                	
                }
                
                //stateGeoJson = new StateGeoJson(stateName, stateGeometry);

                //stateGeoJsonRepository.save(stateGeoJson);

            } 
            assert(true);
            
        }catch(Exception e){
            e.printStackTrace();
            assert(false);
        }
        
    }
    
    
    


	
}
