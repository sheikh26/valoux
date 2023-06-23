package com.valoux.daoImpl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ValouxMetalsMasterPriceBean;
import com.valoux.dao.ValouxMetalMasterPriceDao;

public class ValouxMetalMasterPriceDaoImpl implements ValouxMetalMasterPriceDao {
	
	@Autowired
	@Qualifier(value = "thirdDBSessionFactory")
	private SessionFactory thirdDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(ValouxMetalMasterPriceDaoImpl.class);

	public SessionFactory getThirdDBSessionFactory() {
		return thirdDBSessionFactory;
	}

	public void setThirdDBSessionFactory(SessionFactory thirdDBSessionFactory) {
		this.thirdDBSessionFactory = thirdDBSessionFactory;
	}

	public ValouxMetalsMasterPriceBean getItemComponentMetalSpecifyPrice(
			String metalName) throws Exception {
		LOGGER.debug("Enter Method getItemComponentMetalSpecifyPrice of ValouxMetalMasterPriceDaoImpl");
		ValouxMetalsMasterPriceBean resultBean = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxMetalsMasterPriceBean.class);
		criteria.add(Restrictions.eq("metalsType", metalName));
		criteria.addOrder(Order.desc("createdOn"));
		criteria.setMaxResults(1);
		resultBean = (ValouxMetalsMasterPriceBean) criteria.uniqueResult();
		LOGGER.debug("Exit Method getItemComponentMetalSpecifyPrice of ValouxMetalMasterPriceDaoImpl");
		return resultBean;
	}

	public void addValouxMetalsMasterPrice(
			ValouxMetalsMasterPriceBean masterPriceBean) throws Exception {
		LOGGER.debug("Enter Method createAgent of ValouxMetalMasterPriceDaoImpl");
		thirdDBSessionFactory.getCurrentSession().persist(masterPriceBean);
		LOGGER.debug("Exit Method createAgent of ValouxMetalMasterPriceDaoImpl");
	}

	public ValouxMetalsMasterPriceBean getMetalsMasterPriceByTypeAndDate(
			Date createdOn, String metalsType) throws Exception {
		LOGGER.debug("Enter Method getMetalsMasterPriceByTypeAndDate of ValouxMetalMasterPriceDaoImpl");
		ValouxMetalsMasterPriceBean resultBean = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxMetalsMasterPriceBean.class);
		criteria.add(Restrictions.eq("metalsType", metalsType));
		criteria.add(Restrictions.eq("createdOn", createdOn));
		resultBean = (ValouxMetalsMasterPriceBean) criteria.uniqueResult();
		LOGGER.debug("Exit Method getMetalsMasterPriceByTypeAndDate of ValouxMetalMasterPriceDaoImpl");
		return resultBean;
	}
	
}

	
