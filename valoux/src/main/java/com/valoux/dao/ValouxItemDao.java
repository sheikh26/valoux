/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.ValouxItemBean;
import com.valoux.bean.ValouxItemComponentBean;

public interface ValouxItemDao {
	/**
	 * This method get items Details.
	 * 
	 * @paparam itemId
	 *            : Business object carrying all the information relates to
	 *            items.
	 * @return ValouxItemBean : Created ValouxItemBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */

	public ValouxItemBean getItemsBeanByItemId(Integer itemId) throws Exception;

	/**
	 * Method for fetching items not in a appraisal
	 * 
	 * @paparam publicKey
	 * @paparam objects
	 * @return : List<ValouxItemBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxItemBean> getItemsListNotInAppraisal(String publicKey, Object[] objects) throws Exception;
	/**
	 * This method performs get All Item List.
	 * 
	 * @paparam publicKey
	 * 
	 * @return List<ValouxItemBean> : Created Item list object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxItemBean> getAllItemList(String publicKey,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;
	/**
	 * This method creates add Item.
	 * 
	 * @paparam ValouxItemBean
	 *            : Business object carrying all the information relates to
	 *            Items.
	 * @return ValouxItemBean : Created Item object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	ValouxItemBean addItem(ValouxItemBean itemBean) throws Exception;

	

	/**
	 * This method get the item detail by item id and relation key
	 * 
	 * @paparam itemId
	 * @paparam rKey
	 * @return : ValouxItemBean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	ValouxItemBean getItemDetailByItemIdAndRkey(Integer itemId, String rKey) throws Exception;
	/**
	 * This methos update item details
	 * 
	 * @paparam itemId
	 * @return : ValouxItemBean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	ValouxItemBean updateItemdetail(ValouxItemBean itemBean) throws Exception;

	/**
	 * Method used to get item details
	 * 
	 * @paparam itemId
	 *            : Business object carrying item id data.
	 * @return ValouxItemBean : items object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	ValouxItemBean getValouxItemDetailsById(Integer itemId) throws Exception;

	
	/**
	 * Method for fetching items not in a collection
	 * 
	 * @paparam publicKey
	 * @paparam objects
	 * @return : List<ValouxItemBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxItemBean> getConsumerItemsNotInCollection(String publicKey, Object[] objects) throws Exception;

	/**
	 * This method get the list of valoux store bean on the basis of storeId
	 * 
	 * @paparam storeId
	 * @return : List<ValouxItemBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxItemBean> getItemDetailByStoreId(Integer storeId) throws Exception;

	/**
	 * This method check the item name exist for user
	 * 
	 * @paparam publicKey
	 * @paparam itemName
	 * @return : List<ValouxItemBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxItemBean> checkItemNameExistForUser(String publicKey, String itemName) throws Exception;
	
	/**
	 * This method get item list by storeId and consumer key
	 * @paparam storeId
	 * @paparam rKey
	 * @return
	 * @throws Exception
	 */
	List<ValouxItemBean> getItemDetailByStoreIdAndRkey(Integer storeId, String rKey) throws Exception;



	
	/**
	 * This method get items of consumer with keyword
	 * @paparam publicKey
	 * @paparam keyword
	 * @paparam objects
	 * @return
	 * @throws Exception
	 */
	List<ValouxItemBean> getItemListForGlobalSearchByName(String publicKey,String keyword,Object[] objects,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrders) throws Exception;
	/**
	 * Method for delete item component object
	 * 
	 * @paparam itemId 
	 * @paparam componentId
	 * @return
	 * @throws Exception  : error during the execution of operation
	 */
	void deleteAnyBeanByObject(Object objectBean) throws Exception;

	/**
	 * Method for adding item component
	 * @paparam componentBean
	 * @throws Exception
	 */
	public void addUpdateItemComponent(ValouxItemComponentBean componentBean) throws Exception;
	
	/**
	 * This method merge stores
	 * @paparam primaryStoreId
	 * @paparam storeIdToBeMerged
	 * @throws Exception
	 */
	void mergestore(Integer primaryStoreId, Integer storeIdToBeMerged) throws Exception;

	/**
	 * @paparam itemBean
	 * @throws Exception
	 */
	public void updateItemPriceProperties(ValouxItemBean itemBean) throws Exception;

	/**
	 * @return
	 * @throws Exception
	 */
	public List<ValouxItemBean> getAllListOfItems() throws Exception;

	/**
	 * @paparam userPublicKey
	 * @paparam limit
	 * @return
	 * @throws Exception
	 */
	public List<ValouxItemBean> getTopItemsListByUserIdAndLimit(
			String userPublicKey, Integer limit) throws Exception;
}
