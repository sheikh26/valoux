package com.valoux.bean;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the valoux_item_component database table.
 * 
 */
@Entity
@Table(name = "valoux_item_component")
public class ValouxItemComponentBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer vicid;

	@Column(name = "components_type")
	private Byte componentsType;

	@Column(name = "created_by", length = 100)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn;

	// @Column(name = "item_id", nullable = false)
	// private Integer itemId;

	// bi-directional many-to-one association to ValouxItem
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private ValouxItemBean valouxItem;

	@Column(name = "modified_by", length = 100)
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on")
	private Date modifiedOn;

	@Column(length = 150)
	private String name;

	private Integer quantity;

	@Column(name = "vic_status")
	private Byte vicStatus;

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

	@Column(name = "current_unit_price")
	private Double currentUnitPrice;

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

	@Column(name = "market_value")
	private Double marketValue;

	@Column(name = "purchase_price")
	private Double purchasePrice;
	
	// bi-directional many-to-one association to ValouxComponentsImage
	@OneToMany(mappedBy = "valouxItemComponent",fetch = FetchType.LAZY)
	private List<ValouxComponentsImageBean> valouxComponentsImages;
	
	// bi-directional many-to-one association to ValouxDiamond
	@OneToMany(mappedBy = "valouxItemComponent",fetch = FetchType.LAZY)
	private List<ValouxDiamondBean> valouxDiamonds;

	// bi-directional many-to-one association to valouxGemstones
	@OneToMany(mappedBy = "valouxItemComponent",fetch = FetchType.LAZY)
	private List<ValouxGemstoneBean> valouxGemstones;
	
	// bi-directional many-to-one association to ValouxPearls
	@OneToMany(mappedBy = "valouxItemComponent",fetch = FetchType.LAZY)
	private List<ValouxPearlBean> ValouxPearls;
	
	// bi-directional many-to-one association to ValouxMetals
	@OneToMany(mappedBy = "valouxItemComponent",fetch = FetchType.LAZY)
	private List<ValouxMetalBean> ValouxMetals;
	
	// bi-directional many-to-one association to ItemComponentCertificateBean
	@OneToMany(mappedBy = "valouxItemComponent",fetch = FetchType.LAZY)
	private List<ItemComponentCertificateBean> itemComponentCertificate;
	
	//bi-directional many-to-one association to ValouxAppraisalItemsComponentPriceBean
	@OneToMany(mappedBy="valouxItemComponent", fetch = FetchType.LAZY)
	private List<ValouxAppraisalItemsComponentPriceBean> valouxAppraisalItemsComponentPrices;
	
	//bi-directional many-to-one association to ValouxInclusionBean
	@OneToMany(mappedBy="valouxItemComponent")
	private List<ValouxInclusionBean> valouxInclusions;

	public ValouxItemComponentBean() {
	}

	public Integer getVicid() {
		return this.vicid;
	}

	public void setVicid(Integer vicid) {
		this.vicid = vicid;
	}

	public Byte getComponentsType() {
		return this.componentsType;
	}

	public void setComponentsType(Byte componentsType) {
		this.componentsType = componentsType;
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

	public ValouxItemBean getValouxItem() {
		return valouxItem;
	}

	public void setValouxItem(ValouxItemBean valouxItem) {
		this.valouxItem = valouxItem;
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

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Byte getVicStatus() {
		return this.vicStatus;
	}

	public void setVicStatus(Byte vicStatus) {
		this.vicStatus = vicStatus;
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

	public Double getCurrentUnitPrice() {
		return currentUnitPrice;
	}

	public void setCurrentUnitPrice(Double currentUnitPrice) {
		this.currentUnitPrice = currentUnitPrice;
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

	public void setGeneralPriceAdjustmentOperator(Byte generalPriceAdjustmentOperator) {
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

	public List<ValouxComponentsImageBean> getValouxComponentsImages() {
		return valouxComponentsImages;
	}

	public void setValouxComponentsImages(List<ValouxComponentsImageBean> valouxComponentsImages) {
		this.valouxComponentsImages = valouxComponentsImages;
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
		return ValouxPearls;
	}

	public void setValouxPearls(List<ValouxPearlBean> valouxPearls) {
		ValouxPearls = valouxPearls;
	}

	public List<ValouxMetalBean> getValouxMetals() {
		return ValouxMetals;
	}

	public void setValouxMetals(List<ValouxMetalBean> valouxMetals) {
		ValouxMetals = valouxMetals;
	}

	public List<ItemComponentCertificateBean> getItemComponentCertificate() {
		return itemComponentCertificate;
	}

	public void setItemComponentCertificate(List<ItemComponentCertificateBean> itemComponentCertificate) {
		this.itemComponentCertificate = itemComponentCertificate;
	}
	
	public List<ValouxAppraisalItemsComponentPriceBean> getValouxAppraisalItemsComponentPrices() {
		return this.valouxAppraisalItemsComponentPrices;
	}

	public void setValouxAppraisalItemsComponentPrices(List<ValouxAppraisalItemsComponentPriceBean> valouxAppraisalItemsComponentPrices) {
		this.valouxAppraisalItemsComponentPrices = valouxAppraisalItemsComponentPrices;
	}

	public ValouxAppraisalItemsComponentPriceBean addValouxAppraisalItemsComponentPrice(ValouxAppraisalItemsComponentPriceBean valouxAppraisalItemsComponentPrice) {
		getValouxAppraisalItemsComponentPrices().add(valouxAppraisalItemsComponentPrice);
		valouxAppraisalItemsComponentPrice.setValouxItemComponent(this);

		return valouxAppraisalItemsComponentPrice;
	}

	public ValouxAppraisalItemsComponentPriceBean removeValouxAppraisalItemsComponentPrice(ValouxAppraisalItemsComponentPriceBean valouxAppraisalItemsComponentPrice) {
		getValouxAppraisalItemsComponentPrices().remove(valouxAppraisalItemsComponentPrice);
		valouxAppraisalItemsComponentPrice.setValouxItemComponent(null);

		return valouxAppraisalItemsComponentPrice;
	}
	
	public List<ValouxInclusionBean> getValouxInclusions() {
		return this.valouxInclusions;
	}

	public void setValouxInclusions(List<ValouxInclusionBean> valouxInclusions) {
		this.valouxInclusions = valouxInclusions;
	}

	public ValouxInclusionBean addValouxInclusion(ValouxInclusionBean valouxInclusion) {
		getValouxInclusions().add(valouxInclusion);
		valouxInclusion.setValouxItemComponent(this);

		return valouxInclusion;
	}

	public ValouxInclusionBean removeValouxInclusion(ValouxInclusionBean valouxInclusion) {
		getValouxInclusions().remove(valouxInclusion);
		valouxInclusion.setValouxItemComponent(null);

		return valouxInclusion;
	}

}