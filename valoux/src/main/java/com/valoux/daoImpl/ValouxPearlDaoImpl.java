package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ValouxPearlBean;
import com.valoux.dao.ValouxPearlDao;

public class ValouxPearlDaoImpl implements ValouxPearlDao {

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

	private static final Logger LOGGER = Logger.getLogger(ValouxPearlDaoImpl.class);
	
	/**
	 * This method will add item component property pearl
	 */
	public void addValouxComponentPearlProperty(ValouxPearlBean pearlBean) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method addValouxComponentGemstoneProperty");

		Transaction trans = thirdDBSessionFactory.getCurrentSession().beginTransaction();

		if (pearlBean != null) {
//			if (pearlBean.getVpid() != null) {
//				thirdDBSessionFactory.getCurrentSession().update(pearlBean);
//			} else {
//				thirdDBSessionFactory.getCurrentSession().save(pearlBean);
//			}
			thirdDBSessionFactory.getCurrentSession().persist(pearlBean);
		}
		trans.commit();

		LOGGER.debug("ItemDaoImpl : Exit Method addValouxComponentGemstoneProperty");
	}

	/**
	 * This method used to get item component pearl
	 */
	public ValouxPearlBean getComponentPearlBeanByItemAndComponentId(
			Integer itemId, Integer componentId) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter method getComponentPearlBeanByItemAndComponentId");

		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxPearlBean.class);
		criteria.add(Restrictions.eq("valouxItem.itemId", itemId));
		criteria.add(Restrictions.eq("valouxItemComponent.vicid", componentId));
		ValouxPearlBean componentBean = (ValouxPearlBean) criteria.uniqueResult();

		LOGGER.debug("ItemDaoImpl : Exit method getComponentPearlBeanByItemAndComponentId");
		return componentBean;
	}
	
	/**
	 * This method fetch list of Pearl component
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxPearlBean> getPearlComponentsByComponentId(int componentId) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getPearlComponentsByComponentId");
		List<ValouxPearlBean> componentBeans = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxPearlBean.class);
		criteria.add(Restrictions.eq("valouxItemComponent.vicid", componentId));
		componentBeans = (List<ValouxPearlBean>) criteria.list();
		LOGGER.debug("ItemDaoImpl : Exit Method getPearlComponentsByComponentId");
		return componentBeans;
	}
}
