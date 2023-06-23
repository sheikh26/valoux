/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * This <java>class</java> ValouxCollectionItemBean having DB table and columns
 * names.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
@Entity
@Table(name = "valoux_collection_items")
public class ValouxCollectionItemBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer vcid;

	// @Column(name = "collection_id", nullable = false)
	// private Integer collectionId;

	// bi-directional many-to-one association to ValouxCollection
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "collection_id")
	private ValouxCollectionBean valouxCollectionBean;

	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn;

	// @Column(name = "item_id", nullable = false)
	// private Integer itemId;

	// bi-directional many-to-one association to ValouxItem
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private ValouxItemBean valouxItemBean;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on")
	private Date modifiedOn;

	private byte status;

	public ValouxCollectionItemBean() {
	}

	public Integer getVcid() {
		return this.vcid;
	}

	public void setVcid(Integer vcid) {
		this.vcid = vcid;
	}

	public ValouxCollectionBean getValouxCollectionBean() {
		return valouxCollectionBean;
	}

	public void setValouxCollectionBean(ValouxCollectionBean valouxCollectionBean) {
		this.valouxCollectionBean = valouxCollectionBean;
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

	public ValouxItemBean getValouxItemBean() {
		return valouxItemBean;
	}

	public void setValouxItemBean(ValouxItemBean valouxItemBean) {
		this.valouxItemBean = valouxItemBean;
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

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

}