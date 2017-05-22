package com.archsystemsinc.pqrs.model;

import java.util.List;

public class BarChartData {

	private List<Object> labels;
	private List<Dataset> datasets;
	public List<Object> getLabels() {
		return labels;
	}
	public void setLabels(List<Object> labels) {
		this.labels = labels;
	}
	public List<Dataset> getDatasets() {
		return datasets;
	}
	public void setDatasets(List<Dataset> datasets) {
		this.datasets = datasets;
	}
}
