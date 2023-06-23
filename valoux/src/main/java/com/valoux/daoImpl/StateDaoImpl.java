package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.StateBean;
import com.valoux.dao.StateDao;
import com.valoux.dao.UserDao;

public class StateDaoImpl implements StateDao {
	@Autowired
	@Qualifier(value = "sessionFactory")
	private SessionFactory sessionFactory;

	private static final Logger LOGGER = Logger.getLogger(StateDaoImpl.class);

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * This method performs save State.
	 */

	public StateBean saveState(StateBean stateBean) throws Exception {
		LOGGER.debug("Enter Method saveState of UserDaoImpl");
		sessionFactory.getCurrentSession().save(stateBean);
		LOGGER.debug("Exit Method saveState of UserDaoImpl");
		return stateBean;

	}

	/**
	 * This method performs get State Id.
	 */

	public List<StateBean> getStateId(String stateName) throws Exception {
		LOGGER.debug("Enter Method getStateId of UserDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StateBean.class);
		criteria.add(Restrictions.eq("name", stateName));
		@SuppressWarnings("unchecked")
		List<StateBean> stateBeanList = (List<StateBean>) criteria.list();
		LOGGER.debug("Exit Method getStateId of UserDaoImpl");
		return stateBeanList;
	}
	
	/**
	 * This method performs get State Name By StateId.
	 */

	public StateBean getStateNameByStateId(Integer stateId) throws Exception {
		LOGGER.debug("Enter Method getStateNameByStateId of UserDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StateBean.class);
		criteria.add(Restrictions.eq("stateId", stateId));
		StateBean stateBean = (StateBean) criteria.uniqueResult();
		LOGGER.debug("Exit Method getStateNameByStateId of UserDaoImpl");
		return stateBean;
	}

}
