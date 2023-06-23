/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.transaction.annotation.Transactional;

import com.valoux.bean.AgentBean;
import com.valoux.bean.CountryBean;
import com.valoux.bean.LoginBean;
import com.valoux.bean.LoginLogBean;
import com.valoux.bean.MasterRoleBean;
import com.valoux.bean.StateBean;
import com.valoux.bean.UserAboutBean;
import com.valoux.bean.UserBean;
import com.valoux.bean.UserInterestIn;
import com.valoux.bean.UserJewelryComponentsBean;
import com.valoux.bean.UserPersonalPreferences;
import com.valoux.bean.UserRoleBean;
import com.valoux.bean.UserTypeBean;
import com.valoux.bean.ValouxInterestInBean;
import com.valoux.bean.ValouxOriginBean;
import com.valoux.bean.ValouxPersonalPreferencesBean;
import com.valoux.bean.ValouxSharedRequestBean;
import com.valoux.bean.ValouxStoreBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.AgentDao;
import com.valoux.dao.AppraisalDao;
import com.valoux.dao.CountryDao;
import com.valoux.dao.LoginDao;
import com.valoux.dao.LoginLogDao;
import com.valoux.dao.StateDao;
import com.valoux.dao.UserAboutDao;
import com.valoux.dao.UserDao;
import com.valoux.dao.UserInterestInDao;
import com.valoux.dao.UserJewelryComponentsDao;
import com.valoux.dao.UserPersonalPreferencesDao;
import com.valoux.dao.UserRoleDao;
import com.valoux.dao.UserTypeDao;
import com.valoux.dao.ValouxInterestInDao;
import com.valoux.dao.ValouxOriginDao;
import com.valoux.dao.ValouxPersonalPreferencesDao;
import com.valoux.dao.ValouxSharedRequestDao;
import com.valoux.enums.UserRegistratonEnums;
import com.valoux.model.CountryModel;
import com.valoux.model.LoginModel;
import com.valoux.model.UserModel;
import com.valoux.model.UserRoleModel;
import com.valoux.model.ValouxInterestInModel;
import com.valoux.model.ValouxPersonalPreferencesModel;
import com.valoux.service.UserService;
import com.valoux.util.CommonMailUtility;
import com.valoux.util.CommonUserUtility;
import com.valoux.util.CommonUtility;
import com.valoux.util.MailApi;
import com.valoux.util.SendOTPSms;

