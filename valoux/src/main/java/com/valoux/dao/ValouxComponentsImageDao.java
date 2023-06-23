/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.ValouxComponentsImageBean;

public interface ValouxComponentsImageDao {
	
	
	/**
	 * Method for adding item component images
	 * 
	 * @paparam imageBeanList
	 * @throws Exception
	 */
	void saveValouxComponentsImageBean(List<ValouxComponentsImageBean> imageBeanList) throws Exception;


	/**
	 * Method for getting component images
	 * 
	 * @paparam itemId
	 * @paparam componentId
	 * @return
	 */
	List<ValouxComponentsImageBean> getComponentsImageBeanByComponentIdAndType(Integer componentId) throws Exception;
	
	/**
	 * This method get item component image
	 * 
	 * @paparam componentId
	 * @paparam imageId
	 * @return
	 * @throws Exception
	 */
	ValouxComponentsImageBean getItemComponentImageListByIdAndImageId(Integer componentId, Integer imageId)
			throws Exception;

	/**
	 * This method delete item component image
	 * 
	 * @paparam itemImageBean
	 * @throws Exception
	 */
	void deleteItemComponentImages(ValouxComponentsImageBean itemImageBean) throws Exception;


	/**
	 * @paparam itemComponentId
	 * @paparam imgName
	 * @paparam fileType
	 * @return
	 * @throws Exception
	 */
	ValouxComponentsImageBean getItemComponentImageByNameIdAndType(
			Integer itemComponentId, String imgName, Integer fileType) throws Exception;


	/**
	 * @paparam imageBean
	 * @throws Exception
	 */
	void saveValouxComponentsCertificateImage(
			ValouxComponentsImageBean imageBean) throws Exception;


	List<ValouxComponentsImageBean> getItemComponentImageByIdAndType(
			Integer itemComponentId, Integer fileType) throws Exception;


	/**
	 * @paparam componentId
	 * @paparam fileType
	 * @paparam imageName
	 * @return
	 * @throws Exception
	 */
	ValouxComponentsImageBean getItemComponentImageByIdNameAndType(
			Integer componentId, Integer fileType, String imageName) throws Exception;

}
