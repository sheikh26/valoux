/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.valoux.bean.AgentBean;
import com.valoux.bean.AppraisalBean;
import com.valoux.bean.CountryBean;
import com.valoux.bean.ItemImageBean;
import com.valoux.bean.LoginBean;
import com.valoux.bean.LoginLogBean;
import com.valoux.bean.StateBean;
import com.valoux.bean.UserBean;
import com.valoux.bean.ValouxAgentStoreBean;
import com.valoux.bean.ValouxCollectionBean;
import com.valoux.bean.ValouxCollectionImageBean;
import com.valoux.bean.ValouxInterestInBean;
import com.valoux.bean.ValouxItemBean;
import com.valoux.bean.ValouxItemTypeBean;
import com.valoux.bean.ValouxPersonalPreferencesBean;
import com.valoux.bean.ValouxSharedRequestBean;
import com.valoux.bean.ValouxStoreBean;
import com.valoux.constant.CommonConstants;
import com.valoux.enums.ValouxRoleEnums.UserRoleType;
import com.valoux.model.AgentModel;
import com.valoux.model.AppraisalCollectionModel;
import com.valoux.model.AppraisalItemsModel;
import com.valoux.model.AppraisalModel;
import com.valoux.model.ItemImageModel;
import com.valoux.model.LoginModel;
import com.valoux.model.StateModel;
import com.valoux.model.UserModel;
import com.valoux.model.ValouxAgentStoreModel;
import com.valoux.model.ValouxCollectionImageModel;
import com.valoux.model.ValouxCollectionModel;
import com.valoux.model.ValouxInterestInModel;
import com.valoux.model.ValouxItemModel;
import com.valoux.model.ValouxItemTypeModel;
import com.valoux.model.ValouxPersonalPreferencesModel;
import com.valoux.model.ValouxSharedRequestModel;
import com.valoux.model.ValouxStoreModel;

