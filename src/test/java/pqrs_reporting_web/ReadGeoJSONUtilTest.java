package pqrs_reporting_web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.geojson.GeoJsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.archsystemsinc.pqrs.PersistenceConfig;
import com.archsystemsinc.pqrs.model.StateGeoJson;
import com.archsystemsinc.pqrs.repository.StateGeoJsonRepository;
import com.archsystemsinc.pqrs.repository.ZipCodeGeoJsonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class })
@Transactional
@TransactionConfiguration
public class ReadGeoJSONUtilTest {

	@Autowired
	StateGeoJsonRepository stateGeoJsonRepository;

	@Autowired
	ZipCodeGeoJsonRepository zipCodeGeoJsonRepository;
	
	String stateName = null;
    @Before
    public void init() {
    	stateName = "Maryland";
    }
	
    @Test
    public void testFindGeoMetryByState() {
    	
    	try {
    		
    		StateGeoJson stateGeoJSON = stateGeoJsonRepository.findByStateName(stateName);
    		
    		InputStream inputStream = stateGeoJSON.getStateGeoJSON().getBinaryStream();
    		String geoJSON = getStringFromInputStream(inputStream);
    		GeoJsonObject geoJsonObject = new ObjectMapper().readValue(geoJSON, GeoJsonObject.class);
    		
    		System.out.println("geoJSON::String-->"+geoJSON);
    		
    		assert(true);
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    		assert(false);
    	}

    }
    
	// convert InputStream to String
	private static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
    
}
