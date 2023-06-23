/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.UserJewelryComponentsBean;

public interface UserJewelryComponentsDao {



	/**
	 * This method performs save JewelryComponents For User.
	 * 
	 * @paparam JewelryComponentsList
	 *            : Business object carrying all the information relates to
	 *            JewelryComponents of user.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void saveJewelryComponentsForUser(List<UserJewelryComponentsBean> JewelryComponentsList) throws Exception;

	/**
	 * This method performs get JewelryComponents By RKey.
	 * 
	 * @paparam rKey
	 *            : Business object carrying all the information relates to
	 *            JewelryComponents object.
	 * @return List<UserPersonalPreferences> : Created UserJewelryComponentsBean
	 *         list object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<UserJewelryComponentsBean> getJewelryComponentsByRKey(String rKey) throws Exception;


	

	/**
	 * This method performs delete JewelryComponents Detail.
	 * 
	 * @paparam relationKey
	 *            : Business object carrying all the information relates to
	 *            JewelryComponents object.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void deleteJewelryComponentsDetail(String relationKey) throws Exception;

}
