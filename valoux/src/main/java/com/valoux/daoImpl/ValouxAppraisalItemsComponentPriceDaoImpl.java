/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.daoImpl;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ValouxAppraisalItemsComponentPriceBean;
import com.valoux.dao.ValouxAppraisalItemsComponentPriceDao;

/**
 * This <java>class</java> AppraisalDaoImpl use to perform all our DB related
 * logics for the appraisal.
 * 
 * @author param Sheikh
 * 
 */

public class ValouxAppraisalItemsComponentPriceDaoImpl implements ValouxAppraisalItemsComponentPriceDao {

	@Autowired
	@Qualifier(value = "thirdDBSessionFactory")
	private SessionFactory thirdDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(ValouxAppraisalItemsComponentPriceDaoImpl.class);

	public SessionFactory getThirdDBSessionFactory() {
		return thirdDBSessionFactory;
	}

	public void setThirdDBSessionFactory(SessionFactory thirdDBSessionFactory) {
		this.thirdDBSessionFactory = thirdDBSessionFactory;
	}

	/**
	 *  Method for appraisal item price properties
	 */
	public void addAppraisalItemsComponentPrice(
			ValouxAppraisalItemsComponentPriceBean appraisalComponentPriceBean)
			throws Exception {
		LOGGER.debug("Enter Method addAppraisalItemsComponentPrice of ItemDaoImpl");
		Transaction trans = thirdDBSessionFactory.getCurrentSession().beginTransaction();
		if(appraisalComponentPriceBean != null){
			thirdDBSessionFactory.getCurrentSession().persist(appraisalComponentPriceBean);
		}
		trans.commit();
		LOGGER.debug("Exit Method addAppraisalItemsComponentPrice of ItemDaoImpl");
		
	}

	public ValouxAppraisalItemsComponentPriceBean getAppraisalItemsComponentPriceByAppraisalAndComponentId(
			Integer vicid, Integer appraisalId) throws Exception {
		LOGGER.debug("Enter Method getAppraisalItemsComponentPriceByAppraisalAndComponentId of AppraisalDaoImpl");
		ValouxAppraisalItemsComponentPriceBean appraisalBean = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxAppraisalItemsComponentPriceBean.class);
		criteria.add(Restrictions.eq("valouxItemComponent.vicid", vicid));
		criteria.add(Restrictions.eq("valouxAppraisal.appraisalId", appraisalId));
		appraisalBean = (ValouxAppraisalItemsComponentPriceBean) criteria.uniqueResult();
		LOGGER.debug("Exit Method getAppraisalItemsComponentPriceByAppraisalAndComponentId of AppraisalDaoImpl");
		return appraisalBean;
	}

}
