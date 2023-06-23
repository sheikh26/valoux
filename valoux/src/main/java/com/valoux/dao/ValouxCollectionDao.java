/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.ValouxCollectionBean;
import com.valoux.model.ValouxCollectionModel;

public interface ValouxCollectionDao {


	/**
	 * This method get collection Details.
	 * 
	 * @paparam collectionId
	 *            : Business object carrying all the information relates to
	 *            collection.
	 * @return ValouxCollectionBean : Created ValouxCollectionBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */

	public ValouxCollectionBean getCollectionBeanByCollectionId(Integer collectionId) throws Exception;

	/**
	 * Method for fetching collection not in a appraisal
	 * 
	 * @paparam publicKey
	 * @paparam objects
	 * @return : List<ValouxCollectionBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxCollectionBean> getCollectionListNotInAppraisal(String publicKey, Object[] objects) throws Exception;
	/**
	 * Method used to add update collection details
	 * 
	 * @paparam collectionBean
	 *            : Business object carrying all the information relates to
	 *            Collection.
	 * @paparam requestType
	 * @return ValouxCollectionBean : Created Collection object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	ValouxCollectionBean addUpdateCollectionDetails(ValouxCollectionBean collectionBean, String requestType)
			throws Exception;

	/**
	 * Method used to get collection details
	 * 
	 * @paparam collectionId
	 *            : Business object carrying all the id relates to Collection
	 *            data.
	 * @return ValouxCollectionBean : Collection object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	ValouxCollectionBean getCollectionDetailsById(int collectionId) throws Exception;

	

	/**
	 * Method used to get all user collections
	 * 
	 * @paparam relationKey
	 *            : Business object carrying user data.
	 * @return List<ValouxCollectionBean> : Collection object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxCollectionBean> getCollectionDetailsByUserId(String relationKey,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;

	
	
	
	

	/**
	 * Method for check if collection name already exists for collection
	 * 
	 * @paparam publicKey
	 *            : Business object carrying user data.
	 * @paparam collectionName
	 *            : Business object carrying collection data.
	 * @return : List<ValouxCollectionBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxCollectionBean> checkCollectionNameExistForUser(String publicKey, String collectionName)
			throws Exception;

	
	/**
	 * This method get all collection except collection associated with item
	 * 
	 * @paparam publicKey
	 * 
	 * @paparam collectionArray
	 * @return
	 * @throws Exception
	 */
	List<ValouxCollectionBean> getCollectionListNotAssociatedWithItem(String publicKey, Object[] collectionArray)
			throws Exception;
	

	/**
	 * This method get all collection of user with keyword
	 * @paparam publicKey
	 * @paparam keyword
	 * @paparam objects
	 * @return
	 */
	List<ValouxCollectionBean> getCollectionListForGlobalSearchByName(String publicKey,String keyword,Object[] objects,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;

	/**
	 * @paparam userPublicKey
	 * @paparam limit
	 * @return
	 * @throws Exception
	 */
	public List<ValouxCollectionBean> getTopCollectionsListByUserIdAndLimit(
			String userPublicKey, Integer limit) throws Exception;

	/**
	 * Method for delete item component object
	 * 
	 * @paparam itemId 
	 * @paparam componentId
	 * @return
	 * @throws Exception  : error during the execution of operation
	 */
	void deleteAnyBeanByObject(Object objectBean) throws Exception;
}
