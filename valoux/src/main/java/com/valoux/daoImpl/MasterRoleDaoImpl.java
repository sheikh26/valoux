package com.valoux.daoImpl;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.MasterRoleBean;
import com.valoux.dao.MasterRoleDao;

public class MasterRoleDaoImpl implements MasterRoleDao {
	
	@Autowired
	@Qualifier(value = "secondDBSessionFactory")
	private SessionFactory secondDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(MasterRoleDaoImpl.class);

	public SessionFactory getSecondDBSessionFactory() {
		return secondDBSessionFactory;
	}

	public void setSecondDBSessionFactory(SessionFactory secondDBSessionFactory) {
		this.secondDBSessionFactory = secondDBSessionFactory;
	}
	
	/**
	 * This method performs get Master Role.
	 */

	public MasterRoleBean getUserRole(String agentRole) throws Exception {
		LOGGER.debug("Enter Method getUserRole of AgentDaoImpl");
		Criteria criteria = secondDBSessionFactory.getCurrentSession().createCriteria(MasterRoleBean.class);
		criteria.add(Restrictions.eq("roleName", Integer.parseInt(agentRole)));
		MasterRoleBean masterRoleBean = (MasterRoleBean) criteria.uniqueResult();
		LOGGER.debug("Exit Method getUserRole of AgentDaoImpl");
		return masterRoleBean;
	}

}
