/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.ValouxOriginBean;

public interface ValouxOriginDao {

	/**
	 * This method performs get valoux origin Name By CountryId.
	 * 
	 * @paparam countryId
	 *            : Business object carrying all the information relates to user
	 *            country.
	 * @return CountryBean : Created CountryBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public ValouxOriginBean getCountryOriginNameByCountryId(Integer countryId) throws Exception;

	/**
	 * This method returns valoux origin details.
	 * @return List<ValouxOriginBean>
	 * @throws Exception
	 */
	public List<ValouxOriginBean> getAllCountryOriginDetails();

}
