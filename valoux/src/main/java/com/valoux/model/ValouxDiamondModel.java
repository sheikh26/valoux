/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jettison.json.JSONArray;

/**
 * This <java>class</java> ItemComponentCertificateBean having DB table and
 * columns names.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
@XmlRootElement
public class ValouxDiamondModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer vdid;

	private Integer clarityId;

	private Integer color;

	private String createdBy;

	private Date createdOn;

	private Integer cut;

	private Integer cutlet;

	private String depth;

	private String depthPercentage;

	private Integer fancyColor;

	private Integer fluorescence;

	private Integer girdleThicknessDescription;

	private Integer itemComponentId;

	private Integer itemId;

	private String lengthWidthRatio;
 
	private String marketValue;

	private String maxDiameter;

	private String minDiameter;

	private String modifiedBy;

	private Date modifiedOn;

	private Double diamondHeight;

	private Integer placement;

	private Double diamondLength;

	private Integer polish;

	private String pwidth;

	private Integer secondaryHue;

	private Integer shape;

	private String singleWeight;

	private Integer status;

	private Integer symmetry;

	private String tablePercentage;

	private Integer thickness;

	private String totalWeight;

	private Integer weightMeasure;
	
	private String specifiedValue;
	
	private Date specifiedDate;
	
	private JSONArray internalInclusions;
	
	private JSONArray externalInclusions;
	
	public JSONArray getInternalInclusions() {
		return internalInclusions;
	}

	public void setInternalInclusions(JSONArray internalInclusions) {
		this.internalInclusions = internalInclusions;
	}

	public JSONArray getExternalInclusions() {
		return externalInclusions;
	}

	public void setExternalInclusions(JSONArray externalInclusions) {
		this.externalInclusions = externalInclusions;
	}

	public String getSpecifiedValue() {
		return specifiedValue;
	}

	public void setSpecifiedValue(String specifiedValue) {
		this.specifiedValue = specifiedValue;
	}

	public Date getSpecifiedDate() {
		return specifiedDate;
	}

	public void setSpecifiedDate(Date specifiedDate) {
		this.specifiedDate = specifiedDate;
	}

	public ValouxDiamondModel() {
	}

	public Integer getVdid() {
		return this.vdid;
	}

	public void setVdid(Integer vdid) {
		this.vdid = vdid;
	}

	public Integer getClarityId() {
		return this.clarityId;
	}

	public void setClarityId(Integer clarityId) {
		this.clarityId = clarityId;
	}

	public Integer getColor() {
		return this.color;
	}

	public void setColor(Integer color) {
		this.color = color;
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

	public Integer getCut() {
		return this.cut;
	}

	public void setCut(Integer cut) {
		this.cut = cut;
	}

	public Integer getCutlet() {
		return this.cutlet;
	}

	public void setCutlet(Integer cutlet) {
		this.cutlet = cutlet;
	}

	public String getDepth() {
		return this.depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public String getDepthPercentage() {
		return this.depthPercentage;
	}

	public void setDepthPercentage(String depthPercentage) {
		this.depthPercentage = depthPercentage;
	}

	public Integer getFancyColor() {
		return this.fancyColor;
	}

	public void setFancyColor(Integer fancyColor) {
		this.fancyColor = fancyColor;
	}

	public Integer getFluorescence() {
		return this.fluorescence;
	}

	public void setFluorescence(Integer fluorescence) {
		this.fluorescence = fluorescence;
	}

	public Integer getGirdleThicknessDescription() {
		return this.girdleThicknessDescription;
	}

	public void setGirdleThicknessDescription(Integer girdleThicknessDescription) {
		this.girdleThicknessDescription = girdleThicknessDescription;
	}

	public Integer getItemComponentId() {
		return this.itemComponentId;
	}

	public void setItemComponentId(Integer itemComponentId) {
		this.itemComponentId = itemComponentId;
	}

	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getLengthWidthRatio() {
		return this.lengthWidthRatio;
	}

	public void setLengthWidthRatio(String lengthWidthRatio) {
		this.lengthWidthRatio = lengthWidthRatio;
	}

	public String getMarketValue() {
		return this.marketValue;
	}

	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}

	public String getMaxDiameter() {
		return this.maxDiameter;
	}

	public void setMaxDiameter(String maxDiameter) {
		this.maxDiameter = maxDiameter;
	}

	public String getMinDiameter() {
		return this.minDiameter;
	}

	public void setMinDiameter(String minDiameter) {
		this.minDiameter = minDiameter;
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

	public Integer getPolish() {
		return this.polish;
	}

	public void setPolish(Integer polish) {
		this.polish = polish;
	}

	public Integer getSecondaryHue() {
		return this.secondaryHue;
	}

	public void setSecondaryHue(Integer secondaryHue) {
		this.secondaryHue = secondaryHue;
	}

	public Integer getShape() {
		return this.shape;
	}

	public void setShape(Integer shape) {
		this.shape = shape;
	}

	public String getSingleWeight() {
		return this.singleWeight;
	}

	public void setSingleWeight(String singleWeight) {
		this.singleWeight = singleWeight;
	}

	public Integer getSymmetry() {
		return this.symmetry;
	}

	public void setSymmetry(Integer symmetry) {
		this.symmetry = symmetry;
	}

	public String getTablePercentage() {
		return this.tablePercentage;
	}

	public void setTablePercentage(String tablePercentage) {
		this.tablePercentage = tablePercentage;
	}

	public Integer getThickness() {
		return this.thickness;
	}

	public void setThickness(Integer thickness) {
		this.thickness = thickness;
	}

	public String getTotalWeight() {
		return this.totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Integer getWeightMeasure() {
		return this.weightMeasure;
	}

	public void setWeightMeasure(Integer weightMeasure) {
		this.weightMeasure = weightMeasure;
	}

	public Double getDiamondHeight() {
		return diamondHeight;
	}

	public void setDiamondHeight(Double diamondHeight) {
		this.diamondHeight = diamondHeight;
	}

	public Double getDiamondLength() {
		return diamondLength;
	}

	public void setDiamondLength(Double diamondLength) {
		this.diamondLength = diamondLength;
	}

	public Integer getPlacement() {
		return placement;
	}

	public void setPlacement(Integer placement) {
		this.placement = placement;
	}

	public String getPwidth() {
		return pwidth;
	}

	public void setPwidth(String pwidth) {
		this.pwidth = pwidth;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}