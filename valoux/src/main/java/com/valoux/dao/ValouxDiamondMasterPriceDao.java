/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import com.valoux.bean.ValouxDiamondMasterPriceBean;

public interface ValouxDiamondMasterPriceDao {

	/**
	 * @paparam diamondModel
	 * @throws Exception
	 */
	ValouxDiamondMasterPriceBean getItemComponentDiamondSpecifyPrice(
			Double caratFrom, Double caratTo, String cut, String color) throws Exception;


	

}
