/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.UserRoleBean;

public interface UserRoleDao {

	/**
	 * This method creates User Role.
	 * 
	 * @paparam userRoleBean
	 *            : Business object carrying all the information relates to user
	 *            role type.
	 * @return UserRoleBean : Created UserRoleBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public UserRoleBean createUserRole(UserRoleBean userRoleBean) throws Exception;

	/**
	 * This method performs get Roll.
	 * 
	 * @paparam relationKey
	 *            : Business object carrying all the information relates to user
	 *            role object.
	 * @return UserRoleBean Created UserRoleBean object
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public UserRoleBean getRole(String relationKey) throws Exception;

	/**
	 * This method performs get List Of User As Super Admin Role.
	 * 
	 * @return List<UserRoleBean> : Created UserRoleBean list object
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<UserRoleBean> getListOfUserAsSuperAdminRole() throws Exception;
}
