/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * This <java>class</java> MailApi having configuration regarding mailApi.
 * 
 * @author Mayank Jain
 * 
 */

public class MailApi {
	
	private static final Logger LOGGER = Logger.getLogger(MailApi.class);
	static Properties prop = new Properties();

	public static void SendMail(String email, String from, String sub, String msg, int role) throws IOException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.host", "smtp.sendgrid.net");
		props.put("mail.smtp.port", "2525");
		
		prop = CommonUtility.getFromProperty();
		final String username = prop.getProperty("app.valoux.mail.username");
		final String password = prop.getProperty("app.valoux.mail.password");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			String basePath = prop.getProperty("app.base.path");
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			if (role == 1 || role == 2) {

				String signupMailer = prop.getProperty("app.signup.mailer.name");
				String body = Files.toString(new File(signupMailer), Charsets.UTF_8);
				body = msg;
				body = body.replace("__BASEPATH__", basePath);
				message.setSubject(sub);

				Multipart multipart = new MimeMultipart();

				BodyPart htmlPart = new MimeBodyPart();

				htmlPart.setContent(body, "text/html");
				htmlPart.setDisposition(BodyPart.INLINE);
				multipart.addBodyPart(htmlPart);

				message.setContent(multipart);

			} else if (role == 3) {
				prop = CommonUtility.getFromProperty();
				String forgetMailer = prop.getProperty("app.forget.mailer.name");
				String body = Files.toString(new File(forgetMailer), Charsets.UTF_8);
				body = body.replace("__BASEPATH__", basePath);
				message.setSubject("Reset Your TheApp Password");

				Multipart multipart = new MimeMultipart();

				BodyPart htmlPart = new MimeBodyPart();

				htmlPart.setContent(body, "text/html");
				htmlPart.setDisposition(BodyPart.INLINE);
				multipart.addBodyPart(htmlPart);

				message.setContent(multipart);

			}

			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
			LOGGER.error("Error - ", e);
		}

	}
}