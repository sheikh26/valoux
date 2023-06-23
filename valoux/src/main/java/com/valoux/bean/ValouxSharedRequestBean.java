/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This <java>class</java> ValouxSharedRequestBean having all the properties
 * setters and getters methods.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@Entity
@Table(name = "valoux_shared_request")
public class ValouxSharedRequestBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "srid")
	private Integer sharedRequestId;

	@Column(name = "shared_to")
	private String sharedTo;

	@Column(name = "shared_by")
	private String sharedBy;

	@Column(name = "shared_to_email")
	private String sharedToEmail;

	@Column(name = "shared_item_id")
	private Integer sharedItemId;

	@Column(name = "shared_item_type", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer sharedItemType;

	@Column(name = "shared_item_permission", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer sharedItemPermission;

	@Column(name = "is_registered_user", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer isRegisteredUser;

	@Column(name = "approve_status", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer approveStatus;

	@Column(name = "status", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer status;

	public Integer getSharedRequestId() {
		return sharedRequestId;
	}

	public void setSharedRequestId(Integer sharedRequestId) {
		this.sharedRequestId = sharedRequestId;
	}

	public String getSharedTo() {
		return sharedTo;
	}

	public void setSharedTo(String sharedTo) {
		this.sharedTo = sharedTo;
	}

	public String getSharedToEmail() {
		return sharedToEmail;
	}

	public void setSharedToEmail(String sharedToEmail) {
		this.sharedToEmail = sharedToEmail;
	}

	public Integer getSharedItemId() {
		return sharedItemId;
	}

	public void setSharedItemId(Integer sharedItemId) {
		this.sharedItemId = sharedItemId;
	}

	public Integer getSharedItemType() {
		return sharedItemType;
	}

	public void setSharedItemType(Integer sharedItemType) {
		this.sharedItemType = sharedItemType;
	}

	public Integer getSharedItemPermission() {
		return sharedItemPermission;
	}

	public void setSharedItemPermission(Integer sharedItemPermission) {
		this.sharedItemPermission = sharedItemPermission;
	}

	public Integer getIsRegisteredUser() {
		return isRegisteredUser;
	}

	public void setIsRegisteredUser(Integer isRegisteredUser) {
		this.isRegisteredUser = isRegisteredUser;
	}

	public Integer getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(Integer approveStatus) {
		this.approveStatus = approveStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSharedBy() {
		return sharedBy;
	}

	public void setSharedBy(String sharedBy) {
		this.sharedBy = sharedBy;
	}

}
