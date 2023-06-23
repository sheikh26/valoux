package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.UserJewelryComponentsBean;
import com.valoux.dao.UserDao;
import com.valoux.dao.UserJewelryComponentsDao;

public class UserJewelryComponentsDaoImpl  implements UserJewelryComponentsDao {
	@Autowired
	@Qualifier(value = "sessionFactory")
	private SessionFactory sessionFactory;

	private static final Logger LOGGER = Logger.getLogger(UserJewelryComponentsDaoImpl.class);

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * This method performs save JewelryComponents For User.
	 */
	public void saveJewelryComponentsForUser(List<UserJewelryComponentsBean> JewelryComponentsList) throws Exception {
		LOGGER.debug("Enter Method saveJewelryComponentsForUser of UserDaoImpl");
		for (UserJewelryComponentsBean userJewelryComponentsBean : JewelryComponentsList) {
			sessionFactory.getCurrentSession().save(userJewelryComponentsBean);
		}
		LOGGER.debug("Exit Method saveJewelryComponentsForUser of UserDaoImpl");
	}
	
	/**
	 * This method performs get JewelryComponents By RKey.
	 */

	public List<UserJewelryComponentsBean> getJewelryComponentsByRKey(String rKey) throws Exception {
		LOGGER.debug("Enter Method getJewelryComponentsByRKey of UserDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserJewelryComponentsBean.class);
		criteria.add(Restrictions.eq("relationKey", rKey));
		@SuppressWarnings("unchecked")
		List<UserJewelryComponentsBean> userPreferenceList = (List<UserJewelryComponentsBean>) criteria.list();
		LOGGER.debug("Exit Method getJewelryComponentsByRKey of UserDaoImpl");
		return userPreferenceList;
	}
	
	/**
	 * This method performs delete User JewelryComponents Detail.
	 */
	public void deleteJewelryComponentsDetail(String relationKey) throws Exception {
		LOGGER.debug("Enter Method deleteJewelryComponentsDetail of UserDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserJewelryComponentsBean.class);
		criteria.add(Restrictions.eq("relationKey", relationKey));
		@SuppressWarnings("unchecked")
		List<UserJewelryComponentsBean> userUserJewelryComponentsBeanList = (List<UserJewelryComponentsBean>) criteria
				.list();
		if (userUserJewelryComponentsBeanList != null && userUserJewelryComponentsBeanList.size() > 0) {
			for (UserJewelryComponentsBean userJewelryComponents : userUserJewelryComponentsBeanList) {
				sessionFactory.getCurrentSession().delete(userJewelryComponents);
			}
		}
		LOGGER.debug("Exit Method deleteJewelryComponentsDetail of UserDaoImpl");
	}


}
