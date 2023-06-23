package com.valoux.daoImpl;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.UserRoleBean;
import com.valoux.bean.UserTypeBean;
import com.valoux.dao.UserDao;
import com.valoux.dao.UserTypeDao;

public class UserTypeDaoImpl implements UserTypeDao {
	@Autowired
	@Qualifier(value = "sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	@Qualifier(value = "secondDBSessionFactory")
	private SessionFactory secondDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(UserTypeDaoImpl.class);

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
	 * This method creates User Role.
	 */

	public UserRoleBean createUserRole(UserRoleBean userRoleBean) throws Exception {
		LOGGER.debug("Enter Method createUserRole of UserDaoImpl");
		secondDBSessionFactory.getCurrentSession().save(userRoleBean);
		LOGGER.debug("Exit Method createUserRole of UserDaoImpl");
		return userRoleBean;
	}
	
	/**
	 * This method creates User Type.
	 */

	public UserTypeBean createUserType(UserTypeBean userTypeBean) throws Exception {
		LOGGER.debug("Enter Method createUserType of UserDaoImpl");
		secondDBSessionFactory.getCurrentSession().save(userTypeBean);
		LOGGER.debug("Exit Method createUserType of UserDaoImpl");
		return userTypeBean;
	}
	
	/**
	 * This method performs get User Type.
	 */
	public UserTypeBean getUserType(String relationKey) throws Exception {
		LOGGER.debug("Enter Method getUserType of UserDaoImpl");
		UserTypeBean userRelationKey = null;
		Criteria criteria = secondDBSessionFactory.getCurrentSession().createCriteria(UserTypeBean.class);
		criteria.add(Restrictions.eq("relationKey", relationKey));
		userRelationKey = (UserTypeBean) criteria.uniqueResult();
		LOGGER.debug("Exit Method getUserType of UserDaoImpl");
		return userRelationKey;
	}

}
