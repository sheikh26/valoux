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
public class ValouxPearlModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer vpid;

	private String appraisedValue;

	private Integer blemish;

	private Integer color;

	private Integer composition;

	private String createdBy;

	private Date createdOn;

	private Integer drilled;

	private Integer enhancements;

	private Integer itemComponentId;

	private Integer itemId;

	private Integer luster;

	private String marketValue;

	private Integer matching;

	private String measurements;

	private String modifiedBy;

	private Date modifiedOn;

	private Integer pearlType;

	private Double pearlsLength;

	private Double pearlsMax;

	private Double pearlsMin;

	private Integer pstyle;

	private Integer shape;

	private Integer status;

	private Integer styleUserEntered;

	private String weight;
	
	private Integer origin;
	
	private String originName;

	public ValouxPearlModel() {
	}

	public Integer getVpid() {
		return this.vpid;
	}

	public void setVpid(Integer vpid) {
		this.vpid = vpid;
	}

	public Integer getBlemish() {
		return this.blemish;
	}

	public void setBlemish(Integer blemish) {
		this.blemish = blemish;
	}

	public Integer getColor() {
		return this.color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public Integer getComposition() {
		return this.composition;
	}

	public void setComposition(Integer composition) {
		this.composition = composition;
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

	public Integer getDrilled() {
		return this.drilled;
	}

	public void setDrilled(Integer drilled) {
		this.drilled = drilled;
	}

	public Integer getEnhancements() {
		return this.enhancements;
	}

	public void setEnhancements(Integer enhancements) {
		this.enhancements = enhancements;
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

	public Integer getLuster() {
		return this.luster;
	}

	public void setLuster(Integer luster) {
		this.luster = luster;
	}

	public Integer getMatching() {
		return this.matching;
	}

	public void setMatching(Integer matching) {
		this.matching = matching;
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

	public Integer getPearlType() {
		return this.pearlType;
	}

	public void setPearlType(Integer pearlType) {
		this.pearlType = pearlType;
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

	public Integer getStyleUserEntered() {
		return this.styleUserEntered;
	}

	public void setStyleUserEntered(Integer styleUserEntered) {
		this.styleUserEntered = styleUserEntered;
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

	public String getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}

	public Integer getOrigin() {
		return origin;
	}

	public void setOrigin(Integer origin) {
		this.origin = origin;
	}
	
	public Double getPearlsLength() {
		return pearlsLength;
	}

	public void setPearlsLength(Double pearlsLength) {
		this.pearlsLength = pearlsLength;
	}

	public Double getPearlsMax() {
		return pearlsMax;
	}

	public void setPearlsMax(Double pearlsMax) {
		this.pearlsMax = pearlsMax;
	}

	public Double getPearlsMin() {
		return pearlsMin;
	}

	public void setPearlsMin(Double pearlsMin) {
		this.pearlsMin = pearlsMin;
	}

	public Integer getPstyle() {
		return pstyle;
	}

	public void setPstyle(Integer pstyle) {
		this.pstyle = pstyle;
	}
	
	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

}