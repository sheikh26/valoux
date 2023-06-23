/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.webservices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.json.XML;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.stereotype.Component;

import com.ngs.model.reportcheck.service.ReportCheckWS_PortType;
import com.valoux.constant.CommonConstants;
import com.valoux.helper.ItemHelper;
import com.valoux.model.ValouxComponentsImageModel;
import com.valoux.service.ItemService;
import com.valoux.util.CommonUserUtility;
import com.valoux.util.CommonUtility;
import com.valoux.util.JSONUtility;

/**
 * 
 * @author Shravan Ray
 * 
 */

@Path("reportCheck")
@Component
public class ReportCheckWS {

	@Autowired
	ReportCheckWS_PortType reportCheckWS_PortType;
	
	@Autowired
	ItemService itemService;

	private static final Logger LOGGER = Logger.getLogger(ReportCheckWS.class);

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/reportCheckWebServiceClient.csv")
	public Response reportCheckWebServiceClient(JSONObject requestJson) {
		LOGGER.info("ReportCheckWS : Enter reportCheckWebServiceClient");
		JSONObject responseJSON = new JSONObject();
		JSONObject json = new JSONObject();

		try {
			JSONObject reportDetail = requestJson.getJSONObject("reqPaparam");
			
			String reportNo = JSONUtility.getSafeString(reportDetail, "reportNo");
			String reportWeight = JSONUtility.getSafeString(reportDetail, "reportWeight");
			String valouxPropertyFileName = "valoux.properties";
			Properties prop = new Properties();
			prop = CommonUtility.getProperty(valouxPropertyFileName);
		
			final String ipAddress = prop.getProperty("valoux.server.ip.address");
			
			if (reportNo == null || reportWeight == null || ipAddress == null) {
				responseJSON.put("errorMessage", CommonConstants.REQUEST_PAparam_MISSING);
				json.put("resData", responseJSON);
				json.put("sCode", "2");
				return Response.status(200).entity(json.toString()).build();
			}
			
			JSONObject reqPaparamWithInRequest = new JSONObject();
			JSONObject REPORT_CHECK_REQUEST = new JSONObject();
			JSONObject HEADER = new JSONObject();
			HEADER.put("IP_ADDRESS", ipAddress);
			REPORT_CHECK_REQUEST.put("HEADER", HEADER);
			
			JSONObject BODY = new JSONObject();
			JSONObject REPORT_DTLS = new JSONObject();
			JSONObject REPORT_DTL = new JSONObject();
			REPORT_DTL.put("REPORT_NO", reportNo);
			REPORT_DTL.put("REPORT_WEIGHT", reportWeight);
			REPORT_DTLS.put("REPORT_DTL", REPORT_DTL);
			BODY.put("REPORT_DTLS", REPORT_DTLS);
			REPORT_CHECK_REQUEST.put("BODY", BODY);
			
			reqPaparamWithInRequest.put("REPORT_CHECK_REQUEST", REPORT_CHECK_REQUEST);
			
			org.json.JSONObject inputJson = new org.json.JSONObject(reqPaparamWithInRequest.toString());

			String Input_XML_String = XML.toString(inputJson);

			String output_XML = null;
			output_XML = reportCheckWS_PortType.processRequest(Input_XML_String);
			if (output_XML != null) {
				org.json.JSONObject xmlJSONObj = XML.toJSONObject(output_XML);
				responseJSON = new JSONObject(xmlJSONObj.toString());
				json.put("resData", responseJSON);
				json.put("sCode", CommonConstants.SUCCESS);
			} else {
				responseJSON.put("errorMessage", CommonConstants.LIST_EMPTY);
				json.put("resData", responseJSON);
				json.put("sCode", CommonConstants.ERROR);
			}
		} catch (Exception e) {
			LOGGER.error("Error - ", e);
			try {
				responseJSON.put("errorMessage", e);
				json.put("resData", responseJSON);
				json.put("sCode", CommonConstants.ERROR);
			} catch (JSONException JSONException) {
				JSONException.printStackTrace();
			}
		}
		LOGGER.info("ReportCheckWS : Exit reportCheckWebServiceClient");
		return Response.status(200).entity(json).build();
	}
	
