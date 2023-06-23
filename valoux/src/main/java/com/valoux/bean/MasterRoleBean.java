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
 * This <java>class</java> MasterRoleBean having DB table and columns names.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@Entity
@Table(name = "valoux_role")
public class MasterRoleBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "rid")
	private Integer roleId;

	@Column(name = "role_name", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer roleName;

	@Column(name = "rstatus", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer roleStatus;
	
	@OneToMany(mappedBy="masterRoleBean",fetch = FetchType.LAZY)
	private List<UserRoleBean> userRoleBean;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getRoleName() {
		return roleName;
	}

	public void setRoleName(Integer roleName) {
		this.roleName = roleName;
	}

	public Integer getRoleStatus() {
		return roleStatus;
	}

	public void setRoleStatus(Integer roleStatus) {
		this.roleStatus = roleStatus;
	}

	public List<UserRoleBean> getUserRoleBean() {
		return userRoleBean;
	}

	public void setUserRoleBean(List<UserRoleBean> userRoleBean) {
		this.userRoleBean = userRoleBean;
	}
	

}
