/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.ValouxDiamondBean;

public interface ValouxDiamondDao {

	/**
	 * Method for adding item component diamond
	 * 
	 * @paparam diamondBean
	 *            : Business object carrying item component property data.
	 * @paparam requestType
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	void addValouxComponentDiamondProperty(ValouxDiamondBean diamondBean, String requestType) throws Exception;

	/**
	 * Method for getting component diamond
	 * 
	 * @paparam itemId
	 * @paparam componentId
	 * @return
	 * @throws Exception
	 */
	ValouxDiamondBean getComponentDiamondBeanByItemAndComponentId(Integer itemId, Integer componentId) throws Exception;

	/**
	 * Method for fetching Diamond components
	 * 
	 * @paparam componentId
	 *            : Business object carrying item data.
	 * @return : List<ValouxDiamondBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxDiamondBean> getDiamondComponentsByComponentId(int componentId) throws Exception;
}
