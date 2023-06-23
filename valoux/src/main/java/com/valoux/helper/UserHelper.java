/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.helper;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.valoux.bean.LoginBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.LoginDao;
import com.valoux.model.AppraisalModel;
import com.valoux.model.LoginModel;
import com.valoux.model.UserModel;
import com.valoux.model.ValouxCollectionModel;
import com.valoux.model.ValouxItemModel;
import com.valoux.service.UserService;
import com.valoux.util.CommonUserUtility;
import com.valoux.util.CommonUtility;
import com.valoux.util.JSONUtility;

/**
 * This java class LoginHelper use to perform all our service populate for
 * the user.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */

public class UserHelper {

	/**
	 * Method for populate login model
	 * @paparam userModel
	 * @paparam registrationData
	 * @throws Exception
	 */
	public static void populateLoginModelFromJsonPaparam(LoginModel loginModel,
			JSONObject registrationData) throws Exception {
		
		String password = JSONUtility.getSafeString(registrationData, "password");

		loginModel.setPassword((password));
		loginModel.setFirstName(JSONUtility.getSafeString(registrationData, "firstName"));
		loginModel.setMiddleName(JSONUtility.getSafeString(registrationData, "middleName"));
		loginModel.setLastName(JSONUtility.getSafeString(registrationData, "lastName"));
		loginModel.setUserStatus(CommonConstants.USER_STATUS_INACTIVE);
		loginModel.setForgetPasswordKey(CommonUtility.generatetToken());
		loginModel.setModifiedOn(CommonUtility.getDateAndTime());

	}

	/**
	 * Method for populate user model
	 * @paparam loginModel 
	 * @paparam registrationData
	 * @return
	 * @throws Exception
	 */
	public static UserModel populateUserModelFromJsonPaparam(UserService userService, UserModel userModel, JSONObject registrationData) throws Exception {

		JSONObject personalPreference = registrationData.optJSONObject("personalPreferences");
		if(personalPreference != null){
			userModel.setJewelryTypes(JSONUtility.getSafeJSONArray(personalPreference, "JewelryTypes"));
			userModel.setJewelryDesign(JSONUtility.getSafeJSONArray(personalPreference, "JewelryDesign"));
			userModel.setJewelryStyle(JSONUtility.getSafeJSONArray(personalPreference, "JewelryStyle"));
		}

		JSONObject consumerAbou = registrationData.optJSONObject("consumerAbout");
		if(consumerAbou != null){
			userModel.setJewelryPurchases(JSONUtility.getSafeJSONArray(consumerAbou, "JewelryPurchases"));
			userModel.setJewelryInsurance(JSONUtility.getSafeJSONArray(consumerAbou, "JewelryInsurance"));
			userModel.setJewelryService(JSONUtility.getSafeJSONArray(consumerAbou, "JewelryService"));
			userModel.setJewelryDocumentation(JSONUtility.getSafeJSONArray(consumerAbou, "JewelryDocumentation"));
		}

		JSONObject jewelryComponent = registrationData.optJSONObject("jewelryComponents");
		if(jewelryComponent != null){
			userModel.setMetals(JSONUtility.getSafeJSONArray(jewelryComponent, "metals"));
			userModel.setGemstones(JSONUtility.getSafeJSONArray(jewelryComponent, "gemstones"));
			userModel.setDiamonds(JSONUtility.getSafeJSONArray(jewelryComponent, "diamonds"));
		}

		JSONObject fullAddress = registrationData.getJSONObject("userAddress");
		String state = JSONUtility.getSafeString(fullAddress, "state");
		Integer countryId = userService.getCountryId(JSONUtility.getSafeString(fullAddress, "country"));
		Integer stateId = userService.getStateIdentifier(state, countryId);
		userModel.setStreetNo(JSONUtility.getSafeString(fullAddress, "streetNo"));
		userModel.setAddressLine1(JSONUtility.getSafeString(fullAddress, "addressLine1"));
		userModel.setAddressLine2(JSONUtility.getSafeString(fullAddress, "addressLine2"));
		userModel.setCity(JSONUtility.getSafeString(fullAddress, "city"));
		userModel.setStateId(stateId);
		userModel.setCountryId(countryId);
		userModel.setZipCode(JSONUtility.getSafeString(fullAddress, "zipCode"));
		userModel.setZip4(JSONUtility.getSafeString(fullAddress, "zipCode+4"));
		
		userModel.setRelationKey(JSONUtility.getSafeString(registrationData, "userPublicKey"));
		userModel.setEmailId(JSONUtility.getSafeString(registrationData, "emailId"));
		userModel.setAlternateEmailId(JSONUtility.getSafeString(registrationData, "alternateEmailId"));
		userModel.setGlobalAddress(JSONUtility.getSafeString(registrationData, "gAddress"));
		
		userModel.setMobile(JSONUtility.getSafeString(registrationData, "mobilePhone"));
		userModel.setAlternateMobile(JSONUtility.getSafeString(registrationData, "alternateMobile"));
		String birthDay = JSONUtility.getSafeString(registrationData, "birthday");
		if (CommonUserUtility.paparameterNullCheckStringObject(birthDay)) {
			userModel.setDateOfBirth(CommonUtility.convertUIStringToDate(birthDay));
		}
		userModel.setCompany(JSONUtility.getSafeString(registrationData, "company"));
		userModel.setPasswordResetAnswer( JSONUtility.getSafeString(registrationData, "passwordResetAnswer"));
		userModel.setPasswordResetQuetion(JSONUtility.getSafeString(registrationData, "passwordResetQuetion"));
		userModel.setGender(JSONUtility.getSafeInteger(registrationData, "gender"));
		userModel.setMaritalStatus(JSONUtility.getSafeInteger(registrationData, "maritalStatus"));
		userModel.setIncomeRange(JSONUtility.getSafeInteger(registrationData, "incomeRange"));
		userModel.setFacebook(JSONUtility.getSafeString(registrationData, "facebook"));
		userModel.setGoogle(JSONUtility.getSafeString(registrationData, "google"));
		userModel.setInstagparam(JSONUtility.getSafeString(registrationData, "instagparam"));
		userModel.setTwitter(JSONUtility.getSafeString(registrationData, "twitter"));
		userModel.setSalutation(JSONUtility.getSafeString(registrationData, "salutation"));

		userModel.setInterestedIn(JSONUtility.getSafeJSONArray(registrationData, "interestedIn"));

		userModel.setModifiedOn(CommonUtility.getDateAndTime());
		userModel.setCreatedOn(CommonUtility.getDateAndTime());
		userModel.setCreatedBy(JSONUtility.getSafeString(registrationData, "userPublicKey"));
		userModel.setModifiedBy(JSONUtility.getSafeString(registrationData, "userPublicKey"));
		return userModel;
	}

