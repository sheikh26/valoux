/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.ValouxCollectionImageBean;

public interface ValouxCollectionImageDao {
	/**
	 * This method delete the collection image
	 * 
	 * @paparam imageBeanList
	 *            : Business object carrying collection data.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	void deleteValouxCollectionImageBeanByCollectionId(List<ValouxCollectionImageBean> imageBeanList) throws Exception;
	/**
	 * This method will fetch the collection image details
	 * 
	 * @paparam collectionId
	 *            : Business object carrying collection data.
	 * @return : List<ValouxCollectionImageBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxCollectionImageBean> getValouxCollectionImageBeanByCollectionId(Integer collectionId) throws Exception;

	/**
	 * This method will save the collection image details
	 * 
	 * @paparam collectionImageBeanList
	 *            : Business object carrying collection data.
	 * @return : List<ValouxCollectionImageBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxCollectionImageBean> saveValouxCollectionImageBeanOfCollection(
			List<ValouxCollectionImageBean> collectionImageBeanList) throws Exception;

	/**
	 * This method will get the collection image details
	 * 
	 * @paparam collectionId
	 *            : Business object carrying collection data.
	 * @paparam imageId
	 *            : Business object carrying collection data.
	 * @return : ValouxCollectionImageBean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	ValouxCollectionImageBean getCollectionImageByCollectionAndImageId(Integer collectionId, Integer imageId)
			throws Exception;
	/**
	 * This method delete the collection image
	 * 
	 * @paparam collectionImageBean
	 *            : Business object carrying collection data.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	void deleteValouxCollectionImageBeanByCollectionAndImageId(ValouxCollectionImageBean collectionImageBean)
			throws Exception;
}
