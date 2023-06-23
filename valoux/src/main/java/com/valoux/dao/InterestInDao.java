/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.dao;

import java.util.List;

import com.valoux.bean.ValouxInterestInBean;

/**
 * This <java>class</java> InterestInDao use to perform all our DB related
 * logics for the user interest.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
public interface InterestInDao {

	/**
	 * This method performs get User Interest In.
	 * 
	 * @return List<ValouxInterestInBean> : Created ValouxInterestInBean list
	 *         object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxInterestInBean> getUserInterestIn() throws Exception;
}
