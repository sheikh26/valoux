/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This <java>class</java> ItemComponentCertificateBean having DB table and
 * columns names.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
@XmlRootElement
public class ItemComponentCertificateModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer ccid;

	private String certificateNumber;

	private String createdBy;

	private Date createdOn;

	private Date dateOfCertificate;

	private Integer itemComponentId;

	private Integer itemId;

	private Integer lab;

	private String laserIdNumber;

	private String laserInscription;

	private String modifiedBy;

	private Date modifiedOn;

	private String name;

	private String originOfDiamond;

	private String url;

	public ItemComponentCertificateModel() {
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

	public Integer getLab() {
		return this.lab;
	}

	public void setLab(Integer lab) {
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