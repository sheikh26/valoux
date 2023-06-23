/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.dao;

import java.util.List;

import com.valoux.bean.ValouxStoreBean;

/**
 * This <java>interface</java> ValouxStoreDao interface has all the abstract
 * methods related to store.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
public interface ValouxStoreDao {

	/**
	 * This method performs get Agent Detail By RKey.
	 * 
	 * @paparam storeBean
	 *            : Business object carrying all the information relates to
	 *            store.
	 * @return ValouxStoreBean : Created ValouxStoreBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	ValouxStoreBean createStore(ValouxStoreBean storeBean) throws Exception;

	/**
	 * This method performs get All Store Data.
	 * 
	 * @return List<ValouxStoreBean> : Created ValouxStoreModel list object
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxStoreBean> getAllStoreData() throws Exception;

	/**
	 * This method performs check Duplicate Store.
	 * 
	 * @paparam storeBean
	 *            : Business object carrying all the information relates to
	 *            store.
	 * @return ValouxStoreBean: Created ValouxStoreBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	List<ValouxStoreBean> checkDuplicateStore(ValouxStoreBean storeBean) throws Exception;

	/**
	 * This method performs get Store Data By StoreId.
	 * 
	 * @paparam storeId
	 *            : Business object carrying all the information relates to
	 *            store.
	 * @return ValouxStoreBean : Created ValouxStoreBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	ValouxStoreBean getStoreDataByStoreId(Integer storeId) throws Exception;

	

	/**
	 * This method get list of all active store data
	 * 
	 * @return : List<ValouxStoreBean>
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public List<ValouxStoreBean> getAllActiveStoreData() throws Exception;

	/**
	 * This method update the store detail
	 * 
	 * @paparam storeBean
	 * @return : Created ValouxStoreBean object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	ValouxStoreBean updateStoreData(ValouxStoreBean storeBean) throws Exception;
	
	/**
	 * This method get all active data with keyword
	 * @paparam keyword
	 * @return
	 * @throws Exception
	 */
	List<ValouxStoreBean> getAllActiveStoreDataWithKeyword(String keyword,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception;
	
	/**
	 * This method get all store data not in object
	 * @paparam objects
	 * @return
	 * @throws Exception
	 */
	List<ValouxStoreBean> getStoreListNotInArray(Object[] objects) throws Exception;
	
	/**
	 * This metho get store having advertisment
	 * @paparam objects
	 * @return
	 * @throws Exception
	 */
	List<ValouxStoreBean> getStoreListInArray(Object[] objects) throws Exception;
	
	/**
	 * This method delete store 
	 * @paparam storeBean
	 * @throws Exception
	 */
	void deleteStore(ValouxStoreBean storeBean) throws Exception;

	/**
	 * @paparam storeBean
	 * @throws Exception
	 */
	ValouxStoreBean updateStoreDetails(ValouxStoreBean storeBean) throws Exception;
	

}
