/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.codec.Charsets;

import com.google.common.io.Files;
import com.valoux.bean.AgentBean;
import com.valoux.bean.LoginBean;
import com.valoux.bean.LoginLogBean;
import com.valoux.bean.ValouxStoreBean;
import com.valoux.constant.CommonConstants;
import com.valoux.model.LoginModel;

/**
 * This <java>class</java> CommonMailUtility set all the beans related to user
 * or agent.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

public class CommonMailUtility {

	public static String getUserRegistrationMailContent(LoginBean loginBean) {
		Properties prop = CommonUtility.getFromProperty();
		String userRegistratonMailer = null;

		userRegistratonMailer = prop.getProperty("app.signup.mailer.name");

		String basePath = prop.getProperty("app.base.path");
		String body = null;
		try {
			body = Files.toString(new File(userRegistratonMailer), Charsets.UTF_8);
			body = body.replace("__UserName__", loginBean.getFirstName() + " " + loginBean.getLastName());
			body = body.replace("__BASEPATH__", basePath);
			body = body.replace("__EMAIL__", loginBean.getUserName());
			body = body.replace("__PASSWORD__", loginBean.getPassword());
			body = body.replace("__INVITATIONKEY__", loginBean.getAuthenticationCode());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}
	
	public static String getForgetPasswordMailContent(LoginBean loginBean) {
		Properties prop = CommonUtility.getFromProperty();
		String userRegistratonMailer = null;

		userRegistratonMailer = prop.getProperty("app.forgetPassword.mailer.name");

		String basePath = prop.getProperty("app.base.path");
		String body = null;
		try {
			body = Files.toString(new File(userRegistratonMailer), Charsets.UTF_8);
			body = body.replace("__UserName__", loginBean.getFirstName() + " " + loginBean.getLastName());
			body = body.replace("__BASEPATH__", basePath);
			body = body.replace("__EMAIL__", loginBean.getUserName());
			body = body.replace("__PASSWORD__", loginBean.getPassword());
			body = body.replace("__INVITATIONKEY__", loginBean.getForgetPasswordKey());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}

	public static String getAgentLoginMailContent(LoginBean loginBean, LoginLogBean loginLogBean) {
		Properties prop = CommonUtility.getFromProperty();
		String userRegistratonMailer = null;

		userRegistratonMailer = prop.getProperty("app.verify.agent");

		String basePath = prop.getProperty("app.base.path");
		String body = null;
		try {
			body = Files.toString(new File(userRegistratonMailer), Charsets.UTF_8);
			body = body.replace("__UserName__", loginBean.getFirstName() + " " + loginBean.getLastName());
			body = body.replace("__BASEPATH__", basePath);
			body = body.replace("__EMAIL__", loginBean.getUserName());
			body = body.replace("__PASSWORD__", loginBean.getPassword());
			body = body.replace("__INVITATIONKEY__", loginLogBean.getAuthLoginCode());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}

	public static String getStoreRegistrationMailContent(LoginBean loginBean, ValouxStoreBean storeBean,
			AgentBean agentBean, String agentRole) {
		Properties prop = CommonUtility.getFromProperty();
		String userRegistratonMailer = null;

		userRegistratonMailer = prop.getProperty("app.signup.store.mailer.name");

		String basePath = prop.getProperty("app.base.path");
		String body = null;

		try {
			body = Files.toString(new File(userRegistratonMailer), Charsets.UTF_8);
			body = body.replace("__UserName__", loginBean.getFirstName() + " " + loginBean.getLastName());
			body = body.replace("__BASEPATH__", basePath);
			body = body.replace("__EMAIL__", agentBean.getEmailId());
			body = body.replace("__MOBILE__", agentBean.getMobile());
			body = body.replace("__AgentRole__", agentRole);

			body = body.replace("__StoreName__", storeBean.getName());

			if (storeBean.getAddressLine2() != null && !storeBean.getAddressLine2().equals("")
					&& !storeBean.getAddressLine2().isEmpty()) {
				body = body
						.replace("__StoreAddress__", storeBean.getAddressLine1() + " " + storeBean.getAddressLine2());
			} else {
				body = body.replace("__StoreAddress__", storeBean.getAddressLine1());
			}
			if (storeBean.getPhone() != null && !storeBean.getPhone().equals("")  && !storeBean.getPhone().isEmpty()) {
				body = body.replace("__StorePhone__", storeBean.getPhone());
			} else {
				body = body.replace("__StorePhone__", CommonConstants.STORE_PHONE);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}
	
	public static String getSharedMailContentForRegisteredUser(String userName) {
		Properties prop = CommonUtility.getFromProperty();
		String sharedMailer = null;

		sharedMailer = prop.getProperty("app.shareItemRegisteredUser.mailer.name");
		String basePath = prop.getProperty("app.base.path");

		String body = null;

		try {
			body = Files.toString(new File(sharedMailer), Charsets.UTF_8);
			body = body.replace("__UserName__", userName);
			body = body.replace("__BASEPATH__", basePath);
			} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}
	
	public static String getMailContenForNewItemForConsumer(String userName) {
		Properties prop = CommonUtility.getFromProperty();
		String sharedMailer = null;

		sharedMailer = prop.getProperty("app.addItemForConsumer.mailer.name");
		String basePath = prop.getProperty("app.base.path");

		String body = null;

		try {
			body = Files.toString(new File(sharedMailer), Charsets.UTF_8);
			body = body.replace("__UserName__", userName);
			body = body.replace("__BASEPATH__", basePath);
			} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}
	
	public static String getMailContenForNewCollectionForConsumer(String userName) {
		Properties prop = CommonUtility.getFromProperty();
		String sharedMailer = null;

		sharedMailer = prop.getProperty("app.addCollectionForConsumer.mailer.name");
		String basePath = prop.getProperty("app.base.path");

		String body = null;

		try {
			body = Files.toString(new File(sharedMailer), Charsets.UTF_8);
			body = body.replace("__UserName__", userName);
			body = body.replace("__BASEPATH__", basePath);
			} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}
	
	public static String getMailContenForNewAppraisalForConsumer(String userName) {
		Properties prop = CommonUtility.getFromProperty();
		String sharedMailer = null;

		sharedMailer = prop.getProperty("app.addAppraisalForConsumer.mailer.name");
		String basePath = prop.getProperty("app.base.path");

		String body = null;

		try {
			body = Files.toString(new File(sharedMailer), Charsets.UTF_8);
			body = body.replace("__UserName__", userName);
			body = body.replace("__BASEPATH__", basePath);
			} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}
	public static String getSharedMailContentForUnRegisteredUser() {
		Properties prop = CommonUtility.getFromProperty();
		String sharedMailer = null;

		sharedMailer = prop.getProperty("app.shareItemUnRegisteredUser.mailer.name");
		String basePath = prop.getProperty("app.base.path");

		String body = null;

		try {
			body = Files.toString(new File(sharedMailer), Charsets.UTF_8);
			body = body.replace("__BASEPATH__", basePath);
			} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}
	
	public static String getUnSharedMailContentForRegisteredUser(String userName) {
		Properties prop = CommonUtility.getFromProperty();
		String sharedMailer = null;

		sharedMailer = prop.getProperty("app.UnshareItemRegisteredUser.mailer.name");
		String basePath = prop.getProperty("app.base.path");

		String body = null;

		try {
			body = Files.toString(new File(sharedMailer), Charsets.UTF_8);
			body = body.replace("__UserName__", userName);
			body = body.replace("__BASEPATH__", basePath);
			} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}
	
	public static String getUnSharedMailContentForUnRegisteredUser() {
		Properties prop = CommonUtility.getFromProperty();
		String sharedMailer = null;

		sharedMailer = prop.getProperty("app.UnshareItemUnRegisteredUser.mailer.name");
		String basePath = prop.getProperty("app.base.path");

		String body = null;

		try {
			body = Files.toString(new File(sharedMailer), Charsets.UTF_8);
			body = body.replace("__BASEPATH__", basePath);
			} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}
	
	public static String getSharedItemAcceptedContent(String userName) {
		Properties prop = CommonUtility.getFromProperty();
		String sharedMailer = null;

		sharedMailer = prop.getProperty("app.accept.shared.item.mailer.name");
		String basePath = prop.getProperty("app.base.path");

		String body = null;

		try {
			body = Files.toString(new File(sharedMailer), Charsets.UTF_8);
			body = body.replace("__UserName__", userName);
			body = body.replace("__BASEPATH__", basePath);
			} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}
	
	public static String getSharedItemRejectedContent(String userName) {
		Properties prop = CommonUtility.getFromProperty();
		String sharedMailer = null;

		sharedMailer = prop.getProperty("app.reject.shared.item.mailer.name");
		String basePath = prop.getProperty("app.base.path");

		String body = null;

		try {
			body = Files.toString(new File(sharedMailer), Charsets.UTF_8);
			body = body.replace("__UserName__", userName);
			body = body.replace("__BASEPATH__", basePath);
			} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}
	
	/**
	 * Method used to send invitation mail to user
	 * @paparam loginBean
	 * @return
	 * @throws Exception
	 * @author Paparamjeet
	 */
	public static String getUserInvitationRegistrationMailContent(LoginBean loginBean) throws Exception{
		Properties prop = CommonUtility.getFromProperty();
		String userInvitationMailer = prop.getProperty("app.invitation.mailer.name");
		String basePath = prop.getProperty("app.base.path");
		
		String body = null;
		if(CommonUserUtility.paparameterNullCheckStringObject(userInvitationMailer) && CommonUserUtility.paparameterNullCheckStringObject(basePath)){
			try {
				body = Files.toString(new File(userInvitationMailer), Charsets.UTF_8);
				body = body.replace("__UserName__", loginBean.getFirstName() + " " + loginBean.getLastName());
				body = body.replace("__BASEPATH__", basePath);
				body = body.replace("__EMAIL__", loginBean.getUserName());
				body = body.replace("__PASSWORD__", loginBean.getPassword());
				body = body.replace("__INVITATIONKEY__", loginBean.getAuthenticationCode());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return body;
	}
	
	/**
	 * Method used to send invitation mail to user
	 * @paparam loginBean
	 * @return
	 * @throws Exception
	 * @author Paparamjeet
	 */
	public static String getUserInvitationRegistrationMailContent(LoginModel loginBean) throws Exception{
		Properties prop = CommonUtility.getFromProperty();
		String userInvitationMailer = prop.getProperty("app.invitation.mailer.name");
		String basePath = prop.getProperty("app.base.path");
		
		String body = null;
		if(CommonUserUtility.paparameterNullCheckStringObject(userInvitationMailer) && CommonUserUtility.paparameterNullCheckStringObject(basePath)){
			try {
				body = Files.toString(new File(userInvitationMailer), Charsets.UTF_8);
				body = body.replace("__UserName__", loginBean.getFirstName() + " " + loginBean.getLastName());
				body = body.replace("__BASEPATH__", basePath);
				body = body.replace("__EMAIL__", loginBean.getUserName());
				body = body.replace("__PASSWORD__", loginBean.getPassword());
				body = body.replace("__INVITATIONKEY__", loginBean.getAuthenticationCode());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return body;
	}
	
	/**
	 * Method used to send invitation mail to user
	 * @paparam loginBean
	 * @return
	 * @throws Exception
	 * @author Paparamjeet
	 * @paparam agentModel 
	 */
	public static String getUserAppraisalAppraisedMailContent(LoginModel loginModel) throws Exception{
		Properties prop = CommonUtility.getFromProperty();
		String mailerName = prop.getProperty("app.appraisal.appraised.mailer.name");
		String basePath = prop.getProperty("app.base.path");
		
		String body = null;
		if(CommonUserUtility.paparameterNullCheckStringObject(mailerName) && CommonUserUtility.paparameterNullCheckStringObject(basePath)){
			try {
				body = Files.toString(new File(mailerName), Charsets.UTF_8);
				body = body.replace("__UserName__", loginModel.getFirstName() + " " + loginModel.getLastName());
				body = body.replace("__BASEPATH__", basePath);
				body = body.replace("__EMAIL__", loginModel.getUserName());
				body = body.replace("__PASSWORD__", loginModel.getPassword());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return body;
	}

	/**
	 * @paparam loginModel
	 * @return
	 * @throws Exception
	 */
	public static String getUserAppraisalUnAppraisedMailContent(
			LoginModel loginModel) throws Exception{
		Properties prop = CommonUtility.getFromProperty();
		String mailerName = prop.getProperty("app.appraisal.unappraised.mailer.name");
		String basePath = prop.getProperty("app.base.path");
		
		String body = null;
		if(CommonUserUtility.paparameterNullCheckStringObject(mailerName) && CommonUserUtility.paparameterNullCheckStringObject(basePath)){
			try {
				body = Files.toString(new File(mailerName), Charsets.UTF_8);
				body = body.replace("__UserName__", loginModel.getFirstName() + " " + loginModel.getLastName());
				body = body.replace("__BASEPATH__", basePath);
				body = body.replace("__EMAIL__", loginModel.getUserName());
				body = body.replace("__PASSWORD__", loginModel.getPassword());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return body;
	}

}
