/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.ItemImageBean;

public interface ItemImageDao {

	

	/**
	 * This method add item image
	 * 
	 * @paparam itemImageBean
	 *            : Business object carrying all the information relates to Item
	 *            image
	 * @return ItemImageBean : Created Item object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	ItemImageBean addItemImage(ItemImageBean itemImageBean) throws Exception;


	/**
	 * This method gets the list of all images of an item
	 * 
	 * @paparam itemId
	 * @return List<ItemImageBean> : created item image list
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ItemImageBean> getItemImageListByItemId(Integer itemId) throws Exception;

	/**
	 * This method gets the maximum index of table
	 * 
	 * @return: Integer
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	Integer getMaximumIndexOfItemImageTable() throws Exception;
	/**
	 * This method delete item images
	 * 
	 * @paparam itemImageBean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	void deleteItemImages(ItemImageBean itemImageBean) throws Exception;

	/**
	 * This method get the item image bean
	 * 
	 * @paparam itemId
	 * @paparam imageId
	 * @return ItemImageBean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	ItemImageBean getItemImageListByItemIdAndImageId(Integer itemId, Integer imageId) throws Exception;

}
