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
 * This <java>class</java> AgentProfileBean having DB table and columns names.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
@Entity
@Table(name = "valoux_agent")
public class AgentBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "aid")
	private Integer agentId;

	@Column(name = "rkey")
	private String relationKey;

	@Column(name = "pemail")
	private String emailId;

	@Column(name = "alternate_email")
	private String alternateEmailId;

	@Column(name = "gaddress")
	private String globalAddress;

	@Column(name = "street_number")
	private String streetNo;

	@Column(name = "add_line_1")
	private String addressLine1;

	@Column(name = "add_line2")
	private String addressLine2;

	@Column(name = "city")
	private String city;

	@Column(name = "state_id")
	private Integer stateId;

	@Column(name = "country_id")
	private Integer countryId;

	@Column(name = "zipcode")
	private String zipCode;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "alternate_mobile")
	private String alternateMobile;

	@Column(name = "reset_question")
	private String passwordResetQuetion;

	@Column(name = "reset_answer")
	private String passwordResetAnswer;

	@Column(name = "ip")
	private String ip;
	
	@Column(name = "sign_url")
	private String signUrl;
	
	@Column(name = "sign_name")
	private String signName;
	
	public String getSignUrl() {
		return signUrl;
	}

	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public String getRelationKey() {
		return relationKey;
	}

	public void setRelationKey(String relationKey) {
		this.relationKey = relationKey;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAlternateEmailId() {
		return alternateEmailId;
	}

	public void setAlternateEmailId(String alternateEmailId) {
		this.alternateEmailId = alternateEmailId;
	}

	public String getGlobalAddress() {
		return globalAddress;
	}

	public void setGlobalAddress(String globalAddress) {
		this.globalAddress = globalAddress;
	}

	public String getStreetNo() {
		return streetNo;
	}

	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAlternateMobile() {
		return alternateMobile;
	}

	public void setAlternateMobile(String alternateMobile) {
		this.alternateMobile = alternateMobile;
	}

	public String getPasswordResetQuetion() {
		return passwordResetQuetion;
	}

	public void setPasswordResetQuetion(String passwordResetQuetion) {
		this.passwordResetQuetion = passwordResetQuetion;
	}

	public String getPasswordResetAnswer() {
		return passwordResetAnswer;
	}

	public void setPasswordResetAnswer(String passwordResetAnswer) {
		this.passwordResetAnswer = passwordResetAnswer;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
