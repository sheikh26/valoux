/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.service;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;

import com.valoux.bean.AgentBean;
import com.valoux.bean.LoginBean;
import com.valoux.bean.MasterRoleBean;
import com.valoux.bean.ValouxAgentStoreBean;
import com.valoux.model.AgentModel;
import com.valoux.model.LoginModel;
import com.valoux.model.ValouxAgentStoreModel;
import com.valoux.model.ValouxStoreAdvertisementModel;
import com.valoux.model.ValouxStoreModel;

/**
 * This <java>class</java> provides service contracts for different operations
 * related to agent
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
public interface AgentService {
	/**
	 * Defines Method and Contract to create Agent.
	 * 
	 * @paparam agentModel
	 *            : Business object carrying all the information relates to
	 *            agent.
	 * @return AgentBean : Created Agent object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public AgentBean createAgent(AgentModel agentModel) throws Exception;

	/**
	 * This method creates authenticable user, which can login.
	 * 
	 * @paparam loginModel
	 *            : Business object carrying information relates to agent login
	 *            data.
	 * @return LoginBean : Created Login object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public LoginBean createLoginUser(LoginModel loginModel) throws Exception;

	/**
	 * This method retrieves specific Role data.
	 * 
	 * @paparam roleName
	 *            : Role name for which role data to be retrieved.
	 * @return MasterRoleBean : retrieved role data
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public MasterRoleBean getRoleData(String roleName) throws Exception;

	/**
	 * This method retrieves Agent info using username .
	 * 
	 * @paparam loginBean
	 *            : Agent name for which information to be retrieved.
	 * @return AgentBean : retrieved agent data
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public AgentBean getAgentDetails(String agentName) throws Exception;

	/**
	 * This method verifies User OTP.
	 * 
	 * @paparam authUserID
	 *            : user identifier for which otp need to be checked
	 * @paparam authLoginCode
	 *            : otp to be checked for the user
	 * @return boolean : return true/false
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public boolean verifyUserOTP(Integer authUserID, String authLoginCode) throws Exception;

	/**
	 * This method retrieves agent data using relation key.
	 * 
	 * @paparam rKey
	 *            : public key of agent.
	 * @return AgentBean : retrieved agent data.
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public AgentModel getAgentDetailByRelationKey(String rKey) throws Exception;

	/**
	 * This method perform agent registration operation.
	 * 
	 * @paparam loginModel
	 *            : Business object carrying all the information relates to
	 *            login data.
	 * @paparam agentProfileModel
	 *            : Business object carrying all the information relates to
	 *            agent data.
	 * @return AgentBean : Created agent object
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public AgentBean registerAgent(LoginModel loginModel, AgentModel agentProfileModel) throws Exception;

	/**
	 * This method performs save store bean operation.
	 * 
	 * @paparam agentStoreModel
	 *            : Business object carrying all the information relates to
	 *            store.
	 * @return ValouxAgentStoreModel : created store
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public ValouxAgentStoreModel saveAgentStoreBean(ValouxAgentStoreModel agentStoreModel) throws Exception;

	/**
	 * This method get Store data using relation key.
	 * 
	 * @paparam rKey
	 *            : Business object carrying all the information relates to
	 *            store.
	 * @return valouxAgentStoreModel : business object having info of retrieved
	 *         store
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public ValouxAgentStoreModel getStoreDataByRelationKey(String rKey) throws Exception;

	/**
	 * This method reurns the count of user associated with store
	 * 
	 * @paparam storeId
	 * @return : Integer
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public Integer getNumberOfUserAssociatedWithStore(Integer storeId) throws Exception;

	/**
	 * This method activates or deactivates store
	 * 
	 * @paparam storeId
	 * @paparam action
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public void activateOrDeactiveStore(Integer storeId, String action) throws Exception;

	/**
	 * This method gets the agent store data bu store id
	 * 
	 * @paparam storeId
	 * @return
	 * @throws Exception
	 */
	public List<ValouxAgentStoreBean> getAgentDetailAssociatedWithStore(Integer storeId) throws Exception;
	
	/**
	 * This method saves store adv image
	 * @paparam advImagejArray
	 * @paparam storeAdvModel
	 * @throws Exception
	 */
	void saveStoreAdv(JSONArray advImagejArray, ValouxStoreAdvertisementModel storeAdvModel) throws Exception;
	
	/**
	 * This method activates or deactivates user
	 * 
	 * @paparam status
	 * @paparam userPublicKey
	 * @throws Exception
	 *             : error during the execution of operation
	 */
	public LoginBean activateOrDeactiveAgent(Integer status, String userPublicKey) throws Exception;
	
	/**
	 * This method get adv list by store id
	 * @paparam storeId
	 * @return
	 */
	List<ValouxStoreAdvertisementModel> getStoreAdvertisementListByStoreId(Integer storeId) throws Exception;
	
	/**
	 * This method delete advertisement image
	 * @paparam storeId
	 * @paparam storeAdvertisementId
	 * @throws Exception
	 */
	void deleteStoreAdvertisement(Integer storeId,Integer storeAdvertisementId) throws Exception;
	
	/**
	 * This method activate/inactivate store adv
	 * @paparam storeId
	 * @paparam storeAdvertisementId
	 * @paparam status
	 * @throws Exception
	 */
	void activeInactiveStoreAdv(Integer storeId,Integer storeAdvertisementId,Integer status) throws Exception;
	
	/**
	 * This method get store detail not have advertisement
	 * @return
	 * @throws Exception
	 */
	List<ValouxStoreModel> getStoreNoteHavinAdvertisement() throws Exception;
	
	/**
	 * This method get all store advertisement
	 * @return
	 * @throws Exception
	 */
	List<ValouxStoreAdvertisementModel> getAllStoreAdvertisementDetail() throws Exception;
	
	/**
	 * This method get all store having advertisement
	 * @return
	 * @throws Exception
	 */
	List<ValouxStoreModel> getStoreHavinAdvertisement() throws Exception;
	
	/**
	 * this method update store advertisment
	 * @paparam advImagejArray
	 * @paparam storeAdvertisementId
	 * @throws Exception
	 */
	void updateStoreAdv(JSONArray advImagejArray, Integer storeAdvertisementId) throws Exception;
	
	/**
	 * This method get store advertisment by storeAdvertisementId
	 * @paparam storeAdvertisementId
	 * @return
	 * @throws Exception
	 */
	ValouxStoreAdvertisementModel getStoreAdvertisementListByStoreAdvertisementId(Integer storeAdvertisementId) throws Exception;

	/**
	 * @paparam agentProfileModel
	 * @throws Exception
	 */
	public void updateAgentSignature(AgentModel agentProfileModel) throws Exception;

	/**
	 * @paparam publicKey
	 * @return
	 * @throws Exception
	 */
	public Boolean deleteAgentSignDocument(String publicKey) throws Exception;
	
	/**
	 * This method get all agent list
	 * @return
	 * @throws Exception
	 */
	List<AgentModel> getAllAgentDetail() throws Exception;
	
	/**
	 * this method delete agent
	 * @paparam userPublicKey
	 * @return
	 * @throws Exception
	 */
	Boolean deleteAgent(String userPublicKey) throws Exception;
}
