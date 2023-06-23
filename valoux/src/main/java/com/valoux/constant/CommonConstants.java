/*
 * Copyright (c) 2015, 2016, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.constant;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * This <java>class</java> CommonConstants is use to set some generic
 * CommonConstants.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */

public class CommonConstants {

	// Commom Constants

	public static final Integer STATUS_ACTIVE = 1;

	public static final Integer STATUS_INACTIVE = 2;
	
	public static final Integer STATUS_ACCEPTED = 2;

	public static final SimpleDateFormat DOB_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static final String ADD = "add";

	public static final String UPDATE = "update";
	
	public static final String VIEW = "view";

	public static final String DELETE = "delete";

	public static final Integer SUCCESS = 1;

	public static final Integer ERROR = 2;

	public static final Integer INCOMPLETE = 3;

	public static final Integer EXCEPTION = 4;

	public static final String ERROR_MESSAGE = "Invalid Username or Password.";

	public static final String USER_INACTIVE = "User is inactive.";

	public static final String INVALID_REQUEST = "Invalid Request";

	public static final String INFO_INCOMPLETE = "There is some information missing";
	
	public static final String NO_ITEM_CAN_BE_ADDDED_COLLECTION = "Item cannot be added to Collection";
	
	public static final String NO_ITEM_CANNOT_BE_DELETED_FROM_COLLECTION = "Item cannot be deleted from Collection";
	
	public static final String COLLECTION_CANNOT_BE_UPDATED = "Collection cannot be updated as it is appraised";
	
	public static final String APPRAISAL_CANNOT_BE_UPDATED = "Appraisal cannot be updated as it is appraised";
	
	public static final String NO_ITEM_CAN_BE_ADDDED_APPRAISAL = "Item cannot be added to Appraisal";
	
	public static final String NO_ITEM_AND_COLLECTION_CAN_BE_DELETED = "Item and Collection cannot be deleted from Appraisal";
	
	public static final String NO_COLLECTION_CAN_BE_ADDDED_APPRAISAL = "Collection cannot be added to Appraisal";
	
	public static final String LIST_EMPTY = "No record found";
	
	public static final String REQUEST_PAparam_MISSING = "Request paparameter missing.";
	
	public static final String KEY_MODIFIED_ON = "modifiedOn";
	
	public static final Integer ONE = 1;
	public static final Integer ZERO = 0;
	
	public static final Integer TWO = 2;
	public static final Integer TOP_FIVE = 5;
	
	public static final Double HUNDRED = 100.0;
	
	public static final Double DOUBLE_ZERO = 0d;
	
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

	// Role Constants
	public static final Integer SUPER_ADMIN = 11;

	public static final Integer CONSUMER_ADMIN = 7;

	// Agent Constants
	public static final String AGENT_INFO = "No agent info found for supplied public key";
	
	public static final String AGENT_ACTIVATED = "Agent activated successfully";

	public static final String AGENT_INACTIVATED = "Agent inactivated successfully";
	
	public static final String STORE_ADV_ACTIVATED = "Store Advertisement activated successfully";

	public static final String STORE_ADV_INACTIVATED = "Store Advertisement inactivated successfully";
	
	public static final String AGENT_ERROR_MESSAGE = "There is Something Error.";

	// User Status Constants
	public static final Integer USER_STATUS_ACTIVE = 1;

	public static final Integer USER_STATUS_INACTIVE = 2;
	
	public static final Integer USER_STATUS_INCOMPLETE = 3;
	
	public static final Integer AGENT_STATUS_EMAIL_VERIFIED = 4;

	public static final Integer STATUS = 1;

	// user about Constant Jewelry Purchases

	public static final Integer USER_JEWELLERY_PURCHASES = 1;

	public static final Integer USER_JEWELLERY_INSURANCE = 2;

	public static final Integer USER_JEWELLERY_SERVICE = 3;

	public static final Integer USER_JEWELLERY_DOCUMENTATION = 4;