/**
 * This <java>class</java> CommonUserUtility set all the beans related to user
 * or agent.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

public class CommonUserUtility {

	public static boolean checkValidRequestForUserLogin(LoginBean userBean) {
		if (userBean.getUserName() != null && userBean.getPassword() != null) {
			return true;
		}
		return false;
	}

	/**
	 * This method use to check mandatory fields of registration form.
	 */
	public static boolean checkValidRequestForUserRegistration(UserModel userModel, LoginModel loginModel) {
		if (userModel.getEmailId() != null && loginModel.getPassword() != null && loginModel.getFirstName() != null
				&& loginModel.getLastName() != null && loginModel.getUserStatus() != null
				&& loginModel.getAuthenticationCode() != null && userModel.getRelationKey() != null
				&& userModel.getAddressLine1() != null && userModel.getCity() != null && userModel.getStateId() != null
				&& userModel.getCountryId() != null && userModel.getZipCode() != null && userModel.getMobile() != null
				&& userModel.getPasswordResetAnswer() != null && userModel.getPasswordResetQuetion() != null
				&& userModel.getIp() != null) {
			return true;
		}

		return false;
	}

	/**
	 * This method check the valid request for change password
	 */
	public static boolean checkvalidRequestForChangePassword(String oldPassword, String newPassword) {
		if (oldPassword != null && !oldPassword.equals("") && newPassword != null && !newPassword.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method use to check mandatory fields of registration form.
	 */
	public static boolean checkValidRequestForAgentRegistration(AgentModel agentModel, LoginModel loginModel,
			ValouxStoreModel storeModel) {
		if (agentModel.getEmailId() != null
				&& loginModel.getPassword() != null
				&& loginModel.getFirstName() != null
				&& loginModel.getLastName() != null
				&& loginModel.getUserStatus() != null
				&& loginModel.getAuthenticationCode() != null
				&& agentModel.getRelationKey() != null
				&& agentModel.getAddressLine1() != null
				&& agentModel.getCity() != null
				&& agentModel.getStateId() != null
				&& agentModel.getCountryId() != null
				&& agentModel.getZipCode() != null
				&& agentModel.getMobile() != null
				&& agentModel.getPasswordResetAnswer() != null
				&& agentModel.getPasswordResetQuetion() != null
				&& agentModel.getIp() != null
				&& storeModel.getStatus() != null
				&& (storeModel.getStoreId() != null || (storeModel.getAddressLine1() != null
						&& storeModel.getCity() != null && storeModel.getCountryId() != null
						&& storeModel.getName() != null 
						&& storeModel.getZipcode() != null && storeModel.getStateId() != null))) {
			return true;
		}
		return false;
	}

	/**
	 * This method use to create bean from model.
	 */
	public static UserBean prepareUserBeanFromModel(UserModel userModel) {
		UserBean userBean = new UserBean();

		userBean.setRelationKey(userModel.getRelationKey());
		userBean.setEmailId(userModel.getEmailId());
		userBean.setAlternateEmailId(userModel.getAlternateEmailId());
		userBean.setGlobalAddress(userModel.getGlobalAddress());
		userBean.setStreetNo(userModel.getStreetNo());
		userBean.setAddressLine1(userModel.getAddressLine1());
		userBean.setAddressLine2(userModel.getAddressLine2());
		userBean.setCity(userModel.getCity());
		userBean.setStateId(userModel.getStateId());
		userBean.setCountryId(userModel.getCountryId());
		userBean.setZipCode(userModel.getZipCode());
		userBean.setMobile(userModel.getMobile());
		userBean.setAlternateMobile(userModel.getAlternateMobile());
		userBean.setDateOfBirth(userModel.getDateOfBirth());
		userBean.setCompany(userModel.getCompany());
		userBean.setPasswordResetQuetion(userModel.getPasswordResetQuetion());
		userBean.setPasswordResetAnswer(userModel.getPasswordResetAnswer());
		userBean.setGender(userModel.getGender());
		userBean.setMaritalStatus(userModel.getMaritalStatus());
		userBean.setIncomeRange(userModel.getIncomeRange());
		userBean.setIp(userModel.getIp());
		userBean.setZip4(userModel.getZip4());
		userBean.setFacebook(userModel.getFacebook());
		userBean.setGoogle(userModel.getGoogle());
		userBean.setInstagparam(userModel.getInstagparam());
		userBean.setTwitter(userModel.getTwitter());
		userBean.setSalutation(userModel.getSalutation());
		userBean.setCreatedOn(userModel.getCreatedOn());
		userBean.setCreatedBy(userModel.getCreatedBy()); // need to update
		userBean.setModifiedBy(userModel.getModifiedBy());
		userBean.setModifiedOn(userModel.getModifiedOn());
		return userBean;
	}

	/**
	 * This method use to create bean from model.
	 */
	public static UserModel prepareUserModelFromBean(UserBean userBean) {
		UserModel userModel = new UserModel();
		userModel.setRelationKey(userBean.getRelationKey());
		userModel.setEmailId(userBean.getEmailId());
		userModel.setAlternateEmailId(userBean.getAlternateEmailId());
		userModel.setGlobalAddress(userBean.getGlobalAddress());
		userModel.setStreetNo(userBean.getStreetNo());
		userModel.setAddressLine1(userBean.getAddressLine1());
		userModel.setAddressLine2(userBean.getAddressLine2());
		userModel.setCity(userBean.getCity());
		userModel.setStateId(userBean.getStateId());
		userModel.setCountryId(userBean.getCountryId());
		userModel.setZipCode(userBean.getZipCode());
		userModel.setMobile(userBean.getMobile());
		userModel.setAlternateMobile(userBean.getAlternateMobile());
		String formattedDate = "";

		if (userBean.getDateOfBirth() != null) {
			formattedDate = CommonConstants.DOB_FORMAT.format(userBean.getDateOfBirth());
		}

		userModel.setDateOfBirth(userBean.getDateOfBirth());
		userModel.setDob(formattedDate);
		userModel.setCompany(userBean.getCompany());
		userModel.setPasswordResetQuetion(userBean.getPasswordResetQuetion());
		userModel.setPasswordResetAnswer(userBean.getPasswordResetAnswer());
		userModel.setGender(userBean.getGender());
		userModel.setMaritalStatus(userBean.getMaritalStatus());
		userModel.setIncomeRange(userBean.getIncomeRange());
		userModel.setIp(userBean.getIp());
		userModel.setZip4(userBean.getZip4());
		userModel.setFacebook(userBean.getFacebook());
		userModel.setGoogle(userBean.getGoogle());
		userModel.setInstagparam(userBean.getInstagparam());
		userModel.setTwitter(userBean.getTwitter());
		userModel.setSalutation(userBean.getSalutation());
		userModel.setImageURL(userBean.getImageURL());
		return userModel;
	}

	/**
	 * This method use to create bean from model.
	 */
	public static LoginBean prepareUserLoginBeanFromModel(LoginModel loginModel) {
		LoginBean loginBean = new LoginBean();

		loginBean.setUserName(loginModel.getUserName());
		loginBean.setPassword(loginModel.getPassword());
		loginBean.setFirstName(loginModel.getFirstName());
		loginBean.setMiddleName(loginModel.getMiddleName());
		loginBean.setLastName(loginModel.getLastName());
		loginBean.setUserStatus(loginModel.getUserStatus());

		loginBean.setCreatedOn(CommonUtility.getDateAndTime());
		loginBean.setCreatedBy(loginModel.getCreatedBy());// to be update
		loginBean.setAuthenticationCode(loginModel.getAuthenticationCode());
		loginBean.setForgetPasswordKey(loginModel.getForgetPasswordKey());
		loginBean.setModifiedBy(loginModel.getModifiedBy());
		loginBean.setModifiedOn(loginModel.getModifiedOn());
		loginBean.setPrivateKey(loginModel.getPrivateKey());
		loginBean.setAuthCodeMobile(loginModel.getAuthCodeMobile());

		return loginBean;
	}

	/**
	 * This method use to create model from bean.
	 */
	public static LoginModel prepareloginModelFromBean(LoginBean loginBean) {
		LoginModel loginModel = new LoginModel();
		loginModel.setUserName(loginBean.getUserName());
		loginModel.setPassword(loginBean.getPassword());
		loginModel.setFirstName(loginBean.getFirstName());
		loginModel.setMiddleName(loginBean.getMiddleName());
		loginModel.setLastName(loginBean.getLastName());
		loginModel.setUserStatus(loginBean.getUserStatus());
		loginModel.setAuthenticationCode(loginBean.getAuthenticationCode());
		loginModel.setForgetPasswordKey(loginBean.getForgetPasswordKey());

		loginModel.setPrivateKey(loginBean.getPrivateKey());

		return loginModel;
	}

	/**
	 * This method use to create agent bean from agent model.
	 */
	public static AgentBean prepareAgentBeanFromAgentModel(AgentModel agentModel) throws Exception{
		AgentBean agentBean = new AgentBean();
		agentBean.setRelationKey(agentModel.getRelationKey());
		agentBean.setEmailId(agentModel.getEmailId());
		agentBean.setAlternateEmailId(agentModel.getAlternateEmailId());
		agentBean.setGlobalAddress(agentModel.getGlobalAddress());
		agentBean.setStreetNo(agentModel.getStreetNo());
		agentBean.setAddressLine1(agentModel.getAddressLine1());
		agentBean.setAddressLine2(agentModel.getAddressLine2());
		agentBean.setCity(agentModel.getCity());
		agentBean.setStateId(agentModel.getStateId());
		agentBean.setCountryId(agentModel.getCountryId());
		agentBean.setZipCode(agentModel.getZipCode());
		agentBean.setMobile(agentModel.getMobile());
		agentBean.setAlternateMobile(agentModel.getAlternateMobile());
		agentBean.setPasswordResetQuetion(agentModel.getPasswordResetQuetion());
		agentBean.setPasswordResetAnswer(agentModel.getPasswordResetAnswer());
		agentBean.setIp(agentModel.getIp());
		agentBean.setCreatedOn(CommonUtility.getDateAndTime());
		agentBean.setCreatedBy(agentModel.getCreatedBy());// to be updated;
		agentBean.setModifiedOn(CommonUtility.getDateAndTime());
		agentBean.setModifiedBy(agentModel.getModifiedBy());
		agentBean.setSignName(agentModel.getSignName());
		if(CommonUserUtility.paparameterNullCheckStringObject(agentModel.getSignUrl())){
			agentBean.setSignUrl(agentModel.getSignUrl());
		}

		return agentBean;
	}

	/**
	 * This method use to create agent model from agent bean.
	 * @throws Exception 
	 */
	public static AgentModel prepareAgentModelFromAgentBean(AgentBean agentbean) throws Exception {
		AgentModel agentModel = new AgentModel();
		agentModel.setRelationKey(agentbean.getRelationKey());
		agentModel.setEmailId(agentbean.getEmailId());
		agentModel.setAlternateEmailId(agentbean.getAlternateEmailId());
		agentModel.setGlobalAddress(agentbean.getGlobalAddress());
		agentModel.setStreetNo(agentbean.getStreetNo());
		agentModel.setAddressLine1(agentbean.getAddressLine1());
		agentModel.setAddressLine2(agentbean.getAddressLine2());
		agentModel.setCity(agentbean.getCity());
		agentModel.setStateId(agentbean.getStateId());
		agentModel.setCountryId(agentbean.getCountryId());
		agentModel.setZipCode(agentbean.getZipCode());
		agentModel.setMobile(agentbean.getMobile());
		agentModel.setAlternateMobile(agentbean.getAlternateMobile());
		agentModel.setPasswordResetQuetion(agentbean.getPasswordResetQuetion());
		agentModel.setPasswordResetAnswer(agentbean.getPasswordResetAnswer());
		agentModel.setIp(agentbean.getIp());
		agentModel.setCreatedOn(agentbean.getCreatedOn());
		agentModel.setCreatedBy(agentbean.getCreatedBy());// to be updated;
		agentModel.setAgentId(agentbean.getAgentId());
		if(CommonUserUtility.paparameterNullCheckStringObject(agentbean.getSignName())){
			agentModel.setSignName(agentbean.getSignName());
		}
		if(CommonUserUtility.paparameterNullCheckStringObject(agentbean.getSignUrl())){
			agentModel.setSignUrl(agentbean.getSignUrl());
		}
		return agentModel;
	}

	/**
	 * This method use to check mandatory fields of Item form.
	 */
	public static Boolean checkValidRequestForAddItem(ValouxItemModel itemModel, ValouxStoreModel storeModel) {
		if (itemModel.getStoreExist() != null
				&& itemModel.getrKey() != null
				&& itemModel.getsDescription() != null
				&& (storeModel.getStoreId() != null || (storeModel.getAddressLine1() != null
						&& storeModel.getAddressLine2() != null && storeModel.getCity() != null
						&& storeModel.getCountryId() != null && storeModel.getName() != null
						&& storeModel.getStreetNumber() != null && storeModel.getZipcode() != null && storeModel
						.getStateId() != null))) {
			return true;
		}
		return false;
	}

	/**
	 * This method use to check mandatory fields of Item form.
	 * 
	 * @paparam itemModel
	 * @return
	 */
	public static Boolean checkValidRequestForAddItem(ValouxItemModel itemModel) {
		if (itemModel.getStoreExist() != null && itemModel.getrKey() != null && itemModel.getsDescription() != null) {
			return true;
		}
		return false;
	}

	/**
	 * This method converts item model into item bean
	 * 
	 * @paparam itemModel
	 * @return
	 */
	public static ValouxItemBean prepareItemBeanFromItemModel(ValouxItemModel itemModel) {
		ValouxItemBean itemBean = new ValouxItemBean();
		itemBean.setCreatedBy(itemModel.getCreatedBy());// to be updated
		itemBean.setCreatedOn(itemModel.getModifiedOn());
		itemBean.setDesignerPrice(itemModel.getDesignerPrice());
		itemBean.setDesignerPriceType(itemModel.getDesignerPriceType());
		itemBean.setDesignType(itemModel.getDesignType());
		itemBean.setGender(itemModel.getGender());
		itemBean.setItemId(itemModel.getItemId());
//		itemBean.setItemTypeIt(itemModel.getItemTypeIt());
		ValouxItemTypeBean valouxItemTypeBean = new ValouxItemTypeBean();
		valouxItemTypeBean.setItemTypeId(itemModel.getItemTypeIt());
		itemBean.setValouxItemTypeBean(valouxItemTypeBean);
		
		itemBean.setModifiedBy(itemModel.getModifiedBy());
		itemBean.setModifiedOn(itemModel.getModifiedOn());
		itemBean.setName(itemModel.getName());
		itemBean.setQuantity(itemModel.getQuantity());
		itemBean.setrKey(itemModel.getrKey());
		itemBean.setSalesPrice(itemModel.getSalesPrice());
		itemBean.setSalesTax(itemModel.getSalesTax());
		itemBean.setsDescription(itemModel.getsDescription());
		// itemBean.setStoreExist(itemModel.getStoreExist());
		itemBean.setStoreId(itemModel.getStoreId());
		itemBean.setValouxMarketValue(itemModel.getValouxMarketValue());
		itemBean.setItemStatus(itemModel.getItemStatus());
		return itemBean;
	}

	/**
	 * This method create item model from item bean
	 * 
	 * @paparam itemBean
	 * @return
	 */
	public static ValouxItemModel prepareItemModelFromitemBean(ValouxItemBean itemBean) {
		ValouxItemModel itemModel = new ValouxItemModel();
		itemModel.setCreatedBy(itemBean.getCreatedBy());// to be updated
		itemModel.setCreatedOn(itemBean.getModifiedOn());
		itemModel.setDesignerPrice(itemBean.getDesignerPrice());
		itemModel.setDesignerPriceType(itemBean.getDesignerPriceType());
		itemModel.setDesignType(itemBean.getDesignType());
		itemModel.setGender(itemBean.getGender());
		itemModel.setItemId(itemBean.getItemId());
//		itemModel.setItemTypeIt(itemBean.getItemTypeIt());
		itemModel.setItemTypeIt(itemBean.getValouxItemTypeBean().getItemTypeId());
				
		itemModel.setModifiedBy(itemBean.getModifiedBy());
		itemModel.setModifiedOn(itemBean.getModifiedOn());
		itemModel.setName(itemBean.getName());
		itemModel.setQuantity(itemBean.getQuantity());
		itemModel.setrKey(itemBean.getrKey());
		itemModel.setSalesPrice(itemBean.getSalesPrice());
		itemModel.setSalesTax(itemBean.getSalesTax());
		itemModel.setsDescription(itemBean.getsDescription());
		itemModel.setItemStatus(itemBean.getItemStatus());
		itemModel.setLastAppraisaedPrice(itemBean.getLastAppraisaedPrice());
		itemModel.setLastAppraisedDate(itemBean.getLastAppraisedDate());
		itemModel.setMarketValue(itemBean.getMarketValue());
		itemModel.setFinalPrice(itemBean.getFinalPrice());
		// itemBean.setStoreExist(itemBean.getStoreExist());
		itemModel.setStoreId(itemBean.getStoreId());
		itemModel.setValouxMarketValue(itemModel.getValouxMarketValue());
		return itemModel;
	}

	/**
	 * This method converts itemimagemodel in bean
	 * 
	 * @paparam itemImageModel
	 * @return
	 */
	public static ItemImageBean prepareItemImageBeanFromModel(ItemImageModel itemImageModel) {
		ItemImageBean itemImageBean = new ItemImageBean();
		itemImageBean.setCreatedBy(itemImageModel.getCreatedBy());
		itemImageBean.setCreatedOn(itemImageModel.getCreatedOn());
		itemImageBean.setImageCaption(itemImageModel.getImageCaption());
		itemImageBean.setImageId(itemImageModel.getImageId());
		itemImageBean.setImageType(itemImageModel.getImageType());
		itemImageBean.setImageurl(itemImageModel.getImageurl());
//		itemImageBean.setItemId(itemImageModel.getItemId());
		ValouxItemBean valouxItemBean = new ValouxItemBean();
		valouxItemBean.setItemId(itemImageModel.getItemId());
		itemImageBean.setValouxItemBean(valouxItemBean);
		
		itemImageBean.setModifiedBy(itemImageModel.getModifiedBy());
		itemImageBean.setModifiedOn(itemImageModel.getModifiedOn());
		return itemImageBean;
	}

	/**
	 * This method converts itemimagebean in model
	 * 
	 * @paparam itemImagebean
	 * @return
	 */
	public static ItemImageModel prepareItemImageModelFromBean(ItemImageBean itemImageBean) {
		ItemImageModel itemImagemodel = new ItemImageModel();
		itemImagemodel.setCreatedBy(itemImageBean.getCreatedBy());
		itemImagemodel.setCreatedOn(itemImageBean.getCreatedOn());
		itemImagemodel.setImageCaption(itemImageBean.getImageCaption());
		itemImagemodel.setImageId(itemImageBean.getImageId());
		itemImagemodel.setImageType(itemImageBean.getImageType());
		itemImagemodel.setImageurl(itemImageBean.getImageurl());
//		itemImagemodel.setItemId(itemImageBean.getItemId());
		itemImagemodel.setItemId(itemImageBean.getValouxItemBean().getItemId());
		itemImagemodel.setModifiedBy(itemImageBean.getModifiedBy());
		itemImagemodel.setModifiedOn(itemImageBean.getModifiedOn());
		return itemImagemodel;
	}

	/**
	 * This method use to check valid request for update user detail.
	 */
	public static Boolean checkValidRequestForUpdateUserProfile(UserModel userModel, LoginModel loginModel) {
		if (userModel.getEmailId() != null && loginModel.getPassword() != null && loginModel.getFirstName() != null
				&& loginModel.getLastName() != null && userModel.getGlobalAddress() != null
				&& userModel.getAddressLine1() != null && userModel.getAlternateEmailId() != null
				&& userModel.getAlternateMobile() != null && userModel.getCity() != null
				&& userModel.getCountryId() != null && userModel.getIp() != null && loginModel.getLastName() != null
				&& userModel.getMobile() != null && userModel.getPasswordResetAnswer() != null
				&& userModel.getPasswordResetQuetion() != null && userModel.getStateId() != null
				&& userModel.getZipCode() != null && userModel.getCustomerId() != null) {
			return true;
		}
		return false;
	}

	/**
	 * This method use to create bean from model.
	 */
	public static ValouxInterestInModel convertInterestInbeanToModel(ValouxInterestInBean interestInBean) {
		ValouxInterestInModel interestInModel = new ValouxInterestInModel();
		interestInModel.setInterest(interestInBean.getInterest());
		interestInModel.setInterestId(interestInBean.getInterestId());
		interestInModel.setStatus(interestInBean.getStatus());
		return interestInModel;
	}

	/**
	 * This method use to create bean from model.
	 */
	public static ValouxPersonalPreferencesModel ConvertPPBeanToModel(ValouxPersonalPreferencesBean ppBean) {
		ValouxPersonalPreferencesModel ppModel = new ValouxPersonalPreferencesModel();
		ppModel.setPersonalId(ppBean.getPersonalId());
		ppModel.setPersonalPreferences(ppBean.getPersonalPreferences());
		ppModel.setPpstatus(ppBean.getPpstatus());
		ppModel.setPpType(ppBean.getPpType());
		return ppModel;
	}

	/**
	 * This method use to create state model from bean.
	 */
	public static StateModel prepareStateModelFromBean(StateBean stateBean) {
		StateModel stateModel = new StateModel();
		stateModel.setCountryId(stateBean.getCountryId());
		stateModel.setName(stateBean.getName());
		stateModel.setShortCode(stateBean.getShortCode());
		stateModel.setStateId(stateBean.getStateId());
		return stateModel;
	}

	/**
	 * This method use to create state model from bean.
	 */
	public static StateBean prepareStateBeanFromModel(String stateName, Integer countryId) {
		StateBean stateBean = new StateBean();
		stateBean.setCountryId(countryId);
		// stateBean.setCreatedBy(1);
		stateBean.setCreatedOn(CommonUtility.getDateAndTime());
		// stateBean.setModifiedBy(1);
		stateBean.setModifiedOn(CommonUtility.getDateAndTime());
		stateBean.setName(stateName);
		stateBean.setShortCode("");// to be updated
		return stateBean;
	}

	/**
	 * This method use to create country bean .
	 */
	public static CountryBean prepareCountryBean(String countryName) {
		CountryBean countryBean = new CountryBean();
		// countryBean.setCreatedBy(1);
		countryBean.setCreatedOn(CommonUtility.getDateAndTime());
		// countryBean.setModifiedBy(1);
		countryBean.setModifiedOn(CommonUtility.getDateAndTime());
		countryBean.setName(countryName);
		// countryBean.setShortCode(countryName);
		return countryBean;
	}

	/**
	 * This method use to create store bean from model.
	 */
	public static ValouxStoreBean prepareStoreBeanFromModel(ValouxStoreModel storeModel) {
		ValouxStoreBean storeBean = new ValouxStoreBean();
		storeBean.setAddressLine1(storeModel.getAddressLine1());
		storeBean.setAddressLine2(storeModel.getAddressLine2());
		storeBean.setAlternatePhone(storeModel.getAlternatePhone());
		storeBean.setCity(storeModel.getCity());
		storeBean.setCountryId(storeModel.getCountryId());
		storeBean.setCreatedBy(storeModel.getCreatedBy());
		storeBean.setCreatedOn(storeModel.getCreatedOn());
		storeBean.setEmail(storeModel.getEmail());
		storeBean.setFacebook(storeModel.getFacebook());
		storeBean.setgAddress(storeModel.getgAddress());
		storeBean.setGoogle(storeModel.getGoogle());
		storeBean.setInstagparam(storeModel.getInstagparam());
		storeBean.setIpaddress(storeModel.getIpaddress());
		storeBean.setModifiedBy(storeModel.getModifiedBy());
		storeBean.setModifiedOn(storeModel.getModifiedOn());
		storeBean.setName(storeModel.getName());
		storeBean.setPhone(storeModel.getPhone());
		storeBean.setStateId(storeModel.getStateId());
		storeBean.setStatus(storeModel.getStatus());
		storeBean.setStreetNumber(storeModel.getStreetNumber());
		storeBean.setTwitter(storeModel.getTwitter());
		storeBean.setWebsite(storeModel.getWebsite());
		storeBean.setZipcode(storeModel.getZipcode());
		storeBean.setZipcode4(storeModel.getZipcode4());
		storeBean.setCreatedOn(CommonUtility.getDateAndTime());
		storeBean.setStoreId(storeModel.getStoreId());
		return storeBean;
	}

	/**
	 * This method use to create store model from bean.
	 */
	public static ValouxStoreModel prepareStoreModelFromBean(ValouxStoreBean storeBean, String countryName,
			String stateName) {
		ValouxStoreModel storeModel = new ValouxStoreModel();
		storeModel.setAddressLine1(storeBean.getAddressLine1());
		storeModel.setAddressLine2(storeBean.getAddressLine2());
		storeModel.setAlternatePhone(storeBean.getAlternatePhone());
		storeModel.setCity(storeBean.getCity());
		storeModel.setCountryId(storeBean.getCountryId());
		storeModel.setCreatedBy(storeBean.getCreatedBy());
		storeModel.setCreatedOn(storeBean.getCreatedOn());
		storeModel.setEmail(storeBean.getEmail());
		storeModel.setFacebook(storeBean.getFacebook());
		storeModel.setgAddress(storeBean.getgAddress());
		storeModel.setGoogle(storeBean.getGoogle());
		storeModel.setInstagparam(storeBean.getInstagparam());
		storeModel.setIpaddress(storeBean.getIpaddress());
		storeModel.setModifiedBy(storeBean.getModifiedBy());
		storeModel.setModifiedOn(storeBean.getModifiedOn());
		storeModel.setName(storeBean.getName());
		storeModel.setPhone(storeBean.getPhone());
		storeModel.setStateId(storeBean.getStateId());
		storeModel.setStatus(storeBean.getStatus());
		storeModel.setStreetNumber(storeBean.getStreetNumber());
		storeModel.setTwitter(storeBean.getTwitter());
		storeModel.setWebsite(storeBean.getWebsite());
		storeModel.setZipcode(storeBean.getZipcode());
		storeModel.setZipcode4(storeBean.getZipcode4());
		storeModel.setStoreId(storeBean.getStoreId());
		storeModel.setCountryName(countryName);
		storeModel.setStateName(stateName);
		return storeModel;
	}

	/**
	 * This Method use to check valid request to create store
	 */
	public static Boolean checkValidRequestToCreateStore(ValouxStoreBean storeBean) {
		if (storeBean.getStatus() != null && storeBean.getAddressLine1() != null && storeBean.getAddressLine2() != null
				&& storeBean.getAlternatePhone() != null 
				&& storeBean.getCity() != null && storeBean.getCountryId() != null && storeBean.getEmail() != null
				&& storeBean.getFacebook() != null && storeBean.getgAddress() != null && storeBean.getGoogle() != null
				&& storeBean.getInstagparam() != null && storeBean.getName() != null && storeBean.getPhone() != null
				&& storeBean.getStateId() != null && storeBean.getStreetNumber() != null
				&& storeBean.getTwitter() != null && storeBean.getWebsite() != null && storeBean.getZipcode() != null) {
			return true;
		}
		return false;
	}

	/**
	 * this method convert ValouxItemTypeModel in ValouxItemTypeBean
	 */
	public static ValouxItemTypeModel convertItemTypeModelIntoBean(ValouxItemTypeBean itemTypeBean) {
		ValouxItemTypeModel itemTypeModel = new ValouxItemTypeModel();
		itemTypeModel.setItemType(itemTypeBean.getItemType());
		itemTypeModel.setItemTypeId(itemTypeBean.getItemTypeId());
		itemTypeModel.setStatus(itemTypeBean.getStatus());
		return itemTypeModel;
	}

	/**
	 * This method use to create country bean .
	 */
	public static LoginLogBean prepareLoginLogsBeanForUser(UserBean userBean) {
		LoginLogBean loginLogBean = new LoginLogBean();
		loginLogBean.setRelationKey(userBean.getRelationKey());
		loginLogBean.setIpAddress(userBean.getIp());
		loginLogBean.setLoginDate(CommonUtility.getDateAndTime());
		loginLogBean.setLogoutDate(CommonUtility.getDateAndTime());
		loginLogBean.setCreatedBy(userBean.getRelationKey());
		loginLogBean.setCreatedOn(CommonUtility.getDateAndTime());
		loginLogBean.setModifiedBy(userBean.getRelationKey());
		loginLogBean.setModifiedOn(CommonUtility.getDateAndTime());
		loginLogBean.setLogStatus(1);
		return loginLogBean;
	}

	/**
	 * This method use to create country bean .
	 */
	public static LoginLogBean prepareLoginLogsBeanForAgent(AgentBean agentBean) {
		String authLoginCode = String.valueOf(CommonUtility.generateRandom(6));
		// String authLoginCode = CommonUtility.generatetToken();
		LoginLogBean loginLogBean = new LoginLogBean();
		loginLogBean.setRelationKey(agentBean.getRelationKey());
		loginLogBean.setIpAddress(agentBean.getIp());
		loginLogBean.setAuthLoginCode(authLoginCode);
		loginLogBean.setLoginDate(CommonUtility.getDateAndTime());
		loginLogBean.setLogoutDate(CommonUtility.getDateAndTime());
		loginLogBean.setCreatedBy(agentBean.getRelationKey());
		loginLogBean.setCreatedOn(CommonUtility.getDateAndTime());
		loginLogBean.setModifiedBy(agentBean.getRelationKey());
		loginLogBean.setModifiedOn(CommonUtility.getDateAndTime());
		loginLogBean.setLogStatus(1);
		return loginLogBean;
	}

	/**
	 * This method converts ValouxAgentStoreModel into Bean
	 */
	public static ValouxAgentStoreBean prepareAgentStoreBeanFromModel(ValouxAgentStoreModel agentStoreModel) {
		ValouxAgentStoreBean agentStoreBean = new ValouxAgentStoreBean();
		agentStoreBean.setCreatedBy(agentStoreModel.getCreatedBy());
		agentStoreBean.setCreatedOn(agentStoreModel.getCreatedOn());
		agentStoreBean.setModifiedBy(agentStoreModel.getModifiedBy());
		agentStoreBean.setModifiedOn(agentStoreModel.getModifiedOn());
		agentStoreBean.setRelationKey(agentStoreModel.getRelationKey());
		ValouxStoreBean valouxStoreBean = new ValouxStoreBean();
		valouxStoreBean.setStoreId(agentStoreModel.getStoreId());
		agentStoreBean.setValouxStoreBean(valouxStoreBean);
//		agentStoreBean.setStoreId(agentStoreModel.getStoreId());
		return agentStoreBean;
	}

	/**
	 * This method converts ValouxagentStoreModel into Bean
	 */
	public static ValouxAgentStoreModel prepareAgentStoreModelFromBean(ValouxAgentStoreBean agentStoreBean) {
		ValouxAgentStoreModel agentStoreModel = new ValouxAgentStoreModel();

		if (agentStoreBean != null) {
			agentStoreModel.setCreatedBy(agentStoreBean.getCreatedBy());
			agentStoreModel.setCreatedOn(agentStoreBean.getCreatedOn());
			agentStoreModel.setModifiedBy(agentStoreBean.getModifiedBy());
			agentStoreModel.setModifiedOn(agentStoreBean.getModifiedOn());
			agentStoreModel.setRelationKey(agentStoreBean.getRelationKey());
			agentStoreModel.setStoreId(agentStoreBean.getValouxStoreBean().getStoreId());
			agentStoreModel.setAgentStoreId(agentStoreBean.getAgentStoreId());
		}
		return agentStoreModel;
	}

	/**
	 * Method for populating agent list from store
	 * 
	 * @paparam agentModels
	 * @paparam agentStoreBeans
	 * @return
	 */
	public static List<ValouxAgentStoreModel> populateValouxAgentListFromStoreBean(
			List<ValouxAgentStoreModel> agentModels, List<ValouxAgentStoreBean> agentStoreBeans) {

		ValouxAgentStoreModel agentStoreModel = null;

		for (ValouxAgentStoreBean valouxAgentStoreBean : agentStoreBeans) {

			agentStoreModel = new ValouxAgentStoreModel();
			agentStoreModel.setRelationKey(valouxAgentStoreBean.getRelationKey());
			agentModels.add(agentStoreModel);
		}

		return agentModels;
	}

	/**
	 * Method will return role name of role id
	 * 
	 * @paparam roleId
	 * @return
	 */
	public static String getRoleName(int roleId) {

		String name = "";

		try {
			switch (roleId) {
			case 1:
				name = UserRoleType.StoreOwner.toString();
				break;
			case 2:
				name = UserRoleType.StoreAdmin.toString();
				break;
			case 3:
				name = UserRoleType.StoreManager.toString();
				break;
			case 4:
				name = UserRoleType.StoreSalesperson.toString();
				break;
			case 5:
				name = UserRoleType.StoreAppraiser.toString();
				break;
			case 6:
				name = UserRoleType.Appraiser.toString();
				break;
			case 7:
				name = UserRoleType.ConsumerAdmin.toString();
				break;
			case 8:
				name = "Unknown";
				break;
			case 9:
				name = UserRoleType.PartnerAdmin.toString();
				break;
			case 10:
				name = UserRoleType.VenderAdmin.toString();
				break;
			case 11:
				name = UserRoleType.SuperAdmin.toString();
				break;
			default:
				name = "No Role";
				throw new IllegalArgumentException("No Enum specified for this role id -" + roleId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}

	/**
	 * This method use to check mandatory fields of Appraisal form.
	 */
	public static boolean checkValidRequestForAppraisal(AppraisalModel appraisalModel) {
		if (appraisalModel.getRelationKey() != null && appraisalModel.getName() != null) {
			return true;
		}

		return false;
	}

	/**
	 * This method use to create bean from model.
	 */
	public static AppraisalBean prepareAppraisalBeanFromModel(AppraisalModel appraisalModel, AppraisalBean appraisalBean) {
		if (appraisalModel.getAppraisalId() != null && appraisalModel.getRequestType().equalsIgnoreCase("Update")) {
			if (appraisalModel.getName() != null) {
				appraisalBean.setName(appraisalModel.getName());
			}
			if (appraisalModel.getShortDescription() != null) {
				appraisalBean.setShortDescription(appraisalModel.getShortDescription());

			}
			appraisalBean.setaStatus(CommonConstants.APPRAISAL_STATUS_INACTIVE);
																		// to
			appraisalBean.setModifiedBy(appraisalModel.getModifiedBy());
			appraisalBean.setModifiedOn(appraisalModel.getModifiedOn());

		} else {
			appraisalBean = new AppraisalBean();
			appraisalBean.setRelationKey(appraisalModel.getRelationKey());
			appraisalBean.setName(appraisalModel.getName());
			appraisalBean.setShortDescription(appraisalModel.getShortDescription());
			appraisalBean.setaStatus(CommonConstants.APPRAISAL_STATUS_INACTIVE);
			appraisalBean.setCreatedOn(appraisalModel.getCreatedOn());
			appraisalBean.setCreatedBy(appraisalModel.getCreatedBy()); // need
																		// to
			appraisalBean.setModifiedBy(appraisalModel.getModifiedBy());
			appraisalBean.setModifiedOn(appraisalModel.getModifiedOn());
		}

		return appraisalBean;
	}

	/**
	 * This method use to check mandatory fields of Appraisal form for Items.
	 */
	public static boolean checkValidRequestForAppraisalItems(AppraisalItemsModel appraisalItemsModel) {
		if (appraisalItemsModel.getAppraisalId() != null && appraisalItemsModel.getItemId() != null) {
			return true;
		}

		return false;
	}

	/**
	 * This method use to check mandatory fields of Appraisal form for
	 * Collection.
	 */
	public static boolean checkValidRequestForAppraisalCollection(AppraisalCollectionModel appraisalCollectionModel) {
		if (appraisalCollectionModel.getAppraisalId() != null && appraisalCollectionModel.getCollectionId() != null) {
			return true;
		}

		return false;
	}

	/**
	 * This method use to check mandatory fields of Appraisal form for Items and
	 * Collection.
	 */
	public static boolean checkValidRequestForAppraisalItemsAndCollection(AppraisalItemsModel appraisalItemsModel,
			AppraisalCollectionModel appraisalCollectionModel) {
		if (appraisalItemsModel.getItemId() != null && appraisalCollectionModel.getCollectionId() != null) {
			return true;
		}

		return false;
	}

	/**
	 * This method use to check mandatory fields for Approved Or Disapproved
	 * Appraisal.
	 */
	public static boolean checkValidRequestForAppraisalApprovedOrDisapproved(AppraisalModel appraisalModel) {
		if (appraisalModel.getAppraisalId() != null && appraisalModel.getaStatus() != null
				&& appraisalModel.getApprovedBy() != null) {
			return true;
		}

		return false;
	}

	/**
	 * This method use to create bean from model.
	 */
	public static AppraisalBean prepareAppraisalApprovedDisapprovedBeanFromModel(AppraisalModel appraisalModel,
			AppraisalBean appraisalBean) {
		appraisalBean.setAppraisalId(appraisalModel.getAppraisalId());
		if (appraisalModel.getaStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)) {
			appraisalBean.setaStatus(CommonConstants.APPRAISAL_STATUS_APPROVED);
		} else {
			appraisalBean.setaStatus(CommonConstants.APPRAISAL_STATUS_DIS_APPROVED);
		}
		appraisalBean.setApprovedBy(appraisalModel.getApprovedBy());
		appraisalBean.setApprovedOn(CommonUtility.getDateAndTime());
		appraisalBean.setModifiedOn(CommonUtility.getDateAndTime());
		appraisalBean.setModifiedBy(appraisalModel.getApprovedBy());
		return appraisalBean;
	}

	/**
	 * This method create collection model from collection bean
	 * 
	 * @paparam collectionBean
	 * @return
	 */
	public static ValouxCollectionModel prepareCollectionModelFromCollectionBean(ValouxCollectionBean collectionBean) {
		ValouxCollectionModel collectionModel = new ValouxCollectionModel();
		collectionModel.setCreatedBy(collectionBean.getRkey());
		collectionModel.setCreatedOn(collectionBean.getModifiedOn());
		collectionModel.setCname(collectionBean.getCname());
		collectionModel.setCollectionStatus(collectionBean.getCollectionStatus());
		collectionModel.setShortDescription(collectionBean.getShortDescription());
		collectionModel.setVcid(collectionBean.getVcid());
		collectionModel.setModifiedBy(collectionBean.getRkey());
		collectionModel.setModifiedOn(collectionBean.getModifiedOn());
		return collectionModel;
	}

	public static ValouxSharedRequestBean converSharedRequestModelIntoBean(ValouxSharedRequestModel sharedRequestModel) {
		ValouxSharedRequestBean sharedRequestBean = new ValouxSharedRequestBean();
		sharedRequestBean.setApproveStatus(sharedRequestModel.getApproveStatus());
		sharedRequestBean.setCreatedBy(sharedRequestModel.getCreatedBy());
		sharedRequestBean.setCreatedOn(sharedRequestModel.getCreatedOn());
		sharedRequestBean.setIsRegisteredUser(sharedRequestModel.getIsRegisteredUser());
		sharedRequestBean.setModifiedBy(sharedRequestBean.getModifiedBy());
		sharedRequestBean.setModifiedOn(sharedRequestModel.getModifiedOn());
		sharedRequestBean.setSharedItemId(sharedRequestModel.getSharedItemId());
		sharedRequestBean.setSharedItemPermission(sharedRequestModel.getSharedItemPermission());
		sharedRequestBean.setSharedItemType(sharedRequestModel.getSharedItemType());
		sharedRequestBean.setSharedTo(sharedRequestModel.getSharedTo());
		sharedRequestBean.setSharedToEmail(sharedRequestModel.getSharedToEmail());
		sharedRequestBean.setSharedBy(sharedRequestModel.getSharedBy());
		sharedRequestBean.setStatus(sharedRequestModel.getStatus());
		return sharedRequestBean;

	}

	public static ValouxSharedRequestModel converSharedRequestBeanIntoModel(ValouxSharedRequestBean sharedRequestBean) {
		ValouxSharedRequestModel sharedRequestModel = new ValouxSharedRequestModel();
		sharedRequestModel.setApproveStatus(sharedRequestBean.getApproveStatus());
		sharedRequestModel.setCreatedBy(sharedRequestBean.getCreatedBy());
		sharedRequestModel.setCreatedOn(sharedRequestBean.getCreatedOn());
		sharedRequestModel.setIsRegisteredUser(sharedRequestBean.getIsRegisteredUser());
		sharedRequestModel.setModifiedBy(sharedRequestModel.getModifiedBy());
		sharedRequestModel.setModifiedOn(sharedRequestBean.getModifiedOn());
		sharedRequestModel.setSharedItemId(sharedRequestBean.getSharedItemId());
		sharedRequestModel.setSharedItemPermission(sharedRequestBean.getSharedItemPermission());
		sharedRequestModel.setSharedItemType(sharedRequestBean.getSharedItemType());
		sharedRequestModel.setSharedTo(sharedRequestBean.getSharedTo());
		sharedRequestModel.setSharedToEmail(sharedRequestBean.getSharedToEmail());
		sharedRequestModel.setSharedBy(sharedRequestBean.getSharedBy());
		sharedRequestModel.setStatus(sharedRequestBean.getStatus());
		return sharedRequestModel;
	}

	/**
	 * This method create items model from items bean
	 * 
	 * @paparam itemsBean
	 * @return
	 */
	public static ValouxItemModel prepareitemsModelFromItemsBean(ValouxItemBean itemsBean) {
		ValouxItemModel itemsModel = new ValouxItemModel();
		itemsModel.setCreatedBy(itemsBean.getrKey());
		itemsModel.setCreatedOn(itemsBean.getModifiedOn());
		itemsModel.setDesignerPrice(itemsBean.getDesignerPrice());
		itemsModel.setDesignerPriceType(itemsBean.getDesignerPriceType());
		itemsModel.setDesignType(itemsBean.getDesignType());
		itemsModel.setGender(itemsBean.getGender());
		itemsModel.setModifiedBy(itemsBean.getrKey());
		itemsModel.setModifiedOn(itemsBean.getModifiedOn());
		itemsModel.setItemId(itemsBean.getItemId());
//		itemsModel.setItemTypeIt(itemsBean.getItemTypeIt());
		itemsModel.setItemTypeIt(itemsBean.getValouxItemTypeBean().getItemTypeId());
		itemsModel.setQuantity(itemsBean.getQuantity());
		itemsModel.setStoreId(itemsBean.getStoreId());
		itemsModel.setName(itemsBean.getName());
		itemsModel.setSalesPrice(itemsBean.getSalesPrice());
		itemsModel.setSalesTax(itemsBean.getSalesTax());
		itemsModel.setsDescription(itemsBean.getsDescription());
		return itemsModel;
	}

	/**
	 * Method for checking string
	 * 
	 * @paparam paparamName
	 * @return
	 * @throws Exception
	 * @author Paparamjeet
	 */
	public static boolean paparameterNullCheckStringObject(String paparamName) throws Exception {

		if (paparamName != "" && paparamName != null  && !("null").equals(paparamName) && !paparamName.equals("") && !paparamName.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * Method for checking any object
	 * 
	 * @paparam paparamName
	 * @return
	 * @throws Exception
	 * @author Paparamjeet
	 */
	public static boolean paparameterNullCheckObject(Object paparamName) throws Exception {

		if (paparamName != null) {
			return true;
		}
		return false;
	}

	/**
	 * Generic method for checking required missing paparameters
	 * 
	 * @paparam requestPaparams
	 * @paparam jObject
	 * @return
	 * @throws Exception
	 * @author Paparamjeet
	 */
	public static String checkJSONRequiredPaparameters(String[] requiredPaparams, JSONObject jObject) throws Exception {

		StringBuffer missingPaparams = new StringBuffer();

		if (requiredPaparams != null && requiredPaparams.length > 0) {
			for (int i = 0; i < requiredPaparams.length; i++) {
				if (!jObject.has(requiredPaparams[i])) {
					missingPaparams.append(requiredPaparams[i] + ",");
				}
			}
		}
		return missingPaparams.toString();
	}

	/**
	 * @paparam appraisalBean
	 * @return
	 * @throws Exception
	 */
	public static AppraisalModel prepareAppraisalModelFromAppraisalBean(AppraisalBean appraisalBean) throws Exception {
		AppraisalModel appraisalModel = new AppraisalModel();

		if (appraisalBean != null) {
			appraisalModel.setAppraisalId(appraisalBean.getAppraisalId());
			appraisalModel.setRelationKey(appraisalBean.getRelationKey());
			appraisalModel.setName(appraisalBean.getName());
			appraisalModel.setShortDescription(appraisalBean.getShortDescription());
			appraisalModel.setModifiedOn(appraisalBean.getModifiedOn());
			appraisalModel.setCreatedOn(appraisalBean.getCreatedOn());
			appraisalModel.setCreatedBy(appraisalBean.getCreatedBy());
			appraisalModel.setModifiedBy(appraisalBean.getModifiedBy());
			appraisalModel.setaStatus(appraisalBean.getaStatus());
		}
		return appraisalModel;
	}

	public static void populateModelsForAgentWS(LoginModel loginModel, AgentModel agentProfileModel,
			ValouxStoreModel storeModel, ValouxAgentStoreModel agentStoreModel, JSONObject jObject, String ipAddress)
			throws Exception {

		JSONObject registrationData = jObject.getJSONObject("registrationData");
		JSONObject storeData = registrationData.getJSONObject("storeData");

		String privateKey = CommonUtility.generatetToken();
		String relationKey = String.valueOf(CommonUtility.generateRandom(4));
		String authCode = CommonUtility.generatetToken();
		String forgetPasswordKey = CommonUtility.generatetToken();
		String authCodeMobile = String.valueOf(CommonUtility.generateRandom(6));
		String emailId = JSONUtility.getSafeString(registrationData, "emailId");

		String alternateEmailId = JSONUtility.getSafeString(registrationData, "alternateEmailId");
		Integer storeId = JSONUtility.getSafeInteger(storeData, "storeId");
		String storeName = JSONUtility.getSafeString(storeData, "storeName");
		String storePhone = JSONUtility.getSafeString(storeData, "storePhone");
		String zipCode4 = JSONUtility.getSafeString(registrationData, "zipCode4");
		String website = JSONUtility.getSafeString(registrationData, "website");
		String mobile = JSONUtility.getSafeString(registrationData, "mobilePhone");
		String alternateMobile = JSONUtility.getSafeString(registrationData, "alternateMobile");
		String resetQuestion = JSONUtility.getSafeString(registrationData, "passwordResetQuetion");
		String resetAnswer = JSONUtility.getSafeString(registrationData, "passwordResetAnswer");
		String password = JSONUtility.getSafeString(registrationData, "password");
		String firstName = JSONUtility.getSafeString(registrationData, "firstName");
		String middleName = JSONUtility.getSafeString(registrationData, "middleName");
		String lastName = JSONUtility.getSafeString(registrationData, "lastName");
		String facebook = JSONUtility.getSafeString(registrationData, "facebook");
		String google = JSONUtility.getSafeString(registrationData, "google");
		String instagparam = JSONUtility.getSafeString(registrationData, "instagparam");

		// agentStoreModel.setCreatedBy(UserRegistratonEnums.UsertType.Agent.getType());
		agentStoreModel.setCreatedOn(CommonUtility.getDateAndTime());
		// agentStoreModel.setModifiedBy(UserRegistratonEnums.UsertType.Agent.getType());
		agentStoreModel.setModifiedOn(CommonUtility.getDateAndTime());
		agentProfileModel.setRelationKey(relationKey);
		agentProfileModel.setEmailId(emailId);
		agentProfileModel.setAlternateEmailId(alternateEmailId);
		agentProfileModel.setMobile(mobile);
		agentProfileModel.setAlternateMobile(alternateMobile);
		agentProfileModel.setPasswordResetAnswer(resetAnswer);
		agentProfileModel.setPasswordResetQuetion(resetQuestion);
		agentProfileModel.setIp(ipAddress);
		
		String imagePath = null;
		String imageContent = JSONUtility.getSafeString(registrationData, "imageContent");
		String imageName = JSONUtility.getSafeString(registrationData, "imageName");
		if(CommonUserUtility.paparameterNullCheckStringObject(imageContent) && CommonUserUtility.paparameterNullCheckStringObject(imageName)){
			imagePath = CommonUtility.saveSignDocumentInDirectory(imageContent, imageName, "Agent_Sign");
		} else {
			agentProfileModel.setSignName(JSONUtility.getSafeString(registrationData, "signName"));
		}
		
		agentProfileModel.setSignUrl(imagePath);

		loginModel.setUserName(emailId);
		loginModel.setPassword((password));
		loginModel.setFirstName(firstName);
		loginModel.setMiddleName(middleName);
		loginModel.setLastName(lastName);
		loginModel.setUserStatus(CommonConstants.USER_STATUS_INACTIVE);
		loginModel.setAuthenticationCode(authCode);
		loginModel.setForgetPasswordKey(forgetPasswordKey);
		loginModel.setPrivateKey(privateKey);
		loginModel.setAuthCodeMobile(authCodeMobile);

		storeModel.setStoreId(storeId);
		storeModel.setName(storeName);
		storeModel.setZipcode4(zipCode4);
		storeModel.setPhone(storePhone);
		storeModel.setAlternatePhone(alternateMobile);
		storeModel.setEmail(emailId);
		storeModel.setStatus(CommonConstants.USER_STATUS_INACTIVE);
		storeModel.setWebsite(website);
		storeModel.setIpaddress(ipAddress);
		storeModel.setFacebook(facebook);
		storeModel.setGoogle(google);
		storeModel.setInstagparam(instagparam);
	}

	/**
	 * @paparam nameArray
	 * @return
	 * @author Paparam
	 */
	public static String concatStrings(String[] nameArray) throws Exception {
		
		String name = "";
		String SEPARATOR = " ";
		
		if(nameArray != null && nameArray.length > 0) {
			for (String string : nameArray) {
				if(CommonUserUtility.paparameterNullCheckStringObject(string)){
					name = name.concat(string + SEPARATOR);
				}
			}
		}
		return name.trim();
	}

	/**
	 * @paparam jsonArray
	 * @paparam sortedJsonArray
	 * @paparam jsonValues
	 * @paparam keyModifiedOn
	 * @throws Exception
	 */
	public static void getSortedJsonArrayByObjectKey(JSONArray jsonArray,
			JSONArray sortedJsonArray, List<JSONObject> jsonValues, final String keyModifiedOn) throws Exception {
		
		final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
		
		Collections.sort(jsonValues, new Comparator<JSONObject>() {
	
			public int compare(JSONObject a, JSONObject b) {
			  String valA = new String();
			  String valB = new String();
			  
			  Date date1 = new Date();
			  Date date2 = new Date();

	            try {
	                valA = (String) a.get(keyModifiedOn);
	                valB = (String) b.get(keyModifiedOn);
	                
	                date1 = DATE_TIME_FORMAT.parse(valA);
            		date2 = DATE_TIME_FORMAT.parse(valB);
	            } 
	            catch (Exception e) {
	            }
	            return -date1.compareTo(date2);
	            //if you want to change the sort order, simply use the following:
	            //return -valA.compareTo(valB);
	        }
	    });
		
		 for (int i = 0; i < jsonArray.length(); i++) {
	        sortedJsonArray.put(jsonValues.get(i));
		 }
	}

	/**
	 * @paparam jsonArray
	 * @return
	 * @throws Exception
	 */
	public static List<JSONObject> getJsonObjectListFromArray(
			JSONArray jsonArray) throws Exception{
		List<JSONObject> jsonValues = new ArrayList<JSONObject>();
	    for (int i = 0; i < jsonArray.length(); i++) {
	        jsonValues.add(jsonArray.getJSONObject(i));
	    }
		return jsonValues;
	}

	/**
	 * @paparam valouxCollectionImageBean
	 * @return
	 * @throws Exception
	 */
	public static ValouxCollectionImageModel prepareCollectionImageModelFromBean(
			ValouxCollectionImageBean valouxCollectionImageBean) throws Exception{
		
		ValouxCollectionImageModel collectionImageModel = new ValouxCollectionImageModel();
		if(valouxCollectionImageBean != null){
			collectionImageModel.setId(valouxCollectionImageBean.getId());
			collectionImageModel.setCreatedBy(valouxCollectionImageBean.getCreatedBy());
			collectionImageModel.setCreatedOn(valouxCollectionImageBean.getCreatedOn());
			collectionImageModel.setImgName(valouxCollectionImageBean.getImgName());
			if(valouxCollectionImageBean.getImgStatus() > 0){
				collectionImageModel.setImgStatus(Integer.valueOf(valouxCollectionImageBean.getImgStatus()));
			}
			collectionImageModel.setImgUrl(valouxCollectionImageBean.getImgUrl());
			collectionImageModel.setModifiedBy(valouxCollectionImageBean.getModifiedBy());
			collectionImageModel.setModifiedOn(valouxCollectionImageBean.getModifiedOn());
		}
		return collectionImageModel;
	}
}
