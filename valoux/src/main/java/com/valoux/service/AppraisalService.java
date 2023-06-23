/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.service;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;

import com.valoux.bean.AppraisalBean;
import com.valoux.bean.AppraisalCollectionBean;
import com.valoux.bean.AppraisalItemsBean;
import com.valoux.bean.ValouxAccessPermissionBean;
import com.valoux.bean.ValouxAppraisalItemsPriceBean;
import com.valoux.model.AppraisalCollectionModel;
import com.valoux.model.AppraisalItemsModel;
import com.valoux.model.AppraisalModel;
import com.valoux.model.ValouxAccessPermissionModel;
import com.valoux.model.ValouxCollectionItemModel;
import com.valoux.model.ValouxCollectionModel;
import com.valoux.model.ValouxItemModel;

/**
 * This <java>interface</java> AppraisalService defines all abstract methods of
 * appraisal operations.
 * 
 * @author param Sheikh
 * 
 */

public interface AppraisalService {

	/**
	 * This method creates a new Appraisal or update Appraisal.
	 * 
	 * @paparam appraisalBean
	 *            : Business object carrying all the information related to
	 *            appraisal to be created or updated.
	 * @return AppraisalBean : Created AppraisalBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public AppraisalModel createOrUpdateAppraisal(AppraisalModel appraisalModel) throws Exception;

	/**
	 * This method creates a new Appraisal for Items.
	 * 
	 * @paparam appItemsModel
	 *            : Business object carrying all the information related to
	 *            appraisal to be created for Items.
	 * @return UserBean : Created AppraisalItemsBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public AppraisalItemsBean addAppraisalItems(AppraisalItemsModel appItemsModel) throws Exception;

	/**
	 * This method creates a new Appraisal for Collection.
	 * 
	 * @paparam userBean
	 *            : Business object carrying all the information related to user
	 *            to appraisal to be created for Collection.
	 * @return UserBean : Created AppraisalCollectionBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public AppraisalCollectionBean addAppraisalCollection(AppraisalCollectionModel appCollectionModel) throws Exception;

	/**
	 * This method check Existing Appraisal for Items.
	 * 
	 * @paparam appItemsModel
	 *            : Business object carrying all the information related to
	 *            appraisal to be check Existing Appraisal for Items.
	 * @return UserBean : Created AppraisalItemsBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public AppraisalItemsBean checkExistingAppraisalItems(AppraisalItemsModel appItemsModel) throws Exception;

	/**
	 * This method check Existing Appraisal for Collection.
	 * 
	 * @paparam appItemsModel
	 *            : Business object carrying all the information related to
	 *            appraisal to be check Existing Appraisal for Collection.
	 * @return UserBean : Created AppraisalItemsBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public AppraisalCollectionBean checkExistingAppraisalCollection(AppraisalCollectionModel appCollectionModel)
			throws Exception;

	/**
	 * This method get the json array of all appraisal id associated with item
	 * 
	 * @paparam itemId
	 * @return : JSONArray
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public JSONArray getAppraisalIdAssociatedWithItem(Integer itemId) throws Exception;

	/**
	 * This method checks if Appraisal is already registered.
	 * 
	 * @paparam rKey
	 *            : Business object carrying all the information relates to
	 *            Appraisal.
	 * @paparam name
	 * @return boolean : true/false depending on result
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public boolean checkAppraisalAlreadyExist(String rKey, String name) throws Exception;

	/**
	 * This method getall the list of appraisal
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<AppraisalBean> getAllAppraisalListForSupoerAdmin() throws Exception;

	/**
	 * This method returns number of collection associated with appraisal
	 * 
	 * @paparam appraisalId
	 * @return
	 * @throws Exception
	 */
	public Integer getNumberOfCollectionOfAppraisal(Integer appraisalId) throws Exception;

	/**
	 * This method returns number of items associated with items
	 * 
	 * @paparam appraisalId
	 * @return
	 * @throws Exception
	 */
	public Integer getNumberOfItemsOfAppraisal(Integer appraisalId) throws Exception;

	/**
	 * This method checks if Appraisal is already approved.
	 * 
	 * @paparam appraisalId
	 *            ,status : Business object carrying all the information relates
	 *            to Appraisal approval.
	 * @return boolean : true/false depending on result
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public boolean checkAppraisalAlreadyApproved(Integer appraisalId, Integer status) throws Exception;

	/**
	 * This method approved or disapproved Appraisal.
	 * 
	 * @paparam appraisalModel
	 *            : Business object carrying all the information related to
	 *            appraisal to be approved or disapproved.
	 * @return AppraisalBean : Created AppraisalBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public AppraisalBean approvedOrDisapprovedAppraisal(AppraisalModel appraisalModel) throws Exception;

	/**
	 * This method get the appraisal detail associated with item
	 * 
	 * @paparam itemId
	 * @return
	 * @throws Exception
	 */
	public List<AppraisalBean> getAppraisalDetailAssociatedWithItem(Integer itemId) throws Exception;

