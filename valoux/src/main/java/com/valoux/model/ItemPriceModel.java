package com.valoux.model;

public class ItemPriceModel {
	
	private Integer id;
	
	private Double perUnitPrice;
	
	private Double priceAMorPM;
	
	private Double calculatedPrice;
	
	private Double weight;
	
	private String newestAvailableDate;
	
	private boolean isDataAvailable;
	
	private Integer purityKarat;
	
	private Integer metalType;
	
	private String metalName;
	
	String metalsTypeQuality;
	
	public String getMetalsTypeQuality() {
		return metalsTypeQuality;
	}

	public void setMetalsTypeQuality(String metalsTypeQuality) {
		this.metalsTypeQuality = metalsTypeQuality;
	}

	public Integer getMetalType() {
		return metalType;
	}

	public void setMetalType(Integer metalType) {
		this.metalType = metalType;
	}

	public String getMetalName() {
		return metalName;
	}

	public void setMetalName(String metalName) {
		this.metalName = metalName;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getPriceAMorPM() {
		return priceAMorPM;
	}

	public void setPriceAMorPM(Double priceAMorPM) {
		this.priceAMorPM = priceAMorPM;
	}

	public String getNewestAvailableDate() {
		return newestAvailableDate;
	}

	public void setNewestAvailableDate(String newestAvailableDate) {
		this.newestAvailableDate = newestAvailableDate;
	}

	public Double getPerUnitPrice() {
		return perUnitPrice;
	}

	public void setPerUnitPrice(Double perUnitPrice) {
		this.perUnitPrice = perUnitPrice;
	}

	public boolean isDataAvailable() {
		return isDataAvailable;
	}

	public void setDataAvailable(boolean isDataAvailable) {
		this.isDataAvailable = isDataAvailable;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getCalculatedPrice() {
		return calculatedPrice;
	}

	public void setCalculatedPrice(Double calculatedPrice) {
		this.calculatedPrice = calculatedPrice;
	}

	public Integer getPurityKarat() {
		return purityKarat;
	}

	public void setPurityKarat(Integer purityKarat) {
		this.purityKarat = purityKarat;
	}
	
}
