/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This <java>class</java> ValouxItemModel having getters and setters.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@XmlRootElement
public class ValouxItemModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer itemId;

	private String rKey;

	private String name;

	private String sDescription;

	private String designType;

	private Double designerPrice;

	private Integer designerPriceType;

	private Integer gender;

	private Integer quantity;

	private Integer itemTypeIt;

	private Double salesPrice;

	private Double salesTax;

	private Double valouxMarketValue;

	private Boolean storeExist;

	private Integer storeId;

	private Date createdOn;

	private String createdBy;

	private Date modifiedOn;

	private String modifiedBy;

	private String itemTypeName;
	
	private Double adjustmentPrice;

	private String brandPriceAdjustmentNotes;

	private Byte brandPriceAdjustmentOperator;

	private Byte brandPriceAdjustmentType;

	private Double brandPriceAdjustmentValue;

	private Double finalPrice;

	private String generalPriceAdjustmentNotes;

	private Byte generalPriceAdjustmentOperator;

	private Byte generalPriceAdjustmentType;

	private Double generalPriceAdjustmentValue;

	private Integer itemStatus;

	private Double lastAppraisaedPrice;

	private Date lastAppraisedDate;

	private String lastAppraiserId;

	private Double marketValue;

	private Double purchasePrice;
	
	private Integer lastAppraisalId;
	
	private Integer id;
	
	private Integer status;
	
	private String modifiedByName;
	
	private String description;
	
	private List<ItemImageModel> imageModels;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

	public List<ItemImageModel> getImageModels() {
		return imageModels;
	}

	public void setImageModels(List<ItemImageModel> imageModels) {
		this.imageModels = imageModels;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getModifiedByName() {
		return modifiedByName;
	}

	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}

	public Integer getLastAppraisalId() {
		return lastAppraisalId;
	}

	public void setLastAppraisalId(Integer lastAppraisalId) {
		this.lastAppraisalId = lastAppraisalId;
	}

	public Double getAdjustmentPrice() {
		return adjustmentPrice;
	}

	public void setAdjustmentPrice(Double adjustmentPrice) {
		this.adjustmentPrice = adjustmentPrice;
	}

	public String getBrandPriceAdjustmentNotes() {
		return brandPriceAdjustmentNotes;
	}

	public void setBrandPriceAdjustmentNotes(String brandPriceAdjustmentNotes) {
		this.brandPriceAdjustmentNotes = brandPriceAdjustmentNotes;
	}

	public Byte getBrandPriceAdjustmentOperator() {
		return brandPriceAdjustmentOperator;
	}

	public void setBrandPriceAdjustmentOperator(Byte brandPriceAdjustmentOperator) {
		this.brandPriceAdjustmentOperator = brandPriceAdjustmentOperator;
	}

	public Byte getBrandPriceAdjustmentType() {
		return brandPriceAdjustmentType;
	}

	public void setBrandPriceAdjustmentType(Byte brandPriceAdjustmentType) {
		this.brandPriceAdjustmentType = brandPriceAdjustmentType;
	}

	public Double getBrandPriceAdjustmentValue() {
		return brandPriceAdjustmentValue;
	}

	public void setBrandPriceAdjustmentValue(Double brandPriceAdjustmentValue) {
		this.brandPriceAdjustmentValue = brandPriceAdjustmentValue;
	}

	public Double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getGeneralPriceAdjustmentNotes() {
		return generalPriceAdjustmentNotes;
	}

	public void setGeneralPriceAdjustmentNotes(String generalPriceAdjustmentNotes) {
		this.generalPriceAdjustmentNotes = generalPriceAdjustmentNotes;
	}

	public Byte getGeneralPriceAdjustmentOperator() {
		return generalPriceAdjustmentOperator;
	}

	public void setGeneralPriceAdjustmentOperator(
			Byte generalPriceAdjustmentOperator) {
		this.generalPriceAdjustmentOperator = generalPriceAdjustmentOperator;
	}

	public Byte getGeneralPriceAdjustmentType() {
		return generalPriceAdjustmentType;
	}

	public void setGeneralPriceAdjustmentType(Byte generalPriceAdjustmentType) {
		this.generalPriceAdjustmentType = generalPriceAdjustmentType;
	}

	public Double getGeneralPriceAdjustmentValue() {
		return generalPriceAdjustmentValue;
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

	public Double getLastAppraisaedPrice() {
		return lastAppraisaedPrice;
	}

	public void setLastAppraisaedPrice(Double lastAppraisaedPrice) {
		this.lastAppraisaedPrice = lastAppraisaedPrice;
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

	public Double getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(Double marketValue) {
		this.marketValue = marketValue;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getrKey() {
		return rKey;
	}

	public void setrKey(String rKey) {
		this.rKey = rKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getsDescription() {
		return sDescription;
	}

	public void setsDescription(String sDescription) {
		this.sDescription = sDescription;
	}

	public String getDesignType() {
		return designType;
	}

	public void setDesignType(String designType) {
		this.designType = designType;
	}

	public Double getDesignerPrice() {
		return designerPrice;
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

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getItemTypeIt() {
		return itemTypeIt;
	}

	public void setItemTypeIt(Integer itemTypeIt) {
		this.itemTypeIt = itemTypeIt;
	}

	public Double getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}

	public Double getSalesTax() {
		return salesTax;
	}

	public void setSalesTax(Double salesTax) {
		this.salesTax = salesTax;
	}

	public Double getValouxMarketValue() {
		return valouxMarketValue;
	}

	public void setValouxMarketValue(Double valouxMarketValue) {
		this.valouxMarketValue = valouxMarketValue;
	}

	public Boolean getStoreExist() {
		return storeExist;
	}

	public void setStoreExist(Boolean storeExist) {
		this.storeExist = storeExist;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getItemTypeName() {
		return itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

}
