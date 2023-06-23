package com.valoux.daoImpl;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.LoginLogBean;
import com.valoux.dao.LoginLogDao;

public class LoginLogDaoImpl implements LoginLogDao {
	
	@Autowired
	@Qualifier(value = "secondDBSessionFactory")
	private SessionFactory secondDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(LoginLogDaoImpl.class);

	public SessionFactory getSecondDBSessionFactory() {
		return secondDBSessionFactory;
	}

	public void setSecondDBSessionFactory(SessionFactory secondDBSessionFactory) {
		this.secondDBSessionFactory = secondDBSessionFactory;
	}
	
	/**
	 * This method performs verify User OTP.
	 */

	public boolean verifyUserOTP(Integer authUserID, String authLoginCode) throws Exception {
		LOGGER.debug("Enter Method verifyUserOTP of AgentDaoImpl");
		boolean agentExist = true;
		LoginLogBean loginLogBean = null;
		Criteria criteria = secondDBSessionFactory.getCurrentSession().createCriteria(LoginLogBean.class);
		criteria.add(Restrictions.eq("logId", authUserID));
		criteria.add(Restrictions.eq("authLoginCode", authLoginCode));
		loginLogBean = (LoginLogBean) criteria.uniqueResult();
		if (loginLogBean != null) {
			agentExist = true;
		} else {
			agentExist = false;
		}

		LOGGER.debug("Exit Method verifyUserOTP of AgentDaoImpl");
		return agentExist;
	}
	
	/**
	 * This method creates Login Logs.
	 */

	public LoginLogBean createLoginLogs(LoginLogBean loginBean) throws Exception {
		LOGGER.debug("Enter Method createUserForLogin of UserDaoImpl");
		secondDBSessionFactory.getCurrentSession().save(loginBean);
		LOGGER.debug("Exit Method createUserForLogin of UserDaoImpl");
		return loginBean;
	}

}
