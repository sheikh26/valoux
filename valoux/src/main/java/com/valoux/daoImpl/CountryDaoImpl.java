package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.CountryBean;
import com.valoux.dao.CountryDao;

public class CountryDaoImpl implements CountryDao {
	@Autowired
	@Qualifier(value = "sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	@Qualifier(value = "secondDBSessionFactory")
	private SessionFactory secondDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(CountryDaoImpl.class);

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
	 * This method performs get Country Id.
	 */
	public List<CountryBean> getCountryId(String countryName) throws Exception {
		LOGGER.debug("Enter Method getCountryId of CountryDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CountryBean.class);
		criteria.add(Restrictions.eq("name", countryName));
		@SuppressWarnings("unchecked")
		List<CountryBean> CountryBeanList = (List<CountryBean>) criteria.list();
		LOGGER.debug("Exit Method getCountryId of CountryDaoImpl");
		return CountryBeanList;
	}

	/**
	 * This method performs save Country.
	 */

	public CountryBean saveCountry(CountryBean countryBean) throws Exception {
		LOGGER.debug("Enter Method saveCountry of CountryDaoImpl");
		sessionFactory.getCurrentSession().save(countryBean);
		LOGGER.debug("Exit Method saveCountry of CountryDaoImpl");
		return countryBean;
	}
	
	/**
	 * This method performs get Country Name By CountryId.
	 */
	public CountryBean getCountryNameByCountryId(Integer countryId) throws Exception {
		LOGGER.debug("Enter Method getCountryNameByCountryId of CountryDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CountryBean.class);
		criteria.add(Restrictions.eq("countryId", countryId));
		CountryBean countryBean = (CountryBean) criteria.uniqueResult();
		LOGGER.debug("Enter Method getCountryNameByCountryId of CountryDaoImpl");
		return countryBean;
	}

	/*
	 * This method return all country details
	 */
	@SuppressWarnings("unchecked")
	public List<CountryBean> getAllCountryDetails() {
		LOGGER.debug("Enter Method getCountryId of CountryDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CountryBean.class);
		criteria.addOrder(Order.asc("name"));
		List<CountryBean> countryBeanList = (List<CountryBean>) criteria.list();
		LOGGER.debug("Exit Method getCountryId of CountryDaoImpl");
		return countryBeanList;
	}
}
