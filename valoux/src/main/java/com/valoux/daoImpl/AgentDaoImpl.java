/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.AgentBean;
import com.valoux.dao.AgentDao;

/**
 * This <java>class</java> AgentDaoImpl use to perform all our DB related logics
 * for the agent.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
public class AgentDaoImpl implements AgentDao {
	@Autowired
	@Qualifier(value = "sessionFactory")
	private SessionFactory sessionFactory;

	private static final Logger LOGGER = Logger.getLogger(AgentDaoImpl.class);

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * This method creates Agent.
	 */

	public AgentBean createAgent(AgentBean agentBean) throws Exception {
		LOGGER.debug("Enter Method createAgent of AgentDaoImpl");
		sessionFactory.getCurrentSession().save(agentBean);
		LOGGER.debug("Exit Method createAgent of AgentDaoImpl");
		return agentBean;
	}

	/**
	 * This method performs get Relational Key.
	 */

	public AgentBean getAgentDetailsFromName(String agentName) throws Exception {
		LOGGER.debug("Enter Method getAgentDetailsFromName of AgentDaoImpl");
		AgentBean userRelationKey = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AgentBean.class);
		criteria.add(Restrictions.eq("emailId", agentName));
		userRelationKey = (AgentBean) criteria.uniqueResult();
		LOGGER.debug("Enter Method getAgentDetailsFromName of AgentDaoImpl");
		return userRelationKey;
	}

	/**
	 * This method performs get Agent Detail By Relation Key.
	 */
	public AgentBean getAgentDetailByRelationKey(String rKey) throws Exception {
		LOGGER.debug("Enter method getAgentDetailByRelationKey of AgentDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AgentBean.class);
		criteria.add(Restrictions.eq("relationKey", rKey));
		AgentBean agentBean = (AgentBean) criteria.uniqueResult();
		LOGGER.debug("Exit method getAgentDetailByRelationKey of AgentDaoImpl");
		return agentBean;
	}
	
	/**
	 * This method performs get Agent Detail By RKey.
	 */

	public AgentBean getAgentDetailByRKey(String rKey) throws Exception {
		LOGGER.debug("Enter Method getAgentDetailByRKey of AgentDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AgentBean.class);
		criteria.add(Restrictions.eq("relationKey", rKey));
		AgentBean agentBean = (AgentBean) criteria.uniqueResult();
		LOGGER.debug("Exit Method getAgentDetailByRKey of AgentDaoImpl");
		return agentBean;
	}

	public void updateAgentDetails(AgentBean agentBean) throws Exception {
		
		LOGGER.debug("Enter Method updateAgentDetails of AgentDaoImpl");
		sessionFactory.getCurrentSession().update(agentBean);
		LOGGER.debug("Exit Method updateAgentDetails of AgentDaoImpl");
	}
	
	/**
	 * This method creates Agent.
	 */

	public List<AgentBean> getAllAgent() throws Exception {
		LOGGER.debug("Enter Method createAgent of AgentDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AgentBean.class);
		List<AgentBean> agentBeanList = (List<AgentBean>)criteria.list();
		LOGGER.debug("Exit Method createAgent of AgentDaoImpl");
		return agentBeanList;
	}
	
}
