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
 * This <java>class</java> UserBean having DB table and columns names.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@Entity
@Table(name = "valoux_consumer")
public class UserBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cid")
	private Integer customerId;

	@Column(name = "rkey")
	private String relationKey;

	@Column(name = "pemail")
	private String emailId;

	@Column(name = "alternate_email")
	private String alternateEmailId;

	@Column(name = "gaddress")
	private String globalAddress;

	@Column(name = "street_no")
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

	@Column(name = "dob")
	private Date dateOfBirth;

	@Column(name = "company")
	private String company;

	@Column(name = "reset_question")
	private String passwordResetQuetion;

	@Column(name = "reset_answer")
	private String passwordResetAnswer;

	@Column(name = "gender", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer gender;

	@Column(name = "marital_status", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer maritalStatus;

	@Column(name = "income_range", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer incomeRange;

	@Column(name = "ip")
	private String ip;

	@Column(name = "zip_4")
	private String zip4;

	@Column(name = "facebook")
	private String facebook;

	@Column(name = "google")
	private String google;

	@Column(name = "instagparam")
	private String instagparam;

	@Column(name = "twitter")
	private String twitter;

	@Column(name = "salutation")
	private String salutation;

	@Column(name = "img_url")
	private String imageURL;

	@Column(name = "img_caption")
	private String imageCaption;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(Integer maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Integer getIncomeRange() {
		return incomeRange;
	}

	public void setIncomeRange(Integer incomeRange) {
		this.incomeRange = incomeRange;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getZip4() {
		return zip4;
	}

	public void setZip4(String zip4) {
		this.zip4 = zip4;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getGoogle() {
		return google;
	}

	public void setGoogle(String google) {
		this.google = google;
	}

	public String getInstagparam() {
		return instagparam;
	}

	public void setInstagparam(String instagparam) {
		this.instagparam = instagparam;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getImageCaption() {
		return imageCaption;
	}

	public void setImageCaption(String imageCaption) {
		this.imageCaption = imageCaption;
	}

}
