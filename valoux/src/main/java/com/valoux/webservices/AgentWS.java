/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.webservices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.stereotype.Component;

import com.google.gson.Gson;
import com.valoux.bean.AgentBean;
import com.valoux.bean.LoginBean;
import com.valoux.bean.MasterRoleBean;
import com.valoux.bean.UserRoleBean;
import com.valoux.bean.ValouxAgentStoreBean;
import com.valoux.bean.ValouxItemBean;
import com.valoux.bean.ValouxSharedRequestBean;
import com.valoux.bean.ValouxStoreBean;
import com.valoux.constant.CommonConstants;
import com.valoux.enums.ValouxRoleEnums;
import com.valoux.enums.ValouxRoleEnums.UserRoleType;
import com.valoux.helper.AgentHelper;
import com.valoux.helper.ItemHelper;
import com.valoux.model.AgentModel;
import com.valoux.model.LoginModel;
import com.valoux.model.UserModel;
import com.valoux.model.UserRoleModel;
import com.valoux.model.ValouxAgentStoreModel;
import com.valoux.model.ValouxItemModel;
import com.valoux.model.ValouxStoreAdvertisementModel;
import com.valoux.model.ValouxStoreModel;
import com.valoux.service.AgentService;
import com.valoux.service.ItemService;
import com.valoux.service.UserService;
import com.valoux.service.ValouxStoreService;
import com.valoux.util.CommonUserUtility;
import com.valoux.util.CommonUtility;
import com.valoux.util.JSONUtility;

/**
 * This <java>class</java> AgentWS serves request related to Agent operations.
 * 
 * @author param Sheikh
 * 
 */

@Path("agent")
@Component
public class AgentWS {

	@Autowired
	AgentService agentService;

	@Autowired
	UserService userService;

	@Autowired
	ValouxStoreService storeService;

	@Autowired
	ItemService itemService;

	private static final Logger LOGGER = Logger.getLogger(AgentWS.class);

