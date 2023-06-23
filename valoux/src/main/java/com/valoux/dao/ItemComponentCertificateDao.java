/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import com.valoux.bean.ItemComponentCertificateBean;

public interface ItemComponentCertificateDao {

	

	/**
	 * Method for adding component certificate
	 * 
	 * @paparam certificateBean
	 * @throws Exception
	 */
	void addItemComponentCertificate(ItemComponentCertificateBean certificateBean) throws Exception;

	/**
	 * Method for getting component certificate
	 * 
	 * @paparam itemId
	 * @paparam componentId
	 * @return
	 * @throws Exception
	 */
	ItemComponentCertificateBean getComponentCertificateBeanByItemAndComponentId(Integer itemId, Integer componentId)
			throws Exception;
}
