package com.valoux.model;

import java.io.Serializable;

import java.util.Date;

public class ValouxInclusionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer cid;
	
	private String createdBy;
	
	private Date createdOn;
	
	private Integer inclusion;
	
	private Integer inclusionType;
	
	private String modifiedBy;
	
	private Date modifiedOn;
	
	private Integer status;
	
	private Integer itemComponentId;

	public Integer getItemComponentId() {
		return itemComponentId;
	}

	public void setItemComponentId(Integer itemComponentId) {
		this.itemComponentId = itemComponentId;
	}

	public ValouxInclusionModel() {
		
	}

	public Integer getCid() {
		return this.cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
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

	public Integer getInclusion() {
		return this.inclusion;
	}

	public void setInclusion(Integer inclusion) {
		this.inclusion = inclusion;
	}

	public Integer getInclusionType() {
		return this.inclusionType;
	}

	public void setInclusionType(Integer inclusionType) {
		this.inclusionType = inclusionType;
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

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}