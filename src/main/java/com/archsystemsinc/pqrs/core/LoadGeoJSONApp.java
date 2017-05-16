package com.archsystemsinc.pqrs.core;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Geometry;
import org.springframework.beans.factory.annotation.Autowired;

import com.archsystemsinc.pqrs.model.StateGeoJson;
import com.archsystemsinc.pqrs.repository.StateGeoJsonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by MurugarajKandaswam on 5/12/2017.
 */
public class LoadGeoJSONApp {


    @Autowired
    StateGeoJsonRepository stateGeoJsonRepository;

    private void storeStateGeometry() {

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

                stateGeoJson = new StateGeoJson(stateName, stateGeometry);

                stateGeoJsonRepository.save(stateGeoJson);

            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String [] args) {

        new LoadGeoJSONApp().storeStateGeometry();

    }

}