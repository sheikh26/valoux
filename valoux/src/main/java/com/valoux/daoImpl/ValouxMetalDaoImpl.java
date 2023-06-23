package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ValouxMetalBean;
import com.valoux.dao.ValouxMetalDao;

public class ValouxMetalDaoImpl implements ValouxMetalDao {

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

	private static final Logger LOGGER = Logger.getLogger(ValouxMetalDaoImpl.class);

	/**
	 * This method will add item component property metal
	 */
	public void addValouxComponentMetalProperty(ValouxMetalBean metalBean) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method addValouxComponentMetalProperty");

		Transaction trans = thirdDBSessionFactory.getCurrentSession().beginTransaction();

		if (metalBean != null) {
//			if (metalBean.getVmid() != null) {
//				thirdDBSessionFactory.getCurrentSession().update(metalBean);
//			} else {
//				thirdDBSessionFactory.getCurrentSession().save(metalBean);
//			}
			thirdDBSessionFactory.getCurrentSession().persist(metalBean);
		}
		trans.commit();

		LOGGER.debug("ItemDaoImpl : Exit Method addValouxComponentMetalProperty");

	}
	
	/**
	 * This method used to get item component metal
	 */
	public ValouxMetalBean getComponentMetalBeanByItemAndComponentId(
			Integer itemId, Integer componentId) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter method getComponentMetalBeanByItemAndComponentId");

		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxMetalBean.class);
		criteria.add(Restrictions.eq("valouxItem.itemId", itemId));
		criteria.add(Restrictions.eq("valouxItemComponent.vicid", componentId));
		ValouxMetalBean componentBean = (ValouxMetalBean) criteria.uniqueResult();

		LOGGER.debug("ItemDaoImpl : Exit method getComponentMetalBeanByItemAndComponentId");
		return componentBean;
	}
	
	/**
	 * This method fetch list of Metal component
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxMetalBean> getMetalComponentsByComponentId(int componentId) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getMetalComponentsByComponentId");
		List<ValouxMetalBean> componentBeans = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxMetalBean.class);
		criteria.add(Restrictions.eq("valouxItemComponent.vicid", componentId));
		componentBeans = (List<ValouxMetalBean>) criteria.list();
		LOGGER.debug("ItemDaoImpl : Exit Method getMetalComponentsByComponentId");
		return componentBeans;
	}
}
