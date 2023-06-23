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
 * This <java>class</java> ValouxInterestInBean having all the properties
 * setters and getters methods.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@Entity
@Table(name = "valoux_interest_in")
public class ValouxInterestInBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "intid")
	private Integer InterestId;

	@Column(name = "interest")
	private String interest;

	@Column(name = "status", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer status;
	
	@OneToMany(mappedBy="valouxInterestIn",fetch = FetchType.LAZY)
	private List<UserInterestIn> userInterestIn;
	

	public Integer getInterestId() {
		return InterestId;
	}

	public void setInterestId(Integer interestId) {
		InterestId = interestId;
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

	public List<UserInterestIn> getUserInterestIn() {
		return userInterestIn;
	}

	public void setUserInterestIn(List<UserInterestIn> userInterestIn) {
		this.userInterestIn = userInterestIn;
	}



}
