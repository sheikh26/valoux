package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ValouxAgentStoreBean;
import com.valoux.dao.ValouxAgentStoreDao;

public class ValouxAgentStoreDaoImpl implements ValouxAgentStoreDao {
	
	@Autowired
	@Qualifier(value = "sessionFactory")
	private SessionFactory sessionFactory;

	private static final Logger LOGGER = Logger.getLogger(ValouxAgentStoreDaoImpl.class);

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * This method performs save Agent StoreId.
	 */
	public ValouxAgentStoreBean saveAgentStoreId(ValouxAgentStoreBean agentStoreBean) throws Exception {
		LOGGER.debug("Enter method saveAgentStoreId of AgentDaoImpl");
		sessionFactory.getCurrentSession().save(agentStoreBean);
		LOGGER.debug("Exit method saveAgentStoreId of AgentDaoImpl");
		return agentStoreBean;
	}

	/**
	 * This method performs get Valoux Agent Store Bean By rKey.
	 */
	public ValouxAgentStoreBean getAgentStoreDataByRelationKey(String rKey) throws Exception {
		LOGGER.debug("Enter method getValouxAgentStoreBeanByStoreId of AgentDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxAgentStoreBean.class);
		criteria.add(Restrictions.eq("relationKey", rKey));
		ValouxAgentStoreBean agentStoreBean = (ValouxAgentStoreBean) criteria.uniqueResult();
		LOGGER.debug("Exit method getValouxAgentStoreBeanByStoreId of AgentDaoImpl");
		return agentStoreBean;
	}
	
	/**
	 * This method performs get Valoux Agent Store Bean By rKey.
	 */
	public List<ValouxAgentStoreBean> getAllAgentStoreDataByRelationKey(String rKey) throws Exception {
		LOGGER.debug("Enter method getValouxAgentStoreBeanByStoreId of AgentDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxAgentStoreBean.class);
		criteria.add(Restrictions.eq("relationKey", rKey));
		List<ValouxAgentStoreBean> agentStoreBean = (List<ValouxAgentStoreBean>) criteria.list();
		LOGGER.debug("Exit method getValouxAgentStoreBeanByStoreId of AgentDaoImpl");
		return agentStoreBean;
	}

	/**
	 * This method performs get Valoux Agent Store Bean By storeId.
	 */
	public List<ValouxAgentStoreBean> getAgentStoreDataByStoreId(Integer storeId) throws Exception {
		LOGGER.debug("Enter method getValouxAgentStoreBeanByStoreId of AgentDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxAgentStoreBean.class);
		criteria.add(Restrictions.eq("valouxStoreBean.storeId", storeId));
		@SuppressWarnings("unchecked")
		List<ValouxAgentStoreBean> agentStoreBeanList = (List<ValouxAgentStoreBean>) criteria.list();
		LOGGER.debug("Exit method getValouxAgentStoreBeanByStoreId of ValouxAgentStoreDaoImpl");
		return agentStoreBeanList;
	}
	
	public List<ValouxAgentStoreBean> getValouxAgentStoresByStoreId(Integer storeId) {
		LOGGER.debug("Enter Method getValouxAgentStoresByStoreId of ValouxAgentStoreDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxAgentStoreBean.class);
		criteria.add(Restrictions.eq("valouxStoreBean.storeId", storeId));

		@SuppressWarnings("unchecked")
		List<ValouxAgentStoreBean> valouxStoreBeanList = (List<ValouxAgentStoreBean>) criteria.list();
		LOGGER.debug("Exit Method getValouxAgentStoresByStoreId of ValouxAgentStoreDaoImpl");
		return valouxStoreBeanList;
	}
	
	/**
	 * This method merge store
	 */
	public void mergestore(Integer primaryStoreId, Integer storeIdToBeMerged) throws Exception{
		LOGGER.debug("Enter Method mergestore of ValouxAgentStoreDaoImpl");
//		sessionFactory.openSession();
//		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		String hqlUpdate = "update valoux_agent_store s set s.store_id=:primaryStoreId where s.store_id=:storeIdToBeMerged";
		// or String hqlUpdate = "update Customer set name = :newName where name = :oldName";
		int updatedEntities = sessionFactory.getCurrentSession().createSQLQuery(hqlUpdate).setInteger("primaryStoreId", primaryStoreId).setInteger("storeIdToBeMerged", storeIdToBeMerged).executeUpdate();
		
//		if (!tx.wasCommitted())
//		    tx.commit();
//		sessionFactory.close();
		LOGGER.debug("Enter Method mergestore of ValouxAgentStoreDaoImpl");
	}


}
