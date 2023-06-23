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
import javax.persistence.Table;

/**
 * This <java>class</java> AppraisalItemsBean having DB table and columns names.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@Entity
@Table(name = "valoux_appraisal_items")
public class AppraisalItemsBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

//	@Column(name = "appraisal_id")
//	private Integer appraisalId;

	// @Column(name = "item_id")
	// private Integer itemId;

	@Column(name = "status", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer status;

	// bi-directional many-to-one association to ValouxItem
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private ValouxItemBean valouxItemBean;
	

	//bi-directional many-to-one association to ValouxAppraisal
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="appraisal_id")
	private AppraisalBean appraisalBean;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public AppraisalBean getAppraisalBean() {
		return appraisalBean;
	}

	public void setAppraisalBean(AppraisalBean appraisalBean) {
		this.appraisalBean = appraisalBean;
	}

	public ValouxItemBean getValouxItemBean() {
		return valouxItemBean;
	}

	public void setValouxItemBean(ValouxItemBean valouxItemBean) {
		this.valouxItemBean = valouxItemBean;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
