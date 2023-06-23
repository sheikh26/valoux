package com.valoux.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the valoux_appraisal_items_price database table.
 * 
 */
@Entity
@Table(name="valoux_appraisal_items_price")
public class ValouxAppraisalItemsPriceBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="adjustment_price")
	private Double adjustmentPrice;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="appraised_date")
	private Date appraisedDate;

	@Column(name="appraiser_id", length=100)
	private String appraiserId;

	@Column(name="brand_price_adjustment_notes", columnDefinition = "TEXT")
	private String brandPriceAdjustmentNotes;

	@Column(name="brand_price_adjustment_operator")
	private Byte brandPriceAdjustmentOperator;

	@Column(name="brand_price_adjustment_type")
	private Byte brandPriceAdjustmentType;

	@Column(name="brand_price_adjustment_value")
	private Double brandPriceAdjustmentValue;

	@Column(name="created_by", length=100)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on")
	private Date createdOn;

	@Column(name="final_price")
	private Double finalPrice;

	@Column(name="general_price_adjustment_notes", columnDefinition = "TEXT")
	private String generalPriceAdjustmentNotes;

	@Column(name="general_price_adjustment_operator")
	private Byte generalPriceAdjustmentOperator;

	@Column(name="general_price_adjustment_type")
	private Byte generalPriceAdjustmentType;

	@Column(name="general_price_adjustment_value")
	private Double generalPriceAdjustmentValue;

	@Column(name="market_value")
	private Double marketValue;

	@Column(name="modified_by", length=100)
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_on")
	private Date modifiedOn;

	@Column(name="purchase_price")
	private Double purchasePrice;

	private Byte status;

	//bi-directional many-to-one association to AppraisalBean
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="appraisal_id", nullable=false)
	private AppraisalBean valouxAppraisal;

	//bi-directional many-to-one association to ValouxItemBean
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="item_id", nullable=false)
	private ValouxItemBean valouxItem;

	public ValouxAppraisalItemsPriceBean() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getAdjustmentPrice() {
		return this.adjustmentPrice;
	}

	public void setAdjustmentPrice(Double adjustmentPrice) {
		this.adjustmentPrice = adjustmentPrice;
	}

	public Date getAppraisedDate() {
		return this.appraisedDate;
	}

	public void setAppraisedDate(Date appraisedDate) {
		this.appraisedDate = appraisedDate;
	}

	public String getAppraiserId() {
		return this.appraiserId;
	}

	public void setAppraiserId(String appraiserId) {
		this.appraiserId = appraiserId;
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

	public Double getPurchasePrice() {
		return this.purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public AppraisalBean getValouxAppraisal() {
		return this.valouxAppraisal;
	}

	public void setValouxAppraisal(AppraisalBean valouxAppraisal) {
		this.valouxAppraisal = valouxAppraisal;
	}

	public ValouxItemBean getValouxItem() {
		return this.valouxItem;
	}

	public void setValouxItem(ValouxItemBean valouxItem) {
		this.valouxItem = valouxItem;
	}

}