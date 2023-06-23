/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.ValouxAccessPermissionBean;

public interface ValouxAccessPermissionDao {

	/**
	 * This method will add valoux access permission
	 * 
	 * @paparam accessPermissionBean
	 *            : Business object carrying accessPermissionBean data.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void addValouxAccessPermission(ValouxAccessPermissionBean accessPermissionBean) throws Exception;

	/**
	 * This method will give list of agent having access permission of consumers
	 * 
	 * @paparam agentKey
	 *            : Business object carrying accessPermissionBean data.
	 * @return : List<ValouxAccessPermissionBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<ValouxAccessPermissionBean> getAgentAccessPermissionUsers(String agentKey) throws Exception;

}