	// user about Constant Jewelry Purchases

	public static final Integer USER_JEWELLERY_METALS = 1;

	public static final Integer USER_JEWELLERY_GEMSTONES = 2;

	public static final Integer USER_JEWELLERY_DIAMONDS = 3;

	// User Admin Registration Constants
	public static final String REGISTRATION_ERROR_MESSAGE = "There is Something Error.";

	public static final String INVALID_EMAIL_ADDRESS = "Email address doesn't exist, Please enter valid email address.";

	public static final String REGISTRATION_INCOMPLETE_MESSAGE = "There is Something missing";

	public static final String REGISTRATION_COMPLETE_MESSAGE = "Registration has been completed successfully.";
	
	public static final String REGISTRATION_DONE_MESSAGE = "Registration already done.";

	public static final String ADV_IMAGE_ADDED = "Advertisement saved successfully";
	
	public static final String ADV_IMAGE_UPDATED = "Advertisement updated successfully";
	
	public static final String ADV_IMAGE_DELETED = "Advertisement saved deleted";
	
	public static final String STORES_MERGED = "Stores Merged";
	
	public static final String UPDATE_COMPLETE = "Details is updated successfully";

	public static final String IMAGE_CHANGED = "Profile Image is changed successfully";

	public static final String EMAIL_ID_ALREADY_EXIST = "Email Id already exist";

	public static final String CHANGE_PASSWORD_ERROR_MESSAGE = "Password not changed";

	public static final String CHANGE_PASSWORD_SUCCESS_MESSAGE = "Password changed successfully";

	public static final String INFO_ERROR_MESSAGE = "There is Some Error Information cannot be retrieved.";

	public static final String SUCCCESS_MESSSAGE = "Profile Updated Successfully";

	public static final String FAILURE_MESSSAGE = "Profile not updated";

	public static final String ACCEPT_OR_REJECT_SHARE_MESSAGE = "Accept or Reject request is successful";

	// OTP Constants
	public static final String INVALID_OTP = "Your OTP is invalid";

	public static final String INVALID_INVALID_ACTIVATION_KEY = "Invalid activation key !! Please try or contact your system administrator";

	// Email Constants
	public static final String SIGN_UP_MAIL = "admin@valoux.com";

	public static final String CONSUMER_BODY = "Welcome to Valoux! activate your account now";
	
	public static final String CONSUMER_INVITE_BODY = "You are invited to Valoux! as a consumer";
	
	public static final String APPRAISAL_APPRAISED_BODY = "Your appraisal has been appraised.";
	
	public static final String APPRAISAL_UNAPPRAISED_BODY = "Your appraisal has been unappraised.";

	public static final String ITEM_SHARED_BODY = "Item is Shared with you";
	
	public static final String ITEM_ADDED_BODY = "Item is added for you";
	
	public static final String COLLECTION_ADDED_BODY = "Collection is added for you";
	
	public static final String APPRAISAL_ADDED_BODY = "Appraisal is added for you";

	public static final String ITEM_UNSHARED_BODY = "Item is unshared with you";
	
	public static final String ITEM_ACCEPT_BODY = "Item shared by you is accepted";
	
	public static final String ITEM_REJECT_BODY = "Item shared by you is rejected";

	public static final String RESET_PASSWORD = "Reset Your password";

	public static final String AGENT_REGISTER = "New Store Registered";

	public static final String WELCOME = "Welcome to Valoux!";

	public static final String STORE_PHONE = "There is no phone number listed here";

	public static final String STORE_LIST_EMPTY = "No store found";
	
	public static final String STORE_NOT_FOUND = "Store Not Found";

	public static final String STORE_EXIST = "Store is alredy exist";

	public static final String STORE_ACTIVATED = "Store activated successfully";

	public static final String STORE_INACTIVATED = "Store inactivated successfully";

	public static final String EMAIL_ERROR_MESSAGE = "Email address does not exist.";

	public static final String SHARED_REQUEST_SENDED = "Shared request sended";

