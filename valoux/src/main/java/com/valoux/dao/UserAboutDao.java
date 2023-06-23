/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.UserAboutBean;

public interface UserAboutDao {

	/**
	 * This method performs save UserAbout For User.
	 * 
	 * @paparam userAboutList
	 *            : Business object carrying all the information relates to
	 *            UserAbout of user.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void saveUserAboutForUser(List<UserAboutBean> userAboutList) throws Exception;

	/**
	 * This method performs get User About By RKey.
	 * 
	 * @paparam rKey
	 *            : Business object carrying all the information relates to
	 *            UserAbout object.
	 * @return List<UserPersonalPreferences> : Created UserAboutBean list object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<UserAboutBean> getUserAboutByRKey(String rKey) throws Exception;
	/**
	 * This method performs delete User About Detail.
	 * 
	 * @paparam relationKey
	 *            : Business object carrying all the information relates to User
	 *            About object.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void deleteUserAboutDetail(String relationKey) throws Exception;
}
