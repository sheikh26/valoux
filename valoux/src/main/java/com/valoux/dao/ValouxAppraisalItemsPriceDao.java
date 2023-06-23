/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import com.valoux.bean.ValouxAppraisalItemsPriceBean;

/**
 * This <java>interface</java> AppraisalDao interface has all the abstract
 * methods related to appraisal.
 * 
 * @author param Sheikh
 * 
 */

public interface ValouxAppraisalItemsPriceDao {

	/**
	 * @paparam appraisalItemsPriceBean
	 * @throws Exception
	 */
	public void addAppraisalItemsPrice(
			ValouxAppraisalItemsPriceBean appraisalItemsPriceBean) throws Exception;

	/**
	 * @paparam itemId
	 * @paparam appraisalId
	 * @return
	 * @throws Exception
	 */
	public ValouxAppraisalItemsPriceBean getAppraisalItemsComponentPriceByAppraisalAndItemId(
			Integer itemId, Integer appraisalId)  throws Exception;

}
