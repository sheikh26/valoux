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
@Table(name="valoux_access_permission")
public class ValouxAccessPermissionBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int apid;

	@Column(name="created_by", length=100)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on")
	private Date createdOn;

	@Column(name="given_permission_by", nullable=false, length=100)
	private String givenPermissionBy;

	@Column(name="given_permission_to", nullable=false, length=100)
	private String givenPermissionTo;

	@Column(name="is_added_appraisal")
	private Byte isAddedAppraisal;

	@Column(name="is_added_collection")
	private Byte isAddedCollection;

	@Column(name="is_added_item")
	private Byte isAddedItem;

	@Column(name="modified_by", length=100)
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_on")
	private Date modifiedOn;

	public ValouxAccessPermissionBean() {
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

	public Byte getIsAddedAppraisal() {
		return this.isAddedAppraisal;
	}

	public void setIsAddedAppraisal(Byte isAddedAppraisal) {
		this.isAddedAppraisal = isAddedAppraisal;
	}

	public Byte getIsAddedCollection() {
		return this.isAddedCollection;
	}

	public void setIsAddedCollection(Byte isAddedCollection) {
		this.isAddedCollection = isAddedCollection;
	}

	public Byte getIsAddedItem() {
		return this.isAddedItem;
	}

	public void setIsAddedItem(Byte isAddedItem) {
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