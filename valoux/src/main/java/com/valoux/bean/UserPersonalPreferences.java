/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * This <java>class</java> UserPersonalPreferences having DB table and columns
 * names.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@Entity
@Table(name = "valoux_consumer_pp")
public class UserPersonalPreferences extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cppid")
	private int personalPreferencesId;

	@Column(name = "rkey")
	private String relationKey;

	// @Column(name = "preferences_id")
	// private int preferencesId;

	// bi-directional many-to-one association to ValouxPersonalPreference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "preferences_id", nullable = false)
	private ValouxPersonalPreferencesBean valouxPersonalPreferencesBean;

	public int getPersonalPreferencesId() {
		return personalPreferencesId;
	}

	public void setPersonalPreferencesId(int personalPreferencesId) {
		this.personalPreferencesId = personalPreferencesId;
	}

	public String getRelationKey() {
		return relationKey;
	}

	public void setRelationKey(String relationKey) {
		this.relationKey = relationKey;
	}

	public ValouxPersonalPreferencesBean getValouxPersonalPreferencesBean() {
		return valouxPersonalPreferencesBean;
	}

	public void setValouxPersonalPreferencesBean(ValouxPersonalPreferencesBean valouxPersonalPreferencesBean) {
		this.valouxPersonalPreferencesBean = valouxPersonalPreferencesBean;
	}

}
