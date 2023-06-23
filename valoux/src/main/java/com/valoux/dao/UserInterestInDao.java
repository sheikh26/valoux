/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.UserInterestIn;

public interface UserInterestInDao {


	/**
	 * This method performs save Interested In For User.
	 * 
	 * @paparam userInterestInList
	 *            : Business object carrying all the information relates to
	 *            InterestIn of user.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void saveInterestedInForUser(List<UserInterestIn> userInterestInList) throws Exception;

	/**
	 * This method performs get User InterestIn Detail By RKey.
	 * 
	 * @paparam rKey
	 *            : Business object carrying all the information relates to
	 *            InterestIn object.
	 * @return List<UserInterestIn> : Created UserInterestIn list object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<UserInterestIn> getUserInterestInDetailByRKey(String rKey) throws Exception;

	/**
	 * This method performs delete User InterestIn Detail.
	 * 
	 * @paparam relationKey
	 *            : Business object carrying all the information relates to
	 *            UserInterestIn object.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void deleteUserInterestInDetail(String relationKey) throws Exception;
}
