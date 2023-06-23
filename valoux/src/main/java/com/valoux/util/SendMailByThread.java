/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

import org.apache.log4j.Logger;

/**
 * This <java>class</java> MailApi SendMailByThread configuration regarding
 * mailApi.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

public class SendMailByThread implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(SendMailByThread.class);

	private Transport tr;
	private Message message;

	public void run() {
		try {
			LOGGER.info("thread is running...");
			tr.send(message);
			tr.close();
			LOGGER.info("thread is closed...");
		} catch (MessagingException e) {
			e.printStackTrace();
			LOGGER.error(e);
		}

	}

	public void setPaparameters(Transport tr, Message message) {
		this.tr = tr;
		this.message = message;

	}
}
