/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * This <java>class</java> ValouxCollectionBean having DB table and columns
 * names.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
@Entity
@Table(name = "valoux_components_images")
public class ValouxComponentsImageBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer cid;

	@Column(name = "created_by", length = 100)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "file_type")
	private Byte fileType;

	@Column(name = "img_name", length = 200)
	private String imgName;

	@Column(name = "img_status")
	private Byte imgStatus;

	@Column(name = "img_url", length = 250)
	private String imgUrl;

//	@Column(name = "item_component_id", nullable = false)
//	private Integer itemComponentId;
	
	// bi-directional many-to-one association to ValouxItemComponent
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_component_id")
	private ValouxItemComponentBean valouxItemComponent;

	@Column(name = "modified_by", length = 100)
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on")
	private Date modifiedOn;

	public ValouxComponentsImageBean() {
	}

	public Integer getCid() {
		return this.cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
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

	public Byte getFileType() {
		return this.fileType;
	}

	public void setFileType(Byte fileType) {
		this.fileType = fileType;
	}

	public String getImgName() {
		return this.imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public Byte getImgStatus() {
		return this.imgStatus;
	}

	public void setImgStatus(Byte imgStatus) {
		this.imgStatus = imgStatus;
	}

	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public ValouxItemComponentBean getValouxItemComponent() {
		return valouxItemComponent;
	}

	public void setValouxItemComponent(ValouxItemComponentBean valouxItemComponent) {
		this.valouxItemComponent = valouxItemComponent;
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