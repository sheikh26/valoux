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
 * This <java>class</java> ValouxAgentStoreBean having DB table and columns
 * names.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@Entity
@Table(name = "valoux_agent_store")
public class ValouxAgentStoreBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "asid")
	Integer agentStoreId;

	@Column(name = "rkey")
	String relationKey;

//	@Column(name = "store_id")
//	Integer storeId;

	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="store_id", nullable=false)
	private ValouxStoreBean valouxStoreBean;

	public Integer getAgentStoreId() {
		return agentStoreId;
	}

	public void setAgentStoreId(Integer agentStoreId) {
		this.agentStoreId = agentStoreId;
	}

	public String getRelationKey() {
		return relationKey;
	}

	public void setRelationKey(String relationKey) {
		this.relationKey = relationKey;
	}

	public ValouxStoreBean getValouxStoreBean() {
		return valouxStoreBean;
	}

	public void setValouxStoreBean(ValouxStoreBean valouxStoreBean) {
		this.valouxStoreBean = valouxStoreBean;
	}

	

}
