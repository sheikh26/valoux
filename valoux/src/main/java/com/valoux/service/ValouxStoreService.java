/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.service;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;

import com.valoux.bean.AgentBean;
import com.valoux.bean.ValouxStoreBean;
import com.valoux.model.AgentModel;
import com.valoux.model.ValouxAgentStoreModel;
import com.valoux.model.ValouxStoreModel;

/**
 * This <java>interface</java> ValouxStoreService defines all abstract methods
 * of store operations.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
public interface ValouxStoreService {

	/**
	 * This method creates a new store.
	 * 
	 * @paparam storeBean
	 *            : Business object carrying all the information related to
	 *            store to be created.
	 * @return ValouxStoreBean : Created ValouxStoreBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public ValouxStoreBean createStore(ValouxStoreModel storeModel, AgentBean agentBean, String agentRole)
			throws Exception;

	/**
	 * This method retrieves all Store Data.
	 * 
	 * @return List<ValouxStoreBean> : list of all the store data
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<ValouxStoreModel> getAllActiveStoreData() throws Exception;

	/**
	 * This method check if store with same information already exist.
	 * 
	 * @paparam storeBean
	 *            : business object holding store information
	 * @return ValouxStoreBean: ValouxStoreBean object if already exist, returns
	 *         null if not exist
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public ValouxStoreBean checkDuplicateStore(ValouxStoreModel storeModel) throws Exception;

	/**
	 * This method save agent address same as store address.
	 * 
	 * @paparam storeId
	 *            : to get the store information.
	 * @paparam agentProfileModel
	 *            : Business object carrying all the information relates to
	 *            agent for save.
	 * @return AgentModel : Business Object holding Agent information
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public AgentModel setAgentAdressAsStoreAddress(Integer storeId, AgentModel agentProfileModel) throws Exception;

	/**
	 * This method retrieves store data based on storeId.
	 * 
	 * @paparam storeId
	 *            : identifier for which store data to retrieved.
	 * @return ValouxStoreBean : Store data
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public ValouxStoreModel getStoreDataByStoreId(Integer storeId) throws Exception;

	/**
	 * This method retrieves agents belonging to a store
	 * 
	 * @paparam storeId
	 *            : identifier for which agent information data to be retrieved
	 * @return : list of agent
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<ValouxAgentStoreModel> getStoreAgentsByStoreId(Integer storeId) throws Exception;

	/**
	 * @TODO working on attaching specific content information. This method
	 *       sends mail to super admin
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void sendMailToSuperadmin(ValouxStoreBean storeBean, AgentBean agentBean, String agentRole) throws Exception;

	/**
	 * This method get all store information
	 * 
	 * @return : List<ValouxStoreModel>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<ValouxStoreModel> getAllStoreData() throws Exception;
	
	/**
	 * This method get all active store data with keyword
	 * @paparam keyword
	 * @return
	 * @throws Exception
	 */
	List<ValouxStoreModel> getAllActiveStoreDatawithKeyWord(String keyword,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;
	
	/**
	 * This method merge store ids
	 * @paparam primaryStoreId
	 * @paparam storeIdsToBeMerged
	 * @throws Exception
	 */
	void mergeStores(Integer primaryStoreId,JSONArray storeIdsToBeMerged) throws Exception;

	/**
	 * @paparam storeModel
	 * @throws Exception
	 */
	public void updateStoreDetails(ValouxStoreModel storeModel) throws Exception;
}
