/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This <java>class</java> UserJewelryComponentsModel having all the properties
 * setters and getters methods.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@XmlRootElement
public class UserJewelryComponentsModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer jewelryComponentsId;

	private String relationKey;

	private Integer components;

	private Integer componentsType;

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
