/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.AppraisalBean;
import com.valoux.bean.ValouxAppraisalItemsComponentPriceBean;
import com.valoux.bean.ValouxAppraisalItemsPriceBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.ValouxAppraisalItemsPriceDao;

/**
 * This <java>class</java> AppraisalDaoImpl use to perform all our DB related
 * logics for the appraisal.
 * 
 * @author param Sheikh
 * 
 */

public class ValouxAppraisalItemsPriceDaoImpl implements ValouxAppraisalItemsPriceDao {

	@Autowired
	@Qualifier(value = "thirdDBSessionFactory")
	private SessionFactory thirdDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(ValouxAppraisalItemsPriceDaoImpl.class);

	public SessionFactory getThirdDBSessionFactory() {
		return thirdDBSessionFactory;
	}

	public void setThirdDBSessionFactory(SessionFactory thirdDBSessionFactory) {
		this.thirdDBSessionFactory = thirdDBSessionFactory;
	}

	public void addAppraisalItemsPrice(
			ValouxAppraisalItemsPriceBean appraisalItemsPriceBean)
			throws Exception {
		LOGGER.debug("Enter Method addAppraisalItemsPrice of ItemDaoImpl");
		Transaction trans = thirdDBSessionFactory.getCurrentSession().beginTransaction();
		if(appraisalItemsPriceBean != null){
			thirdDBSessionFactory.getCurrentSession().persist(appraisalItemsPriceBean);
		}
		trans.commit();
		LOGGER.debug("Exit Method addAppraisalItemsPrice of ItemDaoImpl");		
	}

	public ValouxAppraisalItemsPriceBean getAppraisalItemsComponentPriceByAppraisalAndItemId(
			Integer itemId, Integer appraisalId) throws Exception {
		LOGGER.debug("Enter Method getAppraisalItemsComponentPriceByAppraisalAndItemId of AppraisalDaoImpl");
		ValouxAppraisalItemsPriceBean appraisalBean = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxAppraisalItemsPriceBean.class);
		criteria.add(Restrictions.eq("valouxItem.itemId", itemId));
		criteria.add(Restrictions.eq("valouxAppraisal.appraisalId", appraisalId));
		appraisalBean = (ValouxAppraisalItemsPriceBean) criteria.uniqueResult();
		LOGGER.debug("Exit Method getAppraisalItemsComponentPriceByAppraisalAndItemId of AppraisalDaoImpl");
		return appraisalBean;
	}

}
