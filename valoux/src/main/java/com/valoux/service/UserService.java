/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.service;

import java.util.List;
import java.util.Map;

import com.valoux.bean.AgentBean;
import com.valoux.bean.LoginBean;
import com.valoux.bean.LoginLogBean;
import com.valoux.bean.MasterRoleBean;
import com.valoux.bean.UserBean;
import com.valoux.bean.UserRoleBean;
import com.valoux.bean.UserTypeBean;
import com.valoux.bean.ValouxStoreBean;
import com.valoux.model.CountryModel;
import com.valoux.model.LoginModel;
import com.valoux.model.UserModel;
import com.valoux.model.UserRoleModel;
import com.valoux.model.ValouxInterestInModel;
import com.valoux.model.ValouxPersonalPreferencesModel;

/**
 * This <java>interface</java> UserService defines all abstract methods of user
 * operations.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

public interface UserService {

	/**
	 * This method authenticates login credentials.
	 * 
	 * @paparam loginBean
	 *            : Business object carrying all the information relates to
	 *            login.
	 * @return LoginBean : resulting loginBean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public LoginBean checkLoginRequest(LoginBean loginBean) throws Exception;

	/**
	 * This method checks if email is already registered.
	 * 
	 * @paparam email
	 *            : Business object carrying all the information relates to
	 *            login authentication.
	 * @return boolean : true/false depending on result
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public boolean checkEmailAlreadyRegistered(String email) throws Exception;

	/**
	 * This method creates a new User.
	 * 
	 * @paparam userBean
	 *            : Business object carrying all the information related to user
	 *            to be created.
	 * @return UserBean : Created UserBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public UserBean createUser(UserModel userModel) throws Exception;

	/**
	 * This method saves Login information.
	 * 
	 * @paparam loginModel
	 *            : Business object carrying all the information relates to
	 *            login user.
	 * @return LoginBean : Created LoginBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public LoginBean saveLoginInfo(LoginModel loginModel) throws Exception;

	/**
	 * This method retrieves state identifier for country state name combination
	 * 
	 * @paparam stateName
	 *            : name of state.
	 * @return Integer : country identifier
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public Integer getStateIdentifier(String stateName, Integer countryId) throws Exception;

	/**
	 * This method retrieves country identifier using country name.
	 * 
	 * @paparam countryId
	 *            : Business object carrying all the information relates to
	 *            country of user.
	 * @return Integer : Created countryId object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public Integer getCountryId(String countryName) throws Exception;

	/**
	 * This method checks if relation key already exist.
	 * 
	 * @paparam relationKey
	 *            : Business object carrying all the information relates to
	 *            user.
	 * @return UserBean : Created UserBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public UserBean checkRelationKeyAlreadyExist(String relationKey) throws Exception;

	/**
	 * This method retrieves user data based for the supplied user name.
	 * 
	 * @paparam userName
	 *            : user name for which user information to be retrieved.
	 * @return UserBean : retrieved user data.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public UserBean getUserDetails(String userName) throws Exception;

	/**
	 * This method saves User Type information.
	 * 
	 * @paparam userTypeBean
	 *            : Business object carrying all the information relates to user
	 *            type.
	 * @return UserTypeBean : Created UserTypeBean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public UserTypeBean createUserType(UserTypeBean userTypeBean) throws Exception;

	/**
	 * This method creates User Role.
	 * 
	 * @paparam userRoleBean
	 *            : Business object carrying all the information relates to user
	 *            role .
	 * @return UserRoleBean : Created UserRole object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public UserRoleBean createUserRole(UserRoleBean userRoleBean) throws Exception;

	/**
	 * This method updates User information.
	 * 
	 * @paparam userModel
	 *            : Business object holding user information to update.
	 * @return UserBean : Created UserBean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public UserBean updateUserDetail(UserModel userModel) throws Exception;

	/**
	 * This method retrieves list of all the interest.
	 * 
	 * @return List<ValouxInterestInBean> : list of all the interest
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<ValouxInterestInModel> getUserInterestIn() throws Exception;

	/**
	 * This method retrieves list of all Personal Preferences.
	 * 
	 * @return List<ValouxPersonalPreferencesBean> : list of all the personal
	 *         preferences
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<ValouxPersonalPreferencesModel> getAllPersonalPreferences() throws Exception;

	/**
	 * This method checks otp and activates user
	 * 
	 * @paparam activationKey
	 *            : activation key used to identify user
	 * @paparam otp
	 *            : one time password to be checked for user
	 * @return boolean : result
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public boolean checkAndActivatUser(String activationKey, String otp) throws Exception;

	/**
	 * This method retrieves Consumer Detail using relation key.
	 * 
	 * @paparam rKey
	 *            : relation key for which consumer details to be retrieved
	 * @return UserModel : retrieved consumer data
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public UserModel getConsumerDetailByRKey(String rKey) throws Exception;

	/**
	 * This method retrieves Login Info using relation key.
	 * 
	 * @paparam pKey
	 *            : relation key for which login details to be retrieved
	 * @return LoginModel : retrieved login data
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public LoginModel getLoginDetailByPKey(String pKey) throws Exception;

	/**
	 * This method stores user information for an agent
	 * 
	 * @paparam storeBean
	 *            persistable object holding store information for agent
	 * @paparam agentbean
	 *            persistable object holding agent information
	 * @paparam masterRoleBean
	 *            : business object holding role information for agent
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void createUsertypeAnduserRoleForAgent(ValouxStoreBean storeBean, AgentBean agentbean,
			MasterRoleBean masterRoleBean) throws Exception;

	/**
	 * This method stores user information for a consumer
	 * 
	 * @paparam userBean
	 *            persistable user information of consumer
	 * @paparam loginBean
	 *            : persistable information related to login
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void createUserTypeAndUserRoleForConsumer(UserBean userBean, LoginBean loginBean,
			MasterRoleBean masterRoleBean) throws Exception;

	/**
	 * This method updates consumer information
	 * 
	 * @paparam userModel
	 *            business object holding user data to be updated for consumer
	 * @paparam loginModel
	 *            : business object holding to login information of consumer
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void updateConsumerDetail(UserModel userModel, LoginModel loginModel) throws Exception;

	/**
	 * This method converts business object User Model Into persisting user Bean
	 * For Consumer
	 * 
	 * @paparam userBean
	 * @paparam userModel
	 *            : Business object carrying all the information relates to user
	 *            object.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void convertUserModelIntoBeanForConsumer(UserBean userBean, UserModel userModel) throws Exception;

	/**
	 * This method converts Login Model In to login Bean
	 * 
	 * @paparam loginBean
	 * @paparam loginModel
	 *            : Business object carrying all the information relates to
	 *            login object.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void convertLoginModelIntoBeanForConsumer(LoginBean loginBean, LoginModel loginModel) throws Exception;

	/**
	 * This method retrieves Role information using relation key.
	 * 
	 * @paparam relationKey
	 *            : relation key for which role information to be retrieved
	 * @return UserRoleBean retrieved UserRoleBean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public UserRoleBean getRole(String relationKey) throws Exception;

	/**
	 * This method retrieves User Type information using relation key.
	 * 
	 * @paparam relationKey
	 *            : relation key for which role information to be retrieved
	 * @return UserTypeBean : retrieved UserTypeBean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public UserTypeBean getUserType(String relationKey) throws Exception;

	/**
	 * This method stores Login Trail information
	 * 
	 * @paparam loginLogBean
	 *            : Business object carrying information relates to login
	 * @return LoginLogBean : login trail data
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public LoginLogBean createLoginLogs(LoginLogBean loginLogBean) throws Exception;

	/**
	 * This method resends OTP.
	 * 
	 * @paparam authLoginCode
	 *            : login auth code for which otp to be sent
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public LoginBean resendOTP(String authLoginCode) throws Exception;

	/**
	 * This method update Agent Login information.
	 * 
	 * @paparam loginModel
	 *            : Business object with information relates to agent login.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public LoginBean updateAgentLogindetail(LoginModel loginModel) throws Exception;

	/**
	 * This method changes password
	 * 
	 * @paparam loginModel
	 *            : login business object for which password to reset
	 * @paparam newPassword
	 *            : new password to reset
	 * @return boolean : result of operation
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public boolean changePassword(LoginModel loginModel, String newPassword) throws Exception;

	/**
	 * This method get security question for an user
	 * 
	 * @paparam emailId
	 *            : emailId representing user
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public String getSecurityQuestionByEmailid(String emailId) throws Exception;

	/**
	 * This method verifies security answer and then send mail to the user/agent
	 * to reset password
	 * 
	 * @paparam emailId
	 *            : user name for which opeation to be performed
	 * @paparam securityQuestion
	 *            : security question input from user
	 * @paparam securityAnswer
	 *            : security question's answer
	 * @return : true/false : result of operation
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public Boolean verifySecurityAnswer(String emailId, String securityQuestion, String securityAnswer)
			throws Exception;

	/**
	 * This method update the new password if forgetpassword key matches and
	 * update forgetpassword key
	 * 
	 * @paparam forgetPasswordKey
	 *            key for forgetpassword
	 * @paparam newPassword
	 *            : new password to set
	 * @return : true/false : result of operation
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public Boolean updatePasswordAndForgetPasswordKey(String forgetPasswordKey, String newPassword) throws Exception;

	/**
	 * This method gets the map of userInterestIn
	 * 
	 * @return : Map<Integer, String>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public Map<Integer, String> getUserInterestInMap() throws Exception;

	/**
	 * This method performs get All Personal Preferences map.
	 * 
	 * @return Map<Integer, String>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public Map<Integer, String> getAllPersonalPreferencesMap() throws Exception;

	/**
	 * This method retrieves All Consumer Detail.
	 * 
	 * @return List<UserModel> : retrieved consumer data
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<UserModel> getAllConsumerDetail() throws Exception;

	/**
	 * This method change profile image for consumer
	 * 
	 * @paparam userPublicKey
	 * @paparam imageName
	 * @paparam imageContent
	 * @return : UserBean
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public UserBean changeProfieImage(String publicKey, String imageName, String imageContent) throws Exception;

	/**
	 * This method retrieves Login Info using user name.
	 * 
	 * @paparam pKey
	 *            : relation key for which login details to be retrieved
	 * @return LoginModel : retrieved login data
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public LoginModel getLoginDetailByUserName(String emailId) throws Exception;

	/**
	 * This method returns country details.
	 * @return List<CountryBean>
	 * @throws Exception
	 */
	public List<CountryModel> getAllCountryDetails() throws Exception;

	/**
	 * This method retrieves Login Info by auth code.
	 * @paparam authLoginCode
	 * @return : LoginBean
	 * @throws Exception : error during the execution of operation
	 */
	public LoginModel getUserLoginDetailsByAuthCode(String authLoginCode) throws Exception;

	/**
	 * @paparam userBean
	 * @paparam loginBean
	 * @throws Exception
	 */
	public void sendRegistrationMailToUser(UserBean userBean,
			LoginBean loginBean) throws Exception;

	/**
	 * @paparam loginModel
	 * @return
	 * @throws Exception
	 */
	public LoginBean updateUserLoginDetails(LoginModel loginModel) throws Exception;
	
	/**
	 * This method get all login data having keyword
	 * @paparam keyword
	 * @return
	 * @throws Exception
	 */
	List<LoginModel> getLoginDetailListWithKeyWord(String keyword) throws Exception;
	
	/**
	 * This method delete consumer
	 * @paparam userPublicKey
	 * @return
	 * @throws Exception
	 */
	Boolean deleteConsumer(String userPublicKey) throws Exception;

	/**
	 * @paparam relationKey
	 * @return
	 * @throws Exception
	 */
	public UserRoleModel getRoleModel(String relationKey) throws Exception;


}
