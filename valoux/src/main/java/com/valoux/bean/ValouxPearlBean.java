/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.bean;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

/**
 * This <java>class</java> ValouxPearlBean having DB table and columns names.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
@Entity
@Table(name="valoux_pearls")
public class ValouxPearlBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer vpid;

	@Column(name="appraised_value")
	private Double appraisedValue;

	private Integer blemish;

	private Integer color;

	private Integer composition;

	@Column(name="created_by", length=100)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on", nullable=false)
	private Date createdOn;

	private Integer drilled;

	private Integer enhancements;

//	@Column(name="item_component_id", nullable=false)
//	private Integer itemComponentId;

//	@Column(name="item_id", nullable=false)
//	private Integer itemId;

	private Integer luster;

	private Integer matching;

	@Column(name="measurements", length=100)
	private String measurements;

	@Column(name="modified_by", length=100)
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_on")
	private Date modifiedOn;

	private Integer origin;

	@Column(name="pearl_type", nullable=false)
	private Integer pearlType;

	@Column(name="pearls_length")
	private Double pearlsLength;

	@Column(name="pearls_max")
	private Double pearlsMax;

	@Column(name="pearls_min")
	private Double pearlsMin;

	@Column(name="pearls_style")
	private Integer pearlsStyle;

	private Integer shape;

	@Column(nullable=false)
	private Byte status;

	@Column(name="style_user_entered")
	private Integer styleUserEntered;

	private Double weight;
	
	// bi-directional many-to-one association to ValouxItem
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private ValouxItemBean valouxItem;
	
	// bi-directional many-to-one association to ValouxItemComponent
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_component_id")
	private ValouxItemComponentBean valouxItemComponent;

	public ValouxPearlBean() {
	}

	public Integer getVpid() {
		return this.vpid;
	}

	public void setVpid(Integer vpid) {
		this.vpid = vpid;
	}

	public Double getAppraisedValue() {
		return this.appraisedValue;
	}

	public void setAppraisedValue(Double appraisedValue) {
		this.appraisedValue = appraisedValue;
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

	
	public ValouxItemComponentBean getValouxItemComponent() {
		return valouxItemComponent;
	}

	public void setValouxItemComponent(ValouxItemComponentBean valouxItemComponent) {
		this.valouxItemComponent = valouxItemComponent;
	}

	public ValouxItemBean getValouxItem() {
		return valouxItem;
	}

	public void setValouxItem(ValouxItemBean valouxItem) {
		this.valouxItem = valouxItem;
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

	public Integer getOrigin() {
		return this.origin;
	}

	public void setOrigin(Integer origin) {
		this.origin = origin;
	}

	public Integer getPearlType() {
		return this.pearlType;
	}

	public void setPearlType(Integer pearlType) {
		this.pearlType = pearlType;
	}

	public Double getPearlsLength() {
		return this.pearlsLength;
	}

	public void setPearlsLength(Double pearlsLength) {
		this.pearlsLength = pearlsLength;
	}

	public Double getPearlsMax() {
		return this.pearlsMax;
	}

	public void setPearlsMax(Double pearlsMax) {
		this.pearlsMax = pearlsMax;
	}

	public Double getPearlsMin() {
		return this.pearlsMin;
	}

	public void setPearlsMin(Double pearlsMin) {
		this.pearlsMin = pearlsMin;
	}

	public Integer getPearlsStyle() {
		return this.pearlsStyle;
	}

	public void setPearlsStyle(Integer pearlsStyle) {
		this.pearlsStyle = pearlsStyle;
	}

	public Integer getShape() {
		return this.shape;
	}

	public void setShape(Integer shape) {
		this.shape = shape;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Integer getStyleUserEntered() {
		return this.styleUserEntered;
	}

	public void setStyleUserEntered(Integer styleUserEntered) {
		this.styleUserEntered = styleUserEntered;
	}

	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

}