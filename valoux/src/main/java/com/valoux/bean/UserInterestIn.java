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
 * This <java>class</java> UserInterestIn having DB table and columns names.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@Entity
@Table(name = "valoux_consumer_interest_in")
public class UserInterestIn extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ci_id")
	private int consumerInterestInId;

	@Column(name = "rkey")
	private String relationKey;

	// @Column(name = "interest_id")
	// private int interestIn;

	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="interest_id", nullable=false)
	private ValouxInterestInBean valouxInterestIn;

	public int getConsumerInterestInId() {
		return consumerInterestInId;
	}

	public void setConsumerInterestInId(int consumerInterestInId) {
		this.consumerInterestInId = consumerInterestInId;
	}

	public String getRelationKey() {
		return relationKey;
	}

	public void setRelationKey(String relationKey) {
		this.relationKey = relationKey;
	}

	public ValouxInterestInBean getValouxInterestIn() {
		return valouxInterestIn;
	}

	public void setValouxInterestIn(ValouxInterestInBean valouxInterestIn) {
		this.valouxInterestIn = valouxInterestIn;
	}

	

}
