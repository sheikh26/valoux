/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.UserPersonalPreferences;

public interface UserPersonalPreferencesDao {

	/**
	 * This method performs save Personal Preferences For User.
	 * 
	 * @paparam userPersonalPreferencesList
	 *            : Business object carrying all the information relates to
	 *            PersonalPreferences of user.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void savePersonalPreferencesForUser(List<UserPersonalPreferences> userPersonalPreferencesList)
			throws Exception;



	/**
	 * This method performs get User Preferences By RKey.
	 * 
	 * @paparam rKey
	 *            : Business object carrying all the information relates to
	 *            PersonalPreferences object.
	 * @return List<UserPersonalPreferences> : Created UserPersonalPreferences
	 *         list object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<UserPersonalPreferences> getUserPreferencesByRKey(String rKey) throws Exception;
	/**
	 * This method performs delete User Preference Detail.
	 * 
	 * @paparam relationKey
	 *            : Business object carrying all the information relates to user
	 *            object.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void deleteUserPreferenceDetail(String relationKey) throws Exception;
}
