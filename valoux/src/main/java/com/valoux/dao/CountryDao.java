/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.CountryBean;

public interface CountryDao {

	/**
	 * This method performs get Country Name By CountryId.
	 * 
	 * @paparam countryId
	 *            : Business object carrying all the information relates to
	 *            country of user.
	 * @return Integer : Created countryId object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<CountryBean> getCountryId(String countryName) throws Exception;

	/**
	 * This method performs save Country.
	 * 
	 * @paparam countryBean
	 *            : Business object carrying all the information relates to
	 *            country of user.
	 * @return CountryBean : Created CountryBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public CountryBean saveCountry(CountryBean countryBean) throws Exception;
	/**
	 * This method performs get Country Name By CountryId.
	 * 
	 * @paparam countryId
	 *            : Business object carrying all the information relates to user
	 *            country.
	 * @return CountryBean : Created CountryBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public CountryBean getCountryNameByCountryId(Integer countryId) throws Exception;

	/**
	 * This method returns country details.
	 * @return List<CountryBean>
	 * @throws Exception
	 */
	public List<CountryBean> getAllCountryDetails();

}