	/**
	 * This method will fetch list of Appraisal for user.
	 * 
	 * @paparam relationKey
	 *            : Business object carrying list of Appraisal.
	 * @return List<AppraisalModel> : List<AppraisalModel> object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<AppraisalModel> getUserAppraisalList(String relationKey,Integer aStatus) throws Exception;

	/**
	 * This method returns the count of item associated with collection
	 * 
	 * @paparam vcid
	 * @return : Integer
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public Integer getItemAssociatedWithCollection(Integer vcid) throws Exception;

	/**
	 * This method returns the count of collection associated with appraisal
	 * 
	 * @paparam appraisalId
	 * @return : Integer
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public Integer getNumberOfCollectionAssociatedWithAppraisal(Integer appraisalId) throws Exception;

	/**
	 * This method returns the count of item associated with appraisal
	 * 
	 * @paparam appraisalId
	 * @return : Integer
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public Integer getNumberOfItemAssociatedWithAppraisal(Integer appraisalId) throws Exception;

	/**
	 * Method will give list of appraisals of collection
	 * 
	 * @paparam collectionId
	 *            : Business object carrying all the information related to
	 *            appraisal
	 * @return : List<AppraisalCollectionBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<AppraisalCollectionModel> getAppraisalCollectionBeansByCollectionId(Integer collectionId)
			throws Exception;

	/**
	 * Method will give appraisal bean
	 * 
	 * @paparam appraisalId
	 *            : Business object carrying all the information related to
	 *            appraisal
	 * @return : AppraisalBean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public AppraisalBean getAppraisalBeanById(Integer appraisalId) throws Exception;

	/**
	 * Method will give list of appraisals
	 * 
	 * @paparam appraisalId
	 *            : Business object carrying all the information related to
	 *            appraisal
	 * @return : List<AppraisalCollectionBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<AppraisalCollectionModel> getAppraisalBeansByAppraisalId(Integer appraisalId) throws Exception;

	/**
	 * Method will give collection bean
	 * 
	 * @paparam collectionId
	 *            : Business object carrying all the information related to
	 *            collection
	 * @return : ValouxCollectionBean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public ValouxCollectionModel getCollectionBeanByCollectionId(Integer collectionId) throws Exception;

	/**
	 * Method for fetching collection not in a appraisal
	 * 
	 * @paparam rKey
	 * @paparam appraisalId
	 * @return : List<ValouxCollectionModel>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<ValouxCollectionModel> getCollectionListNotInAppraisal(String rKey, Integer appraisalId)
			throws Exception;

	/**
	 * This method checks whether appraisal name exist for user
	 * 
	 * @paparam publicKey
	 * @paparam appraisalName
	 * @return : Boolean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	Boolean checkAppraisalNameExistForUser(String publicKey, String appraisalName, Integer appraisalId)
			throws Exception;

	/**
	 * Method will give list of appraisals
	 * 
	 * @paparam appraisalId
	 *            : Business object carrying all the information related to
	 *            appraisal
	 * @return : List<AppraisalItemsBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<AppraisalItemsModel> getAppraisalItemsBeansByAppraisalId(Integer appraisalId) throws Exception;

	/**
	 * This mehtod get all appraisal detail not associated with item
	 * 
	 * @paparam itemId
	 * @return
	 * @throws Exception
	 */
	List<AppraisalBean> getAppraisalDetailNotAssociatedWithItem(String rKey, Integer itemId) throws Exception;

	/**
	 * Method will give items bean
	 * 
	 * @paparam itemId
	 *            : Business object carrying all the information related to
	 *            items
	 * @return : ValouxItemBean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public ValouxItemModel getItemsBeanByItemId(Integer itemId) throws Exception;

	/**
	 * Method for fetching items not in a appraisal
	 * 
	 * @paparam rKey
	 * @paparam appraisalId
	 * @return : List<ValouxItemModel>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<ValouxItemModel> getItemsListNotInAppraisal(String rKey, Integer appraisalId) throws Exception;

	/**
	 * This method delete collection from appraisal.
	 * 
	 * @paparam collectionId
	 * @return : Boolean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public Boolean deleteCollectionFromAppraisal(JSONArray collectionId, Integer appraisalId) throws Exception;

	/**
	 * This method delete item for appraisal.
	 * 
	 * @paparam itemId
	 * @return : Boolean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public Boolean deleteItemFromAppraisal(JSONArray itemId, Integer appraisalId) throws Exception;

	/**
	 * This method add item in multiple appraisals
	 * 
	 * @paparam appraisalItemsModel
	 * @return
	 * @throws Exception
	 */
	AppraisalItemsBean addItemInAppraisals(AppraisalItemsModel appraisalItemsModel) throws Exception;

