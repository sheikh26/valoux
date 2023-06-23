/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.service;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;

import com.valoux.bean.ValouxCollectionImageBean;
import com.valoux.model.AppraisalCollectionModel;
import com.valoux.model.AppraisalModel;
import com.valoux.model.ValouxCollectionImageModel;
import com.valoux.model.ValouxCollectionItemModel;
import com.valoux.model.ValouxCollectionModel;

/**
 * This interface CollectionService use to perform all our service related
 * logics for the collections.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
public interface CollectionService {

	/**
	 * This method creates a new collection for user.
	 * 
	 * @paparam collectionModel
	 *            : Business object carrying all the information related to user
	 *            collection to be created or updated.
	 * @paparam requestType
	 * @return ValouxCollectionBean : Created ValouxCollectionBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public ValouxCollectionModel addUpdateCollectionDetails(ValouxCollectionModel collectionModel, String requestType)
			throws Exception;

	/**
	 * This method creates a new collection for user.
	 * 
	 * @paparam collectionId
	 *            : Business object carrying all the information related to user
	 *            collection to be fetch.
	 * @return ValouxCollectionBean : Created ValouxCollectionBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public ValouxCollectionModel getCollectionDetailsById(int collectionId) throws Exception;

	/**
	 * This method creates a new collection for user.
	 * 
	 * @paparam collectionItemModel
	 *            : Business object carrying all the information related to user
	 *            collection items to be save.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void addItemsInCollection(ValouxCollectionItemModel collectionItemModel) throws Exception;

	/**
	 * This method will fetch list of collections for user.
	 * 
	 * @paparam relationKey
	 *            : Business object carrying list of collections.
	 * @return List<ValouxCollectionModel> : List<ValouxCollectionModel> object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<ValouxCollectionModel> getUserCollectionsList(String relationKey,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;

	/**
	 * This method will fetch list of collection items.
	 * 
	 * @paparam vcid
	 *            : Business object carrying collection id.
	 * @return List<ValouxCollectionModel> : List<ValouxCollectionModel> object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<ValouxCollectionItemModel> getUserCollectionItemsList(Integer collectionId) throws Exception;

	/**
	 * This method will delete list of collection items.
	 * 
	 * @paparam itemModels
	 *            : Business object carrying collection items.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void deleteUserCollectionItemsList(Integer vcid) throws Exception;

	/**
	 * This method add item in collection
	 * 
	 * @paparam itemId
	 * @paparam collectionId
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void addItemInCollection(Integer itemId, JSONArray collectionId) throws Exception;

	/**
	 * This method check whether item is already added in collection or not
	 * 
	 * @paparam itemId
	 * @paparam collectionId
	 * @return : Boolean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public Boolean checkItemAlreadyAddedInCollection(Integer itemId, JSONArray collectionId) throws Exception;

	/**
	 * This method delete item for collection.
	 * 
	 * @paparam itemId
	 * @paparam collectionId
	 * @return : Boolean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public Boolean deleteItemFromCollection(Integer itemId, Integer collectionId) throws Exception;

	/**
	 * This method get the json array of all collection id associated with item
	 * 
	 * @paparam itemId
	 * @return : JSONArray
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public JSONArray getCollectionIdAssociatedWithItem(Integer itemId) throws Exception;

	/**
	 * This method get the collection image details associated with collection
	 * id
	 * 
	 * @paparam collectionImageModel
	 * @return : List<ValouxCollectionImageBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<ValouxCollectionImageBean> addCollectionImageDetails(
			List<ValouxCollectionImageModel> collectionImageModel) throws Exception;

	/**
	 * Method for fetching collection image details
	 * 
	 * @paparam vcid
	 * @return : List<ValouxCollectionImageBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<ValouxCollectionImageBean> getCollectionImageDetailsById(Integer vcid) throws Exception;

	/**
	 * Method for deleting image from collection
	 * 
	 * @paparam collectionId
	 * @paparam imageId
	 * @return : Boolean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public Boolean deleteImageDocumentByCollectionAndImageId(Integer collectionId, Integer imageId) throws Exception;

	/**
	 * Method for check if collection name already exists for collection
	 * 
	 * @paparam publicKey
	 * @paparam collectionName
	 * @paparam collectionId
	 * @return
	 * @throws Exception
	 */
	public Boolean checkCollectionNameExistForUser(String publicKey, String collectionName, Integer collectionId)
			throws Exception;

	/**
	 * This method get the detail of all collection associated with item
	 * 
	 * @paparam itemId
	 * @return
	 * @throws Exception
	 */
	public List<ValouxCollectionModel> getCollectionDetailAssociatedWithItem(Integer itemId) throws Exception;

	/**
	 * This method get collection detail which is not associated with item
	 * 
	 * @paparam rKey
	 * 
	 * @paparam itemId
	 * @return
	 * @throws Exception
	 */
	public List<ValouxCollectionModel> getCollectionDetailNoteAssociatedWithItem(String rKey, Integer itemId)
			throws Exception;

	/**
	 * This method delete appraisal for collection.
	 * 
	 * @paparam appraisalId
	 *            : Business object carrying collection items.
	 * @paparam collectionId
	 *            : Business object carrying collection items.
	 * @return : Boolean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public Boolean deleteAppraisalFromCollection(Integer appraisalId, Integer collectionId) throws Exception;

	/**
	 * Method for fetching consumer appraisal not in collections
	 * 
	 * @paparam rKey
	 *            : Business object carrying collection items.
	 * @paparam collectionId
	 *            : Business object carrying collection items.
	 * @return
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<AppraisalModel> getConsumerAppraisalsNotInCollection(String rKey, Integer collectionId)
			throws Exception;

	/**
	 * Method for add consumer appraisals in collection
	 * 
	 * @paparam appraisalCollectionModel
	 *            : Business object carrying collection items.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void addAppraisalsInCollection(AppraisalCollectionModel appraisalCollectionModel) throws Exception;
	
	/**
	 * This method will get collection of user with keyword as name
	 * @paparam rKey
	 * @paparam keyword
	 * @return
	 * @throws Exception
	 */
	List<ValouxCollectionModel> getCollectionListAssociatedWithUserAndHaveKeyword(String rKey,String keyword,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;
	
	/**
	 * This method will get collectionassociated with agent and have keyword
	 * @paparam rKey
	 * @paparam keyword
	 * @return
	 * @throws Exception
	 */
	List<ValouxCollectionModel> getCollectionListAssociatedWithAgentAndHaveKeyword(String rKey,String keyword,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;

	/**
	 * @paparam userPublicKey
	 * @paparam limit
	 * @return
	 * @throws Exception
	 */
	public List<ValouxCollectionModel> getTopCollectionsListByUserIdAndLimit(
			String userPublicKey, Integer limit) throws Exception;

	/**
	 * @paparam collectionId
	 * @paparam userPublicKey
	 * @return
	 * @throws Exception
	 */
	public Boolean deleteCollectionAndAllDetails(Integer collectionId,
			String userPublicKey) throws Exception;
	
	/**
	 * This methos delete consumer collections
	 * @paparam userPublicKey
	 * @return
	 * @throws Exception
	 */
	Boolean deleteConsumerCollectionAndAllDetails(
			String userPublicKey) throws Exception ;

}
