/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.webservices;

import java.util.List;

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
import org.springfparamework.transaction.annotation.Transactional;

import com.valoux.bean.AgentBean;
import com.valoux.bean.AppraisalBean;
import com.valoux.bean.LoginBean;
import com.valoux.bean.LoginLogBean;
import com.valoux.bean.MasterRoleBean;
import com.valoux.bean.UserBean;
import com.valoux.bean.UserRoleBean;
import com.valoux.bean.UserTypeBean;
import com.valoux.bean.ValouxSharedRequestBean;
import com.valoux.constant.CommonConstants;
import com.valoux.helper.ItemHelper;
import com.valoux.helper.UserHelper;
import com.valoux.model.AgentModel;
import com.valoux.model.AppraisalModel;
import com.valoux.model.CountryModel;
import com.valoux.model.LoginModel;
import com.valoux.model.UserModel;
import com.valoux.model.ValouxCollectionModel;
import com.valoux.model.ValouxInterestInModel;
import com.valoux.model.ValouxItemModel;
import com.valoux.model.ValouxPersonalPreferencesModel;
import com.valoux.model.ValouxStoreModel;
import com.valoux.service.AgentService;
import com.valoux.service.AppraisalService;
import com.valoux.service.CollectionService;
import com.valoux.service.ItemService;
import com.valoux.service.UserService;
import com.valoux.service.ValouxStoreService;
import com.valoux.util.CommonUserUtility;
import com.valoux.util.CommonUtility;
import com.valoux.util.JSONUtility;

/**
 * This <java>class</java> serves request related to different operations
 * performed on user/s
 * 
 * @author param Sheikh
 * 
 */

@Path("user")
@Component
public class UserWS {
	@Autowired
	UserService userService;

	@Autowired
	AgentService agentService;

	@Autowired
	ItemService itemService;
	
	@Autowired
	AppraisalService appraisalService;

	@Autowired
	CollectionService collectionService;
	
	@Autowired
	ValouxStoreService storeService;

	private static final Logger LOGGER = Logger.getLogger(UserWS.class);

