/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * This <java>class</java> ItemComponentCertificateBean having DB table and columns
 * names.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
@XmlRootElement
public class ValouxAccessPermissionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private int apid;

	private String createdBy;

	private Date createdOn;

	private String givenPermissionBy;

	private String givenPermissionTo;

	private Integer isAddedAppraisal;

	private Integer isAddedCollection;

	private Integer isAddedItem;

	private String modifiedBy;

	private Date modifiedOn;

	public ValouxAccessPermissionModel() {
	}

	public int getApid() {
		return this.apid;
	}

	public void setApid(int apid) {
		this.apid = apid;
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

	public String getGivenPermissionBy() {
		return this.givenPermissionBy;
	}

	public void setGivenPermissionBy(String givenPermissionBy) {
		this.givenPermissionBy = givenPermissionBy;
	}

	public String getGivenPermissionTo() {
		return this.givenPermissionTo;
	}

	public void setGivenPermissionTo(String givenPermissionTo) {
		this.givenPermissionTo = givenPermissionTo;
	}

	public Integer getIsAddedAppraisal() {
		return this.isAddedAppraisal;
	}

	public void setIsAddedAppraisal(Integer isAddedAppraisal) {
		this.isAddedAppraisal = isAddedAppraisal;
	}

	public Integer getIsAddedCollection() {
		return this.isAddedCollection;
	}

	public void setIsAddedCollection(Integer isAddedCollection) {
		this.isAddedCollection = isAddedCollection;
	}

	public Integer getIsAddedItem() {
		return this.isAddedItem;
	}

	public void setIsAddedItem(Integer isAddedItem) {
		this.isAddedItem = isAddedItem;
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