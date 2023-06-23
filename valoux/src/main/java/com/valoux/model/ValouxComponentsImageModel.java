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
public class ValouxComponentsImageModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer cid;

	private String createdBy;

	private Date createdOn;

	private Integer fileType;

	private String imgName;

	private Integer imgStatus;

	private String imgUrl;

	private Integer itemComponentId;

	private String modifiedBy;

	private Date modifiedOn;

	public ValouxComponentsImageModel() {
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

	public Integer getFileType() {
		return this.fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public String getImgName() {
		return this.imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public Integer getImgStatus() {
		return this.imgStatus;
	}

	public void setImgStatus(Integer imgStatus) {
		this.imgStatus = imgStatus;
	}

	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getItemComponentId() {
		return this.itemComponentId;
	}

	public void setItemComponentId(Integer itemComponentId) {
		this.itemComponentId = itemComponentId;
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