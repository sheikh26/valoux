/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This <java>class</java> UserJewelryComponentsBean having DB table and columns
 * names.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@Entity
@Table(name = "valoux_consumer_jewelry_components")
public class UserJewelryComponentsBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "jcid")
	private Integer jewelryComponentsId;

	@Column(name = "rkey")
	private String relationKey;

	@Column(name = "components", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer components;

	@Column(name = "components_type", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer componentsType;

	@Column(name = "status", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer status;

	public Integer getJewelryComponentsId() {
		return jewelryComponentsId;
	}

	public void setJewelryComponentsId(Integer jewelryComponentsId) {
		this.jewelryComponentsId = jewelryComponentsId;
	}

	public String getRelationKey() {
		return relationKey;
	}

	public void setRelationKey(String relationKey) {
		this.relationKey = relationKey;
	}

	public Integer getComponents() {
		return components;
	}

	public void setComponents(Integer components) {
		this.components = components;
	}

	public Integer getComponentsType() {
		return componentsType;
	}

	public void setComponentsType(Integer componentsType) {
		this.componentsType = componentsType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
