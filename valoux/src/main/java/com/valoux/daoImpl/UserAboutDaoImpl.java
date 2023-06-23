/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.AppraisalBean;
import com.valoux.bean.AppraisalCollectionBean;
import com.valoux.bean.AppraisalItemsBean;
import com.valoux.bean.UserAboutBean;
import com.valoux.bean.ValouxAccessPermissionBean;
import com.valoux.bean.ValouxCollectionBean;
import com.valoux.bean.ValouxCollectionItemBean;
import com.valoux.bean.ValouxItemBean;
import com.valoux.dao.AppraisalDao;
import com.valoux.dao.UserAboutDao;

/**
 * This <java>class</java> AppraisalDaoImpl use to perform all our DB related
 * logics for the appraisal.
 * 
 * @author param Sheikh
 * 
 */

public class UserAboutDaoImpl implements UserAboutDao {
	
	@Autowired
	@Qualifier(value = "sessionFactory")
	private SessionFactory sessionFactory;

	private static final Logger LOGGER = Logger.getLogger(UserAboutDaoImpl.class);

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * This method performs save UserAbout In For User.
	 */
	public void saveUserAboutForUser(List<UserAboutBean> userAboutList) throws Exception {
		LOGGER.debug("Enter Method saveUserAboutForUser of UserDaoImpl");
		for (UserAboutBean userAbout : userAboutList) {
			sessionFactory.getCurrentSession().save(userAbout);
		}
		LOGGER.debug("Exit Method saveUserAboutForUser of UserDaoImpl");
	}
	
	/**
	 * This method performs get User About By RKey.
	 */

	public List<UserAboutBean> getUserAboutByRKey(String rKey) throws Exception {
		LOGGER.debug("Enter Method getUserAboutByRKey of UserDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserAboutBean.class);
		criteria.add(Restrictions.eq("relationKey", rKey));
		@SuppressWarnings("unchecked")
		List<UserAboutBean> userPreferenceList = (List<UserAboutBean>) criteria.list();
		LOGGER.debug("Exit Method getUserAboutByRKey of UserDaoImpl");
		return userPreferenceList;
	}
	
	/**
	 * This method performs delete User About Detail.
	 */
	public void deleteUserAboutDetail(String relationKey) throws Exception {
		LOGGER.debug("Enter Method deleteUserAboutDetail of UserDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserAboutBean.class);
		criteria.add(Restrictions.eq("relationKey", relationKey));
		@SuppressWarnings("unchecked")
		List<UserAboutBean> userAboutList = (List<UserAboutBean>) criteria.list();
		if (userAboutList != null && userAboutList.size() > 0) {
			for (UserAboutBean userAbout : userAboutList) {
				sessionFactory.getCurrentSession().delete(userAbout);
			}
		}
		LOGGER.debug("Exit Method deleteUserAboutDetail of UserDaoImpl");
	}



}
