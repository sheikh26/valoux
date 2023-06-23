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
import com.valoux.bean.UserRoleBean;
import com.valoux.bean.ValouxAgentStoreBean;
import com.valoux.bean.ValouxItemBean;
import com.valoux.bean.ValouxStoreAdvertisementBean;
import com.valoux.bean.ValouxStoreBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.CountryDao;
import com.valoux.dao.LoginDao;
import com.valoux.dao.StateDao;
import com.valoux.dao.UserDao;
import com.valoux.dao.UserRoleDao;
import com.valoux.dao.ValouxAgentStoreDao;
import com.valoux.dao.ValouxItemDao;
import com.valoux.dao.ValouxStoreAdvertisementDao;
import com.valoux.dao.ValouxStoreDao;
import com.valoux.enums.UserRegistratonEnums;
import com.valoux.model.AgentModel;
import com.valoux.model.ValouxAgentStoreModel;
import com.valoux.model.ValouxStoreModel;
import com.valoux.service.ValouxStoreService;
import com.valoux.util.CommonMailUtility;
import com.valoux.util.CommonUserUtility;
import com.valoux.util.CommonUtility;
import com.valoux.util.JSONUtility;
import com.valoux.util.MailApi;

/**
 * This <java>class</java> ValouxStoreServiceImpl use to perform all our service
 * related logics for the store.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
public class ValouxStoreServiceImpl implements ValouxStoreService {

	@Autowired
	ValouxStoreDao storeDao;

	@Autowired
	UserDao userDao;
	
	@Autowired
	StateDao stateDao;
	@Autowired
	CountryDao  countryDao;
	
	@Autowired
	UserRoleDao  userRoleDao;
	
	@Autowired
	LoginDao loginDao;
	@Autowired
	ValouxAgentStoreDao valouxAgentStoreDao;
	
	@Autowired
	ValouxStoreAdvertisementDao valouxStoreAdvertisementDao;
	
	@Autowired
	ValouxItemDao valouxItemDao;

	private static final Logger LOGGER = Logger.getLogger(ValouxStoreServiceImpl.class);

	/*
	 * This method creates Store.
	 */
	@Transactional
	public ValouxStoreBean createStore(ValouxStoreModel storeModel, AgentBean agentBean, String agentRole)
			throws Exception {
		LOGGER.info("Enter method create store of ValouxStoreServiceImpl");

		ValouxStoreBean storeBean = null;

		if (storeModel != null) {
			storeBean = CommonUserUtility.prepareStoreBeanFromModel(storeModel);
			ValouxStoreBean valouxStoreBean = null;
			if (storeModel.getStoreId() == null) {
				valouxStoreBean = checkDuplicateStore(storeModel);

				if (valouxStoreBean == null) {
					storeBean = storeDao.createStore(storeBean);
					if (agentBean != null) {
						sendMailToSuperadmin(storeBean, agentBean, agentRole);
					}

				} else {
					storeBean = valouxStoreBean;
				}
			}
		}

		LOGGER.info("Exit method create store of ValouxStoreServiceImpl");
		return storeBean;
	}

	/**
	 * This method performs get All active Store Data.
	 */
	@Transactional
	public List<ValouxStoreModel> getAllActiveStoreData() throws Exception {
		LOGGER.info("Enter method  getAllActiveStoreData of ValouxStoreServiceImpl");
		List<ValouxStoreBean> storeBeanList = storeDao.getAllActiveStoreData();
		List<ValouxStoreModel> storeModelList = new ArrayList<ValouxStoreModel>();
		if (storeBeanList != null && storeBeanList.size() > 0) {
			for (ValouxStoreBean storeBean : storeBeanList) {
				String countryName = countryDao.getCountryNameByCountryId(storeBean.getCountryId()).getName();
				String stateName = stateDao.getStateNameByStateId(storeBean.getStateId()).getName();
				storeModelList.add(CommonUserUtility.prepareStoreModelFromBean(storeBean, countryName, stateName));
			}
		}
		LOGGER.info("Exit method  getAllActiveStoreData of ValouxStoreServiceImpl");
		return storeModelList;
	}
	
	/**
	 * This method performs get All active Store Data with keyword.
	 */
	@Transactional
	public List<ValouxStoreModel> getAllActiveStoreDatawithKeyWord(String keyword,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception {
		LOGGER.info("Enter method  getAllActiveStoreData of ValouxStoreServiceImpl");
		List<ValouxStoreBean> storeBeanList = storeDao.getAllActiveStoreDataWithKeyword(keyword, startRecordNo, numberOfRecords, sortBy, sortOrder);
		List<ValouxStoreModel> storeModelList = new ArrayList<ValouxStoreModel>();
		if (storeBeanList != null && storeBeanList.size() > 0) {
			for (ValouxStoreBean storeBean : storeBeanList) {
				String countryName = countryDao.getCountryNameByCountryId(storeBean.getCountryId()).getName();
				String stateName = stateDao.getStateNameByStateId(storeBean.getStateId()).getName();
				storeModelList.add(CommonUserUtility.prepareStoreModelFromBean(storeBean, countryName, stateName));
			}
		}
		LOGGER.info("Exit method  getAllActiveStoreData of ValouxStoreServiceImpl");
		return storeModelList;
	}

	/**
	 * This method performs check Duplicate Store.
	 */

	@Transactional
	public ValouxStoreBean checkDuplicateStore(ValouxStoreModel storeModel) throws Exception {
		LOGGER.info("Enter method checkDuplicateStore of getAllStoreData of ValouxStoreServiceImpl");
		if (storeModel.getCountryId() == null) {
			storeModel.setCountryId(countryDao.getCountryId(storeModel.getCountryName()).get(0).getCountryId());
		}
		if (storeModel.getStateId() == null) {
			storeModel.setStateId(stateDao.getStateId(storeModel.getStateName()).get(0).getStateId());
		}
		ValouxStoreBean storeBean = CommonUserUtility.prepareStoreBeanFromModel(storeModel);
		List<ValouxStoreBean> storeBeanList = storeDao.checkDuplicateStore(storeBean);
		if (storeBeanList != null && storeBeanList.size() > 0) {
			return storeBeanList.get(0);
		}
		LOGGER.info("Enter method checkDuplicateStore of getAllStoreData of ValouxStoreServiceImpl");
		return null;
	}

	/**
	 * This method performs set Agent Address As Store Address.
	 */
	@Transactional
	public AgentModel setAgentAdressAsStoreAddress(Integer storeId, AgentModel agentProfileModel) throws Exception {
		LOGGER.info("Enter Method setAgentAdressAsStoreAddress of ValouxStoreServiceImpl");
		ValouxStoreBean storeBean = storeDao.getStoreDataByStoreId(storeId);
		if (storeBean != null) {
			agentProfileModel.setGlobalAddress(storeBean.getgAddress());
			agentProfileModel.setStreetNo(storeBean.getStreetNumber());
			agentProfileModel.setAddressLine1(storeBean.getAddressLine1());
			agentProfileModel.setAddressLine2(storeBean.getAddressLine2());
			agentProfileModel.setCity(storeBean.getCity());

			agentProfileModel.setStateId(storeBean.getStateId());
			agentProfileModel.setCountryId(storeBean.getCountryId());
			agentProfileModel.setZipCode(storeBean.getZipcode());
		}
		LOGGER.info("Exit Method setAgentAdressAsStoreAddress of ValouxStoreServiceImpl");
		return agentProfileModel;
	}

	/**
	 * This method performs get Store Data By StoreId.
	 */
	@Transactional
	public ValouxStoreModel getStoreDataByStoreId(Integer storeId) throws Exception {
		LOGGER.info("Enter Method getStoreDataByStoreId of ValouxStoreServiceImpl");

		ValouxStoreModel storeModel = null;
		ValouxStoreBean storeBean = storeDao.getStoreDataByStoreId(storeId);

		if (storeBean != null) {
			storeModel = CommonUserUtility.prepareStoreModelFromBean(storeBean,
					countryDao.getCountryNameByCountryId(storeBean.getCountryId()).getName(), stateDao
							.getStateNameByStateId(storeBean.getStateId()).getName());
		}

		LOGGER.info("Exit Method getStoreDataByStoreId of ValouxStoreServiceImpl");
		return storeModel;
	}

	/**
	 * This method get agents by store id
	 */
	@Transactional
	public List<ValouxAgentStoreModel> getStoreAgentsByStoreId(Integer storeId) throws Exception {
		LOGGER.info("Enter Method getStoreDataByStoreId of ValouxStoreServiceImpl");

		List<ValouxAgentStoreModel> agentModels = new ArrayList<ValouxAgentStoreModel>();
		List<ValouxAgentStoreBean> agentStoreBeans = valouxAgentStoreDao.getValouxAgentStoresByStoreId(storeId);

		agentModels = CommonUserUtility.populateValouxAgentListFromStoreBean(agentModels, agentStoreBeans);

		LOGGER.info("Exit Method getStoreAgentsByStoreId of ValouxStoreServiceImpl");

		return agentModels;
	}

	/**
	 * This method performs send Mail To Super admin.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	@Transactional
	public void sendMailToSuperadmin(ValouxStoreBean storeBean, AgentBean agentBean, String agentRole) throws Exception {
		LOGGER.debug("Enter Method sendMailToSuperadmin of ValouxStoreServiceImpl");
		List<UserRoleBean> userRoleBeanList = userRoleDao.getListOfUserAsSuperAdminRole();
		if (userRoleBeanList != null && userRoleBeanList.size() > 0) {
			for (UserRoleBean userRoleBean : userRoleBeanList) {
				LoginBean loginBean = loginDao
						.getLoginDetailByPKey((userRoleBean.getRelationKey()));
				String from = CommonConstants.SIGN_UP_MAIL;
				StringBuffer sub = new StringBuffer();
				sub.append(CommonConstants.AGENT_REGISTER);

				String body = CommonMailUtility.getStoreRegistrationMailContent(loginBean, storeBean, agentBean,
						agentRole);
				MailApi.SendMail(loginBean.getUserName(), from, sub.toString(), body,
						UserRegistratonEnums.UsertType.Customer.getType());
			}
		}

		LOGGER.debug("Exit Method sendMailToSuperadmin of ValouxStoreServiceImpl");

	}

	/**
	 * This method performs get All Store Data.
	 */
	@Transactional
	public List<ValouxStoreModel> getAllStoreData() throws Exception {
		LOGGER.info("Enter method getAllStoreData of ValouxStoreServiceImpl");
		List<ValouxStoreBean> storeBeanList = storeDao.getAllStoreData();
		List<ValouxStoreModel> storeModelList = new ArrayList<ValouxStoreModel>();
		if (storeBeanList != null && storeBeanList.size() > 0) {
			for (ValouxStoreBean storeBean : storeBeanList) {
				String countryName = countryDao.getCountryNameByCountryId(storeBean.getCountryId()).getName();
				String stateName = stateDao.getStateNameByStateId(storeBean.getStateId()).getName();
				storeModelList.add(CommonUserUtility.prepareStoreModelFromBean(storeBean, countryName, stateName));
			}
		}
		LOGGER.info("Exit method getAllStoreData of ValouxStoreServiceImpl");
		return storeModelList;
	}
	
	/**
	 * This method merge stores
	 */
	@Transactional
	public void mergeStores(Integer primaryStoreId,JSONArray storeIdsToBeMerged) throws Exception{
		LOGGER.info("Enter method mergeStores of ValouxStoreServiceImpl");
		for(int i=0;i<storeIdsToBeMerged.length();i++){
			JSONObject jObject = storeIdsToBeMerged.getJSONObject(i);
			Integer storeIdToBeMerged = JSONUtility.getSafeInteger(jObject, "storeId");
			 List<ValouxAgentStoreBean> agentStoreBeanList = valouxAgentStoreDao.getAgentStoreDataByStoreId(storeIdToBeMerged);
			 List<ValouxItemBean> itemBeanList = valouxItemDao.getItemDetailByStoreId(storeIdToBeMerged);
			 List<ValouxStoreAdvertisementBean> storeAdvBeanList = valouxStoreAdvertisementDao.getStoreAdvertisementListByStoreId(storeIdToBeMerged);
			if(agentStoreBeanList!=null && agentStoreBeanList.size()>0){
			valouxAgentStoreDao.mergestore(primaryStoreId, storeIdToBeMerged);
			}
			if(itemBeanList!=null && itemBeanList.size()>0){
			valouxItemDao.mergestore(primaryStoreId, storeIdToBeMerged);
			}
			if(storeAdvBeanList!=null && storeAdvBeanList.size()>0){
			valouxStoreAdvertisementDao.mergestore(primaryStoreId, storeIdToBeMerged);
			}
			ValouxStoreBean storeBean = new ValouxStoreBean();
			storeBean=storeDao.getStoreDataByStoreId(storeIdToBeMerged);
			if(storeBean!=null){
			storeDao.deleteStore(storeBean);
			}
		}
		LOGGER.info("Exit method mergeStores of ValouxStoreServiceImpl");
	}

	@Transactional
	public void updateStoreDetails(ValouxStoreModel storeModel)
			throws Exception {
		
		LOGGER.info("Enter method updateStoreDetails of ValouxStoreServiceImpl");

		if (storeModel != null) {
			ValouxStoreBean storeBean = storeDao.getStoreDataByStoreId(storeModel.getStoreId());
			
			if(storeBean != null){
				
				if(CommonUserUtility.paparameterNullCheckStringObject(storeModel.getAddressLine1())){
					storeBean.setAddressLine1(storeModel.getAddressLine1());
				}
				storeBean.setAddressLine2(storeModel.getAddressLine2());
				storeBean.setAlternatePhone(storeModel.getAlternatePhone());
				
				if(CommonUserUtility.paparameterNullCheckStringObject(storeModel.getCity())){
					storeBean.setCity(storeModel.getCity());
				}
				
				if(CommonUserUtility.paparameterNullCheckObject(storeModel.getCountryId())){
					storeBean.setCountryId(storeModel.getCountryId());
				}
				
				if(CommonUserUtility.paparameterNullCheckObject(storeModel.getStatus())){
					storeBean.setStatus(storeModel.getStatus());
				}
				
				storeBean.setFacebook(storeModel.getFacebook());
				storeBean.setgAddress(storeModel.getgAddress());
				storeBean.setGoogle(storeModel.getGoogle());
				storeBean.setInstagparam(storeModel.getInstagparam());
				if(CommonUserUtility.paparameterNullCheckStringObject(storeModel.getIpaddress())){
					storeBean.setIpaddress(storeModel.getIpaddress());
				}
				
				if(CommonUserUtility.paparameterNullCheckStringObject(storeModel.getEmail())){
					storeBean.setEmail(storeModel.getEmail());
				}
				
				storeBean.setModifiedBy(storeModel.getModifiedBy());
				storeBean.setModifiedOn(storeModel.getModifiedOn());
				storeBean.setName(storeModel.getName());
				storeBean.setPhone(storeModel.getPhone());
				if(CommonUserUtility.paparameterNullCheckObject(storeModel.getStateId())){
					storeBean.setStateId(storeModel.getStateId());
				}
				if(CommonUserUtility.paparameterNullCheckStringObject(storeModel.getStreetNumber())){
					storeBean.setStreetNumber(storeModel.getStreetNumber());
				}
				storeBean.setTwitter(storeModel.getTwitter());
				storeBean.setWebsite(storeModel.getWebsite());
				
				if(CommonUserUtility.paparameterNullCheckStringObject(storeModel.getZipcode())){
					storeBean.setZipcode(storeModel.getZipcode());
				}
				storeBean.setZipcode4(storeModel.getZipcode4());
				storeDao.updateStoreDetails(storeBean);
			}
		}

		LOGGER.info("Exit method updateStoreDetails of ValouxStoreServiceImpl");
	}

}
