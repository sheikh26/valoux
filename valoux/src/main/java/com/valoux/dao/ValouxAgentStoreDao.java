/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.ValouxAgentStoreBean;

public interface ValouxAgentStoreDao {
	/**
	 * This method performs save Agent StoreId.
	 * 
	 * @paparam agentStoreBean
	 *            : Business object carrying all the information relates to
	 *            agent store.
	 * @return ValouxAgentStoreBean : Created ValouxAgentStoreBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public ValouxAgentStoreBean saveAgentStoreId(ValouxAgentStoreBean agentStoreBean) throws Exception;

	/**
	 * This method performs get Valoux Agent Store Bean By rKey.
	 * 
	 * @paparam rKey
	 *            : Business object carrying all the information relates to
	 *            agent rKey.
	 * @return ValouxAgentStoreBean : Created ValouxAgentStoreBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public ValouxAgentStoreBean getAgentStoreDataByRelationKey(String rKey) throws Exception;

	/**
	 * This method get the agent store detail by storeId
	 * 
	 * @paparam storeId
	 * @return
	 * @throws Exception
	 */
	public List<ValouxAgentStoreBean> getAgentStoreDataByStoreId(Integer storeId) throws Exception;
	
	/**
	 * This method get agents detail by store id
	 * 
	 * @paparam storeId
	 *            : Business object carrying all the information relates to
	 *            store.
	 * @return : Created ValouxAgentStoreModel list object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxAgentStoreBean> getValouxAgentStoresByStoreId(Integer storeId) throws Exception;
	
	/**
	 * This method merge stores
	 * @paparam primaryStoreId
	 * @paparam storeIdToBeMerged
	 * @throws Exception
	 */
	void mergestore(Integer primaryStoreId, Integer storeIdToBeMerged) throws Exception;
	
	/**
	 * This method get all agent store data
	 * @paparam rKey
	 * @return
	 * @throws Exception
	 */
	List<ValouxAgentStoreBean> getAllAgentStoreDataByRelationKey(String rKey) throws Exception;

}
