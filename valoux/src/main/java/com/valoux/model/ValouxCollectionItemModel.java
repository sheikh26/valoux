/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jettison.json.JSONArray;

/**
 * This <java>class</java> ValouxCollectionItemModel having getter and setters.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
@XmlRootElement
public class ValouxCollectionItemModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer vcid;

	private Integer collectionId;

	private String createdBy;

	private Date createdOn;

	private JSONArray items;

	private String modifiedBy;

	private Date modifiedOn;

	private Integer status;

	private Integer itemId;

	private String requestType;

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public Integer getVcid() {
		return vcid;
	}

	public void setVcid(Integer vcid) {
		this.vcid = vcid;
	}

	public Integer getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Integer collectionId) {
		this.collectionId = collectionId;
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

	public JSONArray getItems() {
		return items;
	}

	public void setItems(JSONArray items) {
		this.items = items;
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

	public Integer isStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

}
