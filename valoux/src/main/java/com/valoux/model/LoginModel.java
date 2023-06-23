/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This <java>class</java> LoginModel having all the properties setters and
 * getters methods.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@XmlRootElement
public class LoginModel extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer loginId;

	private String userName;

	private String password;

	private String firstName;

	private String middleName;

	private String lastName;

	private Integer userStatus;

	private String authenticationCode;

	private String forgetPasswordKey;

	private String privateKey;

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
