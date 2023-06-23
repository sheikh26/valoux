/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.dao;

import java.util.List;

import com.valoux.bean.ValouxStoreAdvertisementBean;

public interface ValouxStoreAdvertisementDao {
	/**
	 * This method save store advertisement
	 * @paparam storeAddBean
	 * @return
	 * @throws Exception
	 */
	ValouxStoreAdvertisementBean createStoreAdvertisement(ValouxStoreAdvertisementBean storeAddBean) throws Exception;
	
	/**
	 * This method get maximum index of advertisment table
	 * @return
	 * @throws Exception
	 */
	Integer getMaximumIndexOfStoreAdvTable() throws Exception;
	
	/**
	 * Thismehtod get store advertisement list by store id
	 * @paparam storeId
	 * @return
	 */
	List<ValouxStoreAdvertisementBean> getStoreAdvertisementListByStoreId(Integer storeId) throws Exception;
	
	/**
	 * This method get store advertisement detail by storeAdvertisementId
	 * @paparam storeAdvertisementId
	 * @return
	 */
	List<ValouxStoreAdvertisementBean> getStoreAdvertisementListByStoreAdvertisementId(Integer storeAdvertisementId) throws Exception;
	
	/**
	 * This method delete store advertisement
	 * @paparam storeAdvBean
	 * @throws Exception
	 */
	void deleteStoreAdvImages(ValouxStoreAdvertisementBean storeAdvBean) throws Exception;
	
	/**
	 * This method update store advertisement bean
	 * @paparam storeAdvBean
	 * @throws Exception
	 */
	void updateStoreAdvImages(ValouxStoreAdvertisementBean storeAdvBean) throws Exception;
	
	/**
	 * This method get all store advertisement
	 * @return
	 * @throws Exception
	 */
	List<ValouxStoreAdvertisementBean> getStoreAdvertisementList() throws Exception;
	
	/**
	 * This method get all store advertisment with distinct store id
	 * @return
	 * @throws Exception
	 */
	List<ValouxStoreAdvertisementBean> getStoreAdvertisementListByDistinctStoreId() throws Exception;
	
	/**
	 * This method merge stores
	 * @paparam primaryStoreId
	 * @paparam storeIdToBeMerged
	 * @throws Exception
	 */
	void mergestore(Integer primaryStoreId, Integer storeIdToBeMerged) throws Exception;
	
	/**
	 * This method update store advertisement bean
	 * @paparam storeAddBean
	 * @return
	 * @throws Exception
	 */
	ValouxStoreAdvertisementBean updateStoreAdvertisement(ValouxStoreAdvertisementBean storeAddBean) throws Exception;
}
