/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import com.valoux.bean.UserTypeBean;

public interface UserTypeDao {
	/**
	 * This method creates User Type.
	 * 
	 * @paparam userTypeBean
	 *            : Business object carrying all the information relates to user
	 *            type.
	 * @return UserTypeBean : Created UserTypeBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public UserTypeBean createUserType(UserTypeBean userTypeBean) throws Exception;




	/**
	 * This method performs get User Type.
	 * 
	 * @paparam relationKey
	 *            : Business object carrying all the information relates to user
	 *            type object.
	 * @return UserTypeBean : Created UserTypeBean object
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public UserTypeBean getUserType(String relationKey) throws Exception;

}
