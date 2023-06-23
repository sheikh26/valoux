package com.valoux.bean;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the valoux_inclusions database table.
 * 
 */
@Entity
@Table(name="valoux_inclusions")
public class ValouxInclusionBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer cid;

	@Column(name="created_by", length=100)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on")
	private Date createdOn;

	private Integer inclusion;

	@Column(name = "inclusion_type", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer inclusionType;

	@Column(name="modified_by", length=100)
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_on")
	private Date modifiedOn;

	@Column(name = "status", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer status;

	//bi-directional many-to-one association to ValouxItemComponentBean
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="item_component_id")
	private ValouxItemComponentBean valouxItemComponent;

	public ValouxInclusionBean() {
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

	public ValouxItemComponentBean getValouxItemComponent() {
		return this.valouxItemComponent;
	}

	public void setValouxItemComponent(ValouxItemComponentBean valouxItemComponent) {
		this.valouxItemComponent = valouxItemComponent;
	}

}