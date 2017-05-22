package com.archsystemsinc.pqrs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.archsystemsinc.pqrs.model.annotation.XAxis;
import com.archsystemsinc.pqrs.model.annotation.YAxis;
import com.archsystemsinc.pqrs.model.annotation.ZAxis;

@Entity
@Table(name="BY_OY3_EP_SUMMARY")
public class EPSummary implements IChart{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@XAxis
	public String parameter;
	@ZAxis
	@Column(name="yes_or_no")
	public String yesNo;
	private Integer count;
	@YAxis
	public Float percent;
	private String type;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getYesNo() {
		return yesNo;
	}
	public void setYesNo(String yesNo) {
		this.yesNo = yesNo;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Float getPercent() {
		return percent;
	}
	public void setPercent(Float percent) {
		this.percent = percent;
	}
	
	
}
