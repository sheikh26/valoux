package com.valoux.daoImpl;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ItemComponentCertificateBean;
import com.valoux.dao.ItemComponentCertificateDao;


public class ItemComponentCertificateDaoImpl implements ItemComponentCertificateDao {

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

	private static final Logger LOGGER = Logger.getLogger(ItemComponentCertificateDaoImpl.class);


	/**
	 * This method add item component certificate
	 */
	public void addItemComponentCertificate(ItemComponentCertificateBean certificateBean) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method addItemComponentCertificate");

		Transaction trans = thirdDBSessionFactory.getCurrentSession().beginTransaction();

		if (certificateBean != null) {
			thirdDBSessionFactory.getCurrentSession().persist(certificateBean);
		}
		trans.commit();

		LOGGER.debug("ItemDaoImpl : Exit Method addItemComponentCertificate");

	}

	/**
	 * This method used to get item component certificate bean
	 */
	public ItemComponentCertificateBean getComponentCertificateBeanByItemAndComponentId(Integer itemId,
			Integer componentId) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter method getComponentCertificateBeanByItemAndComponentId");

		Criteria criteria = thirdDBSessionFactory.getCurrentSession()
				.createCriteria(ItemComponentCertificateBean.class);
		criteria.add(Restrictions.eq("valouxItem.itemId", itemId));
		criteria.add(Restrictions.eq("valouxItemComponent.vicid", componentId));
		ItemComponentCertificateBean componentBean = (ItemComponentCertificateBean) criteria.uniqueResult();

		LOGGER.debug("ItemDaoImpl : Exit method getComponentCertificateBeanByItemAndComponentId");
		return componentBean;
	}
}