/**
 * This <java>class</java> UserServiceImpl use to perform all our service
 * related logics for the user.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	LoginDao loginDao;
	
	@Autowired
	AgentDao  agentDao;
	@Autowired
	UserPersonalPreferencesDao userPersonalPreferencesDao;
	@Autowired
	UserInterestInDao userInterestInDao;
	@Autowired
	UserAboutDao  userAboutDao;
	@Autowired
	UserJewelryComponentsDao  userJewelryComponentsDao;
	@Autowired
	StateDao stateDao;
	@Autowired
	CountryDao  countryDao;
	@Autowired
	UserTypeDao  userTypeDao;
	@Autowired
	UserRoleDao  userRoleDao;
	@Autowired
	ValouxInterestInDao valouxInterestInDao;
	@Autowired
	ValouxPersonalPreferencesDao  valouxPersonalPreferencesDao;
	@Autowired
	LoginLogDao  loginLogDao;
	@Autowired
	ValouxOriginDao  valouxOriginDao;
	@Autowired
	ValouxSharedRequestDao valouxSharedRequestDao;
	@Autowired
	AppraisalDao appraisalDao;


	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

	/**
	 * This method performs check Login Request.
	 */
	@Transactional
	public LoginBean checkLoginRequest(LoginBean loginBean) throws Exception {
		LOGGER.debug("Enter Method checkLoginRequest of UserServiceImpl");
		loginBean = loginDao.checkUserCredentials(loginBean);
		LOGGER.debug("Exit Method checkLoginRequest of UserServiceImpl");
		return loginBean;
	}

	/**
	 * This method performs check EmailId. This method implements logic to check
	 * if an user with same email address already exist. It calls data access
	 * layer to check email in persisting data.
	 */
	@Transactional
	public boolean checkEmailAlreadyRegistered(String email) throws Exception {
		LOGGER.debug("Enter Method checkEmailAlreadyRegistered of UserServiceImpl");
		boolean isEmailExist = false;
		isEmailExist = loginDao.checkEmailAlreadyRegistered(email);
		LOGGER.debug("Exit Method checkEmailAlreadyRegistered of UserServiceImpl");
		return isEmailExist;
	}

	/**
	 * This method creates User.
	 * 
	 * @throws JSONException
	 * @throws NumberFormatException
	 */
	@Transactional
	public UserBean createUser(UserModel userModel) throws Exception {
		LOGGER.debug("Enter Method createUser of UserServiceImpl");
		
		UserBean userBean = null;
		UserBean bean = userDao.getUserDetails(userModel.getEmailId());
		if(bean == null){
			userBean = CommonUserUtility.prepareUserBeanFromModel(userModel);
			userBean = userDao.createUser(userBean);
			userInterestedIn(userModel, userBean);
			userPersonalPreferences(userModel, userBean);
			userAbout(userModel, userBean);
			jewelryComponents(userModel, userBean);
		}
		LOGGER.debug("Exit Method createUser of UserServiceImpl");
		return userBean;
	}

	private void userPersonalPreferences(UserModel userModel, UserBean userBean) throws JSONException, Exception {
		LOGGER.debug("Enter Method userPersonalPreferences of UserServiceImpl");
		JSONArray jewelryTypes = userModel.getJewelryTypes();
		List<UserPersonalPreferences> userPersonalPreferencesList = new ArrayList<UserPersonalPreferences>();
		UserPersonalPreferences personalPreferences = null;
		ValouxPersonalPreferencesBean valouxPersonalPreferencesBean = null;
		if (jewelryTypes != null) {
			for (int z = 0; z < jewelryTypes.length(); z++) {
				personalPreferences = new UserPersonalPreferences();
				valouxPersonalPreferencesBean = new ValouxPersonalPreferencesBean();
				valouxPersonalPreferencesBean.setPersonalId((Integer) jewelryTypes.get(z));
				personalPreferences.setValouxPersonalPreferencesBean(valouxPersonalPreferencesBean);
//				personalPreferences.setPreferencesId((Integer) jewelryTypes.get(z));
				personalPreferences.setRelationKey(userBean.getRelationKey());
				personalPreferences.setCreatedOn(CommonUtility.getDateAndTime());
				personalPreferences.setCreatedBy(userBean.getRelationKey());// to
																			// be
																			// update
				personalPreferences.setModifiedOn(CommonUtility.getDateAndTime());
				personalPreferences.setModifiedBy(userBean.getRelationKey());
				userPersonalPreferencesList.add(personalPreferences);
			}
		}
		JSONArray jewelryDesign = userModel.getJewelryDesign();
		if (jewelryDesign != null) {
			for (int z = 0; z < jewelryDesign.length(); z++) {
				personalPreferences = new UserPersonalPreferences();
				valouxPersonalPreferencesBean = new ValouxPersonalPreferencesBean();
				valouxPersonalPreferencesBean.setPersonalId((Integer) jewelryDesign.get(z));
				personalPreferences.setValouxPersonalPreferencesBean(valouxPersonalPreferencesBean);
				//personalPreferences.setPreferencesId((Integer) jewelryDesign.get(z));
				personalPreferences.setRelationKey(userBean.getRelationKey());
				personalPreferences.setCreatedOn(CommonUtility.getDateAndTime());
				personalPreferences.setCreatedBy(userBean.getRelationKey());// to
																			// be
																			// update
				personalPreferences.setModifiedOn(CommonUtility.getDateAndTime());
				personalPreferences.setModifiedBy(userBean.getRelationKey());
				userPersonalPreferencesList.add(personalPreferences);
			}
		}
		JSONArray jewelryStyle = userModel.getJewelryStyle();
		if (jewelryStyle != null) {
			for (int z = 0; z < jewelryStyle.length(); z++) {
				personalPreferences = new UserPersonalPreferences();
				valouxPersonalPreferencesBean = new ValouxPersonalPreferencesBean();
				valouxPersonalPreferencesBean.setPersonalId((Integer) jewelryStyle.get(z));
				personalPreferences.setValouxPersonalPreferencesBean(valouxPersonalPreferencesBean);
//				personalPreferences.setPreferencesId((Integer) jewelryStyle.get(z));
				personalPreferences.setRelationKey(userBean.getRelationKey());
				personalPreferences.setCreatedOn(CommonUtility.getDateAndTime());
				personalPreferences.setCreatedBy(userBean.getRelationKey());// to
																			// be
																			// update
				personalPreferences.setModifiedOn(CommonUtility.getDateAndTime());
				personalPreferences.setModifiedBy(userBean.getRelationKey());
				userPersonalPreferencesList.add(personalPreferences);
			}
		}
		userPersonalPreferencesDao.savePersonalPreferencesForUser(userPersonalPreferencesList);
		LOGGER.debug("Exit Method userPersonalPreferences of UserServiceImpl");
	}

	private void userInterestedIn(UserModel userModel, UserBean userBean) throws JSONException, Exception {
		LOGGER.debug("Enter Method userInterestedIn of UserServiceImpl");
		if (userModel.getInterestedIn() != null && userBean != null) {
			JSONArray interestedInJsonArray = userModel.getInterestedIn();
			List<UserInterestIn> userInterestInList = new ArrayList<UserInterestIn>();

			for (int i = 0; i < interestedInJsonArray.length(); i++) {
				int interestedIn = Integer.parseInt(interestedInJsonArray.getString(i));
				UserInterestIn userInterestIn = new UserInterestIn();
				userInterestIn.setRelationKey(userBean.getRelationKey());
				ValouxInterestInBean valouxInterestIn = new ValouxInterestInBean();
				valouxInterestIn.setInterestId(interestedIn);
				userInterestIn.setValouxInterestIn(valouxInterestIn);
//				userInterestIn.setInterestIn(interestedIn);
				userInterestIn.setCreatedOn(CommonUtility.getDateAndTime());
				userInterestIn.setCreatedBy(userBean.getRelationKey());// to be
																		// update
				userInterestIn.setModifiedOn(CommonUtility.getDateAndTime());
				userInterestIn.setModifiedBy(userBean.getRelationKey());
				userInterestInList.add(userInterestIn);

			}
			userInterestInDao.saveInterestedInForUser(userInterestInList);
		}
		LOGGER.debug("Exit Method userInterestedIn of UserServiceImpl");
	}

	private void userAbout(UserModel userModel, UserBean userBean) throws JSONException, Exception {
		LOGGER.debug("Enter Method userAbout of UserServiceImpl");
		JSONArray jewelryPurchases = userModel.getJewelryPurchases();
		List<UserAboutBean> userAboutList = new ArrayList<UserAboutBean>();
		UserAboutBean userAbout = null;
		if (jewelryPurchases != null) {
			for (int z = 0; z < jewelryPurchases.length(); z++) {
				userAbout = new UserAboutBean();
				userAbout.setAboutText((Integer) jewelryPurchases.get(z));
				userAbout.setRelationKey(userBean.getRelationKey());
				userAbout.setAboutType(CommonConstants.USER_JEWELLERY_PURCHASES);
				userAbout.setStatus(CommonConstants.USER_STATUS_INACTIVE);
				userAbout.setCreatedOn(CommonUtility.getDateAndTime());
				userAbout.setCreatedBy(userBean.getRelationKey());// to be
																	// update
				userAbout.setModifiedOn(CommonUtility.getDateAndTime());
				userAbout.setModifiedBy(userBean.getRelationKey());
				userAboutList.add(userAbout);
			}
		}
		JSONArray jewelryInsurance = userModel.getJewelryInsurance();
		if (jewelryInsurance != null) {
			userAbout = new UserAboutBean();
			for (int z = 0; z < jewelryInsurance.length(); z++) {
				userAbout.setAboutText((Integer) jewelryInsurance.get(z));
				userAbout.setRelationKey(userBean.getRelationKey());
				userAbout.setAboutType(CommonConstants.USER_JEWELLERY_INSURANCE);
				userAbout.setStatus(CommonConstants.USER_STATUS_INACTIVE);
				userAbout.setCreatedOn(CommonUtility.getDateAndTime());
				userAbout.setCreatedBy(userBean.getRelationKey());// to be
																	// update
				userAbout.setModifiedOn(CommonUtility.getDateAndTime());
				userAbout.setModifiedBy(userBean.getRelationKey());
				userAboutList.add(userAbout);
			}
		}
		JSONArray jewelryService = userModel.getJewelryService();
		if (jewelryService != null) {
			userAbout = new UserAboutBean();
			for (int z = 0; z < jewelryService.length(); z++) {
				userAbout.setAboutText((Integer) jewelryService.get(z));
				userAbout.setRelationKey(userBean.getRelationKey());
				userAbout.setAboutType(CommonConstants.USER_JEWELLERY_SERVICE);
				userAbout.setStatus(CommonConstants.USER_STATUS_INACTIVE);
				userAbout.setCreatedOn(CommonUtility.getDateAndTime());
				userAbout.setCreatedBy(userBean.getRelationKey());// to be
																	// update
				userAbout.setModifiedOn(CommonUtility.getDateAndTime());
				userAbout.setModifiedBy(userBean.getRelationKey());
				userAboutList.add(userAbout);
			}
		}
		JSONArray jewelryDocumentation = userModel.getJewelryDocumentation();
		if (jewelryDocumentation != null) {
			userAbout = new UserAboutBean();
			for (int z = 0; z < jewelryDocumentation.length(); z++) {
				userAbout.setAboutText((Integer) jewelryDocumentation.get(z));
				userAbout.setRelationKey(userBean.getRelationKey());
				userAbout.setAboutType(CommonConstants.USER_JEWELLERY_DOCUMENTATION);
				userAbout.setStatus(CommonConstants.USER_STATUS_INACTIVE);
				userAbout.setCreatedOn(CommonUtility.getDateAndTime());
				userAbout.setCreatedBy(userBean.getRelationKey());// to be
																	// update
				userAbout.setModifiedOn(CommonUtility.getDateAndTime());
				userAbout.setModifiedBy(userBean.getRelationKey());
				userAboutList.add(userAbout);
			}
		}

		userAboutDao.saveUserAboutForUser(userAboutList);
		LOGGER.debug("Exit Method userAbout of UserServiceImpl");
	}

	private void jewelryComponents(UserModel userModel, UserBean userBean) throws JSONException, Exception {
		LOGGER.debug("Enter Method jewelryComponents of UserServiceImpl");
		JSONArray metals = userModel.getMetals();
		List<UserJewelryComponentsBean> JewelryComponentsList = new ArrayList<UserJewelryComponentsBean>();
		UserJewelryComponentsBean userJewelryComponentsBean = null;
		if (metals != null) {
			for (int z = 0; z < metals.length(); z++) {
				userJewelryComponentsBean = new UserJewelryComponentsBean();
				userJewelryComponentsBean.setComponents((Integer) metals.get(z));
				userJewelryComponentsBean.setRelationKey(userBean.getRelationKey());
				userJewelryComponentsBean.setComponentsType(CommonConstants.USER_JEWELLERY_METALS);
				userJewelryComponentsBean.setStatus(CommonConstants.USER_STATUS_INACTIVE);
				userJewelryComponentsBean.setCreatedOn(CommonUtility.getDateAndTime());
				userJewelryComponentsBean.setCreatedBy(userBean.getRelationKey());// to
																					// be
																					// update
				userJewelryComponentsBean.setModifiedOn(CommonUtility.getDateAndTime());
				userJewelryComponentsBean.setModifiedBy(userBean.getRelationKey());
				JewelryComponentsList.add(userJewelryComponentsBean);
			}
		}
		JSONArray gemstones = userModel.getGemstones();
		if (gemstones != null) {
			for (int z = 0; z < gemstones.length(); z++) {
				userJewelryComponentsBean = new UserJewelryComponentsBean();
				userJewelryComponentsBean.setComponents((Integer) gemstones.get(z));
				userJewelryComponentsBean.setRelationKey(userBean.getRelationKey());
				userJewelryComponentsBean.setComponentsType(CommonConstants.USER_JEWELLERY_GEMSTONES);
				userJewelryComponentsBean.setStatus(CommonConstants.USER_STATUS_INACTIVE);
				userJewelryComponentsBean.setCreatedOn(CommonUtility.getDateAndTime());
				userJewelryComponentsBean.setCreatedBy(userBean.getRelationKey());// to
																					// be
																					// update
				userJewelryComponentsBean.setModifiedOn(CommonUtility.getDateAndTime());
				userJewelryComponentsBean.setModifiedBy(userBean.getRelationKey());
				JewelryComponentsList.add(userJewelryComponentsBean);
			}
		}
		JSONArray diamonds = userModel.getDiamonds();
		if (diamonds != null) {
			for (int z = 0; z < diamonds.length(); z++) {
				userJewelryComponentsBean = new UserJewelryComponentsBean();
				userJewelryComponentsBean.setComponents((Integer) diamonds.get(z));
				userJewelryComponentsBean.setRelationKey(userBean.getRelationKey());
				userJewelryComponentsBean.setComponentsType(CommonConstants.USER_JEWELLERY_DIAMONDS);
				userJewelryComponentsBean.setStatus(CommonConstants.USER_STATUS_INACTIVE);
				userJewelryComponentsBean.setCreatedOn(CommonUtility.getDateAndTime());
				userJewelryComponentsBean.setCreatedBy(userBean.getRelationKey());// to
																					// be
																					// update
				userJewelryComponentsBean.setModifiedOn(CommonUtility.getDateAndTime());
				userJewelryComponentsBean.setModifiedBy(userBean.getRelationKey());
				JewelryComponentsList.add(userJewelryComponentsBean);
			}
		}

		userJewelryComponentsDao.saveJewelryComponentsForUser(JewelryComponentsList);
		LOGGER.debug("Exit Method jewelryComponents of UserServiceImpl");
	}

	/**
	 * This method creates User For Login.
	 */
	@Transactional
	public LoginBean saveLoginInfo(LoginModel loginModel) throws Exception {
		LOGGER.debug("Enter Method saveLoginInfo of UserServiceImpl");
		LoginBean loginBean = CommonUserUtility.prepareUserLoginBeanFromModel(loginModel);
		loginBean = loginDao.createUserInLogin(loginBean);
		LOGGER.debug("Exit Method saveLoginInfo of UserServiceImpl");
		return loginBean;
	}

	/**
	 * This method creates User For Login.
	 */
	@Transactional
	public Integer getStateIdentifier(String stateName, Integer countryId) throws Exception {
		LOGGER.debug("in getStateId method of UserServiceImpl");
		List<StateBean> stateBeanList = stateDao.getStateId(stateName);
		if (stateBeanList != null && stateBeanList.size() > 0) {
			LOGGER.debug("in if block of getStateId method of UserServiceImpl");
			return CommonUserUtility.prepareStateModelFromBean(stateBeanList.get(0)).getStateId();
		} else {
			LOGGER.debug("in else block of getStateId method of UserServiceImpl");
			StateBean stateBean = stateDao.saveState(CommonUserUtility.prepareStateBeanFromModel(stateName, countryId));
			return stateBean.getStateId();
		}
	}

	/**
	 * This method performs get Country Id.
	 */
	@Transactional
	public Integer getCountryId(String countryName) throws Exception {
		LOGGER.debug("Enter method getCountryId of UserServiceImpl");
		List<CountryBean> countryBeanList = countryDao.getCountryId(countryName);
		if (countryBeanList != null && countryBeanList.size() > 0) {
			return countryBeanList.get(0).getCountryId();
		} else {
			CountryBean countryBean = CommonUserUtility.prepareCountryBean(countryName);
			countryDao.saveCountry(countryBean);
			LOGGER.debug("Exit method getCountryId of UserServiceImpl");
			return countryBean.getCountryId();
		}
	}

	/**
	 * This method performs check New Genarated Relation Key.
	 */
	@Transactional
	public UserBean checkRelationKeyAlreadyExist(String relationKey) throws Exception {
		LOGGER.debug("Enter method checkRelationKeyAlreadyExist of UserServiceImpl");
		UserBean UserRelationKey = null;
		UserRelationKey = userDao.checkNewGenaratedRelationKey(relationKey);
		LOGGER.debug("Exit method checkRelationKeyAlreadyExist of UserServiceImpl");
		return UserRelationKey;
	}

	/**
	 * This method performs get Relational Key.
	 */
	@Transactional
	public UserBean getUserDetails(String userName) throws Exception {
		LOGGER.debug("Enter method getUserDetails of UserServiceImpl");
		UserBean UserRelationKey = null;
		UserRelationKey = userDao.getUserDetails(userName);
		LOGGER.debug("Exit method getUserDetails of UserServiceImpl");
		return UserRelationKey;

	}

	/**
	 * This method creates User Type.
	 */
	@Transactional
	public UserTypeBean createUserType(UserTypeBean userTypeBean) throws Exception {
		LOGGER.debug("Enter method createUserType of UserServiceImpl");
		UserTypeBean userBean = null;
		userBean = userTypeDao.createUserType(userTypeBean);
		LOGGER.debug("Exit method createUserType of UserServiceImpl");
		return userBean;

	}

	/**
	 * This method creates User Role.
	 */
	@Transactional
	public UserRoleBean createUserRole(UserRoleBean userRoleBean) throws Exception {
		LOGGER.debug("Enter method createUserRole of UserServiceImpl");
		UserRoleBean userBean = null;
		userBean = userRoleDao.createUserRole(userRoleBean);
		LOGGER.debug("Exit method createUserRole of UserServiceImpl");
		return userBean;

	}

	/**
	 * This method performs update User Detail.
	 */
	@Transactional
	public UserBean updateUserDetail(UserModel userModel) throws Exception {
		LOGGER.debug("Enter method updateUserDetail of UserServiceImpl");
		UserBean userBean = userDao.getUserDetailByUserId(userModel.getCustomerId());
		userDao.updateUser(userBean);
		LOGGER.debug("Exit method updateUserDetail of UserServiceImpl");
		return userBean;
	}

	/**
	 * This method performs get User InterestIn.
	 */
	@Transactional
	public List<ValouxInterestInModel> getUserInterestIn() throws Exception {
		LOGGER.debug("Enter Method getUserInterestIn of UserServiceImpl");
		List<ValouxInterestInBean> interestInBeanList = valouxInterestInDao.getUserInterestIn();
		List<ValouxInterestInModel> interestInModelList = new ArrayList<ValouxInterestInModel>();
		if (interestInBeanList != null && interestInBeanList.size() != 0) {
			for (ValouxInterestInBean interestInBean : interestInBeanList) {
				ValouxInterestInModel interestInModel = CommonUserUtility.convertInterestInbeanToModel(interestInBean);
				interestInModelList.add(interestInModel);
			}
		}
		Comparator<ValouxInterestInModel> comparator = new Comparator<ValouxInterestInModel>() {

			public int compare(ValouxInterestInModel o1, ValouxInterestInModel o2) {
				return o1.getInterest().compareTo(o2.getInterest());
			}
		};
		Collections.sort(interestInModelList, comparator);
		LOGGER.debug("Exit Method getUserInterestIn of UserServiceImpl");
		return interestInModelList;
	}

	/**
	 * This method performs get User InterestIn map.
	 * 
	 * @throws Exception
	 */
	@Transactional
	public Map<Integer, String> getUserInterestInMap() throws Exception {
		LOGGER.debug("Enter Method getUserInterestInMap of UserServiceImpl");
		List<ValouxInterestInBean> interestInBeanList = valouxInterestInDao.getUserInterestIn();
		Map<Integer, String> interestInBeanMap = new HashMap<Integer, String>();
		if (interestInBeanList != null && interestInBeanList.size() != 0) {
			for (ValouxInterestInBean interestInBean : interestInBeanList) {
				interestInBeanMap.put(interestInBean.getInterestId(), interestInBean.getInterest());
			}
		}
		LOGGER.debug("Exit Method getUserInterestInMap of UserServiceImpl");
		return interestInBeanMap;
	}

	/**
	 * This method performs get All Personal Preferences.
	 */
	@Transactional
	public List<ValouxPersonalPreferencesModel> getAllPersonalPreferences() throws Exception {
		LOGGER.debug("Enter Method getAllPersonalPreferences of UserServiceImpl");
		List<ValouxPersonalPreferencesBean> ppBeanList = valouxPersonalPreferencesDao.getAllPersonalPreferences();
		List<ValouxPersonalPreferencesModel> ppModelList = new ArrayList<ValouxPersonalPreferencesModel>();
		if (ppBeanList != null && ppBeanList.size() != 0) {
			for (ValouxPersonalPreferencesBean ppBean : ppBeanList) {
				ValouxPersonalPreferencesModel ppModel = CommonUserUtility.ConvertPPBeanToModel(ppBean);
				ppModelList.add(ppModel);
			}
		}
		Comparator<ValouxPersonalPreferencesModel> comparator = new Comparator<ValouxPersonalPreferencesModel>() {

			public int compare(ValouxPersonalPreferencesModel o1, ValouxPersonalPreferencesModel o2) {
				return o1.getPersonalPreferences().compareTo(o2.getPersonalPreferences());
			}
		};
		Collections.sort(ppModelList, comparator);
		LOGGER.debug("Exit Method getAllPersonalPreferences of UserServiceImpl");
		return ppModelList;
	}

	/**
	 * This method performs get All Personal Preferences.
	 * 
	 * @throws Exception
	 */
	@Transactional
	public Map<Integer, String> getAllPersonalPreferencesMap() throws Exception {
		LOGGER.debug("Enter Method getAllPersonalPreferencesMap of UserServiceImpl");
		List<ValouxPersonalPreferencesBean> ppBeanList = valouxPersonalPreferencesDao.getAllPersonalPreferences();
		Map<Integer, String> allPreferenceMap = new HashMap<Integer, String>();
		if (ppBeanList != null && ppBeanList.size() != 0) {
			for (ValouxPersonalPreferencesBean ppBean : ppBeanList) {
				allPreferenceMap.put(ppBean.getPersonalId(), ppBean.getPersonalPreferences());
			}
		}
		LOGGER.debug("Exit Method getAllPersonalPreferencesMap of UserServiceImpl");
		return allPreferenceMap;
	}

	/**
	 * This method performs check And Activate User.
	 */
	@Transactional
	public boolean checkAndActivatUser(String activationKey, String otp) throws Exception {
		LOGGER.debug("Enter Method checkAndActivatUser of UserServiceImpl");
		boolean checkAndActivatUser = false;
		LoginBean userRegistrationDetailBean = loginDao.checkUserForActivation(activationKey, otp);
		if(userRegistrationDetailBean!=null){
		if (userRegistrationDetailBean.getUserStatus().equals(CommonConstants.USER_STATUS_INACTIVE)) {
			userRegistrationDetailBean.setUserStatus(CommonConstants.USER_STATUS_ACTIVE);
			
			AgentBean agentBean = agentDao.getAgentDetailByRelationKey((userRegistrationDetailBean.getPrivateKey()));
			if(agentBean!=null){
				userRegistrationDetailBean.setUserStatus(CommonConstants.AGENT_STATUS_EMAIL_VERIFIED);
			}
			userRegistrationDetailBean.setModifiedOn(new Date());
			userRegistrationDetailBean.setAuthCodeMobile("");
			userRegistrationDetailBean.setAuthenticationCode("");
			loginDao.updateLoginBean(userRegistrationDetailBean);
			checkAndActivatUser=true;
		}
		}
		LOGGER.debug("Exit Method checkAndActivatUser of UserServiceImpl");
		return checkAndActivatUser;
	}

	/**
	 * This method performs get Consumer Detail By RKey.
	 */
	@Transactional
	public UserModel getConsumerDetailByRKey(String rKey) throws Exception {
		LOGGER.debug("Enter Method getConsumerDetailByRKey of UserServiceImpl");
		UserBean userBean = userDao.getConsumerDetailByRKey(rKey);
		Map<Integer, String> interestInFullMap = getUserInterestInMap();
		// Map<Integer, String> ppFullMap = getAllPersonalPreferencesMap();
		UserModel userModel = null;
		if (userBean != null) {
			userModel = CommonUserUtility.prepareUserModelFromBean(userBean);
			if (userBean.getCountryId() != null) {
				userModel.setCountryName(countryDao.getCountryNameByCountryId(userBean.getCountryId()).getName());
			}
			if (userBean.getStateId() != null) {
				userModel.setStateName(stateDao.getStateNameByStateId(userBean.getStateId()).getName());
			}
			List<UserInterestIn> userInterestInList = userInterestInDao.getUserInterestInDetailByRKey(rKey);
			if (userInterestInList != null && userInterestInList.size() > 0) {
				JSONArray jArray = new JSONArray();
				for (UserInterestIn userInterestIn : userInterestInList) {

					JSONObject object = new JSONObject();
					object.put("interestdId", userInterestIn.getValouxInterestIn().getInterestId());
					object.put("interestedName", interestInFullMap.get(userInterestIn.getValouxInterestIn().getInterestId()));
					jArray.put(object);
				}
				userModel.setInterestedIn(jArray);
			}
			List<UserPersonalPreferences> userPreferenceList = userPersonalPreferencesDao.getUserPreferencesByRKey(rKey);
			if (userPreferenceList != null && userPreferenceList.size() > 0) {
				JSONArray jArray = new JSONArray();
				for (UserPersonalPreferences userPreferences : userPreferenceList) {

					ValouxPersonalPreferencesBean personalPreferencesBean = valouxPersonalPreferencesDao
							.getUserPreferencesPreferencesId(userPreferences.getValouxPersonalPreferencesBean().getPersonalId());

					JSONObject object = new JSONObject();
					object.put("personalId", userPreferences.getValouxPersonalPreferencesBean().getPersonalId());
					object.put("personalName", personalPreferencesBean.getPersonalPreferences());
					object.put("personalType", personalPreferencesBean.getPpType());
					jArray.put(object);
				}
				userModel.setPersonalPreferences(jArray);
			}

			List<UserAboutBean> userAboutList = userAboutDao.getUserAboutByRKey(rKey);
			if (userAboutList != null && userAboutList.size() > 0) {
				JSONArray jArray = new JSONArray();
				for (UserAboutBean userAbout : userAboutList) {

					JSONObject object = new JSONObject();
					object.put("userAbout", userAbout.getAboutText());
					object.put("aboutType", userAbout.getAboutType());
					jArray.put(object);
				}
				userModel.setConsumerAbout(jArray);
			}

			List<UserJewelryComponentsBean> jewelryComponents = userJewelryComponentsDao.getJewelryComponentsByRKey(rKey);
			if (jewelryComponents != null && jewelryComponents.size() > 0) {
				JSONArray jArray = new JSONArray();
				for (UserJewelryComponentsBean jewelryComponentsBean : jewelryComponents) {

					JSONObject object = new JSONObject();
					object.put("components", jewelryComponentsBean.getComponents());
					object.put("componentsType", jewelryComponentsBean.getComponentsType());
					jArray.put(object);
				}
				userModel.setJewelryComponents(jArray);
			}

		}
		LOGGER.debug("Exit Method getConsumerDetailByRKey of UserServiceImpl");
		return userModel;
	}

	/**
	 * This method performs get Login Detail By PKey.
	 */
	@Transactional
	public LoginModel getLoginDetailByPKey(String pKey) throws Exception {
		LOGGER.debug("Enter Method getLoginDetailByPKey of UserServiceImpl");
		LoginModel loginModel = null;
		LoginBean loginBean = loginDao.getLoginDetailByPKey(pKey);
		if (loginBean != null) {
			loginModel = CommonUserUtility.prepareloginModelFromBean(loginBean);
		}
		LOGGER.debug("Exit Method getLoginDetailByPKey of UserServiceImpl");
		return loginModel;
	}

	/**
	 * This method creates User type And user Role For Agent.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	@Transactional
	public void createUsertypeAnduserRoleForAgent(ValouxStoreBean storeBean, AgentBean agentbean,
			MasterRoleBean masterRoleBean) throws Exception {
		LOGGER.debug("Enter method createUsertypeAnduserRoleForAgent of UserServiceImpl");
		UserTypeBean userTypeBean = new UserTypeBean();
		UserRoleBean userRoleBean = new UserRoleBean();
		if (storeBean != null) {
			userTypeBean.setRelationKey(agentbean.getRelationKey());
			userTypeBean.setUserType(UserRegistratonEnums.UsertType.Agent.getType());

			userTypeBean.setCreatedOn(CommonUtility.getDateAndTime());
			userTypeBean.setCreatedBy(agentbean.getRelationKey());// to be
																	// update
			userTypeBean.setModifiedBy(agentbean.getRelationKey());
			userTypeBean.setModifiedOn(CommonUtility.getDateAndTime());
			userTypeBean = createUserType(userTypeBean);
		}

		if (userTypeBean != null) {
			userRoleBean.setRelationKey(userTypeBean.getRelationKey());
			userRoleBean.setMasterRoleBean(masterRoleBean);
//			userRoleBean.setRoleId(masterRoleBean.getRoleId());
//			userRoleBean.setUserTypeId(userTypeBean.getUserTypeId());
			userRoleBean.setUserTypeBean(userTypeBean);

			userRoleBean.setCreatedOn(CommonUtility.getDateAndTime());
			userRoleBean.setCreatedBy(agentbean.getRelationKey());// to be
																	// update
			userRoleBean.setModifiedBy(agentbean.getRelationKey());
			userRoleBean.setModifiedOn(CommonUtility.getDateAndTime());
			userRoleBean = createUserRole(userRoleBean);
		}

		LOGGER.debug("Exit method createUsertypeAnduserRoleForAgent of UserServiceImpl");
	}

	/**
	 * This method creates User Type And User Role For Consumer.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	@Transactional
	public void createUserTypeAndUserRoleForConsumer(UserBean userBean, LoginBean loginBean,
			MasterRoleBean masterRoleBean) throws Exception {
		LOGGER.debug("Enter method createUserTypeAndUserRoleForConsumer of UserServiceImpl");
		UserTypeBean userTypeBean = new UserTypeBean();
		UserRoleBean userRoleBean = new UserRoleBean();
		if (userBean != null) {
			userTypeBean.setRelationKey(userBean.getRelationKey());
			userTypeBean.setUserType(UserRegistratonEnums.UsertType.Customer.getType());

			userTypeBean.setCreatedOn(userBean.getCreatedOn());
			userTypeBean.setCreatedBy(userBean.getRelationKey());// to be update
			userTypeBean.setModifiedBy(userBean.getRelationKey());
			userTypeBean.setModifiedOn(userBean.getModifiedOn());
			userTypeBean = createUserType(userTypeBean);
		}
		if (userTypeBean != null) {
			userRoleBean.setRelationKey(userTypeBean.getRelationKey());
//			userRoleBean.setRoleId(masterRoleBean.getRoleId());
			userRoleBean.setMasterRoleBean(masterRoleBean);
//			userRoleBean.setUserTypeId(userTypeBean.getUserTypeId());
			userRoleBean.setUserTypeBean(userTypeBean);

			userRoleBean.setCreatedOn(userBean.getCreatedOn());
			userRoleBean.setCreatedBy(userBean.getRelationKey());// to be update
			userRoleBean.setModifiedBy(userBean.getRelationKey());
			userRoleBean.setModifiedOn(userBean.getModifiedOn());
			userRoleBean = createUserRole(userRoleBean);
		}

		String from = CommonConstants.SIGN_UP_MAIL;
		StringBuffer sub = new StringBuffer();
		
		if(userBean != null){
			if(CommonUserUtility.paparameterNullCheckStringObject(userBean.getMobile())){
				sub.append(CommonConstants.CONSUMER_BODY);
				String body = CommonMailUtility.getUserRegistrationMailContent(loginBean);
				
				MailApi.SendMail(userBean.getEmailId(), from, sub.toString(), body,
						UserRegistratonEnums.UsertType.Customer.getType());

				String message = "Hello" + " ! your OTP is: " + loginBean.getAuthCodeMobile();

				if(CommonUserUtility.paparameterNullCheckStringObject(userBean.getMobile())){
					boolean isSend = SendOTPSms.sendOTP(message, userBean.getMobile());
					if (isSend) {
						LOGGER.debug("OTP sent successfully");
					}
				}
			} else {
				sub.append(CommonConstants.CONSUMER_INVITE_BODY);
				String body = CommonMailUtility.getUserInvitationRegistrationMailContent(loginBean);
				
				MailApi.SendMail(userBean.getEmailId(), from, sub.toString(), body,
						UserRegistratonEnums.UsertType.Customer.getType());
			}
		}
		LOGGER.debug("Exit method createUserTypeAndUserRoleForConsumer of UserServiceImpl");
	}

	/**
	 * This method performs convert Login Model Into Bean For Consumer Update.
	 */
	@Transactional
	public void convertLoginModelIntoBeanForConsumer(LoginBean loginBean, LoginModel loginModel) throws Exception{
		LOGGER.debug("Enter method convertLoginModelIntoBeanForConsumerUpdate of UserServiceImpl");
		if (loginModel.getFirstName() != null) {
			loginBean.setFirstName(loginModel.getFirstName());
		}
		if (loginModel.getLastName() != null) {
			loginBean.setLastName(loginModel.getLastName());
		}
		if (loginModel.getMiddleName() != null) {
			loginBean.setMiddleName(loginModel.getMiddleName());
		}
		if (loginModel.getModifiedBy() != null) {
			loginBean.setModifiedBy(loginModel.getModifiedBy());
		}
		if (CommonUserUtility.paparameterNullCheckObject(loginModel.getUserStatus())) {
			loginBean.setUserStatus(loginModel.getUserStatus());
		}
		if (CommonUserUtility.paparameterNullCheckObject(loginModel.getPassword())) {
			loginBean.setPassword(loginModel.getPassword());
		}
		loginBean.setModifiedOn(CommonUtility.getDateAndTime());
		LOGGER.debug("Exit method convertLoginModelIntoBeanForConsumerUpdate of UserServiceImpl");
	}

	/**
	 * This method performs convert User Model IntoBean For Consumer Update.
	 */
	@Transactional
	public void convertUserModelIntoBeanForConsumer(UserBean userBean, UserModel userModel) {
		LOGGER.debug("Enter method convertUserModelIntoBeanForConsumerUpdate of UserServiceImpl");
		if (userModel.getGlobalAddress() != null) {
			userBean.setGlobalAddress(userModel.getGlobalAddress());
		}
		if (userModel.getStreetNo() != null) {
			userBean.setStreetNo(userModel.getStreetNo());
		}
		if (userModel.getAddressLine1() != null) {
			userBean.setAddressLine1(userModel.getAddressLine1());
		}
		if (userModel.getAddressLine2() != null) {
			userBean.setAddressLine2(userModel.getAddressLine2());
		}
		if (userModel.getCity() != null) {
			userBean.setCity(userModel.getCity());
		}
		if (userModel.getCountryId() != null) {
			userBean.setCountryId(userModel.getCountryId());
		}
		if (userModel.getStateId() != null) {
			userBean.setStateId(userModel.getStateId());
		}
		if (userModel.getZipCode() != null) {
			userBean.setZipCode(userModel.getZipCode());
		}
		if (userModel.getDateOfBirth() != null) {
			userBean.setDateOfBirth(userModel.getDateOfBirth());
		} else {
			userBean.setDateOfBirth(null);
		}
		if (userModel.getGender() != null) {
			userBean.setGender(userModel.getGender());
		}
		if (userModel.getMaritalStatus() != null) {
			userBean.setMaritalStatus(userModel.getMaritalStatus());
		}
		if (userModel.getIncomeRange() != null) {
			userBean.setIncomeRange(userModel.getIncomeRange());
		}
		if (userModel.getMobile() != null) {
			userBean.setMobile(userModel.getMobile());
		}
		if (userModel.getModifiedBy() != null) {
			userBean.setModifiedBy(userModel.getModifiedBy());
		}
		if (userModel.getModifiedOn() != null) {
			userBean.setModifiedOn(userModel.getModifiedOn());
		}
		userBean.setIp(userModel.getIp());
		LOGGER.debug("Exit method convertUserModelIntoBeanForConsumerUpdate of UserServiceImpl");
	}

	/**
	 * This method performs update Consumer Detail.
	 * 
	 * @throws JSONException
	 * @throws NumberFormatException
	 */
	@Transactional
	public void updateConsumerDetail(UserModel userModel, LoginModel loginModel) throws Exception {
		LOGGER.debug("Enter method updateConsumerDetail of UserServiceImpl");
		UserBean userBean = userDao.getConsumerDetailByRKey(userModel.getRelationKey());
		LoginBean loginBean = loginDao.getLoginDetailByPKey(loginModel.getPrivateKey());
		convertLoginModelIntoBeanForConsumer(loginBean, loginModel);
		loginDao.updateLoginDetail(loginBean);
		convertUserModelIntoBeanForConsumer(userBean, userModel);
		userDao.updateUser(userBean);
		UpdateUserInterestedIn(userModel, userBean);
		UpdateUserPersonalPreferences(userModel, userBean);
		updateUserAbout(userModel, userBean);
		updateJewelryComponents(userModel, userBean);

		LOGGER.debug("Exit method updateConsumerDetail of UserServiceImpl");
	}

	private void UpdateUserPersonalPreferences(UserModel userModel, UserBean userBean) throws JSONException, Exception {
		LOGGER.debug("Enter method UpdateUserPersonalPreferences of UserServiceImpl");
		JSONArray jewelryTypes = userModel.getJewelryTypes();
		List<UserPersonalPreferences> userPersonalPreferencesList = new ArrayList<UserPersonalPreferences>();
		UserPersonalPreferences personalPreferences = null;
		ValouxPersonalPreferencesBean valouxPersonalPreferencesBean = null;
		if (jewelryTypes != null) {
			for (int z = 0; z < jewelryTypes.length(); z++) {
				personalPreferences = new UserPersonalPreferences();
				valouxPersonalPreferencesBean = new ValouxPersonalPreferencesBean();
				valouxPersonalPreferencesBean.setPersonalId((Integer) jewelryTypes.get(z));
				personalPreferences.setValouxPersonalPreferencesBean(valouxPersonalPreferencesBean);
				
//				personalPreferences.setPreferencesId((Integer) jewelryTypes.get(z));
				personalPreferences.setRelationKey(userBean.getRelationKey());
				personalPreferences.setCreatedOn(CommonUtility.getDateAndTime());
				personalPreferences.setCreatedBy(userBean.getRelationKey());// to
																			// be
																			// update
				personalPreferences.setModifiedOn(CommonUtility.getDateAndTime());
				personalPreferences.setModifiedBy(userBean.getRelationKey());// to
																				// be
																				// update
				userPersonalPreferencesList.add(personalPreferences);
			}
		}
		JSONArray jewelryDesign = userModel.getJewelryDesign();
		if (jewelryDesign != null) {
			for (int z = 0; z < jewelryDesign.length(); z++) {
				personalPreferences = new UserPersonalPreferences();
				valouxPersonalPreferencesBean = new ValouxPersonalPreferencesBean();
				valouxPersonalPreferencesBean.setPersonalId((Integer) jewelryDesign.get(z));
				personalPreferences.setValouxPersonalPreferencesBean(valouxPersonalPreferencesBean);
//				personalPreferences.setPreferencesId((Integer) jewelryDesign.get(z));
				personalPreferences.setRelationKey(userBean.getRelationKey());
				personalPreferences.setCreatedOn(CommonUtility.getDateAndTime());
				personalPreferences.setCreatedBy(userBean.getRelationKey());// to
																			// be
																			// update
				personalPreferences.setModifiedOn(CommonUtility.getDateAndTime());
				personalPreferences.setModifiedBy(userBean.getRelationKey());// to
																				// be
																				// update
				userPersonalPreferencesList.add(personalPreferences);
			}
		}
		JSONArray jewelryStyle = userModel.getJewelryStyle();
		if (jewelryStyle != null) {
			for (int z = 0; z < jewelryStyle.length(); z++) {
				personalPreferences = new UserPersonalPreferences();
				valouxPersonalPreferencesBean = new ValouxPersonalPreferencesBean();
				valouxPersonalPreferencesBean.setPersonalId((Integer) jewelryStyle.get(z));
				personalPreferences.setValouxPersonalPreferencesBean(valouxPersonalPreferencesBean);
//				personalPreferences.setPreferencesId((Integer) jewelryStyle.get(z));
				personalPreferences.setRelationKey(userBean.getRelationKey());
				personalPreferences.setCreatedOn(CommonUtility.getDateAndTime());
				personalPreferences.setCreatedBy(userBean.getRelationKey());// to
																			// be
																			// update
				personalPreferences.setModifiedOn(CommonUtility.getDateAndTime());
				personalPreferences.setModifiedBy(userBean.getRelationKey());// to
																				// be
																				// update
				userPersonalPreferencesList.add(personalPreferences);
			}
		}

		userPersonalPreferencesDao.deleteUserPreferenceDetail(userBean.getRelationKey());
		userPersonalPreferencesDao.savePersonalPreferencesForUser(userPersonalPreferencesList);
		LOGGER.debug("Exit method UpdateUserPersonalPreferences of UserServiceImpl");
	}

	private void UpdateUserInterestedIn(UserModel userModel, UserBean userBean) throws JSONException, Exception {
		LOGGER.debug("Enter method UpdateUserInterestedIn of UserServiceImpl");
		if (userModel.getInterestedIn() != null && userBean != null) {
			JSONArray interestedInJsonArray = userModel.getInterestedIn();
			List<UserInterestIn> userInterestInList = new ArrayList<UserInterestIn>();

			for (int i = 0; i < interestedInJsonArray.length(); i++) {
				int interestedIn = Integer.parseInt(interestedInJsonArray.getString(i));
				UserInterestIn userInterestIn = new UserInterestIn();
				userInterestIn.setRelationKey(userBean.getRelationKey());
				userInterestIn.setCreatedOn(CommonUtility.getDateAndTime());
				userInterestIn.setCreatedBy(userBean.getRelationKey());// to be
								
				// update
				ValouxInterestInBean valouxInterestIn = new ValouxInterestInBean();
				valouxInterestIn.setInterestId(interestedIn);
				userInterestIn.setValouxInterestIn(valouxInterestIn);
//				userInterestIn.setInterestIn(interestedIn);
				userInterestIn.setModifiedOn(CommonUtility.getDateAndTime());
				userInterestIn.setModifiedBy(userBean.getRelationKey());// to be
																		// update
				userInterestInList.add(userInterestIn);

			}
			userInterestInDao.deleteUserInterestInDetail(userBean.getRelationKey());
			userInterestInDao.saveInterestedInForUser(userInterestInList);
		}
		LOGGER.debug("Exit method UpdateUserInterestedIn of UserServiceImpl");
	}

	private void updateUserAbout(UserModel userModel, UserBean userBean) throws JSONException, Exception {
		LOGGER.debug("Enter method updateUserAbout of UserServiceImpl");
		JSONArray jewelryPurchases = userModel.getJewelryPurchases();
		List<UserAboutBean> userAboutList = new ArrayList<UserAboutBean>();
		UserAboutBean userAbout = null;
		if (jewelryPurchases != null) {
			for (int z = 0; z < jewelryPurchases.length(); z++) {
				userAbout = new UserAboutBean();
				userAbout.setAboutText((Integer) jewelryPurchases.get(z));
				userAbout.setRelationKey(userBean.getRelationKey());
				userAbout.setAboutType(CommonConstants.USER_JEWELLERY_PURCHASES);
				userAbout.setStatus(CommonConstants.USER_STATUS_INACTIVE);
				userAbout.setCreatedOn(CommonUtility.getDateAndTime());
				userAbout.setCreatedBy(userBean.getRelationKey());// to be
																	// update
				userAbout.setModifiedOn(CommonUtility.getDateAndTime());
				userAbout.setModifiedBy(userBean.getRelationKey());// to be
																	// update
				userAboutList.add(userAbout);
			}
		}
		JSONArray jewelryInsurance = userModel.getJewelryInsurance();
		if (jewelryInsurance != null) {
			userAbout = new UserAboutBean();
			for (int z = 0; z < jewelryInsurance.length(); z++) {
				userAbout.setAboutText((Integer) jewelryInsurance.get(z));
				userAbout.setRelationKey(userBean.getRelationKey());
				userAbout.setAboutType(CommonConstants.USER_JEWELLERY_INSURANCE);
				userAbout.setStatus(CommonConstants.USER_STATUS_INACTIVE);
				userAbout.setCreatedOn(CommonUtility.getDateAndTime());
				userAbout.setCreatedBy(userBean.getRelationKey());// to be
																	// update
				userAbout.setModifiedOn(CommonUtility.getDateAndTime());
				userAbout.setModifiedBy(userBean.getRelationKey());// to be
																	// update
				userAboutList.add(userAbout);
			}
		}
		JSONArray jewelryService = userModel.getJewelryService();
		if (jewelryService != null) {
			userAbout = new UserAboutBean();
			for (int z = 0; z < jewelryService.length(); z++) {
				userAbout.setAboutText((Integer) jewelryService.get(z));
				userAbout.setRelationKey(userBean.getRelationKey());
				userAbout.setAboutType(CommonConstants.USER_JEWELLERY_SERVICE);
				userAbout.setStatus(CommonConstants.USER_STATUS_INACTIVE);
				userAbout.setCreatedOn(CommonUtility.getDateAndTime());
				userAbout.setCreatedBy(userBean.getRelationKey());// to be
																	// update
				userAbout.setModifiedOn(CommonUtility.getDateAndTime());
				userAbout.setModifiedBy(userBean.getRelationKey());// to be
																	// update
				userAboutList.add(userAbout);
			}
		}
		JSONArray jewelryDocumentation = userModel.getJewelryDocumentation();
		if (jewelryDocumentation != null) {
			userAbout = new UserAboutBean();
			for (int z = 0; z < jewelryDocumentation.length(); z++) {
				userAbout.setAboutText((Integer) jewelryDocumentation.get(z));
				userAbout.setRelationKey(userBean.getRelationKey());
				userAbout.setAboutType(CommonConstants.USER_JEWELLERY_DOCUMENTATION);
				userAbout.setStatus(CommonConstants.USER_STATUS_INACTIVE);
				userAbout.setCreatedOn(CommonUtility.getDateAndTime());
				userAbout.setCreatedBy(userBean.getRelationKey());// to be
																	// update
				userAbout.setModifiedOn(CommonUtility.getDateAndTime());
				userAbout.setModifiedBy(userBean.getRelationKey());// to be
																	// update
				userAboutList.add(userAbout);
			}
		}

		userAboutDao.deleteUserAboutDetail(userBean.getRelationKey());
		userAboutDao.saveUserAboutForUser(userAboutList);
		LOGGER.debug("Exit method updateUserAbout of UserServiceImpl");
	}

	private void updateJewelryComponents(UserModel userModel, UserBean userBean) throws JSONException, Exception {
		LOGGER.debug("Enter method updateJewelryComponents of UserServiceImpl");
		JSONArray metals = userModel.getMetals();
		List<UserJewelryComponentsBean> JewelryComponentsList = new ArrayList<UserJewelryComponentsBean>();
		UserJewelryComponentsBean userJewelryComponentsBean = null;
		if (metals != null) {
			for (int z = 0; z < metals.length(); z++) {
				userJewelryComponentsBean = new UserJewelryComponentsBean();
				userJewelryComponentsBean.setComponents((Integer) metals.get(z));
				userJewelryComponentsBean.setRelationKey(userBean.getRelationKey());
				userJewelryComponentsBean.setComponentsType(CommonConstants.USER_JEWELLERY_METALS);
				userJewelryComponentsBean.setStatus(CommonConstants.USER_STATUS_INACTIVE);
				userJewelryComponentsBean.setCreatedOn(CommonUtility.getDateAndTime());
				userJewelryComponentsBean.setCreatedBy(userBean.getRelationKey());// to
																					// be
																					// update
				userJewelryComponentsBean.setModifiedOn(CommonUtility.getDateAndTime());
				userJewelryComponentsBean.setModifiedBy(userBean.getRelationKey());// to
																					// be
																					// update
				JewelryComponentsList.add(userJewelryComponentsBean);
			}
		}
		JSONArray gemstones = userModel.getGemstones();
		if (gemstones != null) {
			for (int z = 0; z < gemstones.length(); z++) {
				userJewelryComponentsBean = new UserJewelryComponentsBean();
				userJewelryComponentsBean.setComponents((Integer) gemstones.get(z));
				userJewelryComponentsBean.setRelationKey(userBean.getRelationKey());
				userJewelryComponentsBean.setComponentsType(CommonConstants.USER_JEWELLERY_GEMSTONES);
				userJewelryComponentsBean.setStatus(CommonConstants.USER_STATUS_INACTIVE);
				userJewelryComponentsBean.setCreatedOn(CommonUtility.getDateAndTime());
				userJewelryComponentsBean.setCreatedBy(userBean.getRelationKey());// to
																					// be
																					// update
				userJewelryComponentsBean.setModifiedOn(CommonUtility.getDateAndTime());
				userJewelryComponentsBean.setModifiedBy(userBean.getRelationKey());// to
																					// be
																					// update
				JewelryComponentsList.add(userJewelryComponentsBean);
			}
		}
		JSONArray diamonds = userModel.getDiamonds();
		if (diamonds != null) {
			for (int z = 0; z < diamonds.length(); z++) {
				userJewelryComponentsBean = new UserJewelryComponentsBean();
				userJewelryComponentsBean.setComponents((Integer) diamonds.get(z));
				userJewelryComponentsBean.setRelationKey(userBean.getRelationKey());
				userJewelryComponentsBean.setComponentsType(CommonConstants.USER_JEWELLERY_DIAMONDS);
				userJewelryComponentsBean.setStatus(CommonConstants.USER_STATUS_INACTIVE);
				userJewelryComponentsBean.setCreatedOn(CommonUtility.getDateAndTime());
				userJewelryComponentsBean.setCreatedBy(userBean.getRelationKey());// to
																					// be
																					// update
				userJewelryComponentsBean.setModifiedOn(CommonUtility.getDateAndTime());
				userJewelryComponentsBean.setModifiedBy(userBean.getRelationKey());// to
																					// be
																					// update
				JewelryComponentsList.add(userJewelryComponentsBean);
			}
		}

		userJewelryComponentsDao.deleteJewelryComponentsDetail(userBean.getRelationKey());
		userJewelryComponentsDao.saveJewelryComponentsForUser(JewelryComponentsList);
		LOGGER.debug("Exit method updateJewelryComponents of UserServiceImpl");
	}

	/**
	 * This method performs get Roll.
	 */
	@Transactional
	public UserRoleBean getRole(String relationKey) throws Exception {
		LOGGER.debug("Enter method getRole of UserServiceImpl");
		UserRoleBean userBean = null;
		userBean = userRoleDao.getRole(relationKey);
		LOGGER.debug("Exit method getRole of UserServiceImpl");
		return userBean;
	}

	/**
	 * This method performs get User Type.
	 */
	@Transactional
	public UserTypeBean getUserType(String relationKey) throws Exception {
		LOGGER.debug("Enter method getUserType of UserServiceImpl");
		UserTypeBean userBean = null;
		userBean = userTypeDao.getUserType(relationKey);
		LOGGER.debug("Exit method getUserType of UserServiceImpl");
		return userBean;

	}

	/**
	 * This method creates Login Logs.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	@Transactional
	public LoginLogBean createLoginLogs(LoginLogBean loginLogBean) throws Exception {
		LOGGER.debug("Enter Method createLoginLogs of UserServiceImpl");
		LoginLogBean loginLoBean = new LoginLogBean();
		loginLoBean = loginLogDao.createLoginLogs(loginLogBean);
		if (loginLoBean.getAuthLoginCode() != null) {
			String rKey = loginLoBean.getRelationKey();
			String pKey = null;
			pKey = (rKey);

			LoginBean loginBean = loginDao.getLoginDetailByPKey(pKey);
			String from = CommonConstants.SIGN_UP_MAIL;
			StringBuffer sub = new StringBuffer();
			sub.append(CommonConstants.WELCOME);
			String body = CommonMailUtility.getAgentLoginMailContent(loginBean, loginLoBean);
			MailApi.SendMail(loginBean.getUserName(), from, sub.toString(), body,
					UserRegistratonEnums.UsertType.Agent.getType());

		}
		LOGGER.debug("Exit Method createLoginLogs of UserServiceImpl");
		return loginLoBean;
	}

	/**
	 * This method performs resend OTP.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	@Transactional
	public LoginBean resendOTP(String authLoginCode) throws Exception {
		LOGGER.debug("Enter Method resendOTP of UserServiceImpl");
		LoginBean loginBean = null;
		loginBean = loginDao.resendOTP(authLoginCode);
		if (loginBean != null) {
			String authCodeMobile = String.valueOf(CommonUtility.generateRandom(6));
			loginBean.setAuthCodeMobile(authCodeMobile);
			loginDao.updateLoginDetail(loginBean);
			String rKey = (loginBean.getPrivateKey());
			String mobNo = null;
			UserBean userBean = userDao.getConsumerDetailByRKey(rKey);
			if (userBean != null) {
				mobNo = userBean.getMobile();
			} else {
				AgentBean agentBean = agentDao.getAgentDetailByRKey(rKey);
				mobNo = agentBean.getMobile();
			}
			String message = "Hello" + " ! your OTP is: " + loginBean.getAuthCodeMobile();

			boolean isSend = SendOTPSms.sendOTP(message, mobNo);
			if (isSend) {
				LOGGER.debug("OTP sent successfully");
			}
		}
		LOGGER.debug("Exit Method resendOTP of UserServiceImpl");
		return loginBean;
	}

	/**
	 * This method performs update Agent Login detail.
	 */
	@Transactional
	public LoginBean updateAgentLogindetail(LoginModel loginModel) throws Exception {
		LOGGER.debug("Enter method updateAgentLogindetail of UserServiceImpl");
		// LoginBean loginBeanResonse = new LoginBean();
		LoginBean loginBean = loginDao.getLoginDetailByPKey(loginModel.getPrivateKey());
		convertLoginModelIntoBeanForConsumer(loginBean, loginModel);
		loginBean = loginDao.updateLoginDetail(loginBean);
		LOGGER.debug("Exit method updateAgentLogindetail of UserServiceImpl");
		return loginBean;

	}

	/**
	 * this metod first check if the old password is correct then update the new
	 * password
	 */
	@Transactional
	public boolean changePassword(LoginModel loginModel, String newPassword) throws Exception {
		LOGGER.debug("Enter method changePassword of UserServiceImpl");
		LoginBean loginBean = loginDao.getLoginDetailByPKey(loginModel.getPrivateKey());
		if (loginBean == null || !(loginBean.getPassword().equals(loginModel.getPassword()))) {
			return false;
		} else {
			loginBean.setPassword(newPassword);
			loginBean.setModifiedBy(loginModel.getModifiedBy());
			loginBean.setModifiedOn(loginModel.getModifiedOn());
			loginDao.updateLoginDetail(loginBean);
			return true;
		}
	}

	/**
	 * This method gets the security question by email id
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	@Transactional
	public String getSecurityQuestionByEmailid(String emailId) throws Exception {
		LOGGER.debug("Enter method changePassword of getSecurityQuestionByEmailid of UserServiceImpl");
		LoginBean loginBean = loginDao.getLoginBeanByEmailid(emailId);
		if (loginBean != null) {
			UserBean userBean = userDao.getConsumerDetailByRKey((loginBean.getPrivateKey()));
			if (userBean != null) {
				return userBean.getPasswordResetQuetion();
			} else {
				AgentBean agentBean = agentDao.getAgentDetailByRelationKey((loginBean
						.getPrivateKey()));
				if (agentBean != null) {
					return agentBean.getPasswordResetQuetion();
				}
			}
		}

		LOGGER.debug("Enter method changePassword of getSecurityQuestionByEmailid of UserServiceImpl");
		return null;
	}

	/**
	 * This method verifies security answer and then send mail to the user/agent
	 * to reset password
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	@Transactional
	public Boolean verifySecurityAnswer(String emailId, String securityQuestion, String securityAnswer)
			throws Exception {
		LOGGER.debug("Enter method verifySecurityAnswer of UserServiceImpl");
		UserBean userBean = null;
		AgentBean agentBean = null;
		LoginBean loginBean = loginDao.getLoginBeanByEmailid(emailId);
		if (loginBean != null) {
			userBean = userDao.getConsumerDetailByRKey((loginBean.getPrivateKey()));
			if (userBean != null) {
				if (!userBean.getPasswordResetAnswer().equalsIgnoreCase(securityAnswer)) {
					return false;
				}
			} else {
				agentBean = agentDao.getAgentDetailByRelationKey((loginBean.getPrivateKey()));
				if (agentBean != null) {
					if (!agentBean.getPasswordResetAnswer().equalsIgnoreCase(securityAnswer)) {
						return false;
					}
				}
			}
			if (agentBean != null || userBean != null) {
				String from = CommonConstants.SIGN_UP_MAIL;
				StringBuffer sub = new StringBuffer();
				sub.append(CommonConstants.RESET_PASSWORD);

				String body = CommonMailUtility.getForgetPasswordMailContent(loginBean);
				if (agentBean != null) {
					MailApi.SendMail(agentBean.getEmailId(), from, sub.toString(), body,
							UserRegistratonEnums.UsertType.Agent.getType());
				} else {
					MailApi.SendMail(userBean.getEmailId(), from, sub.toString(), body,
							UserRegistratonEnums.UsertType.Customer.getType());
				}
				return true;
			}

		}

		LOGGER.debug("Enter method verifySecurityAnswer of UserServiceImpl");
		return false;
	}

	/**
	 * This method update the new password if forgetpassword key matches and
	 * then update forgetpassword key as well
	 * 
	 * @throws Exception
	 */
	@Transactional
	public Boolean updatePasswordAndForgetPasswordKey(String forgetPasswordKey, String newPassword) throws Exception {
		LOGGER.debug("Enter method updatePasswordAndForgetPasswordKey of UserServiceImpl");
		LoginBean loginBean = loginDao.getLoginBeanByForgetPasswordKey(forgetPasswordKey);
		if (loginBean != null) {
			loginBean.setPassword(newPassword);
			loginBean.setForgetPasswordKey(CommonUtility.generatetToken());
			loginBean = loginDao.updateLoginDetail(loginBean);
			return true;
		}
		LOGGER.debug("Exit method updatePasswordAndForgetPasswordKey of UserServiceImpl");
		return false;
	}

	/**
	 * This method get all consumer detail
	 */
	@Transactional
	public List<UserModel> getAllConsumerDetail() throws Exception {
		LOGGER.debug("Enter method getAllConsumerDetail of UserServiceImpl");
		List<UserModel> userModelList = new ArrayList<UserModel>();
		List<UserBean> userBeanList = userDao.getAllConsumerDetail();
		if (userBeanList != null && userBeanList.size() > 0) {
			for (UserBean userBean : userBeanList) {
				UserModel userModel = CommonUserUtility.prepareUserModelFromBean(userBean);
				userModel = CommonUserUtility.prepareUserModelFromBean(userBean);
				if (userBean.getCountryId() != null) {
					userModel.setCountryName(countryDao.getCountryNameByCountryId(userBean.getCountryId()).getName());
				}
				if (userBean.getStateId() != null) {
					userModel.setStateName(stateDao.getStateNameByStateId(userBean.getStateId()).getName());
				}
				userModelList.add(userModel);
			}
		}
		LOGGER.debug("Exit method getAllConsumerDetail of UserServiceImpl");
		return userModelList;
	}

	/**
	 * This method change the consumer profile image
	 */
	@Transactional
	public UserBean changeProfieImage(String publicKey, String imageName, String imageContent) throws Exception {
		LOGGER.debug("Enter method changeProfieImage of UserServiceImpl");
		String imageToDelete = null;
		UserBean userBean = userDao.getConsumerDetailByRKey(publicKey);
		imageToDelete = userBean.getImageURL();
		if (imageToDelete != null) {
			CommonUtility.deleteConsumerImageInDirectory(imageToDelete);
		}
		String profilePath = CommonUtility.saveConsumerImage(imageContent, imageName, "Consumer_Profile_", userBean
				.getCustomerId().toString());
		userBean.setImageURL(profilePath);
		userBean.setModifiedOn(CommonUtility.getDateAndTime());
		userDao.updateUser(userBean);
		LOGGER.debug("Enter method changeProfieImage of UserServiceImpl");
		return userBean;
	}

	/*
	 * This method get login detail by user name
	 */
	@Transactional
	public LoginModel getLoginDetailByUserName(String emailId) throws Exception {
		LOGGER.debug("Enter method getLoginDetailByUserName of UserServiceImpl");
		LoginBean loginBean = loginDao.getLoginBeanByEmailid(emailId);
		if (loginBean != null) {
			return CommonUserUtility.prepareloginModelFromBean(loginBean);
		}
		LOGGER.debug("Exit method getLoginDetailByUserName of UserServiceImpl");
		return null;
	}

	/*
	 * This method return all country details
	 */
	@Transactional
	public List<CountryModel> getAllCountryDetails() throws Exception {
		LOGGER.debug("Enter method getAllCountryDetails of UserServiceImpl");
		
		List<CountryModel> countryModels = new ArrayList<CountryModel>();
		List<ValouxOriginBean> valouxOriginBeans = valouxOriginDao.getAllCountryOriginDetails();
		
		if(valouxOriginBeans != null && valouxOriginBeans.size()>0) {
			for (ValouxOriginBean valouxOriginBean : valouxOriginBeans) {
				CountryModel countryModel = new CountryModel();
				countryModel.setCountryId(valouxOriginBean.getId());
				countryModel.setName(valouxOriginBean.getNicename());
				countryModel.setShortCode(valouxOriginBean.getIso3());
				countryModels.add(countryModel);
			}
		}
		LOGGER.debug("Exit method getAllCountryDetails of UserServiceImpl");
		return countryModels;
	}
	/*
	 * This method retrieves Login Info by auth code.
	 */
	@Transactional
	public LoginModel getUserLoginDetailsByAuthCode(String authLoginCode)
			throws Exception {
		
		LOGGER.debug("Enter method getUserLoginDetailsByAuthCode of UserServiceImpl");
		LoginBean loginBean = loginDao.getUserLoginDetailsByAuthCode(authLoginCode);
		
		LoginModel loginModel = null;
		if(loginBean != null) {
			loginModel = new LoginModel();
			loginModel.setUserName(loginBean.getUserName());
			loginModel.setPassword(loginBean.getPassword());
			loginModel.setFirstName(loginBean.getFirstName());
			loginModel.setMiddleName(loginBean.getMiddleName());
			loginModel.setLastName(loginBean.getLastName());
			loginModel.setAuthenticationCode(loginBean.getAuthenticationCode());
			loginModel.setPrivateKey(loginBean.getPrivateKey());
		}
		LOGGER.debug("Exit method getUserLoginDetailsByAuthCode of UserServiceImpl");
		return loginModel;
	}

	/*
	 * This method used to send registration mail.
	 */
	@Transactional
	public void sendRegistrationMailToUser(UserBean userBean,
			LoginBean loginBean) throws Exception {
		LOGGER.debug("Enter method sendRegistrationMailToUser of UserServiceImpl");
		String from = CommonConstants.SIGN_UP_MAIL;
		StringBuffer sub = new StringBuffer();
		sub.append(CommonConstants.CONSUMER_BODY);

		String body = CommonMailUtility.getUserRegistrationMailContent(loginBean);
		MailApi.SendMail(userBean.getEmailId(), from, sub.toString(), body,
				UserRegistratonEnums.UsertType.Customer.getType());

		String message = "Hello" + " ! your OTP is: " + loginBean.getAuthCodeMobile();

		if(CommonUserUtility.paparameterNullCheckStringObject(userBean.getMobile())){
			boolean isSend = SendOTPSms.sendOTP(message, userBean.getMobile());
			if (isSend) {
				LOGGER.debug("OTP sent successfully");
			}
		}
		LOGGER.debug("Exit method sendRegistrationMailToUser of UserServiceImpl");
	}

	/*
	 * This method used to update user login details
	 */
	@Transactional
	public LoginBean updateUserLoginDetails(LoginModel loginModel)
			throws Exception {
		LOGGER.debug("Enter method updateUserLoginDetails of UserServiceImpl");
		LoginBean loginBean = loginDao.getLoginBeanByEmailid(loginModel.getUserName());
		if(loginBean != null){
			convertLoginModelIntoBeanForConsumer(loginBean, loginModel);
			loginDao.updateLoginDetail(loginBean);
		}
		LOGGER.debug("Enter method updateUserLoginDetails of UserServiceImpl");
		return loginBean;
	}
	
	/*
	 * This method get all login data having keyword
	 */
	@Transactional
	public List<LoginModel> getLoginDetailListWithKeyWord(String keyword) throws Exception {
		LOGGER.debug("Enter method getLoginDetailListWithKeyWord of UserServiceImpl");
		List<LoginModel> loginModelList = new ArrayList<LoginModel>();
		List<LoginBean> loginBeanList = loginDao.getAllLoginDataWithKeyword(keyword);
		if(loginBeanList!=null && loginBeanList.size()>0){
			for(LoginBean loginBean : loginBeanList){
				loginModelList.add(CommonUserUtility.prepareloginModelFromBean(loginBean));
			}
		}
		
		LOGGER.debug("Exit method getLoginDetailListWithKeyWord of UserServiceImpl");
		return loginModelList;
	}
	
	/**
	 * This method delete consumer
	 */
	@Transactional
	public Boolean deleteConsumer(String userPublicKey) throws Exception
	{	
		LOGGER.debug("Enter method deleteConsumer of UserServiceImpl");
		UserBean userBean = userDao.getConsumerDetailByRKey(userPublicKey);
		if(userBean !=null){
			
			
			LoginBean loginBean = loginDao.getLoginDetailByPKey((userPublicKey));
			if(loginBean!=null){
				loginDao.deleteAnyBeanByObject(loginBean);
			}
			UserTypeBean userTypeBean = userTypeDao.getUserType(userPublicKey);
			if(userTypeBean!=null){
				loginDao.deleteAnyBeanByObject(userTypeBean);
			}
			
			userInterestInDao.deleteUserInterestInDetail(userBean.getRelationKey());

			userAboutDao.deleteUserAboutDetail(userBean.getRelationKey());

			userJewelryComponentsDao.deleteJewelryComponentsDetail(userBean.getRelationKey());

			userPersonalPreferencesDao.deleteUserPreferenceDetail(userBean.getRelationKey());
			
			UserRoleBean userRoleBean = userRoleDao.getRole(userPublicKey);
			if(userRoleBean!=null){
				loginDao.deleteAnyBeanByObject(userRoleBean);
			}
			
			List<ValouxSharedRequestBean> sharedRequestBeans = valouxSharedRequestDao.getAllBeanSharedByUser(userPublicKey);
			if(sharedRequestBeans != null && sharedRequestBeans.size() > 0){
				for (ValouxSharedRequestBean valouxSharedRequestBean : sharedRequestBeans) {
					appraisalDao.deleteAnyBeanByObject(valouxSharedRequestBean);
				}
			}
			List<ValouxSharedRequestBean> sharedRequestBeansNew = valouxSharedRequestDao.getAllBeanSharedToUser(userPublicKey);
			if(sharedRequestBeansNew != null && sharedRequestBeansNew.size() > 0){
				for (ValouxSharedRequestBean valouxSharedRequestBeanNew : sharedRequestBeansNew) {
					appraisalDao.deleteAnyBeanByObject(valouxSharedRequestBeanNew);
				}
			}
			userDao.deleteAnyBeanByObject(userBean);
			return true; 
		}
		LOGGER.debug("Exit method deleteConsumer of UserServiceImpl");
		return false;
	}

	@Transactional
	public UserRoleModel getRoleModel(String relationKey) throws Exception {
		LOGGER.debug("Enter method getRoleModel of UserServiceImpl");
		UserRoleBean userBean = userRoleDao.getRole(relationKey);
		UserRoleModel roleModel = new UserRoleModel();
		if(userBean != null){
			roleModel.setUserRoleId(userBean.getUserRoleId());
			roleModel.setRelationKey(userBean.getRelationKey());
			if(userBean.getMasterRoleBean() != null){
				roleModel.setMasterRoleBeanId(userBean.getMasterRoleBean().getRoleId());
			}
			
			if(userBean.getUserTypeBean() != null){
				roleModel.setUserTypeBeanId(userBean.getUserTypeBean().getUserTypeId());
			}
		}
		LOGGER.debug("Exit method getRoleModel of UserServiceImpl");
		return roleModel;
	}
}
