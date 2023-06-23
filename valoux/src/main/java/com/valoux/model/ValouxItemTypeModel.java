/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This <java>class</java> ValouxItemTypeModel having all getter and setter.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
@XmlRootElement
public class ValouxItemTypeModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer itemTypeId;

	private String itemType;

	private Integer status;

	public Integer getItemTypeId() {
		return itemTypeId;
	}

	public void setItemTypeId(Integer itemTypeId) {
		this.itemTypeId = itemTypeId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
