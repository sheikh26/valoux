/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.helper;

import org.codehaus.jettison.json.JSONObject;

import com.valoux.constant.CommonConstants;
import com.valoux.model.AgentModel;
import com.valoux.model.LoginModel;
import com.valoux.model.ValouxAgentStoreModel;
import com.valoux.model.ValouxStoreModel;
import com.valoux.service.AgentService;
import com.valoux.service.UserService;
import com.valoux.service.ValouxStoreService;
import com.valoux.util.CommonUtility;
import com.valoux.util.JSONUtility;

/**
 * This java class AgentHelper use to perform all our service populate for
 * the collections.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */

public class AgentHelper {

	/**
	 * @paparam agentService 
	 * @paparam agentProfileModel
	 * @throws Exception
	 */
	public static void updateAgentSignature(AgentService agentService, AgentModel agentProfileModel) throws Exception{
		
		if(agentProfileModel != null) {
			agentService.updateAgentSignature(agentProfileModel);
		}
	}

	public static void populateAgentDetailsInModel(UserService userService, ValouxStoreModel storeModel,
			JSONObject jObject) throws Exception{
		
		JSONObject storeData = jObject.getJSONObject("storeData");
		
		if(storeData != null){
			storeModel.setStoreId(JSONUtility.getSafeInteger(storeData, "storeId"));
			storeModel.setName(JSONUtility.getSafeString(storeData, "storeName"));
			storeModel.setgAddress(JSONUtility.getSafeString(storeData, "gAddress"));
			JSONObject storeAddress = storeData.getJSONObject("storeAddress");
			if(storeAddress != null){
				storeModel.setStreetNumber(JSONUtility.getSafeString(storeAddress, "streetNumber"));
				storeModel.setAddressLine1(JSONUtility.getSafeString(storeAddress, "addressLine1"));
				storeModel.setAddressLine2(JSONUtility.getSafeString(storeAddress, "addressLine2"));
				storeModel.setCity(JSONUtility.getSafeString(storeAddress, "city"));
				storeModel.setZipcode(JSONUtility.getSafeString(storeAddress, "zipCode"));
				storeModel.setZipcode4(JSONUtility.getSafeString(storeAddress, "zipcode4"));
				
				String state = JSONUtility.getSafeString(storeAddress, "state");
				Integer countryId = userService.getCountryId(JSONUtility.getSafeString(storeAddress, "country"));
				Integer stateId = userService.getStateIdentifier(state, countryId);
				storeModel.setStateId(stateId);
				storeModel.setCountryId(countryId);
			}
			
			storeModel.setStatus(JSONUtility.getSafeInteger(storeData, "status"));
			storeModel.setPhone(JSONUtility.getSafeString(storeData, "phone"));
			storeModel.setEmail(JSONUtility.getSafeString(storeData, "email"));
			storeModel.setAlternatePhone(JSONUtility.getSafeString(storeData, "alternatePhone"));
			storeModel.setWebsite(JSONUtility.getSafeString(storeData, "website"));
			storeModel.setFacebook(JSONUtility.getSafeString(storeData, "facebook"));
			storeModel.setGoogle(JSONUtility.getSafeString(storeData, "google"));
			storeModel.setInstagparam(JSONUtility.getSafeString(storeData, "instagparam"));
			storeModel.setTwitter(JSONUtility.getSafeString(storeData, "twitter"));
			storeModel.setModifiedOn(CommonUtility.getDateAndTime());
			storeModel.setModifiedBy(JSONUtility.getSafeString(jObject, "publicKey"));
		}
	}

	/**
	 * @paparam agentService
	 * @paparam userService
	 * @paparam storeService
	 * @paparam agentJson
	 * @paparam rKey
	 * @throws Exception
	 */
	public static void getReponseAgentDetails(AgentService agentService, UserService userService, ValouxStoreService storeService, JSONObject agentJson, String rKey) throws Exception{
		
		JSONObject responseJson = new JSONObject();
		JSONObject storeJson = new JSONObject();
		JSONObject storeAddressJson = new JSONObject();
		//String pKey = (rKey);
		String pKey = "";
		LoginModel loginModel = new LoginModel();
		AgentModel agentProfileModel = new AgentModel();
		ValouxStoreModel storeModel = new ValouxStoreModel();
		ValouxAgentStoreModel agentStoreModel = new ValouxAgentStoreModel();
		agentProfileModel = agentService.getAgentDetailByRelationKey(rKey);
		loginModel = userService.getLoginDetailByPKey(pKey);
		agentStoreModel = agentService.getStoreDataByRelationKey(rKey);
		if (agentStoreModel != null) {
			storeModel = storeService.getStoreDataByStoreId(agentStoreModel.getStoreId());
			storeJson.put("storeId", storeModel.getStoreId());
			storeJson.put("storeName", storeModel.getName());
			storeAddressJson.put("streetNo", storeModel.getStreetNumber());
			storeAddressJson.put("addressLine1", storeModel.getAddressLine1());
			storeAddressJson.put("addressLine2", storeModel.getAddressLine2());
			storeAddressJson.put("country", storeModel.getCountryName());
			storeAddressJson.put("state", storeModel.getStateName());
			storeAddressJson.put("zipCode", storeModel.getZipcode());
			storeAddressJson.put("city", storeModel.getCity());
			storeJson.put("storeAddress", storeAddressJson);
			responseJson.put("rKey", rKey);
			responseJson.put("firstName", loginModel.getFirstName());
			responseJson.put("lastName", loginModel.getLastName());
			responseJson.put("middeName", loginModel.getMiddleName());
			responseJson.put("emailId", agentProfileModel.getEmailId());
			responseJson.put("mobilePhone", agentProfileModel.getMobile());
			responseJson.put("signUrl", agentProfileModel.getSignUrl());
			responseJson.put("signName", agentProfileModel.getSignName());
			responseJson.put("storeData", storeJson);
			agentJson.put("agentData", responseJson);
		} 
		
	}

}
