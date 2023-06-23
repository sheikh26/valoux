/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.bean;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * This <java>class</java> ValouxCollectionBean having DB table and columns
 * names.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
@Entity
@Table(name = "valoux_collection")
public class ValouxCollectionBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer vcid;

	@Column(nullable = false, length = 200)
	private String cname;

	@Column(name = "collection_status", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer collectionStatus;

	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on")
	private Date modifiedOn;

	@Column(nullable = false, length = 100)
	private String rkey;

	@Column(name = "short_description", columnDefinition = "TEXT")
	private String shortDescription;

	// bi-directional many-to-one association to ValouxCollectionItem
	@OneToMany(mappedBy = "valouxCollectionBean",fetch = FetchType.LAZY)
	private List<ValouxCollectionItemBean> valouxCollectionItemsBean;

	// bi-directional many-to-one association to ValouxCollectionImage
	@OneToMany(mappedBy = "valouxCollectionBean",fetch = FetchType.LAZY)
	private List<ValouxCollectionImageBean> valouxCollectionImagesBean;

	// bi-directional many-to-one association to ValouxAppraisalCollection
	@OneToMany(mappedBy = "valouxCollectionBean",fetch = FetchType.LAZY)
	private List<AppraisalCollectionBean> appraisalCollectionBean;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_appraised_date")
	private Date lastAppraisedDate;

	@Column(name = "last_appraiser_id", length = 100)
	private String lastAppraiserId;
	
	//bi-directional many-to-one association to AppraisalBean
	@ManyToOne
	@JoinColumn(name="last_appraisal_id")
	private AppraisalBean valouxAppraisal;
	
	@Column(name = "last_appraisaed_price")
	private Double lastAppraisaedPrice;
	
	public Double getLastAppraisaedPrice() {
		return this.lastAppraisaedPrice;
	}

	public void setLastAppraisaedPrice(Double lastAppraisaedPrice) {
		this.lastAppraisaedPrice = lastAppraisaedPrice;
	}

	public ValouxCollectionBean() {
	}

	public Integer getVcid() {
		return this.vcid;
	}

	public void setVcid(Integer vcid) {
		this.vcid = vcid;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Integer getCollectionStatus() {
		return collectionStatus;
	}

	public void setCollectionStatus(Integer collectionStatus) {
		this.collectionStatus = collectionStatus;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return this.modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getRkey() {
		return this.rkey;
	}

	public void setRkey(String rkey) {
		this.rkey = rkey;
	}

	public String getShortDescription() {
		return this.shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public List<ValouxCollectionItemBean> getValouxCollectionItemsBean() {
		return valouxCollectionItemsBean;
	}

	public void setValouxCollectionItemsBean(List<ValouxCollectionItemBean> valouxCollectionItemsBean) {
		this.valouxCollectionItemsBean = valouxCollectionItemsBean;
	}

	public List<ValouxCollectionImageBean> getValouxCollectionImagesBean() {
		return valouxCollectionImagesBean;
	}

	public void setValouxCollectionImagesBean(List<ValouxCollectionImageBean> valouxCollectionImagesBean) {
		this.valouxCollectionImagesBean = valouxCollectionImagesBean;
	}

	public List<AppraisalCollectionBean> getAppraisalCollectionBean() {
		return appraisalCollectionBean;
	}

	public void setAppraisalCollectionBean(List<AppraisalCollectionBean> appraisalCollectionBean) {
		this.appraisalCollectionBean = appraisalCollectionBean;
	}

	public Date getLastAppraisedDate() {
		return lastAppraisedDate;
	}

	public void setLastAppraisedDate(Date lastAppraisedDate) {
		this.lastAppraisedDate = lastAppraisedDate;
	}

	public String getLastAppraiserId() {
		return lastAppraiserId;
	}

	public void setLastAppraiserId(String lastAppraiserId) {
		this.lastAppraiserId = lastAppraiserId;
	}
	
	public AppraisalBean getValouxAppraisal() {
		return this.valouxAppraisal;
	}

	public void setValouxAppraisal(AppraisalBean valouxAppraisal) {
		this.valouxAppraisal = valouxAppraisal;
	}

}