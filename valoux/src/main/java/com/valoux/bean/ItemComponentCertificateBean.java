/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * This <java>class</java> ItemComponentCertificateBean having DB table and
 * columns names.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
@Entity
@Table(name = "item_component_certificate")
public class ItemComponentCertificateBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer ccid;

	@Column(name = "certificate_number", length = 50)
	private String certificateNumber;

	@Column(name = "created_by", length = 100)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_of_certificate")
	private Date dateOfCertificate;

//	@Column(name = "item_component_id", nullable = false)
//	private Integer itemComponentId;
//
//	@Column(name = "item_id", nullable = false)
//	private Integer itemId;
	
	// bi-directional many-to-one association to ValouxItem
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private ValouxItemBean valouxItem;

	// bi-directional many-to-one association to ValouxItemComponent
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_component_id")
	private ValouxItemComponentBean valouxItemComponent;
	
	private Byte lab;

	@Column(name = "laser_id_number", length = 50)
	private String laserIdNumber;

	@Column(name = "laser_inscription", length = 50)
	private String laserInscription;

	@Column(name = "modified_by", length = 100)
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on")
	private Date modifiedOn;

	@Column(length = 200)
	private String name;

	@Column(name = "origin_of_diamond", length = 50)
	private String originOfDiamond;

	@Column(length = 50)
	private String url;

	public ItemComponentCertificateBean() {
	}

	public Integer getCcid() {
		return this.ccid;
	}

	public void setCcid(Integer ccid) {
		this.ccid = ccid;
	}

	public String getCertificateNumber() {
		return this.certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
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

	public Date getDateOfCertificate() {
		return this.dateOfCertificate;
	}

	public void setDateOfCertificate(Date dateOfCertificate) {
		this.dateOfCertificate = dateOfCertificate;
	}

	public ValouxItemBean getValouxItem() {
		return valouxItem;
	}

	public void setValouxItem(ValouxItemBean valouxItem) {
		this.valouxItem = valouxItem;
	}

	public ValouxItemComponentBean getValouxItemComponent() {
		return valouxItemComponent;
	}

	public void setValouxItemComponent(ValouxItemComponentBean valouxItemComponent) {
		this.valouxItemComponent = valouxItemComponent;
	}

	public Byte getLab() {
		return this.lab;
	}

	public void setLab(Byte lab) {
		this.lab = lab;
	}

	public String getLaserIdNumber() {
		return this.laserIdNumber;
	}

	public void setLaserIdNumber(String laserIdNumber) {
		this.laserIdNumber = laserIdNumber;
	}

	public String getLaserInscription() {
		return this.laserInscription;
	}

	public void setLaserInscription(String laserInscription) {
		this.laserInscription = laserInscription;
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

	public String getOriginOfDiamond() {
		return this.originOfDiamond;
	}

	public void setOriginOfDiamond(String originOfDiamond) {
		this.originOfDiamond = originOfDiamond;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}