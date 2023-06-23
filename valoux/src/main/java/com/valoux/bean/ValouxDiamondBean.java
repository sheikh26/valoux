/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * This <java>class</java> ValouxDiamondBean having DB table and columns names.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
@Entity
@Table(name="valoux_diamond")
public class ValouxDiamondBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer vdid;

	@Column(name="clarity_id", nullable=false)
	private Integer clarityId;

	private Integer color;

	@Column(name="created_by", length=100)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on", nullable=false)
	private Date createdOn;

	private Integer cut;

	private Integer cutlet;

	private Double depth;

	@Column(name="depth_percentage")
	private Double depthPercentage;

	@Column(name="diamond_height")
	private Double diamondHeight;

	@Column(name="diamond_length")
	private Double diamondLength;

	@Column(name="diamond_width")
	private Double diamondWidth;

	@Column(name="fancy_color")
	private Integer fancyColor;

	private Integer fluorescence;

	@Column(name="girdle_thickness_description")
	private Integer girdleThicknessDescription;

//	@Column(name="item_component_id", nullable=false)
//	private Integer itemComponentId;

//	@Column(name="item_id", nullable=false)
//	private Integer itemId;

	@Column(name="length_width_ratio")
	private Double lengthWidthRatio;

	@Column(name="market_value")
	private Double marketValue;

	@Column(name="max_diameter")
	private Double maxDiameter;

	@Column(name="min_diameter")
	private Double minDiameter;

	@Column(name="modified_by", length=100)
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_on")
	private Date modifiedOn;

	private Integer placement;

	private Integer polish;

	@Column(name="secondary_hue")
	private Integer secondaryHue;

	private Integer shape;

	@Column(name="single_weight")
	private Double singleWeight;

	@Column(nullable=false)
	private Byte status;

	private Integer symmetry;

	@Column(name="table_percentage")
	private Double tablePercentage;

	private Integer thickness;

	@Column(name="total_weight")
	private Double totalWeight;

	@Column(name="weight_measure")
	private Integer weightMeasure;
	
	// bi-directional many-to-one association to ValouxItem
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private ValouxItemBean valouxItem;
	
	// bi-directional many-to-one association to ValouxItemComponent
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_component_id")
	private ValouxItemComponentBean valouxItemComponent;

	public ValouxDiamondBean() {
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

	public Double getDepth() {
		return this.depth;
	}

	public void setDepth(Double depth) {
		this.depth = depth;
	}

	public Double getDepthPercentage() {
		return this.depthPercentage;
	}

	public void setDepthPercentage(Double depthPercentage) {
		this.depthPercentage = depthPercentage;
	}

	public Double getDiamondHeight() {
		return this.diamondHeight;
	}

	public void setDiamondHeight(Double diamondHeight) {
		this.diamondHeight = diamondHeight;
	}

	public Double getDiamondLength() {
		return this.diamondLength;
	}

	public void setDiamondLength(Double diamondLength) {
		this.diamondLength = diamondLength;
	}

	public Double getDiamondWidth() {
		return this.diamondWidth;
	}

	public void setDiamondWidth(Double diamondWidth) {
		this.diamondWidth = diamondWidth;
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

	public ValouxItemComponentBean getValouxItemComponent() {
		return valouxItemComponent;
	}

	public void setValouxItemComponent(ValouxItemComponentBean valouxItemComponent) {
		this.valouxItemComponent = valouxItemComponent;
	}

	public Double getLengthWidthRatio() {
		return this.lengthWidthRatio;
	}

	public void setLengthWidthRatio(Double lengthWidthRatio) {
		this.lengthWidthRatio = lengthWidthRatio;
	}

	public Double getMarketValue() {
		return this.marketValue;
	}

	public void setMarketValue(Double marketValue) {
		this.marketValue = marketValue;
	}

	public Double getMaxDiameter() {
		return this.maxDiameter;
	}

	public void setMaxDiameter(Double maxDiameter) {
		this.maxDiameter = maxDiameter;
	}

	public Double getMinDiameter() {
		return this.minDiameter;
	}

	public void setMinDiameter(Double minDiameter) {
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

	public Integer getPlacement() {
		return this.placement;
	}

	public void setPlacement(Integer placement) {
		this.placement = placement;
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

	public Double getSingleWeight() {
		return this.singleWeight;
	}

	public void setSingleWeight(Double singleWeight) {
		this.singleWeight = singleWeight;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Integer getSymmetry() {
		return this.symmetry;
	}

	public void setSymmetry(Integer symmetry) {
		this.symmetry = symmetry;
	}

	public Double getTablePercentage() {
		return this.tablePercentage;
	}

	public void setTablePercentage(Double tablePercentage) {
		this.tablePercentage = tablePercentage;
	}

	public Integer getThickness() {
		return this.thickness;
	}

	public void setThickness(Integer thickness) {
		this.thickness = thickness;
	}

	public Double getTotalWeight() {
		return this.totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Integer getWeightMeasure() {
		return this.weightMeasure;
	}

	public void setWeightMeasure(Integer weightMeasure) {
		this.weightMeasure = weightMeasure;
	}

	public ValouxItemBean getValouxItem() {
		return valouxItem;
	}

	public void setValouxItem(ValouxItemBean valouxItem) {
		this.valouxItem = valouxItem;
	}

}