	/**
	 * This method receives request to perform authentication: 1. Check request
	 * for essential paparameters. 2. Converts <code>JSONObject</code> request to
	 * business objects. 3. Calls service method to perform authentication. 4.
	 * If user authenticated send back supporting data to land on application
	 * screens. 5. If user authentication fails send back authentication error.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/userLogin.dns")
	@Transactional
	public Response authenticate(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method login of UserWS");
		JSONObject json = new JSONObject();
		JSONObject resData = new JSONObject();
		JSONObject loginJson = new JSONObject();
		LoginBean authUser = new LoginBean();
		LoginModel loginModel = new LoginModel();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject loginData = jObject.getJSONObject("loginData");
			String requiredPaparams[] = { "emailId", "password" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, loginData);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String token = CommonUtility.generatetToken();
			String emailId = JSONUtility.getSafeString(loginData, "emailId");
			String password = JSONUtility.getSafeString(loginData, "password");
			String encryptedPassword = (password);
			LoginBean loginBean = new LoginBean();
			loginBean.setUserName(emailId);
			loginBean.setPassword(encryptedPassword);

			loginModel = userService.getLoginDetailByUserName(emailId);

			if (loginModel != null && (loginModel.getUserStatus() == CommonConstants.STATUS_INACTIVE || loginModel.getUserStatus() == CommonConstants.AGENT_STATUS_EMAIL_VERIFIED)) {
				resData.put("loginInfo", loginJson);
				json.put("resData", resData);
				json.putOpt("errorMessage", CommonConstants.USER_INACTIVE);
				json.putOpt("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json.toString()).build();
			}
			boolean isValidRequest = CommonUserUtility.checkValidRequestForUserLogin(loginBean);

			if (!isValidRequest) {
				resData.put("loginInfo", loginJson);
				json.put("resData", resData);
				json.putOpt("errorMessage", CommonConstants.ERROR_MESSAGE);
				json.putOpt("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json.toString()).build();
			}// end of if block if user name and password is invalid

			UserBean userBean = null;
			AgentBean agentBean = null;
			UserRoleBean userRoleBean = new UserRoleBean();
			UserTypeBean userTypeBean = new UserTypeBean();
			LoginLogBean loginLogBean = new LoginLogBean();
			// LoginLogModel loginLogModel = new LoginLogModel();
			authUser = userService.checkLoginRequest(loginBean);
			// JSONArray loginArray = new JSONArray();
			if (authUser != null) {
				loginJson.put("firstName", authUser.getFirstName());
				loginJson.put("middleName", authUser.getMiddleName());
				loginJson.put("lastName", authUser.getLastName());
				// loginJson.put("emailId", authUser.getUserName());
				json.put("resData", resData);
				json.putOpt("sCode", CommonConstants.SUCCESS);
				userBean = userService.getUserDetails(authUser.getUserName());
				if (userBean != null) {
					loginLogBean = CommonUserUtility.prepareLoginLogsBeanForUser(userBean);
					loginLogBean = userService.createLoginLogs(loginLogBean);
					userRoleBean = userService.getRole(userBean.getRelationKey());
//					MasterRoleBean masterRoleBean = agentService.getRoleData(String.valueOf(userRoleBean.getRoleId()));
					MasterRoleBean masterRoleBean = agentService.getRoleData(String.valueOf(userRoleBean.getMasterRoleBean().getRoleId()));
					userTypeBean = userService.getUserType(userBean.getRelationKey());
					json.put("role", masterRoleBean.getRoleId());
					json.put("roleType", userTypeBean.getUserType());
					json.put("publicKey", userBean.getRelationKey());
					json.put("token", token);
					json.put("memberShipKey", userBean.getRelationKey());
					// loginArray.put(loginJson);
					resData.put("loginInfo", loginJson);
					json.put("resData", resData);
				}// end of if block if userBean is not null

				else {
					agentBean = agentService.getAgentDetails(authUser.getUserName());
					loginLogBean = CommonUserUtility.prepareLoginLogsBeanForAgent(agentBean);
					loginLogBean = userService.createLoginLogs(loginLogBean);
					userRoleBean = userService.getRole(agentBean.getRelationKey());
					userTypeBean = userService.getUserType(agentBean.getRelationKey());
					/*int sharedRequestCount=0;
					List<ValouxSharedRequestBean> sharedRequestBeanList = itemService.getListOfRequestedSharedItemsToAgent(agentBean.getRelationKey());
					if(sharedRequestBeanList != null && sharedRequestBeanList.size() > 0){
						sharedRequestCount = sharedRequestBeanList.size();
					}*/
					json.put("role", userRoleBean.getMasterRoleBean().getRoleId());
					json.put("roleType", userTypeBean.getUserType());
					json.put("authRequired", 1);
					json.put("authUserID", loginLogBean.getLogId());
					json.put("publicKey", agentBean.getRelationKey());
					json.put("token", token);
					json.put("memberShipKey", agentBean.getRelationKey());
					//json.put("sharedRequestCount", sharedRequestCount);
					// loginArray.put(loginJson);
					resData.put("loginInfo", loginJson);
					json.put("resData", resData);
				}

			} else {
				resData.put("loginInfo", loginJson);
				json.put("resData", resData);
				json.putOpt("errorMessage", CommonConstants.ERROR_MESSAGE);
				json.putOpt("sCode", CommonConstants.ERROR);

			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}// end of try catch block
		LOGGER.info("Exit Method login of UserWS");
		return Response.status(200).entity(json.toString()).build();
	} // end of method userLogin

	/**
	 * This method receives request to check if user already exist with same
	 * email address. 1. Check request for essential paparameters. 2. Calls
	 * service method to check if email is already exist as user. 3. Prepare and
	 * send response based on result from service.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/checkExistingEmailId.dns")
	public Response checkEmail(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method checkExistingEmailId of UserWS");
		JSONObject json = new JSONObject();
		JSONObject resData = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jsonReqPaparam = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "emailId" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jsonReqPaparam);

			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String email = JSONUtility.getSafeString(jsonReqPaparam, "emailId");
			boolean isEmailExist = userService.checkEmailAlreadyRegistered(email);
			if (isEmailExist) {
				resData.put("isEmailExist", true);
				json.put("resData", resData);
				json.putOpt("sCode", CommonConstants.SUCCESS);
				return Response.status(200).entity(json.toString()).build();

			} else {

				resData.put("isEmailExist", false);
				json.put("resData", resData);
				json.putOpt("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json.toString()).build();
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}// end of try catch block

	}

	/**
	 * This method receives request to register an User and performs following
	 * operations: 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to check if user already exist. 4. If validations are not failing
	 * call service method to create User. 5. On successful creation of User
	 * send email. 6. Send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/userRegistration.dns")
	public Response userRegistration(JSONObject formPaparam, @Context HttpServletRequest request) throws Exception {
		LOGGER.info("Enter Method useRregistration of UserWS");
		JSONObject json = new JSONObject();
		JSONObject userRegistrationData = new JSONObject();
		UserModel userModel = new UserModel();
		UserBean userBean = new UserBean();
		LoginModel loginModel = new LoginModel();
		LoginBean loginBean = new LoginBean();

		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject registrationData = jObject.getJSONObject("registrationData");
			String requiredPaparams[] = { "emailId", "mobilePhone", "passwordResetQuetion", "passwordResetAnswer",
					"password", "firstName", "lastName" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, registrationData);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String relationKey = String.valueOf(CommonUtility.generateRandom(4));
			String authCode = CommonUtility.generatetToken();
			String forgetPasswordKey = CommonUtility.generatetToken();
			String privateKey = CommonUtility.generatetToken();
			String authCodeMobile = String.valueOf(CommonUtility.generateRandom(6));
			String emailId = JSONUtility.getSafeString(registrationData, "emailId");
			String alternateEmailId = JSONUtility.getSafeString(registrationData, "alternateEmailId");
			String globalAddress = JSONUtility.getSafeString(registrationData, "gAddress");
			JSONObject fullAddress = registrationData.getJSONObject("userAddress");
			String streetNo = JSONUtility.getSafeString(fullAddress, "streetNo");
			String addressLine1 = JSONUtility.getSafeString(fullAddress, "addressLine1");
			String addressLine2 = JSONUtility.getSafeString(fullAddress, "addressLine2");
			String city = JSONUtility.getSafeString(fullAddress, "city");
			String state = JSONUtility.getSafeString(fullAddress, "state");
			String country = JSONUtility.getSafeString(fullAddress, "country");
			String zipCode = JSONUtility.getSafeString(fullAddress, "zipCode");
			String mobile = JSONUtility.getSafeString(registrationData, "mobilePhone");
			String alternateMobile = JSONUtility.getSafeString(registrationData, "alternateMobile");
			String birthDay = JSONUtility.getSafeString(registrationData, "birthday");
			String company = JSONUtility.getSafeString(registrationData, "company");
			String resetQuestion = JSONUtility.getSafeString(registrationData, "passwordResetQuetion");
			String resetAnswer = JSONUtility.getSafeString(registrationData, "passwordResetAnswer");
			Integer gender = JSONUtility.getSafeInteger(registrationData, "gender");
			Integer maritalStatus = JSONUtility.getSafeInteger(registrationData, "maritalStatus");
			Integer incomeRange = JSONUtility.getSafeInteger(registrationData, "incomeRange");
			String ipAddress = request.getRemoteAddr();
			String zipPlus4 = JSONUtility.getSafeString(fullAddress, "zipCode+4");
			String facebook = JSONUtility.getSafeString(registrationData, "facebook");
			String google = JSONUtility.getSafeString(registrationData, "google");
			String instagparam = JSONUtility.getSafeString(registrationData, "instagparam");
			String twitter = JSONUtility.getSafeString(registrationData, "twitter");
			String salutation = JSONUtility.getSafeString(registrationData, "salutation");
			String password = JSONUtility.getSafeString(registrationData, "password");
			String firstName = JSONUtility.getSafeString(registrationData, "firstName");
			String middleName = JSONUtility.getSafeString(registrationData, "middleName");
			String lastName = JSONUtility.getSafeString(registrationData, "lastName");

			JSONObject personalPreference = registrationData.getJSONObject("personalPreferences");
			JSONArray JewelryTypes = JSONUtility.getSafeJSONArray(personalPreference, "JewelryTypes");
			JSONArray JewelryDesign = JSONUtility.getSafeJSONArray(personalPreference, "JewelryDesign");
			JSONArray JewelryStyle = JSONUtility.getSafeJSONArray(personalPreference, "JewelryStyle");

			JSONObject consumerAbou = registrationData.getJSONObject("consumerAbout");

			JSONArray JewelryPurchases = JSONUtility.getSafeJSONArray(consumerAbou, "JewelryPurchases");
			JSONArray JewelryInsurance = JSONUtility.getSafeJSONArray(consumerAbou, "JewelryInsurance");
			JSONArray JewelryService = JSONUtility.getSafeJSONArray(consumerAbou, "JewelryService");
			JSONArray JewelryDocumentation = JSONUtility.getSafeJSONArray(consumerAbou, "JewelryDocumentation");

			JSONObject jewelryComponent = registrationData.getJSONObject("jewelryComponents");

			JSONArray metals = JSONUtility.getSafeJSONArray(jewelryComponent, "metals");
			JSONArray gemstones = JSONUtility.getSafeJSONArray(jewelryComponent, "gemstones");

			JSONArray diamonds = JSONUtility.getSafeJSONArray(jewelryComponent, "diamonds");
						JSONArray interestedIn = JSONUtility.getSafeJSONArray(registrationData, "interestedIn");
			userModel.setRelationKey(relationKey);
			userModel.setEmailId(emailId);
			userModel.setAlternateEmailId(alternateEmailId);
			userModel.setGlobalAddress(globalAddress);
			userModel.setStreetNo(streetNo);
			userModel.setAddressLine1(addressLine1);
			userModel.setAddressLine2(addressLine2);
			userModel.setCity(city);
			Integer countryId = userService.getCountryId(country);
			Integer stateId = userService.getStateIdentifier(state, countryId);
			userModel.setStateId(stateId);
			userModel.setCountryId(countryId);
			userModel.setZipCode(zipCode);
			userModel.setMobile(mobile);
			userModel.setAlternateMobile(alternateMobile);
			if (!birthDay.isEmpty()) {
				userModel.setDateOfBirth(CommonUtility.convertUIStringToDate(birthDay));
			}
			userModel.setCompany(company);
			userModel.setPasswordResetAnswer(resetAnswer);
			userModel.setPasswordResetQuetion(resetQuestion);
			userModel.setGender(gender);
			userModel.setMaritalStatus(maritalStatus);
			userModel.setIncomeRange(incomeRange);
			userModel.setIp(ipAddress);
			userModel.setZip4(zipPlus4);
			userModel.setFacebook(facebook);
			userModel.setGoogle(google);
			userModel.setInstagparam(instagparam);
			userModel.setTwitter(twitter);
			userModel.setSalutation(salutation);
			userModel.setJewelryTypes(JewelryTypes);
			userModel.setJewelryDesign(JewelryDesign);
			userModel.setJewelryStyle(JewelryStyle);
			// userModel.setJewelryStyle(JewelryStyle);

			userModel.setInterestedIn(interestedIn);
			userModel.setJewelryPurchases(JewelryPurchases);
			userModel.setJewelryInsurance(JewelryInsurance);
			userModel.setJewelryService(JewelryService);
			userModel.setJewelryDocumentation(JewelryDocumentation);

			userModel.setMetals(metals);
			userModel.setGemstones(gemstones);
			userModel.setDiamonds(diamonds);

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
			loginModel.setCreatedOn(CommonUtility.getDateAndTime());
			loginModel.setModifiedOn(CommonUtility.getDateAndTime());

			userModel.setCreatedOn(CommonUtility.getDateAndTime());
			userModel.setModifiedOn(CommonUtility.getDateAndTime());

			boolean isValidRequest = CommonUserUtility.checkValidRequestForUserRegistration(userModel, loginModel);
			if (!isValidRequest) {
				JSONObject blankJsonObject = new JSONObject();
				json.put("resData", blankJsonObject);
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("errorMessage", CommonConstants.INVALID_REQUEST);
				return Response.status(200).entity(json).build();
			}
			boolean isEmailExist = userService.checkEmailAlreadyRegistered(emailId);
			if (isEmailExist) {
				json.put("resData", userRegistrationData);
				json.put("errorMessage", CommonConstants.EMAIL_ID_ALREADY_EXIST);
				json.put("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}

			loginBean = userService.saveLoginInfo(loginModel);
			if (loginBean != null) {
				String pKey = loginBean.getPrivateKey();
				userModel.setRelationKey((pKey));
				userModel.setCreatedBy((pKey));
				userModel.setModifiedBy((pKey));
				userBean = userService.createUser(userModel);
			}
			if (userBean == null) {
				json.put("resData", userRegistrationData);
				json.put("errorMessage", CommonConstants.REGISTRATION_ERROR_MESSAGE);
				json.put("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			
			MasterRoleBean masterRoleBean = agentService.getRoleData(String.valueOf(CommonConstants.CONSUMER_ADMIN));

			userService.createUserTypeAndUserRoleForConsumer(userBean, loginBean, masterRoleBean);
			itemService.updateSharedRequestForNewRegistration(loginBean.getUserName(), userBean.getRelationKey(),
					CommonConstants.CONSUMER);
			json.put("resData", userRegistrationData);
			json.put("successMessage", CommonConstants.REGISTRATION_COMPLETE_MESSAGE);
			json.put("sCode", CommonConstants.SUCCESS);

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.SUCCESS);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit Method useRregistration of UserWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to get master data related to Interest. 1.
	 * Get list of all the interest from service method. 2. Prepare response and
	 * send for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getUserInterestIn.dns")
	public Response getUserInterestIn() throws Exception {
		LOGGER.info("Enter Method getUserInterestIn of UserWS");
		JSONObject json = new JSONObject();
		JSONObject responseJson = new JSONObject();
		List<ValouxInterestInModel> interestInList = userService.getUserInterestIn();
		JSONArray jsonArray = new JSONArray();
		try {
			if (interestInList != null && interestInList.size() != 0) {
				for (ValouxInterestInModel interestInModel : interestInList) {
					JSONObject jObject = new JSONObject();
					jObject.put("interestId", interestInModel.getInterestId());
					jObject.put("interest", interestInModel.getInterest());
					jsonArray.put(jObject);
				}
				json.put("userInterestIn", jsonArray);
				responseJson.put("resData", json);
				responseJson.put("sCode", CommonConstants.SUCCESS);
			}else{
				responseJson.put("userInterestIn", jsonArray);
				responseJson.put("errorMessage", CommonConstants.LIST_EMPTY);
				responseJson.put("sCode", CommonConstants.SUCCESS);
			}
			
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}// end of try catch block
		LOGGER.info("Exit Method getUserInterestIn of UserWS");
		return Response.status(200).entity(responseJson).build();
	}

	/**
	 * This method receives request to get master data related to Personal
	 * Preferences. 1. Get list of all the personal preferences using service
	 * method. 2. Prepare response and send for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getUserPersonalPreference.dns")
	public Response getAllPersonalPreferences() throws Exception {
		LOGGER.info("Enter Method getAllPersonalPreferences of UserWS");
		JSONObject json = new JSONObject();
		JSONObject responseJson = new JSONObject();
		List<ValouxPersonalPreferencesModel> ppModelList = userService.getAllPersonalPreferences();
		JSONArray jsonArray = new JSONArray();
		try {
			if (ppModelList != null && ppModelList.size() != 0) {
				for (ValouxPersonalPreferencesModel ppModel : ppModelList) {
					JSONObject jObject = new JSONObject();
					jObject.put("personalId", ppModel.getPersonalId());
					jObject.put("personalPreferences", ppModel.getPersonalPreferences());
					jObject.put("ppType", ppModel.getPpType());
					jsonArray.put(jObject);
				}
				json.put("allPreferenceData", jsonArray);
				responseJson.put("resData", json);
				responseJson.put("sCode", CommonConstants.SUCCESS);
			}
			else{
				responseJson.put("allPreferenceData", jsonArray);
				responseJson.put("errorMessage", CommonConstants.LIST_EMPTY);
				responseJson.put("sCode", CommonConstants.SUCCESS);
			}
			
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}// end of try catch block
		LOGGER.info("Exit Method getAllPersonalPreferences of UserWS");
		return Response.status(200).entity(responseJson).build();
	}

	/**
	 * This method receives request to activate an user 1. Get required
	 * paparameters from request 2. Call service method to activate user. 3.
	 * Prepare and send response based on result from service call.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/activateUser.dns")
	public Response activateUser(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method activateUser of UserWS");
		JSONObject json = new JSONObject();
		JSONObject resData = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jsonReqPaparam = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "token", "otp" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jsonReqPaparam);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String token = JSONUtility.getSafeString(jsonReqPaparam, "token");
			String otp = JSONUtility.getSafeString(jsonReqPaparam, "otp");
			if (null == token || token.length() == 0) {
				json.put("resData", resData);
				json.putOpt("errorMessage", CommonConstants.INVALID_INVALID_ACTIVATION_KEY);
				json.putOpt("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json.toString()).build();
			}

			boolean activationResult = userService.checkAndActivatUser(token, otp);
			if (activationResult) {
				// resData.put("activationResult", true);
				json.put("resData", resData);
				json.putOpt("successMessage", "Email verified successfully.");
				json.putOpt("sCode", CommonConstants.SUCCESS);
			} else {
				// resData.put("activationResult", false);
				json.put("resData", resData);
				json.putOpt("errorMessage", CommonConstants.INVALID_INVALID_ACTIVATION_KEY);
				json.putOpt("sCode", CommonConstants.ERROR);
			}

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}// end of try catch block
		LOGGER.info("Exit Method activateUser of UserWS");
		return Response.status(200).entity(json.toString()).build();
	}

	/**
	 * This method receives request to provide consumer details.. 1. get
	 * required paparameters from request 2. call service method to get consumer
	 * details. 3. based on service API data prepare and send response
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getConsumerInfo.csv")
	public Response getConsumerInfo(JSONObject formPaparam) throws Exception {
		LOGGER.info("Enter Method getConsumerInfo of UserWS");
		JSONObject json = new JSONObject();
		JSONObject responseJson = new JSONObject();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "userPublicKey" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String rKey = JSONUtility.getSafeString(jObject, "userPublicKey");
			String pKey = (rKey);
			UserModel userModel = userService.getConsumerDetailByRKey(rKey);
			LoginModel loginModel = userService.getLoginDetailByPKey(pKey);
			if (userModel == null || loginModel == null) {
				json.put("resData", responseJson);
				json.put("errorMessage", CommonConstants.INFO_ERROR_MESSAGE);
				json.put("sCode", CommonConstants.ERROR);
			} else {

				JSONObject userDataJson = new JSONObject();
				responseJson.put("rKey", rKey);
				responseJson.put("firstName", loginModel.getFirstName());
				responseJson.put("lastName", loginModel.getLastName());
				responseJson.put("middleName", loginModel.getMiddleName());
				responseJson.put("emailId", loginModel.getUserName());
				responseJson.put("mobilePhone", userModel.getMobile());
				responseJson.put("gender", userModel.getGender());
				responseJson.put("birthday", userModel.getDob());
				responseJson.put("maritalStatus", userModel.getMaritalStatus());
				responseJson.put("incomeRange", userModel.getIncomeRange());
				responseJson.put("interestedIn", userModel.getInterestedIn());
				responseJson.put("personalPreferences", userModel.getPersonalPreferences());
				responseJson.put("consumerAbout", userModel.getConsumerAbout());
				responseJson.put("jewelryComponents", userModel.getJewelryComponents());
				responseJson.put("imageUrl", userModel.getImageURL());
				JSONObject addressJson = new JSONObject();
				addressJson.put("address", userModel.getGlobalAddress());
				addressJson.put("addressLine1", userModel.getAddressLine1());
				addressJson.put("addressLine2", userModel.getAddressLine2());
				addressJson.put("streetNo", userModel.getStreetNo());
				addressJson.put("country", userModel.getCountryName());
				addressJson.put("city", userModel.getCity());
				addressJson.put("state", userModel.getStateName());
				addressJson.put("zipCode", userModel.getZipCode());
				responseJson.put("userAddress", addressJson);
				userDataJson.put("userData", responseJson);
				json.put("resData", userDataJson);
				json.put("sCode", CommonConstants.SUCCESS);

			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}// end of try catch block
		LOGGER.info("Exit Method getConsumerInfo of UserWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to update consumer information. 1. Get
	 * required paparams from request and prepare business object 2. Call service
	 * method to update consumer information 3. Based on result form service
	 * prepare response and send for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updateConsumerInfo.csv")
	public Response updateConsumerInfo(JSONObject formPaparam, @Context HttpServletRequest request) throws Exception {
		LOGGER.info("Enter Method updateConsumerInfo of UserWS");
		JSONObject json = new JSONObject();
		JSONObject resJson = new JSONObject();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			LoginModel loginModel = new LoginModel();
			UserModel userModel = new UserModel();
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject jsonToUpdate = jObject.getJSONObject("userDataToUpdate");
			String requiredPaparams[] = { "userPublicKey" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jsonToUpdate);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String rKey = JSONUtility.getSafeString(jsonToUpdate, "userPublicKey");
			String firstName = JSONUtility.getSafeString(jsonToUpdate, "firstName");
			String lastName = JSONUtility.getSafeString(jsonToUpdate, "lastName");
			String middleName = JSONUtility.getSafeString(jsonToUpdate, "middleName");

			// Mobile cannot be update in this request
			// String mobilePhone = JSONUtility.getSafeString(jsonToUpdate,
			// "mobilePhone");

			Integer gender = JSONUtility.getSafeInteger(jsonToUpdate, "gender");
			String birthDay = JSONUtility.getSafeString(jsonToUpdate, "birthday");
			Integer maritalStatus = JSONUtility.getSafeInteger(jsonToUpdate, "maritalStatus");
			Integer incomeRange = JSONUtility.getSafeInteger(jsonToUpdate, "incomeRange");
			JSONArray interestedInJsonArray = JSONUtility.getSafeJSONArray(jsonToUpdate, "interestedIn");
			// JSONObject personalPreference =
			// JSONUtility.getSafeJSONArray(jsonToUpdate,
			// "personalPreferences");
			JSONObject personalPreference = jsonToUpdate.getJSONObject("personalPreferences");

			JSONArray JewelryTypes = JSONUtility.getSafeJSONArray(personalPreference, "JewelryTypes");
			JSONArray JewelryDesign = JSONUtility.getSafeJSONArray(personalPreference, "JewelryDesign");
			JSONArray JewelryStyle = JSONUtility.getSafeJSONArray(personalPreference, "JewelryStyle");

			JSONObject consumerAbou = jsonToUpdate.getJSONObject("consumerAbout");

			JSONArray JewelryPurchases = JSONUtility.getSafeJSONArray(consumerAbou, "JewelryPurchases");
			JSONArray JewelryInsurance = JSONUtility.getSafeJSONArray(consumerAbou, "JewelryInsurance");
			JSONArray JewelryService = JSONUtility.getSafeJSONArray(consumerAbou, "JewelryService");
			JSONArray JewelryDocumentation = JSONUtility.getSafeJSONArray(consumerAbou, "JewelryDocumentation");

			JSONObject jewelryComponent = jsonToUpdate.getJSONObject("jewelryComponents");

			JSONArray metals = JSONUtility.getSafeJSONArray(jewelryComponent, "metals");
			JSONArray gemstones = JSONUtility.getSafeJSONArray(jewelryComponent, "gemstones");
			JSONArray diamonds = JSONUtility.getSafeJSONArray(jewelryComponent, "diamonds");

			String privateKey = (rKey);
			JSONObject fullAddress = jsonToUpdate.getJSONObject("userAddress");

			String streetNo = null;
			String globalAddress = null;
			if (fullAddress != null) {

				// Currently global address is not updated
				// if (fullAddress.has("address")) {
				// globalAddress = fullAddress.getString("address");
				// }

				if (fullAddress.has("streetNo")) {
					streetNo = JSONUtility.getSafeString(fullAddress, "streetNo");
				}

				String addressLine1 = JSONUtility.getSafeString(fullAddress, "addressLine1");

				String addressLine2 = JSONUtility.getSafeString(fullAddress, "addressLine2");

				String city = JSONUtility.getSafeString(fullAddress, "city");
				String state = JSONUtility.getSafeString(fullAddress, "state");
				String country = JSONUtility.getSafeString(fullAddress, "country");
				String zipCode = JSONUtility.getSafeString(fullAddress, "zipCode");

				userModel.setGlobalAddress(globalAddress);
				userModel.setStreetNo(streetNo);
				userModel.setAddressLine1(addressLine1);
				userModel.setAddressLine2(addressLine2);
				userModel.setCity(city);

				if (country != null) {
					Integer countryId = userService.getCountryId(country);

					Integer stateId = userService.getStateIdentifier(state, countryId);

					userModel.setStateId(stateId);
					userModel.setCountryId(countryId);
				}
				userModel.setZipCode(zipCode);
			}
			if (birthDay != null && !birthDay.equals("")  && !birthDay.isEmpty()) {
				userModel.setDateOfBirth(CommonUtility.convertUIStringToDate(birthDay));
			} else {
				userModel.setDateOfBirth(null);
			}
			userModel.setGender(gender);
			userModel.setMaritalStatus(maritalStatus);
			userModel.setIncomeRange(incomeRange);
			userModel.setRelationKey(rKey);

			// Mobile cannot be update in this request
			// userModel.setMobile(mobilePhone);
			String ipAddress = request.getRemoteAddr();
			userModel.setIp(ipAddress);
			// userModel.setPersonalPreferences(personalPreferencesArray);
			userModel.setInterestedIn(interestedInJsonArray);

			userModel.setJewelryTypes(JewelryTypes);
			userModel.setJewelryDesign(JewelryDesign);
			userModel.setJewelryStyle(JewelryStyle);

			userModel.setJewelryPurchases(JewelryPurchases);
			userModel.setJewelryInsurance(JewelryInsurance);
			userModel.setJewelryService(JewelryService);
			userModel.setJewelryDocumentation(JewelryDocumentation);

			userModel.setMetals(metals);
			userModel.setGemstones(gemstones);
			userModel.setDiamonds(diamonds);

			loginModel.setFirstName(firstName);
			loginModel.setMiddleName(middleName);
			loginModel.setLastName(lastName);
			loginModel.setModifiedOn(CommonUtility.getDateAndTime());
			loginModel.setModifiedBy(rKey);
			loginModel.setPrivateKey(privateKey);
			userModel.setModifiedBy(rKey);
			userModel.setModifiedOn(CommonUtility.getDateAndTime());
			userService.updateConsumerDetail(userModel, loginModel);
			json.put("resData", resJson);
			json.put("successMessage", CommonConstants.SUCCCESS_MESSSAGE);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}// end of try catch block
		LOGGER.info("Exit Method updateConsumerInfo of UserWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to re-send OTP for the user specific
	 * authcode. 1. Get required auth code from request 2. call service method
	 * to resend One time password to user 3. preapare and send response for
	 * call back
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/resendOTP.dns")
	public Response resendOTP(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method resendOTP of UserWS");
		JSONObject json = new JSONObject();
		JSONObject resData = new JSONObject();
		LoginBean loginBean = new LoginBean();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jsonReqPaparam = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "authLoginCode" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jsonReqPaparam);

			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String authLoginCode = JSONUtility.getSafeString(jsonReqPaparam, "authLoginCode");
			loginBean = userService.resendOTP(authLoginCode);
			if (loginBean != null) {
				resData.put("isOTPSent", true);
				json.put("resData", resData);
				json.putOpt("sCode", CommonConstants.SUCCESS);
				return Response.status(200).entity(json.toString()).build();
			} else {
				resData.put("isOTPSent", false);
				json.put("resData", resData);
				json.putOpt("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json.toString()).build();
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}// end of try catch block

	}

	/**
	 * This method receives request to change password of user. 1. Fetch
	 * required inputs from request. 2. Prepare business object from request
	 * paparameters 3. call service method to change password 4. prepare and send
	 * response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/changePassword.csv")
	public Response changePassword(JSONObject formPaparam) throws Exception {
		LOGGER.info("Enter Method changePassword of UserWS");
		JSONObject json = new JSONObject();
		JSONObject resJson = new JSONObject();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			LoginModel loginModel = new LoginModel();
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject reqJson = jObject.getJSONObject("changePasswordRequest");
			String requiredPaparams[] = { "userPublicKey", "oldPassword", "newPassword" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqJson);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String rKey = JSONUtility.getSafeString(reqJson, "userPublicKey");
			String pKey = (rKey);
			String oldPassword = JSONUtility.getSafeString(reqJson, "oldPassword");
			String newPassword = JSONUtility.getSafeString(reqJson, "newPassword");
			loginModel.setPrivateKey(pKey);
			loginModel.setPassword((oldPassword));
			loginModel.setModifiedOn(CommonUtility.getDateAndTime());
			loginModel.setModifiedBy(rKey);
			Boolean isValidrequest = CommonUserUtility.checkvalidRequestForChangePassword(oldPassword, newPassword);
			if (!isValidrequest) {
				json.put("resData", resJson);
				json.put("errorMessage", CommonConstants.INVALID_REQUEST);
				json.put("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			Boolean isPasswordChanged = userService.changePassword(loginModel,
					(newPassword));
			if (!isPasswordChanged) {
				json.put("resData", resJson);
				json.put("errorMessage", CommonConstants.EMAIL_ERROR_MESSAGE);
				json.put("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			json.put("resData", resJson);
			json.put("successMessage", CommonConstants.CHANGE_PASSWORD_SUCCESS_MESSAGE);
			json.put("sCode", CommonConstants.SUCCESS);
			return Response.status(200).entity(json).build();
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}// end of try catch block
	}

	/**
	 * This method changeEmailIdOfUser() use to change user password.
	 * 
	 * 
	 * @throws Exception
	 * 
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getSecurityQuestion.csv")
	public Response getSecurityQuestion(JSONObject formPaparam) throws Exception {
		LOGGER.info("Enter Method getSecurityQuestion of UserWS");
		JSONObject json = new JSONObject();
		JSONObject resJson = new JSONObject();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject reqJson = jObject.getJSONObject("getSecurityQuestionRequest");
			String requiredPaparams[] = { "emailid" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqJson);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String emailId = JSONUtility.getSafeString(reqJson, "emailid");
			if (emailId == null) {
				json.put("resData", resJson);
				json.put("errorMessage", CommonConstants.INVALID_REQUEST);
				json.put("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			String securityQuestion = userService.getSecurityQuestionByEmailid(emailId);
			if (securityQuestion == null) {
				json.put("resData", resJson);
				json.put("errorMessage", CommonConstants.INVALID_EMAIL_ADDRESS);
				json.put("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			resJson.put("securityQuestion", securityQuestion);
			json.put("resData", resJson);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit method getSecurityQuestion of UserWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method changeEmailIdOfUser() use to change user password.
	 * 
	 * 
	 * @throws Exception
	 * 
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/verifySecurityAnswer.csv")
	public Response verifySecurityAnswer(JSONObject formPaparam) throws Exception {
		LOGGER.info("Enter Method verifySecurityAnswer of UserWS");
		JSONObject json = new JSONObject();
		JSONObject resJson = new JSONObject();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject reqJson = jObject.getJSONObject("verifySecurityAnswer");
			String requiredPaparams[] = { "emailid", "securityQuestion", "securityAnswer" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqJson);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String emailId = JSONUtility.getSafeString(reqJson, "emailid");
			String securityQuestion = JSONUtility.getSafeString(reqJson, "securityQuestion");
			String securityAnswer = JSONUtility.getSafeString(reqJson, "securityAnswer");
			if (emailId == null || securityAnswer == null || securityQuestion == null) {
				json.put("resData", resJson);
				json.put("errorMessage", CommonConstants.INVALID_REQUEST);
				json.put("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			Boolean isAnswerVerified = userService.verifySecurityAnswer(emailId, securityQuestion, securityAnswer);
			if (!isAnswerVerified) {
				json.put("resData", resJson);
				json.put("errorMessage", CommonConstants.WRONG_ANSWER);
				json.put("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			json.put("resData", resJson);
			json.put("successMessage", CommonConstants.RIGHT_ANSWER);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit method verifySecurityAnswer of UserWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method changeEmailIdOfUser() use to change user password.
	 * 
	 * 
	 * @throws Exception
	 * 
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/verifyKeyAndChangePassword.csv")
	public Response verifyKeyAndChangePassword(JSONObject formPaparam) throws Exception {
		LOGGER.info("Enter Method verifyKeyAndChangePassword of UserWS");
		JSONObject json = new JSONObject();
		JSONObject resJson = new JSONObject();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject reqJson = jObject.getJSONObject("verifyKeyAndChangePasswordRequest");
			String requiredPaparams[] = { "forgetPasswordKey", "newPassword" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqJson);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String forgetPasswordKey = JSONUtility.getSafeString(reqJson, "forgetPasswordKey");
			String newPassword = JSONUtility.getSafeString(reqJson, "newPassword");
			if (forgetPasswordKey == null || newPassword == null) {
				json.put("resData", resJson);
				json.put("errorMessage", CommonConstants.INVALID_REQUEST);
				json.put("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			Boolean isPasswordUpdated = userService.updatePasswordAndForgetPasswordKey(forgetPasswordKey,
					(newPassword));
			if (!isPasswordUpdated) {
				json.put("resData", resJson);
				json.put("errorMessage", CommonConstants.INVALID_REQUEST);
				json.put("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			json.put("resData", resJson);
			json.put("successMessage", CommonConstants.PASSWORD_UPDATED_SUCCESSFULLY);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit method verifyKeyAndChangePassword of UserWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to provide all consumer details.. 1. call
	 * service method to get all consumer details. 2. based on service API data
	 * prepare and send response
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAllConsumerInfo.csv")
	public Response getAllConsumerInfo() throws Exception {
		LOGGER.info("Enter method getAllConsumerInfo of UserWS");
		JSONObject json = new JSONObject();
		JSONObject jsonresponse = new JSONObject();
		JSONArray jArray = new JSONArray();
		try {
			List<UserModel> userModelList = userService.getAllConsumerDetail();
			if (userModelList != null && userModelList.size() > 0) {
				for (UserModel userModel : userModelList) {
					LoginModel loginModel = userService.getLoginDetailByPKey((userModel
							.getRelationKey()));
					JSONObject responseJson = new JSONObject();
					responseJson.put("publicKey", userModel.getRelationKey());
					responseJson.put("firstName", loginModel.getFirstName());
					responseJson.put("lastName", loginModel.getLastName());
					responseJson.put("middleName", loginModel.getMiddleName());
					responseJson.put("emailId", loginModel.getUserName());
					responseJson.put("mobilePhone", userModel.getMobile());
					JSONObject addressJson = new JSONObject();
					addressJson.put("address", userModel.getGlobalAddress());
					addressJson.put("addressLine1", userModel.getAddressLine1());
					addressJson.put("addressLine2", userModel.getAddressLine2());
					addressJson.put("streetNo", userModel.getStreetNo());
					addressJson.put("country", userModel.getCountryName());
					addressJson.put("city", userModel.getCity());
					addressJson.put("state", userModel.getStateName());
					addressJson.put("zipCode", userModel.getZipCode());
					responseJson.put("userAddress", addressJson);
					jArray.put(responseJson);
				}
			}
			jsonresponse.put("userDataList", jArray);
			json.put("resData", jsonresponse);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}// end of try catch block
		LOGGER.info("Exit method getAllConsumerInfo of UserWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to changeProfileImage 1. get required
	 * paparameters from request 2. call service method to save image. 3. based on
	 * service API data prepare and send response
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/changeProfieImage.csv")
	public Response changeProfieImage(JSONObject formPaparam) throws Exception {
		LOGGER.info("Enter method changeProfieImage of UserWS");
		JSONObject resJson = new JSONObject();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "publicKey", "imageName", "imageContent" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				resJson.put("resData", "");
				resJson.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				resJson.putOpt("sCode", CommonConstants.INCOMPLETE);
				resJson.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(resJson.toString()).build();
			}
			String rKey = JSONUtility.getSafeString(jObject, "publicKey");
			String imageName = JSONUtility.getSafeString(jObject, "imageName");
			String imageContent = JSONUtility.getSafeString(jObject, "imageContent");
			if (rKey == null | imageContent == null | imageName == null) {
				resJson.put("resData", new JSONObject());
				resJson.put("errorMessage", CommonConstants.INVALID_REQUEST);
				resJson.put("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(resJson).build();
			}
			UserBean userBean = userService.changeProfieImage(rKey, imageName, imageContent);
			JSONObject json = new JSONObject();
			json.put("imageUrl", userBean.getImageURL());
			resJson.put("resData", json);
			resJson.put("successMessage", CommonConstants.IMAGE_CHANGED);
			resJson.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			resJson.put("errorMessage", e);
			resJson.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(resJson).build();
		}
		LOGGER.info("Exit method changeProfieImage of UserWS");
		return Response.status(200).entity(resJson).build();
	}
	
	/**
	 * This method receives request to changeProfileImage 1. get required
	 * paparameters from request 2. call service method to save image. 3. based on
	 * service API data prepare and send response
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/globalSearch.csv")
	public Response globalSearch(JSONObject formPaparam) throws Exception {
		LOGGER.info("Enter method globalSearch of UserWS");
		JSONObject resJson = new JSONObject();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "publicKey"};
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				resJson.put("resData", "");
				resJson.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				resJson.putOpt("sCode", CommonConstants.INCOMPLETE);
				resJson.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(resJson.toString()).build();
			}
			String publicKey = JSONUtility.getSafeString(jObject, "publicKey");
			String keyword = JSONUtility.getSafeString(jObject, "keyword");
			Integer startRecordNo = JSONUtility.getSafeInteger(jObject, "startRecordNo");
			Integer numberOfRecords = JSONUtility.getSafeInteger(jObject, "numberOfRecords");
			String searchKey = JSONUtility.getSafeString(jObject, "searchKey");
			String sortBy = JSONUtility.getSafeString(jObject, "sortBy");
			String sortOrder = JSONUtility.getSafeString(jObject, "sortOrder");
			if(startRecordNo!=null && numberOfRecords==null){
				resJson.put("resData", "");
				resJson.put("sCode", CommonConstants.INCOMPLETE);
				resJson.put("missingPaparams", "Paparameters missing - numberOfRecords");
				resJson.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(resJson.toString()).build();
			}
			if(sortBy!=null && sortOrder==null){
				resJson.put("resData", "");
				resJson.put("sCode", CommonConstants.INCOMPLETE);
				resJson.put("missingPaparams", "Paparameters missing - sortOrder");
				resJson.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(resJson.toString()).build();
			}
			UserModel userModel = userService.getConsumerDetailByRKey(publicKey);
			if(userModel!=null){
				if(keyword.equals("")){
					resJson.put("itemDetail", "[]");
					resJson.put("collectionDetail", "[]");
					resJson.put("appraisalDetail", "[]");
					resJson.put("storeDetail", "[]");
					resJson.put("agentDetail", "[]");
					resJson.put("sCode", CommonConstants.SUCCESS);
					return Response.status(200).entity(resJson).build();
				}
				List<ValouxItemModel> itemBeanList = itemService.getItemListAssociatedWithUserAndHaveKeyword(publicKey, keyword, startRecordNo, numberOfRecords, sortBy, sortOrder);
				ItemHelper.getItemsDetail(itemService, itemBeanList, resJson,collectionService,appraisalService,userService,false);
				List<ValouxCollectionModel> collectionBeanList = collectionService.getCollectionListAssociatedWithUserAndHaveKeyword(publicKey, keyword, startRecordNo, numberOfRecords, sortBy, sortOrder);
					ItemHelper.getCollectionDetail(itemService, collectionService, collectionBeanList, resJson,appraisalService,userService,false);
				List<AppraisalBean> appraisalBeanList = appraisalService.getAppraisalListAssociatedWithUserAndHaveKeyword(publicKey, keyword, startRecordNo, numberOfRecords, sortBy, sortOrder);
					ItemHelper.getAppraisalDetail(appraisalService, appraisalBeanList, resJson, collectionService, itemService,userService,false);
				List<ValouxStoreModel> storeModelList = storeService.getAllActiveStoreDatawithKeyWord(keyword, startRecordNo, numberOfRecords, sortBy, sortOrder);
					ItemHelper.getStoreDetail(storeModelList, resJson);
				List<LoginModel> loginModelList = userService.getLoginDetailListWithKeyWord(keyword);
					ItemHelper.getAgentDetail(loginModelList, resJson, agentService, storeService);
			}else{
				AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
				if(agentModel!=null){
					if(keyword.equals("")){
						resJson.put("itemDetail", "[]");
						resJson.put("collectionDetail", "[]");
						resJson.put("appraisalDetail", "[]");
						resJson.put("sCode", CommonConstants.SUCCESS);
						return Response.status(200).entity(resJson).build();
					}
					List<ValouxItemModel> itemBeanList = itemService.getItemListAssociatedWithAgentAndHaveKeyword(publicKey, keyword, null, null, null, null);
						ItemHelper.getItemsDetail(itemService, itemBeanList, resJson,collectionService,appraisalService,userService,true);
					List<ValouxCollectionModel> collectionBeanList = collectionService.getCollectionListAssociatedWithAgentAndHaveKeyword(publicKey, keyword, startRecordNo, numberOfRecords, sortBy, sortOrder);
						ItemHelper.getCollectionDetail(itemService, collectionService, collectionBeanList, resJson,appraisalService,userService,true);
					List<AppraisalBean> appraisalBeanList = appraisalService.getAppraisalListAssociatedWithAgentAndHaveKeyword(publicKey, keyword, startRecordNo, numberOfRecords, sortBy, sortOrder);
						ItemHelper.getAppraisalDetail(appraisalService, appraisalBeanList, resJson, collectionService, itemService,userService,true);
					}
			}
			
			resJson.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			resJson.put("errorMessage", e);
			resJson.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(resJson).build();
		}
		LOGGER.info("Exit method globalSearch of UserWS");
		return Response.status(200).entity(resJson).build();
	}
	
	/**
	 * This method receives request to get countries: 1. Call
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
	@Path("/getAllCountryDetails.csv")
	public Response getAllCountryDetails() throws Exception {
		LOGGER.info("Enter method getAllStoreDetail of AgentWS");
		JSONObject json = new JSONObject();
		JSONObject resJson = new JSONObject();
		List<CountryModel> countryModels = userService.getAllCountryDetails();
		JSONArray jsonArray = new JSONArray();
		try {
			if (countryModels != null && countryModels.size() != 0) {
				for (CountryModel countryModel : countryModels) {
					JSONObject responseJson = new JSONObject();
					responseJson.put("countryId",countryModel.getCountryId());
					responseJson.put("countryName",countryModel.getName());
					responseJson.put("countryShortCode",countryModel.getShortCode());
					jsonArray.put(responseJson);
				}
			}
			resJson.put("countryList", jsonArray);
			json.put("resData", resJson);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit method getAllStoreDetail of AgentWS");
		return Response.status(200).entity(json).build();

	} // end of method getAllStoreDetail

	

	/**
	 * This method receives request to perform sharedRequestCount: 1. Check request
	 * for essential paparameters. 2. Converts <code>JSONObject</code> request to
	 * business objects. 3. Calls service method to perform get sharedRequestCount.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/sharedRequestCount.csv")
	public Response sharedRequestCount(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method sharedRequestCount of UserWS");
		JSONObject json = new JSONObject();
		JSONObject resData = new JSONObject();
		//JSONObject sharedJson = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "userPublicKey"};
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String userPublicKey = JSONUtility.getSafeString(jObject, "userPublicKey");
			//if(userPublicKey !=null){
				int sharedRequestCount=0;
				List<ValouxSharedRequestBean> sharedRequestBeanList = itemService.getListOfRequestedSharedItemsToAgent(userPublicKey);
				if(sharedRequestBeanList != null && sharedRequestBeanList.size() > 0){
					sharedRequestCount = sharedRequestBeanList.size();
					resData.put("sharedRequestCount", sharedRequestCount);
					json.put("resData", resData);
					json.putOpt("sCode", CommonConstants.SUCCESS);
				}
		//	}
			else {
				//resData.put("sharedInfo", sharedJson);
				json.put("sharedInfo", "");
				json.put("errorMessage", CommonConstants.LIST_EMPTY);
				json.put("sCode", CommonConstants.SUCCESS);
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}// end of try catch block
		LOGGER.info("Exit Method sharedRequestCount of UserWS");
		return Response.status(200).entity(json.toString()).build();
	} // end of method userLogin
	
	/**
	 * This method receives request to get the user specific by
	 * authcode. 1. Get required auth code from request 2. call service method
	 * to resend One time password to user 3. preapare and send response for
	 * call back
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getUserLoginDetailsByAuthCode.dns")
	@Transactional
	public Response getUserLoginDetailsByAuthCode(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method getUserLoginDetailsByAuthCode of UserWS");
		JSONObject json = new JSONObject();
		JSONObject resData = new JSONObject();
		LoginModel loginModel = new LoginModel();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jsonReqPaparam = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "authLoginCode" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jsonReqPaparam);

			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String authLoginCode = JSONUtility.getSafeString(jsonReqPaparam, "authLoginCode");
			loginModel = userService.getUserLoginDetailsByAuthCode(authLoginCode);
			if (loginModel != null) {
				
				String rKey = (loginModel.getPrivateKey());
				UserBean userBean = userService.getUserDetails(loginModel.getUserName());
				if(userBean == null){
					resData.put("userPublicKey", rKey);
					resData.put("firstName", loginModel.getFirstName());
					resData.put("lastName", loginModel.getLastName());
					resData.put("middleName", loginModel.getMiddleName());
					resData.put("emailId", loginModel.getUserName());
					resData.put("authLoginCode", loginModel.getAuthenticationCode());
					json.put("resData", resData);
					json.putOpt("sCode", CommonConstants.SUCCESS);
				} else {
					json.put("resData", resData);
					json.putOpt("sCode", CommonConstants.SUCCESS);
					json.put("successMessage", CommonConstants.REGISTRATION_DONE_MESSAGE);
				}
				return Response.status(200).entity(json.toString()).build();
			} else {
				json.put("resData", resData);
				json.putOpt("sCode", CommonConstants.ERROR);
				json.put("errorMessage", CommonConstants.INVALID_INVALID_ACTIVATION_KEY);
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}// end of try catch block
		
		LOGGER.info("Enter Method getUserLoginDetailsByAuthCode of UserWS");
		return Response.status(200).entity(json.toString()).build();
	}
	
	/**
	 * This method receives request to get the user specific by
	 * authcode. 1. Get required auth code from request 2. call service method
	 * to resend One time password to user 3. preapare and send response for
	 * call back
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/addUserDetailsViaInvitation.dns")
	public Response addUserDetailsViaInvitation(JSONObject formpaparam, @Context HttpServletRequest request) throws Exception {
		LOGGER.info("Enter Method addUserDetailsByInvitation of UserWS");
		JSONObject json = new JSONObject();
		JSONObject resData = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject registrationData = jObject.optJSONObject("registrationData");
			
			if(registrationData != null){
				String requiredPaparams[] = { "userPublicKey", "emailId", "mobilePhone", "passwordResetQuetion", "passwordResetAnswer",
						"password", "firstName", "lastName" };
				String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, registrationData);
				if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
					json.put("resData", "");
					json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
					json.putOpt("sCode", CommonConstants.INCOMPLETE);
					json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
					return Response.status(200).entity(json.toString()).build();
				} else {
					
					JSONObject fullAddress = registrationData.optJSONObject("userAddress");
					
					if(fullAddress != null){
						requiredPaparams = new String[] { "addressLine1", "city", "state", "country", "zipCode"};
						
						missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, fullAddress);
						if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
							json.put("resData", "");
							json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
							json.putOpt("sCode", CommonConstants.INCOMPLETE);
							json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
							return Response.status(200).entity(json.toString()).build();
						} else {
							UserBean userBean = new UserBean();
							String emailId = JSONUtility.getSafeString(registrationData, "emailId");
							LoginModel loginModel = userService.getLoginDetailByUserName(emailId);
							if(loginModel != null){
								UserHelper.populateLoginModelFromJsonPaparam(loginModel, registrationData);
								LoginBean loginBean = userService.updateUserLoginDetails(loginModel);
								if(loginBean != null){
									UserModel userModel = new UserModel();
									userModel.setIp(request.getRemoteAddr());
									UserHelper.populateUserModelFromJsonPaparam(userService, userModel, registrationData);
									userBean = userService.createUser(userModel);
									userService.sendRegistrationMailToUser(userBean, loginBean);
								}
								json.put("resData", resData);
								json.put("successMessage", CommonConstants.REGISTRATION_COMPLETE_MESSAGE);
								json.put("sCode", CommonConstants.SUCCESS);
							} else {
								json.put("resData", resData);
								json.put("errorMessage", CommonConstants.INVALID_REQUEST);
								json.put("sCode", CommonConstants.ERROR);
							}
						}
					} else {
						json.put("resData", "");
						json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
						json.putOpt("sCode", CommonConstants.INCOMPLETE);
						json.put("missingPaparams", "Paparameters missing - fullAddress");
						return Response.status(200).entity(json.toString()).build();
					}
				}
			} else {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - registrationData");
				return Response.status(200).entity(json.toString()).build();
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}// end of try catch block
		LOGGER.info("Exit Method addUserDetailsByInvitation of UserWS");
		return Response.status(200).entity(json.toString()).build();
	}

	/**
	 * This method receives request to fetch recent activities of user: 1. Check request
	 * for essential paparameters. 2. Converts <code>JSONObject</code> request to
	 * business objects. 3. Calls service method to perform get sharedRequestCount.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/recentActivityForDashboard.csv")
	public Response recentActivityForDashboard(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method sharedRequestCount of UserWS");
		JSONObject json = new JSONObject();
		JSONObject resData = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "userPublicKey"};
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String userPublicKey = JSONUtility.getSafeString(jObject, "userPublicKey");
			LoginModel loginModel = userService.getLoginDetailByPKey((userPublicKey));
			if(loginModel != null) {
				Integer limit = CommonConstants.TOP_FIVE;
				JSONArray jsonArray = new JSONArray();
				JSONArray sortedJsonArray = new JSONArray();
				
				//Fetching item component details
				List<ValouxItemModel> itemModels = itemService.getTopItemsListByUserIdAndLimit(userPublicKey, limit);
				UserHelper.addValouxItemModelList(jsonArray, itemModels);
				
				//Fetching collection component details
				List<ValouxCollectionModel> collectionModels = collectionService.getTopCollectionsListByUserIdAndLimit(userPublicKey, limit);
				UserHelper.addValouxCollectionModelList(jsonArray, collectionModels);
				
				//Fetching appraisal component details
				List<AppraisalModel> appraisalModels = appraisalService.getTopAppraisalsListByUserIdAndLimit(userPublicKey, limit);
				UserHelper.addValouxAppraisalModelList(jsonArray, appraisalModels);
				
				//Fetch json objects from json array
				List<JSONObject> jsonValues = CommonUserUtility.getJsonObjectListFromArray(jsonArray);
				
				//Fetch sorted json array
			    CommonUserUtility.getSortedJsonArrayByObjectKey(jsonArray, sortedJsonArray, jsonValues, CommonConstants.KEY_MODIFIED_ON);
				
				resData.put("dataList", sortedJsonArray);
				json.put("resData", resData);
				json.put("sCode", CommonConstants.SUCCESS);
			} else {
				json.put("resData", resData);
				json.put("errorMessage", CommonConstants.INVALID_REQUEST);
				json.put("sCode", CommonConstants.ERROR);
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}// end of try catch block
		LOGGER.info("Exit Method sharedRequestCount of UserWS");
		return Response.status(200).entity(json.toString()).build();
	} 
	
	
	/**
	 * This method receives request to get delete consumer: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to delete consumer. 4. Prepare response and send it for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deleteConsumerByUserPublicKey.csv")
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
			UserModel userModel = userService.getConsumerDetailByRKey(userPublicKey);
			if(userModel == null){
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.CONSUMER_NOT_FOUND);
				json.putOpt("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			Boolean flag = itemService.deleteConsumerItemAndAllDetails(userPublicKey);
			flag = collectionService.deleteConsumerCollectionAndAllDetails(userPublicKey);
			flag = appraisalService.deleteConsumerAppraisalAndAllDetails(userPublicKey);
			flag = userService.deleteConsumer(userPublicKey);
			if (flag) {
				json.put("resData", "");
				json.putOpt("successMessage", CommonConstants.CONSUMER_DELETED_SUCCESSFULLY);
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

}// end of class
