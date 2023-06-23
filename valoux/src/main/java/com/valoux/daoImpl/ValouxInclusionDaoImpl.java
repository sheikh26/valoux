package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ValouxInclusionBean;
import com.valoux.dao.ValouxInclusionDao;

public class ValouxInclusionDaoImpl implements ValouxInclusionDao {

	private static final Logger LOGGER = Logger.getLogger(ValouxInclusionDaoImpl.class);

	@Autowired
	@Qualifier(value = "thirdDBSessionFactory")
	private SessionFactory thirdDBSessionFactory;

	public SessionFactory getThirdDBSessionFactory() {
		return thirdDBSessionFactory;
	}

	public void setThirdDBSessionFactory(SessionFactory thirdDBSessionFactory) {
		this.thirdDBSessionFactory = thirdDBSessionFactory;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	@SuppressWarnings("unchecked")
	public List<ValouxInclusionBean> getValouxInclusionBeanListByComponentAndType(
			Integer componentId, Integer inclusionType) throws Exception {
		LOGGER.debug("ValouxInclusionDaoImpl : Enter Method getValouxInclusionBeanListByComponentAndType");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxInclusionBean.class);
		criteria.add(Restrictions.eq("valouxItemComponent.vicid", componentId));
		criteria.add(Restrictions.eq("inclusionType", inclusionType));
		List<ValouxInclusionBean> beans = (List<ValouxInclusionBean>) criteria.list();
		LOGGER.debug("ValouxInclusionDaoImpl : Exit Method getValouxInclusionBeanListByComponentAndType");
		return beans;
	}

	public void deleteInclusionsTypeFromRequest(
			List<ValouxInclusionBean> inclusionBeans) throws Exception {
		LOGGER.debug("ValouxInclusionDaoImpl : Enter Method deleteInclusionsTypeFromRequest");

		for (ValouxInclusionBean bean : inclusionBeans) {
			thirdDBSessionFactory.getCurrentSession().delete(bean);
		}

		LOGGER.debug("ValouxInclusionDaoImpl : Exit Method deleteInclusionsTypeFromRequest");
		
	}

	public void saveInclusionsTypeFromRequest(
			List<ValouxInclusionBean> valouxInclusionBeans) throws Exception {
		LOGGER.debug("ValouxInclusionDaoImpl : Enter Method saveInclusionsTypeFromRequest");

		for (ValouxInclusionBean bean : valouxInclusionBeans) {
			thirdDBSessionFactory.getCurrentSession().save(bean);
		}

		LOGGER.debug("ValouxInclusionDaoImpl : Exit Method saveInclusionsTypeFromRequest");
		
	}

	@SuppressWarnings("unchecked")
	public List<ValouxInclusionBean> getValouxInclusionBeanListByComponentAndType(
			Integer componentId) throws Exception {
		LOGGER.debug("ValouxInclusionDaoImpl : Enter Method getValouxInclusionBeanListByComponentAndType");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxInclusionBean.class);
		criteria.add(Restrictions.eq("valouxItemComponent.vicid", componentId));
		List<ValouxInclusionBean> beans = (List<ValouxInclusionBean>) criteria.list();
		LOGGER.debug("ValouxInclusionDaoImpl : Exit Method getValouxInclusionBeanListByComponentAndType");
		return beans;
	}

}
