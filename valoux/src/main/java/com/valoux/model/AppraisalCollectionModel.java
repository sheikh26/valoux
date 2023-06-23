/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jettison.json.JSONArray;

/**
 * This <java>class</java> AppraisalCollectionModel having all the properties
 * setters and getters methods.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@XmlRootElement
public class AppraisalCollectionModel  extends BaseModel implements Serializable  {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer appraisalId;
	
	private Integer valouxCollectionId;

	private JSONArray collectionId;

	private Integer appraisalCollectionId;

	private JSONArray collectionAppraisals;

	private Integer status;

	private String requestType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAppraisalId() {
		return appraisalId;
	}

	public void setAppraisalId(Integer appraisalId) {
		this.appraisalId = appraisalId;
	}

	public JSONArray getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(JSONArray collectionId) {
		this.collectionId = collectionId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public Integer getAppraisalCollectionId() {
		return appraisalCollectionId;
	}

	public void setAppraisalCollectionId(Integer appraisalCollectionId) {
		this.appraisalCollectionId = appraisalCollectionId;
	}

	public JSONArray getCollectionAppraisals() {
		return collectionAppraisals;
	}

	public void setCollectionAppraisals(JSONArray collectionAppraisals) {
		this.collectionAppraisals = collectionAppraisals;
	}

	public Integer getValouxCollectionId() {
		return valouxCollectionId;
	}

	public void setValouxCollectionId(Integer valouxCollectionId) {
		this.valouxCollectionId = valouxCollectionId;
	}

}
