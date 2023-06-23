package com.valoux.daoImpl;

import java.util.Date;
import java.util.List;

import com.valoux.bean.AgentBean;
import com.valoux.bean.LoginBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.AgentDao;
import com.valoux.dao.LoginDao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

public class LoginDaoImpl implements LoginDao {
	
	@Autowired
	@Qualifier(value = "secondDBSessionFactory")
	private SessionFactory secondDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(LoginDaoImpl.class);

	public SessionFactory getSecondDBSessionFactory() {
		return secondDBSessionFactory;
	}

	public void setSecondDBSessionFactory(SessionFactory secondDBSessionFactory) {
		this.secondDBSessionFactory = secondDBSessionFactory;
	}
	
	/**
	 * This method creates User For Login.
	 */

	public LoginBean createLoginUser(LoginBean loginBean) throws Exception {
		LOGGER.debug("Enter Method createLoginUser of AgentDaoImpl");
		secondDBSessionFactory.getCurrentSession().save(loginBean);
		LOGGER.debug("Exit Method createLoginUser of AgentDaoImpl");
		return loginBean;
	}
	
	/**
	 * This method performs check User Credentials.
	 */

	public LoginBean checkUserCredentials(LoginBean loginBean) throws Exception {
		LOGGER.debug("Enter Method checkUserCredentials of UserDaoImpl");
		LoginBean userLogin = new LoginBean();
		if (loginBean != null) {
			Criteria criteria = secondDBSessionFactory.getCurrentSession().createCriteria(LoginBean.class);
			criteria.add(Restrictions.eq("userName", loginBean.getUserName()));
			criteria.add(Restrictions.eq("password", loginBean.getPassword()));
			criteria.add(Restrictions.eq("userStatus", CommonConstants.USER_STATUS_ACTIVE));
			userLogin = (LoginBean) criteria.uniqueResult();

		}
		LOGGER.debug("Exit Method checkUserCredentials of UserDaoImpl");
		return userLogin;
	}

	/**
	 * This method performs check User Exist.
	 */
	@SuppressWarnings("unchecked")
	public boolean checkEmailAlreadyRegistered(String email) throws Exception {
		LOGGER.debug("Enter Method checkEmailAlreadyRegistered of UserDaoImpl");
		boolean agentExist = true;
		Criteria criteria = secondDBSessionFactory.getCurrentSession().createCriteria(LoginBean.class);
		criteria.add(Restrictions.eq("userName", email));
		List<LoginBean> loginBeanList = (List<LoginBean>) criteria.list();
		if (!(loginBeanList != null && loginBeanList.size() > 0)) {
			agentExist = false;
		}

		LOGGER.debug("Exit Method checkEmailAlreadyRegistered of UserDaoImpl");
		return agentExist;
	}
	
	/**
	 * This method creates User For Login.
	 */

	public LoginBean createUserInLogin(LoginBean loginBean) throws Exception {
		LOGGER.debug("Enter Method createUserForLogin of UserDaoImpl");
		secondDBSessionFactory.getCurrentSession().save(loginBean);
		LOGGER.debug("Exit Method createUserForLogin of UserDaoImpl");
		return loginBean;
	}
	

	/**
	 * This method performs check And Activate User.
	 */

	public LoginBean checkUserForActivation(String activationKey, String otp) throws Exception {
		LOGGER.debug("Enter Method checkUserForActivation of UserDaoImpl");
		if (activationKey != null) {
			Criteria criteria = secondDBSessionFactory.getCurrentSession().createCriteria(LoginBean.class);
			criteria.add(Restrictions.eq("authenticationCode", activationKey));
			criteria.add(Restrictions.eq("authCodeMobile", otp));
			LoginBean userRegistrationDetailBean = (LoginBean) criteria.uniqueResult();
			if (userRegistrationDetailBean != null) {
				
				return userRegistrationDetailBean;
			}

			return null;
		}
		LOGGER.debug("Exit Method checkUserForActivation of UserDaoImpl");
		return null;
	}
	
	public void updateLoginBean(LoginBean loginBean) throws Exception{
		LOGGER.debug("Enter Method updateLoginBean of UserDaoImpl");
		secondDBSessionFactory.getCurrentSession().saveOrUpdateCopy(loginBean);
		LOGGER.debug("Enter Method updateLoginBean of UserDaoImpl");
		
	}

	/**
	 * This method performs get Login Detail By PKey.
	 */

	public LoginBean getLoginDetailByPKey(String pKey) throws Exception {
		LOGGER.debug("Enter Method getLoginDetailByPKey of UserDaoImpl");
		Criteria criteria = secondDBSessionFactory.getCurrentSession().createCriteria(LoginBean.class);
		criteria.add(Restrictions.eq("privateKey", pKey));
		LoginBean loginBean = (LoginBean) criteria.uniqueResult();
		LOGGER.debug("Exit Method getLoginDetailByPKey of UserDaoImpl");
		return loginBean;
	}
	
