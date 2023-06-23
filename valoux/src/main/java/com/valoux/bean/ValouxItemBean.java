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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This <java>class</java> ValouxItemBean having DB table and columns names.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
@Entity
@Table(name = "valoux_item")
public class ValouxItemBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "itmid")
	private Integer itemId;

	@Column(name = "adjustment_price")
	private Double adjustmentPrice;

	@Column(name = "brand_price_adjustment_notes", columnDefinition = "TEXT")
	private String brandPriceAdjustmentNotes;

	@Column(name = "brand_price_adjustment_operator")
	private Byte brandPriceAdjustmentOperator;

	@Column(name = "brand_price_adjustment_type")
	private Byte brandPriceAdjustmentType;

	@Column(name = "brand_price_adjustment_value")
	private Double brandPriceAdjustmentValue;

	@Column(name = "created_by", length = 100)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "design_type", length = 100)
	private String designType;

	@Column(name = "designer_price")
	private Double designerPrice;

	@Column(name = "designer_price_type", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer designerPriceType;

	@Column(name = "final_price")
	private Double finalPrice;

	@Column(name = "general_price_adjustment_notes", columnDefinition = "TEXT")
	private String generalPriceAdjustmentNotes;

	@Column(name = "general_price_adjustment_operator")
	private Byte generalPriceAdjustmentOperator;

	@Column(name = "general_price_adjustment_type")
	private Byte generalPriceAdjustmentType;

	@Column(name = "general_price_adjustment_value")
	private Double generalPriceAdjustmentValue;

	@Column(name = "item_status", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer itemStatus;

	// @Column(name="item_type_it")
	// private Integer itemTypeIt;

	// bi-directional many-to-one association to ValouxItemType
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_type_it")
	private ValouxItemTypeBean valouxItemTypeBean;

	@Column(name = "jewelry_gender", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer gender;

	@Column(name = "last_appraisaed_price")
	private Double lastAppraisaedPrice;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_appraised_date")
	private Date lastAppraisedDate;

	@Column(name = "last_appraiser_id", length = 100)
	private String lastAppraiserId;

	@Column(name = "market_value")
	private Double marketValue;

	@Column(name = "modified_by", length = 100)
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on")
	private Date modifiedOn;

	@Column(length = 200)
	private String name;

	@Column(name = "purchase_price")
	private Double purchasePrice;

	private Integer quantity;

	@Column(name = "rkey")
	private String rKey;

	@Column(name = "sales_price")
	private Double salesPrice;

	@Column(name = "sales_tax")
	private Double salesTax;

	@Column(name = "sdescription", columnDefinition = "TEXT")
	private String sDescription;

	@Column(name = "store_id", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer storeId;

	@Column(name = "valoux_market_value")
	private Double valouxMarketValue;

	// bi-directional many-to-one association to ItemImage
	@OneToMany(mappedBy = "valouxItemBean", fetch = FetchType.LAZY)
	private List<ItemImageBean> itemImagesBean;

	// bi-directional many-to-one association to ValouxCollectionItem
	@OneToMany(mappedBy = "valouxItemBean", fetch = FetchType.LAZY)
	private List<ValouxCollectionItemBean> valouxCollectionItemsBean;

	// bi-directional many-to-one association to ValouxAppraisalItem
	@OneToMany(mappedBy = "valouxItemBean", fetch = FetchType.LAZY)
	private List<AppraisalItemsBean> appraisalItemsBean;

	// bi-directional many-to-one association to ValouxItemComponent
	@OneToMany(mappedBy = "valouxItem", fetch = FetchType.LAZY)
	private List<ValouxItemComponentBean> valouxItemComponents;

	// bi-directional many-to-one association to ValouxDiamond
	@OneToMany(mappedBy = "valouxItem", fetch = FetchType.LAZY)
	private List<ValouxDiamondBean> valouxDiamonds;

	// bi-directional many-to-one association to ValouxGemstone
	@OneToMany(mappedBy = "valouxItem", fetch = FetchType.LAZY)
	private List<ValouxGemstoneBean> valouxGemstones;

	// bi-directional many-to-one association to ValouxPearl
	@OneToMany(mappedBy = "valouxItem", fetch = FetchType.LAZY)
	private List<ValouxPearlBean> valouxPearls;

	// bi-directional many-to-one association to ValouxMetal
	@OneToMany(mappedBy = "valouxItem", fetch = FetchType.LAZY)
	private List<ValouxMetalBean> valouxMetals;

	// bi-directional many-to-one association to ValouxMetal
	@OneToMany(mappedBy = "valouxItem", fetch = FetchType.LAZY)
	private List<ItemComponentCertificateBean> itemComponentCertificate;
	
	//bi-directional many-to-one association to AppraisalBean
	@ManyToOne
	@JoinColumn(name="last_appraisal_id")
	private AppraisalBean valouxAppraisal;
	
	//bi-directional many-to-one association to ValouxAppraisalItemsPriceBean
	@OneToMany(mappedBy="valouxItem", fetch = FetchType.LAZY)
	private List<ValouxAppraisalItemsPriceBean> valouxAppraisalItemsPrices;

	public ValouxItemBean() {
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Double getAdjustmentPrice() {
		return this.adjustmentPrice;
	}

	public void setAdjustmentPrice(Double adjustmentPrice) {
		this.adjustmentPrice = adjustmentPrice;
	}

	public String getBrandPriceAdjustmentNotes() {
		return this.brandPriceAdjustmentNotes;
	}

	public void setBrandPriceAdjustmentNotes(String brandPriceAdjustmentNotes) {
		this.brandPriceAdjustmentNotes = brandPriceAdjustmentNotes;
	}

	public Byte getBrandPriceAdjustmentOperator() {
		return this.brandPriceAdjustmentOperator;
	}

	public void setBrandPriceAdjustmentOperator(Byte brandPriceAdjustmentOperator) {
		this.brandPriceAdjustmentOperator = brandPriceAdjustmentOperator;
	}

	public Byte getBrandPriceAdjustmentType() {
		return this.brandPriceAdjustmentType;
	}

	public void setBrandPriceAdjustmentType(Byte brandPriceAdjustmentType) {
		this.brandPriceAdjustmentType = brandPriceAdjustmentType;
	}

	public Double getBrandPriceAdjustmentValue() {
		return this.brandPriceAdjustmentValue;
	}

	public void setBrandPriceAdjustmentValue(Double brandPriceAdjustmentValue) {
		this.brandPriceAdjustmentValue = brandPriceAdjustmentValue;
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

	public String getDesignType() {
		return this.designType;
	}

	public void setDesignType(String designType) {
		this.designType = designType;
	}

	public Double getDesignerPrice() {
		return this.designerPrice;
	}

	public void setDesignerPrice(Double designerPrice) {
		this.designerPrice = designerPrice;
	}

	public Integer getDesignerPriceType() {
		return designerPriceType;
	}

	public void setDesignerPriceType(Integer designerPriceType) {
		this.designerPriceType = designerPriceType;
	}

	public Double getFinalPrice() {
		return this.finalPrice;
	}

	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getGeneralPriceAdjustmentNotes() {
		return this.generalPriceAdjustmentNotes;
	}

	public void setGeneralPriceAdjustmentNotes(String generalPriceAdjustmentNotes) {
		this.generalPriceAdjustmentNotes = generalPriceAdjustmentNotes;
	}

	public Byte getGeneralPriceAdjustmentOperator() {
		return this.generalPriceAdjustmentOperator;
	}

	public void setGeneralPriceAdjustmentOperator(Byte generalPriceAdjustmentOperator) {
		this.generalPriceAdjustmentOperator = generalPriceAdjustmentOperator;
	}

	public Byte getGeneralPriceAdjustmentType() {
		return this.generalPriceAdjustmentType;
	}

	public void setGeneralPriceAdjustmentType(Byte generalPriceAdjustmentType) {
		this.generalPriceAdjustmentType = generalPriceAdjustmentType;
	}

	public Double getGeneralPriceAdjustmentValue() {
		return this.generalPriceAdjustmentValue;
	}

	public void setGeneralPriceAdjustmentValue(Double generalPriceAdjustmentValue) {
		this.generalPriceAdjustmentValue = generalPriceAdjustmentValue;
	}

	

	public Integer getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Double getLastAppraisaedPrice() {
		return this.lastAppraisaedPrice;
	}

	public void setLastAppraisaedPrice(Double lastAppraisaedPrice) {
		this.lastAppraisaedPrice = lastAppraisaedPrice;
	}

	public Date getLastAppraisedDate() {
		return this.lastAppraisedDate;
	}

	public void setLastAppraisedDate(Date lastAppraisedDate) {
		this.lastAppraisedDate = lastAppraisedDate;
	}

	public String getLastAppraiserId() {
		return this.lastAppraiserId;
	}

	public void setLastAppraiserId(String lastAppraiserId) {
		this.lastAppraiserId = lastAppraiserId;
	}

	public Double getMarketValue() {
		return this.marketValue;
	}

	public void setMarketValue(Double marketValue) {
		this.marketValue = marketValue;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPurchasePrice() {
		return this.purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getrKey() {
		return rKey;
	}

	public void setrKey(String rKey) {
		this.rKey = rKey;
	}

	public Double getSalesPrice() {
		return this.salesPrice;
	}

	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}

	public Double getSalesTax() {
		return this.salesTax;
	}

	public void setSalesTax(Double salesTax) {
		this.salesTax = salesTax;
	}

	public String getsDescription() {
		return sDescription;
	}

	public void setsDescription(String sDescription) {
		this.sDescription = sDescription;
	}

	public Integer getStoreId() {
		return this.storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Double getValouxMarketValue() {
		return this.valouxMarketValue;
	}

	public void setValouxMarketValue(Double valouxMarketValue) {
		this.valouxMarketValue = valouxMarketValue;
	}

	public ValouxItemTypeBean getValouxItemTypeBean() {
		return valouxItemTypeBean;
	}

	public void setValouxItemTypeBean(ValouxItemTypeBean valouxItemTypeBean) {
		this.valouxItemTypeBean = valouxItemTypeBean;
	}

	public List<ItemImageBean> getItemImagesBean() {
		return itemImagesBean;
	}

	public void setItemImagesBean(List<ItemImageBean> itemImagesBean) {
		this.itemImagesBean = itemImagesBean;
	}

	public List<ValouxCollectionItemBean> getValouxCollectionItemsBean() {
		return valouxCollectionItemsBean;
	}

	public void setValouxCollectionItemsBean(List<ValouxCollectionItemBean> valouxCollectionItemsBean) {
		this.valouxCollectionItemsBean = valouxCollectionItemsBean;
	}

	public List<AppraisalItemsBean> getAppraisalItemsBean() {
		return appraisalItemsBean;
	}

	public void setAppraisalItemsBean(List<AppraisalItemsBean> appraisalItemsBean) {
		this.appraisalItemsBean = appraisalItemsBean;
	}

	public List<ValouxItemComponentBean> getValouxItemComponents() {
		return valouxItemComponents;
	}

	public void setValouxItemComponents(List<ValouxItemComponentBean> valouxItemComponents) {
		this.valouxItemComponents = valouxItemComponents;
	}

	public List<ValouxDiamondBean> getValouxDiamonds() {
		return valouxDiamonds;
	}

	public void setValouxDiamonds(List<ValouxDiamondBean> valouxDiamonds) {
		this.valouxDiamonds = valouxDiamonds;
	}

	public List<ValouxGemstoneBean> getValouxGemstones() {
		return valouxGemstones;
	}

	public void setValouxGemstones(List<ValouxGemstoneBean> valouxGemstones) {
		this.valouxGemstones = valouxGemstones;
	}

	public List<ValouxPearlBean> getValouxPearls() {
		return valouxPearls;
	}

	public void setValouxPearls(List<ValouxPearlBean> valouxPearls) {
		this.valouxPearls = valouxPearls;
	}

	public List<ValouxMetalBean> getValouxMetals() {
		return valouxMetals;
	}

	public void setValouxMetals(List<ValouxMetalBean> valouxMetals) {
		this.valouxMetals = valouxMetals;
	}

	public List<ItemComponentCertificateBean> getItemComponentCertificate() {
		return itemComponentCertificate;
	}

	public void setItemComponentCertificate(List<ItemComponentCertificateBean> itemComponentCertificate) {
		this.itemComponentCertificate = itemComponentCertificate;
	}
	
	public AppraisalBean getValouxAppraisal() {
		return this.valouxAppraisal;
	}

	public void setValouxAppraisal(AppraisalBean valouxAppraisal) {
		this.valouxAppraisal = valouxAppraisal;
	}

	public List<ValouxAppraisalItemsPriceBean> getValouxAppraisalItemsPrices() {
		return this.valouxAppraisalItemsPrices;
	}

	public void setValouxAppraisalItemsPrices(List<ValouxAppraisalItemsPriceBean> valouxAppraisalItemsPrices) {
		this.valouxAppraisalItemsPrices = valouxAppraisalItemsPrices;
	}

	public ValouxAppraisalItemsPriceBean addValouxAppraisalItemsPrice(ValouxAppraisalItemsPriceBean valouxAppraisalItemsPrice) {
		getValouxAppraisalItemsPrices().add(valouxAppraisalItemsPrice);
		valouxAppraisalItemsPrice.setValouxItem(this);

		return valouxAppraisalItemsPrice;
	}

	public ValouxAppraisalItemsPriceBean removeValouxAppraisalItemsPrice(ValouxAppraisalItemsPriceBean valouxAppraisalItemsPrice) {
		getValouxAppraisalItemsPrices().remove(valouxAppraisalItemsPrice);
		valouxAppraisalItemsPrice.setValouxItem(null);

		return valouxAppraisalItemsPrice;
	}

}