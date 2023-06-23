package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ValouxItemTypeBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.ValouxItemTypeDao;

public class ValouxItemTypeDaoImpl implements ValouxItemTypeDao {

	@Autowired
	@Qualifier(value = "thirdDBSessionFactory")
	private SessionFactory thirdDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(ValouxItemTypeDaoImpl.class);

	public SessionFactory getThirdDBSessionFactory() {
		return thirdDBSessionFactory;
	}

	public void setThirdDBSessionFactory(SessionFactory thirdDBSessionFactory) {
		this.thirdDBSessionFactory = thirdDBSessionFactory;
	}
	
	/**
	 * This method performs get Master Data For Item Type.
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxItemTypeBean> getMasterDataForItemType() throws Exception {
		LOGGER.debug("Enter method getMasterDataForItemType of ItemDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxItemTypeBean.class);
		criteria.add(Restrictions.eq("status", CommonConstants.STATUS));
		List<ValouxItemTypeBean> itemTypeBeanList = (List<ValouxItemTypeBean>) criteria.list();
		LOGGER.debug("Exit method getMasterDataForItemType of ItemDaoImpl");
		return itemTypeBeanList;
	}

}
