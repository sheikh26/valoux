/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This <java>class</java> StateModel having all the properties setters and
 * getters methods.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@XmlRootElement
public class StateModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer stateId;

	private String shortCode;

	private String name;

	private Integer countryId;

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

}
