/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.ValouxGemstoneBean;

public interface ValouxGemstoneDao {


	/**
	 * Method for adding item component gemstone
	 * 
	 * @paparam gemstoneBean
	 *            : Business object carrying item component property data.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	void addValouxComponentGemstoneProperty(ValouxGemstoneBean gemstoneBean) throws Exception;


	/**
	 * Method for getting component gemstone
	 * 
	 * @paparam itemId
	 * @paparam componentId
	 * @return
	 * @throws Exception
	 */
	ValouxGemstoneBean getComponentGemstoneBeanByItemAndComponentId(
			Integer itemId, Integer componentId) throws Exception;

	
	
	
	/**
	 * Method for fetching Gemstone components
	 * 
	 * @paparam componentId
	 *            : Business object carrying item data.
	 * @return : List<ValouxGemstoneBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxGemstoneBean> getGemstoneComponentsByComponentId(int componentId) throws Exception;
}
