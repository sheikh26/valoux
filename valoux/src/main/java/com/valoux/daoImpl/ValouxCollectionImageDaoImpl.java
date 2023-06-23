package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ValouxCollectionImageBean;
import com.valoux.dao.ValouxCollectionImageDao;

public class ValouxCollectionImageDaoImpl implements ValouxCollectionImageDao {

	@Autowired
	@Qualifier(value = "thirdDBSessionFactory")
	private SessionFactory thirdDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(ValouxCollectionImageDaoImpl.class);

	public SessionFactory getThirdDBSessionFactory() {
		return thirdDBSessionFactory;
	}

	public void setThirdDBSessionFactory(SessionFactory thirdDBSessionFactory) {
		this.thirdDBSessionFactory = thirdDBSessionFactory;
	}

	public static Logger getLogger() {
		return LOGGER;
	}
	
	/**
	 * This method delete the ValouxCollectionImageBean
	 */
	public void deleteValouxCollectionImageBeanByCollectionId(List<ValouxCollectionImageBean> imageBeanList)
			throws Exception {

		LOGGER.debug("CollectionDaoImpl : Enter Method deleteValouxCollectionImageBeanByCollectionId");

		for (ValouxCollectionImageBean valouxCollectionImageBean : imageBeanList) {
			thirdDBSessionFactory.getCurrentSession().delete(valouxCollectionImageBean);
		}

		LOGGER.debug("CollectionDaoImpl : Exit Method deleteValouxCollectionImageBeanByCollectionId");

	}

	/**
	 * This method get the ValouxCollectionImageBean by collection id
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxCollectionImageBean> getValouxCollectionImageBeanByCollectionId(Integer collectionId)
			throws Exception {

		LOGGER.debug("CollectionDaoImpl : Enter method getValouxCollectionImageBeanByCollectionId");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxCollectionImageBean.class);
		criteria.add(Restrictions.eq("valouxCollectionBean.vcid", collectionId));

		List<ValouxCollectionImageBean> collectionImageBeans = (List<ValouxCollectionImageBean>) criteria.list();

		LOGGER.debug("CollectionDaoImpl : Exit method getValouxCollectionImageBeanByCollectionId");

		return collectionImageBeans;
	}

	/**
	 * This method save the ValouxCollectionImageBean
	 */
	public List<ValouxCollectionImageBean> saveValouxCollectionImageBeanOfCollection(
			List<ValouxCollectionImageBean> collectionImageBeanList) throws Exception {

		LOGGER.debug("CollectionDaoImpl : Enter Method saveValouxCollectionImageBeanOfCollection");

		for (ValouxCollectionImageBean valouxCollectionImageBean : collectionImageBeanList) {
			thirdDBSessionFactory.getCurrentSession().save(valouxCollectionImageBean);
		}

		LOGGER.debug("CollectionDaoImpl : Exit Method saveValouxCollectionImageBeanOfCollection");

		return collectionImageBeanList;
	}

	/**
	 * This method get the ValouxCollectionImageBean by collection id and image
	 */
	public ValouxCollectionImageBean getCollectionImageByCollectionAndImageId(Integer collectionId, Integer imageId) {
		LOGGER.debug("CollectionDaoImpl : Enter Method getCollectionImageByCollectionAndImageId");

		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxCollectionImageBean.class);
		criteria.add(Restrictions.eq("valouxCollectionBean.vcid", collectionId));
		criteria.add(Restrictions.eq("id", imageId));
		ValouxCollectionImageBean itemImageBean = (ValouxCollectionImageBean) criteria.uniqueResult();

		LOGGER.debug("CollectionDaoImpl : Exit Method getCollectionImageByCollectionAndImageId");
		return itemImageBean;
	}
	
	/**
	 * This method delete the ValouxCollectionImageBean
	 */
	public void deleteValouxCollectionImageBeanByCollectionAndImageId(ValouxCollectionImageBean collectionImageBean)
			throws Exception {
		LOGGER.debug("CollectionDaoImpl : Enter Method deleteValouxCollectionImageBeanByCollectionId");

		thirdDBSessionFactory.getCurrentSession().delete(collectionImageBean);

		LOGGER.debug("CollectionDaoImpl : Exit Method deleteValouxCollectionImageBeanByCollectionId");

	}

}
