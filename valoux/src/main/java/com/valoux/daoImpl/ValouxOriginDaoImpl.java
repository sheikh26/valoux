package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ValouxOriginBean;
import com.valoux.dao.ValouxOriginDao;

public class ValouxOriginDaoImpl implements ValouxOriginDao {
	@Autowired
	@Qualifier(value = "sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	@Qualifier(value = "secondDBSessionFactory")
	private SessionFactory secondDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(ValouxOriginDaoImpl.class);

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
	 * This method performs get Country Name By CountryId.
	 */
	public ValouxOriginBean getCountryOriginNameByCountryId(Integer countryId) throws Exception {
		LOGGER.debug("Enter Method getCountryOriginNameByCountryId of ValouxOriginDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxOriginBean.class);
		criteria.add(Restrictions.eq("id", countryId));
		ValouxOriginBean countryBean = (ValouxOriginBean) criteria.uniqueResult();
		LOGGER.debug("Enter Method getCountryOriginNameByCountryId of ValouxOriginDaoImpl");
		return countryBean;
	}

	/*
	 * This method return all country details
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxOriginBean> getAllCountryOriginDetails() {
		LOGGER.debug("Enter Method getAllCountryOriginDetails of ValouxOriginDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxOriginBean.class);
		criteria.addOrder(Order.asc("name"));
		List<ValouxOriginBean> countryBeanList = (List<ValouxOriginBean>) criteria.list();
		LOGGER.debug("Exit Method getAllCountryOriginDetails of ValouxOriginDaoImpl");
		return countryBeanList;
	}
}
