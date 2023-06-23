/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This <java>class</java> ItemComponentCertificateBean having DB table and
 * columns names.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
@XmlRootElement
public class ValouxMetalModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer vmid;

	private Integer certificateId;

	private Integer color;

	private String createdBy;

	private Date createdOn;

	private Integer itemComponentId;

	private Integer itemId;

	private String marketValue;

	private String measurements;

	private Integer metalsType;

	private String modifiedBy;

	private Date modifiedOn;

	private Integer purity;

	private Integer status;

	private Byte typeDetermined;

	private String typeSpecified;

	private String weight;
	
	private String appraisedValue;
	
	private String specifiedValue;
	
	private Date specifiedDate;
	
	public String getSpecifiedValue() {
		return specifiedValue;
	}

	public void setSpecifiedValue(String specifiedValue) {
		this.specifiedValue = specifiedValue;
	}

	public Date getSpecifiedDate() {
		return specifiedDate;
	}

	public void setSpecifiedDate(Date specifiedDate) {
		this.specifiedDate = specifiedDate;
	}

	public ValouxMetalModel() {
	}

	public Integer getVmid() {
		return this.vmid;
	}

	public void setVmid(Integer vmid) {
		this.vmid = vmid;
	}

	public Integer getCertificateId() {
		return this.certificateId;
	}

	public void setCertificateId(Integer certificateId) {
		this.certificateId = certificateId;
	}

	public Integer getColor() {
		return this.color;
	}

	public void setColor(Integer color) {
		this.color = color;
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

	public Integer getItemComponentId() {
		return this.itemComponentId;
	}

	public void setItemComponentId(Integer itemComponentId) {
		this.itemComponentId = itemComponentId;
	}

	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getMarketValue() {
		return this.marketValue;
	}

	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}

	public String getMeasurements() {
		return this.measurements;
	}

	public void setMeasurements(String measurements) {
		this.measurements = measurements;
	}

	public Integer getMetalsType() {
		return this.metalsType;
	}

	public void setMetalsType(Integer metalsType) {
		this.metalsType = metalsType;
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

	public Integer getPurity() {
		return this.purity;
	}

	public void setPurity(Integer purity) {
		this.purity = purity;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Byte getTypeDetermined() {
		return this.typeDetermined;
	}

	public void setTypeDetermined(Byte typeDetermined) {
		this.typeDetermined = typeDetermined;
	}

	public String getTypeSpecified() {
		return this.typeSpecified;
	}

	public void setTypeSpecified(String typeSpecified) {
		this.typeSpecified = typeSpecified;
	}

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	public String getAppraisedValue() {
		return appraisedValue;
	}

	public void setAppraisedValue(String appraisedValue) {
		this.appraisedValue = appraisedValue;
	}

}