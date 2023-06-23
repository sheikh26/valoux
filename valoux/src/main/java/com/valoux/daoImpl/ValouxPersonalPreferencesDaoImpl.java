package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.UserPersonalPreferences;
import com.valoux.bean.ValouxPersonalPreferencesBean;
import com.valoux.dao.ValouxPersonalPreferencesDao;

public class ValouxPersonalPreferencesDaoImpl implements ValouxPersonalPreferencesDao {
	@Autowired
	@Qualifier(value = "sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	@Qualifier(value = "secondDBSessionFactory")
	private SessionFactory secondDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(ValouxPersonalPreferencesDaoImpl.class);

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
	 * This method performs get All Personal Preferences.
	 */

	public List<ValouxPersonalPreferencesBean> getAllPersonalPreferences() throws Exception {
		LOGGER.debug("Enter Method getAllPersonalPreferences of UserDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxPersonalPreferencesBean.class);
		criteria.add(Restrictions.eq("ppstatus", 1));
		@SuppressWarnings("unchecked")
		List<ValouxPersonalPreferencesBean> valouxInterestInBeanList = (List<ValouxPersonalPreferencesBean>) criteria
				.list();
		LOGGER.debug("Exit Method getAllPersonalPreferences of UserDaoImpl");
		return valouxInterestInBeanList;
	}
	
	/**
	 * This method get user preference detail by Id.
	 */
	public ValouxPersonalPreferencesBean getUserPreferencesPreferencesId(int preferencesId) throws Exception {
		LOGGER.debug("UserDaoImpl : Enter Method getUserPreferencesPreferencesId");

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxPersonalPreferencesBean.class);
		criteria.add(Restrictions.eq("PersonalId", preferencesId));
		ValouxPersonalPreferencesBean personalPreferencesBean = (ValouxPersonalPreferencesBean) criteria.uniqueResult();

		LOGGER.debug("UserDaoImpl : Exit Method getUserPreferencesPreferencesId");
		return personalPreferencesBean;
	}

	
}
