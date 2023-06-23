/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.transaction.annotation.Transactional;

import com.valoux.bean.AgentBean;
import com.valoux.bean.LoginBean;
import com.valoux.bean.MasterRoleBean;
import com.valoux.bean.UserBean;
import com.valoux.bean.UserRoleBean;
import com.valoux.bean.UserTypeBean;
import com.valoux.bean.ValouxAgentStoreBean;
import com.valoux.bean.ValouxSharedRequestBean;
import com.valoux.bean.ValouxStoreAdvertisementBean;
import com.valoux.bean.ValouxStoreBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.AgentDao;
import com.valoux.dao.AppraisalDao;
import com.valoux.dao.CountryDao;
import com.valoux.dao.LoginDao;
import com.valoux.dao.LoginLogDao;
import com.valoux.dao.MasterRoleDao;
import com.valoux.dao.StateDao;
import com.valoux.dao.UserDao;
import com.valoux.dao.UserRoleDao;
import com.valoux.dao.UserTypeDao;
import com.valoux.dao.ValouxAgentStoreDao;
import com.valoux.dao.ValouxSharedRequestDao;
import com.valoux.dao.ValouxStoreAdvertisementDao;
import com.valoux.dao.ValouxStoreDao;
import com.valoux.enums.UserRegistratonEnums;
import com.valoux.model.AgentModel;
import com.valoux.model.LoginModel;
import com.valoux.model.ValouxAgentStoreModel;
import com.valoux.model.ValouxStoreAdvertisementModel;
import com.valoux.model.ValouxStoreModel;
import com.valoux.service.AgentService;
import com.valoux.util.CommonMailUtility;
import com.valoux.util.CommonUserUtility;
import com.valoux.util.CommonUtility;
import com.valoux.util.JSONUtility;
import com.valoux.util.MailApi;
import com.valoux.util.SendOTPSms;