	/**
	 * @paparam resData
	 * @paparam itemModels
	 * @throws Exception
	 */
	public static void addValouxItemModelList(JSONArray jsonArray, List<ValouxItemModel> itemModels) throws Exception {

		Gson gson = new Gson();
		if(itemModels != null && itemModels.size() > 0){
			for (ValouxItemModel itemModel : itemModels) {
				String jsonInString = gson.toJson(itemModel);
				jsonArray.put(new JSONObject(jsonInString));
			}
		}
	}

	/**
	 * @paparam resData
	 * @paparam collectionModels
	 * @throws Exception
	 */
	public static void addValouxCollectionModelList(JSONArray jsonArray,
			List<ValouxCollectionModel> collectionModels) throws Exception {
		Gson gson = new Gson();
		if(collectionModels != null && collectionModels.size() > 0){
			for (ValouxCollectionModel valouxCollectionModel : collectionModels) {
				String jsonInString = gson.toJson(valouxCollectionModel);
				jsonArray.put(new JSONObject(jsonInString));
			}
		}
	}

	/**
	 * @paparam resData
	 * @paparam appraisalModels
	 * @throws Exception
	 */
	public static void addValouxAppraisalModelList(JSONArray jsonArray,
			List<AppraisalModel> appraisalModels) throws Exception {
		Gson gson = new Gson();
		if(appraisalModels != null && appraisalModels.size() > 0){
			for (AppraisalModel appraisalModel : appraisalModels) {
				String jsonInString = gson.toJson(appraisalModel);
				jsonArray.put(new JSONObject(jsonInString));
			}
		}
	}
	
	/**
	 * @paparam loginDao
	 * @paparam modifiedBy
	 * @return
	 * @throws Exception
	 */
	public static String getUserNameByPublicKey(LoginDao loginDao, String modifiedBy) throws Exception{
		
		String modifiedByName = "";
		
		if(CommonUserUtility.paparameterNullCheckStringObject(modifiedBy)){
			LoginBean loginBean = loginDao.getLoginDetailByPKey((modifiedBy));
			if(loginBean != null){
				String nameArray [] = {loginBean.getFirstName(), loginBean.getLastName()};
				
				modifiedByName = CommonUserUtility.concatStrings(nameArray);
			}
		}
		return modifiedByName;
	}

	/**
	 * @paparam userService
	 * @paparam userObject
	 * @paparam rKey
	 * @throws Exception
	 */
	public static void getUserDetailsFromLogin(UserService userService,
			JSONObject userObject, String rKey) throws Exception{

		LoginModel loginModel = userService.getLoginDetailByPKey((rKey));
		if(loginModel !=null){
			userObject.put("emailId", loginModel.getUserName());
			
			String userName = "";
			String nameArray [] = {loginModel.getFirstName(), loginModel.getMiddleName(), loginModel.getLastName()};
			userName = CommonUserUtility.concatStrings(nameArray);
			userObject.put("userName", userName);
			
			UserModel userModel = userService.getConsumerDetailByRKey(rKey);
			if(userModel != null) {
				userObject.put("mobileNo", userModel.getMobile());
			}
		}
	}
}
