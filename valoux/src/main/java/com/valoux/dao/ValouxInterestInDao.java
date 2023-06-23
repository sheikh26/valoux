/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.ValouxInterestInBean;

public interface ValouxInterestInDao {
	/**
	 * This method performs get User Interest In.
	 * 
	 * @return List<ValouxInterestInBean> : Created InterestIn list object
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<ValouxInterestInBean> getUserInterestIn() throws Exception;

}
