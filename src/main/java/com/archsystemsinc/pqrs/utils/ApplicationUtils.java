package com.archsystemsinc.pqrs.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.archsystemsinc.pqrs.model.BarChartData;
import com.archsystemsinc.pqrs.model.Dataset;
import com.archsystemsinc.pqrs.model.annotation.XAxis;
import com.archsystemsinc.pqrs.model.annotation.YAxis;
import com.archsystemsinc.pqrs.model.annotation.ZAxis;

public class ApplicationUtils {

	public static BarChartData getBarChartData(List<?> iCharts, Class<?> clazz) throws IllegalArgumentException, IllegalAccessException {
    	Field xAxisField = null;
    	Field yAxisField = null;
    	Field zAxisField = null;
    	List<Object>uniqueXAxisValues = new ArrayList<Object>();
    	List<Object>uniqueZAxisValues = new ArrayList<Object>();
    	Field[] fields = clazz.getDeclaredFields();
    	for(Field field: fields) {
    		if(field.isAnnotationPresent(XAxis.class)) {
    			xAxisField = field;
    		}
    		if(field.isAnnotationPresent(YAxis.class)) {
    			yAxisField = field;
    		}
    		if(field.isAnnotationPresent(ZAxis.class)) {
    			zAxisField = field;
    		}
    	}
    	BarChartData barChartData = new BarChartData();
    	Map<String, Object> map = new HashMap<String, Object>();
    	for(Object iChart: iCharts) {
    		Object obj = clazz.cast(iChart);
    		Object xAxisValue = xAxisField.get(obj);
    		if(!uniqueXAxisValues.contains(xAxisValue)) uniqueXAxisValues.add(xAxisValue);
    		Object zAxisValue = zAxisField.get(obj);
    		if(!uniqueZAxisValues.contains(zAxisValue)) uniqueZAxisValues.add(zAxisValue);
    		map.put(xAxisValue.toString()+zAxisValue.toString(), yAxisField.get(obj));
    	}
    	
    	List<Dataset> datasets = new ArrayList();
    	for(Object uniqueZAxisValue: uniqueZAxisValues) {
    		Dataset dataset = new Dataset();
    		dataset.setLabel(uniqueZAxisValue.toString());
    		dataset.setBackgroundColor("");
    		List<Object> objects = new ArrayList();
    		for(Object uniqueXAxisValue: uniqueXAxisValues) {
    			objects.add(map.get(uniqueXAxisValue.toString()+uniqueZAxisValue.toString()));
    		}
    		dataset.setData(objects);
    		datasets.add(dataset);
    	}
    	barChartData.setLabels(uniqueXAxisValues);
    	barChartData.setDatasets(datasets);
    	return barChartData;
    }
}