/**
 * This <java>class</java> AgentServiceImpl use to perform all our service
 * related logics for the user or agent.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
public class AgentServiceImpl implements AgentService {

	@Autowired
	AgentDao agentDao;

	@Autowired
	ValouxStoreDao storeDao;
	
	@Autowired
	LoginDao loginDao;

	@Autowired
	MasterRoleDao masterRoleDao;
	
	@Autowired
	LoginLogDao loginLogDao;
	
	@Autowired
	ValouxAgentStoreDao valouxAgentStoreDao;
	@Autowired
	ValouxStoreAdvertisementDao valouxStoreAdvertisementDao;
	
	@Autowired
	StateDao stateDao;
	@Autowired
	CountryDao  countryDao;
	
	@Autowired
	UserTypeDao  userTypeDao;
	
	@Autowired
	UserRoleDao  userRoleDao;
	
	@Autowired
	ValouxSharedRequestDao valouxSharedRequestDao;
	
	@Autowired
	AppraisalDao appraisalDao;
	
	@Autowired
	UserDao userDao;

	private static final Logger LOGGER = Logger.getLogger(AgentServiceImpl.class);

	/**
	 * This method creates Agent.
	 */
	@Transactional
	public AgentBean createAgent(AgentModel agentModel) throws Exception {
		LOGGER.debug("Enter Method createAgent of AgentServiceImpl");
		AgentBean agentBean = CommonUserUtility.prepareAgentBeanFromAgentModel(agentModel);
		agentDao.createAgent(agentBean);
		LOGGER.debug("Exit Method createAgent of AgentServiceImpl");
		return agentBean;
	}

	/**
	 * This method creates User For Login.
	 */
	@Transactional
	public LoginBean createLoginUser(LoginModel loginModel) throws Exception {
		LOGGER.debug("Enter Method createLoginUser of AgentServiceImpl");
		LoginBean loginBean = CommonUserUtility.prepareUserLoginBeanFromModel(loginModel);
		loginBean = loginDao.createLoginUser(loginBean);
		LOGGER.debug("Exit Method createLoginUser of AgentServiceImpl");
		return loginBean;
	}

	/**
	 * This method registers new agent. 1. converts business objects into
	 * persisting POJO. 2. calls data access layer to persist information. 3.
	 * send email notification 4. generate and send OTP.
	 * 
	 * @paparam loginModel
	 *            : business object carrying login information
	 * @paparam agentProfileModel
	 *            : business object carrying agent information.
	 * @return agentBean: newly created agent
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	@Transactional
	public AgentBean registerAgent(LoginModel loginModel, AgentModel agentProfileModel) throws Exception {
		LOGGER.info("Enter Method registerAgent of AgentServiceImpl");
		LoginBean loginBean = createLoginUser(loginModel);
		AgentBean agentBean = new AgentBean();
		String pKey = loginBean.getPrivateKey();
		String rKey = (pKey);
		agentProfileModel.setRelationKey((pKey));
		agentProfileModel.setCreatedBy(rKey);
		agentProfileModel.setModifiedOn(CommonUtility.getDateAndTime());
		agentProfileModel.setModifiedBy(rKey);
		agentProfileModel.setCreatedOn(CommonUtility.getDateAndTime());
		if (loginBean != null) {
			agentBean = createAgent(agentProfileModel);
		}
		String from = CommonConstants.SIGN_UP_MAIL;
		StringBuffer sub = new StringBuffer();
		sub.append(CommonConstants.CONSUMER_BODY);
		
		if(agentBean != null){
			String body = CommonMailUtility.getUserRegistrationMailContent(loginBean);

			MailApi.SendMail(agentBean.getEmailId(), from, sub.toString(), body,
					UserRegistratonEnums.UsertType.Agent.getType());

			String message = "Hello" + " ! your OTP is: " + loginBean.getAuthCodeMobile();

			boolean isSend = SendOTPSms.sendOTP(message, agentBean.getMobile());
			if (isSend) {
				LOGGER.debug("OTP sent successfully");
			}
		}

		LOGGER.info("Exit Method registerAgent of AgentServiceImpl");
		return agentBean;
	}

	/**
	 * This method retrieves specific Role data.
	 */
	@Transactional
	public MasterRoleBean getRoleData(String roleName) throws Exception {
		LOGGER.debug("Enter Method getMasterRole of AgentServiceImpl");
		MasterRoleBean masterRoleBean = masterRoleDao.getUserRole(roleName);
		LOGGER.debug("Exit Method getMasterRole of AgentServiceImpl");
		return masterRoleBean;
	}

	/**
	 * This method performs get Relational Key.
	 */
	@Transactional
	public AgentBean getAgentDetails(String agentName) throws Exception {
		LOGGER.debug("Enter Method getAgentDetails of AgentServiceImpl");
		AgentBean agentBean = null;
		agentBean = agentDao.getAgentDetailsFromName(agentName);
		LOGGER.debug("Exit Method getAgentDetails of AgentServiceImpl");
		return agentBean;
	}

	/**
	 * This method verifies OTP against an user.
	 */
	@Transactional
	public boolean verifyUserOTP(Integer authUserID, String authLoginCode) throws Exception {
		LOGGER.debug("Enter Method verifyUserOTP of AgentServiceImpl");
		boolean isVerifyUserOTP = false;
		isVerifyUserOTP = loginLogDao.verifyUserOTP(authUserID, authLoginCode);
		LOGGER.debug("Exit Method verifyUserOTP of AgentServiceImpl");
		return isVerifyUserOTP;
	}

	/**
	 * This method performs get Agent Detail By Relation Key.
	 */
	@Transactional
	public AgentModel getAgentDetailByRelationKey(String rKey) throws Exception {
		LOGGER.debug("Enter method getAgentDetailByRelationKey of AgentServiceImpl");
		AgentBean agentBean = agentDao.getAgentDetailByRelationKey(rKey);
		AgentModel agentModel = null;
		if (agentBean != null) {
			agentModel = CommonUserUtility.prepareAgentModelFromAgentBean(agentBean);
			
			if (agentBean.getCountryId() != null) {
				agentModel.setCountryName(countryDao.getCountryNameByCountryId(agentBean.getCountryId()).getName());
			}
			if (agentBean.getStateId() != null) {
				agentModel.setStateName(stateDao.getStateNameByStateId(agentBean.getStateId()).getName());
			}
		}
		LOGGER.debug("Exit method getAgentDetailByRelationKey of AgentServiceImpl");
		return agentModel;
	}

	/**
	 * This method performs save Agent StoreBean.
	 */
	@Transactional
	public ValouxAgentStoreModel saveAgentStoreBean(ValouxAgentStoreModel agentStoreModel) throws Exception {
		LOGGER.debug("Enter method saveAgentStoreBean of AgentServiceImpl");
		ValouxAgentStoreBean agentStoreBean = CommonUserUtility.prepareAgentStoreBeanFromModel(agentStoreModel);
		valouxAgentStoreDao.saveAgentStoreId(agentStoreBean);
		agentStoreModel = CommonUserUtility.prepareAgentStoreModelFromBean(agentStoreBean);
		LOGGER.debug("Exit method saveAgentStoreBean of AgentServiceImpl");
		return agentStoreModel;
	}

	/**
	 * This method retrieves store information using relation key.
	 * 
	 * @paparam rkey
	 *            : relation key of the store
	 * @return agentStoreModel: business object having info of retrieved store
	 */
	@Transactional
	public ValouxAgentStoreModel getStoreDataByRelationKey(String rKey) throws Exception {
		LOGGER.debug("Enter method getStoreDataByRelationKey of AgentServiceImpl");
		ValouxAgentStoreBean agentStoreBean = valouxAgentStoreDao.getAgentStoreDataByRelationKey(rKey);
		if(agentStoreBean==null){
			return null;
		}
		ValouxAgentStoreModel agentStoreModel = CommonUserUtility.prepareAgentStoreModelFromBean(agentStoreBean);
		LOGGER.debug("Exit method getStoreDataByRelationKey of AgentServiceImpl");
		return agentStoreModel;
	}

	/**
	 * This method reurns the number of user associated with store
	 */
	@Transactional
	public Integer getNumberOfUserAssociatedWithStore(Integer storeId) throws Exception {
		LOGGER.debug("Enter method getNumberOfUserAssociatedWithStore of AgentServiceImpl");
		Integer count = 0;
		List<ValouxAgentStoreBean> agentStoreBeanList = valouxAgentStoreDao.getAgentStoreDataByStoreId(storeId);
		if (agentStoreBeanList != null && agentStoreBeanList.size() > 0) {
			count = agentStoreBeanList.size();
		}
		LOGGER.debug("Exit method getNumberOfUserAssociatedWithStore of AgentServiceImpl");
		return count;
	}

	/**
	 * This method reurns the agent information associated with store
	 */
	@Transactional
	public List<ValouxAgentStoreBean> getAgentDetailAssociatedWithStore(Integer storeId) throws Exception {
		LOGGER.debug("Enter method getAgentDetailAssociatedWithStore of AgentServiceImpl");
		List<ValouxAgentStoreBean> agentStoreBeanList = valouxAgentStoreDao.getAgentStoreDataByStoreId(storeId);
		LOGGER.debug("Exit method getAgentDetailAssociatedWithStore of AgentServiceImpl");
		return agentStoreBeanList;
	}

	/**
	 * This method activate or deactivate store accordin to the action
	 * performaed
	 * 
	 * @paparam storeId
	 * @paparam action
	 */
	@Transactional
	public void activateOrDeactiveStore(Integer storeId, String action) throws Exception {
		LOGGER.debug("Enter method activateOrDeactiveStore of AgentServiceImpl");
		ValouxStoreBean storeBean = storeDao.getStoreDataByStoreId(storeId);
		if (action.equals("activate")) {
			storeBean.setStatus(CommonConstants.STATUS_ACTIVE);
		}
		if (action.equals("inactivate")) {
			storeBean.setStatus(CommonConstants.STATUS_INACTIVE);
		}
		storeDao.updateStoreData(storeBean);
		LOGGER.debug("Exit method activateOrDeactiveStore of AgentServiceImpl");
	}
	
	/**
	 * This method saves item image.
	 */
	@Transactional
	public void saveStoreAdv(JSONArray advImagejArray, ValouxStoreAdvertisementModel storeAdvModel) throws Exception {
		LOGGER.info("Enter Method saveStoreAdv of AgentServiceImpl");
		for (int i = 0; i < advImagejArray.length(); i++) {
			JSONObject imageJObject = advImagejArray.getJSONObject(i);
			String imageContent = JSONUtility.getSafeString(imageJObject, "advImageContent");
			String imageName = JSONUtility.getSafeString(imageJObject, "advImageName");
			String advImageTitle = JSONUtility.getSafeString(imageJObject, "advImageTitle");
			Integer maxValue = valouxStoreAdvertisementDao.getMaximumIndexOfStoreAdvTable();
			String imagePath;
			imagePath = CommonUtility.saveAdvImage(imageContent, imageName, "Store_Adv"+maxValue, storeAdvModel.getStoreId(),storeAdvModel);
			ValouxStoreAdvertisementBean storeAdvBean = new ValouxStoreAdvertisementBean();
			storeAdvBean.setCreatedBy(storeAdvModel.getCreatedBy());
			storeAdvBean.setCreatedOn(storeAdvModel.getCreatedOn());
			storeAdvBean.setImgPath(storeAdvModel.getImgPath());
			storeAdvBean.setModifiedBy(storeAdvModel.getModifiedBy());
			storeAdvBean.setModifiedOn(storeAdvModel.getModifiedOn());
			storeAdvBean.setStatus(storeAdvModel.getStatus());
			storeAdvBean.setStoreId(storeAdvModel.getStoreId());
			storeAdvBean.setTitle(advImageTitle);
			storeAdvBean.setUrl(imagePath);
			valouxStoreAdvertisementDao.createStoreAdvertisement(storeAdvBean);
		}
		LOGGER.info("Exit Method saveStoreAdv of AgentServiceImpl");

	}
	
	/**
	 * This method saves item image.
	 */
	@Transactional
	public void updateStoreAdv(JSONArray advImagejArray, Integer storeAdvertisementId) throws Exception {
		LOGGER.info("Enter Method updateStoreAdv of AgentServiceImpl");
		for (int i = 0; i < advImagejArray.length(); i++) {
			JSONObject imageJObject = advImagejArray.getJSONObject(i);
			String imageContent = JSONUtility.getSafeString(imageJObject, "advImageContent");
			String imageName = JSONUtility.getSafeString(imageJObject, "advImageName");
			String advImageTitle = JSONUtility.getSafeString(imageJObject, "advImageTitle");
			List<ValouxStoreAdvertisementBean> storeAdvBeanList = valouxStoreAdvertisementDao.getStoreAdvertisementListByStoreAdvertisementId(storeAdvertisementId);
			if(storeAdvBeanList!=null && storeAdvBeanList.size()>0){
				for(ValouxStoreAdvertisementBean storeAdvBean:storeAdvBeanList){
					if(imageContent!=null && imageName!=null){
						CommonUtility.deleteStoreAdvImageInDirectory(storeAdvBean.getUrl());
						Integer maxValue = valouxStoreAdvertisementDao.getMaximumIndexOfStoreAdvTable();
						ValouxStoreAdvertisementModel storeAdvModel = new ValouxStoreAdvertisementModel();
						String imagePath;
						imagePath = CommonUtility.saveAdvImage(imageContent, imageName, "Store_Adv"+maxValue, storeAdvBean.getStoreId(),storeAdvModel);
						storeAdvBean.setUrl(imagePath);
						storeAdvBean.setImgPath(storeAdvModel.getImgPath());
						storeAdvBean.setModifiedOn(CommonUtility.getDateAndTime());
						if(advImageTitle!=null){
							storeAdvBean.setTitle(advImageTitle);
						}
					}
					if(advImageTitle!=null){
						storeAdvBean.setTitle(advImageTitle);
					}
					valouxStoreAdvertisementDao.updateStoreAdvertisement(storeAdvBean);
				}
			}
			
		}
		LOGGER.info("Exit Method updateStoreAdv of AgentServiceImpl");

	}
	
	/**
	 * This method get store advertisement list by store id
	 * @throws Exception 
	 * 
	 */
	@Transactional
	public List<ValouxStoreAdvertisementModel> getStoreAdvertisementListByStoreId(Integer storeId) throws Exception{
		LOGGER.info("Exit method getAllStoreData of getStoreAdvertisementListBuStoreId");
		List<ValouxStoreAdvertisementBean> storeAdvBeanList = valouxStoreAdvertisementDao.getStoreAdvertisementListByStoreId(storeId);
		List<ValouxStoreAdvertisementModel> storeAdvModelList = new ArrayList<ValouxStoreAdvertisementModel>();
		if(storeAdvBeanList!=null && storeAdvBeanList.size()>0){
			for(ValouxStoreAdvertisementBean storeAdvBean : storeAdvBeanList){
				ValouxStoreAdvertisementModel storeAdvModel = new ValouxStoreAdvertisementModel();
				storeAdvModel.setCreatedBy(storeAdvBean.getCreatedBy());
				storeAdvModel.setCreatedOn(storeAdvBean.getCreatedOn());
				storeAdvModel.setImgPath(storeAdvBean.getImgPath());
				storeAdvModel.setModifiedBy(storeAdvBean.getModifiedBy());
				storeAdvModel.setModifiedOn(storeAdvBean.getModifiedOn());
				storeAdvModel.setStatus(storeAdvBean.getStatus());
				storeAdvModel.setStoreId(storeAdvBean.getStoreId());
				storeAdvModel.setTitle(storeAdvBean.getTitle());
				storeAdvModel.setUrl(storeAdvBean.getUrl());
				storeAdvModel.setStoreAdvertisementId(storeAdvBean.getStoreAdvertisementId());
				storeAdvModelList.add(storeAdvModel);
			}
		}
		LOGGER.info("Exit method getAllStoreData of getStoreAdvertisementListBuStoreId");
		return storeAdvModelList;
	}
	
	/**
	 * This method activate or deactivate user according to the action
	 * performed
	 * 
	 * @paparam status
	 * @paparam userPublicKey
	 */
	@Transactional
	public LoginBean activateOrDeactiveAgent(Integer status, String userPublicKey) throws Exception {
		LOGGER.debug("Enter method activateOrDeactiveAgent of AgentServiceImpl");
		
		LoginBean loginBean = loginDao.getLoginDetailByPKey(userPublicKey);
		if(loginBean !=null){
			if (status == 1) {
				loginBean.setUserStatus(CommonConstants.STATUS_ACTIVE);
			}
			if (status == 2) {
				loginBean.setUserStatus(CommonConstants.STATUS_INACTIVE);
			}
			loginDao.updateLoginData(loginBean);
		}
		
		LOGGER.debug("Exit method activateOrDeactiveAgent of AgentServiceImpl");
		return loginBean;
	}
	
	/**
	 * This method delete store advertisement
	 */
	@Transactional
	public void deleteStoreAdvertisement(Integer storeId,Integer storeAdvertisementId) throws Exception{
		LOGGER.info("Enter method deleteStoreAdvertisement of AgentServiceImpl");
		List<ValouxStoreAdvertisementBean> storeAdvBeanList = new ArrayList<ValouxStoreAdvertisementBean>();
		if(storeId!=null){
			storeAdvBeanList = valouxStoreAdvertisementDao.getStoreAdvertisementListByStoreId(storeId);
		}else if(storeAdvertisementId!=null){
			storeAdvBeanList = valouxStoreAdvertisementDao.getStoreAdvertisementListByStoreAdvertisementId(storeAdvertisementId);
		}
		if(storeAdvBeanList!=null && storeAdvBeanList.size()>0){
			for(ValouxStoreAdvertisementBean storeAdvBean: storeAdvBeanList){
				valouxStoreAdvertisementDao.deleteStoreAdvImages(storeAdvBean);
				CommonUtility.deleteStoreAdvImageInDirectory(storeAdvBean.getUrl());
			}
		}
		LOGGER.info("Exit method deleteStoreAdvertisement of AgentServiceImpl");
	}
	
	/**
	 * This method get store advertisement list bu storeadvertismentId
	 */
	@Transactional
	public ValouxStoreAdvertisementModel getStoreAdvertisementListByStoreAdvertisementId(Integer storeAdvertisementId) throws Exception{
		LOGGER.info("Enter method getStoreAdvertisementListByStoreAdvertisementId of AgentServiceImpl");
		List<ValouxStoreAdvertisementBean> storeAdvBeanList = new ArrayList<ValouxStoreAdvertisementBean>();
		 ValouxStoreAdvertisementModel storeAdvModel = new ValouxStoreAdvertisementModel();
			storeAdvBeanList = valouxStoreAdvertisementDao.getStoreAdvertisementListByStoreAdvertisementId(storeAdvertisementId);
		if(storeAdvBeanList!=null && storeAdvBeanList.size()>0){
			for(ValouxStoreAdvertisementBean storeAdvBean: storeAdvBeanList){
				storeAdvModel.setCreatedBy(storeAdvBean.getCreatedBy());
				storeAdvModel.setCreatedOn(storeAdvBean.getCreatedOn());
				storeAdvModel.setImgPath(storeAdvBean.getImgPath());
				storeAdvModel.setModifiedBy(storeAdvBean.getModifiedBy());
				storeAdvModel.setModifiedOn(storeAdvBean.getModifiedOn());
				storeAdvModel.setStatus(storeAdvBean.getStatus());
				storeAdvModel.setStoreId(storeAdvBean.getStoreId());
				storeAdvModel.setTitle(storeAdvBean.getTitle());
				storeAdvModel.setUrl(storeAdvBean.getUrl());
				storeAdvModel.setStoreAdvertisementId(storeAdvBean.getStoreAdvertisementId());
			}
		}
		LOGGER.info("Exit method getStoreAdvertisementListByStoreAdvertisementId of AgentServiceImpl");
		return storeAdvModel;
	}
	
	/**
	 * This method active or inactive store advertisement
	 */
	@Transactional
	public void activeInactiveStoreAdv(Integer storeId,Integer storeAdvertisementId,Integer status) throws Exception{
		LOGGER.info("Enter method activeInactiveStoreAdv of AgentServiceImpl");
		List<ValouxStoreAdvertisementBean> storeAdvBeanList = new ArrayList<ValouxStoreAdvertisementBean>();
		if(storeId!=null){
			storeAdvBeanList = valouxStoreAdvertisementDao.getStoreAdvertisementListByStoreId(storeId);
		}else if(storeAdvertisementId!=null){
			storeAdvBeanList = valouxStoreAdvertisementDao.getStoreAdvertisementListByStoreAdvertisementId(storeAdvertisementId);
		}
		if(storeAdvBeanList!=null && storeAdvBeanList.size()>0){
			for(ValouxStoreAdvertisementBean storeAdvBean: storeAdvBeanList){
				storeAdvBean.setStatus(status);
				valouxStoreAdvertisementDao.updateStoreAdvImages(storeAdvBean);
			}
		}
		LOGGER.info("Exit method activeInactiveStoreAdv of AgentServiceImpl");
	}
	
	/**
	 * this mehtod get list of store which don't have advertisement
	 */
	@Transactional
	public List<ValouxStoreModel> getStoreNoteHavinAdvertisement() throws Exception{
		LOGGER.info("Enter method getStoreNoteHavinAdvertisement of AgentServiceImpl");
		List<Integer> storeIdList = new ArrayList<Integer>();
		List<ValouxStoreModel> storeModelList = new ArrayList<ValouxStoreModel>();
		List<ValouxStoreAdvertisementBean> storeAdvBeanList = valouxStoreAdvertisementDao.getStoreAdvertisementListByDistinctStoreId();
		if(storeAdvBeanList!=null && storeAdvBeanList.size()>0){
			for(ValouxStoreAdvertisementBean storeAdvBean: storeAdvBeanList){
				storeIdList.add(storeAdvBean.getStoreId());
			}
		}
			List<ValouxStoreBean> storeBeanList = storeDao.getStoreListNotInArray(storeIdList.toArray());
			if(storeBeanList!=null && storeBeanList.size()>0){
				for(ValouxStoreBean storeBean:storeBeanList){
					String countryName = countryDao.getCountryNameByCountryId(storeBean.getCountryId()).getName();
					String stateName = stateDao.getStateNameByStateId(storeBean.getStateId()).getName();
					storeModelList.add(CommonUserUtility.prepareStoreModelFromBean(storeBean, countryName, stateName));
				}
			}
		
		LOGGER.info("Enter method getStoreNoteHavinAdvertisement of AgentServiceImpl");
		return storeModelList;
	}
	
	/**
	 * This method get all store advertisement
	 */
	@Transactional
	public List<ValouxStoreAdvertisementModel> getAllStoreAdvertisementDetail() throws Exception{
		LOGGER.info("Enter method getAllStoreAdvertisementDetail of AgentServiceImpl");
		List<ValouxStoreAdvertisementBean> storeAdvBeanList = valouxStoreAdvertisementDao.getStoreAdvertisementList();
		List<ValouxStoreAdvertisementModel> storeAdvModelList = new ArrayList<ValouxStoreAdvertisementModel>();
		if(storeAdvBeanList!=null && storeAdvBeanList.size()>0){
			for(ValouxStoreAdvertisementBean storeAdvBean : storeAdvBeanList){
				ValouxStoreAdvertisementModel storeAdvModel = new ValouxStoreAdvertisementModel();
				storeAdvModel.setCreatedBy(storeAdvBean.getCreatedBy());
				storeAdvModel.setCreatedOn(storeAdvBean.getCreatedOn());
				storeAdvModel.setImgPath(storeAdvBean.getImgPath());
				storeAdvModel.setModifiedBy(storeAdvBean.getModifiedBy());
				storeAdvModel.setModifiedOn(storeAdvBean.getModifiedOn());
				storeAdvModel.setStatus(storeAdvBean.getStatus());
				storeAdvModel.setStoreId(storeAdvBean.getStoreId());
				storeAdvModel.setTitle(storeAdvBean.getTitle());
				storeAdvModel.setUrl(storeAdvBean.getUrl());
				storeAdvModel.setStoreAdvertisementId(storeAdvBean.getStoreAdvertisementId());
				storeAdvModelList.add(storeAdvModel);
			}
		}
		LOGGER.info("Enter method getAllStoreAdvertisementDetail of AgentServiceImpl");
		return storeAdvModelList;
	}
	
	/**
	 * this method get list of store which have advertisement
	 */
	@Transactional
	public List<ValouxStoreModel> getStoreHavinAdvertisement() throws Exception{
		LOGGER.info("Enter method getStoreeHavinAdvertisement of AgentServiceImpl");
		List<Integer> storeIdList = new ArrayList<Integer>();
		List<ValouxStoreModel> storeModelList = new ArrayList<ValouxStoreModel>();
		List<ValouxStoreAdvertisementBean> storeAdvBeanList = valouxStoreAdvertisementDao.getStoreAdvertisementListByDistinctStoreId();
		if(storeAdvBeanList!=null && storeAdvBeanList.size()>0){
			for(ValouxStoreAdvertisementBean storeAdvBean: storeAdvBeanList){
				storeIdList.add(storeAdvBean.getStoreId());
			}
			List<ValouxStoreBean> storeBeanList = storeDao.getStoreListInArray(storeIdList.toArray());
			if(storeBeanList!=null && storeBeanList.size()>0){
				for(ValouxStoreBean storeBean:storeBeanList){
					String countryName = countryDao.getCountryNameByCountryId(storeBean.getCountryId()).getName();
					String stateName = stateDao.getStateNameByStateId(storeBean.getStateId()).getName();
					storeModelList.add(CommonUserUtility.prepareStoreModelFromBean(storeBean, countryName, stateName));
				}
			}
		}
		LOGGER.info("Enter method getStoreeHavinAdvertisement of AgentServiceImpl");
		return storeModelList;
	}

	/**
	 * this method update sign of agent
	 */
	@Transactional
	public void updateAgentSignature(AgentModel agentProfileModel)
			throws Exception {
		if(agentProfileModel != null){
			AgentBean agentBean = agentDao.getAgentDetailByRelationKey(agentProfileModel.getRelationKey());
			if(agentBean != null){
				agentBean.setSignName(agentProfileModel.getSignName());
				if(CommonUserUtility.paparameterNullCheckStringObject(agentProfileModel.getSignUrl())){
					agentBean.setSignUrl(agentProfileModel.getSignUrl());
				}
				agentDao.updateAgentDetails(agentBean);
			}
		}
		
	}

	@Transactional
	public Boolean deleteAgentSignDocument(String publicKey) throws Exception {
		if(CommonUserUtility.paparameterNullCheckStringObject(publicKey)){
			AgentBean agentBean = agentDao.getAgentDetailByRelationKey(publicKey);
			if(agentBean != null) {
				CommonUtility.deleteAgentSignDocumentFromDirectory(agentBean.getSignUrl());
				agentBean.setSignUrl(null);
				agentBean.setModifiedBy(publicKey);
				agentBean.setModifiedOn(CommonUtility.getDateAndTime());
				agentDao.updateAgentDetails(agentBean);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method get All agent list
	 */
	@Transactional
	public List<AgentModel> getAllAgentDetail() throws Exception {
		LOGGER.debug("Enter method getAgentDetailByRelationKey of AgentServiceImpl");
		List<AgentBean> agentBeanList = agentDao.getAllAgent();
		List<AgentModel>  agentModelList = new ArrayList<AgentModel>();
		if (agentBeanList != null) {
			for(AgentBean agentBean:agentBeanList){
				AgentModel agentModel = new AgentModel();
			agentModel = CommonUserUtility.prepareAgentModelFromAgentBean(agentBean);
			
			if (agentBean.getCountryId() != null) {
				agentModel.setCountryName(countryDao.getCountryNameByCountryId(agentBean.getCountryId()).getName());
			}
			if (agentBean.getStateId() != null) {
				agentModel.setStateName(stateDao.getStateNameByStateId(agentBean.getStateId()).getName());
			}
			agentModelList.add(agentModel);
			}
		}
		LOGGER.debug("Exit method getAgentDetailByRelationKey of AgentServiceImpl");
		return agentModelList;
	}
	
	/**
	 * This method delete agent
	 */
	@Transactional
	public Boolean deleteAgent(String userPublicKey) throws Exception
	{	
		LOGGER.debug("Enter method deleteAgent of AgentServiceImpl");
		AgentBean agentBean = agentDao.getAgentDetailByRelationKey(userPublicKey);
		if(agentBean !=null){
			
			
			LoginBean loginBean = loginDao.getLoginDetailByPKey((userPublicKey));
			if(loginBean!=null){
				loginDao.deleteAnyBeanByObject(loginBean);
			}
			UserTypeBean userTypeBean = userTypeDao.getUserType(userPublicKey);
			if(userTypeBean!=null){
				loginDao.deleteAnyBeanByObject(userTypeBean);
			}
			
			UserRoleBean userRoleBean = userRoleDao.getRole(userPublicKey);
			if(userRoleBean!=null){
				loginDao.deleteAnyBeanByObject(userRoleBean);
			}
			
			List<ValouxSharedRequestBean> sharedRequestBeansNew = valouxSharedRequestDao.getAllBeanSharedToUser(userPublicKey);
			if(sharedRequestBeansNew != null && sharedRequestBeansNew.size() > 0){
				for (ValouxSharedRequestBean valouxSharedRequestBeanNew : sharedRequestBeansNew) {
					appraisalDao.deleteAnyBeanByObject(valouxSharedRequestBeanNew);
				}
			}
			List<ValouxAgentStoreBean> agentStoreBeanList = valouxAgentStoreDao.getAllAgentStoreDataByRelationKey(userPublicKey);
			if(agentStoreBeanList!=null){
				for(ValouxAgentStoreBean agentStoreBean:agentStoreBeanList){
					userDao.deleteAnyBeanByObject(agentStoreBean);
				}
			}
			userDao.deleteAnyBeanByObject(agentBean);
			return true; 
		}
		LOGGER.debug("Exit method deleteAgent of AgentServiceImpl");
		return false;
	}

}
