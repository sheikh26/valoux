/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;

import com.valoux.bean.AppraisalCollectionBean;
import com.valoux.bean.AppraisalItemsBean;

public interface AppraisalItemsDao {
	/**
	 * This method creates Appraisal for Items.
	 * 
	 * @paparam appraisalItemList
	 *            : Business object carrying all the information relates to
	 *            create appraisal for Items.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public AppraisalItemsBean saveAppraisalItemList(List<AppraisalItemsBean> appraisalItemList, String requestType)
			throws Exception;

	/**
	 * This method check Existing Appraisal for ItemList.
	 * 
	 * @paparam appraisalItemList
	 *            : Business object carrying all the information relates to
	 *            check Existing Appraisal for ItemList.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public AppraisalItemsBean checkExistingAppraisalItemList(List<AppraisalItemsBean> appraisalItemList,
			String requestType) throws Exception;


	/**
	 * This method get the apraisal item detail by itemid
	 * 
	 * @paparam itemId
	 * @return
	 * @throws Exception
	 */
	public List<AppraisalItemsBean> getApraisalItemListByItemId(Integer itemId) throws Exception;

	

	/**
	 * This method get all the item associated with appraisal
	 * 
	 * @paparam appraisalId
	 * @return
	 * @throws Exception
	 */
	public List<AppraisalItemsBean> getItemAssociatedWithAppraisal(Integer appraisalId) throws Exception;

	/**
	 * This method get the appraisal item detail by appraisalId
	 * 
	 * @paparam appraisalId
	 * @return
	 * @throws Exception
	 */
	public List<AppraisalItemsBean> getAppraisalItemDataByAppraisalId(Integer appraisalId) throws Exception;

	/**
	 * This method gets the appraisal by collectionId and appraisal id
	 * 
	 * @paparam itemId
	 *            : Business object carrying item data.
	 * @paparam appraisalId
	 *            : Business object carrying appraisal data.
	 * @return : List<AppraisalItemsBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<AppraisalItemsBean> getAppraisalByAppraisalIdAndItemId(JSONArray itemId, Integer appraisalId) throws Exception;

	/**
	 * Method used to delete all item from appraisal
	 * 
	 * @paparam itemBeans
	 *            : Business object carrying item data.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	void deleteItemList(List<AppraisalItemsBean> itemBeans) throws Exception;

	/**
	 * This method gets the appraisal by itemId and appraisal id
	 * 
	 * @paparam itemId
	 *            : Business object carrying item data.
	 * @paparam appraisalId
	 *            : Business object carrying appraisal data.
	 * @return : List<AppraisalItemsBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<AppraisalItemsBean> getAppraisalByAppraisalIdAndItemId(Integer itemId, JSONArray appraisalId) throws Exception;
	/**
	 * This method will delete the Appraisal item details
	 * 
	 * @paparam deleteListItems
	 *            : Business object carrying collection data.
	 * @paparam appraisalId
	 *            : Business object carrying collection data.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	void deletedItemsFromAppraisal(List<Integer> deleteListItems, Integer appraisalId) throws Exception;

	
	/**
	 * This method will delete the Appraisal item details
	 * 
	 * @paparam itemId
	 *            : Business object carrying appraisal data.
	 * @paparam appraisalId
	 *            : Business object carrying appraisal data.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<AppraisalItemsBean> getAppraisalItemsBeanByAppraisalAndItemId(Integer appraisalId, int itemId)
			throws Exception;

	/**
	 * This method will add Appraisal item details
	 * 
	 * @paparam itemsList
	 *            : Business object carrying appraisal data.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void addItemsInAppraisals(List<AppraisalItemsBean> itemsList) throws Exception;


	/**
	 * This method will delete Appraisal item details
	 * 
	 * @paparam beanList
	 *            : Business object carrying appraisal data.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void deletedAllItemsFromAppraisals(List<AppraisalItemsBean> beanList) throws Exception;

	/**
	 * @paparam appraisalId
	 * @paparam itemId
	 * @return
	 * @throws Exception
	 */
	public AppraisalItemsBean getAppraisalItemBeanByAppraisalAndItemId(Integer appraisalId, int itemId) throws Exception;

	/**
	 * @paparam itemId
	 * @paparam appraisalId
	 * @return
	 * @throws Exception
	 */
	public List<AppraisalItemsBean> getAppraisalItemsByItemIdAndNotThisAppraisalId(Integer itemId,
			Integer appraisalId) throws Exception;

	public void updateAppraisalItemDetails(AppraisalItemsBean appraisalItemsBean) throws Exception;

}
