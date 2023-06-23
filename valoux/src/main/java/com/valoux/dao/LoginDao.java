/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.LoginBean;

public interface LoginDao {
	/**
	 * This method creates User For Login.
	 * 
	 * @paparam loginModel
	 *            : Business object carrying information relates to agent login
	 *            data.
	 * @return LoginBean : Created Login object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	LoginBean createLoginUser(LoginBean loginBean) throws Exception;
	/**
	 * This method performs check User Credentials.
	 * 
	 * @paparam loginBean
	 *            : Business object carrying all the information relates to
	 *            login.
	 * @return LoginBean : Created loginBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public LoginBean checkUserCredentials(LoginBean loginBean) throws Exception;

	/**
	 * This method performs if User is already Exist or not.
	 * 
	 * @paparam email
	 *            : Business object carrying all the information relates to
	 *            login authentication.
	 * @return boolean : true/false
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public boolean checkEmailAlreadyRegistered(String email) throws Exception;

	/**
	 * This method creates User For Login.
	 * 
	 * @paparam loginModel
	 *            : Business object carrying all the information relates to
	 *            login user.
	 * @return LoginBean : Created LoginBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public LoginBean createUserInLogin(LoginBean loginBean) throws Exception;
	/**
	 * This method performs check And Activate User.
	 * 
	 * @paparam activationKey
	 * @paparam otp
	 *            : Business object carrying all the information relates to user
	 *            activation.
	 * @return boolean : true/false
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public LoginBean checkUserForActivation(String activationKey, String otp) throws Exception;
	/**
	 * This method performs get Login Detail By PKey.
	 * 
	 * @paparam pKey
	 *            : Business object carrying all the information relates to
	 *            login object.
	 * @return LoginModel : Created LoginModel object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public LoginBean getLoginDetailByPKey(String pKey) throws Exception;

	/**
	 * This method performs update Login Detail.
	 * 
	 * @paparam loginBean
	 *            : Business object carrying all the information relates to
	 *            login object.
	 * @return LoginBean : Created LoginBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public LoginBean updateLoginDetail(LoginBean loginBean) throws Exception;

	/**
	 * This method performs resend OTP.
	 * 
	 * @paparam authLoginCode
	 *            : Business object carrying all the information relates to
	 *            login OTP.
	 * @return LoginBean : Created LoginBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public LoginBean resendOTP(String authLoginCode) throws Exception;;



	/**
	 * This method gets login detail by email id
	 * 
	 * @paparam email
	 *            : Business object carrying all the information relates to
	 *            login.
	 * @return LoginBean : Created LoginBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public LoginBean getLoginBeanByEmailid(String email) throws Exception;

	/**
	 * This method gets login detail by forgetPasswordKey
	 * 
	 * @paparam email
	 *            : Business object carrying all the information relates to
	 *            login.
	 * @return LoginBean : Created LoginBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public LoginBean getLoginBeanByForgetPasswordKey(String forgetPasswordKey) throws Exception;
	
	/**
	 * This method update the login detail
	 * 
	 * @paparam loginBean
	 * @return : Created LoginBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	LoginBean updateLoginData(LoginBean loginBean) throws Exception;
	
	/**
	 * This method retrieves Login Info by auth code.
	 * @paparam authLoginCode
	 * @return : LoginBean
	 * @throws Exception : error during the execution of operation
	 */
	public LoginBean getUserLoginDetailsByAuthCode(String authLoginCode) throws Exception;
	
	/**
	 * This metod get all login data with keyword
	 * @paparam keyword
	 * @return
	 * @throws Exception
	 */
	List<LoginBean> getAllLoginDataWithKeyword(String keyword) throws Exception;
	
	/**
	 * This method delete login object
	 * @paparam objectBean
	 * @throws Exception
	 */
	void deleteAnyBeanByObject(Object objectBean)
			throws Exception;
	
	/**
	 * This method update login bean
	 * @paparam loginBean
	 * @throws Exception
	 */
	void updateLoginBean(LoginBean loginBean) throws Exception;
}
