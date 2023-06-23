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
 * This <java>class</java> LoginBean having DB table and columns names.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@Entity
@Table(name = "valoux_login")
public class LoginBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "lid")
	private Integer loginId;

	@Column(name = "un")
	private String userName;

	@Column(name = "pw")
	private String password;

	@Column(name = "fname")
	private String firstName;

	@Column(name = "mname")
	private String middleName;

	@Column(name = "lname")
	private String lastName;

	@Column(name = "ustatus", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer userStatus;

	@Column(name = "auth_code")
	private String authenticationCode;

	@Column(name = "forget_password_key")
	private String forgetPasswordKey;

	@Column(name = "pkey")
	private String privateKey;

	@Column(name = "auth_code_mobile")
	private String authCodeMobile;

	public Integer getLoginId() {
		return loginId;
	}

	public void setLoginId(Integer loginId) {
		this.loginId = loginId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public String getAuthenticationCode() {
		return authenticationCode;
	}

	public void setAuthenticationCode(String authenticationCode) {
		this.authenticationCode = authenticationCode;
	}

	public String getForgetPasswordKey() {
		return forgetPasswordKey;
	}

	public void setForgetPasswordKey(String forgetPasswordKey) {
		this.forgetPasswordKey = forgetPasswordKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getAuthCodeMobile() {
		return authCodeMobile;
	}

	public void setAuthCodeMobile(String authCodeMobile) {
		this.authCodeMobile = authCodeMobile;
	}

}