	public static final String UNSHARED_REQUEST_SENDED = "Item is unshared";

	// Items Constants

	public static final String ITEM_ERROR_MESSAGE = "There is Something Error.";

	public static final String ITEM_INFO_INCOMPLETE = "There is some information missing";

	public static final String ITEM_NAME_EXIST = "Item name exist for user";

	public static final String ITEM_NAME_NOTEXIST = "Item name not exist for user";

	public static final String IMAGE_DELETED_SUCCESSFULLY = "Image deleted successfully";
	
	public static final String ITEM_DELETED_SUCCESSFULLY = "Item deleted successfully";
	
	public static final String CONSUMER_DELETED_SUCCESSFULLY = "Consumer deleted successfully";
	
	public static final String AGENT_DELETED_SUCCESSFULLY = "Agent deleted successfully";
	
	public static final String COLLECTION_DELETED_SUCCESSFULLY = "Collection deleted successfully";
	
	public static final String APPRAISAL_DELETED_SUCCESSFULLY = "Appraisal deleted successfully";

	public static final String ITEM_ADDED_SUCCESSFULL = "Item Added Successfully";

	public static final String ITEM_UPDATED_SUCCESSFULL = "Item Updated Successfully";
	
	public static final String ITEM_METAL_PRICE_UPDATED_SUCCESSFULL = "Item Metal Price Updated Successfully.";
	
	public static final String ITEM_MARKET_PRICE_UPDATED_SUCCESSFULL = "Item Market Price Updated Successfully.";

	public static final String ITEMS_NOT_FOUND = "Item not found. Please add.";

	public static final String ITEM_COMPONENTS_ADDED_SUCCESSFULL = "Item components added successfully";

	public static final String ITEM_COMPONENTS_UPDATED_SUCCESSFULL = "Item component updated successfully";

	public static final String ITEM_COMPONENTS_DELETED_SUCCESSFULL = "Item component deleted successfully";
	
	public static final Integer STATUS_REQUESTED = 1;
	
	public static final Integer STATUS_REJECTED = 3;

	// forget password
	public static final String WRONG_ANSWER = "Please enter the correct answer";

	public static final String RIGHT_ANSWER = "Password reset link is sended on email please reset the password";

	public static final String PASSWORD_UPDATED_SUCCESSFULLY = "Password updated successfully";

	// Collections Constants
	public static final String COLLECTION_ERROR_MESSAGE = "There is Something Error.";

	public static final String COLLECTION_ADDED_SUCCESSFULL = "Collection created successfully";

	public static final String COLLECTION_UPDATED_SUCCESSFULL = "Collection updated successfully";

	public static final String ITEM_ADDEDD_IN_COLLECTION = "Item added in collection successfully";

	public static final String ITEM_ALREADY_ADDEDD_IN_COLLECTION = "Item already added in collection";

	public static final String ITEM_DELETE_IN_COLLECTION = "Item deleted from collection";

	public static final String ITEM_NOT_FOUND_IN_COLLECTION = "Item not found in collection";

	public static final String COLLECTION_NAME_NOT_EXIST = "Collection name not exist for user.";

	public static final String COLLECTION_NAME_EXIST = "Collection name exist for user.";

	public static final String COLLECTION_NOT_FOUND = "Collection not found. Please add.";

	// Create Appraisal Constants
	public static final String APPRAISAL_ERROR_MESSAGE = "There is Something Error.";

	public static final String APPRAISAL_COMPLETE_MESSAGE = "Appraisal has been created successfully.";

	public static final String APPRAISAL_UPDATE_COMPLETE_MESSAGE = "Appraisal has been updated successfully.";
	
	public static final String APPRAISAL_APPRAISED_INCOMPLETE_MESSAGE = "Appraisal items final price missing.";
	
	public static final String APPRAISAL_APPRAISED_COMPLETE_MESSAGE = "Appraisal has been appraised successfully.";
	
