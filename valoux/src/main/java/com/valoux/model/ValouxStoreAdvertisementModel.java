/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.model;

import java.io.Serializable;

/**
 * This <java>class</java> ValouxStoreAdvertisementModel having DB table and columns names.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
public class ValouxStoreAdvertisementModel extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer storeAdvertisementId;

	private Integer storeId;

	private String title;

	private String url;

	private String altText;

	private String imgPath;

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
