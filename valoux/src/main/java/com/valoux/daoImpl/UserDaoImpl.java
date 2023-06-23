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

import com.valoux.bean.UserBean;
import com.valoux.dao.UserDao;

/**
 * This <java>class</java> UserDaoImpl use to perform all our DB related logics
 * for the user.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

public class UserDaoImpl implements UserDao {
	@Autowired
	@Qualifier(value = "sessionFactory")
	private SessionFactory sessionFactory;

	private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * This method creates User.
	 */

	public UserBean createUser(UserBean userBean) throws Exception {
		LOGGER.debug("Enter Method createUser of UserDaoImpl");
		sessionFactory.getCurrentSession().save(userBean);
		LOGGER.debug("Exit Method createUser of UserDaoImpl");
		return userBean;
	}

	/**
	 * This method performs check New Genarated RelationKey.
	 */

	public UserBean checkNewGenaratedRelationKey(String relationKey) throws Exception {
		LOGGER.debug("Enter Method checkNewGenaratedRelationKey of UserDaoImpl");
		UserBean userRelationKey = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserBean.class);
		criteria.add(Restrictions.eq("rkey", relationKey));
		userRelationKey = (UserBean) criteria.uniqueResult();
		LOGGER.debug("Exit Method checkNewGenaratedRelationKey of UserDaoImpl");
		return userRelationKey;
	}

	/**
	 * This method performs get Relational Key.
	 */

	public UserBean getUserDetails(String userName) throws Exception {
		LOGGER.debug("Enter Method getUserDetails of UserDaoImpl");
		UserBean userRelationKey = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserBean.class);
		criteria.add(Restrictions.eq("emailId", userName));
		userRelationKey = (UserBean) criteria.uniqueResult();
		LOGGER.debug("Exit Method getUserDetails of UserDaoImpl");
		return userRelationKey;
	}

	/**
	 * This method performs get User Detail By User Id.
	 */
	public UserBean getUserDetailByUserId(Integer userId) throws Exception {
		LOGGER.debug("Enter Method getUserDetail of UserDaoImpl");
		UserBean userBean = null;
		sessionFactory.getCurrentSession().beginTransaction();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserBean.class);
		criteria.add(Restrictions.eq("customerId", userId));
		userBean = (UserBean) criteria.uniqueResult();
		sessionFactory.getCurrentSession().getTransaction().commit();
		LOGGER.debug("Exit Method getUserDetail of UserDaoImpl");
		return userBean;
	}

	/**
	 * This method performs update User.
	 */
	public UserBean updateUser(UserBean userBean) throws Exception {
		LOGGER.debug("Enter Method updateUser of UserDaoImpl");
		sessionFactory.getCurrentSession().update(userBean);
		LOGGER.debug("Exit Method updateUser of UserDaoImpl");
		return userBean;
	}

	/**
	 * This method performs get Consumer Detail By RKey.
	 */

	public UserBean getConsumerDetailByRKey(String rKey) throws Exception {
		LOGGER.debug("Enter Method getConsumerDetailByRKey of UserDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserBean.class);
		criteria.add(Restrictions.eq("relationKey", rKey));
		UserBean userBean = (UserBean) criteria.uniqueResult();
		LOGGER.debug("Exit Method getConsumerDetailByRKey of UserDaoImpl");
		return userBean;
	}

	/**
	 * This method get All consumer detail.
	 */
	public List<UserBean> getAllConsumerDetail() throws Exception {
		LOGGER.debug("Enter Method getAllConsumerDetail of UserDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserBean.class);
		@SuppressWarnings("unchecked")
		List<UserBean> userBeanlist = (List<UserBean>) criteria.list();
		LOGGER.debug("Exit Method getAllConsumerDetail of UserDaoImpl");
		return userBeanlist;
	}
	
	
	/**
	 * This method delete user component object
	 */
	public void deleteAnyBeanByObject(Object objectBean)
			throws Exception {
		LOGGER.debug("UserDaoImpl : Enter Method deleteAnyBeanByObject");

		sessionFactory.getCurrentSession().delete(objectBean);

		LOGGER.debug("UserDaoImpl : Exit Method deleteAnyBeanByObject");
		
	}

}
