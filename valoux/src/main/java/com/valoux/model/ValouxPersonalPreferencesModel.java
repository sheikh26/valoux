/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This <java>class</java> ValouxPersonalPreferencesModel having all the
 * properties setters and getters methods.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
@XmlRootElement
public class ValouxPersonalPreferencesModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer PersonalId;

	private String personalPreferences;

	private Integer ppstatus;

	private Integer ppType;

	public Integer getPersonalId() {
		return PersonalId;
	}

	public void setPersonalId(Integer personalId) {
		PersonalId = personalId;
	}

	public String getPersonalPreferences() {
		return personalPreferences;
	}

	public void setPersonalPreferences(String personalPreferences) {
		this.personalPreferences = personalPreferences;
	}

	public Integer getPpstatus() {
		return ppstatus;
	}

	public void setPpstatus(Integer ppstatus) {
		this.ppstatus = ppstatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getPpType() {
		return ppType;
	}

	public void setPpType(Integer ppType) {
		this.ppType = ppType;
	}

}
