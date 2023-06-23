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
import javax.persistence.Table;


/**
 * This <java>class</java> ValouxItemTypeBean having DB table and columns names.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
@Entity
@Table(name = "valoux_item_type")
public class ValouxItemTypeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "itid")
	private Integer itemTypeId;

	@Column(name = "item_type")
	private String itemType;

	@Column(name = "status")
	private Integer status;

	// bi-directional many-to-one association to ValouxItem
	@OneToMany(mappedBy = "valouxItemTypeBean",fetch = FetchType.LAZY)
	private List<ValouxItemBean> valouxItemBean;

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

	public List<ValouxItemBean> getValouxItemBean() {
		return valouxItemBean;
	}

	public void setValouxItemBean(List<ValouxItemBean> valouxItemBean) {
		this.valouxItemBean = valouxItemBean;
	}

}
