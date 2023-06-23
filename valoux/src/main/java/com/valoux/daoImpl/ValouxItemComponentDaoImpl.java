package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ValouxItemComponentBean;
import com.valoux.dao.ValouxItemComponentDao;

public class ValouxItemComponentDaoImpl implements ValouxItemComponentDao {

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

	private static final Logger LOGGER = Logger.getLogger(ValouxItemComponentDaoImpl.class);
	
	/**
	 * This method save item component
	 */
	public List<ValouxItemComponentBean> saveValouxItemComponents(List<ValouxItemComponentBean> componentBeanList)
			throws Exception {

		LOGGER.debug("ItemDaoImpl : Enter Method saveValouxItemComponents");

		for (ValouxItemComponentBean valouxItemComponentBean : componentBeanList) {

			if (valouxItemComponentBean.getVicid() != null) {
				thirdDBSessionFactory.getCurrentSession().update(valouxItemComponentBean);
			} else {
				thirdDBSessionFactory.getCurrentSession().save(valouxItemComponentBean);
			}
		}

		LOGGER.debug("ItemDaoImpl : Exit Method saveValouxItemComponents");

		return componentBeanList;
	}

	/**
	 * This method fetch list of item component
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxItemComponentBean> getComponentsByItemId(Integer itemId) throws Exception {

		LOGGER.debug("ItemDaoImpl : Enter Method getComponentsByItemId");

		List<ValouxItemComponentBean> componentBeans = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxItemComponentBean.class);
		criteria.add(Restrictions.eq("valouxItem.itemId", itemId));
		componentBeans = (List<ValouxItemComponentBean>) criteria.list();

		LOGGER.debug("ItemDaoImpl : Exit Method getComponentsByItemId");
		return componentBeans;
	}

	/**
	 * This method will fetch item component
	 */
	public ValouxItemComponentBean getItemComponentByItemAndComponentId(Integer itemId, Integer componentId)
			throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter method getItemComponentByItemAndComponentId");

		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxItemComponentBean.class);
		criteria.add(Restrictions.eq("valouxItem.itemId", itemId));
		criteria.add(Restrictions.eq("vicid", componentId));
		ValouxItemComponentBean componentBean = (ValouxItemComponentBean) criteria.uniqueResult();

		LOGGER.debug("ItemDaoImpl : Exit method getItemComponentByItemAndComponentId");
		return componentBean;
	}

	/**
	 * This method will delete item component
	 */
	public void deleteValouxItemComponentBean(ValouxItemComponentBean componentBean) throws Exception {

		LOGGER.debug("ItemDaoImpl : Enter Method deleteValouxItemComponentBean");

		thirdDBSessionFactory.getCurrentSession().delete(componentBean);

		LOGGER.debug("ItemDaoImpl : Exit Method deleteValouxItemComponentBean");
	}

	/**
	 * This method will add update item component
	 */
	public void saveValouxItemComponent(ValouxItemComponentBean componentBean)
			throws Exception {
		Transaction trans = thirdDBSessionFactory.getCurrentSession().beginTransaction();

		if (componentBean != null) {
			thirdDBSessionFactory.getCurrentSession().persist(componentBean);
		}
		trans.commit();

		LOGGER.debug("ItemDaoImpl : Exit Method addValouxComponentDiamondProperty");
		
	}

	public ValouxItemComponentBean getItemComponentByComponentId(
			Integer componentId) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter method getItemComponentByComponentId");

		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxItemComponentBean.class);
		criteria.add(Restrictions.eq("vicid", componentId));
		ValouxItemComponentBean componentBean = (ValouxItemComponentBean) criteria.uniqueResult();

		LOGGER.debug("ItemDaoImpl : Exit method getItemComponentByComponentId");
		return componentBean;
	}

}
