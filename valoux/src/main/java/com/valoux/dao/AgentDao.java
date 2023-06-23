/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.AgentBean;

/**
 * This <java>interface</java> AgentDao interface has all the abstract methods
 * related to agent.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

public interface AgentDao {

	/**
	 * Defines Method and Contract to create Agent.
	 * 
	 * @paparam agentModel
	 *            : Business object carrying all the information relates to
	 *            agent.
	 * @return AgentBean : Created Agent object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	AgentBean createAgent(AgentBean agentBean) throws Exception;

	

	/**
	 * This method retrieves Agent info using username .
	 * 
	 * @paparam loginBean
	 *            : Business object carrying all the information relates to
	 *            login data.
	 * @return AgentBean : Created agent object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public AgentBean getAgentDetailsFromName(String agentName) throws Exception;



	/**
	 * This method performs get Agent Detail By Relation Key.
	 * 
	 * @paparam rKey
	 *            : Business object carrying all the information relates to
	 *            agent relation key.
	 * @return AgentBean : Created agent object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public AgentBean getAgentDetailByRelationKey(String rKey) throws Exception;

	/**
	 * This method performs get Agent Detail By RKey.
	 * 
	 * @paparam rKey
	 *            : Business object carrying all the information relates to
	 *            agent.
	 * @return AgentBean : Created AgentBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public AgentBean getAgentDetailByRKey(String rKey) throws Exception;

	/**
	 * @paparam agentBean
	 * @throws Exception
	 */
	void updateAgentDetails(AgentBean agentBean) throws Exception;
	
	/**
	 * This method get all agent list
	 * @return
	 * @throws Exception
	 */
	List<AgentBean> getAllAgent() throws Exception;



}
