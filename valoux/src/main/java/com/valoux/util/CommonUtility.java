/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.valoux.enums.ItemComponents.Components;
import com.valoux.model.ValouxStoreAdvertisementModel;

/*import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;*/

/**
 * This <java>class</java> responds all the requests related to CommonUtility.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

public class CommonUtility {
	public static final Logger LOG = Logger.getLogger(CommonUtility.class);
	private static final String ALGO = "AES";
	private static final byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't',
			'K', 'e', 'y' };

	/**
	 * This method encryptedPassword using MD5.
	 */
	public static String encryptedPassword(String password) throws NoSuchAlgorithmException {
		try {
			StringBuffer hexString = new StringBuffer();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte byteData[] = md.digest();

			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * This method decodeImage decode the image string into Base64.
	 * 
	 */
	public static byte[] decodeDoc(String docDataString) {
		return Base64.decodeBase64(docDataString);
	}

	/**
	 * This method convert string into Date.
	 * 
	 */
	public static Date convertUIStringToDate(String dateStr) {
		Date date = null;
		try {
			DateFormat userDateFormat = new SimpleDateFormat("MM/dd/yyyy");
			date = userDateFormat.parse(dateStr);
			date = userDateFormat.parse(new SimpleDateFormat("MM/dd/yyyy").format(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * This method get current date and time.
	 * 
	 */
	public static Date getDateAndTime() {
		Date date = new Date();

		return date;

	}

	/**
	 * This method generate Random no for relation key.
	 * 
	 */
	public static long generateRandom(int length) {

		Random random = new Random();
		char[] digits = new char[length];
		digits[0] = (char) (random.nextInt(9) + '1');
		for (int i = 1; i < length; i++) {
			digits[i] = (char) (random.nextInt(10) + '0');
		}
		return Long.parseLong(new String(digits));
	}

	/**
	 * This method generatetToken randomly.
	 * 
	 */
	public static String generatetToken() {
		String token = null;
		try {
			char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
			char[] num = "1234567890".toCharArray();
			StringBuilder sb = new StringBuilder();
			Random random = new Random();
			for (int i = 0; i < 6; i++) {
				char c = chars[random.nextInt(chars.length)];
				sb.append(c);
			}

			for (int i = 0; i < 9; i++) {
				char n = num[random.nextInt(num.length)];
				sb.append(n);
			}
			token = sb.toString();
			LOG.info("Generated Token" + token);
		} catch (Exception e) {

			e.printStackTrace();

		}
		return token;

	}

	/**
	 * This method encrypt data using BASE64.
	 */
	/*
	 * public static String encrypt(String Data) throws Exception { Key key =
	 * generateKey(); Cipher c = Cipher.getInstance(ALGO);
	 * c.init(Cipher.ENCRYPT_MODE, key); byte[] encVal = c.doFinal(Data.getBytes());
	 * String encryptedValue = new BASE64Encoder().encode(encVal); return
	 * encryptedValue; }
	 * 
	 *//**
		 * This method decrypt data using BASE64.
		 *//*
			 * public static String decrypt(String encryptedData) throws Exception { Key key
			 * = generateKey(); Cipher c = Cipher.getInstance(ALGO);
			 * c.init(Cipher.DECRYPT_MODE, key); byte[] decordedValue = new
			 * BASE64Decoder().decodeBuffer(encryptedData); byte[] decValue =
			 * c.doFinal(decordedValue); String decryptedValue = new String(decValue);
			 * return decryptedValue; }
			 */

	/**
	 * This method generateKey data using BASE64.
	 */
	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}

	/**
	 * This method get Property file.
	 */
	public static Properties getFromProperty() {
		String resourceName = "valouxMail.properties"; // could also be a
														// constant
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties props = new Properties();
		try {
			InputStream resourceStream = loader.getResourceAsStream(resourceName);
			props.load(resourceStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return props;

	}

	/**
	 * This method save Document In Directory.
	 * 
	 * @throws Exception
	 */
	public static String saveDocumentInDirectory(String docContent, String docName, String fileNamePrefix,
			Integer userId) throws Exception {
		String profilePath = null;

		String valouxPropertyFileName = "valoux.properties";
		Properties prop = new Properties();
		prop = CommonUtility.getProperty(valouxPropertyFileName);
		String extension = CommonUtility.getDocumentExtension(docName);
		String responsePath = prop.getProperty("valoux.image.respons.path");
		long timeStamp = getTimeStamp();
		profilePath = fileNamePrefix + userId + "_" + timeStamp + "." + extension;
		String docPath = responsePath + profilePath;
		File file = new File(prop.getProperty("valoux.image.path"));
		if (!file.exists()) {
			file.mkdir();
		}
		byte[] docByteArray = decodeDoc(docContent);

		// Write a image byte array into file system
		FileOutputStream docOutFile = new FileOutputStream(docPath);
		docOutFile.write(docByteArray);
		docOutFile.close();
		// System.out.println("Image Successfully Manipulated!");
		LOG.info("document Successfully Manipulated!");
		return profilePath;
	}

	/**
	 * This method save Document In Directory.
	 * 
	 * @throws Exception
	 */
	public static String saveAdvImage(String docContent, String docName, String fileNamePrefix, Integer userId,
			ValouxStoreAdvertisementModel storeAdvModel) throws Exception {
		String profilePath = null;

		String valouxPropertyFileName = "valoux.properties";
		Properties prop = new Properties();
		prop = CommonUtility.getProperty(valouxPropertyFileName);
		String extension = CommonUtility.getDocumentExtension(docName);
		String responsePath = prop.getProperty("valoux.advImage.respons.path");
		storeAdvModel.setImgPath(responsePath);
		long timeStamp = getTimeStamp();
		profilePath = fileNamePrefix + userId + "_" + timeStamp + "." + extension;
		String docPath = responsePath + profilePath;
		File file = new File(prop.getProperty("valoux.advImage.path"));
		if (!file.exists()) {
			file.mkdir();
		}
		byte[] docByteArray = decodeDoc(docContent);

		// Write a image byte array into file system
		FileOutputStream docOutFile = new FileOutputStream(docPath);
		docOutFile.write(docByteArray);
		docOutFile.close();
		// System.out.println("Image Successfully Manipulated!");
		LOG.info("adv image Successfully Manipulated!");
		return profilePath;
	}

	/**
	 * This method get current timestamp
	 */
	public static long getTimeStamp() {
		return System.currentTimeMillis();
	}

	/**
	 * This method save Consumer Image.
	 * 
	 * @throws Exception
	 */
	public static String saveConsumerImage(String imageContent, String imageName, String imageNamePrefix, String userId)
			throws Exception {
		String profilePath = null;

		String valouxPropertyFileName = "valoux.properties";
		Properties prop = new Properties();
		prop = CommonUtility.getProperty(valouxPropertyFileName);
		String extension = CommonUtility.getDocumentExtension(imageName);
		String responsePath = prop.getProperty("valoux.consumerImage.respons.path");
		long timeStamp = getTimeStamp();
		profilePath = imageNamePrefix + userId + "_" + timeStamp + "." + extension;
		String docPath = responsePath + profilePath;
		File file = new File(prop.getProperty("valoux.consumerImage.path"));
		if (!file.exists()) {
			file.mkdir();
		}
		byte[] docByteArray = decodeDoc(imageContent);

		// Write a image byte array into file system
		FileOutputStream docOutFile = new FileOutputStream(docPath);
		docOutFile.write(docByteArray);
		docOutFile.close();
		// System.out.println("Image Successfully Manipulated!");
		LOG.info("saveConsumerImage:image Successfully Manipulated!");
		return profilePath;
	}

	/**
	 * This method delete Document In Directory.
	 */
	public static String deleteDocumentInDirectory(String docName) throws FileNotFoundException, IOException {
		String profilePath = null;

		String valouxPropertyFileName = "valoux.properties";
		Properties prop = new Properties();
		prop = CommonUtility.getProperty(valouxPropertyFileName);
		String responsePath = prop.getProperty("valoux.image.respons.path");
		String docPath = responsePath + docName;
		File file = new File(prop.getProperty("valoux.image.path"));
		if (!file.exists()) {
			return null;
		}
		File fileToDelete = new File(docPath);
		fileToDelete.delete();
		LOG.info("document deletedd successfully!");
		return profilePath;
	}

	/**
	 * This method delete Document In Directory.
	 */
	public static String deleteStoreAdvImageInDirectory(String docName) throws FileNotFoundException, IOException {
		String profilePath = null;

		String valouxPropertyFileName = "valoux.properties";
		Properties prop = new Properties();
		prop = CommonUtility.getProperty(valouxPropertyFileName);
		String responsePath = prop.getProperty("valoux.advImage.respons.path");
		String docPath = responsePath + docName;
		File file = new File(prop.getProperty("valoux.advImage.path"));
		if (!file.exists()) {
			return null;
		}
		File fileToDelete = new File(docPath);
		fileToDelete.delete();
		LOG.info("document deletedd successfully!");
		return profilePath;
	}

	/**
	 * This method delete Consumer Image In Directory.
	 */
	public static String deleteConsumerImageInDirectory(String imageName) throws FileNotFoundException, IOException {
		String profilePath = null;

		String valouxPropertyFileName = "valoux.properties";
		Properties prop = new Properties();
		prop = CommonUtility.getProperty(valouxPropertyFileName);
		String responsePath = prop.getProperty("valoux.consumerImage.respons.path");
		String docPath = responsePath + imageName;
		File file = new File(prop.getProperty("valoux.consumerImage.path"));
		if (!file.exists()) {
			return null;
		}
		File fileToDelete = new File(docPath);
		fileToDelete.delete();
		LOG.info("image deleted successfully!");
		return profilePath;
	}

	/**
	 * This method getFromProperty get Property from valoux.properties.
	 * 
	 */
	public static Properties getProperty(String fileName) {
		// String resourceName = "imd.properties"; // could also be a constant
		String resourceName = fileName;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties props = new Properties();
		try {
			InputStream resourceStream = loader.getResourceAsStream(resourceName);
			props.load(resourceStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return props;

	}

	/**
	 * This method return image extension from image name .
	 * 
	 */

	public static String getDocumentExtension(String docName) throws Exception {

		if (docName != null) {
			String doc = new String(docName);
			String temp = doc.toLowerCase();

			if (temp.endsWith(".png"))
				return "png";
			else if (temp.endsWith(".gif"))
				return "gif";
			else if (temp.endsWith(".jpg"))
				return "jpg";
			else if (temp.endsWith(".jpeg"))
				return "jpeg";
			else if (temp.endsWith(".pdf"))
				return "pdf";
			else if (temp.endsWith(".docx"))
				return "docx";
			else if (temp.endsWith(".doc"))
				return "doc";
			else if (temp.endsWith(".txt"))
				return "txt";
			else if (temp.endsWith(".tif"))
				return "tif";
		}
		return "jpg";
	}

	/**
	 * Method for fetching item component type name
	 * 
	 * @paparam componentsType
	 * @return
	 */
	public static String getItemComponentTypeName(int componentsType) {

		String name = "";

		try {
			switch (componentsType) {
			case 1:
				name = Components.Diamonds.toString();
				break;
			case 2:
				name = Components.Gemstones.toString();
				break;
			case 3:
				name = Components.Pearls.toString();
				break;
			case 4:
				name = Components.Metals.toString();
				break;
			default:
				name = "Unknown Type";
				throw new IllegalArgumentException("No Enum specified for this component type -" + componentsType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}

	/**
	 * This method save Document In Directory.
	 */
	public static String saveSignDocumentInDirectory(String docContent, String docName, String fileNamePrefix)
			throws Exception {
		String profilePath = null;

		String valouxPropertyFileName = "valoux.properties";
		Properties prop = new Properties();
		prop = CommonUtility.getProperty(valouxPropertyFileName);
		String extension = CommonUtility.getDocumentExtension(docName);
		String responsePath = prop.getProperty("valoux.agentSign.path");
		long timeStamp = getTimeStamp();
		profilePath = fileNamePrefix + "_" + timeStamp + "." + extension;
		String docPath = responsePath + profilePath;
		File file = new File(prop.getProperty("valoux.agentSign.path"));
		if (!file.exists()) {
			file.mkdir();
		}
		byte[] docByteArray = decodeDoc(docContent);

		// Write a image byte array into file system
		FileOutputStream docOutFile = new FileOutputStream(docPath);
		docOutFile.write(docByteArray);
		docOutFile.close();
		// System.out.println("Image Successfully Manipulated!");
		LOG.info("document Successfully Manipulated!");
		return profilePath;
	}

	/**
	 * This method delete Document In Directory.
	 */
	public static String deleteAgentSignDocumentFromDirectory(String docName)
			throws FileNotFoundException, IOException {
		String profilePath = null;

		String valouxPropertyFileName = "valoux.properties";
		Properties prop = new Properties();
		prop = CommonUtility.getProperty(valouxPropertyFileName);
		String responsePath = prop.getProperty("valoux.agentSign.path");
		String docPath = responsePath + docName;
		File file = new File(prop.getProperty("valoux.agentSign.path"));
		if (!file.exists()) {
			return null;
		}
		File fileToDelete = new File(docPath);
		fileToDelete.delete();
		LOG.info("document deleted successfully!");
		return profilePath;
	}
}
