/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.util;

/*<dependency>
 <groupId>com.twilio.sdk</groupId>
 <artifactId>twilio-java-sdk</artifactId>
 <version>3.4.5</version>
 </dependency>
 <dependency>
 <groupId>org.codehaus.jackson</groupId>
 <artifactId>jackson-core-asl</artifactId>
 <version>1.9.13</version>
 </dependency>*/

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This <java>class</java> SendOTPSms having configuration regarding otp.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
public class SendOTPSms {

	// Find your Account Sid and Token at twilio.com/user/account
	//public static final String ACCOUNT_SID = "ACe29da4f696b9324d190b0e7f092cbe8a";
	//public static final String AUTH_TOKEN = "2cd840b93c2abab23df9175a8e00c4dd";
	
	private static final Logger LOGGER = Logger.getLogger(SendOTPSms.class);

	public static synchronized boolean sendOTP(String otp, String phnno) throws Exception {
		
		LOGGER.info("Enter Method sendOTP of SendOTPSms");
		
		try {
			String valouxPropertyFileName = "valoux.properties";
			Properties prop = new Properties();
			prop = CommonUtility.getProperty(valouxPropertyFileName);
		
			final String ACCOUNT_SID = prop.getProperty("valoux.account.sid");
			final String AUTH_TOKEN = prop.getProperty("valoux.account.token");
			final String PHONE_NO = prop.getProperty("valoux.account.from.phone.no");
			
			TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

			// Build a filter for the MessageList
			List<NameValuePair> paparams = new ArrayList<NameValuePair>();
			paparams.add(new BasicNameValuePair("Body", otp));
			paparams.add(new BasicNameValuePair("To", phnno));
			paparams.add(new BasicNameValuePair("From", PHONE_NO));

			MessageFactory messageFactory = client.getAccount().getMessageFactory();
			Message message = messageFactory.create(paparams);
			/*if(message != null) {
				System.out.println(message.getSid());
				LOGGER.info("<<<<<Message>>from>>>sendOTP>>>>"+message.getSid());
			} else {
				return false;
			}*/
		} catch (Exception e){
			LOGGER.error("Error - ", e);
			return false;
		}
		
		LOGGER.info("Exit Method sendOTP of SendOTPSms");
		return true;
	}
}