	/**
	 * This method delete single item from multiple appraisals
	 * 
	 * @paparam appraisalId
	 * @paparam itemId
	 * @return
	 * @throws Exception
	 */
	Boolean deleteSingleItemFromAppraisals(JSONArray appraisalId, Integer itemId) throws Exception;

	/**
	 * This method add access permission for user via agent
	 * @paparam accessPermissionModel
	 * @throws Exception
	 */
	public void addValouxAccessPermission(ValouxAccessPermissionModel accessPermissionModel) throws Exception;
	
	/**
	 * This method will get all appraisal list of user with keyword
	 * @paparam rKey
	 * @paparam keyword
	 * @return
	 * @throws Exception
	 */
	List<AppraisalBean> getAppraisalListAssociatedWithUserAndHaveKeyword(String rKey,String keyword,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;
	
	/**
	 * This method with appraisal of agent with keyword
	 * @paparam rKey
	 * @paparam keyword
	 * @return
	 * @throws Exception
	 */
	List<AppraisalBean> getAppraisalListAssociatedWithAgentAndHaveKeyword(String rKey,String keyword,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;

	/**
	 * This method will give list of agent having access permission of consumers
	 * @paparam agentKey
	 * @return
	 * @throws Exception
	 */
	public List<ValouxAccessPermissionBean> getAgentAccessPermissionUsers(String agentKey) throws Exception;
	/**
	 * This method returns the count of item associated with collection
	 * 
	 * @paparam vcid
	 * @return : Integer
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<ValouxCollectionItemModel> getItemAssociatedWithCollectionList(Integer vcid) throws Exception;
	
	/**
	 * This method get appraisal list associated with agent and have particular status
	 * @paparam rKey
	 * @paparam aStatus
	 * @return
	 * @throws Exception
	 */
	List<AppraisalModel> getAppraisalListAssociatedWithAgentWithStatus(String rKey, Integer aStatus)
			throws Exception;

	
	/**
	 * Method to change appraisal status
	 * @paparam appraisalModel
	 * @return
	 * @throws Exception
	 */
	public AppraisalModel changeStatusToAppraised(AppraisalModel appraisalModel) throws Exception;
	
	/**
	 * This method get items appraised price by appraisal
	 * @paparam itemId
	 * @paparam appraisalId
	 * @return
	 * @throws Exception
	 */
	ValouxAppraisalItemsPriceBean getAppraisedItemPriceOfAppraisal(Integer itemId,Integer appraisalId) throws Exception;

	/**
	 * @paparam appraisalModel
	 * @return
	 * @throws Exception
	 */
	public AppraisalModel changeStatusToUnAppraised(AppraisalModel appraisalModel) throws Exception;
	
	/**
	 * @paparam appraisalId
	 * @return
	 * @throws Exception
	 */
	public boolean checkAppraisedItemFinalValue(Integer appraisalId) throws Exception;

	/**
	 * @paparam appraisalModel
	 * @throws Exception
	 */
	public void addAppraisalPdfFile(AppraisalModel appraisalModel) throws Exception;

	/**
	 * @paparam appraisalId
	 * @return
	 * @throws Exception
	 */
	public AppraisalModel getAppraisalDetailsById(Integer appraisalId) throws Exception;

	/**
	 * @return
	 * @throws Exception
	 */
	public List<AppraisalModel> getAllAppraisalsList() throws Exception;

	/**
	 * @paparam userPublicKey
	 * @paparam limit
	 * @return
	 * @throws Exception
	 */
	public List<AppraisalModel> getTopAppraisalsListByUserIdAndLimit(
			String userPublicKey, Integer limit) throws Exception;

	/**
	 * @paparam appraisalId
	 * @paparam userPublicKey
	 * @return
	 * @throws Exception
	 */
	public Boolean deleteAppraisalAndAllDetails(Integer appraisalId,
			String userPublicKey) throws Exception;
	
	/**
	 * This method delete consumer appraisal
	 * @paparam userPublicKey
	 * @return
	 * @throws Exception
	 */
	Boolean deleteConsumerAppraisalAndAllDetails(String userPublicKey) throws Exception;

}
