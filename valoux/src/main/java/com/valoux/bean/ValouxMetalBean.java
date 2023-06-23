/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.bean;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

/**
 * This <java>class</java> ValouxMetalBean having DB table and columns names.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
@Entity
@Table(name="valoux_metals")
public class ValouxMetalBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer vmid;

	@Column(name="appraised_value")
	private Double appraisedValue;

	@Column(name="color")
	private Integer color;

	@Column(name="created_by", length=100)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on", nullable=false)
	private Date createdOn;

//	@Column(name="item_component_id", nullable=false)
//	private Integer itemComponentId;

	// bi-directional many-to-one association to ValouxItemComponent
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_component_id")
	private ValouxItemComponentBean valouxItemComponent;
	

//	@Column(name="item_id", nullable=false)
//	private Integer itemId;
	
	// bi-directional many-to-one association to ValouxItem
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private ValouxItemBean valouxItem;

	@Column(name="market_value")
	private Double marketValue;

	@Column(name="measurements", length=100)
	private String measurements;

	@Column(name="metals_type")
	private Integer metalsType;

	@Column(name="modified_by", length=100)
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_on")
	private Date modifiedOn;

	private Integer purity;

	@Column(nullable=false)
	private Byte status;

	@Column(name="type_determined")
	private Byte typeDetermined;

	@Column(name="type_specified", length=100)
	private String typeSpecified;

	private Double weight;

	public ValouxMetalBean() {
	}

	public Integer getVmid() {
		return this.vmid;
	}

	public void setVmid(Integer vmid) {
		this.vmid = vmid;
	}

	public Double getAppraisedValue() {
		return this.appraisedValue;
	}

	public void setAppraisedValue(Double appraisedValue) {
		this.appraisedValue = appraisedValue;
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

	public Double getMarketValue() {
		return this.marketValue;
	}

	public void setMarketValue(Double marketValue) {
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

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
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

	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

}