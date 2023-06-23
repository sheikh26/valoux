package com.valoux.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the valoux_diamond_master_price database table.
 * 
 */
@Entity
@Table(name="valoux_diamond_master_price")
public class ValouxDiamondMasterPriceBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="carat_from")
	private Double caratFrom;

	@Column(name="carat_to")
	private Double caratTo;

	@Column(length=10)
	private String color;

	@Column(name="created_by", length=100)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on", nullable=false)
	private Date createdOn;

	@Column(name="d_cut", length=30)
	private String dCut;

	private Double i1;

	private Double i2;

	private Double i3;

	@Column(name="modified_by", length=100)
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_on")
	private Date modifiedOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="price_date", nullable=false)
	private Date priceDate;

	@Column(name="price_if")
	private Double priceIf;

	private Double si1;

	private Double si2;

	private Double si3;

	@Column(nullable=false)
	private Byte status;

	private Double vs1;

	private Double vs2;

	private Double vvs1;

	private Double vvs2;

	public ValouxDiamondMasterPriceBean() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getCaratFrom() {
		return this.caratFrom;
	}

	public void setCaratFrom(Double caratFrom) {
		this.caratFrom = caratFrom;
	}

	public Double getCaratTo() {
		return this.caratTo;
	}

	public void setCaratTo(Double caratTo) {
		this.caratTo = caratTo;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
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

	public String getDCut() {
		return this.dCut;
	}

	public void setDCut(String dCut) {
		this.dCut = dCut;
	}

	public Double getI1() {
		return this.i1;
	}

	public void setI1(Double i1) {
		this.i1 = i1;
	}

	public Double getI2() {
		return this.i2;
	}

	public void setI2(Double i2) {
		this.i2 = i2;
	}

	public Double getI3() {
		return this.i3;
	}

	public void setI3(Double i3) {
		this.i3 = i3;
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

	public Date getPriceDate() {
		return this.priceDate;
	}

	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}

	public Double getPriceIf() {
		return this.priceIf;
	}

	public void setPriceIf(Double priceIf) {
		this.priceIf = priceIf;
	}

	public Double getSi1() {
		return this.si1;
	}

	public void setSi1(Double si1) {
		this.si1 = si1;
	}

	public Double getSi2() {
		return this.si2;
	}

	public void setSi2(Double si2) {
		this.si2 = si2;
	}

	public Double getSi3() {
		return this.si3;
	}

	public void setSi3(Double si3) {
		this.si3 = si3;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Double getVs1() {
		return this.vs1;
	}

	public void setVs1(Double vs1) {
		this.vs1 = vs1;
	}

	public Double getVs2() {
		return this.vs2;
	}

	public void setVs2(Double vs2) {
		this.vs2 = vs2;
	}

	public Double getVvs1() {
		return this.vvs1;
	}

	public void setVvs1(Double vvs1) {
		this.vvs1 = vvs1;
	}

	public Double getVvs2() {
		return this.vvs2;
	}

	public void setVvs2(Double vvs2) {
		this.vvs2 = vvs2;
	}

}