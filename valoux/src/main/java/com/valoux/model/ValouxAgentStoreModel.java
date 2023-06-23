/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This <java>class</java> ValouxAgentStoreModel having getter and setters.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
@XmlRootElement
public class ValouxAgentStoreModel {

	private static final long serialVersionUID = 1L;

	Integer agentStoreId;

	String relationKey;

	Integer storeId;

	private Date createdOn;

	private String createdBy;

	private Date modifiedOn;

	private String modifiedBy;

	public Integer getAgentStoreId() {
		return agentStoreId;
	}

	public void setAgentStoreId(Integer agentStoreId) {
		this.agentStoreId = agentStoreId;
	}

	public String getRelationKey() {
		return relationKey;
	}

	public void setRelationKey(String relationKey) {
		this.relationKey = relationKey;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
