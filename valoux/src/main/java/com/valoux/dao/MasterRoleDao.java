/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import com.valoux.bean.MasterRoleBean;

public interface MasterRoleDao {
	/**
	 * This method retrieves Master data for Role.
	 * 
	 * @paparam agentRole
	 *            : Business object carrying all the information relates to user
	 *            role.
	 * @return MasterRoleBean : Created role object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public MasterRoleBean getUserRole(String agentRole) throws Exception;

}
