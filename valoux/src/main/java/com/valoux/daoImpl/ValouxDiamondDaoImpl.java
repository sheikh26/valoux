package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ValouxDiamondBean;
import com.valoux.dao.ValouxDiamondDao;

public class ValouxDiamondDaoImpl implements ValouxDiamondDao {

	@Autowired
	@Qualifier(value = "thirdDBSessionFactory")
	private SessionFactory thirdDBSessionFactory;

	public SessionFactory getThirdDBSessionFactory() {
		return thirdDBSessionFactory;
	}

	public void setThirdDBSessionFactory(SessionFactory thirdDBSessionFactory) {
		this.thirdDBSessionFactory = thirdDBSessionFactory;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	private static final Logger LOGGER = Logger.getLogger(ValouxDiamondDaoImpl.class);
	
	/**
	 * This method will add item component property diamond
	 */
	public void addValouxComponentDiamondProperty(ValouxDiamondBean diamondBean, String requestType) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method addValouxComponentDiamondProperty");

		Transaction trans = thirdDBSessionFactory.getCurrentSession().beginTransaction();

		if (diamondBean != null) {
			thirdDBSessionFactory.getCurrentSession().persist(diamondBean);
		}
		trans.commit();

		LOGGER.debug("ItemDaoImpl : Exit Method addValouxComponentDiamondProperty");
	}

	/**
	 * This method used to get valoux diamond bean
	 */
	public ValouxDiamondBean getComponentDiamondBeanByItemAndComponentId(Integer itemId, Integer componentId)
			throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter method getComponentDiamondBeanByItemAndComponentId");

		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxDiamondBean.class);
		criteria.add(Restrictions.eq("valouxItem.itemId", itemId));
		criteria.add(Restrictions.eq("valouxItemComponent.vicid", componentId));
		ValouxDiamondBean componentBean = (ValouxDiamondBean) criteria.uniqueResult();

		LOGGER.debug("ItemDaoImpl : Exit method getComponentDiamondBeanByItemAndComponentId");
		return componentBean;
	}
	
	/**
	 * This method fetch list of Diamond component
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxDiamondBean> getDiamondComponentsByComponentId(int componentId) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getDiamondComponentsByComponentId");
		List<ValouxDiamondBean> componentBeans = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxDiamondBean.class);
		criteria.add(Restrictions.eq("valouxItemComponent.vicid", componentId));
		componentBeans = (List<ValouxDiamondBean>) criteria.list();
		LOGGER.debug("ItemDaoImpl : Exit Method getDiamondComponentsByComponentId");
		return componentBeans;
	}
}