	// private Logger logger =
	// LogManager.getLogger(TestService.class.getName());
	/**
	 * This method receives request to register an Agent and performs following
	 * operations: 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to check if user already exist. 4. If validations are not failing
	 * call service method to create Agent. 5. On successful creation of Agent
	 * send email. 6. Send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/agentRegistration.dns")
	public Response agentRegistration(JSONObject formPaparam, @Context HttpServletRequest request) throws Exception {
		LOGGER.info("Enter Method useRregistration of AgentWS");
		JSONObject json = new JSONObject();
		LoginModel loginModel = new LoginModel();
		AgentModel agentProfileModel = new AgentModel();
		AgentBean agentbean = new AgentBean();
		ValouxStoreModel storeModel = new ValouxStoreModel();
		ValouxStoreBean storeBean = new ValouxStoreBean();
		ValouxAgentStoreModel agentStoreModel = new ValouxAgentStoreModel();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject registrationData = jObject.getJSONObject("registrationData");
			String requiredPaparams[] = { "emailId", "firstName", "passwordResetQuetion", "passwordResetAnswer",
					"password" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, registrationData);

			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			JSONObject storeData = registrationData.getJSONObject("storeData");

			checkStoreAddressAndPopulateModelForAgentWS(agentProfileModel, storeModel, jObject);

			Integer storeId = JSONUtility.getSafeInteger(storeData, "storeId");
			String agentRole = JSONUtility.getSafeString(registrationData, "agentRole");
			String ipAddress = request.getRemoteAddr();

			CommonUserUtility.populateModelsForAgentWS(loginModel, agentProfileModel, storeModel, agentStoreModel,
					jObject, ipAddress);

			if (storeId != null) {
				agentProfileModel = storeService.setAgentAdressAsStoreAddress(storeId, agentProfileModel);
			}

			Boolean isValidRequest = CommonUserUtility.checkValidRequestForAgentRegistration(agentProfileModel,
					loginModel, storeModel);
			if (!isValidRequest) {
				json.put("resData", new JSONObject());
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("errorMessage", CommonConstants.REGISTRATION_INCOMPLETE_MESSAGE);
				return Response.status(200).entity(json).build();
			} // end of block checking validRequest

			boolean isEmailExist = userService.checkEmailAlreadyRegistered(agentProfileModel.getEmailId());
			if (isEmailExist) {
				json.put("resData", new JSONObject());
				json.put("errorMessage", CommonConstants.EMAIL_ID_ALREADY_EXIST);
				json.put("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			} // end of block checking if Email already exist

			agentbean = agentService.registerAgent(loginModel, agentProfileModel);
			if (agentbean != null) {
				storeModel.setCreatedBy(agentbean.getRelationKey());
				storeModel.setModifiedBy(agentbean.getRelationKey());
				storeBean = storeService.createStore(storeModel, agentbean, agentRole);
			} else {
				json.put("resData", new JSONObject());
				json.put("errorMessage", CommonConstants.REGISTRATION_ERROR_MESSAGE);
				json.put("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			agentStoreModel.setStoreId(storeBean.getStoreId());
			agentStoreModel.setRelationKey(agentProfileModel.getRelationKey());
			agentStoreModel.setCreatedBy(agentbean.getRelationKey());
			agentStoreModel.setModifiedBy(agentbean.getRelationKey());
			agentService.saveAgentStoreBean(agentStoreModel);
			MasterRoleBean masterRoleBean = agentService.getRoleData(agentRole);

			userService.createUsertypeAnduserRoleForAgent(storeBean, agentbean, masterRoleBean);
			// userService.sendMailToSuperadmin(storeBean,agentbean,agentRole);
			itemService.updateSharedRequestForNewRegistration(loginModel.getUserName(), agentbean.getRelationKey(),
					CommonConstants.AGENT);

			json.put("resData", new JSONObject());
			json.put("successMessage", CommonConstants.REGISTRATION_COMPLETE_MESSAGE);
			json.put("sCode", CommonConstants.SUCCESS);

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.SUCCESS);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block

		LOGGER.info("Exit Method useRregistration of AgentWS");
		return Response.status(200).entity(json).build();
	} // end of method agentRegistration

	private void checkStoreAddressAndPopulateModelForAgentWS(AgentModel agentProfileModel, ValouxStoreModel storeModel,
			JSONObject jObject) throws Exception {
		LOGGER.info("Enter Method checkStoreAddressAndPopulateModelForAgentWS of AgentWS");
		JSONObject registrationData = jObject.getJSONObject("registrationData");
		JSONObject storeData = registrationData.getJSONObject("storeData");
		if (storeData.has("storeAddress")) {
			JSONObject storeAddress = storeData.getJSONObject("storeAddress");
			String globalAddress = JSONUtility.getSafeString(storeAddress, "globalAddress");
			String streetNo = JSONUtility.getSafeString(storeAddress, "streetNo");
			String addressLine1 = JSONUtility.getSafeString(storeAddress, "addressLine1");
			String addressLine2 = JSONUtility.getSafeString(storeAddress, "addressLine2");
			String city = JSONUtility.getSafeString(storeAddress, "city");
			String state = JSONUtility.getSafeString(storeAddress, "state");
			String country = JSONUtility.getSafeString(storeAddress, "country");
			String zipCode = JSONUtility.getSafeString(storeAddress, "zipCode");
			agentProfileModel.setGlobalAddress(globalAddress);
			agentProfileModel.setStreetNo(streetNo);
			agentProfileModel.setAddressLine1(addressLine1);
			agentProfileModel.setAddressLine2(addressLine2);
			agentProfileModel.setCity(city);
			Integer countryId = userService.getCountryId(country);
			Integer stateId = userService.getStateIdentifier(state, countryId);
			agentProfileModel.setStateId(stateId);
			agentProfileModel.setCountryId(countryId);
			agentProfileModel.setZipCode(zipCode);
			storeModel.setStreetNumber(streetNo);
			storeModel.setAddressLine1(addressLine1);
			storeModel.setAddressLine2(addressLine2);
			storeModel.setCity(city);
			storeModel.setStateId(stateId);
			storeModel.setCountryId(countryId);
			storeModel.setZipcode(zipCode);
			storeModel.setCreatedOn(CommonUtility.getDateAndTime());
			storeModel.setModifiedOn(CommonUtility.getDateAndTime());
		}
		LOGGER.info("Exit Method checkStoreAddressAndPopulateModelForAgentWS of AgentWS");
	}

	/**
	 * This method receives request to get info of all the stores: 1. Call
	 * service method to get list of all the stores. 2. Populate response in
	 * list of <code>JSONObject</code> 2. Send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAllStoreDetail.csv")
	public Response getAllStoreDetail() throws Exception {
		LOGGER.info("Enter method getAllStoreDetail of AgentWS");
		JSONObject json = new JSONObject();
		JSONObject responseJson = new JSONObject();
		List<ValouxStoreModel> storeModelList = storeService.getAllActiveStoreData();
		JSONArray jsonArray = new JSONArray();
		try {
			if (storeModelList != null && storeModelList.size() != 0) {
				for (ValouxStoreModel storeModel : storeModelList) {
					JSONObject jObject = new JSONObject();
					jObject.put("storeId", storeModel.getStoreId());
					jObject.put("storeName", storeModel.getName());
					jObject.put("phone", storeModel.getPhone());
					JSONObject addressJson = new JSONObject();
					addressJson.put("gAddress", storeModel.getgAddress());
					addressJson.put("addressLine1", storeModel.getAddressLine1());
					addressJson.put("addressLine2", storeModel.getAddressLine2());
					// addressJson.put("streetNo",
					// storeModel.getStreetNumber());
					addressJson.put("country", storeModel.getCountryName());
					addressJson.put("city", storeModel.getCity());
					addressJson.put("state", storeModel.getStateName());
					addressJson.put("zipCode", storeModel.getZipcode());
					addressJson.put("street_number", storeModel.getStreetNumber());
					jObject.put("storeAddress", addressJson);
					jsonArray.put(jObject);
				} // end of for loop
				json.put("storeData", jsonArray);
				responseJson.put("resData", json);
				responseJson.put("sCode", CommonConstants.SUCCESS);
			} else {
				responseJson.put("errorMessage", CommonConstants.STORE_LIST_EMPTY);
				responseJson.put("sCode", CommonConstants.ERROR);
			}

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit method getAllStoreDetail of AgentWS");
		return Response.status(200).entity(responseJson).build();

	} // end of method getAllStoreDetail

	/**
	 * This method receives request to verify User OTP: 1. Check request for
	 * essential paparameters. 2. Converts <code>JSONObject</code> request to
	 * business objects. 3. Call service method to verify OTP provided in
	 * request. 4. Send back result in response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/verifyUserOTP.dns")
	public Response verifyUserOTP(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method verfyUserOTP of AgentWS");
		JSONObject json = new JSONObject();
		JSONObject resData = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jsonReqPaparam = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "authUserID", "authLoginCode" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jsonReqPaparam);

			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer authUserID = JSONUtility.getSafeInteger(jsonReqPaparam, "authUserID");
			String authLoginCode = JSONUtility.getSafeString(jsonReqPaparam, "authLoginCode");

			boolean isVerifyUserOTP = agentService.verifyUserOTP(authUserID, authLoginCode);

			if (isVerifyUserOTP) {
				resData.put("isVerfyUserOTP", true);
				json.put("resData", resData);
				json.putOpt("sCode", CommonConstants.SUCCESS);
				return Response.status(200).entity(json.toString()).build();
			} // end of if block checking if Verify User OTP
			else {
				resData.put("isVerfyUserOTP", false);
				json.put("resData", resData);
				json.putOpt("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json.toString()).build();
			} // end of else block if Verify User OTP is not match
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block

	} // end of method verifyUserOTP

	/**
	 * This method receives request to get agent information using
	 * agentPublicKey: 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Call service
	 * method to get details of agent using agentPublicKey 4. Based on data
	 * received from service send response.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAgentInfo.csv")
	public Response getAgentInfo(JSONObject formPaparam) throws Exception {
		LOGGER.info("Enter Method getAgentInfo of AgentWS");
		JSONObject json = new JSONObject();
		JSONObject responseJson = new JSONObject();
		JSONObject storeJson = new JSONObject();
		JSONObject storeAddressJson = new JSONObject();
		JSONObject agentJson = new JSONObject();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "agentPublicKey" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String rKey = JSONUtility.getSafeString(jObject, "agentPublicKey");
			String pKey = (rKey);
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
				json.put("resData", agentJson);
				json.put("sCode", CommonConstants.SUCCESS);
				storeJson.put("storeAddress", storeAddressJson);
			} else {
				json.put("resData", agentJson);
				json.put("sCode", CommonConstants.ERROR);
				json.put("errorMessage", CommonConstants.AGENT_INFO);
			} // end of if block checking if agentStoreModel is not null
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit Method getAgentInfo of AgentWS");
		return Response.status(200).entity(json).build();
	} // end of method getAgentInfo

	/**
	 * This method receives request to update Agent Info: 1. Check request for
	 * essential paparameters. 2. Converts <code>JSONObject</code> request to
	 * business objects. 3. Call service method to update Agent Info. 4. Based
	 * on result send response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updateAgentInfo.csv")
	public Response updateAgentInfo(JSONObject formPaparam) throws Exception {
		LOGGER.info("Enter Method updateAgentInfo of AgentWS");
		JSONObject json = new JSONObject();
		JSONObject agentJson = new JSONObject();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			LoginModel loginModel = new LoginModel();
			LoginBean loginBean = new LoginBean();
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject jsonToUpdate = jObject.getJSONObject("agentDataToUpdate");
			String requiredPaparams[] = { "agentPublicKey", "firstName", "lastName" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jsonToUpdate);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String rKey = JSONUtility.getSafeString(jsonToUpdate, "agentPublicKey");
			String pKey = (rKey);
			String firstName = JSONUtility.getSafeString(jsonToUpdate, "firstName");
			String lastName = JSONUtility.getSafeString(jsonToUpdate, "lastName");
			String middleName = JSONUtility.getSafeString(jsonToUpdate, "middleName");
			loginModel.setFirstName(firstName);
			loginModel.setMiddleName(middleName);
			loginModel.setLastName(lastName);
			loginModel.setPrivateKey(pKey);
			loginModel.setModifiedBy(rKey);
			loginModel.setModifiedOn(CommonUtility.getDateAndTime());
			loginBean = userService.updateAgentLogindetail(loginModel);
			if (loginBean != null) {
				
				AgentModel agentProfileModel = agentService.getAgentDetailByRelationKey(rKey);
				
				if(agentProfileModel != null) {
					String imagePath = null;
					String imageContent = JSONUtility.getSafeString(jsonToUpdate, "imageContent");
					String imageName = JSONUtility.getSafeString(jsonToUpdate, "imageName");
					if(CommonUserUtility.paparameterNullCheckStringObject(imageContent) && CommonUserUtility.paparameterNullCheckStringObject(imageName)){
						imagePath = CommonUtility.saveSignDocumentInDirectory(imageContent, imageName, "Agent_Sign");
						agentProfileModel.setSignName(null);
					} else {
						agentProfileModel.setSignName(JSONUtility.getSafeString(jsonToUpdate, "signName"));
					}
					Boolean isImageDeleted = jsonToUpdate.optBoolean("isImageDeleted");
					if(isImageDeleted != null && isImageDeleted) {
						agentService.deleteAgentSignDocument(rKey);
					}
					agentProfileModel.setSignUrl(imagePath);
					agentProfileModel.setRelationKey(rKey);
					
					AgentHelper.updateAgentSignature(agentService, agentProfileModel);
				}
				
				json.put("resData", agentJson);
				json.put("successMessage", CommonConstants.SUCCCESS_MESSSAGE);
				json.put("sCode", CommonConstants.SUCCESS);
			} else {
				json.put("resData", agentJson);
				json.put("sCode", CommonConstants.ERROR);
				json.put("errorMessage", CommonConstants.FAILURE_MESSSAGE);
			}
			
			AgentHelper.getReponseAgentDetails(agentService, userService, storeService, agentJson, rKey);

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit Method updateAgentInfo of AgentWS");
		return Response.status(200).entity(json).build();
	} // end of method update Agent Info

	/**
	 * This method receives request to get info of all the users associated with
	 * a store. 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to get information of all the users existing in a store. 4.
	 * Populate and send response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAllStoreAgentsForAdmin.dns")
	public Response getAllStoreAgentsForAdmin(JSONObject formPaparam) throws Exception {
		LOGGER.info("Enter method getAllStoreAgentsForAdmin of AgentWS");
		JSONObject json = new JSONObject();
		JSONObject responseJson = new JSONObject();

		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "agentPublicKey" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String rKey = JSONUtility.getSafeString(jObject, "agentPublicKey");
			String pKey = (rKey);
			LoginModel loginModel = new LoginModel();
			AgentModel agentProfileModel = new AgentModel();
			ValouxStoreModel storeModel = new ValouxStoreModel();
			ValouxAgentStoreModel agentStoreModel = new ValouxAgentStoreModel();
			UserRoleBean userRoleBean = new UserRoleBean();

			agentProfileModel = agentService.getAgentDetailByRelationKey(rKey);
			loginModel = userService.getLoginDetailByPKey(pKey);
			agentStoreModel = agentService.getStoreDataByRelationKey(rKey);
			userRoleBean = userService.getRole(rKey);

			JSONArray jsonStoreArray = new JSONArray();
			JSONObject agentJson = new JSONObject();
//			if (userRoleBean != null && userRoleBean.getRoleId() == ValouxRoleEnums.UserRoleType.StoreAdmin.getRoleId()) {
			if (userRoleBean != null && userRoleBean.getMasterRoleBean().getRoleId() == ValouxRoleEnums.UserRoleType.StoreAdmin.getRoleId()) {
				List<ValouxAgentStoreModel> agentModels = storeService.getStoreAgentsByStoreId(agentStoreModel
						.getStoreId());

				if (agentModels != null && agentModels.size() > 0) {
					for (ValouxAgentStoreModel agentModel : agentModels) {
						AgentModel profileModel = agentService.getAgentDetailByRelationKey(agentModel.getRelationKey());
						LoginModel logModel = userService.getLoginDetailByPKey((agentModel
								.getRelationKey()));
						UserRoleBean roleBean = userService.getRole(agentModel.getRelationKey());

						JSONObject agent = new JSONObject();
						agent.put("agentKey", agentModel.getRelationKey());

						if (logModel != null) {
							agent.put("firstName", logModel.getFirstName());
							agent.put("lastName", logModel.getLastName());
							agent.put("middeName", logModel.getMiddleName());
						}

						if (profileModel != null) {
							agent.put("emailId", profileModel.getEmailId());
							agent.put("mobilePhone", profileModel.getMobile());
						}

						if (roleBean != null) {
//							agent.put("roleId", roleBean.getRoleId());
							agent.put("roleId", roleBean.getMasterRoleBean().getRoleId());
//							agent.put("roleName", CommonUserUtility.getRoleName(roleBean.getRoleId()));
							agent.put("roleName", CommonUserUtility.getRoleName(roleBean.getMasterRoleBean().getRoleId()));
						}

						jsonStoreArray.put(agent);
					} // end of for loop
				} // end of if block if agentModels is not null
			} // end of if block if role is match as store admin
			responseJson.put("storeAgents", jsonStoreArray);
			if (agentStoreModel != null) {
				storeModel = storeService.getStoreDataByStoreId(agentStoreModel.getStoreId());

				if (storeModel != null) {
					JSONObject agentStoreJson = new JSONObject();
					agentStoreJson.put("storeId", storeModel.getStoreId());
					agentStoreJson.put("storeName", storeModel.getName());
					responseJson.put("agentStoreData", agentStoreJson);
				}
			} // end of if block if agentStoreModel is not null

			if (loginModel != null) {
				responseJson.put("firstName", loginModel.getFirstName());
				responseJson.put("lastName", loginModel.getLastName());
				responseJson.put("middeName", loginModel.getMiddleName());
			} // end of if block if loginModel is not null

			if (agentProfileModel != null) {
				responseJson.put("emailId", agentProfileModel.getEmailId());
				responseJson.put("mobilePhone", agentProfileModel.getMobile());
			} // end of if block if agentProfileModel is not null

			agentJson.put("agentsData", responseJson);
			json.put("resData", agentJson);
			json.put("sCode", CommonConstants.SUCCESS);

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit Method getAllStoreAgentsForAdmin of AgentWS");
		return Response.status(200).entity(json).build();
	} // end of method get All Store Agents For Admin

	/**
	 * This method receives request to get info of all the stores: 1. Call
	 * service method to get list of all the stores. 2. Populate response in
	 * list of <code>JSONObject</code> 2. Send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAllStoreDetailWithUserCount.csv")
	public Response getAllStoreDetailWithUserCount() throws Exception {
		LOGGER.info("Enter method getAllStoreDetailWithUserCount of AgentWS");
		JSONObject json = new JSONObject();
		JSONObject responseJson = new JSONObject();
		List<ValouxStoreModel> storeModelList = storeService.getAllStoreData();
		JSONArray jsonArray = new JSONArray();
		try {
			if (storeModelList != null && storeModelList.size() != 0) {
				for (ValouxStoreModel storeModel : storeModelList) {
					JSONObject jObject = new JSONObject();
					jObject.put("storeId", storeModel.getStoreId());
					jObject.put("storeName", storeModel.getName());
					jObject.put("phone", storeModel.getPhone());
					if (storeModel.getStatus().equals(1)) {
						jObject.put("status", "Active");
					}
					if (storeModel.getStatus().equals(2)) {
						jObject.put("status", "Inactive");
					}
					if (storeModel.getStatus().equals(3)) {
						jObject.put("status", "Invited");
					}
					Integer count = agentService.getNumberOfUserAssociatedWithStore(storeModel.getStoreId());
					jObject.put("noOfUserAssociated", count);
					jObject.put("noOfItemAssociated",
							itemService.getNumberOfItemAssociatedWithStore(storeModel.getStoreId()));
					JSONObject addressJson = new JSONObject();
					addressJson.put("gAddress", storeModel.getgAddress());
					addressJson.put("addressLine1", storeModel.getAddressLine1());
					addressJson.put("addressLine2", storeModel.getAddressLine2());
					// addressJson.put("streetNo",
					// storeModel.getStreetNumber());
					addressJson.put("country", storeModel.getCountryName());
					addressJson.put("city", storeModel.getCity());
					addressJson.put("state", storeModel.getStateName());
					addressJson.put("zipCode", storeModel.getZipcode());
					addressJson.put("street_number", storeModel.getStreetNumber());
					jObject.put("storeAddress", addressJson);
					List<ValouxStoreAdvertisementModel> storeAdvModelList = agentService.getStoreAdvertisementListByStoreId(storeModel.getStoreId());
					if(storeAdvModelList!=null && storeAdvModelList.size()>0){
						JSONArray jAdvImage = new JSONArray();
						for(ValouxStoreAdvertisementModel storeAdvModel : storeAdvModelList){
							JSONObject jAdvObject = new JSONObject();
							jAdvObject.put("imageUrl", storeAdvModel.getUrl());
							jAdvObject.put("imagePath", storeAdvModel.getImgPath());
							jAdvObject.put("storeAdvertisementId", storeAdvModel.getStoreAdvertisementId());
							jAdvObject.put("tittle", storeAdvModel.getTitle());
							jAdvObject.put("status", storeAdvModel.getStatus());
							jAdvImage.put(jAdvObject);
						}
						jObject.put("advertisementImageDetail", jAdvImage);
					}
					jsonArray.put(jObject);
				} // end of for loop
			}
			json.put("storeData", jsonArray);
			responseJson.put("resData", json);
			responseJson.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit method getAllStoreDetailWithUserCount of AgentWS");
		return Response.status(200).entity(responseJson).build();

	} // end of method getAllStoreDetail
	
	
	/**
	 * This method receives request to get info of the store: 1. Call
	 * service method to get list of all the stores. 2. Populate response in
	 * list of <code>JSONObject</code> 2. Send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getStoreDetailWithUserCount.csv")
	public Response getStoreDetailWithUserCount(JSONObject formPaparam) throws Exception {
		LOGGER.info("Enter method getStoreDetailWithUserCount of AgentWS");
		JSONObject json = new JSONObject();
		JSONObject responseJson = new JSONObject();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "storeId"};
			
			String requiredPaparams1[] = { "storeAdvertisementId"};
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			
			String missingPaparams1 = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams1, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams) && CommonUserUtility.paparameterNullCheckStringObject(missingPaparams1)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer storeId = JSONUtility.getSafeInteger(jObject, "storeId");
			Integer storeAdvertisementId = JSONUtility.getSafeInteger(jObject, "storeAdvertisementId");
			ValouxStoreModel storeModel = new ValouxStoreModel();
			ValouxStoreAdvertisementModel storeAdvModel = new ValouxStoreAdvertisementModel();
			if(storeId!=null){
			storeModel	 = storeService.getStoreDataByStoreId(storeId);
			}else{
				storeAdvModel = agentService.getStoreAdvertisementListByStoreAdvertisementId(storeAdvertisementId);
				storeModel = storeService.getStoreDataByStoreId(storeAdvModel.getStoreId());
			}
				JSONObject jStoreObject = new JSONObject();
				if(storeModel!=null){
					jStoreObject.put("storeId", storeModel.getStoreId());
					jStoreObject.put("storeName", storeModel.getName());
					jStoreObject.put("phone", storeModel.getPhone());
					if (storeModel.getStatus().equals(1)) {
						jStoreObject.put("status", "Active");
					}
					if (storeModel.getStatus().equals(2)) {
						jStoreObject.put("status", "Inactive");
					}
					if (storeModel.getStatus().equals(3)) {
						jStoreObject.put("status", "Invited");
					}
					Integer count = agentService.getNumberOfUserAssociatedWithStore(storeModel.getStoreId());
					jStoreObject.put("noOfUserAssociated", count);
					jStoreObject.put("noOfItemAssociated",
							itemService.getNumberOfItemAssociatedWithStore(storeModel.getStoreId()));
					JSONObject addressJson = new JSONObject();
					addressJson.put("gAddress", storeModel.getgAddress());
					addressJson.put("addressLine1", storeModel.getAddressLine1());
					addressJson.put("addressLine2", storeModel.getAddressLine2());
					// addressJson.put("streetNo",
					// storeModel.getStreetNumber());
					addressJson.put("country", storeModel.getCountryName());
					addressJson.put("city", storeModel.getCity());
					addressJson.put("state", storeModel.getStateName());
					addressJson.put("zipCode", storeModel.getZipcode());
					addressJson.put("street_number", storeModel.getStreetNumber());
					jStoreObject.put("storeAddress", addressJson);
					if(storeAdvertisementId==null){
					List<ValouxStoreAdvertisementModel> storeAdvModelList = agentService.getStoreAdvertisementListByStoreId(storeModel.getStoreId());
					if(storeAdvModelList!=null && storeAdvModelList.size()>0){
						JSONArray jAdvImage = new JSONArray();
						for(ValouxStoreAdvertisementModel storeAdvModelNew : storeAdvModelList){
							JSONObject jAdvObject = new JSONObject();
							jAdvObject.put("imageUrl", storeAdvModelNew.getUrl());
							jAdvObject.put("imagePath", storeAdvModelNew.getImgPath());
							jAdvObject.put("storeAdvertisementId", storeAdvModelNew.getStoreAdvertisementId());
							jAdvObject.put("tittle", storeAdvModelNew.getTitle());
							jAdvObject.put("status", storeAdvModelNew.getStatus());
							jAdvImage.put(jAdvObject);
						}
						jStoreObject.put("advertisementImageDetail", jAdvImage);
					}
					}else{
						JSONObject jAdvObject = new JSONObject();
						JSONArray jAdvImage = new JSONArray();
						jAdvObject.put("imageUrl", storeAdvModel.getUrl());
						jAdvObject.put("imagePath", storeAdvModel.getImgPath());
						jAdvObject.put("storeAdvertisementId", storeAdvModel.getStoreAdvertisementId());
						jAdvObject.put("tittle", storeAdvModel.getTitle());
						jAdvObject.put("status", storeAdvModel.getStatus());
						jAdvImage.put(jAdvObject);
						jStoreObject.put("advertisementImageDetail", jAdvImage);
					}
					
				}
			json.put("storeData", jStoreObject);
			responseJson.put("resData", json);
			responseJson.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit method getStoreDetailWithUserCount of AgentWS");
		return Response.status(200).entity(responseJson).build();

	} // end of method getAllStoreDetail


	/**
	 * This method receives request to get info of all the users associated with
	 * a store. 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to get information of all the users existing in a store. 4.
	 * Populate and send response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/activateOrDeactivateStore.csv")
	public Response activateOrDeactivateStore(JSONObject formPaparam) throws Exception {
		LOGGER.info("Enter method activateOrDeactivateStore of AgentWS");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "storeId", "action" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer storeId = JSONUtility.getSafeInteger(jObject, "storeId");
			String action = JSONUtility.getSafeString(jObject, "action");
//			if (storeId.equals(null) || action.equals(null)) {
			if (storeId == null || action == null) {
				json.put("resData", new JSONObject());
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("errorMessage", CommonConstants.REGISTRATION_INCOMPLETE_MESSAGE);
				return Response.status(200).entity(json).build();
			}
			agentService.activateOrDeactiveStore(storeId, action);
			json.put("resData", new JSONObject());
			json.put("sCode", CommonConstants.SUCCESS);
			if (action.equals("activate")) {
				json.put("successMessage", CommonConstants.STORE_ACTIVATED);
			}
			if (action.equals("inactivate")) {
				json.put("errorMessage", CommonConstants.STORE_INACTIVATED);
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit method activateOrDeactivateStore of AgentWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to get agent information using
	 * agentPublicKey: 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Call service
	 * method to get details of agent using agentPublicKey 4. Based on data
	 * received from service send response.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAgentInfoAssociatedWithStore.csv")
	public Response getAgentInfoAssociatedWithStore(JSONObject formPaparam) throws Exception {
		LOGGER.info("Enter Method getAgentInfo of AgentWS");
		JSONObject json = new JSONObject();
		JSONObject agentJson = new JSONObject();
		JSONArray agentArray = new JSONArray();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "agentPublicKey" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer storeId = JSONUtility.getSafeInteger(jObject, "storeId");
			
			ValouxAgentStoreModel agentStoreModel = null;
			
			if(storeId == null){
				String agentKey = JSONUtility.getSafeString(jObject, "agentPublicKey");
				
				agentStoreModel = agentService.getStoreDataByRelationKey(agentKey);
				storeId = agentStoreModel.getStoreId();
			}
			
//			if(storeId != null && !storeId.equals("")){
			if(storeId != null){
				LoginModel loginModel = new LoginModel();
				AgentModel agentProfileModel = new AgentModel();
				List<ValouxAgentStoreBean> agentStoreBeanList = agentService.getAgentDetailAssociatedWithStore(storeId);

				if (agentStoreBeanList != null && agentStoreBeanList.size() > 0) {
					for (ValouxAgentStoreBean agentStoreBean : agentStoreBeanList) {
						JSONObject responseJson = new JSONObject();
						String rKey = agentStoreBean.getRelationKey();
						String pKey = (rKey);
						agentProfileModel = agentService.getAgentDetailByRelationKey(rKey);
						loginModel = userService.getLoginDetailByPKey(pKey);
						int sharedRequestCount=0;
						int shareditemCount=0;
						int sharedCollectionCount=0;
						int sharedAppraisalCount=0;
						List<ValouxSharedRequestBean> sharedRequestBeanList = itemService.getListOfRequestedAndAcceptedSharedItemsToAgent(rKey);
						
						if(sharedRequestBeanList != null && sharedRequestBeanList.size() > 0){
							
						for(ValouxSharedRequestBean sharedRequestBean : sharedRequestBeanList){
							if(sharedRequestBean.getSharedItemType().equals(CommonConstants.SHARED_TYPE_ITEM)){
								shareditemCount++;
							}
							else if(sharedRequestBean.getSharedItemType().equals(CommonConstants.SHARED_TYPE_COLLECTION)){
								sharedCollectionCount++;
							}
							else if(sharedRequestBean.getSharedItemType().equals(CommonConstants.SHARED_TYPE_APPRAISAL)){
								sharedAppraisalCount++;
							}
						}
						
							sharedRequestCount = sharedRequestBeanList.size();
						}
						
						responseJson.put("rKey", rKey);
						responseJson.put("firstName", loginModel.getFirstName());
						responseJson.put("lastName", loginModel.getLastName());
						responseJson.put("middeName", loginModel.getMiddleName());
						responseJson.put("sharedRequestCount", sharedRequestCount);
						responseJson.put("shareditemCount", shareditemCount);
						responseJson.put("sharedCollectionCount", sharedCollectionCount);
						responseJson.put("sharedAppraisalCount", sharedAppraisalCount);
						responseJson.put("userStatus", loginModel.getUserStatus());
						if(agentProfileModel != null){
							responseJson.put("emailId", agentProfileModel.getEmailId());
							responseJson.put("mobilePhone", agentProfileModel.getMobile());
							
							JSONObject addressJson = new JSONObject();
							addressJson.put("address", agentProfileModel.getGlobalAddress());
							addressJson.put("addressLine1", agentProfileModel.getAddressLine1());
							addressJson.put("addressLine2", agentProfileModel.getAddressLine2());
							addressJson.put("streetNo", agentProfileModel.getStreetNo());
							addressJson.put("country", agentProfileModel.getCountryName());
							addressJson.put("city", agentProfileModel.getCity());
							addressJson.put("state", agentProfileModel.getStateName());
							addressJson.put("zipCode", agentProfileModel.getZipCode());
							responseJson.put("userAddress", addressJson);
						} else {
							responseJson.put("userAddress", new JSONObject());
						}
						agentArray.put(responseJson);
					}
					agentJson.put("agentData", agentArray);
					json.put("resData", agentJson);
					json.put("sCode", CommonConstants.SUCCESS);
				} else {
					json.put("resData", agentJson);
					json.put("sCode", CommonConstants.ERROR);
					json.put("errorMessage", CommonConstants.AGENT_INFO);
				} // end of if block checking if agentStoreModel is not null
			} else {
				json.put("resData", agentJson);
				json.put("sCode", CommonConstants.ERROR);
				json.put("errorMessage", CommonConstants.AGENT_INFO);
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit Method getAgentInfo of AgentWS");
		return Response.status(200).entity(json).build();
	} // end of method getAgentInfo
	
	
	/**
	 * This method receives request to register an Agent and performs following
	 * operations: 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to check if user already exist. 4. If validations are not failing
	 * call service method to create Agent. 5. On successful creation of Agent
	 * send email. 6. Send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/saveOrUpdateStoreAdvInfo.csv")
	public Response saveOrUpdateStoreAdvInfo(JSONObject formPaparam, @Context HttpServletRequest request) throws Exception {
		LOGGER.info("Enter Method saveOrUpdateStoreAdvInfo of AgentWS");
		JSONObject json = new JSONObject();
		ValouxStoreAdvertisementModel advModel = new ValouxStoreAdvertisementModel();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "userPublicKey", "storeId", "advImages"};
			String requiredPaparams1[] = { "storeAdvertisementId"};
			String missingPaparams = null;
			String action = JSONUtility.getSafeString(jObject, "action");
			if(action==null){
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + "action");
				return Response.status(200).entity(json.toString()).build();
			}
			if(action.equalsIgnoreCase(CommonConstants.ADD)){
				missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
				
			}
			if(action.equals(CommonConstants.UPDATE)){
				missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams1, jObject);
			}
			

			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String successMessage = null;
			Integer storeAdvertisementId = JSONUtility.getSafeInteger(jObject, "storeAdvertisementId");
			Integer storeId = JSONUtility.getSafeInteger(jObject, "storeId");
			JSONArray imagesjArray = JSONUtility.getSafeJSONArray(jObject, "advImages");
			String userPublicKey = JSONUtility.getSafeString(jObject, "userPublicKey");
			advModel.setCreatedBy(userPublicKey);
			advModel.setCreatedOn(CommonUtility.getDateAndTime());
			advModel.setModifiedBy(userPublicKey);
			advModel.setModifiedOn(CommonUtility.getDateAndTime());
			advModel.setStoreId(storeId);
			if(action.equalsIgnoreCase(CommonConstants.ADD)){
			advModel.setStatus(1);//active status
			agentService.saveStoreAdv(imagesjArray, advModel);
			successMessage = CommonConstants.ADV_IMAGE_ADDED;
			}
			if(action.equalsIgnoreCase(CommonConstants.UPDATE)){
				agentService.updateStoreAdv(imagesjArray, storeAdvertisementId);
				successMessage = CommonConstants.ADV_IMAGE_UPDATED;
			}
			json.put("resData", new JSONObject());
			json.put("successMessage", successMessage);
			json.put("sCode", CommonConstants.SUCCESS);

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.SUCCESS);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			// LOGGER.;
			return Response.status(200).entity(json).build();
		} // end of try catch block

		LOGGER.info("Exit Method saveOrUpdateStoreAdvInfo of AgentWS");
		return Response.status(200).entity(json).build();
	} // end of method agentRegistration

	/**
	 * This method receives request to get info of all the users associated with
	 * a store. 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to get information of all the users existing in a store. 4.
	 * Populate and send response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/activeInactiveAgent.csv")
	public Response activeInactiveAgent(JSONObject formPaparam) throws Exception {
		LOGGER.info("Enter method activeInactiveAgent of AgentWS");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "status", "userPublicKey" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer status = JSONUtility.getSafeInteger(jObject, "status");
			String userPublicKey = JSONUtility.getSafeString(jObject, "userPublicKey");
			String pKey = (userPublicKey);
			LoginBean loginBean= agentService.activateOrDeactiveAgent(status, pKey);
			if(loginBean == null){
				json.put("resData", "");
				json.put("errorMessage", CommonConstants.AGENT_ERROR_MESSAGE);
				json.put("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}else{
				json.put("resData", new JSONObject());
				json.put("sCode", CommonConstants.SUCCESS);
				if (status == 1) {
					json.put("successMessage", CommonConstants.AGENT_ACTIVATED);
				}
				if (status == 2) {
					json.put("successMessage", CommonConstants.AGENT_INACTIVATED);
				}	
			}
			
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit method activeInactiveAgent of AgentWS");
		return Response.status(200).entity(json).build();
	}
	
	/**
	 * This method receives request to get list of consumer who shared item with
	 * agent: 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to get list of shared request. 4. On success send back response
	 * for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getUserListSharedToAgent.csv")
	public Response getUserListSharedToAgent(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method getConsumerListWhoSharedItemWithAgent of ItemWS");
		JSONObject json = new JSONObject();

		JSONArray jArray = new JSONArray();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "publicKey" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			String sharedTo = JSONUtility.getSafeString(jObject, "publicKey");
			List<ValouxSharedRequestBean> sharedRequestBeanList = itemService.getUserListSharedToAgent(sharedTo);
			if (sharedRequestBeanList != null && sharedRequestBeanList.size() > 0) {
				for (ValouxSharedRequestBean sharedRequestBean : sharedRequestBeanList) {
					if (sharedRequestBean.getSharedBy() != null) {
						JSONObject addressJson = new JSONObject();
						JSONObject responseJson = new JSONObject();
						String rKey = sharedRequestBean.getSharedBy();
						String pKey = (sharedRequestBean.getSharedBy());
						LoginModel loginModel = userService.getLoginDetailByPKey(pKey);
						UserModel userModel = userService.getConsumerDetailByRKey(rKey);
						int sharedItemCount = 0; 
						int sharedCollectionCount = 0; 
						int sharedAppraisalCount = 0; 
						
						if(loginModel != null){
							List<ValouxSharedRequestBean> requestBeanList = itemService.getUserListSharedToAgentSharedByUser(sharedRequestBean.getSharedBy(), sharedTo);
							for(ValouxSharedRequestBean sharedRequest : requestBeanList){
								if(sharedRequest.getSharedItemType().equals(CommonConstants.SHARED_TYPE_ITEM)){
									sharedItemCount++;
								}
								else if(sharedRequest.getSharedItemType().equals(CommonConstants.SHARED_TYPE_COLLECTION)){
									sharedCollectionCount++;
								}
								else if(sharedRequest.getSharedItemType().equals(CommonConstants.SHARED_TYPE_APPRAISAL)){
									sharedAppraisalCount++;
								}
							}
							
							responseJson.put("sharedItemCount", sharedItemCount);
							responseJson.put("sharedCollectionCount", sharedCollectionCount);
							responseJson.put("sharedAppraisalCount", sharedAppraisalCount);
							responseJson.put("consumerPublicKey", rKey);
							responseJson.put("firstName", loginModel.getFirstName());
							responseJson.put("lastName", loginModel.getLastName());
							responseJson.put("middleName", loginModel.getMiddleName());
							responseJson.put("emailId", loginModel.getUserName());
							responseJson.put("userStatus", loginModel.getUserStatus());
							
							if (userModel != null) {
								responseJson.put("mobilePhone", userModel.getMobile());
								responseJson.put("imageUrl", userModel.getImageURL());
								addressJson.put("address", userModel.getGlobalAddress());
								addressJson.put("addressLine1", userModel.getAddressLine1());
								addressJson.put("addressLine2", userModel.getAddressLine2());
								addressJson.put("streetNo", userModel.getStreetNo());
								addressJson.put("country", userModel.getCountryName());
								addressJson.put("city", userModel.getCity());
								addressJson.put("state", userModel.getStateName());
								addressJson.put("zipCode", userModel.getZipCode());
								responseJson.put("userAddress", addressJson);
							}
							jArray.put(responseJson);
						}
					}
				}
			}
			json.put("consumerList", jArray);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit Method getConsumerListWhoSharedItemWithAgent of ItemWS");
		return Response.status(200).entity(json).build();
	}
	
	/**
	 * This method receives request to get list of item and agent associated with consumer
	 * : 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to get list of shared request. 4. On success send back response
	 * for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getStoreAndAgentAssociatedWithConsumer.csv")
	public Response getStoreAndAgentAssociatedWithConsumer(JSONObject formpaparam, @Context HttpServletRequest request) throws Exception {
		LOGGER.info("Enter Method getStoreAndAgentAssociatedWithConsumer of ItemWS");
		JSONObject json = new JSONObject();

		JSONArray jArrayAgent = new JSONArray();
		JSONArray jArrayStore = new JSONArray();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "userPublicKey" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			String userPublicKey = JSONUtility.getSafeString(jObject, "userPublicKey");
			Map<Integer, Integer> storeIdMap = new HashMap<Integer, Integer>();
			List<ValouxItemModel> itemModelList = itemService.getAllItemList(userPublicKey,null,null,null,null);
			if(itemModelList!=null && itemModelList.size()>0){
				for(ValouxItemModel itemModel:itemModelList){
					if(itemModel.getStoreId()!=null){
						ValouxStoreModel storeModel = storeService.getStoreDataByStoreId(itemModel.getStoreId());
						JSONObject storeJson = new JSONObject();
						JSONObject storeAddressJson = new JSONObject();
						if(storeModel!=null && storeModel.getStatus().equals(CommonConstants.STATUS_ACTIVE)){
							storeJson.put("storeId", storeModel.getStoreId());
							storeJson.put("storeName", storeModel.getName());
							storeJson.put("phoneNo", storeModel.getPhone());
							storeAddressJson.put("streetNo", storeModel.getStreetNumber());
							storeAddressJson.put("addressLine1", storeModel.getAddressLine1());
							storeAddressJson.put("addressLine2", storeModel.getAddressLine2());
							storeAddressJson.put("country", storeModel.getCountryName());
							storeAddressJson.put("state", storeModel.getStateName());
							storeAddressJson.put("zipCode", storeModel.getZipcode());
							storeAddressJson.put("city", storeModel.getCity());
							storeJson.put("storeAddress", storeAddressJson);
							List<ValouxItemBean> itemBeanList = itemService.getItemDetailByStoreIdAndRKey(storeModel.getStoreId(), userPublicKey);
							if(itemBeanList!=null && itemBeanList.size()>0){
								JSONArray storeItemArray = new JSONArray();
								
								for(ValouxItemBean itemBean : itemBeanList){
									JSONObject storeItemJson = new JSONObject();
									storeItemJson.put("itemId", itemBean.getItemId());
									storeItemJson.put("name", itemBean.getName());
									storeItemArray.put(storeItemJson);
								}
								storeJson.put("itemPurchased", storeItemArray);
							}
							if(storeIdMap!=null && !storeIdMap.containsKey(storeModel.getStoreId())){
								List<ValouxStoreAdvertisementModel> storeAdvModelList = agentService.getStoreAdvertisementListByStoreId(storeModel.getStoreId());
								if(storeAdvModelList!=null && storeAdvModelList.size()>0){
									JSONArray jAdvImage = new JSONArray();
									for(ValouxStoreAdvertisementModel storeAdvModel : storeAdvModelList){
										JSONObject jAdvObject = new JSONObject();
										jAdvObject.put("imageUrl", storeAdvModel.getUrl());
										jAdvObject.put("imagePath", storeAdvModel.getImgPath());
										jAdvObject.put("storeAdvertisementId", storeAdvModel.getStoreAdvertisementId());
										jAdvObject.put("tittle", storeAdvModel.getTitle());
										jAdvObject.put("status", storeAdvModel.getStatus());
										jAdvImage.put(jAdvObject);
									}
									storeJson.put("advertisementImageDetail", jAdvImage);
								}
							jArrayStore.put(storeJson);
							storeIdMap.put(storeModel.getStoreId(), storeModel.getStoreId());
							}
						}
					}
				}
			}
			List<ValouxSharedRequestBean> sharedRequestBeanList = itemService.getListOfSharedContacts(userPublicKey, null,null,null,null,null);
			if (sharedRequestBeanList != null && sharedRequestBeanList.size() > 0) {
				for (ValouxSharedRequestBean sharedRequestBean : sharedRequestBeanList) {
					if (sharedRequestBean.getSharedTo() != null) {
						AgentModel agentProfileModel = new AgentModel();
						JSONObject storeJson = new JSONObject();
						JSONObject storeAddressJson = new JSONObject();
						agentProfileModel = agentService.getAgentDetailByRelationKey(sharedRequestBean.getSharedTo());
						if(agentProfileModel!=null){
						LoginModel loginModel = userService.getLoginDetailByPKey((sharedRequestBean.getSharedTo()));
						ValouxAgentStoreModel agentStoreModel = agentService.getStoreDataByRelationKey(sharedRequestBean.getSharedTo());
						if(agentStoreModel!=null){
							ValouxStoreModel storeModel = storeService.getStoreDataByStoreId(agentStoreModel.getStoreId());
							if(storeModel.getStatus().equals(CommonConstants.STATUS_ACTIVE)){
							storeJson.put("storeId", storeModel.getStoreId());
							storeJson.put("storeName", storeModel.getName());
							storeJson.put("phoneNo", storeModel.getPhone());
							storeAddressJson.put("streetNo", storeModel.getStreetNumber());
							storeAddressJson.put("addressLine1", storeModel.getAddressLine1());
							storeAddressJson.put("addressLine2", storeModel.getAddressLine2());
							storeAddressJson.put("country", storeModel.getCountryName());
							storeAddressJson.put("state", storeModel.getStateName());
							storeAddressJson.put("zipCode", storeModel.getZipcode());
							storeAddressJson.put("city", storeModel.getCity());
							storeJson.put("storeAddress", storeAddressJson);
							if(storeIdMap!=null && !storeIdMap.containsKey(storeModel.getStoreId())){
								List<ValouxStoreAdvertisementModel> storeAdvModelList = agentService.getStoreAdvertisementListByStoreId(storeModel.getStoreId());
								if(storeAdvModelList!=null && storeAdvModelList.size()>0){
									JSONArray jAdvImage = new JSONArray();
									for(ValouxStoreAdvertisementModel storeAdvModel : storeAdvModelList){
										JSONObject jAdvObject = new JSONObject();
										jAdvObject.put("imageUrl", storeAdvModel.getUrl());
										jAdvObject.put("imagePath", storeAdvModel.getImgPath());
										jAdvObject.put("storeAdvertisementId", storeAdvModel.getStoreAdvertisementId());
										jAdvObject.put("tittle", storeAdvModel.getTitle());
										jAdvObject.put("status", storeAdvModel.getStatus());
										jAdvImage.put(jAdvObject);
									}
									storeJson.put("advertisementImageDetail", jAdvImage);
								}
							jArrayStore.put(storeJson);
							storeIdMap.put(storeModel.getStoreId(), storeModel.getStoreId());
							}
							}
						}
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("rKey", sharedRequestBean.getSharedTo());
						jsonObject.put("firstName", loginModel.getFirstName());
						jsonObject.put("lastName", loginModel.getLastName());
						jsonObject.put("middeName", loginModel.getMiddleName());
						jsonObject.put("emailId", agentProfileModel.getEmailId());
						jsonObject.put("mobilePhone", agentProfileModel.getMobile());
						jsonObject.put("storeData", storeJson);
							
						jArrayAgent.put(jsonObject);
						}
					}
				}
			}
	
			json.put("storeList", jArrayStore);
			json.put("agentList", jArrayAgent);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit Method getStoreAndAgentAssociatedWithConsumer of ItemWS");
		return Response.status(200).entity(json).build();
	}
	

	/**
	 * This method receives request to delete advertisement image
	 * : 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deleteAdvImage.csv")
	public Response deleteAdvImage(JSONObject formPaparam, @Context HttpServletRequest request) throws Exception {
		LOGGER.info("Enter Method deleteAdvImage of AgentWS");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			Integer storeId = JSONUtility.getSafeInteger(jObject, "storeId");
			Integer storeAdvertisementId = JSONUtility.getSafeInteger(jObject, "storeAdvertisementId");
			agentService.deleteStoreAdvertisement(storeId, storeAdvertisementId);
			json.put("resData", new JSONObject());
			json.put("successMessage", CommonConstants.ADV_IMAGE_DELETED);
			json.put("sCode", CommonConstants.SUCCESS);

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.SUCCESS);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			// LOGGER.;
			return Response.status(200).entity(json).build();
		} // end of try catch block

		LOGGER.info("Exit Method deleteAdvImage of AgentWS");
		return Response.status(200).entity(json).build();
	} // end of method agentRegistration
	
	/**
	 * This method receives request to get info of all the users associated with
	 * a store. 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to get information of all the users existing in a store. 4.
	 * Populate and send response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/activeInactiveStoreAdv.csv")
	public Response activeInactiveStoreAdv(JSONObject formPaparam, @Context HttpServletRequest request) throws Exception {
		LOGGER.info("Enter method activeInactiveStoreAdv of AgentWS");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "status"};
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer storeAdvertisementId = JSONUtility.getSafeInteger(jObject, "storeAdvertisementId");
			Integer storeId = JSONUtility.getSafeInteger(jObject, "storeId");
			if(storeAdvertisementId==null && storeId==null){
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer status = JSONUtility.getSafeInteger(jObject, "status");
			agentService.activeInactiveStoreAdv(storeId, storeAdvertisementId, status);
			if (status == 1) {
					json.put("successMessage", CommonConstants.STORE_ADV_ACTIVATED);
				}
			if (status == 2) {
					json.put("successMessage", CommonConstants.STORE_ADV_INACTIVATED);
				}	
			
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit method activeInactiveStoreAdv of AgentWS");
		return Response.status(200).entity(json).build();
	}
	
	
	/**
	 * This method get all advertisment data
	 * . 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to get All data. 4.
	 * Populate and send response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAllStoreAdvDetail.csv")
	public Response getAllStoreAdvDetail() throws Exception {
		LOGGER.info("Enter method getAllStoreAdvDetail of AgentWS");
		JSONObject json = new JSONObject();
		try {
			List<ValouxStoreModel> storeModelList = agentService.getStoreHavinAdvertisement();
			JSONArray jsonArray = new JSONArray();
			if (storeModelList != null && storeModelList.size() != 0) {
				for (ValouxStoreModel storeModel : storeModelList) {
					JSONObject jObject = new JSONObject();
					jObject.put("storeId", storeModel.getStoreId());
					jObject.put("storeName", storeModel.getName());
					List<ValouxStoreAdvertisementModel> storeAdvModelList = agentService.getStoreAdvertisementListByStoreId(storeModel.getStoreId());
					if(storeAdvModelList!=null && storeAdvModelList.size()>0){
						JSONArray jAdvImage = new JSONArray();
						for(ValouxStoreAdvertisementModel storeAdvModel : storeAdvModelList){
							JSONObject jAdvObject = new JSONObject();
							jAdvObject.put("imageUrl", storeAdvModel.getUrl());
							jAdvObject.put("imagePath", storeAdvModel.getImgPath());
							jAdvObject.put("storeAdvertisementId", storeAdvModel.getStoreAdvertisementId());
							jAdvObject.put("tittle", storeAdvModel.getTitle());
							jAdvObject.put("status", storeAdvModel.getStatus());
							jAdvImage.put(jAdvObject);
						}
						jObject.put("advertisementImageDetail", jAdvImage);
					}
					jsonArray.put(jObject);
				} // end of for loop
			}
			json.put("storeDetail", jsonArray);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit method getAllStoreAdvDetail of AgentWS");
		return Response.status(200).entity(json).build();
	}
	
	
	/**
	 * This method receives request to get info of all the stores: 1. Call
	 * service method to get list of all the stores. 2. Populate response in
	 * list of <code>JSONObject</code> 2. Send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAllStoreDetailWithNoAdvertisement.csv")
	public Response getAllStoreDetailWithNoAdvertisement() throws Exception {
		LOGGER.info("Enter method getAllStoreDetailWithNoAdvertisement of AgentWS");
		JSONObject json = new JSONObject();
		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			List<ValouxStoreModel> storeModelList = agentService.getStoreNoteHavinAdvertisement();
			if (storeModelList != null && storeModelList.size() != 0) {
				for (ValouxStoreModel storeModel : storeModelList) {
					JSONObject jObject = new JSONObject();
					jObject.put("storeId", storeModel.getStoreId());
					jObject.put("storeName", storeModel.getName());
					jsonArray.put(jObject);
				} // end of for loop
				json.put("storeData", jsonArray);
				responseJson.put("resData", json);
				responseJson.put("sCode", CommonConstants.SUCCESS);
			} else {
				responseJson.put("errorMessage", CommonConstants.STORE_LIST_EMPTY);
				responseJson.put("sCode", CommonConstants.ERROR);
			}

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit method getAllStoreDetailWithNoAdvertisement of AgentWS");
		return Response.status(200).entity(responseJson).build();

	} // end of method getAllStoreDetail
	
	
	/**
	 * This method receives request to merge storess
	 * : 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/mergeStores.csv")
	public Response mergeStores(JSONObject formPaparam, @Context HttpServletRequest request) throws Exception {
		LOGGER.info("Enter Method mergeStores of AgentWS");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "primaryStoreId","storeIdsToBeMerged"};
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer primaryStoreId = JSONUtility.getSafeInteger(jObject, "primaryStoreId");
			JSONArray storeIdsToBeMerged = JSONUtility.getSafeJSONArray(jObject, "storeIdsToBeMerged");
			storeService.mergeStores(primaryStoreId, storeIdsToBeMerged);
			json.put("resData", new JSONObject());
			json.put("successMessage", CommonConstants.STORES_MERGED);
			json.put("sCode", CommonConstants.SUCCESS);

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.ERROR);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			// LOGGER.;
			return Response.status(200).entity(json).build();
		} // end of try catch block

		LOGGER.info("Exit Method mergeStores of AgentWS");
		return Response.status(200).entity(json).build();
	} // end of method mergeStores
	
	/**
	 * This method receives request to get info of all the stores: 1. Call
	 * service method to get list of all the stores. 2. Populate response in
	 * list of <code>JSONObject</code> 2. Send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getStoreDetailById.csv")
	public Response getStoreDetailById(JSONObject formPaparam) throws Exception {
		LOGGER.info("Enter method getStoreDetailById of AgentWS");
		
		JSONObject json = new JSONObject();
		JSONObject responseJson = new JSONObject();
		String reqPaparam = formPaparam.getString("reqPaparam");
		JSONObject jObject = new JSONObject(reqPaparam);
		String requiredPaparams[] = { "publicKey", "storeId"};
		String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
		if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
			json.put("resData", "");
			json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
			json.putOpt("sCode", CommonConstants.INCOMPLETE);
			json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
			return Response.status(200).entity(json.toString()).build();
		}
		
		try {
			Integer storeId = JSONUtility.getSafeInteger(jObject, "storeId");
			if(storeId != null){
				ValouxStoreModel storeModel = storeService.getStoreDataByStoreId(storeId);
				if (storeModel != null) {
					JSONObject resData = new JSONObject();
					Gson gson = new Gson();
					
					String jsonInString = gson.toJson(storeModel);
					resData.put("storeData", new JSONObject(jsonInString));
					responseJson.put("resData", resData);
					responseJson.put("sCode", CommonConstants.SUCCESS);
				} else {
					responseJson.put("errorMessage", CommonConstants.STORE_NOT_FOUND);
					responseJson.put("sCode", CommonConstants.ERROR);
				}
			} else {
				responseJson.put("errorMessage", CommonConstants.STORE_NOT_FOUND);
				responseJson.put("sCode", CommonConstants.ERROR);
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit method getStoreDetailById of AgentWS");
		return Response.status(200).entity(responseJson).build();

	} // end of method getStoreDetailById
	
	/**
	 * This method receives request to get info of all the stores: 1. Call
	 * service method to get list of all the stores. 2. Populate response in
	 * list of <code>JSONObject</code> 2. Send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updateStoreDetailById.csv")
	public Response updateStoreDetailById(JSONObject formPaparam, @Context HttpServletRequest request) throws Exception {
		LOGGER.info("Enter method getStoreDetailById of AgentWS");
		
		JSONObject json = new JSONObject();
		JSONObject responseJson = new JSONObject();
		String reqPaparam = formPaparam.getString("reqPaparam");
		JSONObject jObject = new JSONObject(reqPaparam);
		JSONObject storeData = jObject.getJSONObject("storeData");
		String requiredPaparams[] = { "publicKey"};
		String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
		if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
			json.put("resData", "");
			json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
			json.putOpt("sCode", CommonConstants.INCOMPLETE);
			json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
			return Response.status(200).entity(json.toString()).build();
		}
		String requiredPaparams2[] = { "storeId"};
		missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams2, storeData);
		if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
			json.put("resData", "");
			json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
			json.putOpt("sCode", CommonConstants.INCOMPLETE);
			json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
			return Response.status(200).entity(json.toString()).build();
		}
		
		try {
			Integer storeId = JSONUtility.getSafeInteger(storeData, "storeId");
			if(storeId != null){
				ValouxStoreModel storeModel = storeService.getStoreDataByStoreId(storeId);
				if (storeModel != null) {
					JSONObject resData = new JSONObject();
					Gson gson = new Gson();
					
					AgentHelper.populateAgentDetailsInModel(userService, storeModel, jObject);
					
					storeModel.setIpaddress(request.getRemoteAddr());
					storeService.updateStoreDetails(storeModel);
					
					storeModel = storeService.getStoreDataByStoreId(storeId);
					String jsonInString = gson.toJson(storeModel);
					resData.put("storeData", new JSONObject(jsonInString));
					
					responseJson.put("resData", resData);
					responseJson.put("sCode", CommonConstants.SUCCESS);
					responseJson.put("successMessage", CommonConstants.STORE_UPDATED_SUCCESSFULLY);
				} else {
					responseJson.put("errorMessage", CommonConstants.STORE_NOT_FOUND);
					responseJson.put("sCode", CommonConstants.ERROR);
				}
			} else {
				responseJson.put("errorMessage", CommonConstants.STORE_NOT_FOUND);
				responseJson.put("sCode", CommonConstants.ERROR);
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			 e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit method getStoreDetailById of AgentWS");
		return Response.status(200).entity(responseJson).build();

	} // end of method getStoreDetailById
	
	/**
	 * This method receives request to delete agent sign image: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to get all item
	 * list. 4. Prepare response and send it for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deleteSignImageByAgentId.csv")
	public Response deleteImageByImageId(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter deleteImageByImageId method of ItemWS");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "publicKey" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String publicKey = JSONUtility.getSafeString(reqObject, "publicKey");
			
			AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
			if(agentModel != null) {
				Boolean flag = agentService.deleteAgentSignDocument(publicKey);
				if (flag) {
					json.put("resData", "");
					json.putOpt("successMessage", CommonConstants.IMAGE_DELETED_SUCCESSFULLY);
					json.putOpt("sCode", CommonConstants.SUCCESS);
					return Response.status(200).entity(json.toString()).build();
				} else {
					json.put("resData", "");
					json.putOpt("errorMessage", CommonConstants.ITEM_ERROR_MESSAGE);
					json.putOpt("sCode", CommonConstants.ERROR);
				}
			} else {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_ERROR_MESSAGE);
				json.putOpt("sCode", CommonConstants.ERROR);
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit deleteImageByImageId method of ItemWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to get agent information using
	 * agentPublicKey: 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Call service
	 * method to get details of agent using agentPublicKey 4. Based on data
	 * received from service send response.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAppraiserListOfStore.csv")
	public Response getAppraiserListOfStore(JSONObject formPaparam) throws Exception {
		LOGGER.info("Enter Method getAppraiserListOfStore of AgentWS");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "publicKey", "appraisalId" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			
			ValouxAgentStoreModel agentStoreModel = null;
			String agentKey = JSONUtility.getSafeString(jObject, "publicKey");
			
			JSONArray jArray = new JSONArray();
			AgentModel agentModel = agentService.getAgentDetailByRelationKey(agentKey);
			if(agentModel != null) {
				agentStoreModel = agentService.getStoreDataByRelationKey(agentKey);
				if(agentStoreModel != null) {
					Integer storeId = agentStoreModel.getStoreId();
					List<ValouxAgentStoreBean> agentStoreBeanList = agentService.getAgentDetailAssociatedWithStore(storeId);
					if(agentStoreBeanList != null && agentStoreBeanList.size() > 0){
						
						for (ValouxAgentStoreBean valouxAgentStoreBean : agentStoreBeanList) {
							if(CommonUserUtility.paparameterNullCheckStringObject(valouxAgentStoreBean.getRelationKey())){
								AgentModel agentProfileModel = agentService.getAgentDetailByRelationKey(valouxAgentStoreBean.getRelationKey());
								if(agentProfileModel != null) {
									UserRoleModel roleModel = userService.getRoleModel(agentProfileModel.getRelationKey());
									if(roleModel != null && roleModel.getMasterRoleBeanId() != null){
										if(roleModel.getMasterRoleBeanId().intValue() == UserRoleType.StoreAppraiser.getRoleId()){
											
											LoginModel loginModel = userService.getLoginDetailByPKey((agentProfileModel.getRelationKey()));
											
											JSONObject userData = new JSONObject();
											userData.put("publickKey", agentProfileModel.getRelationKey());
											if(loginModel != null) {
												userData.put("emailId", loginModel.getUserName());
												userData.put("firstName", loginModel.getFirstName());
												userData.put("middleName", loginModel.getMiddleName());
												userData.put("lastName", loginModel.getLastName());
												userData.put("userStatus", loginModel.getUserStatus());
											}
											jArray.put(userData);
										}
									}
								}
							}
						}
					}
				}
			}
			json.put("contactList", jArray);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit Method getAppraiserListOfStore of AgentWS");
		return Response.status(200).entity(json).build();
	} 
	
	
	/**
	 * This method get All agent detail
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAllAgentDetail.csv")
	public Response globalSearch() throws Exception {
		LOGGER.info("Enter method getAllAgentDetail of AgentWS");
		JSONObject resJson = new JSONObject();
		try {
				List<AgentModel> agentModelList = agentService.getAllAgentDetail();
					
				ItemHelper.getAllAgentDetail(agentModelList, resJson, agentService, storeService, userService);
			
			
			resJson.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			resJson.put("errorMessage", e);
			resJson.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(resJson).build();
		}
		LOGGER.info("Exit method getAllAgentDetail of AgentWS");
		return Response.status(200).entity(resJson).build();
	}
	
	/**
	 * This method receives request to get delete agent: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to delete agent. 4. Prepare response and send it for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deleteAgentByUserPublicKey.csv")
	public Response deleteItemByItemId(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter deleteConsumerByUserPublicKey method of UserWs");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "publicKey", "userPublicKey" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String userPublicKey = JSONUtility.getSafeString(reqObject, "userPublicKey");
			AgentModel agentModel = agentService.getAgentDetailByRelationKey(userPublicKey);
			if(agentModel == null){
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.AGENT_NOT_FOUND);
				json.putOpt("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			Boolean flag = false;
			flag = agentService.deleteAgent(userPublicKey);
			if (flag) {
				json.put("resData", "");
				json.putOpt("successMessage", CommonConstants.AGENT_DELETED_SUCCESSFULLY);
				json.putOpt("sCode", CommonConstants.SUCCESS);
				return Response.status(200).entity(json.toString()).build();
			}
			json.put("resData", "");
			json.putOpt("errorMessage", CommonConstants.ITEM_ERROR_MESSAGE);
			json.putOpt("sCode", CommonConstants.ERROR);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit deleteConsumerByUserPublicKey method of UserWs");
		return Response.status(200).entity(json).build();
	}


} // end of class
