/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This <java>class</java> ValousInterestInModel having all the properties
 * setters and getters methods.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@XmlRootElement
public class ValouxInterestInModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer interestId;

	private String interest;

	private Integer status;

	public Integer getInterestId() {
		return interestId;
	}

	public void setInterestId(Integer interestId) {
		this.interestId = interestId;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