	public static final String APPRAISAL_UNAPPRAISED_COMPLETE_MESSAGE = "Appraisal has been unappraised successfully.";
	
	public static final String APPRAISAL_APPRAISED_ERROR_MESSAGE = "Appraisal not appriased yet.";
	
	public static final String APPRAISAL_APPRAISED_PDF_MESSAGE = "Appraisal pdf download successfully.";
	
	public static final String APPRAISAL_INVALID_MESSAGE = "Either appraisal missing or not appraised yet.";

	public static final String APPRAISAL_COMPLETE_MESSAGE_ITEMS = "Appraisal has been created successfully for items.";

	public static final String APPRAISAL_COMPLETE_MESSAGE_ITEMS_UPDATE = "Appraisal has been updated successfully for items.";

	public static final String APPRAISAL_COMPLETE_MESSAGE_COLLECTION = "Appraisal has been created successfully for collection.";

	public static final String APPRAISAL_COMPLETE_MESSAGE_COLLECTION_UPDATE = "Appraisal has been updated successfully for collection.";

	public static final String APPRAISAL_COMPLETE_MESSAGE_FOR_ITEM_OR_COLLECTION = "Appraisal has been created successfully for items and collection.";

	public static final String APPRAISAL_COMPLETE_MESSAGE_FOR_ITEM_OR_COLLECTION_UPDATE = "Appraisal has been updated successfully for items and collection.";
	
	public static final String APPRAISALS_NOT_FOUND = "Appraisals not found.";

	public static final Integer APPRAISAL_STATUS_ACTIVE = 1;

	public static final Integer APPRAISAL_STATUS_INACTIVE = 2;

	public static final Integer APPRAISAL_STATUS_APPROVED = 3;
	
	public static final Integer PERCENTAGE_INC = 1;

	public static final Integer PERCENTAGE_DEC = 2;

	public static final Integer PERCENTAGE_NO_CHANGE = 3;

	public static final Integer APPRAISAL_STATUS_DIS_APPROVED = 4;

	public static final String APPRAISAL_ITEM_EXISTING_MESSAGE = "This item is already exist";

	public static final String APPRAISAL_COLLECTION_EXISTING_MESSAGE = "This collection is already exist";

	public static final String APPRAISAL_ALREADY_EXIST = "Appraisal is already exist";

	public static final String APPRAISAL_ALREADY_APPROVED = "Appraisal is already approved";

	public static final String APPRAISAL_APPROVED_MESSAGE = "Appraisal has been approved successfully.";

	public static final String APPRAISAL_DIS_APPROVED_MESSAGE = "Appraisal has been disapproved successfully.";

	public static final String APPRAISAL_NOT_FOUND = "Appraisal not found. Please add.";
	
	public static final String CONSUMER_NOT_FOUND = "Consumer not found. Please add.";
	
	public static final String AGENT_NOT_FOUND = "Agent not found. Please add.";

	public static final String APPRAISAL_INFO_INCOMPLETE = "There is some information missing";

	public static final String APPRAISAL_NAME_NOT_EXIST = "Appraisal name not exist for user";

	public static final String APPRAISAL_NAME_EXIST = "Appraisal name exist for user";

	public static final String APPRAISAL_ITEMS_NOT_FOUND = "Items not found. Please add.";

	public static final String APPRAISAL_ITEM_OR_COLLECTION_NOT_FOUND = "Item or collection not found. Please add.";

	public static final String APPRAISAL_DELETE_FROM_COLLECTION = "Appraisal deleted from collection.";

	public static final String APPRAISAL_NOT_FOUND_IN_COLLECTION = "Appraisal not found in collection.";

	public static final String APPRAISAL_COLLECTION_DELETE_IN_APPRAISAL = "Item deleted from appraisal";

	public static final String APPRAISAL_COLLECTION_NOT_FOUND_IN_APPRAISAL = "Item not found in appraisal";

	public static final String APPRAISAL_COLLECTION_ITEM_DELETE_IN_APPRAISAL = "Collection and item deleted from appraisal";
	
