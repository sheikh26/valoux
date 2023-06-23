/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This <java>class</java> ValouxCollectionModel having getter and setters.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
@XmlRootElement
public class ValouxCollectionModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer vcid;

	private String cname;

	private Integer collectionStatus;

	private String createdBy;

	private Date createdOn;

	private String modifiedBy;

	private Date modifiedOn;

	private String rkey;

	private String shortDescription;

	private String requestType;
	
	private Date lastAppraisedDate;

	private String lastAppraiserId;
	
	private Integer lastAppraisalId;
	
	private String name;
	
	private Integer status;
	
	private String modifiedByName;
	
	private String description;
	
	private List<ValouxCollectionImageModel> imageModels;
	
	public List<ValouxCollectionImageModel> getImageModels() {
		return imageModels;
	}

	public void setImageModels(List<ValouxCollectionImageModel> imageModels) {
		this.imageModels = imageModels;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getModifiedByName() {
		return modifiedByName;
	}

	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}

	public Integer getLastAppraisalId() {
		return lastAppraisalId;
	}

	public void setLastAppraisalId(Integer lastAppraisalId) {
		this.lastAppraisalId = lastAppraisalId;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVcid() {
		return vcid;
	}

	public void setVcid(Integer vcid) {
		this.vcid = vcid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Integer getCollectionStatus() {
		return collectionStatus;
	}

	public void setCollectionStatus(Integer collectionStatus) {
		this.collectionStatus = collectionStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getRkey() {
		return rkey;
	}

	public void setRkey(String rkey) {
		this.rkey = rkey;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public Date getLastAppraisedDate() {
		return lastAppraisedDate;
	}

	public void setLastAppraisedDate(Date lastAppraisedDate) {
		this.lastAppraisedDate = lastAppraisedDate;
	}

	public String getLastAppraiserId() {
		return lastAppraiserId;
	}

	public void setLastAppraiserId(String lastAppraiserId) {
		this.lastAppraiserId = lastAppraiserId;
	}
	
	
}
