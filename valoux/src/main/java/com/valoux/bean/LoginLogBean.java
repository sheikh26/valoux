/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This <java>class</java> LoginLogBean having DB table and columns names.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@Entity
@Table(name = "valoux_login_log")
public class LoginLogBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "logid")
	private Integer logId;

	@Column(name = "rkey")
	private String relationKey;

	@Column(name = "ipaddress")
	private String ipAddress;

	@Column(name = "login_date")
	private Date loginDate;

	@Column(name = "logout_date")
	private Date logoutDate;

	@Column(name = "auth_login_code")
	private String authLoginCode;

	@Column(name = "log_status", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer logStatus;

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getRelationKey() {
		return relationKey;
	}

	public void setRelationKey(String relationKey) {
		this.relationKey = relationKey;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Date getLogoutDate() {
		return logoutDate;
	}

	public void setLogoutDate(Date logoutDate) {
		this.logoutDate = logoutDate;
	}

	public String getAuthLoginCode() {
		return authLoginCode;
	}

	public void setAuthLoginCode(String authLoginCode) {
		this.authLoginCode = authLoginCode;
	}

	public Integer getLogStatus() {
		return logStatus;
	}

	public void setLogStatus(Integer logStatus) {
		this.logStatus = logStatus;
	}

}
