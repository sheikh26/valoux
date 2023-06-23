/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.ValouxItemTypeBean;

public interface ValouxItemTypeDao {
	/**
	 * This method get Master Data For Item Type.
	 * 
	 * @return List<ValouxItemTypeBean> : Created Item type list object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxItemTypeBean> getMasterDataForItemType() throws Exception;
}
