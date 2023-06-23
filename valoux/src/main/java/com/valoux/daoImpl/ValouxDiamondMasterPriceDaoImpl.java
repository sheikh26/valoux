package com.valoux.daoImpl;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ValouxCollectionBean;
import com.valoux.bean.ValouxDiamondMasterPriceBean;
import com.valoux.dao.ValouxDiamondMasterPriceDao;

public class ValouxDiamondMasterPriceDaoImpl implements ValouxDiamondMasterPriceDao {
	
	@Autowired
	@Qualifier(value = "thirdDBSessionFactory")
	private SessionFactory thirdDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(ValouxDiamondMasterPriceDaoImpl.class);

	public SessionFactory getThirdDBSessionFactory() {
		return thirdDBSessionFactory;
	}

	public void setThirdDBSessionFactory(SessionFactory thirdDBSessionFactory) {
		this.thirdDBSessionFactory = thirdDBSessionFactory;
	}

	public ValouxDiamondMasterPriceBean getItemComponentDiamondSpecifyPrice(
			Double caratFrom, Double caratTo, String shape, String color)
			throws Exception {
		
		LOGGER.debug("Enter Method getItemComponentDiamondSpecifyPrice of ValouxDiamondMasterPriceDaoImpl");
		ValouxDiamondMasterPriceBean resultBean = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxDiamondMasterPriceBean.class);
		criteria.add(Restrictions.eq("caratFrom", caratFrom));
		criteria.add(Restrictions.eq("caratTo", caratTo));
		criteria.add(Restrictions.eq("dCut", shape));
		criteria.add(Restrictions.eq("color", color));
		criteria.addOrder(Order.desc("priceDate"));
		criteria.setMaxResults(1);
		resultBean = (ValouxDiamondMasterPriceBean) criteria.uniqueResult();
		LOGGER.debug("Exit Method getItemComponentDiamondSpecifyPrice of ValouxDiamondMasterPriceDaoImpl");
		return resultBean;
	}
	
}

	
