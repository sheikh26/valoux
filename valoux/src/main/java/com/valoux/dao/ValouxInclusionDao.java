/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.ValouxInclusionBean;

public interface ValouxInclusionDao {

	/**
	 * @paparam componentId
	 * @paparam inclusionType
	 * @return
	 * @throws Exception
	 */
	List<ValouxInclusionBean> getValouxInclusionBeanListByComponentAndType(
			Integer componentId, Integer inclusionType) throws Exception;

	/**
	 * @paparam inclusionBeans
	 * @throws Exception
	 */
	void deleteInclusionsTypeFromRequest(
			List<ValouxInclusionBean> inclusionBeans) throws Exception;

	/**
	 * @paparam valouxInclusionBeans
	 * @throws Exception
	 */
	void saveInclusionsTypeFromRequest(
			List<ValouxInclusionBean> valouxInclusionBeans) throws Exception;

	/**
	 * @paparam componentId
	 * @return
	 * @throws Exception
	 */
	List<ValouxInclusionBean> getValouxInclusionBeanListByComponentAndType(
			Integer componentId) throws Exception;
	

}
