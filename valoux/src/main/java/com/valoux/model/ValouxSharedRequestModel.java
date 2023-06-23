package com.valoux.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ValouxSharedRequestModel extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer sharedRequestId;

	private String sharedTo;

	private String sharedBy;

	private String sharedToEmail;

	private Integer sharedItemId;

	private Integer sharedItemType;

	private Integer sharedItemPermission;

	private Integer isRegisteredUser;

	private Integer approveStatus;

	private Integer status;

	public Integer getSharedRequestId() {
		return sharedRequestId;
	}

	public void setSharedRequestId(Integer sharedRequestId) {
		this.sharedRequestId = sharedRequestId;
	}

	public String getSharedTo() {
		return sharedTo;
	}

	public void setSharedTo(String sharedTo) {
		this.sharedTo = sharedTo;
	}

	public String getSharedToEmail() {
		return sharedToEmail;
	}

	public void setSharedToEmail(String sharedToEmail) {
		this.sharedToEmail = sharedToEmail;
	}

	public Integer getSharedItemId() {
		return sharedItemId;
	}

	public void setSharedItemId(Integer sharedItemId) {
		this.sharedItemId = sharedItemId;
	}

	public Integer getSharedItemType() {
		return sharedItemType;
	}

	public void setSharedItemType(Integer sharedItemType) {
		this.sharedItemType = sharedItemType;
	}

	public Integer getSharedItemPermission() {
		return sharedItemPermission;
	}

	public void setSharedItemPermission(Integer sharedItemPermission) {
		this.sharedItemPermission = sharedItemPermission;
	}

	public Integer getIsRegisteredUser() {
		return isRegisteredUser;
	}

	public void setIsRegisteredUser(Integer isRegisteredUser) {
		this.isRegisteredUser = isRegisteredUser;
	}

	public Integer getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(Integer approveStatus) {
		this.approveStatus = approveStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSharedBy() {
		return sharedBy;
	}

	public void setSharedBy(String sharedBy) {
		this.sharedBy = sharedBy;
	}

}
