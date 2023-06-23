/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.service;

import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;

import com.valoux.bean.AppraisalBean;
import com.valoux.bean.ValouxItemBean;
import com.valoux.bean.ValouxItemComponentBean;
import com.valoux.bean.ValouxSharedRequestBean;
import com.valoux.model.AppraisalModel;
import com.valoux.model.ItemComponentCertificateModel;
import com.valoux.model.ItemImageModel;
import com.valoux.model.ValouxCollectionModel;
import com.valoux.model.ValouxComponentsImageModel;
import com.valoux.model.ValouxDiamondModel;
import com.valoux.model.ValouxGemstoneModel;
import com.valoux.model.ValouxInclusionModel;
import com.valoux.model.ValouxItemComponentModel;
import com.valoux.model.ValouxItemModel;
import com.valoux.model.ValouxItemTypeModel;
import com.valoux.model.ValouxMetalModel;
import com.valoux.model.ValouxPearlModel;
import com.valoux.model.ValouxSharedRequestModel;

/**
 * This <java>class</java> provides service contracts for different operations
 * related to items
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
public interface ItemService {

	/**
	 * This method creates new item
	 * 
	 * @paparam valouxItemModel
	 *            : Business object carrying all the information relates to
	 *            Item.
	 * @return ValouxItemBean : newly created Item object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public ValouxItemBean addItem(ValouxItemModel valouxItemModel) throws Exception;

	/**
	 * This method retrieves list of all the item type.
	 * 
	 * @return List<ValouxItemTypeBean> : list of all the item type
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<ValouxItemTypeModel> getMasterDataForItemType() throws Exception;

	/**
	 * This method retrieves list of all the items.
	 * 
	 * @paparam publicKey
	 * @return List<ValouxItemBean> : list of all the items
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<ValouxItemModel> getAllItemList(String publicKey,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;

	/**
	 * This method save the item image
	 * 
	 * @paparam imageName
	 * @paparam imageContent
	 * @paparam itemId
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void saveItemImage(JSONArray imagesjArray, Integer itemId, String rKey) throws Exception;

	/**
	 * This method returns the item image model list
	 * 
	 * @paparam itemId
	 * @return
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<ItemImageModel> getItemImageModelList(Integer itemId) throws Exception;

	/**
	 * This method save the document of itemreceipt
	 * 
	 * @paparam itemReceiptName
	 * @paparam itemReceiptContent
	 * @paparam itemId
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void saveItemReceipt(String itemReceiptName, String itemReceiptContent, Integer itemId, String rKey)
			throws Exception;

	/**
	 * This method save the document of item certificate
	 * 
	 * @paparam itemCertificateName
	 * @paparam itemCertificateContent
	 * @paparam itemId
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void saveItemCertificate(String itemCertificateName, String itemCertificateContent, Integer itemId,
			String rKey) throws Exception;

	/**
	 * This method get the item detail
	 * 
	 * @paparam itemId
	 * @paparam rKey
	 * @return : Created ValouxItemModel object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	ValouxItemModel updateItemDetail(Integer itemId, String rKey, ValouxItemModel itemModel) throws Exception;

	/**
	 * This method will items details by id.
	 * 
	 * @paparam itemId
	 *            : Business object carrying item id.
	 * @return ValouxItemModel : Created ValouxItemModel object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	ValouxItemModel getValouxItemDetailsById(Integer itemId) throws Exception;

	/**
	 * This method update the itemImage profile,certificate and receipt document
	 * 
	 * @paparam imagesjArray
	 * @paparam itemId
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	void updateItemImageDocument(JSONArray imagesjArray, String itemReceiptContent, String itemReceiptName,
			String itemCertificateName, String itemCertificateContent, Integer itemId, String rKey) throws Exception;

	/**
	 * This method delete the particular image
	 * 
	 * @paparam itemId
	 * @paparam imageId
	 * @return : Boolean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public Boolean deleteItemDocument(Integer itemId, Integer imageId) throws Exception;

	/**
	 * Method for fetching items not in a collection
	 * 
	 * @paparam rKey
	 * @paparam collectionId
	 * @return : List<ValouxItemModel>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<ValouxItemModel> getConsumerItemsNotInCollection(String rKey, Integer collectionId) throws Exception;

	/**
	 * This method return the number of item associated with store
	 * 
	 * @paparam storeId
	 * @return : Integer
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public Integer getNumberOfItemAssociatedWithStore(Integer storeId) throws Exception;

	/**
	 * This method returns the map of item type
	 * 
	 * @return : Map<Integer, String>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public Map<Integer, String> getMasterDataForItemTypeMap() throws Exception;

	/**
	 * This method checks whether item name exist for user
	 * 
	 * @paparam publicKey
	 * @paparam itemName
	 * @return : Boolean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	Boolean checkItemNameExistForUser(String publicKey, String itemName, Integer itemId) throws Exception;

	/**
	 * This method used to save update item components
	 * 
	 * @paparam valouxItemComponentModel
	 * @return List<ValouxItemComponentBean>
	 * @throws Exception
	 */
	public List<ValouxItemComponentBean> addValouxItemComponents(List<ValouxItemComponentModel> valouxItemComponentModel)
			throws Exception;

	/**
	 * Method for fetching components of item
	 * 
	 * @paparam itemId
	 * @return List<ValouxItemComponentBean>
	 * @throws Exception
	 */
	public List<ValouxItemComponentModel> getComponentsByItemId(Integer itemId) throws Exception;

	/**
	 * Method for delete component from item
	 * 
	 * @paparam itemId
	 * @paparam componentId
	 * @return
	 * @throws Exception
	 */
	public ValouxItemComponentBean deleteComponentFromItem(Integer itemId, Integer componentId) throws Exception;

	/**
	 * This method first send email to user to which item is share and then
	 * 
	 * @paparam sharedRequestModel
	 * @paparam sharedToArray
	 * @paparam sharedToEmailArray
	 * @return
	 * @throws Exception
	 */
	public List<ValouxSharedRequestModel> createSharedRequestAndSendEmail(ValouxSharedRequestModel sharedRequestModel,
			String[] sharedToArray, String[] sharedToEmailArray) throws Exception;

	/**
	 * Method for adding item component diamond valoux property
	 * 
	 * @paparam diamondModel
	 * @paparam requestType
	 * @throws Exception
	 */
	public void addValouxComponentDiamondProperty(ValouxDiamondModel diamondModel, String requestType) throws Exception;

	/**
	 * Method for adding item component gemstone valoux property
	 * 
	 * @paparam gemstoneModel
	 * @throws Exception
	 */
	public void addValouxComponentGemstoneProperty(ValouxGemstoneModel gemstoneModel) throws Exception;

	/**
	 * Method for adding item component pearl valoux property
	 * 
	 * @paparam pearlModel
	 * @throws Exception
	 */
	public void addValouxComponentPearlProperty(ValouxPearlModel pearlModel) throws Exception;

	/**
	 * Method for adding item component metal valoux property
	 * 
	 * @paparam metalModel
	 * @throws Exception
	 */
	public void addValouxComponentMetalProperty(ValouxMetalModel metalModel) throws Exception;

	/**
	 * Method for adding item component valoux images
	 * 
	 * @paparam valouxComponentsImageModel
	 * @throws Exception
	 */
	public void addValouxComponentsImageDetails(List<ValouxComponentsImageModel> valouxComponentsImageModel)
			throws Exception;

	/**
	 * This method get list of contact who is already shared
	 * 
	 * @paparam sharedBy
	 * @paparam itemType
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getListOfSharedContacts(String sharedBy, Integer itemType,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;

	/**
	 * Method for adding item component diamond certificate
	 * 
	 * @paparam certificateModel
	 * @throws Exception
	 */
	public void addItemComponentCertificate(ItemComponentCertificateModel certificateModel) throws Exception;

	/**
	 * This method get count of agent and consumer associated with item
	 * 
	 * @paparam sharedItemId
	 * @return
	 * @throws Exception
	 */
	Map<Integer, Integer> getNumberOfAgentAndConsumerToWhichItemIsShared(Integer sharedItemId,Integer sharedItemType) throws Exception;

	/**
	 * this method checks if new user registered email is already have item then
	 * update public key of that request
	 * 
	 * @paparam emailid
	 * @paparam publicKey
	 * @paparam role
	 * @throws Exception
	 */
	void updateSharedRequestForNewRegistration(String emailid, String publicKey, String role) throws Exception;

	/**
	 * This method get list of shared items
	 * 
	 * @paparam sharedBy
	 * @paparam itemType
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getListOfSharedItems(String sharedBy, Integer itemType, Integer sharedWith,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder)
			throws Exception;

	/**
	 * This metho get list of item share to user
	 * 
	 * @paparam sharedTo
	 * @paparam itemType
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getListOfSharedItemsToUser(String sharedTo, Integer itemType, Integer approvedStatus,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder)
			throws Exception;

	/**
	 * Method for fetching item component
	 * 
	 * @paparam itemId
	 * @paparam componentId
	 * @return
	 * @throws Exception
	 */
	public ValouxItemComponentModel getComponentsByItemAndComponentId(Integer itemId, Integer componentId)
			throws Exception;

	/**
	 * Method for fetching item component certificate
	 * 
	 * @paparam itemId
	 * @paparam componentId
	 * @return
	 * @throws Exception
	 */
	public ItemComponentCertificateModel getComponentCertificateBeanByItemAndComponentId(Integer itemId,
			Integer componentId) throws Exception;

	/**
	 * Method for fetching item component diamond
	 * 
	 * @paparam itemId
	 * @paparam componentId
	 * @return
	 * @throws Exception
	 */
	public ValouxDiamondModel getComponentDiamondBeanByItemAndComponentId(Integer itemId, Integer componentId)
			throws Exception;

	/**
	 * Method for fetching item images list
	 * 
	 * @paparam itemId
	 * @paparam componentId
	 * @return
	 * @throws Exception
	 */
	public List<ValouxComponentsImageModel> getComponentsImageModelByComponentIdAndType(Integer componentId)
			throws Exception;

	/**
	 * This method delete shared item
	 * 
	 * @paparam sharedRequestModel
	 * @paparam sharedToArray
	 * @paparam sharedToEmailArray
	 * @throws Exception
	 */
	void unShareItemAndSendEmail(ValouxSharedRequestModel sharedRequestModel, JSONArray shareToArray) throws Exception;

	/**
	 * This method get list of share request
	 * 
	 * @paparam sharedBy
	 * @paparam itemType
	 * @paparam itemId
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getListOfSharedRequest(String sharedBy, Integer itemType, Integer itemId,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder)
			throws Exception;

	/**
	 * This method delete item component image
	 * 
	 * @paparam componentId
	 * @paparam imageId
	 * @return
	 * @throws Exception
	 */
	public Boolean deleteItemComponentDocument(Integer componentId, Integer imageId) throws Exception;

	/**
	 * This method get item shared to user in requested state
	 * 
	 * @paparam sharedTo
	 * @paparam itemType
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getListOfRequestedSharedItemsToUser(String sharedTo, Integer itemType,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder)
			throws Exception;

	/**
	 * This method get list of share request shared to user
	 * 
	 * @paparam sharedTo
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getListOfSharedItemsBySharedTo(String sharedTo,Integer sharedItemType,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;

	/**
	 * This method approve or disapprove share request
	 * 
	 * @paparam sharedRequestId
	 * @paparam approvedStatus
	 * @return
	 * @throws Exception
	 */
	Boolean approveOrRejectSharedRequest(Integer sharedRequestId, Integer approvedStatus) throws Exception;

	/**
	 * This method get list of item detail which is shared with agent and not
	 * added n collection
	 * 
	 * @paparam sharedTo
	 * @paparam collectionId
	 * @return
	 */
	List<ValouxItemModel> getAgentSharedItemsNotInCollection(String sharedTo, Integer collectionId, String sharedBy)
			throws Exception;

	/**
	 * This method get list of item shared to agent and not in appraisal
	 * 
	 * @paparam sharedTo
	 * @paparam appraisalId
	 * @return
	 * @throws Exception
	 */
	List<ValouxItemModel> getAgentSharedItemsNotInAppraisal(String sharedTo, Integer appraisalId,
			String sharedBy) throws Exception;

	/**
	 * This method get all collection shared to agent and not in appraisal
	 * 
	 * @paparam sharedTo
	 * @paparam appraisalId
	 * @return
	 * @throws Exception
	 */
	List<ValouxCollectionModel> getAgentSharedCollectionNotInAppraisal(String sharedTo, Integer appraisalId,
			String sharedBy) throws Exception;

	/**
	 * This method get collection shared to agent and not added in item
	 * 
	 * @paparam sharedTo
	 * @paparam itemId
	 * @paparam sharedBy
	 * @return
	 * @throws Exception
	 */
	List<ValouxCollectionModel> getAgentSharedCollectionNotInItem(String sharedTo, Integer itemId, String sharedBy)
			throws Exception;
	
	/**
	 * This method get list of appraisal shared to agent and not added in collection
	 * @paparam sharedTo
	 * @paparam collectionId
	 * @paparam sharedBy
	 * @return
	 * @throws Exception
	 */
	List<AppraisalModel> getAgentSharedAppraisalsNotInCollection(String sharedTo, Integer collectionId,String sharedBy)
			throws Exception;
	/**
	 * This method get appraisal list not in item shared to agent
	 * @paparam sharedTo
	 * @paparam itemId
	 * @paparam sharedBy
	 * @return
	 */
	List<AppraisalBean> getAgentSharedAppraisalsNotInItem(String sharedTo, Integer itemId,String sharedBy) throws Exception;

	/**
	 * Method for fetching item component metal
	 * 
	 * @paparam itemId
	 * @paparam componentId
	 * @return
	 * @throws Exception
	 */
	public ValouxMetalModel getComponentMetalBeanByItemAndComponentId(
			Integer itemId, Integer componentId) throws Exception;

	/**
	 * Method for fetching item component pearl
	 * 
	 * @paparam itemId
	 * @paparam componentId
	 * @return
	 * @throws Exception
	 */
	public ValouxPearlModel getComponentPearlBeanByItemAndComponentId(
			Integer itemId, Integer componentId) throws Exception;

	/**
	 * Method for fetching item component gemstone
	 * 
	 * @paparam itemId
	 * @paparam componentId
	 * @return
	 * @throws Exception
	 */
	public ValouxGemstoneModel getComponentGemstoneBeanByItemAndComponentId(
			Integer itemId, Integer componentId) throws Exception;
	
	/**
	 * This method get item shared to Agent in requested state
	 * 
	 * @paparam relationKey
	 * @return ValouxSharedRequestBean
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getListOfRequestedSharedItemsToAgent(String relationKey)
			throws Exception;
	
	/**
	 * This method get all the item having keyword
	 * @paparam rKey
	 * @paparam keyword
	 * @return
	 * @throws Exception
	 */
	List<ValouxItemModel> getItemListAssociatedWithUserAndHaveKeyword(String rKey,String keyword,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;
	/**
	 * This method
	 * @paparam rKey
	 * @paparam keyword
	 * @return
	 * @throws Exception
	 */
	List<ValouxItemModel> getItemListAssociatedWithAgentAndHaveKeyword(String rKey,String keyword,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;

	/**
	 * Method for delete diamond component from item
	 * 
	 * @paparam itemId 
	 * @paparam componentId
	 * @return
	 * @throws Exception  : error during the execution of operation
	 */
	public void deleteDiamondComponentFromItem(Integer itemId,
			Integer componentId) throws Exception;

	/**
	 * Method for delete gemstone component from item
	 * 
	 * @paparam itemId 
	 * @paparam componentId
	 * @return
	 * @throws Exception  : error during the execution of operation
	 */
	public void deleteGemstoneComponentFromItem(Integer itemId,
			Integer componentId) throws Exception;
	
	/**
	 * Method for delete pearl component from item
	 * 
	 * @paparam itemId 
	 * @paparam componentId
	 * @return
	 * @throws Exception  : error during the execution of operation
	 */
	public void deletePearlComponentFromItem(Integer itemId, Integer componentId) throws Exception;
	
	/**
	 * Method for delete metal component from item
	 * 
	 * @paparam itemId 
	 * @paparam componentId
	 * @return
	 * @throws Exception  : error during the execution of operation
	 */
	public void deleteMetalComponentFromItem(Integer itemId, Integer componentId) throws Exception;
	
	/**
	 * Method for fetching components of Diamond
	 * 
	 * @paparam ComponentId
	 * @return List<ValouxDiamondModel>
	 * @throws Exception
	 */
	public List<ValouxDiamondModel> getDiamondComponentsByComponentId(int componentId) throws Exception;
	
	/**
	 * Method for fetching components of Gemstone
	 * 
	 * @paparam ComponentId
	 * @return List<ValouxGemstoneModel>
	 * @throws Exception
	 */
	public List<ValouxGemstoneModel> getGemstoneComponentsByComponentId(int componentId) throws Exception;
	
	/**
	 * Method for fetching components of Pearl
	 * 
	 * @paparam ComponentId
	 * @return List<ValouxPearlModel>
	 * @throws Exception
	 */
	public List<ValouxPearlModel> getPearlComponentsByComponentId(int componentId) throws Exception;
	/**
	 * Method for fetching components of Metal
	 * 
	 * @paparam ComponentId
	 * @return List<ValouxMetalModel>
	 * @throws Exception
	 */
	public List<ValouxMetalModel> getMetalComponentsByComponentId(int componentId) throws Exception;

	/**
	 * Method for fetching user list shared with agent
	 * @paparam sharedTo
	 * @return
	 * @throws Exception
	 */
	public List<ValouxSharedRequestBean> getUserListSharedToAgent(String sharedTo) throws Exception;

	/**
	 * Method for fetching user list shared with agent
	 * @paparam sharedBy
	 * @paparam sharedTo
	 * @return
	 * @throws Exception
	 */
	public List<ValouxSharedRequestBean> getUserListSharedToAgentSharedByUser(
			String sharedBy, String sharedTo) throws Exception;

	/**
	 * Method for updating item component price property details
	 * @paparam valouxItemComponentModel
	 * @throws Exception
	 */
	public void addUpdateValouxItemComponentModelPriceDetails(
			ValouxItemComponentModel valouxItemComponentModel) throws Exception;
	
	/**
	 * This method get list of items shared to agent by consumer
	 * @paparam sharedTo
	 * @paparam itemType
	 * @paparam approvedStatus
	 * @paparam sharedBy
	 * @return
	 * @throws Exception
	 */
	List<ValouxSharedRequestBean> getListOfSharedItemsToUserByConsumer(String sharedTo, Integer itemType,
			Integer approvedStatus,String sharedBy,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;
	
	/**
	 * This method get list of item associated with store
	 * @paparam storeId
	 * @return
	 * @throws Exception
	 */
	List<ValouxItemBean> getItemAssociatedWithStore(Integer storeId) throws Exception;
	
	/**
	 * @paparam diamondModel
	 * @throws Exception
	 */
	public void getItemComponentDiamondSpecifyPrice(
			ValouxDiamondModel diamondModel) throws Exception;

	/**
	 * @paparam metalModel
	 * @throws Exception
	 */
	public void getItemComponentMetalSpecifyPrice(ValouxMetalModel metalModel) throws Exception;

	/**
	 * @throws Exception
	 */
	public void callMetalPriceUpdateService() throws Exception;

	/**
	 * @paparam itemModel
	 * @throws Exception
	 */
	public void updateItemPriceProperties(ValouxItemModel itemModel) throws Exception;

	/**
	 * @paparam itemId
	 * @return
	 * @throws Exception
	 */
	public ValouxItemModel getUpdatedItemPriceProperties(Integer itemId) throws Exception;

	/**
	 * @paparam itemId
	 * @throws Exception
	 */
	public void updateItemMarketValueFromComponents(Integer itemId) throws Exception;
	
	/**
	 * This method send email to consumer that item/appraisal/collection is added for him
	 * @paparam itemType
	 * @paparam rKey
	 * @throws Exception
	 */
	void sendMailToConsumerForItemCollectionAppraisalIdAdded(Integer itemType,String rKey) throws Exception;
	
	/**
	 * This method get all item associated with store and consumer
	 * @paparam storeId
	 * @paparam rKey
	 * @return
	 * @throws Exception
	 */
	List<ValouxItemBean> getItemDetailByStoreIdAndRKey(Integer storeId,String rKey)
			throws Exception;

	/**
	 * This method used to add component valoux inclusion
	 * @paparam valouxInclusionModels
	 * @throws Exception
	 */
	public void addInclusionsTypeFromRequest(List<ValouxInclusionModel> valouxInclusionModels) throws Exception;

	/**
	 * @paparam componentId
	 * @paparam inclusionType
	 * @throws Exception
	 */
	public void deleteAllInclusionsByComponentAndType(Integer componentId, Integer inclusionType) throws Exception;

	/**
	 * @paparam componentId
	 * @return
	 * @throws Exception
	 */
	public ValouxItemComponentModel getComponentsById(Integer componentId) throws Exception;

	/**
	 * @paparam valouxComponentsImageModel
	 * @throws Exception
	 */
	public void addValouxComponentsCertificateImageDetails(
			List<ValouxComponentsImageModel> valouxComponentsImageModel) throws Exception;

	/**
	 * @paparam componentId
	 * @paparam fileType
	 * @paparam imageName
	 * @return
	 * @throws Exception
	 */
	public boolean getImageModelByComponentIdNameFileType(
			Integer componentId, Integer fileType, String imageName) throws Exception;

	/**
	 * @paparam rKey
	 * @return
	 * @throws Exception
	 */
	public List<ValouxSharedRequestBean> getListOfRequestedAndAcceptedSharedItemsToAgent(
			String rKey) throws Exception;

	/**
	 * @throws Exception
	 */
	public void callItemMarketPriceUpdateService() throws Exception;

	/**
	 * @paparam userPublicKey
	 * @paparam limit 
	 * @return
	 * @throws Exception
	 */
	public List<ValouxItemModel> getTopItemsListByUserIdAndLimit(String userPublicKey, Integer limit) throws Exception;

	/**
	 * @paparam itemId
	 * @paparam userPublicKey 
	 * @return
	 * @throws Exception
	 */
	public Boolean deleteItemAndAllDetails(Integer itemId, String userPublicKey) throws Exception;
	
	/**
	 * This method delete all consumer items
	 * @paparam userPublicKey
	 * @return
	 * @throws Exception
	 */
	Boolean deleteConsumerItemAndAllDetails(String userPublicKey) throws Exception;
}
