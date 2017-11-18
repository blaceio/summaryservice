package io.blace.microservices.summaryservice.http;

import java.util.Date;

public class SummaryUpdate {

	private Date start;
	private Date end;
	private String product;
	private String type;
	private String category;
	private String division;
	private String region;
	private double updateby;
	
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public double getUpdateby() {
		return updateby;
	}
	public void setUpdateby(double updateby) {
		this.updateby = updateby;
	}
	@Override
	public String toString() {
		return "SummaryUpdate [start=" + start + ", end=" + end + ", product=" + product + ", type=" + type
				+ ", category=" + category + ", division=" + division + ", region=" + region + ", updateby=" + updateby
				+ "]";
	}
		
}
