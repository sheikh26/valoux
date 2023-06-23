/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;

import com.valoux.bean.AppraisalCollectionBean;
import com.valoux.bean.ValouxCollectionItemBean;

public interface AppraisalCollectionDao {


	/**
	 * This method creates Appraisal for Collection.
	 * 
	 * @paparam appraisalItemList
	 *            : Business object carrying all the information relates to
	 *            create appraisal for Collection.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public AppraisalCollectionBean saveAppraisalCollectionList(List<AppraisalCollectionBean> appraisalItemList,
			String requestType) throws Exception;


	/**
	 * This method check Existing Appraisal for CollectionList.
	 * 
	 * @paparam appraisalCollectionList
	 *            : Business object carrying all the information relates to
	 *            check Existing Appraisal for CollectionList.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public AppraisalCollectionBean checkExistingAppraisalCollectionList(
			List<AppraisalCollectionBean> appraisalCollectionList, String requestType) throws Exception;
	
	/**
	 * This method get the appraisal collection detail by appraisalId
	 * 
	 * @paparam appraisalId
	 * @return
	 * @throws Exception
	 */
	public List<AppraisalCollectionBean> getAppraisalCollectionDataByAppraisalId(Integer appraisalId) throws Exception;

	/**
	 * Method for fetching list of appraisals of collection
	 * 
	 * @paparam collectionId
	 * @return
	 * @throws Exception
	 */
	public List<AppraisalCollectionBean> getAppraisalCollectionBeansByCollectionId(Integer collectionId)
			throws Exception;
	
	/**
	 * Method for fetching list of appraisals
	 * 
	 * @paparam appraisalId
	 * @return
	 * @throws Exception
	 */
	public List<AppraisalCollectionBean> getAppraisalBeansByAppraisalId(Integer appraisalId) throws Exception;

	/**
	 * This method gets the appraisal by collectionId and appraisal id
	 * 
	 * @paparam collectionId
	 *            : Business object carrying collection data.
	 * @paparam appraisalId
	 *            : Business object carrying appraisal data.
	 * @return : List<AppraisalCollectionBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<AppraisalCollectionBean> getAppraisalByAppraisalIdAndCollectionId(JSONArray collectionId, Integer appraisalId)
			throws Exception;


	/**
	 * Method used to delete all collection from appraisal
	 * 
	 * @paparam collectionBeans
	 *            : Business object carrying collection items data.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	void deleteCollectionList(List<AppraisalCollectionBean> collectionBeans) throws Exception;

	/**
	 * This method will delete the Appraisal collection details
	 * 
	 * @paparam deleteCollectionList
	 *            : Business object carrying collection data.
	 * @paparam appraisalId
	 *            : Business object carrying collection data.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	void deletedCollectionFromAppraisal(List<Integer> deleteCollectionList, Integer appraisalId) throws Exception;
	/**
	 * This method get all collectionassociated with appraisal
	 * 
	 * @paparam appraisalId
	 * @return
	 * @throws Exception
	 */
	public List<AppraisalCollectionBean> getCollectionAssociatedWithAppraisal(Integer appraisalId) throws Exception;


	/**
	 * This method get all collection appraisals
	 * 
	 * @paparam collectionId
	 * @paparam appraisalId
	 * @return
	 * @throws Exception
	 */
	List<AppraisalCollectionBean> getCollectionAppraisalByCollectionIdAndAppraisalId(Integer collectionId,
			Integer appraisalId) throws Exception;
	/**
	 * This method delete collection appraisals
	 * 
	 * @paparam collectionBeans
	 * @throws Exception
	 */
	void deleteUserCollectionAppraisalList(List<AppraisalCollectionBean> collectionBeans) throws Exception;

	
	/**
	 * This method get list collection appraisals
	 * 
	 * @paparam collectionId
	 * @return
	 * @throws Exception
	 */
	List<AppraisalCollectionBean> getCollectionAppraisalsById(Integer collectionId) throws Exception;


	/**
	 * This method delete list of deleted collection appraisals in collection
	 * 
	 * @paparam deleteListAppraisals
	 * @paparam appraisalCollectionId
	 * @throws Exception
	 */
	void deletedAppraisalsFromCollection(List<Integer> deleteListAppraisals, Integer appraisalCollectionId)
			throws Exception;


	/**
	 * This method add list of collection appraisals in collection
	 * 
	 * @paparam appraisalsList
	 * @throws Exception
	 */
	void addAppraisalsInCollection(List<AppraisalCollectionBean> appraisalsList) throws Exception;

	/**
	 * This method delete list of collection appraisals from collection
	 * 
	 * @paparam beanList
	 * @throws Exception
	 */
	void deletedAllAppraisalsFromCollection(List<AppraisalCollectionBean> beanList) throws Exception;


	/**
	 * @paparam vcid
	 * @paparam appraisalId
	 * @return
	 * @throws Exception
	 */
	public List<AppraisalCollectionBean> getAppraisalCollectionssByCollectionAndNotThisAppraisalId(
			Integer vcid, Integer appraisalId) throws Exception;

	/**
	 * @paparam vcid
	 * @paparam appraisalId
	 * @return
	 * @throws Exception
	 */
	public AppraisalCollectionBean getAppraisalCollectionByCollectionAndAppraisalId(
			Integer vcid, Integer appraisalId) throws Exception;


	/**
	 * @paparam appraisalCollectionBean
	 * @throws Exception
	 */
	public void updateAppraisalCollectionDetails(
			AppraisalCollectionBean appraisalCollectionBean) throws Exception;
	
}