	/**
	 * @paparam valouxComponentsImageModel
	 * @paparam reportDetail
	 * @paparam imageName
	 * @paparam imagePath
	 * @paparam fileType 
	 * @throws Exception
	 */
	private void populateValouxComponentsImageModel(
			List<ValouxComponentsImageModel> valouxComponentsImageModel,
			JSONObject reportDetail, String imageName, String imagePath, Integer fileType) throws Exception{
		
		LOGGER.info("ReportCheckWS : Enter Method populateValouxComponentsImageModel");
		
		ValouxComponentsImageModel componentsImageModel = new ValouxComponentsImageModel();

		componentsImageModel.setCreatedBy(JSONUtility.getSafeString(reportDetail, "userPublicKey"));
		componentsImageModel.setCreatedOn(CommonUtility.getDateAndTime());
		componentsImageModel.setFileType(fileType);
		componentsImageModel.setImgName(imageName);
		componentsImageModel.setImgStatus(CommonConstants.STATUS_ACTIVE);
		componentsImageModel.setImgUrl(imagePath);
		componentsImageModel.setItemComponentId(JSONUtility.getSafeInteger(reportDetail, "itemComponentId"));
		componentsImageModel.setModifiedBy(JSONUtility.getSafeString(reportDetail, "publicKey"));
		componentsImageModel.setModifiedOn(CommonUtility.getDateAndTime());
		valouxComponentsImageModel.add(componentsImageModel);
		
		LOGGER.info("ReportCheckWS : Exit Method populateValouxComponentsImageModel");
	}

	/**
	 * @paparam imageUrl
	 * @paparam destinationFile
	 * @paparam reportNo 
	 * @throws Exception
	 */
	public static boolean saveDocuments(String imageUrl, String destinationFile) throws Exception {
		
		LOGGER.info("ReportCheckWS : Enter Method saveDocuments");
		boolean flag = false;
			
		try {
			URL url = new URL(imageUrl);
			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream(destinationFile);

			byte[] b = new byte[1024];
			int length;

			flag = false;
			int count = 0;
			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
				count++;
			}
			is.close();
			os.close();
			
			if(count > 0){
				flag = true;
			} else {
				flag = false;
				File f = new File(destinationFile);
		        f.delete();
			}
		} catch (Exception e) {
			LOGGER.error("Error - ", e);
		}
		
