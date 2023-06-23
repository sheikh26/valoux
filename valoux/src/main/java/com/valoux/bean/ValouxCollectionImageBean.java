/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * This <java>class</java> ValouxCollectionImageBean having DB table and columns
 * names.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
@Entity
@Table(name = "valoux_collection_images")
public class ValouxCollectionImageBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer id;

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

	@Column(name = "img_name", length = 200)
	private String imgName;

	@Column(name = "img_status")
	private byte imgStatus;

	@Column(name = "img_url", length = 250)
	private String imgUrl;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on")
	private Date modifiedOn;

	public ValouxCollectionImageBean() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getImgName() {
		return this.imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public byte getImgStatus() {
		return this.imgStatus;
	}

	public void setImgStatus(byte imgStatus) {
		this.imgStatus = imgStatus;
	}

	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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

}