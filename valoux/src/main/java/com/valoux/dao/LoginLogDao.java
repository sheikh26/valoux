/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import com.valoux.bean.LoginLogBean;

public interface LoginLogDao {
	/**
	 * This method performs verify User OTP.
	 * 
	 * @paparam authUserID
	 * @paparam authLoginCode
	 *            : Business object carrying all the information relates to user
	 *            verification.
	 * @return boolean : return true/false
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public boolean verifyUserOTP(Integer authUserID, String authLoginCode) throws Exception;


	/**
	 * This method creates Login Logs.
	 * 
	 * @paparam loginLogBean
	 *            : Business object carrying all the information relates to
	 *            login object.
	 * @return LoginLogBean : Created LoginLogBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public LoginLogBean createLoginLogs(LoginLogBean loginLogBean) throws Exception;
}
