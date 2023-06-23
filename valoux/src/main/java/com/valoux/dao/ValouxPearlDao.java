/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.ValouxPearlBean;

public interface ValouxPearlDao {

	/**
	 * Method for adding item component pearl
	 * 
	 * @paparam pearlBean
	 *            : Business object carrying item component property data.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	void addValouxComponentPearlProperty(ValouxPearlBean pearlBean) throws Exception;
	/**
	 * Method for getting component pearl
	 * 
	 * @paparam itemId
	 * @paparam componentId
	 * @return
	 * @throws Exception
	 */
	ValouxPearlBean getComponentPearlBeanByItemAndComponentId(Integer itemId,
			Integer componentId) throws Exception;


	/**
	 * Method for fetching Pearl components
	 * 
	 * @paparam componentId
	 *            : Business object carrying item data.
	 * @return : List<ValouxPearlBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxPearlBean> getPearlComponentsByComponentId(int componentId) throws Exception;
}
