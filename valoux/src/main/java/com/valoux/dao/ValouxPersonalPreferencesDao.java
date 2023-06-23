/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.ValouxPersonalPreferencesBean;

public interface ValouxPersonalPreferencesDao {
	
	/**
	 * This method performs get All Personal Preferences.
	 * 
	 * @return List<ValouxPersonalPreferencesBean> : Created PersonalPreferences
	 *         list object
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<ValouxPersonalPreferencesBean> getAllPersonalPreferences() throws Exception;

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
//	public void deleteUserPreferenceDetail(String relationKey) throws Exception;
	/**
	 * This method retrieves all consumer detail
	 * 
	 * @paparam preferencesId
	 * @return : Created ValouxPersonalPreferencesBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public ValouxPersonalPreferencesBean getUserPreferencesPreferencesId(int preferencesId) throws Exception;
}
