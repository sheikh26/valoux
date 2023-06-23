/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import com.valoux.bean.ValouxAppraisalItemsComponentPriceBean;
/**
 * This <java>interface</java> AppraisalDao interface has all the abstract
 * methods related to appraisal.
 * 
 * @author param Sheikh
 * 
 */

public interface ValouxAppraisalItemsComponentPriceDao {

	/**
	 * @paparam appraisalComponentPriceBean
	 * @paparam request 
	 * @throws Exception
	 */
	public void addAppraisalItemsComponentPrice(
			ValouxAppraisalItemsComponentPriceBean appraisalComponentPriceBean) throws Exception;

	/**
	 * @paparam vicid
	 * @paparam appraisalId
	 * @return
	 * @throws Exception
	 */
	public ValouxAppraisalItemsComponentPriceBean getAppraisalItemsComponentPriceByAppraisalAndComponentId(
			Integer vicid, Integer appraisalId)  throws Exception;


}
