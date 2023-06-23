/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.Date;

import com.valoux.bean.ValouxMetalsMasterPriceBean;

public interface ValouxMetalMasterPriceDao {

	/**
	 * @paparam metalName
	 * @return
	 * @throws Exception
	 */
	ValouxMetalsMasterPriceBean getItemComponentMetalSpecifyPrice(
			String metalName) throws Exception;

	/**
	 * @paparam masterPriceBean
	 * @throws Exception
	 */
	void addValouxMetalsMasterPrice(ValouxMetalsMasterPriceBean masterPriceBean) throws Exception;

	/**
	 * @paparam createdOn
	 * @paparam metalsType
	 * @return
	 * @throws Exception
	 */
	ValouxMetalsMasterPriceBean getMetalsMasterPriceByTypeAndDate(
			Date createdOn, String metalsType) throws Exception;

	

}
