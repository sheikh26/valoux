/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.bean;

import java.io.Serializable;
import java.util.Date;
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
 * This <java>class</java> AppraisalBean having DB table and columns names.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@Entity
@Table(name = "valoux_appraisal")
public class AppraisalBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "vaid")
	private Integer appraisalId;

	@Column(name = "rkey")
	private String relationKey;

	@Column(name = "name")
	private String name;

	@Column(name = "short_description", columnDefinition = "TEXT")
	private String shortDescription;

	@Column(name = "astatus", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer aStatus;

	@Column(name = "approved_by")
	private String approvedBy;

	@Column(name = "approved_on")
	private Date approvedOn;
	
	@Column(name="last_appraisaed_price")
	private Double lastAppraisaedPrice;
	
	@Column(name = "pdf_file")
	private String pdfFile;

	// bi-directional many-to-one association to ValouxAppraisalItem
	@OneToMany(mappedBy = "appraisalBean" , fetch = FetchType.LAZY)
	private List<AppraisalItemsBean> appraisalItemsBean;

	//bi-directional many-to-one association to ValouxAppraisalCollection
	@OneToMany(mappedBy="appraisalBean" , fetch = FetchType.LAZY)
	private List<AppraisalCollectionBean> appraisalCollectionBean;
	
	//bi-directional many-to-one association to ValouxCollectionBean
	@OneToMany(mappedBy="valouxAppraisal" , fetch = FetchType.LAZY)
	private List<ValouxCollectionBean> valouxCollections;

	//bi-directional many-to-one association to ValouxItemBean
	@OneToMany(mappedBy="valouxAppraisal" , fetch = FetchType.LAZY)
	private List<ValouxItemBean> valouxItems;
	
	//bi-directional many-to-one association to ValouxAppraisalItemsComponentPriceBean
	@OneToMany(mappedBy="valouxAppraisal")
	private List<ValouxAppraisalItemsComponentPriceBean> valouxAppraisalItemsComponentPrices;

	//bi-directional many-to-one association to ValouxAppraisalItemsPriceBean
	@OneToMany(mappedBy="valouxAppraisal")
	private List<ValouxAppraisalItemsPriceBean> valouxAppraisalItemsPrices;
	
	public Double getLastAppraisaedPrice() {
		return lastAppraisaedPrice;
	}

	public void setLastAppraisaedPrice(Double lastAppraisaedPrice) {
		this.lastAppraisaedPrice = lastAppraisaedPrice;
	}

	public Integer getAppraisalId() {
		return appraisalId;
	}

	public void setAppraisalId(Integer appraisalId) {
		this.appraisalId = appraisalId;
	}

	public String getRelationKey() {
		return relationKey;
	}

	public void setRelationKey(String relationKey) {
		this.relationKey = relationKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public Integer getaStatus() {
		return aStatus;
	}

	public void setaStatus(Integer aStatus) {
		this.aStatus = aStatus;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedOn() {
		return approvedOn;
	}

	public void setApprovedOn(Date approvedOn) {
		this.approvedOn = approvedOn;
	}

	public List<AppraisalItemsBean> getAppraisalItemsBean() {
		return appraisalItemsBean;
	}

	public void setAppraisalItemsBean(List<AppraisalItemsBean> appraisalItemsBean) {
		this.appraisalItemsBean = appraisalItemsBean;
	}

	public List<AppraisalCollectionBean> getAppraisalCollectionBean() {
		return appraisalCollectionBean;
	}

	public void setAppraisalCollectionBean(List<AppraisalCollectionBean> appraisalCollectionBean) {
		this.appraisalCollectionBean = appraisalCollectionBean;
	}
	
	public List<ValouxCollectionBean> getValouxCollections() {
		return this.valouxCollections;
	}

	public void setValouxCollections(List<ValouxCollectionBean> valouxCollections) {
		this.valouxCollections = valouxCollections;
	}

	public ValouxCollectionBean addValouxCollection(ValouxCollectionBean valouxCollection) {
		getValouxCollections().add(valouxCollection);
		valouxCollection.setValouxAppraisal(this);

		return valouxCollection;
	}

	public ValouxCollectionBean removeValouxCollection(ValouxCollectionBean valouxCollection) {
		getValouxCollections().remove(valouxCollection);
		valouxCollection.setValouxAppraisal(null);

		return valouxCollection;
	}

	public List<ValouxItemBean> getValouxItems() {
		return this.valouxItems;
	}

	public void setValouxItems(List<ValouxItemBean> valouxItems) {
		this.valouxItems = valouxItems;
	}

	public ValouxItemBean addValouxItem(ValouxItemBean valouxItem) {
		getValouxItems().add(valouxItem);
		valouxItem.setValouxAppraisal(this);

		return valouxItem;
	}

	public ValouxItemBean removeValouxItem(ValouxItemBean valouxItem) {
		getValouxItems().remove(valouxItem);
		valouxItem.setValouxAppraisal(null);

		return valouxItem;
	}

	public List<ValouxAppraisalItemsComponentPriceBean> getValouxAppraisalItemsComponentPrices() {
		return this.valouxAppraisalItemsComponentPrices;
	}

	public void setValouxAppraisalItemsComponentPrices(List<ValouxAppraisalItemsComponentPriceBean> valouxAppraisalItemsComponentPrices) {
		this.valouxAppraisalItemsComponentPrices = valouxAppraisalItemsComponentPrices;
	}

	public ValouxAppraisalItemsComponentPriceBean addValouxAppraisalItemsComponentPrice(ValouxAppraisalItemsComponentPriceBean valouxAppraisalItemsComponentPrice) {
		getValouxAppraisalItemsComponentPrices().add(valouxAppraisalItemsComponentPrice);
		valouxAppraisalItemsComponentPrice.setValouxAppraisal(this);

		return valouxAppraisalItemsComponentPrice;
	}

	public ValouxAppraisalItemsComponentPriceBean removeValouxAppraisalItemsComponentPrice(ValouxAppraisalItemsComponentPriceBean valouxAppraisalItemsComponentPrice) {
		getValouxAppraisalItemsComponentPrices().remove(valouxAppraisalItemsComponentPrice);
		valouxAppraisalItemsComponentPrice.setValouxAppraisal(null);

		return valouxAppraisalItemsComponentPrice;
	}

	public List<ValouxAppraisalItemsPriceBean> getValouxAppraisalItemsPrices() {
		return this.valouxAppraisalItemsPrices;
	}

	public void setValouxAppraisalItemsPrices(List<ValouxAppraisalItemsPriceBean> valouxAppraisalItemsPrices) {
		this.valouxAppraisalItemsPrices = valouxAppraisalItemsPrices;
	}

	public ValouxAppraisalItemsPriceBean addValouxAppraisalItemsPrice(ValouxAppraisalItemsPriceBean valouxAppraisalItemsPrice) {
		getValouxAppraisalItemsPrices().add(valouxAppraisalItemsPrice);
		valouxAppraisalItemsPrice.setValouxAppraisal(this);

		return valouxAppraisalItemsPrice;
	}

	public ValouxAppraisalItemsPriceBean removeValouxAppraisalItemsPrice(ValouxAppraisalItemsPriceBean valouxAppraisalItemsPrice) {
		getValouxAppraisalItemsPrices().remove(valouxAppraisalItemsPrice);
		valouxAppraisalItemsPrice.setValouxAppraisal(null);

		return valouxAppraisalItemsPrice;
	}
	
	public String getPdfFile() {
		return pdfFile;
	}

	public void setPdfFile(String pdfFile) {
		this.pdfFile = pdfFile;
	}


}
