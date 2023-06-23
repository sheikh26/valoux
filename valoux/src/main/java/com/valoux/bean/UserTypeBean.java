/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This <java>class</java> UserTypeBean having DB table and columns names.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@Entity
@Table(name = "valoux_user_type")
public class UserTypeBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "utid")
	private Integer userTypeId;

	@Column(name = "rkey")
	private String relationKey;

	@Column(name = "utype", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer userType;
	
	// bi-directional many-to-one association to ValouxUserRole
	@OneToMany(mappedBy = "userTypeBean", fetch = FetchType.LAZY)
	private List<UserRoleBean> userRoleBean;

	public Integer getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getRelationKey() {
		return relationKey;
	}

	public void setRelationKey(String relationKey) {
		this.relationKey = relationKey;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public List<UserRoleBean> getUserRoleBean() {
		return userRoleBean;
	}

	public void setUserRoleBean(List<UserRoleBean> userRoleBean) {
		this.userRoleBean = userRoleBean;
	}

}
