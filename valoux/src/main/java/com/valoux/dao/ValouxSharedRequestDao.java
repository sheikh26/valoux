/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.ValouxSharedRequestBean;

public interface ValouxSharedRequestDao {

	/**
	 * This method saves shared request object
	 * 
	 * @paparam sharedRequestBean
	 * @return
	 * @throws Exception
	 */
	ValouxSharedRequestBean saveSharedRequest(ValouxSharedRequestBean sharedRequestBean) throws Exception;

	/**
	 * This method get shared bean on the basis of shared by
	 * 
	 * @paparam sharedBy
	 * @paparam itemType
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getSharedRequestListBySharedByAndItemType(String sharedBy, Integer itemType,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder)
			throws Exception;

	/**
	 * This method get list of shared bean on the basis of item id
	 * 
	 * @paparam itemId
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getSharedRequestListByItemId(Integer itemId,Integer shareItemType) throws Exception;

	/**
	 * This method get list od shared request on the basis of share to email
	 * 
	 * @paparam sharedToEmail
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getSharedRequestListBySharedToEmail(String sharedToEmail) throws Exception;

	/**
	 * This method update shaered request bean
	 * 
	 * @paparam sharedRequestBean
	 * @return
	 * @throws Exception
	 */
	ValouxSharedRequestBean updateSharedRequestBean(ValouxSharedRequestBean sharedRequestBean) throws Exception;

	/**
	 * This method get shared bean on basis of item id and sharedToEmail and
	 * SharedTo
	 * 
	 * @paparam itemId
	 * @paparam sharedToEmail
	 * @paparam sharedTo
	 * @return
	 * @throws Exception
	 */
	ValouxSharedRequestBean getSharedRequestBeanByItemIdSharedToEmailAndSharedTo(Integer itemId, String sharedToEmail,
			String sharedTo, Integer sharedItemType) throws Exception;

	/**
	 * This method get shared bean on basis of item id and sharedToEmail and
	 * SharedTo having distinct item id
	 * 
	 * @paparam sharedBy
	 * @paparam itemType
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getSharedRequestListBySharedByAndItemTypeDistinctItemId(String sharedBy,
			Integer itemType,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;

	/**
	 * This method get shared bean share to user
	 * 
	 * @paparam sharedBy
	 * @paparam itemType
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getSharedRequestItemListSharedToUser(String sharedTo, Integer itemType,
			Integer approvedStatus,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;

	/**
	 * This method delete item shared with user
	 * 
	 * @paparam sharedRequestBean
	 * @throws Exception
	 */
	void deleteSharedItem(ValouxSharedRequestBean sharedRequestBean) throws Exception;

	/**
	 * This method get shared request list to which item is shared
	 * 
	 * @paparam sharedBy
	 * @paparam itemType
	 * @paparam itemId
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getSharedRequestListBySharedByAndItemType(String sharedBy, Integer itemType,
			Integer itemId, String sharedTo,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;
	/**
	 * This method get list of item shared to user in requested state
	 * 
	 * @paparam sharedTo
	 * @paparam itemType
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getRequetedItemListSharedToUser(String sharedTo, Integer itemType,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;

	/**
	 * This method get list of item shared to user
	 * 
	 * @paparam sharedTo
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getSharedRequestListBySharedTo(String sharedTo,Integer sharedItemType,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;

	/**
	 * This method get list of shared request bean on the basis of
	 * sharedRequestId
	 * 
	 * @paparam sharedRequestId
	 * @return
	 * @throws Exception
	 */
	ValouxSharedRequestBean getSaredRequestBySharedRequestId(Integer sharedRequestId) throws Exception;

	/**
	 * This method get list of share request items which is not added in
	 * collection
	 * 
	 * @paparam sharedTo
	 * @paparam objects
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getShareItemListNotInItemList(String sharedTo, Object[] objects, String sharedBy)
			throws Exception;

	/**
	 * This method get the list of collection shared request except collection
	 * id passed in object array
	 * 
	 * @paparam sharedTo
	 * @paparam objects
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getShareCollectionListNotInItemList(String sharedTo, Object[] objects, String sharedBy)
			throws Exception;
	
	/**
	 * This method get appraisal list shared to agent and not in object array
	 * @paparam sharedTo
	 * @paparam objects
	 * @paparam sharedBy
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getShareAppraisalListNotInItemList(String sharedTo, Object[] objects,String sharedBy) throws Exception;

	
	/**
	 * This method get list of item shared to agent in requested state
	 * 
	 * @paparam relationKey
	 * @return ValouxSharedRequestBean
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getRequetedItemListSharedToAgent(String relationKey) throws Exception;

	/**
	 * Method for fetching user list shared with agent
	 * @paparam sharedTo
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getUserListSharedToAgent(String sharedTo) throws Exception;

	/**
	 * Method for fetching user list shared with agent
	 * @paparam sharedBy
	 * @paparam sharedTo
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getUserListSharedToAgentSharedByUser(
			String sharedBy, String sharedTo) throws Exception;
	
	/**
	 * This method will get item shared to agent by consumer
	 * @paparam sharedTo
	 * @paparam itemType
	 * @paparam approvedStatus
	 * @paparam sharedBy
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getSharedRequestItemListSharedToUserByConsumer(String sharedTo, Integer itemType,
			Integer approvedStatus,String sharedBy,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;

	/**
	 * @paparam relationKey
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getListOfRequestedAndAcceptedSharedItemsToAgent(
			String relationKey)  throws Exception;
	
	/**
	 * This method get all bean shared by user
	 * @paparam sharedBy
	 * @return
	 */
	List<ValouxSharedRequestBean> getAllBeanSharedByUser(
			String sharedBy) throws Exception;
	
	/**
	 * This method get all bean shared to user
	 * @paparam sharedTo
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getAllBeanSharedToUser(String sharedTo) throws Exception;
	
}
