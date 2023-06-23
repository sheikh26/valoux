package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.UserPersonalPreferences;
import com.valoux.dao.UserDao;
import com.valoux.dao.UserPersonalPreferencesDao;

public class UserPersonalPreferencesDaoImpl implements UserPersonalPreferencesDao {
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
	 * This method performs save Personal Preferences For User.
	 */

	public void savePersonalPreferencesForUser(List<UserPersonalPreferences> preferencesUserList) throws Exception {
		LOGGER.debug("Enter Method savePersonalPreferencesForUser of UserDaoImpl");
		for (UserPersonalPreferences personalPreferences : preferencesUserList) {
			sessionFactory.getCurrentSession().save(personalPreferences);
		}
		LOGGER.debug("Exit Method savePersonalPreferencesForUser of UserDaoImpl");
	}
	
	/**
	 * This method performs get User Preferences By RKey.
	 */

	public List<UserPersonalPreferences> getUserPreferencesByRKey(String rKey) throws Exception {
		LOGGER.debug("Enter Method getUserPreferencesByRKey of UserDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserPersonalPreferences.class);
		criteria.add(Restrictions.eq("relationKey", rKey));
		@SuppressWarnings("unchecked")
		List<UserPersonalPreferences> userPreferenceList = (List<UserPersonalPreferences>) criteria.list();
		LOGGER.debug("Exit Method getUserPreferencesByRKey of UserDaoImpl");
		return userPreferenceList;
	}
	
	/**
	 * This method performs delete User Preference Detail.
	 */
	public void deleteUserPreferenceDetail(String relationKey) throws Exception {
		LOGGER.debug("Enter Method deleteUserPreferenceDetail of UserDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserPersonalPreferences.class);
		criteria.add(Restrictions.eq("relationKey", relationKey));
		@SuppressWarnings("unchecked")
		List<UserPersonalPreferences> userPreferenceList = (List<UserPersonalPreferences>) criteria.list();
		if (userPreferenceList != null && userPreferenceList.size() > 0) {
			for (UserPersonalPreferences userPersonalPreference : userPreferenceList) {
				sessionFactory.getCurrentSession().delete(userPersonalPreference);
			}
		}
		LOGGER.debug("Exit Method deleteUserPreferenceDetail of UserDaoImpl");

	}


}
