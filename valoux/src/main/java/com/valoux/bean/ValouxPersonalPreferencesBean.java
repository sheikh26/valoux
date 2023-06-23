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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * This <java>class</java> ValouxPersonalPreferencesBean having all the
 * properties setters and getters methods.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@Entity
@Table(name = "valoux_personal_preferences")
public class ValouxPersonalPreferencesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pid")
	private Integer PersonalId;

	@Column(name = "ppreferences")
	private String personalPreferences;

	@Column(name = "ppstatus", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer ppstatus;

	@Column(name = "pp_type", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer ppType;

	// bi-directional many-to-one association to ValouxConsumerPp
	@OneToMany(mappedBy = "valouxPersonalPreferencesBean", fetch = FetchType.LAZY)
	private List<UserPersonalPreferences> userPersonalPreferences;


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

	public List<UserPersonalPreferences> getUserPersonalPreferences() {
		return userPersonalPreferences;
	}

	public void setUserPersonalPreferences(List<UserPersonalPreferences> userPersonalPreferences) {
		this.userPersonalPreferences = userPersonalPreferences;
	}


}
