/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This <java>class</java> ItemComponentCertificateBean having DB table and
 * columns names.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
@XmlRootElement
public class ValouxItemComponentModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer vicid;

	private Byte componentsType;

	private String createdBy;

	private Date createdOn;

	private Integer itemId;

	private String modifiedBy;

	private Date modifiedOn;

	private String name;

	private Integer quantity;

	private Byte vicStatus;
	
	private String adjustmentPrice;

	private String brandPriceAdjustmentNotes;

	private Byte brandPriceAdjustmentOperator;

	private Byte brandPriceAdjustmentType;

	private String brandPriceAdjustmentValue;

	private String currentUnitPrice;

	private Double finalPrice;

	private String generalPriceAdjustmentNotes;

	private Byte generalPriceAdjustmentOperator;

	private Byte generalPriceAdjustmentType;

	private String generalPriceAdjustmentValue;

	private Double marketValue;

	private Double purchasePrice;

	public ValouxItemComponentModel() {
	}

	public Integer getVicid() {
		return this.vicid;
	}

	public void setVicid(Integer vicid) {
		this.vicid = vicid;
	}

	public Byte getComponentsType() {
		return this.componentsType;
	}

	public void setComponentsType(Byte componentsType) {
		this.componentsType = componentsType;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return this.modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Byte getVicStatus() {
		return this.vicStatus;
	}

	public void setVicStatus(Byte vicStatus) {
		this.vicStatus = vicStatus;
	}

	public String getAdjustmentPrice() {
		return adjustmentPrice;
	}

	public void setAdjustmentPrice(String adjustmentPrice) {
		this.adjustmentPrice = adjustmentPrice;
	}

	public String getBrandPriceAdjustmentNotes() {
		return brandPriceAdjustmentNotes;
	}

	public void setBrandPriceAdjustmentNotes(String brandPriceAdjustmentNotes) {
		this.brandPriceAdjustmentNotes = brandPriceAdjustmentNotes;
	}

	public Byte getBrandPriceAdjustmentOperator() {
		return brandPriceAdjustmentOperator;
	}

	public void setBrandPriceAdjustmentOperator(Byte brandPriceAdjustmentOperator) {
		this.brandPriceAdjustmentOperator = brandPriceAdjustmentOperator;
	}

	public Byte getBrandPriceAdjustmentType() {
		return brandPriceAdjustmentType;
	}

	public void setBrandPriceAdjustmentType(Byte brandPriceAdjustmentType) {
		this.brandPriceAdjustmentType = brandPriceAdjustmentType;
	}

	public String getBrandPriceAdjustmentValue() {
		return brandPriceAdjustmentValue;
	}

	public void setBrandPriceAdjustmentValue(String brandPriceAdjustmentValue) {
		this.brandPriceAdjustmentValue = brandPriceAdjustmentValue;
	}

	public String getCurrentUnitPrice() {
		return currentUnitPrice;
	}

	public void setCurrentUnitPrice(String currentUnitPrice) {
		this.currentUnitPrice = currentUnitPrice;
	}

	public Double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getGeneralPriceAdjustmentNotes() {
		return generalPriceAdjustmentNotes;
	}

	public void setGeneralPriceAdjustmentNotes(String generalPriceAdjustmentNotes) {
		this.generalPriceAdjustmentNotes = generalPriceAdjustmentNotes;
	}

	public Byte getGeneralPriceAdjustmentOperator() {
		return generalPriceAdjustmentOperator;
	}

	public void setGeneralPriceAdjustmentOperator(
			Byte generalPriceAdjustmentOperator) {
		this.generalPriceAdjustmentOperator = generalPriceAdjustmentOperator;
	}

	public Byte getGeneralPriceAdjustmentType() {
		return generalPriceAdjustmentType;
	}

	public void setGeneralPriceAdjustmentType(Byte generalPriceAdjustmentType) {
		this.generalPriceAdjustmentType = generalPriceAdjustmentType;
	}

	public String getGeneralPriceAdjustmentValue() {
		return generalPriceAdjustmentValue;
	}

	public void setGeneralPriceAdjustmentValue(String generalPriceAdjustmentValue) {
		this.generalPriceAdjustmentValue = generalPriceAdjustmentValue;
	}

	public Double getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(Double marketValue) {
		this.marketValue = marketValue;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

}