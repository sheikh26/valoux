package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.UserInterestIn;
import com.valoux.dao.UserDao;
import com.valoux.dao.UserInterestInDao;

public class UserInterestInDaoImpl implements UserInterestInDao {
	@Autowired
	@Qualifier(value = "sessionFactory")
	private SessionFactory sessionFactory;

	private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * This method performs save Interested In For User.
	 */
	public void saveInterestedInForUser(List<UserInterestIn> userInterestInList) throws Exception {
		LOGGER.debug("Enter Method saveInterestedInForUser of UserDaoImpl");
		for (UserInterestIn userInterestIn : userInterestInList) {
			sessionFactory.getCurrentSession().save(userInterestIn);
		}
		LOGGER.debug("Exit Method saveInterestedInForUser of UserDaoImpl");
	}
	
	/**
	 * This method performs get User InterestIn Detail By RKey.
	 */

	public List<UserInterestIn> getUserInterestInDetailByRKey(String rKey) throws Exception {
		LOGGER.debug("Enter Method getUserInterestInDetailByRKey of UserDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserInterestIn.class);
		criteria.add(Restrictions.eq("relationKey", rKey));
		@SuppressWarnings("unchecked")
		List<UserInterestIn> userInterestinList = (List<UserInterestIn>) criteria.list();
		LOGGER.debug("Exit Method getUserInterestInDetailByRKey of UserDaoImpl");
		return userInterestinList;
	}
	
	/**
	 * This method performs delete User InterestIn Detail.
	 */
	public void deleteUserInterestInDetail(String relationKey) throws Exception {
		LOGGER.debug("Enter Method deleteUserInterestInDetail of UserDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserInterestIn.class);
		criteria.add(Restrictions.eq("relationKey", relationKey));
		@SuppressWarnings("unchecked")
		List<UserInterestIn> userInterestinList = (List<UserInterestIn>) criteria.list();
		if (userInterestinList != null && userInterestinList.size() > 0) {
			for (UserInterestIn userInterestIn : userInterestinList) {
				sessionFactory.getCurrentSession().delete(userInterestIn);
			}
		}
		LOGGER.debug("Exit Method deleteUserInterestInDetail of UserDaoImpl");
	}

}
