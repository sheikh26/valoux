/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This <java>class</java> UserAboutModel having all the properties setters and
 * getters methods.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@XmlRootElement
public class UserAboutModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer consumerAboutUserId;

	private String relationKey;

	private Integer aboutText;

	private Integer aboutType;

	private Integer status;

	public Integer getConsumerAboutUserId() {
		return consumerAboutUserId;
	}

	public void setConsumerAboutUserId(Integer consumerAboutUserId) {
		this.consumerAboutUserId = consumerAboutUserId;
	}

	public String getRelationKey() {
		return relationKey;
	}

	public void setRelationKey(String relationKey) {
		this.relationKey = relationKey;
	}

	public Integer getAboutText() {
		return aboutText;
	}

	public void setAboutText(Integer aboutText) {
		this.aboutText = aboutText;
	}

	public Integer getAboutType() {
		return aboutType;
	}

	public void setAboutType(Integer aboutType) {
		this.aboutType = aboutType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
