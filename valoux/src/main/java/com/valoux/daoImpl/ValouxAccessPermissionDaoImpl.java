package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ValouxAccessPermissionBean;
import com.valoux.dao.ValouxAccessPermissionDao;

public class ValouxAccessPermissionDaoImpl implements ValouxAccessPermissionDao {

	@Autowired
	@Qualifier(value = "thirdDBSessionFactory")
	private SessionFactory thirdDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(ValouxAccessPermissionDaoImpl.class);

	public SessionFactory getThirdDBSessionFactory() {
		return thirdDBSessionFactory;
	}

	public void setThirdDBSessionFactory(SessionFactory thirdDBSessionFactory) {
		this.thirdDBSessionFactory = thirdDBSessionFactory;
	}
	
	/**
	 * This method add valoux access permission
	 */
	public void addValouxAccessPermission(
			ValouxAccessPermissionBean accessPermissionBean) throws Exception {
		LOGGER.debug("AppraisalDaoImpl : Enter Method addValouxAccessPermission");

		if (accessPermissionBean != null) {
			thirdDBSessionFactory.getCurrentSession().persist(accessPermissionBean);
		}
		LOGGER.debug("AppraisalDaoImpl : Exit Method addValouxAccessPermission");
	}
	
	/**
	 * This method will give list of agent having access permission of consumers.
	 */
	public List<ValouxAccessPermissionBean> getAgentAccessPermissionUsers(
			String agentKey) throws Exception {
		LOGGER.debug("AppraisalDaoImpl : Enter Method getAppraisalItemsBeanByAppraisalAndItemId");
		List<ValouxAccessPermissionBean> beans = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxAccessPermissionBean.class);
		criteria.add(Restrictions.eq("givenPermissionTo", agentKey));
		beans = (List<ValouxAccessPermissionBean>) criteria.list();

		LOGGER.debug("AppraisalDaoImpl : Exit Method getAppraisalItemsBeanByAppraisalAndItemId");
		return beans;
	}
	
	

}
