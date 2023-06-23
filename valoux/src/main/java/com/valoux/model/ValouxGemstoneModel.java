/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jettison.json.JSONArray;

/**
 * This <java>class</java> ItemComponentCertificateBean having DB table and
 * columns names.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
@XmlRootElement
public class ValouxGemstoneModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer vgid;

	private String createdBy;

	private Date createdOn;

	private Integer cut;

	private Integer enhancement;

//	private Integer externalInclusions;

	private Integer gemstonesType;

//	private Integer internalInclusions;

	private Integer itemComponentId;

	private Integer itemId;

	private String marketValue;

	private String measurements;

	private String modifiedBy;

	private Date modifiedOn;

	private Integer placement;

	private Integer shape;

	private Integer status;

	private String weight;
	
	private Integer origin;
	
	private String originName;
	
	private JSONArray internalInclusions;
	
	private JSONArray externalInclusions;
	
	public JSONArray getInternalInclusions() {
		return internalInclusions;
	}

	public void setInternalInclusions(JSONArray internalInclusions) {
		this.internalInclusions = internalInclusions;
	}

	public JSONArray getExternalInclusions() {
		return externalInclusions;
	}

	public void setExternalInclusions(JSONArray externalInclusions) {
		this.externalInclusions = externalInclusions;
	}

	
	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public ValouxGemstoneModel() {
	}

	public Integer getVgid() {
		return this.vgid;
	}

	public void setVgid(Integer vgid) {
		this.vgid = vgid;
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

	public Integer getCut() {
		return this.cut;
	}

	public void setCut(Integer cut) {
		this.cut = cut;
	}

	public Integer getEnhancement() {
		return this.enhancement;
	}

	public void setEnhancement(Integer enhancement) {
		this.enhancement = enhancement;
	}

	public Integer getGemstonesType() {
		return this.gemstonesType;
	}

	public void setGemstonesType(Integer gemstonesType) {
		this.gemstonesType = gemstonesType;
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

	public String getMeasurements() {
		return this.measurements;
	}

	public void setMeasurements(String measurements) {
		this.measurements = measurements;
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

	public Integer getOrigin() {
		return this.origin;
	}

	public void setOrigin(Integer origin) {
		this.origin = origin;
	}

	public Integer getPlacement() {
		return this.placement;
	}

	public void setPlacement(Integer placement) {
		this.placement = placement;
	}

	public Integer getShape() {
		return this.shape;
	}

	public void setShape(Integer shape) {
		this.shape = shape;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

//	public Integer getExternalInclusions() {
//		return externalInclusions;
//	}
//
//	public void setExternalInclusions(Integer externalInclusions) {
//		this.externalInclusions = externalInclusions;
//	}
//
//	public Integer getInternalInclusions() {
//		return internalInclusions;
//	}
//
//	public void setInternalInclusions(Integer internalInclusions) {
//		this.internalInclusions = internalInclusions;
//	}

	public String getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}

}