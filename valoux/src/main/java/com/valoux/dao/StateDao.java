/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.StateBean;

public interface StateDao {

	/**
	 * This method performs save State.
	 * 
	 * @paparam stateBean
	 *            : Business object carrying all the information relates to
	 *            state of user.
	 * @return StateBean : Created StateBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public StateBean saveState(StateBean stateBean) throws Exception;

	/**
	 * This method performs get State Id.
	 * 
	 * @paparam stateName
	 *            : Business object carrying all the information relates to
	 *            state and country of user.
	 * @return Integer : Created stateId object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<StateBean> getStateId(String stateName) throws Exception;
	/**
	 * This method performs get State Name By StateId.
	 * 
	 * @paparam stateId
	 *            : Business object carrying all the information relates to user
	 *            state.
	 * @return StateBean : Created StateBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public StateBean getStateNameByStateId(Integer stateId) throws Exception;

}
