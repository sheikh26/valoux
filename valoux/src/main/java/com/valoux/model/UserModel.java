/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jettison.json.JSONArray;

/**
 * This <java>class</java> UserModel having all the properties setters and
 * getters methods.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@XmlRootElement
public class UserModel extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer customerId;

	private String relationKey;

	private String emailId;

	private String alternateEmailId;

	private String globalAddress;

	private String streetNo;

	private String addressLine1;

	private String addressLine2;

	private String city;

	private Integer stateId;

	private Integer countryId;

	private String zipCode;

	private String mobile;

	private String alternateMobile;

	private Date dateOfBirth;

	private String company;

	private String passwordResetQuetion;

	private String passwordResetAnswer;

	private Integer gender;

	private Integer maritalStatus;

	private Integer incomeRange;

	private String ip;

	private String zip4;

	private String facebook;

	private String google;

	private String instagparam;

	private String twitter;

	private String salutation;

	private JSONArray interestedIn;

	private JSONArray personalPreferences;

	private String countryName;

	private String stateName;

	private String dob;

	private String imageURL;

	private String imageCaption;

	private JSONArray consumerAbout;

	private JSONArray JewelryTypes;

	private JSONArray JewelryDesign;

	private JSONArray JewelryStyle;

	private JSONArray JewelryPurchases;

	private JSONArray JewelryInsurance;

	private JSONArray JewelryService;

	private JSONArray JewelryDocumentation;

	private JSONArray metals;

	private JSONArray gemstones;

	private JSONArray diamonds;

	private JSONArray jewelryComponents;

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

	public JSONArray getPersonalPreferences() {
		return personalPreferences;
	}

	public void setPersonalPreferences(JSONArray personalPreferences) {
		this.personalPreferences = personalPreferences;
	}

	public JSONArray getInterestedIn() {
		return interestedIn;
	}

	public void setInterestedIn(JSONArray interestedIn) {
		this.interestedIn = interestedIn;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
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

	public JSONArray getConsumerAbout() {
		return consumerAbout;
	}

	public void setConsumerAbout(JSONArray consumerAbout) {
		this.consumerAbout = consumerAbout;
	}

	public JSONArray getJewelryComponents() {
		return jewelryComponents;
	}

	public void setJewelryComponents(JSONArray jewelryComponents) {
		this.jewelryComponents = jewelryComponents;
	}

	public JSONArray getJewelryTypes() {
		return JewelryTypes;
	}

	public void setJewelryTypes(JSONArray jewelryTypes) {
		JewelryTypes = jewelryTypes;
	}

	public JSONArray getJewelryDesign() {
		return JewelryDesign;
	}

	public void setJewelryDesign(JSONArray jewelryDesign) {
		JewelryDesign = jewelryDesign;
	}

	public JSONArray getJewelryStyle() {
		return JewelryStyle;
	}

	public void setJewelryStyle(JSONArray jewelryStyle) {
		JewelryStyle = jewelryStyle;
	}

	public JSONArray getJewelryPurchases() {
		return JewelryPurchases;
	}

	public void setJewelryPurchases(JSONArray jewelryPurchases) {
		JewelryPurchases = jewelryPurchases;
	}

	public JSONArray getJewelryInsurance() {
		return JewelryInsurance;
	}

	public void setJewelryInsurance(JSONArray jewelryInsurance) {
		JewelryInsurance = jewelryInsurance;
	}

	public JSONArray getJewelryService() {
		return JewelryService;
	}

	public void setJewelryService(JSONArray jewelryService) {
		JewelryService = jewelryService;
	}

	public JSONArray getJewelryDocumentation() {
		return JewelryDocumentation;
	}

	public void setJewelryDocumentation(JSONArray jewelryDocumentation) {
		JewelryDocumentation = jewelryDocumentation;
	}

	public JSONArray getMetals() {
		return metals;
	}

	public void setMetals(JSONArray metals) {
		this.metals = metals;
	}

	public JSONArray getGemstones() {
		return gemstones;
	}

	public void setGemstones(JSONArray gemstones) {
		this.gemstones = gemstones;
	}

	public JSONArray getDiamonds() {
		return diamonds;
	}

	public void setDiamonds(JSONArray diamonds) {
		this.diamonds = diamonds;
	}

}
