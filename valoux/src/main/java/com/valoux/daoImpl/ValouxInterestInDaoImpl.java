package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ValouxInterestInBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.UserDao;
import com.valoux.dao.ValouxInterestInDao;

public class ValouxInterestInDaoImpl implements ValouxInterestInDao {
	@Autowired
	@Qualifier(value = "sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	@Qualifier(value = "secondDBSessionFactory")
	private SessionFactory secondDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(ValouxInterestInDaoImpl.class);

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSecondDBSessionFactory() {
		return secondDBSessionFactory;
	}

	public void setSecondDBSessionFactory(SessionFactory secondDBSessionFactory) {
		this.secondDBSessionFactory = secondDBSessionFactory;
	}
	
	/**
	 * This method performs get User Interest In.
	 */

	public List<ValouxInterestInBean> getUserInterestIn() throws Exception {
		LOGGER.debug("Enter Method getUserInterestIn of UserDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxInterestInBean.class);
		criteria.add(Restrictions.eq("status", CommonConstants.STATUS));
		@SuppressWarnings("unchecked")
		List<ValouxInterestInBean> valouxInterestInBeanList = (List<ValouxInterestInBean>) criteria.list();
		LOGGER.debug("Exit Method getUserInterestIn of UserDaoImpl");
		return valouxInterestInBeanList;
	}

}
