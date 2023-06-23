/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;

import com.valoux.bean.ValouxCollectionItemBean;

public interface ValouxCollectionItemDao {
	/**
	 * This method get the item count by collection id
	 * 
	 * @paparam vcid
	 * @return : List<ValouxCollectionItemBean>
	 * @throws Exception
	 */
	public List<ValouxCollectionItemBean> getItemAssociatedWithCollection(Integer vcid) throws Exception;
	/**
	 * Method used to save items in collection
	 * 
	 * @paparam itemsList
	 *            : Business object carrying all the id relates to Collection
	 *            data.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	void addItemsInCollection(List<ValouxCollectionItemBean> itemsList) throws Exception;
	/**
	 * Method used to get all user collections items
	 * 
	 * @paparam vcid
	 *            : Business object carrying collection id data.
	 * @return List<ValouxCollectionItemBean> : Collection items list object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxCollectionItemBean> getCollectionItemsById(Integer collectionId) throws Exception;
	/**
	 * Method used to delete all user collections items
	 * 
	 * @paparam collectionBeans
	 *            : Business object carrying collection items data.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	void deleteUserCollectionItemsList(List<ValouxCollectionItemBean> collectionBeans) throws Exception;

	/**
	 * This method gets the detail by collection id and item id
	 * 
	 * @paparam collectionId
	 *            : Business object carrying collection data.
	 * @paparam itemId
	 *            : Business object carrying collection data.
	 * @return : List<ValouxCollectionItemBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxCollectionItemBean> getCollectionItemsByCollectionIdAndItemId(Integer collectionId, Integer itemId)
			throws Exception;

	
	/**
	 * This method get the detail by item id
	 * 
	 * @paparam itemId
	 *            : Business object carrying collection data.
	 * @return : List<ValouxCollectionItemBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxCollectionItemBean> getCollectionItemsByItemId(Integer itemId) throws Exception;
	/**
	 * This method will delete the collection item details
	 * 
	 * @paparam deleteListItems
	 *            : Business object carrying collection data.
	 * @paparam collectionId
	 *            : Business object carrying collection data.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	void deletedItemsFromCollection(List<Integer> deleteListItems, Integer collectionId) throws Exception;
	/**
	 * Method for delete all items from collection
	 * 
	 * @paparam beanList
	 *            : Business object carrying collection item data.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	void deletedAllItemsFromCollection(List<ValouxCollectionItemBean> beanList) throws Exception;
	/**
	 * This method gets the detail by collection id and item id
	 * 
	 * @paparam collectionId
	 *            : Business object carrying collection data.
	 * @paparam itemId
	 *            : Business object carrying collection data.
	 * @return : List<ValouxCollectionItemBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxCollectionItemBean> getCollectionItemsByCollectionArrayIdAndItemId(JSONArray collectionId, Integer itemId)
			throws Exception;
	/**
	 * @paparam vcid
	 * @paparam itemId
	 * @return
	 * @throws Exception
	 */
	public ValouxCollectionItemBean getCollectionItemsByCollectionAndItemId(
			Integer vcid, Integer itemId) throws Exception;
	/**
	 * @paparam valouxCollectionItemBean
	 * @throws Exception
	 */
	public void updateCollectionItemDetails(
			ValouxCollectionItemBean valouxCollectionItemBean) throws Exception;
	
	/**
	 * @paparam vcid
	 * @paparam itemId
	 * @return
	 * @throws Exception
	 */
	public List<ValouxCollectionItemBean> getCollectionItemsByItemIdAndNotThisCollectionId(
			Integer vcid, Integer itemId) throws Exception;

}
