/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.UserBean;

/**
 * This <java>interface</java> UserDao interface has all the abstract methods
 * related to user.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

public interface UserDao {

	
	/**
	 * This method creates User.
	 * 
	 * @paparam userBean
	 *            : Business object carrying all the information relates to
	 *            create user.
	 * @return UserBean : Created UserBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public UserBean createUser(UserBean userBean) throws Exception;

	/**
	 * This method performs check New Genarated RelationKey.
	 * 
	 * @paparam relationKey
	 *            : Business object carrying all the information relates to
	 *            user.
	 * @return UserBean : Created UserBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public UserBean checkNewGenaratedRelationKey(String relationKey) throws Exception;

	/**
	 * This method performs get Relational Key.
	 * 
	 * @paparam loginBean
	 *            : Business object carrying all the information relates to
	 *            user.
	 * @return UserBean : Created UserBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public UserBean getUserDetails(String userName) throws Exception;

	/**
	 * This method performs get User Detail By User Id.
	 * 
	 * @paparam userId
	 *            : Business object carrying all the information relates to
	 *            user.
	 * @return UserBean : Created UserBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public UserBean getUserDetailByUserId(Integer userId) throws Exception;

	/**
	 * This method performs update User.
	 * 
	 * @paparam userModel
	 *            : Business object carrying all the information relates to user
	 *            type.
	 * @return UserBean : Created UserBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public UserBean updateUser(UserBean userBean) throws Exception;


	/**
	 * This method performs get Consumer Detail By RKey.
	 * 
	 * @paparam rKey
	 *            : Business object carrying all the information relates to user
	 *            object.
	 * @return UserModel : Created UserModel object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public UserBean getConsumerDetailByRKey(String rKey) throws Exception;

	/**
	 * This method retrieves all consumer detail
	 * 
	 * @return : List<UserBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<UserBean> getAllConsumerDetail() throws Exception;
	
	/**
	 * This method delete user object
	 * @paparam objectBean
	 * @throws Exception
	 */
	void deleteAnyBeanByObject(Object objectBean)
			throws Exception;

	
}