		LOGGER.info("ReportCheckWS : Exit Method saveDocuments");
		return flag;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/reportImageServiceClient.csv")
	public Response reportImageServiceClient(JSONObject requestJson) {
		LOGGER.info("ReportCheckWS : Enter reportCheckWebServiceClient");
		JSONObject responseJSON = new JSONObject();
		JSONObject json = new JSONObject();
		JSONObject resJson = new JSONObject();

		try {
			JSONObject reportDetail = requestJson.getJSONObject("reqPaparam");
			String reportNo = JSONUtility.getSafeString(reportDetail, "reportNo");
			Integer componentId = JSONUtility.getSafeInteger(reportDetail, "itemComponentId");
			String valouxPropertyFileName = "valoux.properties";
			Properties prop = new Properties();
			prop = CommonUtility.getProperty(valouxPropertyFileName);
		
			final String ipAddress = prop.getProperty("valoux.server.ip.address");

			if (ipAddress == null) {
				responseJSON.put("errorMessage", CommonConstants.REQUEST_PAparam_MISSING);
				json.put("resData", responseJSON);
				json.put("sCode", "2");
				json.put("missingPaparams", "Paparameters missing - ipAddress");
				return Response.status(200).entity(json.toString()).build();
			}
			
			String requiredPaparams[] = { "publicKey", "userPublicKey", "itemId", "itemComponentId", "componentType", "reportNo", "reportWeight"};

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reportDetail);

			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			
			final String docLocation = prop.getProperty("valoux.image.path");
			final String gia_image_url = prop.getProperty("valoux.diamond.gia.certificate.image.url");
			
			String imageServiceUrl = gia_image_url+"reportNo="+reportNo+"&IPAddress="+ipAddress;
			Integer fileType = 1;
			List<ValouxComponentsImageModel> valouxComponentsImageModel = new ArrayList<ValouxComponentsImageModel>();
			boolean isUpload = false;
			
			////////////////////////////////////////
			String PROPIMG = CommonConstants.DIAMOND_PROPIMG;
			String imageUrl = imageServiceUrl+"&imgType="+PROPIMG;
			String imageName = PROPIMG + "_"+ reportNo + CommonConstants.EXTENSION_JPG;
			String imagePath = "Item_Component_"+ componentId +"_"+ fileType +"_"+ imageName;
			String imageFullPath = docLocation + imagePath;
			
			boolean imageModel1 = itemService.getImageModelByComponentIdNameFileType(componentId, fileType, imageName);
			
			if(!imageModel1){
				boolean isPropPopulate = saveDocuments(imageUrl, imageFullPath);
				if(isPropPopulate){
					isUpload = true;
					populateValouxComponentsImageModel(valouxComponentsImageModel, reportDetail, imageName, imagePath, fileType);
				}
			}
			
			//////////////////////////////////////////
			String PLOTIMG = CommonConstants.DIAMOND_PLOTIMG;
			imageUrl = imageServiceUrl+"&imgType="+PLOTIMG;
			imageName = PLOTIMG + "_"+ reportNo + CommonConstants.EXTENSION_JPG;
			imagePath = "Item_Component_"+ componentId +"_"+ fileType +"_"+ imageName;
			imageFullPath = docLocation + imagePath;
			
			boolean imageModel2 = itemService.getImageModelByComponentIdNameFileType(componentId, fileType, imageName);
			
			if(!imageModel2){
				boolean isPlotPopulate = saveDocuments(imageUrl, imageFullPath);
				if(isPlotPopulate) {
					isUpload = true;
					populateValouxComponentsImageModel(valouxComponentsImageModel, reportDetail, imageName, imagePath, fileType);
				}
			}
			
			////////////////////////////////////////////
			String ERPTIMG = CommonConstants.DIAMOND_ERPTIMG;
			imageUrl = imageServiceUrl+"&imgType="+ERPTIMG;
			imageName = ERPTIMG + "_"+ reportNo + CommonConstants.EXTENSION_JPG;
			imagePath = "Item_Component_"+ componentId +"_"+ fileType +"_"+ imageName;
			imageFullPath = docLocation + imagePath;
			
			boolean imageModel3 = itemService.getImageModelByComponentIdNameFileType(componentId, fileType, imageName);
			
			if(!imageModel3){
				boolean isErptPopulate = saveDocuments(imageUrl, imageFullPath);
				if(isErptPopulate) {
					isUpload = true;
					populateValouxComponentsImageModel(valouxComponentsImageModel, reportDetail, imageName, imagePath, fileType);
				}
			}
			
			///////////////////////////////////////////
			String CERTIFICATE = CommonConstants.DIAMOND_CERTIFICATE;
			fileType = 3;
			imageName = CERTIFICATE + "_"+ reportNo + CommonConstants.EXTENSION_PDF;
			imagePath = "Item_Component_"+ componentId +"_"+ fileType +"_"+ imageName;
			imageFullPath = docLocation + imagePath;
			
			boolean imageModel4 = itemService.getImageModelByComponentIdNameFileType(componentId, fileType, imageName);
			
			if(!imageModel4){
				boolean isPdfPopulate = savePDFDocuments(imageFullPath, reportDetail, ipAddress);
				
				if(isPdfPopulate) {
					isUpload = true;
					populateValouxComponentsImageModel(valouxComponentsImageModel, reportDetail, imageName, imagePath, fileType);
				}
			}
			
			if(isUpload) {
				itemService.addValouxComponentsCertificateImageDetails(valouxComponentsImageModel);
			}
			// Response
			ItemHelper.getValouxItemComponentProperty(itemService, reportDetail, resJson);
			
			json.put("resData", resJson);
			json.put("sCode", CommonConstants.SUCCESS);
			
		} catch (Exception e) {
			LOGGER.error("Error - ", e);
		}
		LOGGER.info("ReportCheckWS : Exit reportCheckWebServiceClient");
		return Response.status(200).entity(json).build();
	}

	/**
	 * @paparam imageFullPath
	 * @paparam reportDetail 
	 * @paparam ipAddress 
	 * @return
	 * @throws Exception
	 */
	private boolean savePDFDocuments(String imageFullPath, JSONObject reportDetail, String ipAddress) throws Exception {
		
		LOGGER.info("ReportCheckWS : Enter Method savePDFDocuments");
		boolean flag = false;
		
		String reportNo = JSONUtility.getSafeString(reportDetail, "reportNo");
		String reportWeight = JSONUtility.getSafeString(reportDetail, "reportWeight");
		
		try {
			JSONObject reqPaparamWithInRequest = new JSONObject();
			JSONObject REPORT_CHECK_REQUEST = new JSONObject();
			JSONObject HEADER = new JSONObject();
			HEADER.put("IP_ADDRESS", ipAddress);
			REPORT_CHECK_REQUEST.put("HEADER", HEADER);
			
			JSONObject BODY = new JSONObject();
			JSONObject REPORT_DTLS = new JSONObject();
			JSONObject REPORT_DTL = new JSONObject();
			REPORT_DTL.put("REPORT_NO", reportNo);
			REPORT_DTL.put("REPORT_WEIGHT", reportWeight);
			REPORT_DTLS.put("REPORT_DTL", REPORT_DTL);
			BODY.put("REPORT_DTLS", REPORT_DTLS);
			REPORT_CHECK_REQUEST.put("BODY", BODY);
			
			reqPaparamWithInRequest.put("REPORT_CHECK_REQUEST", REPORT_CHECK_REQUEST);

			org.json.JSONObject inputJson = new org.json.JSONObject(reqPaparamWithInRequest.toString());

			String Input_XML_String = XML.toString(inputJson);

			String output_XML = null;
			output_XML = reportCheckWS_PortType.processRequest(Input_XML_String);
			
			if (output_XML != null) {
				org.json.JSONObject xmlJSONObj = XML.toJSONObject(output_XML);
				JSONObject responseJSON = new JSONObject(xmlJSONObj.toString());
				
				JSONObject jObject = new JSONObject(responseJSON.toString());
				
				JSONObject REPORT_CHECK_RESPONSE = jObject.optJSONObject("REPORT_CHECK_RESPONSE");
				JSONObject RES_REPORT_DTLS = REPORT_CHECK_RESPONSE.optJSONObject("REPORT_DTLS");
				JSONObject RES_REPORT_DTL = RES_REPORT_DTLS.optJSONObject("REPORT_DTL");
				
				if(RES_REPORT_DTL != null){
					Boolean isPdfAvailable = RES_REPORT_DTL.getBoolean("IS_PDF_AVAILABLE");
					if(isPdfAvailable){
						byte []pdfData = new byte[1024];
						pdfData = reportCheckWS_PortType.downloadPDFReport(Input_XML_String);
						OutputStream out = new FileOutputStream(imageFullPath);
						if(pdfData != null) {
							flag = true;
							out.write(pdfData);
							out.close();
						} else {
							out.close();
							flag = false;
							File f = new File(imageFullPath);
					        f.delete();
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error - ", e);
		}
		
		LOGGER.info("ReportCheckWS : Exit Method savePDFDocuments");
		return flag;
	}

}// end of class
