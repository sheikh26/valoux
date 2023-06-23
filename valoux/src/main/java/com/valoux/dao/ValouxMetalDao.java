/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.ValouxMetalBean;

public interface ValouxMetalDao {

	/**
	 * Method for adding item component metal
	 * 
	 * @paparam metalBean
	 *            : Business object carrying item component property data.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	void addValouxComponentMetalProperty(ValouxMetalBean metalBean) throws Exception;
	/**
	 * Method for getting component metal
	 * 
	 * @paparam itemId
	 * @paparam componentId
	 * @return
	 * @throws Exception
	 */
	ValouxMetalBean getComponentMetalBeanByItemAndComponentId(Integer itemId,
			Integer componentId) throws Exception;

	/**
	 * Method for fetching Metal components
	 * 
	 * @paparam componentId
	 *            : Business object carrying item data.
	 * @return : List<ValouxMetalBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxMetalBean> getMetalComponentsByComponentId(int componentId) throws Exception;
}
