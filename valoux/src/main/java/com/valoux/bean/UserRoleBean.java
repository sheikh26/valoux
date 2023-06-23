/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * This <java>class</java> UserRoleBean having DB table and columns names.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@Entity
@Table(name = "valoux_user_role")
public class UserRoleBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "urid")
	private Integer userRoleId;

	@Column(name = "rkey")
	private String relationKey;

	// @Column(name = "role_id")
	// private Integer roleId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private MasterRoleBean masterRoleBean;

	// @Column(name = "user_type_id")
	// private Integer userTypeId;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_type_id")
	private UserTypeBean userTypeBean;

	public Integer getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getRelationKey() {
		return relationKey;
	}

	public void setRelationKey(String relationKey) {
		this.relationKey = relationKey;
	}

	public MasterRoleBean getMasterRoleBean() {
		return masterRoleBean;
	}

	public void setMasterRoleBean(MasterRoleBean masterRoleBean) {
		this.masterRoleBean = masterRoleBean;
	}

	public UserTypeBean getUserTypeBean() {
		return userTypeBean;
	}

	public void setUserTypeBean(UserTypeBean userTypeBean) {
		this.userTypeBean = userTypeBean;
	}

}
