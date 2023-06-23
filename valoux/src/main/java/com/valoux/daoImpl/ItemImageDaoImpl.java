package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ItemImageBean;
import com.valoux.dao.ItemImageDao;

public class ItemImageDaoImpl implements ItemImageDao {

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

	private static final Logger LOGGER = Logger.getLogger(ItemImageDaoImpl.class);
	
	/**
	 * This method add item image
	 */
	public ItemImageBean addItemImage(ItemImageBean itemImageBean) throws Exception {
		LOGGER.debug("Enter Method addItemImage of ItemDaoImpl");
		Transaction trans = thirdDBSessionFactory.getCurrentSession().beginTransaction();
		thirdDBSessionFactory.getCurrentSession().save(itemImageBean);
		trans.commit();
		LOGGER.debug("Exit Method addItemImage of ItemDaoImpl");
		return itemImageBean;
	}

	/**
	 * This method get the list of item image
	 */
	public List<ItemImageBean> getItemImageListByItemId(Integer itemId) throws Exception {
		LOGGER.debug("Enter Method getItemImageListByItemId of ItemDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ItemImageBean.class);
		criteria.add(Restrictions.eq("valouxItemBean.itemId", itemId));
		@SuppressWarnings("unchecked")
		List<ItemImageBean> itemImageBeanList = (List<ItemImageBean>) criteria.list();
		LOGGER.debug("Enter Method getItemImageListByItemId of ItemDaoImpl");
		return itemImageBeanList;
	}

	/**
	 * This method get the of item image bean
	 */
	public ItemImageBean getItemImageListByItemIdAndImageId(Integer itemId, Integer imageId) throws Exception {
		LOGGER.debug("Enter Method getItemImageListByItemId of ItemDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ItemImageBean.class);
		criteria.add(Restrictions.eq("valouxItemBean.itemId", itemId));
		criteria.add(Restrictions.eq("imageId", imageId));
		ItemImageBean itemImageBean = (ItemImageBean) criteria.uniqueResult();
		LOGGER.debug("Enter Method getItemImageListByItemId of ItemDaoImpl");
		return itemImageBean;
	}

	/**
	 * This method gets the maximum index of the table
	 */
	public Integer getMaximumIndexOfItemImageTable() throws Exception {
		LOGGER.debug("Enter Method getMaximumIndexOfItemImageTable of ItemDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ItemImageBean.class)
				.setProjection(Projections.max("imageId"));
		Integer maxValue = (Integer) criteria.uniqueResult();
		LOGGER.debug("Enter Method getMaximumIndexOfItemImageTable of ItemDaoImpl");
		return maxValue;
	}
	
	/**
	 * This method deleteO item images
	 */
	public void deleteItemImages(ItemImageBean itemImageBean) throws Exception {
		LOGGER.debug("Enter Method deleteItemImages of ItemDaoImpl");
		thirdDBSessionFactory.getCurrentSession().delete(itemImageBean);
		LOGGER.debug("Enter Method deleteItemImages of ItemDaoImpl");
	}

}
