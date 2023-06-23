/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.ValouxItemComponentBean;

public interface ValouxItemComponentDao {

	/**
	 * Method for saving item components
	 * 
	 * @paparam componentBeanList
	 *            : Business object carrying item component data.
	 * @return : List<ValouxItemComponentBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxItemComponentBean> saveValouxItemComponents(List<ValouxItemComponentBean> componentBeanList)
			throws Exception;

	/**
	 * Method for fetching item components
	 * 
	 * @paparam itemId
	 *            : Business object carrying item data.
	 * @return : List<ValouxItemComponentBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxItemComponentBean> getComponentsByItemId(Integer itemId) throws Exception;

	/**
	 * Method for fetching item components
	 * 
	 * @paparam itemId
	 *            : Business object carrying item component data.
	 * @paparam componentId
	 *            : Business object carrying item component data.
	 * @return : ValouxItemComponentBean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	ValouxItemComponentBean getItemComponentByItemAndComponentId(Integer itemId, Integer componentId) throws Exception;

	/**
	 * Method for deleting item components
	 * 
	 * @paparam componentBean
	 *            : Business object carrying item component data.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	void deleteValouxItemComponentBean(ValouxItemComponentBean componentBean) throws Exception;

	/**
	 * @paparam componentBean
	 * @throws Exception
	 */
	void saveValouxItemComponent(ValouxItemComponentBean componentBean) throws Exception;

	/**
	 * @paparam componentId
	 * @return
	 * @throws Exception
	 */
	ValouxItemComponentBean getItemComponentByComponentId(Integer componentId) throws Exception;

}