	public static final String APPRAISAL_ALREADY_APPRAISED = "Appraisal already appraised and cannot be edited.";
	
	public static final String APPRAISAL_ALREADY_APPRAISED_ONDELETE = "Appraisal already appraised and cannot be deleted.";
	
	public static final String COLLECTION_ALREADY_APPRAISED = "Collection already appraised and cannot be edited.";
	
	public static final String ITEM_ALREADY_APPRAISED = "Item already appraised and cannot be edited.";
	
	public static final String ITEM_ALREADY_APPRAISED_ONDELETE = "Item already appraised and cannot be deleted.";
	
	public static final String COLLECTION_ALREADY_APPRAISED_ONDELETE = "Collection already appraised and cannot be deleted.";
	
	public static final String ITEM_NOT_FOUND = "Item not found.";
	
	public static final String ITEM_COMPONENET_ALREADY_APPRAISED = "Item already appraised and so only price can be edited.";
	
	public static final String STORE_UPDATED_SUCCESSFULLY = "Store is updated successfully.";

	// Components Constants
	public static final Integer COMPONENT_DIAMOND = 1;

	public static final Integer COMPONENT_GEMSTONES = 2;

	public static final Integer COMPONENT_PEARLS = 3;

	public static final Integer COMPONENT_METALS = 4;

	public static final String AGENT = "agent";

	public static final String CONSUMER = "consumer";

	public static final Integer COMPONENT_PHOTO = 1;

	public static final Integer COMPONENT_RECEIPT = 2;

	public static final Integer COMPONENT_CERTIFICATE = 3;
	
	public static final Integer COMPONENT_INCLUSION_INTERNAL = 1;
	
	public static final Integer COMPONENT_INCLUSION_EXTERNAL = 2;
	
	public static final String ITEM_COMPONENT_ADDED_SUCCESSFULL = "Item component properties added successfully";
	
	public static final String ITEM_COMPONENT_UPDATED_SUCCESSFULL = "Item component properties updated successfully";
	
	public static final Double PURE_CARAT = 24.0;
	
	public static final Double CONVERSION_CALC = 31.1035;
	
	public static final Double DUMMY_APPRAISED_ITEM_PRICE = 10000.00;
	public static final Double DUMMY_APPRAISED_COLLECTION_PRICE = 15000.00;
	public static final Double DUMMY_APPRAISED_PRICE = 20000.00;
	
	public static final Integer  METAL_GOLD_ID = 1;
	public static final Integer  METAL_SILVER_ID = 2;
	public static final Integer  METAL_PLATINUM_ID = 3;
	public static final Integer  METAL_RHODIUM_ID = 4;
	public static final Integer  METAL_PALLADIUM_ID = 5;
	public static final Integer  METAL_TITANIUM_ID = 6;
	public static final Integer  METAL_TUNGSTEN_ID = 7;
	
	public static final String  CUT_ROUNDS = "Rounds";
	public static final String  SHAPE_ROUND = "round";
	public static final String  CUT_FANCIES = "Fancies";
	
	public static final String  QUANDL_API = "quandl";
	public static final String  PRICE_UNIT = "troy oz";
	
	// Share Constants
	
	public static final Integer SHARED_TYPE_ITEM = 1;
	
	public static final Integer SHARED_TYPE_COLLECTION = 2;
	
	public static final Integer SHARED_TYPE_APPRAISAL = 3;
	
	public static final String ASC = "asc";

	public static final String DESC = "desc";
	
	public static final String DIAMOND_PROPIMG = "PROPIMG";
	
	public static final String DIAMOND_PLOTIMG = "PLOTIMG";
	
	public static final String DIAMOND_ERPTIMG = "ERPTIMG";
	public static final String DIAMOND_CERTIFICATE = "CERTIFICATE";
	
	public static final String EXTENSION_JPG = ".jpg";
	public static final String EXTENSION_PDF = ".pdf";


}
