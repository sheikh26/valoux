/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jettison.json.JSONArray;

/**
 * This <java>class</java> AppraisalItemsModel having all the properties setters
 * and getters methods.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

@XmlRootElement
public class AppraisalItemsModel extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer appraisalId;
	
	private Integer valouxItemId;

	private JSONArray itemId;

	private JSONArray appraisalIdArray;

	private Integer singleItemId;

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

	public JSONArray getItemId() {
		return itemId;
	}

	public void setItemId(JSONArray itemId) {
		this.itemId = itemId;
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

	public JSONArray getAppraisalIdArray() {
		return appraisalIdArray;
	}

	public void setAppraisalIdArray(JSONArray appraisalIdArray) {
		this.appraisalIdArray = appraisalIdArray;
	}

	public Integer getSingleItemId() {
		return singleItemId;
	}

	public void setSingleItemId(Integer singleItemId) {
		this.singleItemId = singleItemId;
	}

	public Integer getValouxItemId() {
		return valouxItemId;
	}

	public void setValouxItemId(Integer valouxItemId) {
		this.valouxItemId = valouxItemId;
	}

}
