/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This <java>class</java> ValouxStoreAdvertisementBean having DB table and columns names.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
@Entity
@Table(name = "valoux_store_advertisement")
public class ValouxStoreAdvertisementBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "said")
	private Integer storeAdvertisementId;

	@Column(name = "store_id")
	private Integer storeId;

	@Column(name = "title")
	private String title;

	@Column(name = "url")
	private String url;

	@Column(name = "alt_text")
	private String altText;

	@Column(name = "img_path")
	private String imgPath;

	@Column(name = "status", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer status;

	public Integer getStoreAdvertisementId() {
		return storeAdvertisementId;
	}

	public void setStoreAdvertisementId(Integer storeAdvertisementId) {
		this.storeAdvertisementId = storeAdvertisementId;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAltText() {
		return altText;
	}

	public void setAltText(String altText) {
		this.altText = altText;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
