/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * This <java>class</java> ValouxGemstoneBean having DB table and columns names.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
@Entity
@Table(name="valoux_gemstones")
public class ValouxGemstoneBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer vgid;

	@Column(name="created_by", length=100)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on", nullable=false)
	private Date createdOn;

	private Integer cut;

	private Integer enhancement;

	@Column(name="external_inclusions")
	private Integer externalInclusions;

	@Column(name="gemstones_type", nullable=false)
	private Integer gemstonesType;

	@Column(name="internal_inclusions")
	private Integer internalInclusions;

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

	private String measurements;

	@Column(name="modified_by", length=100)
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_on")
	private Date modifiedOn;

	private Integer origin;

	private Integer placement;

	private Integer shape;

	@Column(nullable=false)
	private Byte status;

	private Double weight;

	public ValouxGemstoneBean() {
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

	public Integer getExternalInclusions() {
		return this.externalInclusions;
	}

	public void setExternalInclusions(Integer externalInclusions) {
		this.externalInclusions = externalInclusions;
	}

	public Integer getGemstonesType() {
		return this.gemstonesType;
	}

	public void setGemstonesType(Integer gemstonesType) {
		this.gemstonesType = gemstonesType;
	}

	public Integer getInternalInclusions() {
		return this.internalInclusions;
	}

	public void setInternalInclusions(Integer internalInclusions) {
		this.internalInclusions = internalInclusions;
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

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

}