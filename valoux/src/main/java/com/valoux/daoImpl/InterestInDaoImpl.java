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

import com.valoux.bean.ValouxInterestInBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.InterestInDao;

/**
 * This <java>class</java> InterestInDaoImpl use to perform all Dao layer
 * operations related to user Interest.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
public class InterestInDaoImpl implements InterestInDao {

	@Autowired
	@Qualifier(value = "sessionFactory")
	private SessionFactory sessionFactory;
	private static final Logger LOGGER = Logger.getLogger(InterestInDaoImpl.class);
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * This method performs get User Interest In.
	 */
	public List<ValouxInterestInBean> getUserInterestIn() throws Exception {
		LOGGER.debug("InterestInDaoImpl : Enter Method getUserInterestIn");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxInterestInBean.class);
		criteria.add(Restrictions.eq("status", CommonConstants.STATUS));
		@SuppressWarnings("unchecked")
		List<ValouxInterestInBean> valouxInterestInBeanList = (List<ValouxInterestInBean>) criteria.list();
		LOGGER.debug("InterestInDaoImpl : Enter Method getUserInterestIn");
		return valouxInterestInBeanList;
	}

}
