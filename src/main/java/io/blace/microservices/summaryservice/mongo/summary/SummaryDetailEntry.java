package io.blace.microservices.summaryservice.mongo.summary;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class SummaryDetailEntry {

	@Id
	private String id;	
	
	private Date period;
	private Product product;
	private double actual;
	private double budget;
	private double forecast;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getPeriod() {
		return period;
	}
	public void setPeriod(Date period) {
		this.period = period;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public double getActual() {
		return actual;
	}
	public void setActual(double actual) {
		this.actual = actual;
	}
	public double getBudget() {
		return budget;
	}
	public void setBudget(double budget) {
		this.budget = budget;
	}
	public double getForecast() {
		return forecast;
	}
	public void setForecast(double forecast) {
		this.forecast = forecast;
	}
	@Override
	public String toString() {
		return "SummaryDetailEntry [id=" + id + ", period=" + period + ", product=" + product + ", actual=" + actual
				+ ", budget=" + budget + ", forecast=" + forecast + "]";
	}
}
