package com.valoux.model;

public class UserRoleModel {

	private Integer userRoleId;

	private String relationKey;

	private Integer masterRoleBeanId;

	private Integer userTypeBeanId;

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

	public Integer getMasterRoleBeanId() {
		return masterRoleBeanId;
	}

	public void setMasterRoleBeanId(Integer masterRoleBeanId) {
		this.masterRoleBeanId = masterRoleBeanId;
	}

	public Integer getUserTypeBeanId() {
		return userTypeBeanId;
	}

	public void setUserTypeBeanId(Integer userTypeBeanId) {
		this.userTypeBeanId = userTypeBeanId;
	}

}
