package com.valoux.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the valoux_metals_master_price database table.
 * 
 */
@Entity
@Table(name="valoux_metals_master_price")
public class ValouxMetalsMasterPriceBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="conversion_calc")
	private Double conversionCalc;

	@Column(name="created_by", length=100)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on", nullable=false)
	private Date createdOn;

	@Column(name="metals_type", length=50)
	private String metalsType;

	@Column(name="metals_type_quality", length=50)
	private String metalsTypeQuality;

	@Column(name="modified_by", length=100)
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_on")
	private Date modifiedOn;

	@Column(name="price_unit", length=50)
	private String priceUnit;

	@Column(nullable=false)
	private Byte status;

	@Column(name="unit_price")
	private Double unitPrice;

	@Column(name="valoux_unit")
	private Double valouxUnit;

	public ValouxMetalsMasterPriceBean() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getConversionCalc() {
		return this.conversionCalc;
	}

	public void setConversionCalc(Double conversionCalc) {
		this.conversionCalc = conversionCalc;
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

	public String getMetalsType() {
		return this.metalsType;
	}

	public void setMetalsType(String metalsType) {
		this.metalsType = metalsType;
	}

	public String getMetalsTypeQuality() {
		return this.metalsTypeQuality;
	}

	public void setMetalsTypeQuality(String metalsTypeQuality) {
		this.metalsTypeQuality = metalsTypeQuality;
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

	public String getPriceUnit() {
		return this.priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Double getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getValouxUnit() {
		return this.valouxUnit;
	}

	public void setValouxUnit(Double valouxUnit) {
		this.valouxUnit = valouxUnit;
	}

}