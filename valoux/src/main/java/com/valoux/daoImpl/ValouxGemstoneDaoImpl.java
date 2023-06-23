package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ValouxGemstoneBean;
import com.valoux.dao.ValouxGemstoneDao;

public class ValouxGemstoneDaoImpl implements ValouxGemstoneDao {

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

	private static final Logger LOGGER = Logger.getLogger(ValouxGemstoneDaoImpl.class);
	
	/**
	 * This method will add item component property gemstone
	 */
	public void addValouxComponentGemstoneProperty(ValouxGemstoneBean gemstoneBean) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method addValouxComponentGemstoneProperty");

		Transaction trans = thirdDBSessionFactory.getCurrentSession().beginTransaction();

		if (gemstoneBean != null) {
//			if (gemstoneBean.getVgid() != null) {
//				thirdDBSessionFactory.getCurrentSession().update(gemstoneBean);
//			} else {
//				thirdDBSessionFactory.getCurrentSession().save(gemstoneBean);
//			}
			thirdDBSessionFactory.getCurrentSession().persist(gemstoneBean);
		}
		trans.commit();

		LOGGER.debug("ItemDaoImpl : Exit Method addValouxComponentGemstoneProperty");
	}
	
	/**
	 * This method used to get item component gemstone
	 */
	public ValouxGemstoneBean getComponentGemstoneBeanByItemAndComponentId(
			Integer itemId, Integer componentId) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter method getComponentGemstoneBeanByItemAndComponentId");

		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxGemstoneBean.class);
		criteria.add(Restrictions.eq("valouxItem.itemId", itemId));
		criteria.add(Restrictions.eq("valouxItemComponent.vicid", componentId));
		ValouxGemstoneBean componentBean = (ValouxGemstoneBean) criteria.uniqueResult();

		LOGGER.debug("ItemDaoImpl : Exit method getComponentGemstoneBeanByItemAndComponentId");
		return componentBean;
	}
	
	/**
	 * This method fetch list of Gemstone component
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxGemstoneBean> getGemstoneComponentsByComponentId(int componentId) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getGemstoneComponentsByComponentId");
		List<ValouxGemstoneBean> componentBeans = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxGemstoneBean.class);
		criteria.add(Restrictions.eq("valouxItemComponent.vicid", componentId));
		componentBeans = (List<ValouxGemstoneBean>) criteria.list();
		LOGGER.debug("ItemDaoImpl : Exit Method getGemstoneComponentsByComponentId");
		return componentBeans;
	}

}
