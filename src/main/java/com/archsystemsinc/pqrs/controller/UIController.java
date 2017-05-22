package com.archsystemsinc.pqrs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archsystemsinc.pqrs.model.EPStateCount;
import com.archsystemsinc.pqrs.model.EPSummary;
import com.archsystemsinc.pqrs.model.EPZipCodeCount;
import com.archsystemsinc.pqrs.model.StateZipCodeRef;
import com.archsystemsinc.pqrs.queryprocessor.CustomRsqlVisitor;
import com.archsystemsinc.pqrs.repository.EPStateCountRepository;
import com.archsystemsinc.pqrs.repository.EPSummaryRepository;
import com.archsystemsinc.pqrs.repository.EPZipCodeCountRepository;
import com.archsystemsinc.pqrs.repository.StateZipCodeRefRepository;
import com.archsystemsinc.pqrs.utility.ReadGeoJSONUtil;
import com.archsystemsinc.pqrs.utils.ApplicationUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;

/**
 * 
 * @author lekan reju
 * 
 * UIController is a RestController. 
 *
 */
@RestController
public class UIController {

	private ObjectMapper objectMapper;
	
	@Autowired
    private EPStateCountRepository ePStateCountRepository;
	@Autowired
    private EPZipCodeCountRepository ePZipCodeCountRepository;
	@Autowired
    private EPSummaryRepository ePSummaryRepository;	
	@Autowired
	ReadGeoJSONUtil readGeoJSONUtil;
	@Autowired
	StateZipCodeRefRepository stateZipCodeRefRepository;
	
	public UIController() {
		super();
		objectMapper = new ObjectMapper();
	}

	/**
	 * 
	 * @param search
	 * @param dataset
	 * @return
	 * @throws JsonProcessingException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * 
	 * This method should be able to handle any dataset. 
	 * Each dataset needs SpringDataRepository that extends JpaSpecificationExecutor  
	 * The Entity needs to have 3 fields annotated with the @XAxis, @YAxis and @ZAxis. 
	 * See EPSummary.java and EPSummaryRepository.java
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/charts/dataset/{dataset}")
	public String findAllForChartsByRsql(@RequestParam(value = "search") String search, @PathVariable("dataset") String dataset) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		Node rootNode = new RSQLParser().parse(search);
		Specification<EPSummary> spec = rootNode.accept(new CustomRsqlVisitor<EPSummary>());
		List<EPSummary> epSummaryData = ePSummaryRepository.findAll(spec);
		return "var data = " + objectMapper.writeValueAsString(ApplicationUtils.getBarChartData(epSummaryData, EPSummary.class));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/maps/dataset/{dataset}")
	public String findAllForMapsByRsql(@PathVariable("dataset") String dataset,@RequestParam(value = "search") String search) throws JsonProcessingException {
		FeatureCollection featureCollection = new FeatureCollection();
		if(dataset.equalsIgnoreCase("ByOy3EpState")) {
			Node rootNode = new RSQLParser().parse(search);
			Specification<EPStateCount> spec = rootNode.accept(new CustomRsqlVisitor<EPStateCount>());
			List<EPStateCount> epCounts = ePStateCountRepository.findAll(spec);
			for(EPStateCount epCount: epCounts) {
				Feature feature = new Feature();
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put("NumberOfEPs", epCount.getNumberOfEPs());
				properties.put("Area", epCount.getAreaType());
				properties.put("id", epCount.getId());
				properties.put("State", epCount.getStateCode());
				feature.setProperties(properties);
				feature.setGeometry(readGeoJSONUtil.findGeometryByState(epCount.getStateCode()));
				featureCollection.add(feature);
			}
		}
		return "var data = " + objectMapper.writeValueAsString(featureCollection);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/maps/dataset/{dataset}/state/{state}")
	public String findZipCodesForMapsByRsql(@PathVariable("dataset") String dataset,@RequestParam(value = "search") String search, @PathVariable(value = "state") String state) throws JsonProcessingException {
		FeatureCollection featureCollection = new FeatureCollection();
		if(dataset.equalsIgnoreCase("ByOy3EpZipCode") && null!=state) {
			List<StateZipCodeRef> stateZipCodeRefs = stateZipCodeRefRepository.findByStateName(state);
			Node rootNode = new RSQLParser().parse(search);
			Specification<EPZipCodeCount> spec = rootNode.accept(new CustomRsqlVisitor<EPZipCodeCount>());
			List<EPZipCodeCount> epCounts = ePZipCodeCountRepository.findAll(spec);
			Map<String, EPZipCodeCount> map = new HashMap<String, EPZipCodeCount>();
			for(EPZipCodeCount epCount: epCounts) {
				map.put(epCount.getZipCode(), epCount);
			}
			Feature feature = new Feature();
			Map<String, Object> properties = new HashMap<String, Object>();
			for(StateZipCodeRef stateZipCodeRef: stateZipCodeRefs) {
				feature = new Feature();
				EPZipCodeCount epCount = map.get(stateZipCodeRef.getZipCode());
				if(epCount!=null) {
					properties = new HashMap<String, Object>();
					properties.put("NumberOfEPs", epCount.getNumberOfEPs());
					properties.put("Area", epCount.getAreaType());
					properties.put("Id", epCount.getId());
					properties.put("ZipCode", epCount.getZipCode());
					properties.put("State", epCount.getStateCode());
					feature.setProperties(properties);
				}
				feature.setGeometry(readGeoJSONUtil.findGeometryByZipCode(stateZipCodeRef.getZipCode()));
				featureCollection.add(feature);
			}
		}
		return "var data = " + objectMapper.writeValueAsString(featureCollection);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/maps/state/{state}")
	public String findFeaturesForState(@PathVariable(value = "state") String state) throws JsonProcessingException {
		FeatureCollection featureCollection = new FeatureCollection();
		Feature feature = new Feature();
		Map<String, Object> properties = new HashMap<String, Object>();
		feature = new Feature();
		properties = new HashMap<String, Object>();
		properties.put("NumberOfEPs", 0);
		properties.put("Area", "State");
		properties.put("Id", 0);
		properties.put("State", state);
		feature.setProperties(properties);
		feature.setGeometry(readGeoJSONUtil.findGeometryByZipCode(state));
		featureCollection.add(feature);
		return "var stateData = " + objectMapper.writeValueAsString(featureCollection);
	}
}