	/**
	 * This method performs update Login Detail.
	 */
	public LoginBean updateLoginDetail(LoginBean loginBean) throws Exception {
		LOGGER.debug("Enter Method updateLoginDetail of UserDaoImpl");
		secondDBSessionFactory.getCurrentSession().beginTransaction();
		secondDBSessionFactory.getCurrentSession().update(loginBean);
		LOGGER.debug("Exit Method updateLoginDetail of UserDaoImpl");
		secondDBSessionFactory.getCurrentSession().getTransaction().commit();
		return loginBean;
	}
	
	/**
	 * This method performs resend OTP.
	 * 
	 */

	public LoginBean resendOTP(String authLoginCode) throws Exception {
		LOGGER.debug("Enter Method resendOTP of UserDaoImpl");
		LoginBean loginBean = null;
		Criteria criteria = secondDBSessionFactory.getCurrentSession().createCriteria(LoginBean.class);
		criteria.add(Restrictions.eq("authenticationCode", authLoginCode));
		loginBean = (LoginBean) criteria.uniqueResult();
		LOGGER.debug("Exit Method resendOTP of UserDaoImpl");
		return loginBean;
	}
	
	/**
	 * This method gets login bean by email id.
	 */

	public LoginBean getLoginBeanByEmailid(String email) throws Exception {
		LOGGER.debug("Enter Method getLoginBeanByEmailid of UserDaoImpl");
		Criteria criteria = secondDBSessionFactory.getCurrentSession().createCriteria(LoginBean.class);
		criteria.add(Restrictions.eq("userName", email));
		@SuppressWarnings("unchecked")
		List<LoginBean> loginBeanList = (List<LoginBean>) criteria.list();
		if (loginBeanList != null && loginBeanList.size() > 0) {
			return loginBeanList.get(0);
		}

		LOGGER.debug("Exit Method getLoginBeanByEmailid of UserDaoImpl");
		return null;
	}

	/**
	 * This method gets login bean by forget password key.
	 */

	public LoginBean getLoginBeanByForgetPasswordKey(String forgetPasswordKey) throws Exception {
		LOGGER.debug("Enter Method getLoginBeanByForgetPasswordKey of UserDaoImpl");
		Criteria criteria = secondDBSessionFactory.getCurrentSession().createCriteria(LoginBean.class);
		criteria.add(Restrictions.eq("forgetPasswordKey", forgetPasswordKey));
		@SuppressWarnings("unchecked")
		List<LoginBean> loginBeanList = (List<LoginBean>) criteria.list();
		if (loginBeanList != null && loginBeanList.size() > 0) {
			return loginBeanList.get(0);
		}
		LOGGER.debug("Exit Method getLoginBeanByForgetPasswordKey of UserDaoImpl");
		return null;
	}
	
	/**
	 * This method update login detail.
	 */
	public LoginBean updateLoginData(LoginBean loginBean) throws Exception {
		LOGGER.debug("Enter Method updateLoginData of UserDaoImpl");
		secondDBSessionFactory.getCurrentSession().update(loginBean);
		LOGGER.debug("Exit Method updateLoginData of UserDaoImpl");
		return loginBean;
	}

	/*
	 * This method retrieves Login Info by auth code.
	 */
	public LoginBean getUserLoginDetailsByAuthCode(String authLoginCode)
			throws Exception {
		LOGGER.debug("Enter Method getUserLoginDetailsByAuthCode of LoginDaoImpl");
		Criteria criteria = secondDBSessionFactory.getCurrentSession().createCriteria(LoginBean.class);
		criteria.add(Restrictions.eq("authenticationCode", authLoginCode));
		LoginBean loginBean = (LoginBean) criteria.uniqueResult();
		LOGGER.debug("Exit Method getUserLoginDetailsByAuthCode of LoginDaoImpl");
		return loginBean;
	}
	
	/**
	 * This method performs get All Active login Data with keyword.
	 */
	public List<LoginBean> getAllLoginDataWithKeyword(String keyword) throws Exception {
		LOGGER.debug("Enter Method getAllLoginDataWithKeyword of LoginDaoImpl");
		Criteria criteria = secondDBSessionFactory.getCurrentSession().createCriteria(LoginBean.class);
		Disjunction orConditions = Restrictions.disjunction();
		orConditions.add(Restrictions.ilike("lastName", "%"+keyword+"%"));
		orConditions.add(Restrictions.ilike("firstName", "%"+keyword+"%"));
		orConditions.add(Restrictions.ilike("middleName", "%"+keyword+"%"));
		criteria.add(orConditions);
		@SuppressWarnings("unchecked")
		List<LoginBean> loginBeanList = (List<LoginBean>) criteria.list();
		LOGGER.debug("Exit Method getAllLoginDataWithKeyword of LoginDaoImpl");
		return loginBeanList;
	}
	
	/**
	 * This method delete login  object
	 */
	public void deleteAnyBeanByObject(Object objectBean)
			throws Exception {
		LOGGER.debug("LoginDaoImpl : Enter Method deleteAnyBeanByObject");

		secondDBSessionFactory.getCurrentSession().delete(objectBean);

		LOGGER.debug("LoginDaoImpl : Exit Method deleteAnyBeanByObject");
		
	}

}
