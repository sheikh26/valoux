package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.UserRoleBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.UserDao;
import com.valoux.dao.UserRoleDao;

public class UserRoleDaoImpl implements UserRoleDao {

	@Autowired
	@Qualifier(value = "secondDBSessionFactory")
	private SessionFactory secondDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

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
	 * This method performs get Roll.
	 */
	public UserRoleBean getRole(String relationKey) throws Exception {
		LOGGER.debug("Enter Method getRole of UserDaoImpl");
		UserRoleBean userRelationKey = null;
		Criteria criteria = secondDBSessionFactory.getCurrentSession().createCriteria(UserRoleBean.class);
		criteria.add(Restrictions.eq("relationKey", relationKey));
		userRelationKey = (UserRoleBean) criteria.uniqueResult();
		LOGGER.debug("Exit Method getRole of UserDaoImpl");
		return userRelationKey;
	}
	
	/**
	 * This method performs get List Of User As Super Admin Role.
	 */
	public List<UserRoleBean> getListOfUserAsSuperAdminRole() throws Exception {
		LOGGER.debug("Enter Method getListOfUserAsSuperAdminRole of UserDaoImpl");
		Criteria criteria = secondDBSessionFactory.getCurrentSession().createCriteria(UserRoleBean.class);
		criteria.add(Restrictions.eq("masterRoleBean.roleId", CommonConstants.SUPER_ADMIN));
		@SuppressWarnings("unchecked")
		List<UserRoleBean> roleBeanList = (List<UserRoleBean>) criteria.list();

		LOGGER.debug("Exit Method getListOfUserAsSuperAdminRole of UserDaoImpl");
		return roleBeanList;
	}

}
