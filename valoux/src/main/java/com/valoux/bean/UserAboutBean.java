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
 * This <java>class</java> UserAboutBean having DB table and columns names.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@Entity
@Table(name = "valoux_consumer_about")
public class UserAboutBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cauid")
	private Integer consumerAboutUserId;

	@Column(name = "rkey")
	private String relationKey;

	@Column(name = "about_text", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer aboutText;

	@Column(name = "about_type", columnDefinition = "TINYINT(3) DEFAULT 0")
	private Integer aboutType;

	@Column(name = "status", columnDefinition = "TINYINT(3) DEFAULT 0")
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
