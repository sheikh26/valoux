package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ValouxComponentsImageBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.ValouxComponentsImageDao;

public class ValouxComponentsImageDaoImpl implements  ValouxComponentsImageDao {

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

	private static final Logger LOGGER = Logger.getLogger(ValouxComponentsImageDaoImpl.class);
	
	/**
	 * This method save the ValouxComponentsImageBean
	 */
	public void saveValouxComponentsImageBean(List<ValouxComponentsImageBean> imageBeanList) throws Exception {

		LOGGER.debug("ItemDaoImpl : Enter Method saveValouxComponentsImageBean");

		for (ValouxComponentsImageBean valouxCollectionImageBean : imageBeanList) {
			
			Integer fileType = Integer.valueOf(valouxCollectionImageBean.getFileType());
			
			ValouxComponentsImageBean imageBean = null;
			
			if(fileType.equals(CommonConstants.COMPONENT_RECEIPT) || fileType.equals(CommonConstants.COMPONENT_CERTIFICATE)){
				imageBean = getValouxComponentsImageBeanByComponentIdAndFileType(valouxCollectionImageBean.getValouxItemComponent().getVicid(), Integer.valueOf(valouxCollectionImageBean.getFileType()));
				if (imageBean != null) {
					thirdDBSessionFactory.getCurrentSession().delete(imageBean);
				} 
			}
			if(valouxCollectionImageBean != null) {
				thirdDBSessionFactory.getCurrentSession().save(valouxCollectionImageBean);
			}
			
//			if (imageBean != null) {
//				thirdDBSessionFactory.getCurrentSession().update(valouxCollectionImageBean);
//			} else {
//				thirdDBSessionFactory.getCurrentSession().save(valouxCollectionImageBean);
//			}
		}

		LOGGER.debug("ItemDaoImpl : Exit Method saveValouxComponentsImageBean");
	}
	
	/**
	 * This method used to get valoux component property images 
	 */
	public ValouxComponentsImageBean getValouxComponentsImageBeanByComponentIdAndFileType(
			Integer componentId, Integer fileType) {
		LOGGER.debug("Enter Method getItemComponentImageListByIdAndImageId of ItemDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxComponentsImageBean.class);
		criteria.add(Restrictions.eq("valouxItemComponent.vicid", componentId));
		criteria.add(Restrictions.eq("fileType", fileType.byteValue()));
		ValouxComponentsImageBean itemImageBean = (ValouxComponentsImageBean) criteria.uniqueResult();
		LOGGER.debug("Enter Method getItemComponentImageListByIdAndImageId of ItemDaoImpl");
		return itemImageBean;
	}

	/**
	 * This method used to get valoux component property images list
	 */
	public List<ValouxComponentsImageBean> getComponentsImageBeanByComponentIdAndType(Integer componentId)
			throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getComponentsImageBeanByComponentIdAndType");

		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxComponentsImageBean.class);
		criteria.add(Restrictions.eq("valouxItemComponent.vicid", componentId));
		List<ValouxComponentsImageBean> beanList = (List<ValouxComponentsImageBean>) criteria.list();

		LOGGER.debug("ItemDaoImpl : Enter Method getComponentsImageBeanByComponentIdAndType");
		return beanList;
	}
	
	/**
	 * This method get item component image
	 */
	public ValouxComponentsImageBean getItemComponentImageListByIdAndImageId(Integer componentId, Integer imageId)
			throws Exception {

		LOGGER.debug("Enter Method getItemComponentImageListByIdAndImageId of ItemDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxComponentsImageBean.class);
		criteria.add(Restrictions.eq("valouxItemComponent.vicid", componentId));
		criteria.add(Restrictions.eq("cid", imageId));
		ValouxComponentsImageBean itemImageBean = (ValouxComponentsImageBean) criteria.uniqueResult();
		LOGGER.debug("Enter Method getItemComponentImageListByIdAndImageId of ItemDaoImpl");
		return itemImageBean;
	}

	/**
	 * This method delete item component image
	 */
	public void deleteItemComponentImages(ValouxComponentsImageBean itemImageBean) throws Exception {
		LOGGER.debug("Enter Method deleteItemComponentImages of ItemDaoImpl");
		thirdDBSessionFactory.getCurrentSession().delete(itemImageBean);
		LOGGER.debug("Enter Method deleteItemComponentImages of ItemDaoImpl");
	}

	public ValouxComponentsImageBean getItemComponentImageByNameIdAndType(
			Integer componentId, String imgName, Integer fileType)
			throws Exception {
		LOGGER.debug("Enter Method getItemComponentImageListByIdAndImageId of ItemDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxComponentsImageBean.class);
		criteria.add(Restrictions.eq("valouxItemComponent.vicid", componentId));
		criteria.add(Restrictions.eq("imgName", imgName));
		criteria.add(Restrictions.eq("fileType", fileType.byteValue()));
		ValouxComponentsImageBean itemImageBean = (ValouxComponentsImageBean) criteria.uniqueResult();
		LOGGER.debug("Enter Method getItemComponentImageListByIdAndImageId of ItemDaoImpl");
		return itemImageBean;
	}

	public void saveValouxComponentsCertificateImage(
			ValouxComponentsImageBean imageBean) throws Exception {
		LOGGER.debug("Enter Method saveValouxComponentsCertificateImage of ItemDaoImpl");
		if(imageBean != null) {
			thirdDBSessionFactory.getCurrentSession().persist(imageBean);
		}
		LOGGER.debug("Enter Method saveValouxComponentsCertificateImage of ItemDaoImpl");
	}

	@SuppressWarnings("unchecked")
	public List<ValouxComponentsImageBean> getItemComponentImageByIdAndType(
			Integer itemComponentId, Integer fileType) throws Exception {
		LOGGER.debug("Enter Method getItemComponentImageByIdAndType of ItemDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxComponentsImageBean.class);
		criteria.add(Restrictions.eq("valouxItemComponent.vicid", itemComponentId));
		criteria.add(Restrictions.eq("fileType", fileType.byteValue()));
		List<ValouxComponentsImageBean> beanList = (List<ValouxComponentsImageBean>) criteria.list();
		LOGGER.debug("Enter Method getItemComponentImageByIdAndType of ItemDaoImpl");
		return beanList;
	}

	public ValouxComponentsImageBean getItemComponentImageByIdNameAndType(
			Integer componentId, Integer fileType, String imageName)
			throws Exception {
		LOGGER.debug("Enter Method getItemComponentImageByIdNameAndType of ItemDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxComponentsImageBean.class);
		criteria.add(Restrictions.eq("valouxItemComponent.vicid", componentId));
		criteria.add(Restrictions.eq("fileType", fileType.byteValue()));
		criteria.add(Restrictions.eq("imgName", imageName.trim()));
		ValouxComponentsImageBean imageBean = (ValouxComponentsImageBean) criteria.uniqueResult();
		LOGGER.debug("Enter Method getItemComponentImageByIdNameAndType of ItemDaoImpl");
		return imageBean;
	}

}
