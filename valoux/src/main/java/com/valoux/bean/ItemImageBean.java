/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This <java>class</java> ItemImageBean having DB table and columns names.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
@Entity
@Table(name = "item_images")
public class ItemImageBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "imgid")
	private Integer imageId;

	// @Column(name = "item_id")
	// private Integer itemId;

	// bi-directional many-to-one association to ValouxItem
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private ValouxItemBean valouxItemBean;

	@Column(name = "img_url")
	private String imageurl;

	@Column(name = "img_caption")
	private String imageCaption;

	@Column(name = "type", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer imageType;

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	

	public ValouxItemBean getValouxItemBean() {
		return valouxItemBean;
	}

	public void setValouxItemBean(ValouxItemBean valouxItemBean) {
		this.valouxItemBean = valouxItemBean;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public String getImageCaption() {
		return imageCaption;
	}

	public void setImageCaption(String imageCaption) {
		this.imageCaption = imageCaption;
	}

	public Integer getImageType() {
		return imageType;
	}

	public void setImageType(Integer imageType) {
		this.imageType = imageType;
	}

}
