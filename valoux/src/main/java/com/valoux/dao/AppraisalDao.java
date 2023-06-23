/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;

import com.valoux.bean.AppraisalBean;
import com.valoux.bean.AppraisalCollectionBean;
import com.valoux.bean.AppraisalItemsBean;
import com.valoux.bean.ValouxAccessPermissionBean;
import com.valoux.bean.ValouxAppraisalItemsComponentPriceBean;
import com.valoux.bean.ValouxAppraisalItemsPriceBean;
import com.valoux.bean.ValouxCollectionBean;
import com.valoux.bean.ValouxCollectionItemBean;
import com.valoux.bean.ValouxItemBean;

/**
 * This <java>interface</java> AppraisalDao interface has all the abstract
 * methods related to appraisal.
 * 
 * @author param Sheikh
 * 
 */

public interface AppraisalDao {

	/**
	 * This method creates Appraisal or update Appraisal.
	 * 
	 * @paparam appraisalBean
	 *            : Business object carrying all the information relates to
	 *            create or update appraisal.
	 * @return AppraisalBean : Created AppraisalBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public AppraisalBean createOrUpdateAppraisal(AppraisalBean appraisalBean) throws Exception;

	


	/**
	 * This method performs if Appraisal is already Exist or not.
	 * 
	 * @paparam rKey
	 *            : Business object carrying all the information relates to
	 *            Appraisal.
	 * @paparam name
	 * @return boolean : true/false
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public boolean checkAppraisalAlreadyExist(String rKey, String name) throws Exception;

	/**
	 * This method getall the list of appraisal
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<AppraisalBean> getAllAppraisalListForSupoerAdmin() throws Exception;

	/**
	 * This method performs if Appraisal is already Exist or not.
	 * 
	 * @paparam appraisalId
	 *            ,status : Business object carrying all the information relates
	 *            to Appraisal already Approved.
	 * @return boolean : true/false
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public boolean checkAppraisalAlreadyApproved(Integer appraisalId, Integer status) throws Exception;

	/**
	 * This method approved or disapproved Appraisal.
	 * 
	 * @paparam appraisalBean
	 *            : Business object carrying all the information relates to
	 *            approved or disapproved appraisal.
	 * @return AppraisalBean : Created AppraisalBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public AppraisalBean approvedOrDisapprovedAppraisal(AppraisalBean appraisalBean) throws Exception;

	/**
	 * This method get Appraisal Details.
	 * 
	 * @paparam appraisalId
	 *            : Business object carrying all the information relates to
	 *            appraisal.
	 * @return AppraisalBean : Created AppraisalBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */

	public AppraisalBean getAppraisalDetails(Integer appraisalId) throws Exception;

	/**
	 * Method used to get all user Appraisal
	 * 
	 * @paparam relationKey
	 *            : Business object carrying user data.
	 * @return List<AppraisalBean> : Collection object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<AppraisalBean> getAppraisalDetailsByUserId(String relationKey,Integer aStatus,Object[] objects) throws Exception;

	
	/**
	 * This method check the appraisal name exist for user
	 * 
	 * @paparam publicKey
	 * @paparam appraisalName
	 * @return : List<AppraisalBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<AppraisalBean> checkAppraisalNameExistForUser(String publicKey, String appraisalName) throws Exception;

	/**
	 * This method get the list of all appraisal which is not in appraisal array
	 * 
	 * @paparam appraisalArray
	 * @return
	 * @throws Exception
	 */
	List<AppraisalBean> getAppraisalListNotAssociatedWithItem(String rKey, Object[] appraisalArray) throws Exception;

	

	
	/**
	 * This method get all appraisal of user with keyword
	 * @paparam publicKey
	 * @paparam keyword
	 * @paparam objects
	 * @return
	 * @throws Exception
	 */
	List<AppraisalBean> getAppraisalListForGlobalSearchByName(String publicKey,String keyword,Object[] objects,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;


	/**
	 * This method get list collection appraisals not in collection
	 * 
	 * @paparam publicKey
	 * @paparam array
	 * @return
	 * @throws Exception
	 */
	List<AppraisalBean> getConsumerAppraisalsNotInCollection(String publicKey, Object[] array) throws Exception;

	/**
	 * @return
	 * @throws Exception
	 */
	public List<AppraisalBean> getAllAppraisalsList() throws Exception;

	/**
	 * @paparam userPublicKey
	 * @paparam limit
	 * @return
	 * @throws Exception
	 */
	public List<AppraisalBean> getTopAppraisalsListByUserIdAndLimit(
			String userPublicKey, Integer limit) throws Exception;
	
	/**
	 * Method for delete item component object
	 * 
	 * @paparam itemId 
	 * @paparam componentId
	 * @return
	 * @throws Exception  : error during the execution of operation
	 */
	void deleteAnyBeanByObject(Object objectBean) throws Exception;
		
}
