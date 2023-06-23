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
 * This <java>class</java> AppraisalCollectionBean having DB table and columns
 * names.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@Entity
@Table(name = "valoux_appraisal_collection")
public class AppraisalCollectionBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	// @Column(name = "appraisal_id")
	// private Integer appraisalId;

	// bi-directional many-to-one association to ValouxAppraisal
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appraisal_id")
	private AppraisalBean appraisalBean;

	// @Column(name = "collection_id")
	// private Integer collectionId;

	// bi-directional many-to-one association to ValouxCollection
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "collection_id")
	private ValouxCollectionBean valouxCollectionBean;

	@Column(name = "status", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer status;

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

	public ValouxCollectionBean getValouxCollectionBean() {
		return valouxCollectionBean;
	}

	public void setValouxCollectionBean(ValouxCollectionBean valouxCollectionBean) {
		this.valouxCollectionBean = valouxCollectionBean;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
