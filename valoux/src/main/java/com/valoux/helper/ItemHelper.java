/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.valoux.bean.AppraisalBean;
import com.valoux.bean.AppraisalItemsBean;
import com.valoux.bean.ItemComponentCertificateBean;
import com.valoux.bean.ItemImageBean;
import com.valoux.bean.ValouxAppraisalItemsComponentPriceBean;
import com.valoux.bean.ValouxAppraisalItemsPriceBean;
import com.valoux.bean.ValouxCollectionImageBean;
import com.valoux.bean.ValouxCollectionItemBean;
import com.valoux.bean.ValouxComponentsImageBean;
import com.valoux.bean.ValouxDiamondBean;
import com.valoux.bean.ValouxDiamondMasterPriceBean;
import com.valoux.bean.ValouxGemstoneBean;
import com.valoux.bean.ValouxInclusionBean;
import com.valoux.bean.ValouxItemBean;
import com.valoux.bean.ValouxItemComponentBean;
import com.valoux.bean.ValouxMetalBean;
import com.valoux.bean.ValouxMetalsMasterPriceBean;
import com.valoux.bean.ValouxPearlBean;
import com.valoux.bean.ValouxSharedRequestBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.ValouxItemDao;
import com.valoux.model.AgentModel;
import com.valoux.model.AppraisalCollectionModel;
import com.valoux.model.AppraisalModel;
import com.valoux.model.ItemComponentCertificateModel;
import com.valoux.model.ItemImageModel;
import com.valoux.model.ItemPriceModel;
import com.valoux.model.LoginModel;
import com.valoux.model.ValouxAgentStoreModel;
import com.valoux.model.ValouxCollectionItemModel;
import com.valoux.model.ValouxCollectionModel;
import com.valoux.model.ValouxComponentsImageModel;
import com.valoux.model.ValouxDiamondModel;
import com.valoux.model.ValouxGemstoneModel;
import com.valoux.model.ValouxInclusionModel;
import com.valoux.model.ValouxItemComponentModel;
import com.valoux.model.ValouxItemModel;
import com.valoux.model.ValouxMetalModel;
import com.valoux.model.ValouxPearlModel;
import com.valoux.model.ValouxStoreModel;
import com.valoux.service.AgentService;
import com.valoux.service.AppraisalService;
import com.valoux.service.CollectionService;
import com.valoux.service.ItemService;
import com.valoux.service.UserService;
import com.valoux.service.ValouxStoreService;
import com.valoux.util.CommonUserUtility;
import com.valoux.util.CommonUtility;
import com.valoux.util.JSONUtility;

/**
 * This java class ItemHelper use to perform all our service populate for
 * the collections.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
public class ItemHelper {

	private static final Logger LOGGER = Logger.getLogger(ItemHelper.class);

	/**
	 * @paparam valouxItemComponentModelList
	 * @paparam itemInfo
	 * @throws Exception
	 */
	public static void populateValouxItemComponentModel(List<ValouxItemComponentModel> valouxItemComponentModelList,
			JSONObject itemInfo) throws Exception {

		LOGGER.info("ItemHelper : Enter Method populateValouxItemComponentModel");

		Integer itemId = JSONUtility.getSafeInteger(itemInfo, "itemId");

		JSONArray itemComponents = JSONUtility.getSafeJSONArray(itemInfo, "itemComponents");

		if (itemComponents != null && itemComponents.length() > 0 && itemId != null) {

			for (int i = 0; i < itemComponents.length(); i++) {

				ValouxItemComponentModel itemComponentModel = new ValouxItemComponentModel();

				JSONObject object = itemComponents.getJSONObject(i);

				String componentName = JSONUtility.getSafeString(object, "componentName");
				Byte componentType = Byte.valueOf(JSONUtility.getSafeString(object, "componentType"));

				if (componentName != null && componentType != null && componentType != 0) {
					itemComponentModel.setVicid(JSONUtility.getSafeInteger(object, "itemComponentId"));
					itemComponentModel.setItemId(itemId);
					itemComponentModel.setName(JSONUtility.getSafeString(object, "componentName"));
					itemComponentModel.setComponentsType(Byte.valueOf(JSONUtility
							.getSafeString(object, "componentType")));
					itemComponentModel.setQuantity(JSONUtility.getSafeInteger(object, "componentQuantity"));
					itemComponentModel.setVicStatus((byte) 1);
					itemComponentModel.setCreatedBy(JSONUtility.getSafeString(itemInfo, "publicKey"));
					itemComponentModel.setCreatedOn(CommonUtility.getDateAndTime());
					itemComponentModel.setModifiedBy(JSONUtility.getSafeString(itemInfo, "publicKey"));
					itemComponentModel.setModifiedOn(CommonUtility.getDateAndTime());

					valouxItemComponentModelList.add(itemComponentModel);
				}
			}
		}
		LOGGER.info("ItemHelper : Exit Method populateValouxItemComponentModel");
	}

	/**
	 * @paparam diamondModel
	 * @paparam componentProperty
	 */
	public static void populateValouxDiamondModel(ValouxDiamondModel diamondModel, JSONObject reqObject)
			throws Exception {

		LOGGER.info("ItemHelper : Enter Method populateValouxDiamondModel");

		Integer itemId = JSONUtility.getSafeInteger(reqObject, "itemId");
		Integer componentId = JSONUtility.getSafeInteger(reqObject, "itemComponentId");
		Integer componentType = JSONUtility.getSafeInteger(reqObject, "componentType");
		JSONObject componentProperty = reqObject.getJSONObject("componentProperty");

		if (itemId != null && componentId != null && componentType.equals(CommonConstants.COMPONENT_DIAMOND)
				&& componentProperty != null) {

			diamondModel.setItemId(itemId);
			diamondModel.setItemComponentId(componentId);
			diamondModel.setVdid(JSONUtility.getSafeInteger(componentProperty, "vdid"));
			String clarityId = JSONUtility.getSafeString(componentProperty, "clarityId");
			if(CommonUserUtility.paparameterNullCheckStringObject(clarityId)){
				diamondModel.setClarityId(Integer.parseInt(clarityId));
			}
			String color = JSONUtility.getSafeString(componentProperty, "color");
			if(CommonUserUtility.paparameterNullCheckStringObject(color)){
				diamondModel.setColor(Integer.parseInt(color));
			}
			diamondModel.setCreatedBy(JSONUtility.getSafeString(reqObject, "publicKey"));
			diamondModel.setCreatedOn(CommonUtility.getDateAndTime());
			String cut = JSONUtility.getSafeString(componentProperty, "cut");
			if(CommonUserUtility.paparameterNullCheckStringObject(cut)){
				diamondModel.setCut(Integer.parseInt(cut));
			}
			
			String cutlet = JSONUtility.getSafeString(componentProperty, "cutlet");
			if(CommonUserUtility.paparameterNullCheckStringObject(cutlet)){
				diamondModel.setCutlet(Integer.parseInt(cutlet));
			}
			
			diamondModel.setDepth(JSONUtility.getSafeString(componentProperty, "depth"));
			diamondModel.setDepthPercentage(JSONUtility.getSafeString(componentProperty, "depthPercentage"));
			String fancyColor = JSONUtility.getSafeString(componentProperty, "fancyColor");
			if(CommonUserUtility.paparameterNullCheckStringObject(fancyColor)){
				diamondModel.setFancyColor(Integer.parseInt(fancyColor));
			}
			
			String fluorescence = JSONUtility.getSafeString(componentProperty, "fluorescence");
			if(CommonUserUtility.paparameterNullCheckStringObject(fluorescence)){
				diamondModel.setFluorescence(Integer.parseInt(fluorescence));
			}
			String girdleThicknessDescription = JSONUtility.getSafeString(componentProperty, "girdleThicknessDescription");
			if(CommonUserUtility.paparameterNullCheckStringObject(girdleThicknessDescription)){
				diamondModel.setGirdleThicknessDescription(Integer.parseInt(girdleThicknessDescription));
			}
			
			String diamondHeight = JSONUtility.getSafeString(componentProperty, "diamondHeight");
			if(CommonUserUtility.paparameterNullCheckStringObject(diamondHeight)){
				diamondModel.setDiamondHeight(Double.valueOf(diamondHeight));
			}
			
			String diamondLength = JSONUtility.getSafeString(componentProperty, "diamondLength");
			if(CommonUserUtility.paparameterNullCheckStringObject(diamondLength)){
				diamondModel.setDiamondLength(Double.valueOf(diamondLength));
			}
			
			diamondModel.setLengthWidthRatio(JSONUtility.getSafeString(componentProperty, "lengthWidthRatio"));
			diamondModel.setMarketValue(JSONUtility.getSafeString(componentProperty, "marketValue"));
			diamondModel.setMaxDiameter(JSONUtility.getSafeString(componentProperty, "maxDiameter"));
			diamondModel.setMinDiameter(JSONUtility.getSafeString(componentProperty, "minDiameter"));
			diamondModel.setModifiedBy(JSONUtility.getSafeString(reqObject, "publicKey"));
			diamondModel.setModifiedOn(CommonUtility.getDateAndTime());
			String placement = JSONUtility.getSafeString(componentProperty, "placement");
			if(CommonUserUtility.paparameterNullCheckStringObject(placement)){
				diamondModel.setPlacement(Integer.parseInt(placement));
			}
			String polish = JSONUtility.getSafeString(componentProperty, "polish");
			if(CommonUserUtility.paparameterNullCheckStringObject(polish)){
				diamondModel.setPolish(Integer.parseInt(polish));
			}
			String secondaryHue = JSONUtility.getSafeString(componentProperty, "secondaryHue");
			if(CommonUserUtility.paparameterNullCheckStringObject(secondaryHue)){
				diamondModel.setSecondaryHue(Integer.parseInt(secondaryHue));
			}
			String shape = JSONUtility.getSafeString(componentProperty, "shape");
			if(CommonUserUtility.paparameterNullCheckStringObject(shape)){
				diamondModel.setShape(Integer.parseInt(shape));
			}
			diamondModel.setSingleWeight(JSONUtility.getSafeString(componentProperty, "singleWeight"));
			diamondModel.setStatus(CommonConstants.STATUS_ACTIVE);
			String symmetry = JSONUtility.getSafeString(componentProperty, "symmetry");
			if(CommonUserUtility.paparameterNullCheckStringObject(symmetry)){
				diamondModel.setSymmetry(Integer.parseInt(symmetry));
			}
			diamondModel.setTablePercentage(JSONUtility.getSafeString(componentProperty, "tablePercentage"));
			String thickness = JSONUtility.getSafeString(componentProperty, "thickness");
			if(CommonUserUtility.paparameterNullCheckStringObject(thickness)){
				diamondModel.setThickness(Integer.parseInt(thickness));
			}
			diamondModel.setTotalWeight(JSONUtility.getSafeString(componentProperty, "totalWeight"));
			
			String weightMeasure = JSONUtility.getSafeString(componentProperty, "weightMeasure");
			if(CommonUserUtility.paparameterNullCheckStringObject(weightMeasure)){
				diamondModel.setWeightMeasure(Integer.parseInt(weightMeasure));
			}
			
			diamondModel.setPwidth(JSONUtility.getSafeString(componentProperty, "diamondWidth"));
		}
		LOGGER.info("ItemHelper : Exit Method populateValouxDiamondModel");
	}

	/**
	 * @paparam gemstoneModel
	 * @paparam componentProperty
	 */
	public static void populateValouxGemstoneModel(ValouxGemstoneModel gemstoneModel, JSONObject reqObject)
			throws Exception {

		LOGGER.info("ItemHelper : Enter Method populateValouxGemstoneModel");

		Integer itemId = JSONUtility.getSafeInteger(reqObject, "itemId");
		Integer componentId = JSONUtility.getSafeInteger(reqObject, "itemComponentId");
		Integer componentType = JSONUtility.getSafeInteger(reqObject, "componentType");
		JSONObject componentProperty = reqObject.getJSONObject("componentProperty");

		if (itemId != null && componentId != null && componentType.equals(CommonConstants.COMPONENT_GEMSTONES)
				&& componentProperty != null) {
			gemstoneModel.setItemId(itemId);
			gemstoneModel.setItemComponentId(componentId);
			gemstoneModel.setVgid(JSONUtility.getSafeInteger(componentProperty, "vgid"));
			gemstoneModel.setCreatedBy(JSONUtility.getSafeString(reqObject, "publicKey"));
			gemstoneModel.setCreatedOn(CommonUtility.getDateAndTime());
			String cut = JSONUtility.getSafeString(componentProperty, "cut");
			if(CommonUserUtility.paparameterNullCheckStringObject(cut)){
				gemstoneModel.setCut(Integer.parseInt(cut));
			}
			String enhancement = JSONUtility.getSafeString(componentProperty, "enhancement");
			if(CommonUserUtility.paparameterNullCheckStringObject(enhancement)){
				gemstoneModel.setEnhancement(Integer.parseInt(enhancement));
			}
			String gemstonesType = JSONUtility.getSafeString(componentProperty, "gemstonesType");
			if(CommonUserUtility.paparameterNullCheckStringObject(gemstonesType)){
				gemstoneModel.setGemstonesType(Integer.parseInt(gemstonesType));
			}
			gemstoneModel.setMeasurements(JSONUtility.getSafeString(componentProperty, "measurements"));
			gemstoneModel.setModifiedBy(JSONUtility.getSafeString(reqObject, "publicKey"));
			gemstoneModel.setModifiedOn(CommonUtility.getDateAndTime());
			String origin = JSONUtility.getSafeString(componentProperty, "origin");
			if(CommonUserUtility.paparameterNullCheckStringObject(origin)){
				gemstoneModel.setOrigin(Integer.parseInt(origin));
			}
			String placement = JSONUtility.getSafeString(componentProperty, "placement");
			if(CommonUserUtility.paparameterNullCheckStringObject(placement)){
				gemstoneModel.setPlacement(Integer.parseInt(placement));
			}
			String shape = JSONUtility.getSafeString(componentProperty, "shape");
			if(CommonUserUtility.paparameterNullCheckStringObject(shape)){
				gemstoneModel.setShape(Integer.parseInt(shape));
			}
			gemstoneModel.setStatus(CommonConstants.STATUS_ACTIVE);
			gemstoneModel.setWeight(JSONUtility.getSafeString(componentProperty, "weight"));
//			String internalInclusions = JSONUtility.getSafeString(componentProperty, "internalInclusions");
//			if(CommonUserUtility.paparameterNullCheckStringObject(internalInclusions)){
//				gemstoneModel.setInternalInclusions(Integer.parseInt(internalInclusions));
//			}
//			String externalInclusions = JSONUtility.getSafeString(componentProperty, "externalInclusions");
//			if(CommonUserUtility.paparameterNullCheckStringObject(externalInclusions)){
//				gemstoneModel.setExternalInclusions(Integer.parseInt(externalInclusions));
//			}
		}
		LOGGER.info("ItemHelper : Exit Method populateValouxGemstoneModel");
	}

	/**
	 * @paparam pearlModel
	 * @paparam componentProperty
	 */
	public static void populateValouxPearlModel(ValouxPearlModel pearlModel, JSONObject reqObject) throws Exception {

		LOGGER.info("ItemHelper : Enter Method populateValouxPearlModel");

		Integer itemId = JSONUtility.getSafeInteger(reqObject, "itemId");
		Integer componentId = JSONUtility.getSafeInteger(reqObject, "itemComponentId");
		Integer componentType = JSONUtility.getSafeInteger(reqObject, "componentType");
		JSONObject componentProperty = reqObject.getJSONObject("componentProperty");

		if (itemId != null && componentId != null && componentType.equals(CommonConstants.COMPONENT_PEARLS)
				&& componentProperty != null) {

			pearlModel.setItemId(itemId);
			pearlModel.setItemComponentId(componentId);
			pearlModel.setVpid(JSONUtility.getSafeInteger(componentProperty, "vpid"));
			String blemish = JSONUtility.getSafeString(componentProperty, "blemish");
			if(CommonUserUtility.paparameterNullCheckStringObject(blemish)){
				pearlModel.setBlemish(Integer.parseInt(blemish));
			}
			String color = JSONUtility.getSafeString(componentProperty, "color");
			if(CommonUserUtility.paparameterNullCheckStringObject(color)){
				pearlModel.setColor(Integer.parseInt(color));
			}
			String composition = JSONUtility.getSafeString(componentProperty, "composition");
			if(CommonUserUtility.paparameterNullCheckStringObject(composition)){
				pearlModel.setComposition(Integer.parseInt(composition));
			}
			pearlModel.setCreatedBy(JSONUtility.getSafeString(reqObject, "publicKey"));
			pearlModel.setCreatedOn(CommonUtility.getDateAndTime());
			String drilled = JSONUtility.getSafeString(componentProperty, "drilled");
			if(CommonUserUtility.paparameterNullCheckStringObject(drilled)){
				pearlModel.setDrilled(Integer.parseInt(drilled));
			}
			String enhancements = JSONUtility.getSafeString(componentProperty, "enhancements");
			if(CommonUserUtility.paparameterNullCheckStringObject(enhancements)){
				pearlModel.setEnhancements(Integer.parseInt(enhancements));
			}
			String pearlsLength = JSONUtility.getSafeString(componentProperty, "pearlsLength");
			if(CommonUserUtility.paparameterNullCheckStringObject(pearlsLength)){
				pearlModel.setPearlsLength(Double.valueOf(pearlsLength));
			}
			String luster = JSONUtility.getSafeString(componentProperty, "luster");
			if(CommonUserUtility.paparameterNullCheckStringObject(luster)){
				pearlModel.setLuster(Integer.parseInt(luster));
			}
			String matching = JSONUtility.getSafeString(componentProperty, "matching");
			if(CommonUserUtility.paparameterNullCheckStringObject(matching)){
				pearlModel.setMatching(Integer.parseInt(matching));
			}
			String pearlsMax = JSONUtility.getSafeString(componentProperty, "pearlsMax");
			if(CommonUserUtility.paparameterNullCheckStringObject(pearlsMax)){
				pearlModel.setPearlsMax(Double.valueOf(pearlsMax));
			}
			pearlModel.setMeasurements(JSONUtility.getSafeString(componentProperty, "measurements"));
			String pearlsMin = JSONUtility.getSafeString(componentProperty, "pearlsMin");
			if(CommonUserUtility.paparameterNullCheckStringObject(pearlsMin)){
				pearlModel.setPearlsMin(Double.valueOf(pearlsMin));
			}
			pearlModel.setModifiedBy(JSONUtility.getSafeString(reqObject, "publicKey"));
			pearlModel.setModifiedOn(CommonUtility.getDateAndTime());
			String origin = JSONUtility.getSafeString(componentProperty, "origin");
			if(CommonUserUtility.paparameterNullCheckStringObject(origin)){
				pearlModel.setOrigin(Integer.parseInt(origin));
			}
			String pearlType = JSONUtility.getSafeString(componentProperty, "pearlType");
			if(CommonUserUtility.paparameterNullCheckStringObject(pearlType)){
				pearlModel.setPearlType(Integer.parseInt(pearlType));
			}
			String shape = JSONUtility.getSafeString(componentProperty, "shape");
			if(CommonUserUtility.paparameterNullCheckStringObject(shape)){
				pearlModel.setShape(Integer.parseInt(shape));
			}
			pearlModel.setStatus(CommonConstants.STATUS_ACTIVE);
			String pearlsStyle = JSONUtility.getSafeString(componentProperty, "pearlsStyle");
			if(CommonUserUtility.paparameterNullCheckStringObject(pearlsStyle)){
				pearlModel.setPstyle(Integer.parseInt(pearlsStyle));
			}
			String styleUserEntered = JSONUtility.getSafeString(componentProperty, "styleUserEntered");
			if(CommonUserUtility.paparameterNullCheckStringObject(styleUserEntered)){
				pearlModel.setStyleUserEntered(Integer.parseInt(styleUserEntered));
			}
			pearlModel.setWeight(JSONUtility.getSafeString(componentProperty, "weight"));
			pearlModel.setAppraisedValue(JSONUtility.getSafeString(componentProperty, "appraisedValue"));
		}
		LOGGER.info("ItemHelper : Exit Method populateValouxPearlModel");
	}

	/**
	 * @paparam metalModel
	 * @paparam componentProperty
	 */
	public static void populateValouxMetalModel(ValouxMetalModel metalModel, JSONObject reqObject) throws Exception {

		LOGGER.info("ItemHelper : Enter Method populateValouxMetalModel");

		Integer itemId = JSONUtility.getSafeInteger(reqObject, "itemId");
		Integer componentId = JSONUtility.getSafeInteger(reqObject, "itemComponentId");
		Integer componentType = JSONUtility.getSafeInteger(reqObject, "componentType");
		JSONObject componentProperty = reqObject.getJSONObject("componentProperty");

		if (itemId != null && componentId != null && componentType.equals(CommonConstants.COMPONENT_METALS)
				&& componentProperty != null) {

			metalModel.setItemId(itemId);
			metalModel.setItemComponentId(componentId);
			metalModel.setVmid(JSONUtility.getSafeInteger(componentProperty, "vmid"));
			String color = JSONUtility.getSafeString(componentProperty, "color");
			if(CommonUserUtility.paparameterNullCheckStringObject(color)){
				metalModel.setColor(Integer.parseInt(color));
			} else {
				metalModel.setColor(null);
			}
			metalModel.setCreatedBy(JSONUtility.getSafeString(reqObject, "publicKey"));
			metalModel.setCreatedOn(CommonUtility.getDateAndTime());
			metalModel.setMarketValue(JSONUtility.getSafeString(componentProperty, "marketValue"));
			metalModel.setMeasurements(JSONUtility.getSafeString(componentProperty, "measurements"));
			String metalsType = JSONUtility.getSafeString(componentProperty, "metalsType");
			if(CommonUserUtility.paparameterNullCheckStringObject(metalsType)){
				metalModel.setMetalsType(Integer.parseInt(metalsType));
			}
			metalModel.setModifiedBy(JSONUtility.getSafeString(reqObject, "publicKey"));
			metalModel.setModifiedOn(CommonUtility.getDateAndTime());
			String purity = JSONUtility.getSafeString(componentProperty, "purity");
			if(CommonUserUtility.paparameterNullCheckStringObject(purity)){
				metalModel.setPurity(Integer.parseInt(purity));
			}
			metalModel.setStatus(CommonConstants.STATUS_ACTIVE);
			
			String typeDetermined = JSONUtility.getSafeString(componentProperty, "typeDetermined");
			if(CommonUserUtility.paparameterNullCheckStringObject(typeDetermined)){
				metalModel.setTypeDetermined(Byte.valueOf(typeDetermined));
			}
			metalModel.setTypeSpecified(JSONUtility.getSafeString(componentProperty, "typeSpecified"));
			metalModel.setWeight(JSONUtility.getSafeString(componentProperty, "weight"));
			metalModel.setAppraisedValue(JSONUtility.getSafeString(componentProperty, "appraisedValue"));
		}
		LOGGER.info("ItemHelper : Exit Method populateValouxMetalModel");
	}

	/**
	 * @paparam valouxComponentsImageModel
	 * @paparam reqObject
	 * @paparam componentId
	 * @throws Exception
	 */
	public static void populateValouxComponentsImageModel(List<ValouxComponentsImageModel> valouxComponentsImageModel,
			JSONObject reqObject, Integer componentId) throws Exception {

		JSONObject componentProperty = reqObject.getJSONObject("componentProperty");

		LOGGER.info("CollectionHelper : Enter Method populateValouxCollectionImageModel");

		JSONArray componentImages = JSONUtility.getSafeJSONArray(componentProperty, "componentImages");

		if (componentImages != null && componentImages.length() > 0) {

			for (int i = 0; i < componentImages.length(); i++) {

				ValouxComponentsImageModel componentsImageModel = new ValouxComponentsImageModel();

				JSONObject object = componentImages.getJSONObject(i);

				Integer cid = JSONUtility.getSafeInteger(object, "cid");
				String imageContent = JSONUtility.getSafeString(object, "imageContent");
				String imageName = JSONUtility.getSafeString(object, "imageName");
				String fileType = JSONUtility.getSafeString(object, "fileType");
				String imagePath = CommonUtility.saveDocumentInDirectory(imageContent, imageName, "Item_Component_"
						+ fileType + "_" + componentId + "_", i);

				componentsImageModel.setCid(cid);
				componentsImageModel.setCreatedBy(JSONUtility.getSafeString(reqObject, "publicKey"));
				componentsImageModel.setCreatedOn(CommonUtility.getDateAndTime());
				componentsImageModel.setFileType(JSONUtility.getSafeInteger(object, "fileType"));
				componentsImageModel.setImgName(imageName);
				componentsImageModel.setImgStatus(CommonConstants.STATUS_ACTIVE);
				componentsImageModel.setImgUrl(imagePath);
				componentsImageModel.setItemComponentId(JSONUtility.getSafeInteger(reqObject, "itemComponentId"));
				componentsImageModel.setModifiedBy(JSONUtility.getSafeString(reqObject, "publicKey"));
				componentsImageModel.setModifiedOn(CommonUtility.getDateAndTime());

				valouxComponentsImageModel.add(componentsImageModel);
			}
		}
		LOGGER.info("ItemHelper : Exit Method populateValouxComponentsImageModel");
	}

	/**
	 * Method for populating item certificate model
	 * 
	 * @paparam certificateModel
	 * @paparam reqObject
	 * @throws Exception
	 */
	public static void populateItemComponentCertificateModel(ItemComponentCertificateModel certificateModel,
			JSONObject reqObject) throws Exception {

		LOGGER.info("ItemHelper : Enter Method populateItemComponentCertificateModel");

		Integer itemId = JSONUtility.getSafeInteger(reqObject, "itemId");
		Integer componentId = JSONUtility.getSafeInteger(reqObject, "itemComponentId");
		JSONObject componentProperty = reqObject.getJSONObject("componentProperty");
		
		JSONObject componentCertificate = new JSONObject();
		if(componentProperty != null){
			componentCertificate = componentProperty.optJSONObject("componentCertificate");
		}

		if (itemId != null && componentId != null && componentProperty != null && componentCertificate != null) {

			certificateModel.setItemId(itemId);
			certificateModel.setItemComponentId(componentId);
			certificateModel.setCertificateNumber(JSONUtility.getSafeString(componentCertificate, "certificateNumber"));
			certificateModel.setCreatedBy(JSONUtility.getSafeString(reqObject, "publicKey"));
			certificateModel.setCreatedOn(CommonUtility.getDateAndTime());

			if (JSONUtility.getSafeString(componentCertificate, "dateOfCertificate") != null) {
				certificateModel.setDateOfCertificate(CommonUtility.convertUIStringToDate(JSONUtility.getSafeString(
						componentCertificate, "dateOfCertificate")));
			}

			String lab = JSONUtility.getSafeString(componentCertificate, "lab");
			if(CommonUserUtility.paparameterNullCheckStringObject(lab)){
				certificateModel.setLab(Integer.valueOf(lab));
			} else {
				certificateModel.setLab(0);
			}
			certificateModel.setLaserIdNumber(JSONUtility.getSafeString(componentCertificate, "laserIdNumber"));
			certificateModel.setLaserInscription(JSONUtility.getSafeString(componentCertificate, "laserInscription"));
			certificateModel.setModifiedBy(JSONUtility.getSafeString(reqObject, "publicKey"));
			certificateModel.setModifiedOn(CommonUtility.getDateAndTime());
			certificateModel.setName(JSONUtility.getSafeString(componentCertificate, "name"));
			certificateModel.setOriginOfDiamond(JSONUtility.getSafeString(componentCertificate, "originOfDiamond"));
			certificateModel.setUrl(JSONUtility.getSafeString(componentCertificate, "url"));

		}
		LOGGER.info("ItemHelper : Exit Method populateItemComponentCertificateModel");
	}

	/**
	 * Method for response item component property
	 * 
	 * @paparam compoJson
	 * @paparam diamondModel
	 */
	public static void getResponseItemComponentDiamondProperty(JSONObject compoJson, ValouxDiamondModel diamondModel)
			throws Exception {
		LOGGER.info("ItemHelper : Enter Method getResponseItemComponentDiamondProperty");
		if (diamondModel != null) {
			compoJson.put("vdid", diamondModel.getVdid());
			compoJson.put("clarityId", diamondModel.getClarityId());
			compoJson.put("clarityName", ItemComponentHelper.DIAMOND_CLARITY_MAP.get(diamondModel.getClarityId()));
			compoJson.put("color", diamondModel.getColor());
			compoJson.put("colorName", ItemComponentHelper.DIAMOND_COLOR_MAP.get(diamondModel.getColor()));
			compoJson.put("cut", diamondModel.getCut());
			compoJson.put("cutName", ItemComponentHelper.COMPONENT_QUALITY_MAP.get(diamondModel.getCut()));
			compoJson.put("cutlet", diamondModel.getCutlet());
			compoJson.put("cutletName", ItemComponentHelper.DIAMOND_CUTLET_MAP.get(diamondModel.getCutlet()));
			compoJson.put("depth", diamondModel.getDepth());
			compoJson.put("depthPercentage", diamondModel.getDepthPercentage());
			compoJson.put("fancyColor", diamondModel.getFancyColor());
			compoJson.put("fancyColorName", ItemComponentHelper.FANCY_COLOR_MAP.get(diamondModel.getFancyColor()));
			compoJson.put("fluorescence", diamondModel.getFluorescence());
			compoJson.put("fluorescenceName", ItemComponentHelper.DIAMOND_FLUORESCENCE_MAP.get(diamondModel.getFluorescence()));
			compoJson.put("girdleThicknessDescription", diamondModel.getGirdleThicknessDescription());
			compoJson.put("girdleThicknessDescriptionName", ItemComponentHelper.DIAMOND_GIRDLE_THICKNESS_MAP.get(diamondModel.getGirdleThicknessDescription()));
			compoJson.put("diamondHeight", diamondModel.getDiamondHeight());
			compoJson.put("diamondLength", diamondModel.getDiamondLength());
			compoJson.put("lengthWidthRatio", diamondModel.getLengthWidthRatio());
			compoJson.put("itemComponentId", diamondModel.getItemComponentId());
			compoJson.put("itemId", diamondModel.getItemId());
			compoJson.put("marketValue", diamondModel.getMarketValue());
			compoJson.put("maxDiameter", diamondModel.getMaxDiameter());
			compoJson.put("minDiameter", diamondModel.getMinDiameter());
			compoJson.put("placement", diamondModel.getPlacement());
			compoJson.put("placementName", ItemComponentHelper.COMPONENT_PLACEMENT_MAP.get(diamondModel.getPlacement()));
			compoJson.put("polish", diamondModel.getPolish());
			compoJson.put("polishName", ItemComponentHelper.COMPONENT_QUALITY_MAP.get(diamondModel.getPolish()));
			compoJson.put("secondaryHue", diamondModel.getSecondaryHue());
			compoJson.put("secondaryHueName", ItemComponentHelper.DIAMOND_SECONDARY_HUE_MAP.get(diamondModel.getSecondaryHue()));
			compoJson.put("shape", diamondModel.getShape());
			compoJson.put("shapeName", ItemComponentHelper.getDiamondShapeName(ItemComponentHelper.DIAMOND_SHAPE_MAP.get(diamondModel.getShape()), CommonConstants.ZERO));
			compoJson.put("singleWeight", diamondModel.getSingleWeight());
			compoJson.put("status", diamondModel.getStatus());
			compoJson.put("symmetry", diamondModel.getSymmetry());
			compoJson.put("symmetryName", ItemComponentHelper.COMPONENT_QUALITY_MAP.get(diamondModel.getSymmetry()));
			compoJson.put("tablePercentage", diamondModel.getTablePercentage());
			compoJson.put("thickness", diamondModel.getThickness());
			compoJson.put("thicknessName", ItemComponentHelper.DIAMOND_THICKNESS_MAP.get(diamondModel.getThickness()));
			compoJson.put("totalWeight", diamondModel.getTotalWeight());
			compoJson.put("weightMeasure", diamondModel.getWeightMeasure());
			compoJson.put("weightMeasureName", ItemComponentHelper.DIAMOND_WEIGHT_MEASURE_MAP.get(diamondModel.getWeightMeasure()));
			compoJson.put("diamondWidth", diamondModel.getPwidth());

			compoJson.put("internalInclusions", diamondModel.getInternalInclusions());
			StringBuffer internalbuffer = new StringBuffer();
			if(diamondModel.getInternalInclusions() != null && diamondModel.getInternalInclusions().length() > 0){
				
				for (int count = 0; count < diamondModel.getInternalInclusions().length(); count++) {
					
					String name = ItemComponentHelper.INTERNAL_INCLUSION_MAP.get((Integer)diamondModel.getInternalInclusions().get(count));
					internalbuffer.append(name);
					if(count < diamondModel.getInternalInclusions().length() - 1){
						internalbuffer.append(", ");
					}
				}
				compoJson.put("internalInclusionsName", internalbuffer.toString());
			}
			
			compoJson.put("externalInclusions", diamondModel.getExternalInclusions());
			
			StringBuffer externalbuffer = new StringBuffer();
			if(diamondModel.getExternalInclusions() != null && diamondModel.getExternalInclusions().length() > 0){
				
				for (int count = 0; count < diamondModel.getExternalInclusions().length(); count++) {
					String name = ItemComponentHelper.EXTERNAL_INCLUSION_MAP.get((Integer)diamondModel.getExternalInclusions().get(count));
					externalbuffer.append(name);
					if(count < diamondModel.getExternalInclusions().length() - 1){
						externalbuffer.append(", ");
					}
				}
				compoJson.put("externalInclusionsName", externalbuffer.toString());
			}
		}
		LOGGER.info("ItemHelper : Exit Method getResponseItemComponentDiamondProperty");
	}

	/**
	 * Method for response item component property certificate
	 * 
	 * @paparam compoJson
	 * @paparam certificateModel
	 * @throws Exception
	 */
	public static void getResponseItemComponentPropertyCertificate(JSONObject compoJson,
			ItemComponentCertificateModel certificateModel) throws Exception {
		LOGGER.info("ItemHelper : Enter Method getResponseItemComponentPropertyCertificate");
		if (certificateModel != null) {
			compoJson.put("ccid", certificateModel.getCcid());
			compoJson.put("certificateNumber", certificateModel.getCertificateNumber());
			compoJson.put("dateOfCertificate", certificateModel.getDateOfCertificate());
			compoJson.put("lab", certificateModel.getLab());
			compoJson.put("labName", ItemComponentHelper.DIAMOND_CERTIFICATE_NAME_MAP.get(certificateModel.getLab()));
			compoJson.put("laserIdNumber", certificateModel.getLaserIdNumber());
			compoJson.put("laserInscription", certificateModel.getLaserInscription());
			compoJson.put("name", certificateModel.getName());
			compoJson.put("originOfDiamond", certificateModel.getOriginOfDiamond());
			compoJson.put("url", certificateModel.getUrl());
		}
		LOGGER.info("ItemHelper : Exit Method getResponseItemComponentPropertyCertificate");
	}

	/**
	 * Method for response item component property images
	 * 
	 * @paparam resJson
	 * @paparam imageModels
	 * @throws Exception
	 */
	public static void getResponseItemComponentPropertyImages(JSONObject resJson,
			List<ValouxComponentsImageModel> imageModels) throws Exception {
		LOGGER.info("ItemHelper : Enter Method getResponseItemComponentPropertyImages");
		JSONArray photoArray = new JSONArray();
		JSONArray receiptArray = new JSONArray();
		JSONArray certificateArray = new JSONArray();
		if (imageModels != null && imageModels.size() > 0) {
			for (ValouxComponentsImageModel valouxComponentsImageModel : imageModels) {

				if (valouxComponentsImageModel.getFileType().equals(CommonConstants.COMPONENT_PHOTO)) {

					JSONObject compoJson = new JSONObject();
					compoJson.put("cid", valouxComponentsImageModel.getCid());
					compoJson.put("fileType", valouxComponentsImageModel.getFileType());
					compoJson.put("imgName", valouxComponentsImageModel.getImgName());
					compoJson.put("imgStatus", valouxComponentsImageModel.getImgStatus());
					compoJson.put("imgUrl", valouxComponentsImageModel.getImgUrl());
					compoJson.put("itemComponentId", valouxComponentsImageModel.getItemComponentId());
					photoArray.put(compoJson);

				} else if (valouxComponentsImageModel.getFileType().equals(CommonConstants.COMPONENT_RECEIPT)) {
					JSONObject compoJson = new JSONObject();
					compoJson.put("cid", valouxComponentsImageModel.getCid());
					compoJson.put("fileType", valouxComponentsImageModel.getFileType());
					compoJson.put("imgName", valouxComponentsImageModel.getImgName());
					compoJson.put("imgStatus", valouxComponentsImageModel.getImgStatus());
					compoJson.put("imgUrl", valouxComponentsImageModel.getImgUrl());
					compoJson.put("itemComponentId", valouxComponentsImageModel.getItemComponentId());
					receiptArray.put(compoJson);
				} else if (valouxComponentsImageModel.getFileType().equals(CommonConstants.COMPONENT_CERTIFICATE)) {
					JSONObject compoJson = new JSONObject();
					compoJson.put("cid", valouxComponentsImageModel.getCid());
					compoJson.put("fileType", valouxComponentsImageModel.getFileType());
					compoJson.put("imgName", valouxComponentsImageModel.getImgName());
					compoJson.put("imgStatus", valouxComponentsImageModel.getImgStatus());
					compoJson.put("imgUrl", valouxComponentsImageModel.getImgUrl());
					compoJson.put("itemComponentId", valouxComponentsImageModel.getItemComponentId());
					certificateArray.put(compoJson);
				}
			}
		}
		resJson.put("componentPhotosImages", photoArray);
		resJson.put("componentReceiptsImages", receiptArray);
		resJson.put("componentCertificatesImages", certificateArray);
		LOGGER.info("ItemHelper : Exit Method getResponseItemComponentPropertyImages");
	}
	
	/**
	 * @paparam resJson
	 * @paparam itemId
	 * @throws Exception
	 */
	public static void getComponentsByItemId(ItemService itemService, JSONObject resJson, Integer itemId) throws Exception {
		LOGGER.info("ItemWS : Enter method getComponentsByItemId");
		resJson.put("itemId", itemId);

		List<ValouxItemComponentModel> valouxItemComponentModels = itemService.getComponentsByItemId(itemId);

		JSONArray jArray = new JSONArray();
		if (valouxItemComponentModels != null && valouxItemComponentModels.size() > 0) {

			for (ValouxItemComponentModel componentModel : valouxItemComponentModels) {
				JSONObject compoJson = new JSONObject();
				compoJson.put("itemComponentId", componentModel.getVicid());
				compoJson.put("componentType", componentModel.getComponentsType());
				compoJson.put("componentTypeName",CommonUtility.getItemComponentTypeName(componentModel.getComponentsType()));
				compoJson.put("componentName", componentModel.getName());
				compoJson.put("componentQuantity", componentModel.getQuantity());
				compoJson.put("vicStatus", componentModel.getVicStatus());
				
				JSONObject componentObject = new JSONObject();
				if (Integer.valueOf(componentModel.getComponentsType()).equals(CommonConstants.COMPONENT_DIAMOND)) {
					// Component type 1 - Diamond

					componentObject = getItemComponentDiamondProperty(itemService, itemId, componentModel.getVicid(), resJson);
					getItemComponentPropertyCertificate(itemService, itemId, componentModel.getVicid(), componentObject);
					if(!componentObject.isNull("itemId")) {
						compoJson.put("propertyFlag", true);
					} else {
						compoJson.put("propertyFlag", false);
					}

				} else if (Integer.valueOf(componentModel.getComponentsType()).equals(CommonConstants.COMPONENT_GEMSTONES)) {
					// Component type 2 - Gemstones
					
					componentObject = getItemComponentGemstonesProperty(itemService, itemId, componentModel.getVicid(), resJson);
					if(!componentObject.isNull("itemId")) {
						compoJson.put("propertyFlag", true);
					} else {
						compoJson.put("propertyFlag", false);
					}

				} else if (Integer.valueOf(componentModel.getComponentsType()).equals(CommonConstants.COMPONENT_PEARLS)) {
					// Component type 3 - Pearls
					
					componentObject = getItemComponentPearlsProperty(itemService, itemId, componentModel.getVicid(), resJson);
					if(!componentObject.isNull("itemId")) {
						compoJson.put("propertyFlag", true);
					} else {
						compoJson.put("propertyFlag", false);
					}

				} else if (Integer.valueOf(componentModel.getComponentsType()).equals(CommonConstants.COMPONENT_METALS)) {
					// Component type 4 - Metals
					
					componentObject = getItemComponentMetalsProperty(itemService, itemId, componentModel.getVicid(), resJson);
					if(!componentObject.isNull("itemId")) {
						compoJson.put("propertyFlag", true);
					} else {
						compoJson.put("propertyFlag", false);
					}
				}
				// Item component images
				getItemComponentPropertyImages(itemService, componentModel.getVicid(), componentObject);
				
				getUpdatedItemComponentPriceProperties(itemService, componentObject, itemId, componentModel.getVicid());
				
				compoJson.put("componentProperty", componentObject);
				
				jArray.put(compoJson);
			}
		}

		resJson.put("componentList", jArray);
		LOGGER.info("ItemWS : Exit method getComponentsByItemId");
		
	}

	/**
	 * @paparam itemService
	 * @paparam resJson
	 * @paparam itemId
	 * @paparam componentId 
	 */
	public static void getUpdatedItemComponentPriceProperties(ItemService itemService,
			JSONObject componentObject, Integer itemId, Integer componentId) throws Exception{
		
		ValouxItemComponentModel itemModel = itemService.getComponentsByItemAndComponentId(itemId, componentId);
		if(itemModel != null){
			getItemComponentPricePropertyDetails(itemModel, componentObject);
		}
	}

	/**
	 * @paparam reqObject
	 * @paparam itemStatus 
	 * @throws Exception
	 */
	public static void addValouxComponentByType(ItemService itemService, JSONObject reqObject, Integer itemStatus) throws Exception {
		LOGGER.info("ItemWS : Enter method addValouxComponentByType");
		Integer componentId = JSONUtility.getSafeInteger(reqObject, "itemComponentId");
		Integer componentType = JSONUtility.getSafeInteger(reqObject, "componentType");
		Integer itemId = JSONUtility.getSafeInteger(reqObject, "itemId");

		String requestType = JSONUtility.getSafeString(reqObject, "requestType");

		JSONObject componentProperty = reqObject.getJSONObject("componentProperty");

		ItemComponentCertificateModel certificateModel = new ItemComponentCertificateModel();
		List<ValouxComponentsImageModel> valouxComponentsImageModel = new ArrayList<ValouxComponentsImageModel>();
		
		if(itemStatus != null && !(itemStatus.equals(CommonConstants.APPRAISAL_STATUS_APPROVED))){
			if (componentType.equals(CommonConstants.COMPONENT_DIAMOND)) {
				// Component type 1 - Diamond
				ValouxDiamondModel diamondModel = new ValouxDiamondModel();
				// Populate diamond model
				ItemHelper.populateValouxDiamondModel(diamondModel, reqObject);

				if (diamondModel != null) {

					JSONObject componentCertificate = componentProperty.optJSONObject("componentCertificate");
					if (componentCertificate != null) {
						// Populate certificate
						ItemHelper.populateItemComponentCertificateModel(certificateModel, reqObject);
						if(CommonUserUtility.paparameterNullCheckStringObject(certificateModel.getCertificateNumber()) || CommonUserUtility.paparameterNullCheckObject(certificateModel.getLab())){
							itemService.addItemComponentCertificate(certificateModel);
						}
					}
					// Adding diamond model to bean
					if(CommonUserUtility.paparameterNullCheckObject(diamondModel.getClarityId())){
						itemService.addValouxComponentDiamondProperty(diamondModel, requestType);
					}
					
					//Internal Inclusion
					Integer inclusionType = 0;
					JSONArray internalInclusionsArray = JSONUtility.getSafeJSONArray(componentProperty, "internalInclusions");
					if(internalInclusionsArray != null) {
						if(internalInclusionsArray.length() > 0){
							inclusionType = CommonConstants.COMPONENT_INCLUSION_INTERNAL;
							addInclusionsTypeFromRequest(itemService, reqObject, internalInclusionsArray, inclusionType);
						} else {
							inclusionType = CommonConstants.COMPONENT_INCLUSION_INTERNAL;
							itemService.deleteAllInclusionsByComponentAndType(componentId, inclusionType);
						}
					} 
					
					//External Inclusion
					inclusionType = 0;
					JSONArray externalInclusionsArray = JSONUtility.getSafeJSONArray(componentProperty, "externalInclusions");
					if(externalInclusionsArray != null) {
						if(externalInclusionsArray.length() > 0) {
							inclusionType = CommonConstants.COMPONENT_INCLUSION_EXTERNAL;
							addInclusionsTypeFromRequest(itemService, reqObject, externalInclusionsArray, inclusionType);
						} else {
							inclusionType = CommonConstants.COMPONENT_INCLUSION_EXTERNAL;
							itemService.deleteAllInclusionsByComponentAndType(componentId, inclusionType);
						}
					} 
					
					// Fetching image for update
					JSONArray componentImages = JSONUtility.getSafeJSONArray(componentProperty, "componentImages");
					if (componentImages != null) {
						ItemHelper.populateValouxComponentsImageModel(valouxComponentsImageModel, reqObject, componentId);

						itemService.addValouxComponentsImageDetails(valouxComponentsImageModel);
					}
				}

			} else if (componentType.equals(CommonConstants.COMPONENT_GEMSTONES)) {
				// Component type 2 - Gemstones
				ValouxGemstoneModel gemstoneModel = new ValouxGemstoneModel();
				ItemHelper.populateValouxGemstoneModel(gemstoneModel, reqObject);

				if (gemstoneModel != null) {
					
					if(CommonUserUtility.paparameterNullCheckObject(gemstoneModel.getGemstonesType())){
						itemService.addValouxComponentGemstoneProperty(gemstoneModel);
					}
					
					//Internal Inclusion
					Integer inclusionType = 0;
					JSONArray internalInclusionsArray = JSONUtility.getSafeJSONArray(componentProperty, "internalInclusions");
					if(internalInclusionsArray != null) {
						if(internalInclusionsArray.length() > 0){
							inclusionType = CommonConstants.COMPONENT_INCLUSION_INTERNAL;
							addInclusionsTypeFromRequest(itemService, reqObject, internalInclusionsArray, inclusionType);
						} else {
							inclusionType = CommonConstants.COMPONENT_INCLUSION_INTERNAL;
							itemService.deleteAllInclusionsByComponentAndType(componentId, inclusionType);
						}
					} 
					
					//External Inclusion
					inclusionType = 0;
					JSONArray externalInclusionsArray = JSONUtility.getSafeJSONArray(componentProperty, "externalInclusions");
					if(externalInclusionsArray != null) {
						if(externalInclusionsArray.length() > 0) {
							inclusionType = CommonConstants.COMPONENT_INCLUSION_EXTERNAL;
							addInclusionsTypeFromRequest(itemService, reqObject, externalInclusionsArray, inclusionType);
						} else {
							inclusionType = CommonConstants.COMPONENT_INCLUSION_EXTERNAL;
							itemService.deleteAllInclusionsByComponentAndType(componentId, inclusionType);
						}
					} 
					
					// Fetching image for update
					JSONArray componentImages = JSONUtility.getSafeJSONArray(componentProperty, "componentImages");
					if (componentImages != null) {
						ItemHelper.populateValouxComponentsImageModel(valouxComponentsImageModel, reqObject, componentId);

						itemService.addValouxComponentsImageDetails(valouxComponentsImageModel);
					}
				}

			} else if (componentType.equals(CommonConstants.COMPONENT_PEARLS)) {
				// Component type 3 - Pearls
				ValouxPearlModel pearlModel = new ValouxPearlModel();
				ItemHelper.populateValouxPearlModel(pearlModel, reqObject);

				if (pearlModel != null) {
					if(CommonUserUtility.paparameterNullCheckObject(pearlModel.getPearlType())){
						itemService.addValouxComponentPearlProperty(pearlModel);
					}
					
					// Fetching image for update
					JSONArray componentImages = JSONUtility.getSafeJSONArray(componentProperty, "componentImages");
					if (componentImages != null) {
						ItemHelper.populateValouxComponentsImageModel(valouxComponentsImageModel, reqObject, componentId);

						itemService.addValouxComponentsImageDetails(valouxComponentsImageModel);
					}
				}

			} else if (componentType.equals(CommonConstants.COMPONENT_METALS)) {
				// Component type 4 - Metals
				ValouxMetalModel metalModel = new ValouxMetalModel();
				ItemHelper.populateValouxMetalModel(metalModel, reqObject);

				if (metalModel != null) {
					if(CommonUserUtility.paparameterNullCheckObject(metalModel.getMetalsType())){
						itemService.addValouxComponentMetalProperty(metalModel);
					}
					
					// Fetching image for update
					JSONArray componentImages = JSONUtility.getSafeJSONArray(componentProperty, "componentImages");
					if (componentImages != null) {
						ItemHelper.populateValouxComponentsImageModel(valouxComponentsImageModel, reqObject, componentId);

						itemService.addValouxComponentsImageDetails(valouxComponentsImageModel);
					}
				}
			}
		}
		
		// Populate item component price property details
		ValouxItemComponentModel valouxItemComponentModel = itemService.getComponentsByItemAndComponentId(itemId, componentId);
		if(valouxItemComponentModel != null){
			
			valouxItemComponentModel = new ValouxItemComponentModel();
			ItemHelper.populateUpdateValouxItemComponentModelPriceDetails(valouxItemComponentModel, reqObject);
			
			itemService.addUpdateValouxItemComponentModelPriceDetails(valouxItemComponentModel);
			itemService.updateItemMarketValueFromComponents(itemId);
		}
		
		LOGGER.info("ItemWS : Exit method addValouxComponentByType");
	}
	
	/**
	 * @paparam itemService 
	 * @paparam reqObject
	 * @paparam internalInclusionsArray
	 * @paparam inclusionType
	 */
	public static void addInclusionsTypeFromRequest(ItemService itemService, JSONObject reqObject, JSONArray inclusionsArray, Integer inclusionType) throws Exception{
		if(inclusionsArray != null && inclusionsArray.length() > 0 && inclusionType > 0) {
			
			List<ValouxInclusionModel> valouxInclusionModels = new ArrayList<ValouxInclusionModel>();
			
			for (int i = 0; i < inclusionsArray.length(); i++) {

				ValouxInclusionModel valouxInclusionModel = new ValouxInclusionModel();

				Integer inclusion = Integer.parseInt(inclusionsArray.getString(i));

				valouxInclusionModel.setInclusion(inclusion);
				valouxInclusionModel.setInclusionType(inclusionType);
				valouxInclusionModel.setCreatedBy(JSONUtility.getSafeString(reqObject, "userPublicKey"));
				valouxInclusionModel.setCreatedOn(CommonUtility.getDateAndTime());
				valouxInclusionModel.setItemComponentId(JSONUtility.getSafeInteger(reqObject, "itemComponentId"));
				valouxInclusionModel.setModifiedBy(JSONUtility.getSafeString(reqObject, "publicKey"));
				valouxInclusionModel.setModifiedOn(CommonUtility.getDateAndTime());
				valouxInclusionModel.setStatus(CommonConstants.STATUS_ACTIVE);

				valouxInclusionModels.add(valouxInclusionModel);
			}
			itemService.addInclusionsTypeFromRequest(valouxInclusionModels);
		}
	}

	/**
	 * Method for populate item component price property
	 * @paparam valouxItemComponentModel
	 * @paparam reqObject
	 * @throws Exception
	 */
	private static void populateUpdateValouxItemComponentModelPriceDetails(
			ValouxItemComponentModel valouxItemComponentModel,
			JSONObject reqObject) throws Exception {
		LOGGER.info("ItemWS : Enter method populateUpdateValouxItemComponentModelPriceDetails");
		
		Integer componentType = JSONUtility.getSafeInteger(reqObject, "componentType");
		JSONObject componentProperty = reqObject.getJSONObject("componentProperty");
		JSONObject priceProperty = componentProperty.optJSONObject("priceProperty");
		
		if(priceProperty != null){
			valouxItemComponentModel.setItemId(JSONUtility.getSafeInteger(reqObject, "itemId"));
			valouxItemComponentModel.setVicid(JSONUtility.getSafeInteger(reqObject, "itemComponentId"));
			
			if(componentType.equals(CommonConstants.COMPONENT_DIAMOND) || componentType.equals(CommonConstants.COMPONENT_METALS)){
				valouxItemComponentModel.setAdjustmentPrice(JSONUtility.getSafeString(priceProperty, "adjustmentPrice"));
				valouxItemComponentModel.setBrandPriceAdjustmentNotes(JSONUtility.getSafeString(priceProperty, "brandPriceAdjustmentNotes"));
				
				String brandPriceAdjustmentOperator = JSONUtility.getSafeString(priceProperty, "brandPriceAdjustmentOperator");
				if(CommonUserUtility.paparameterNullCheckStringObject(brandPriceAdjustmentOperator)){
					valouxItemComponentModel.setBrandPriceAdjustmentOperator(Byte.valueOf(brandPriceAdjustmentOperator));
				}
				
				String brandPriceAdjustmentType = JSONUtility.getSafeString(priceProperty, "brandPriceAdjustmentType");
				if(CommonUserUtility.paparameterNullCheckStringObject(brandPriceAdjustmentType)){
					valouxItemComponentModel.setBrandPriceAdjustmentType(Byte.valueOf(brandPriceAdjustmentType));
				}
				
				valouxItemComponentModel.setBrandPriceAdjustmentValue(JSONUtility.getSafeString(priceProperty, "brandPriceAdjustmentValue"));
				valouxItemComponentModel.setCurrentUnitPrice(JSONUtility.getSafeString(priceProperty, "currentUnitPrice"));
				String finalPrice = JSONUtility.getSafeString(priceProperty, "finalPrice");
				if(CommonUserUtility.paparameterNullCheckStringObject(finalPrice)){
					valouxItemComponentModel.setFinalPrice(Double.valueOf(finalPrice));
				}
				
				valouxItemComponentModel.setGeneralPriceAdjustmentNotes(JSONUtility.getSafeString(priceProperty, "generalPriceAdjustmentNotes"));
				
				String generalPriceAdjustmentOperator = JSONUtility.getSafeString(priceProperty, "generalPriceAdjustmentOperator");
				if(CommonUserUtility.paparameterNullCheckStringObject(generalPriceAdjustmentOperator)){
					valouxItemComponentModel.setGeneralPriceAdjustmentOperator(Byte.valueOf(generalPriceAdjustmentOperator));
				}
				
				String generalPriceAdjustmentType = JSONUtility.getSafeString(priceProperty, "generalPriceAdjustmentType");
				if(CommonUserUtility.paparameterNullCheckStringObject(generalPriceAdjustmentType)){
					valouxItemComponentModel.setGeneralPriceAdjustmentType(Byte.valueOf(generalPriceAdjustmentType));
				}
				
				valouxItemComponentModel.setGeneralPriceAdjustmentValue(JSONUtility.getSafeString(priceProperty, "generalPriceAdjustmentValue"));
				
				String marketValue = JSONUtility.getSafeString(priceProperty, "marketValue");
				if(CommonUserUtility.paparameterNullCheckStringObject(marketValue)){
					valouxItemComponentModel.setMarketValue(Double.valueOf(marketValue));
				}
				
				String purchasePrice = JSONUtility.getSafeString(priceProperty, "purchasePrice");
				if(CommonUserUtility.paparameterNullCheckStringObject(purchasePrice)){
					valouxItemComponentModel.setPurchasePrice(Double.valueOf(purchasePrice));
				}
				
			} else if(componentType.equals(CommonConstants.COMPONENT_PEARLS) || componentType.equals(CommonConstants.COMPONENT_GEMSTONES)){
				String purchasePrice = JSONUtility.getSafeString(priceProperty, "purchasePrice");
				if(CommonUserUtility.paparameterNullCheckStringObject(purchasePrice)){
					valouxItemComponentModel.setPurchasePrice(Double.valueOf(purchasePrice));
					valouxItemComponentModel.setFinalPrice(Double.valueOf(purchasePrice));
					valouxItemComponentModel.setMarketValue(Double.valueOf(purchasePrice));
				}
			}
		}
		
		LOGGER.info("ItemWS : Exit method populateUpdateValouxItemComponentModelPriceDetails");
	}

	/**
	 * @paparam itemService
	 * @paparam collectionService
	 * @paparam sharedRequestBean
	 * @paparam resJson
	 * @throws Exception
	 */
	public static void getItemComponentAppraisalDetail(ItemService itemService, CollectionService collectionService, AppraisalService appraisalService, ValouxSharedRequestBean sharedRequestBean, JSONObject resJson,Boolean isAgent)
			throws Exception {
		LOGGER.info("ItemWS : Enter method getItemComponentAppraisalDetail");
		Map<Integer, Integer> countMap = itemService.getNumberOfAgentAndConsumerToWhichItemIsShared(sharedRequestBean
				.getSharedItemId(),sharedRequestBean.getSharedItemType());
		if (sharedRequestBean.getSharedItemType() == 1) {
			List<ItemImageModel> itemImageModelList = new ArrayList<ItemImageModel>();
			JSONArray imageJArray = new JSONArray();
			ValouxItemModel itemModel = itemService.getValouxItemDetailsById(sharedRequestBean.getSharedItemId());
			resJson.put("name", itemModel.getName());
			resJson.put("id", itemModel.getItemId());
			resJson.put("description", itemModel.getsDescription());
			resJson.put("designType", itemModel.getDesignType());
			resJson.put("jewelryGender", itemModel.getGender());
			resJson.put("quantity", itemModel.getQuantity());
			resJson.put("itemType", itemModel.getItemTypeIt());
			resJson.put("sharedItemType", sharedRequestBean.getSharedItemType());
			resJson.put("itemTypeName", itemModel.getItemTypeName());
			addPricesOfItemToJson(itemModel, resJson,isAgent);
			itemImageModelList = itemService.getItemImageModelList(itemModel.getItemId());
			if (itemImageModelList != null && itemImageModelList.size() > 0) {
				for (ItemImageModel itemImageModel : itemImageModelList) {
					if (itemImageModel.getImageType() == 1) {
						JSONObject imageJObject = new JSONObject();
						imageJObject.put("imagePath", itemImageModel.getImageurl());
						imageJObject.put("imageId", itemImageModel.getImageId());
						imageJArray.put(imageJObject);
					}
				}
				resJson.put("images", imageJArray);
			}
			getComponentsByItemId(itemService, resJson, itemModel.getItemId());
			if (countMap != null) {
				if (countMap.get(1) != 0) {
					resJson.put("sharedWithNumberOfConsumer", countMap.get(1));
				}
				if (countMap.get(2) != 0) {
					resJson.put("sharedWithNumberOfAgent", countMap.get(2));
				}
				if(countMap.get(3)!=0){
					resJson.put("sharedWithNumberOfUnregisteredUser", countMap.get(3));
				}
			}
			int noOfAppraisal = 0;
				if(	appraisalService.getAppraisalIdAssociatedWithItem(itemModel.getItemId())!=null){
					noOfAppraisal = appraisalService.getAppraisalIdAssociatedWithItem(itemModel.getItemId()).length();
				}
			resJson.put("noOfAppraisal",noOfAppraisal);
			int noOfCollection = 0;
			if(collectionService.getCollectionIdAssociatedWithItem(itemModel.getItemId())!=null){
				noOfCollection = collectionService.getCollectionIdAssociatedWithItem(itemModel.getItemId()).length();
			}
			resJson.put("noOfCollection",noOfCollection);
			if(sharedRequestBean.getSharedBy()!=null && sharedRequestBean.getSharedTo()!=null){
				List<ValouxCollectionModel> collectionModelList = itemService.getAgentSharedCollectionNotInItem(sharedRequestBean.getSharedTo(), itemModel.getItemId(),sharedRequestBean.getSharedBy());
			if(collectionModelList!=null && collectionModelList.size()>0){
				resJson.put("collectionFlag", true);
			}else{
				resJson.put("collectionFlag", false);
			}
			List<AppraisalBean> appraisalBeanList = itemService.getAgentSharedAppraisalsNotInItem(sharedRequestBean.getSharedTo(), itemModel.getItemId(),sharedRequestBean.getSharedBy());
			if(appraisalBeanList!=null && appraisalBeanList.size()>0){
				resJson.put("appraisalFlag", true);
			}else{
				resJson.put("appraisalFlag", false);
			}	
			
			
			}

		}
		if (sharedRequestBean.getSharedItemType() == 2) {
			ValouxCollectionModel collectionBean = collectionService.getCollectionDetailsById(sharedRequestBean
					.getSharedItemId());
			CollectionHelper.getPricesOfCollection(collectionService, itemService, resJson, collectionBean,isAgent);
			if (collectionBean != null) {
				resJson.put("id", collectionBean.getVcid());
				resJson.put("name", collectionBean.getCname());
				resJson.put("description", collectionBean.getShortDescription());
				resJson.put("cStatus", collectionBean.getCollectionStatus());
				resJson.put("sharedItemType", sharedRequestBean.getSharedItemType());
				List<ValouxCollectionImageBean> collectionImageBeanList = collectionService
						.getCollectionImageDetailsById(collectionBean.getVcid());
				JSONArray jImageArray = new JSONArray();
				if (collectionImageBeanList != null && collectionImageBeanList.size() > 0) {

					for (ValouxCollectionImageBean valouxCollectionImageBean : collectionImageBeanList) {
						JSONObject imageJson = new JSONObject();
						imageJson.put("imageId", valouxCollectionImageBean.getId());
						imageJson.put("imagePath", valouxCollectionImageBean.getImgUrl());
						imageJson.put("imageName", valouxCollectionImageBean.getImgName());
						jImageArray.put(imageJson);
					}
				}

				resJson.put("images", jImageArray);
				if (countMap != null) {
					if (countMap.get(1) != 0) {
						resJson.put("sharedWithNumberOfConsumer", countMap.get(1));
					}
					if (countMap.get(2) != 0) {
						resJson.put("sharedWithNumberOfAgent", countMap.get(2));
					}
					if(countMap.get(3)!=0){
						resJson.put("sharedWithNumberOfUnregisteredUser", countMap.get(3));
					}
				}
				
				List<AppraisalCollectionModel> appraisalCollectionBeans = appraisalService
						.getAppraisalCollectionBeansByCollectionId(collectionBean.getVcid());
				int appraisalCount = 0;
				if(appraisalCollectionBeans!=null && appraisalCollectionBeans.size()>0){
					appraisalCount = appraisalCollectionBeans.size();
				}
				resJson.put("noOfAppraisal", appraisalCount);
				// adding collection item images
				getItemsImagesOfCollectionById(itemService, collectionService, resJson, collectionBean.getVcid());
				
				if(sharedRequestBean.getSharedBy()!=null && sharedRequestBean.getSharedTo()!=null){
					List<ValouxItemModel> itemModelList = itemService.getAgentSharedItemsNotInCollection(sharedRequestBean.getSharedTo(), collectionBean.getVcid(),sharedRequestBean.getSharedBy());
				if(itemModelList!=null && itemModelList.size()>0){
					resJson.put("itemFlag", true);
				}else{
					resJson.put("itemFlag", false);
				}
				List<AppraisalModel> appraisalModelList = itemService.getAgentSharedAppraisalsNotInCollection(sharedRequestBean.getSharedTo(), collectionBean.getVcid(),sharedRequestBean.getSharedBy());
				if(appraisalModelList!=null && appraisalModelList.size()>0){
					resJson.put("appraisalFlag", true);
				}else{
					resJson.put("appraisalFlag", false);
				}	
				
				
				}
			}
		}
		if (sharedRequestBean.getSharedItemType() == 3) {
			AppraisalBean appraisalBean = appraisalService.getAppraisalBeanById(sharedRequestBean.getSharedItemId());
			
			if(appraisalBean != null) {
				resJson.put("publicKey", appraisalBean.getRelationKey());
				resJson.put("name", appraisalBean.getName());
				resJson.put("description", appraisalBean.getShortDescription());
				resJson.put("appraisalStatus", appraisalBean.getaStatus());
				resJson.put("id", appraisalBean.getAppraisalId());
				resJson.put("sharedItemType", sharedRequestBean.getSharedItemType());
				if (countMap != null) {
					if (countMap.get(1) != 0) {
						resJson.put("sharedWithNumberOfConsumer", countMap.get(1));
					}
					if (countMap.get(2) != 0) {
						resJson.put("sharedWithNumberOfAgent", countMap.get(2));
					}
					if(countMap.get(3)!=0){
						resJson.put("sharedWithNumberOfUnregisteredUser", countMap.get(3));
					}
				}
				if(sharedRequestBean.getSharedBy()!=null && sharedRequestBean.getSharedTo()!=null){
					List<ValouxItemModel> itemModelList = itemService.getAgentSharedItemsNotInAppraisal(sharedRequestBean.getSharedTo(), appraisalBean.getAppraisalId(),sharedRequestBean.getSharedBy());
				if(itemModelList!=null && itemModelList.size()>0){
					resJson.put("itemFlag", true);
				}else{
					resJson.put("itemFlag", false);
				}
				
				List<ValouxCollectionModel> collectionModelList = itemService.getAgentSharedCollectionNotInAppraisal(sharedRequestBean.getSharedTo(),appraisalBean.getAppraisalId(),sharedRequestBean.getSharedBy());
				if(collectionModelList!=null && collectionModelList.size()>0){
					resJson.put("collectionFlag", true);
				}else{
					resJson.put("collectionFlag", false);
				}	
				}
				AppraisalModel appraisalModel = new AppraisalModel();
				appraisalModel.setAppraisalId(appraisalBean.getAppraisalId());
				appraisalModel.setName(appraisalBean.getName());
				appraisalModel.setShortDescription(appraisalBean.getShortDescription());
				appraisalModel.setaStatus(appraisalBean.getaStatus());
				appraisalModel.setApprovedBy(appraisalBean.getApprovedBy());
				appraisalModel.setApprovedOn(appraisalBean.getApprovedOn());
				appraisalModel.setCreatedBy(appraisalBean.getCreatedBy());
				appraisalModel.setCreatedOn(appraisalBean.getCreatedOn());
				appraisalModel.setModifiedBy(appraisalBean.getModifiedBy());
				appraisalModel.setModifiedOn(appraisalBean.getModifiedOn());
				appraisalModel.setRelationKey(appraisalBean.getRelationKey());
				appraisalModel.setLastAppraisaedPrice(appraisalBean.getLastAppraisaedPrice());
				AppraisalHelper.addPriceOfAppraisal(appraisalModel, resJson, appraisalService, itemService, collectionService,isAgent);
			}
		}
		LOGGER.info("ItemWS : Exit method getItemComponentAppraisalDetail");
	}

	/**
	 * Method for fetching item images of collection
	 * 
	 * @paparam cObject
	 * @paparam collectionId
	 * @paparam isCount
	 * @throws Exception
	 */
	public static void getItemsImagesOfCollectionById(ItemService itemService, CollectionService collectionService, JSONObject cObject, Integer collectionId) throws Exception {
		LOGGER.info("ItemWS : Enter method getItemsImagesOfCollectionById");
		try {
			List<ValouxCollectionItemModel> itemModels = collectionService.getUserCollectionItemsList(collectionId);

			JSONArray jArray = new JSONArray();
			JSONArray imageJArray = null;
			if (itemModels != null && itemModels.size() > 0) {
				for (ValouxCollectionItemModel collectionitemModel : itemModels) {

					List<ItemImageModel> itemImageModelList = new ArrayList<ItemImageModel>();

					ValouxItemModel itemModel = itemService.getValouxItemDetailsById(collectionitemModel.getItemId());

					if (itemModel != null) {
						imageJArray = new JSONArray();
						JSONObject jObject = new JSONObject();
						// jObject.put("itemId", itemModel.getItemId());

						itemImageModelList = itemService.getItemImageModelList(itemModel.getItemId());

						if (itemImageModelList != null && itemImageModelList.size() > 0) {
							for (ItemImageModel itemImageModel : itemImageModelList) {

								if (itemImageModel.getImageType().equals(1)) {
									JSONObject imageJObject = new JSONObject();
									imageJObject.put("itemImagePath", itemImageModel.getImageurl());
									imageJObject.put("imageId", itemImageModel.getImageId());
									imageJArray.put(imageJObject);
								}
								if (itemImageModel.getImageType().equals(2)) {
									jObject.put("itemReceiptPath", itemImageModel.getImageurl());
									jObject.put("itemReceiptId", itemImageModel.getImageId());
								}
								if (itemImageModel.getImageType().equals(3)) {
									jObject.put("itemCertificatePath", itemImageModel.getImageurl());
									jObject.put("itemCertificateId", itemImageModel.getImageId());
								}
							}
							jObject.put("itemImages", imageJArray);
							jArray.put(jObject);
						}
					}
				}
			}
			cObject.put("items", jArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("ItemWS : Exit method getItemsImagesOfCollectionById");
	}
	
	/**
	 * Method for fetching item component property
	 * 
	 * @paparam reqObject
	 * @paparam resJson
	 * @throws Exception
	 */
	public static void getValouxItemComponentProperty(ItemService itemService, JSONObject reqObject, JSONObject resJson) throws Exception {
		LOGGER.info("ItemWS : Enter method getValouxItemComponentProperty");
		Integer itemId = JSONUtility.getSafeInteger(reqObject, "itemId");
		Integer componentId = JSONUtility.getSafeInteger(reqObject, "itemComponentId");

		ValouxItemComponentModel valouxItemComponentModel = itemService.getComponentsByItemAndComponentId(itemId,
				componentId);
		if (valouxItemComponentModel != null) {
			JSONObject componentObject = new JSONObject();
			if (Integer.valueOf(valouxItemComponentModel.getComponentsType()).equals(CommonConstants.COMPONENT_DIAMOND)) {
				// Component type 1 - Diamond

				componentObject = getItemComponentDiamondProperty(itemService, itemId, componentId, resJson);
				getItemComponentPropertyCertificate(itemService, itemId, componentId, componentObject);

			} else if (Integer.valueOf(valouxItemComponentModel.getComponentsType()).equals(CommonConstants.COMPONENT_GEMSTONES)) {
				// Component type 2 - Gemstones
				
				componentObject = getItemComponentGemstonesProperty(itemService, itemId, componentId, resJson);

			} else if (Integer.valueOf(valouxItemComponentModel.getComponentsType()).equals(CommonConstants.COMPONENT_PEARLS)) {
				// Component type 3 - Pearls
				
				componentObject = getItemComponentPearlsProperty(itemService, itemId, componentId, resJson);

			} else if (Integer.valueOf(valouxItemComponentModel.getComponentsType()).equals(CommonConstants.COMPONENT_METALS)) {
				// Component type 4 - Metals
				
				componentObject = getItemComponentMetalsProperty(itemService, itemId, componentId, resJson);

			}
			// Item component price property
			getItemComponentPricePropertyDetails(valouxItemComponentModel, componentObject);
			
			// Item component images
			getItemComponentPropertyImages(itemService, componentId, componentObject);
			resJson.put("componentProperty", componentObject);
		}
		LOGGER.info("ItemWS : Exit method getValouxItemComponentProperty");
	}

	/**
	 * Method for fetching item component property price
	 * @paparam valouxItemComponentModel
	 * @paparam componentObject
	 * @throws Exception
	 */
	private static void getItemComponentPricePropertyDetails(ValouxItemComponentModel valouxItemComponentModel,
			JSONObject componentObject) throws Exception {
		
		JSONObject compoJson = new JSONObject();
		if(valouxItemComponentModel != null){
			compoJson.put("adjustmentPrice", valouxItemComponentModel.getAdjustmentPrice());
			compoJson.put("brandPriceAdjustmentNotes", valouxItemComponentModel.getBrandPriceAdjustmentNotes());
			compoJson.put("brandPriceAdjustmentOperator", valouxItemComponentModel.getBrandPriceAdjustmentOperator());
			compoJson.put("brandPriceAdjustmentType", valouxItemComponentModel.getBrandPriceAdjustmentType());
			compoJson.put("brandPriceAdjustmentValue", valouxItemComponentModel.getBrandPriceAdjustmentValue());
			compoJson.put("currentUnitPrice", valouxItemComponentModel.getCurrentUnitPrice());
			compoJson.put("finalPrice", valouxItemComponentModel.getFinalPrice());
			compoJson.put("generalPriceAdjustmentNotes", valouxItemComponentModel.getGeneralPriceAdjustmentNotes());
			compoJson.put("generalPriceAdjustmentOperator", valouxItemComponentModel.getGeneralPriceAdjustmentOperator());
			compoJson.put("generalPriceAdjustmentType", valouxItemComponentModel.getGeneralPriceAdjustmentType());
			compoJson.put("generalPriceAdjustmentValue", valouxItemComponentModel.getGeneralPriceAdjustmentValue());
			compoJson.put("marketValue", valouxItemComponentModel.getMarketValue());
			compoJson.put("purchasePrice", valouxItemComponentModel.getPurchasePrice());
			compoJson.put("quantity", valouxItemComponentModel.getQuantity());
		}
		componentObject.put("priceProperty", compoJson);
	}

	/**
	 * Method for fetching item component property metal
	 * @paparam itemService
	 * @paparam itemId
	 * @paparam componentId
	 * @paparam resJson
	 * @return 
	 */
	private static JSONObject getItemComponentMetalsProperty(
			ItemService itemService, Integer itemId, Integer componentId,
			JSONObject resJson) throws Exception {
		LOGGER.info("ItemWS : Enter method getItemComponentMetalsProperty");
		JSONObject compoJson = new JSONObject();
		ValouxMetalModel metalModel = itemService.getComponentMetalBeanByItemAndComponentId(itemId, componentId);
		if (metalModel != null) {
			ItemHelper.getResponseItemComponentMetalProperty(compoJson, metalModel);
		}
//		resJson.put("componentProperty", compoJson);
		LOGGER.info("ItemWS : Exit method getItemComponentMetalsProperty");
		return compoJson;
	}

	/**
	 * Method for fetching item component property item response
	 * @paparam compoJson
	 * @paparam metalModel
	 * @throws Exception
	 */
	private static void getResponseItemComponentMetalProperty(
			JSONObject compoJson, ValouxMetalModel metalModel) throws Exception {
		LOGGER.info("ItemHelper : Enter Method getResponseItemComponentMetalProperty");
		if (metalModel != null) {
			compoJson.put("vmid", metalModel.getVmid());
			compoJson.put("certificateId", metalModel.getCertificateId());
			compoJson.put("color", metalModel.getColor());
			compoJson.put("colorName", ItemComponentHelper.METAL_GOLD_COLOR_MAP.get(metalModel.getColor()));
			compoJson.put("createdBy", metalModel.getCreatedBy());
			compoJson.put("createdOn", metalModel.getCreatedOn());
			compoJson.put("itemComponentId", metalModel.getItemComponentId());
			compoJson.put("itemId", metalModel.getItemId());
			compoJson.put("marketValue", metalModel.getMarketValue());
			compoJson.put("measurements", metalModel.getMeasurements());
			compoJson.put("metalsType", metalModel.getMetalsType());
			compoJson.put("metalsTypeName", ItemComponentHelper.METAL_NAME_MAP.get(metalModel.getMetalsType()));
			compoJson.put("modifiedBy", metalModel.getModifiedBy());
			compoJson.put("modifiedOn", metalModel.getModifiedOn());
			compoJson.put("purity", metalModel.getPurity());
			compoJson.put("purityName", ItemComponentHelper.METAL_PURITY_MAP.get(metalModel.getPurity()));
			compoJson.put("status", metalModel.getStatus());
			compoJson.put("typeDetermined", metalModel.getTypeDetermined());
			if(metalModel.getTypeDetermined()!=null && metalModel.getTypeDetermined() > 0){
				compoJson.put("typeDeterminedName", ItemComponentHelper.METAL_TYPE_DETERMINED_MAP.get(metalModel.getTypeDetermined().intValue()));
			}
			compoJson.put("typeSpecified", metalModel.getTypeSpecified());
			compoJson.put("weight", metalModel.getWeight());
			compoJson.put("appraisedValue", metalModel.getAppraisedValue());
		}
		LOGGER.info("ItemHelper : Exit Method getResponseItemComponentMetalProperty");
	}

	/**
	 * Method for fetching item component property pearl
	 * @paparam itemService
	 * @paparam itemId
	 * @paparam componentId
	 * @paparam resJson
	 * @return 
	 */
	private static JSONObject getItemComponentPearlsProperty(
			ItemService itemService, Integer itemId, Integer componentId,
			JSONObject resJson) throws Exception {
		LOGGER.info("ItemWS : Enter method getItemComponentPearlsProperty");
		JSONObject compoJson = new JSONObject();
		ValouxPearlModel pearlModel = itemService.getComponentPearlBeanByItemAndComponentId(itemId, componentId);
		if (pearlModel != null) {
			ItemHelper.getResponseItemComponentPearlProperty(compoJson, pearlModel);
		}
//		resJson.put("componentProperty", compoJson);
		LOGGER.info("ItemWS : Exit method getItemComponentPearlsProperty");
		return compoJson;
	}

	/**
	 * Method for fetching item component property pearl
	 * @paparam compoJson
	 * @paparam pearlModel
	 * @throws Exception
	 */
	private static void getResponseItemComponentPearlProperty(
			JSONObject compoJson, ValouxPearlModel pearlModel) throws Exception {
		LOGGER.info("ItemHelper : Enter Method getResponseItemComponentPearlProperty");
		if (pearlModel != null) {
			compoJson.put("vpid", pearlModel.getVpid());
			compoJson.put("blemish", pearlModel.getBlemish());
			compoJson.put("blemishName", ItemComponentHelper.PEARL_BLEMISH_MAP.get(pearlModel.getBlemish()));
			compoJson.put("color", pearlModel.getColor());
			compoJson.put("colorName",  ItemComponentHelper.PEARL_COLOR_MAP.get(pearlModel.getColor()));
			compoJson.put("composition", pearlModel.getComposition());
			compoJson.put("compositionName", ItemComponentHelper.PEARL_COMPOSITION_MAP.get(pearlModel.getComposition()));
			compoJson.put("createdBy", pearlModel.getCreatedBy());
			compoJson.put("createdOn", pearlModel.getCreatedOn());
			compoJson.put("drilled", pearlModel.getDrilled());
			compoJson.put("drilledName", ItemComponentHelper.PEARL_DRILLED_MAP.get(pearlModel.getDrilled()));
			compoJson.put("enhancements", pearlModel.getEnhancements());
			compoJson.put("enhancementsName", ItemComponentHelper.PEARL_ENHANCEMENTS_MAP.get(pearlModel.getEnhancements()));
			compoJson.put("itemComponentId", pearlModel.getItemComponentId());
			compoJson.put("itemId", pearlModel.getItemId());
			compoJson.put("pearlsLength", pearlModel.getPearlsLength());
			compoJson.put("luster", pearlModel.getLuster());
			compoJson.put("lusterName", ItemComponentHelper.COMPONENT_QUALITY_MAP.get(pearlModel.getLuster()));
			compoJson.put("matching", pearlModel.getMatching());
			compoJson.put("matchingName", ItemComponentHelper.COMPONENT_QUALITY_MAP.get(pearlModel.getMatching()));
			compoJson.put("pearlsMax", pearlModel.getPearlsMax());
			compoJson.put("measurements", pearlModel.getMeasurements());
			compoJson.put("pearlsMin", pearlModel.getPearlsMin());
			compoJson.put("modifiedBy", pearlModel.getModifiedBy());
			compoJson.put("modifiedOn", pearlModel.getModifiedOn());
			compoJson.put("origin", pearlModel.getOrigin());
			compoJson.put("originName", pearlModel.getOriginName());
			compoJson.put("pearlType", pearlModel.getPearlType());
			compoJson.put("pearlTypeName", ItemComponentHelper.PEARL_TYPE_MAP.get(pearlModel.getPearlType()));
			compoJson.put("shape", pearlModel.getShape());
			compoJson.put("shapeName", ItemComponentHelper.COMPONENT_SHAPE_MAP.get(pearlModel.getShape()));
			compoJson.put("status", pearlModel.getStatus());
			compoJson.put("pearlsStyle", pearlModel.getPstyle());
			compoJson.put("pearlsStyleName", ItemComponentHelper.PEARL_STYLE_MAP.get(pearlModel.getPstyle()));
			compoJson.put("styleUserEntered", pearlModel.getStyleUserEntered());
			compoJson.put("weight", pearlModel.getWeight());
			compoJson.put("appraisedValue", pearlModel.getAppraisedValue());
		}
		LOGGER.info("ItemHelper : Exit Method getResponseItemComponentPearlProperty");
	}

	/**
	 * Method for fetching item component property gemstone
	 * @paparam itemService
	 * @paparam itemId
	 * @paparam componentId
	 * @paparam resJson
	 * @return 
	 */
	private static JSONObject getItemComponentGemstonesProperty(
			ItemService itemService, Integer itemId, Integer componentId,
			JSONObject resJson) throws Exception {
		LOGGER.info("ItemWS : Enter method getItemComponentGemstonesProperty");  
		JSONObject compoJson = new JSONObject();
		ValouxGemstoneModel gemstoneModel = itemService.getComponentGemstoneBeanByItemAndComponentId(itemId, componentId);
		if (gemstoneModel != null) {
			ItemHelper.getResponseItemComponentGemstoneProperty(compoJson, gemstoneModel);
		}
//		resJson.put("componentProperty", compoJson);
		LOGGER.info("ItemWS : Exit method getItemComponentGemstonesProperty");
		return compoJson;
	}

	/**
	 * Method for fetching item component property gemstone response
	 * @paparam compoJson
	 * @paparam gemstoneModel
	 * @throws Exception
	 */
	private static void getResponseItemComponentGemstoneProperty(
			JSONObject compoJson, ValouxGemstoneModel gemstoneModel) throws Exception {
		LOGGER.info("ItemHelper : Enter Method getResponseItemComponentGemstoneProperty");
		if (gemstoneModel != null) {
			compoJson.put("vgid", gemstoneModel.getVgid());
			compoJson.put("createdBy", gemstoneModel.getCreatedBy());
			compoJson.put("createdOn", gemstoneModel.getCreatedOn());
			compoJson.put("cut", gemstoneModel.getCut());
			compoJson.put("cutName", ItemComponentHelper.GEMSTONE_CUT_MAP.get(gemstoneModel.getCut()));
			compoJson.put("enhancement", gemstoneModel.getEnhancement());
			compoJson.put("enhancementName", ItemComponentHelper.GEMSTONE_ENHANCEMENT_MAP.get(gemstoneModel.getEnhancement()));
			compoJson.put("gemstonesType", gemstoneModel.getGemstonesType());
			compoJson.put("gemstonesTypeName", ItemComponentHelper.GEMSTONE_TYPE_MAP.get(gemstoneModel.getGemstonesType()));
			compoJson.put("itemComponentId", gemstoneModel.getItemComponentId());
			compoJson.put("itemId", gemstoneModel.getItemId());
			compoJson.put("measurements", gemstoneModel.getMeasurements());
			compoJson.put("modifiedBy", gemstoneModel.getModifiedBy());
			compoJson.put("modifiedOn", gemstoneModel.getModifiedOn());
			compoJson.put("origin", gemstoneModel.getOrigin());
			compoJson.put("originName", gemstoneModel.getOriginName());
			compoJson.put("placement", gemstoneModel.getPlacement());
			compoJson.put("placementName", ItemComponentHelper.COMPONENT_PLACEMENT_MAP.get(gemstoneModel.getPlacement()));
			compoJson.put("shape", gemstoneModel.getShape());
			compoJson.put("shapeName", ItemComponentHelper.COMPONENT_SHAPE_MAP.get(gemstoneModel.getShape()));
			compoJson.put("status", gemstoneModel.getStatus());
			compoJson.put("weight", gemstoneModel.getWeight());
			
			compoJson.put("internalInclusions", gemstoneModel.getInternalInclusions());
			StringBuffer internalbuffer = new StringBuffer();
			
			if(gemstoneModel.getInternalInclusions() != null && gemstoneModel.getInternalInclusions().length() > 0){
				
				for (int count = 0; count < gemstoneModel.getInternalInclusions().length(); count++) {
					String name = ItemComponentHelper.INTERNAL_INCLUSION_MAP.get((Integer)gemstoneModel.getInternalInclusions().get(count));
					internalbuffer.append(name);
					if(count < gemstoneModel.getInternalInclusions().length() - 1){
						internalbuffer.append(", ");
					}
				}
				compoJson.put("internalInclusionsName", internalbuffer.toString());
			}
			
			compoJson.put("externalInclusions", gemstoneModel.getExternalInclusions());
			
			StringBuffer externalbuffer = new StringBuffer();
			if(gemstoneModel.getExternalInclusions() != null && gemstoneModel.getExternalInclusions().length() > 0){
				
				for (int count = 0; count < gemstoneModel.getExternalInclusions().length(); count++) {
					String name = ItemComponentHelper.EXTERNAL_INCLUSION_MAP.get((Integer)gemstoneModel.getExternalInclusions().get(count));
					externalbuffer.append(name);
					if(count < gemstoneModel.getExternalInclusions().length() - 1){
						externalbuffer.append(", ");
					}
				}
				compoJson.put("externalInclusionsName", externalbuffer.toString());
			}
		}
		LOGGER.info("ItemHelper : Exit Method getResponseItemComponentGemstoneProperty");
	}

	/**
	 * Method for fetching item component images
	 * 
	 * @paparam itemId
	 * @paparam componentId
	 * @paparam resJson
	 * @throws Exception
	 */
	public static void getItemComponentPropertyImages(ItemService itemService, Integer componentId, JSONObject resJson)
			throws Exception {
		LOGGER.info("ItemWS : Enter method getItemComponentPropertyImages");
		List<ValouxComponentsImageModel> imageModels = itemService
				.getComponentsImageModelByComponentIdAndType(componentId);

		ItemHelper.getResponseItemComponentPropertyImages(resJson, imageModels);
		LOGGER.info("ItemWS : Exit method getItemComponentPropertyImages");
	}

	/**
	 * Method for fetching item component property diamond
	 * 
	 * @paparam itemId
	 * @paparam componentId
	 * @paparam resJson
	 * @return 
	 * @throws Exception
	 */
	public static JSONObject getItemComponentDiamondProperty(ItemService itemService, Integer itemId, Integer componentId, JSONObject resJson)
			throws Exception {
		LOGGER.info("ItemWS : Enter method getItemComponentDiamondProperty");
		JSONObject compoJson = new JSONObject();
		ValouxDiamondModel diamondModel = itemService.getComponentDiamondBeanByItemAndComponentId(itemId, componentId);
		if (diamondModel != null) {
			ItemHelper.getResponseItemComponentDiamondProperty(compoJson, diamondModel);
		}
//		resJson.put("componentProperty", compoJson);
		LOGGER.info("ItemWS : Exit method getItemComponentDiamondProperty");
		return compoJson;
	}

	/**
	 * Method for fetching item component property certificate
	 * 
	 * @paparam itemId
	 * @paparam componentId
	 * @paparam resJson
	 * @throws Exception
	 */
	public static void getItemComponentPropertyCertificate(ItemService itemService, Integer itemId, Integer componentId, JSONObject resJson)
			throws Exception {
		LOGGER.info("ItemWS : Enter method getItemComponentPropertyCertificate");
		JSONObject compoJson = new JSONObject();

		ItemComponentCertificateModel certificateModel = itemService.getComponentCertificateBeanByItemAndComponentId(
				itemId, componentId);
		if (certificateModel != null) {
			ItemHelper.getResponseItemComponentPropertyCertificate(compoJson, certificateModel);
		}
		resJson.put("componentCertificate", compoJson);
		LOGGER.info("ItemWS : Exit method getItemComponentPropertyCertificate");
	}
	
	/**
	 * 
	 * @paparam itemService
	 * @paparam itemBeanList
	 * @paparam resJson
	 * @throws Exception
	 */
	public static void getItemsDetail(ItemService itemService, List<ValouxItemModel> itemBeanList, JSONObject resJson,CollectionService collectionService,AppraisalService appraisalService,UserService userService,Boolean isAgent)
			throws Exception {
		LOGGER.info("ItemWS : Enter method getItemsDetail");
		JSONArray jArray = new JSONArray();
		if (itemBeanList!=null) {
			for(ValouxItemModel itemModel : itemBeanList){
			List<ItemImageModel> itemImageModelList = new ArrayList<ItemImageModel>();
			JSONArray imageJArray = new JSONArray();
			JSONObject itemJson = new JSONObject();
			itemJson.put("consumerPublicKey", itemModel.getrKey());
			itemJson.put("name", itemModel.getName());
			itemJson.put("id", itemModel.getItemId());
			itemJson.put("description", itemModel.getsDescription());
			itemJson.put("designType", itemModel.getDesignType());
			itemJson.put("jewelryGender", itemModel.getGender());
			itemJson.put("quantity", itemModel.getQuantity());
			itemJson.put("itemType", itemModel.getItemTypeIt());
			addPricesOfItemToJson(itemModel, itemJson,isAgent);
			itemImageModelList = itemService.getItemImageModelList(itemModel.getItemId());
			if (itemImageModelList != null && itemImageModelList.size() > 0) {
				for (ItemImageModel itemImageModel : itemImageModelList) {
					if (itemImageModel.getImageType() == 1) {
						JSONObject imageJObject = new JSONObject();
						imageJObject.put("imagePath", itemImageModel.getImageurl());
						imageJObject.put("imageId", itemImageModel.getImageId());
						imageJArray.put(imageJObject);
					}
				}
				itemJson.put("images", imageJArray);
			}
			getComponentsByItemId(itemService, itemJson, itemModel.getItemId());
			int appraisalCount = 0;
			if(appraisalService.getAppraisalIdAssociatedWithItem(itemModel.getItemId())!=null){
				appraisalCount = appraisalService.getAppraisalIdAssociatedWithItem(itemModel.getItemId()).length();
			}
			itemJson.put("noOfAppraisal",appraisalCount);
			int collectionCount = 0;
			if(collectionService.getCollectionIdAssociatedWithItem(itemModel.getItemId())!=null){
				collectionCount = collectionService.getCollectionIdAssociatedWithItem(itemModel.getItemId()).length();
			}
			itemJson.put("noOfCollection",collectionCount);
			String pKey = (itemModel.getrKey());
			LoginModel loginModel = userService.getLoginDetailByPKey(pKey);
			JSONObject consumerDetail = new JSONObject();
			if(loginModel!=null){
				consumerDetail.put("firstName", loginModel.getFirstName());
				consumerDetail.put("middleName", loginModel.getMiddleName());
				consumerDetail.put("lastName", loginModel.getLastName());
			}
			itemJson.put("consumerDeatil", consumerDetail);
			jArray.put(itemJson);
			}
			
			}
		resJson.put("itemDetail", jArray);
		
		LOGGER.info("ItemWS : Exit method getItemsDetail");
	}

	/**
	 * 
	 * @paparam itemService
	 * @paparam collectionService
	 * @paparam collectionBeanList
	 * @paparam resJson
	 * @throws Exception
	 */
	public static void getCollectionDetail(ItemService itemService, CollectionService collectionService,List<ValouxCollectionModel> collectionBeanList, JSONObject resJson,AppraisalService appraisalService,UserService userService,Boolean isAgent)
			throws Exception {
		LOGGER.info("ItemWS : Enter method getCollectionDetail");
		JSONArray jArray = new JSONArray();
		
		if (collectionBeanList!=null) {
			for(ValouxCollectionModel collectionBean : collectionBeanList) {
				JSONObject collectionJson = new JSONObject();
				collectionJson.put("consumerPublicKey", collectionBean.getRkey());
				collectionJson.put("id", collectionBean.getVcid());
				collectionJson.put("name", collectionBean.getCname());
				collectionJson.put("description", collectionBean.getShortDescription());
				collectionJson.put("cStatus", collectionBean.getCollectionStatus());
				CollectionHelper.getPricesOfCollection(collectionService, itemService, collectionJson, collectionBean,isAgent);
				List<ValouxCollectionImageBean> collectionImageBeanList = collectionService
						.getCollectionImageDetailsById(collectionBean.getVcid());
				JSONArray jImageArray = new JSONArray();
				if (collectionImageBeanList != null && collectionImageBeanList.size() > 0) {

					for (ValouxCollectionImageBean valouxCollectionImageBean : collectionImageBeanList) {
						JSONObject imageJson = new JSONObject();
						imageJson.put("imageId", valouxCollectionImageBean.getId());
						imageJson.put("imagePath", valouxCollectionImageBean.getImgUrl());
						imageJson.put("imageName", valouxCollectionImageBean.getImgName());
						jImageArray.put(imageJson);
					}
				}

				collectionJson.put("images", jImageArray);
				
				List<ValouxCollectionItemModel> itemModels = collectionService
						.getUserCollectionItemsList(collectionBean.getVcid());
				int itemCount = 0;
				if (itemModels != null && itemModels.size() > 0) {
					itemCount = itemModels.size();
				}
				collectionJson.put("noOfItem", itemCount);
				int appraisalCount = 0;
				if(appraisalService.getAppraisalCollectionBeansByCollectionId(collectionBean.getVcid())!=null){
					appraisalCount = appraisalService
							.getAppraisalCollectionBeansByCollectionId(collectionBean.getVcid()).size();
				}
				collectionJson.put("noOfAppraisal", appraisalCount);

				// adding collection item images
				getItemsImagesOfCollectionById(itemService, collectionService, collectionJson, collectionBean.getVcid());
				String pKey = (collectionBean.getRkey());
				LoginModel loginModel = userService.getLoginDetailByPKey(pKey);
				JSONObject consumerDetail = new JSONObject();
				if(loginModel!=null){
					consumerDetail.put("firstName", loginModel.getFirstName());
					consumerDetail.put("middleName", loginModel.getMiddleName());
					consumerDetail.put("lastName", loginModel.getLastName());
				}
				collectionJson.put("consumerDeatil", consumerDetail);
				jArray.put(collectionJson);
			}
			
		}
		resJson.put("collectionDetail", jArray);
		
		LOGGER.info("ItemWS : Exit method getCollectionDetail");
	}

	/**
	 * 
	 * @paparam appraisalService
	 * @paparam appraisalBeanList
	 * @paparam resJson
	 * @throws Exception
	 */
	public static void getAppraisalDetail( AppraisalService appraisalService,List<AppraisalBean> appraisalBeanList, JSONObject resJson,CollectionService collectionService,ItemService itemService ,UserService userService,Boolean isAgent)
			throws Exception {
		LOGGER.info("ItemWS : Enter method getAppraisalDetail");
		JSONArray jArray = new JSONArray();
	
		if (appraisalBeanList!=null) {
			for(AppraisalBean appraisalBean : appraisalBeanList){
				JSONObject appraisalJson = new JSONObject();
			appraisalJson.put("consumerPublicKey", appraisalBean.getRelationKey());
			appraisalJson.put("name", appraisalBean.getName());
			appraisalJson.put("description", appraisalBean.getShortDescription());
			appraisalJson.put("appraisalStatus", appraisalBean.getaStatus());
			appraisalJson.put("id", appraisalBean.getAppraisalId());
			AppraisalModel appraisalModel = new AppraisalModel();
			appraisalModel.setAppraisalId(appraisalBean.getAppraisalId());
			appraisalModel.setName(appraisalBean.getName());
			appraisalModel.setShortDescription(appraisalBean.getShortDescription());
			appraisalModel.setaStatus(appraisalBean.getaStatus());
			appraisalModel.setApprovedBy(appraisalBean.getApprovedBy());
			appraisalModel.setApprovedOn(appraisalBean.getApprovedOn());
			appraisalModel.setCreatedBy(appraisalBean.getCreatedBy());
			appraisalModel.setCreatedOn(appraisalBean.getCreatedOn());
			appraisalModel.setModifiedBy(appraisalBean.getModifiedBy());
			appraisalModel.setModifiedOn(appraisalBean.getModifiedOn());
			appraisalModel.setRelationKey(appraisalBean.getRelationKey());
			appraisalModel.setLastAppraisaedPrice(appraisalBean.getLastAppraisaedPrice());
			AppraisalHelper.addPriceOfAppraisal(appraisalModel, appraisalJson, appraisalService, itemService, collectionService,isAgent);
			
			
			String pKey = (appraisalBean.getRelationKey());
			LoginModel loginModel = userService.getLoginDetailByPKey(pKey);
			JSONObject consumerDetail = new JSONObject();
			if(loginModel!=null){
				consumerDetail.put("firstName", loginModel.getFirstName());
				consumerDetail.put("middleName", loginModel.getMiddleName());
				consumerDetail.put("lastName", loginModel.getLastName());
			}
			appraisalJson.put("consumerDeatil", consumerDetail);
			jArray.put(appraisalJson);
		}
			
			
		}
		resJson.put("appraisalDetail", jArray);
		LOGGER.info("ItemWS : Exit method getAppraisalDetail");
	}
	
	/**
	 * @paparam storeModelList
	 * @paparam resJson
	 * @throws Exception
	 */
	public static void getStoreDetail(List<ValouxStoreModel> storeModelList,JSONObject resJson) throws Exception{
		JSONArray jArray = new JSONArray();
		if(storeModelList!=null){
			for (ValouxStoreModel storeModel : storeModelList) {
				JSONObject jObject = new JSONObject();
				jObject.put("storeId", storeModel.getStoreId());
				jObject.put("storeName", storeModel.getName());
				jObject.put("phone", storeModel.getPhone());
				JSONObject addressJson = new JSONObject();
				addressJson.put("gAddress", storeModel.getgAddress());
				addressJson.put("addressLine1", storeModel.getAddressLine1());
				addressJson.put("addressLine2", storeModel.getAddressLine2());
				// addressJson.put("streetNo",
				// storeModel.getStreetNumber());
				addressJson.put("country", storeModel.getCountryName());
				addressJson.put("city", storeModel.getCity());
				addressJson.put("state", storeModel.getStateName());
				addressJson.put("zipCode", storeModel.getZipcode());
				addressJson.put("street_number", storeModel.getStreetNumber());
				jObject.put("storeAddress", addressJson);
				jArray.put(jObject);
			} // end of for loop
		}
		resJson.put("storeDetail", jArray);
	}
	
	/**
	 * @paparam loginModelList
	 * @paparam resJson
	 * @paparam agentService
	 * @paparam storeService
	 * @throws Exception
	 */
	public static void getAgentDetail(List<LoginModel> loginModelList,JSONObject resJson,AgentService agentService,ValouxStoreService storeService) throws Exception{
		JSONArray jArray = new JSONArray();
		if(loginModelList!=null){
			for (LoginModel loginModel : loginModelList) {
				String pKey = loginModel.getPrivateKey();
				JSONObject jObject = new JSONObject();
				String rKey = (pKey);
				JSONObject storeJson = new JSONObject();
				JSONObject storeAddressJson = new JSONObject();
				AgentModel agentProfileModel = new AgentModel();
				ValouxStoreModel storeModel = new ValouxStoreModel();
				ValouxAgentStoreModel agentStoreModel = new ValouxAgentStoreModel();
				agentProfileModel = agentService.getAgentDetailByRelationKey(rKey);
				if(agentProfileModel!=null){
				agentStoreModel = agentService.getStoreDataByRelationKey(rKey);
				if(agentStoreModel!=null){
					storeModel = storeService.getStoreDataByStoreId(agentStoreModel.getStoreId());
					storeJson.put("storeId", storeModel.getStoreId());
					storeJson.put("storeName", storeModel.getName());
					storeAddressJson.put("streetNo", storeModel.getStreetNumber());
					storeAddressJson.put("addressLine1", storeModel.getAddressLine1());
					storeAddressJson.put("addressLine2", storeModel.getAddressLine2());
					storeAddressJson.put("country", storeModel.getCountryName());
					storeAddressJson.put("state", storeModel.getStateName());
					storeAddressJson.put("zipCode", storeModel.getZipcode());
					storeAddressJson.put("city", storeModel.getCity());
					storeJson.put("storeAddress", storeAddressJson);
				}
					
				jObject.put("rKey", rKey);
				jObject.put("firstName", loginModel.getFirstName());
				jObject.put("lastName", loginModel.getLastName());
				jObject.put("middeName", loginModel.getMiddleName());
				jObject.put("emailId", agentProfileModel.getEmailId());
				jObject.put("mobilePhone", agentProfileModel.getMobile());
				jObject.put("storeData", storeJson);
					
				jArray.put(jObject);
				}
			} // end of for loop
		}
		resJson.put("agentDetail", jArray);
	}
	
	/**
	 * @paparam agentModelList
	 * @paparam resJson
	 * @paparam agentService
	 * @paparam storeService
	 * @throws Exception
	 */
	public static void getAllAgentDetail(List<AgentModel> agentModelList,JSONObject resJson,AgentService agentService,ValouxStoreService storeService,UserService userService) throws Exception{
		JSONArray jArray = new JSONArray();
		if(agentModelList!=null){
			for (AgentModel agentProfileModel : agentModelList) {
				JSONObject jObject = new JSONObject();
				JSONObject storeJson = new JSONObject();
				JSONObject storeAddressJson = new JSONObject();
				ValouxStoreModel storeModel = new ValouxStoreModel();
				String rKey = agentProfileModel.getRelationKey();
				String pKey = (rKey);
				LoginModel loginModel = userService.getLoginDetailByPKey(pKey);
				ValouxAgentStoreModel agentStoreModel = new ValouxAgentStoreModel();
				if(agentProfileModel!=null){
				agentStoreModel = agentService.getStoreDataByRelationKey(rKey);
				if(agentStoreModel!=null){
					storeModel = storeService.getStoreDataByStoreId(agentStoreModel.getStoreId());
					storeJson.put("storeId", storeModel.getStoreId());
					storeJson.put("storeName", storeModel.getName());
					storeAddressJson.put("streetNo", storeModel.getStreetNumber());
					storeAddressJson.put("addressLine1", storeModel.getAddressLine1());
					storeAddressJson.put("addressLine2", storeModel.getAddressLine2());
					storeAddressJson.put("country", storeModel.getCountryName());
					storeAddressJson.put("state", storeModel.getStateName());
					storeAddressJson.put("zipCode", storeModel.getZipcode());
					storeAddressJson.put("city", storeModel.getCity());
					storeJson.put("storeAddress", storeAddressJson);
				}
					
				jObject.put("rKey", rKey);
				jObject.put("firstName", loginModel.getFirstName());
				jObject.put("lastName", loginModel.getLastName());
				jObject.put("middeName", loginModel.getMiddleName());
				jObject.put("emailId", agentProfileModel.getEmailId());
				jObject.put("mobilePhone", agentProfileModel.getMobile());
				jObject.put("agentStatus", loginModel.getUserStatus());
				jObject.put("agentCreateDate", agentProfileModel.getCreatedOn());
				jObject.put("storeData", storeJson);
					
				jArray.put(jObject);
				}
			} // end of for loop
		}
		resJson.put("agentList", jArray);
	}
	
	/**
	 * @paparam diamondModel
	 * @paparam reqObject
	 * @throws Exception
	 */
	public static void populateValouxDiamondModelForPrice(
			ValouxDiamondModel diamondModel, JSONObject componentProperty) throws Exception {

		String clarityId = JSONUtility.getSafeString(componentProperty, "clarityId");
		if(CommonUserUtility.paparameterNullCheckStringObject(clarityId)){
			diamondModel.setClarityId(Integer.parseInt(clarityId));
		}
		
		String color = JSONUtility.getSafeString(componentProperty, "color");
		if(CommonUserUtility.paparameterNullCheckStringObject(color)){
			diamondModel.setColor(Integer.parseInt(color));
		}
		
		String shape = JSONUtility.getSafeString(componentProperty, "shape");
		if(CommonUserUtility.paparameterNullCheckStringObject(shape)){
			diamondModel.setShape(Integer.parseInt(shape));
		}
		
		diamondModel.setTotalWeight(JSONUtility.getSafeString(componentProperty, "totalWeight"));
	}

	/**
	 * @paparam metalModel
	 * @paparam componentProperty
	 * @throws Exception
	 */
	public static void populateValouxMetalModelForPrice(
			ValouxMetalModel metalModel, JSONObject componentProperty) throws Exception {
		String metalsType = JSONUtility.getSafeString(componentProperty, "metalsType");
		if(CommonUserUtility.paparameterNullCheckStringObject(metalsType)){
			metalModel.setMetalsType(Integer.parseInt(metalsType));
		}
		String purity = JSONUtility.getSafeString(componentProperty, "purity");
		if(CommonUserUtility.paparameterNullCheckStringObject(purity)){
			metalModel.setPurity(Integer.parseInt(purity));
		}
		metalModel.setWeight(JSONUtility.getSafeString(componentProperty, "weight"));
	}
	
	/**
	 * @paparam perUnitPrice
	 * @paparam weight 
	 * @paparam purity
	 * @return
	 * @throws Exception
	 */
	public static Double getComponentMetalGoldSpecifiedPrice(Double perUnitPrice,
			Integer purityKarat, Double weight) throws Exception {
		
		Double calculatedTotalValue = null;
		
		if(perUnitPrice != null){
			
			Double purity = ItemHelper.getGoldPurityByType(purityKarat);
			
			if(purity != null){
				calculatedTotalValue = weight * perUnitPrice * (purity / CommonConstants.PURE_CARAT);
			}
		}
		return calculatedTotalValue;
	}

	/**
	 * @paparam unitPrice
	 * @return
	 * @throws Exception
	 */
	public static Double getMetalComponentPerUnitPrice(Double unitPrice) throws Exception{

		Double perUnitValue = null;
		
		if(unitPrice != null){
			perUnitValue = unitPrice / CommonConstants.CONVERSION_CALC;
		}
		return perUnitValue;
	}

	/**
	 * @paparam clarityId
	 * @paparam priceBean
	 * @return
	 */
	public static Double getClarityValueById(Integer clarityId,
			ValouxDiamondMasterPriceBean priceBean) throws Exception{

		Double clarityValue = null;
		
		if(clarityId != null){
			if(clarityId.equals(1)){
				clarityValue = priceBean.getPriceIf();
			} else if(clarityId.equals(2)){
				clarityValue = priceBean.getPriceIf();
			} else if(clarityId.equals(3)){
				clarityValue = priceBean.getVvs1();
			} else if(clarityId.equals(4)){
				clarityValue = priceBean.getVvs2();
			} else if(clarityId.equals(5)){
				clarityValue = priceBean.getVs1();
			} else if(clarityId.equals(6)){
				clarityValue = priceBean.getVs2();
			} else if(clarityId.equals(7)){
				clarityValue = priceBean.getSi1();
			} else if(clarityId.equals(8)){
				clarityValue = priceBean.getSi2();
			} else if(clarityId.equals(9)){
				clarityValue = priceBean.getSi3();
			} else if(clarityId.equals(10)){
				clarityValue = priceBean.getI1();
			} else if(clarityId.equals(11)){
				clarityValue = priceBean.getI2();
			} else if(clarityId.equals(12)){
				clarityValue = priceBean.getI3();
			} 
		}
		return clarityValue;
	}
	
	/**
	 * @paparam perUnitPrice
	 * @paparam purityKarat
	 * @paparam weight
	 * @return
	 * @throws Exception
	 */
	public static Double getComponentMetalSilverSpecifiedPrice(
			Double perUnitPrice, Integer purityKarat, Double weight) throws Exception{

		Double calculatedTotalValue = null;
		
		if(perUnitPrice != null){
			
			Double purity = ItemHelper.getSilverPurityByType(purityKarat);
			if(purity != null){
				calculatedTotalValue = weight * perUnitPrice * purity;
			}
		}
		return calculatedTotalValue;
	}
	
	/**
	 * @paparam purityKarat
	 * @return
	 * @throws Exception
	 */
	private static Double getSilverPurityByType(Integer purityKarat) throws Exception{
		Double carat= null;
		
		if(purityKarat != null){
			try {
				switch (purityKarat) {
				case 12: // Pure
					carat = 1.0;
					break;
				case 13: //Sterling
					carat = 0.925;
					break;
				case 14: // 800 Silver
					carat = 0.8;
					break;
				case 15: // 600 Silver
					carat = 0.6;
					break;
				case 16: // 400 Silver
					carat = 0.4;
					break;
				default:
					throw new IllegalArgumentException("No Enum specified for this purityKarat id -" + purityKarat);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return carat;
	}

	/**
	 * @paparam purityKarat
	 * @return
	 */
	public static Double getGoldPurityByType(Integer purityKarat) throws Exception{
		Double carat= null;
		if(purityKarat != null){
			try {
				switch (purityKarat) {
				case 1:
					carat = 24.0;
					break;
				case 2:
					carat = 22.0;
					break;
				case 3:
					carat = 22.0;
					break;
				case 4: //900 coin = 21.60
					carat = 21.60;
					break;
				case 5:
					carat = 21.0;
					break;
				case 6:
					carat = 18.0;
					break;
				case 7:
					carat = 16.0;
					break;
				case 8:
					carat = 14.0;
					break;
				case 9:
					carat = 12.0;
					break;
				case 10:
					carat = 10.0;
					break;
				case 11:
					carat = 9.0;
					break;
				default:
					throw new IllegalArgumentException("No Enum specified for this purityKarat id -" + purityKarat);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return carat;
	}

	/**
	 * @paparam perUnitPrice
	 * @paparam purity
	 * @paparam weight
	 * @return
	 * @throws Exception
	 */
	public static Double getComponentMetalPlatinumSpecifiedPrice(
			Double perUnitPrice, Integer purityKarat, Double weight) throws Exception{

		Double calculatedTotalValue = null;
		
		if(perUnitPrice != null){
			
			Double purity = ItemHelper.getPlatinumPurityByType(purityKarat);
			if(purity != null){
				calculatedTotalValue = weight * perUnitPrice * purity;
			}
		
		}
		return calculatedTotalValue;
	}

	/**
	 * @paparam purityKarat
	 * @return
	 * @throws Exception
	 */
	private static Double getPlatinumPurityByType(Integer purityKarat) throws Exception{
		Double carat= null;
		
		if(purityKarat != null){
			try {
				switch (purityKarat) {
				case 17:
					carat = 1.0;
					break;
				case 18:
					carat = 0.9;
					break;
				default:
					throw new IllegalArgumentException("No Enum specified for this purityKarat id -" + purityKarat);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return carat;
	}

	/**
	 * @paparam perUnitPrice
	 * @paparam purityKarat
	 * @paparam weight
	 * @return
	 * @throws Exception
	 */
	public static Double getComponentMetalAllSpecifiedPrice(
			Double perUnitPrice, Integer purityKarat, Double weight) throws Exception{
		Double calculatedTotalValue = null;
		
		if(perUnitPrice != null){
			
			Double purity = 1.0;
			calculatedTotalValue = weight * perUnitPrice * purity;
		}
		return calculatedTotalValue;
	}

	/**
	 * @paparam itemPriceModel
	 * @paparam masterPriceBean
	 * @return 
	 * @throws Exception
	 */
	public static void getValouxMetalsMasterPriceBean(
			ItemPriceModel itemPriceModel,
			ValouxMetalsMasterPriceBean masterPriceBean) throws Exception{
		
		if(itemPriceModel != null){
			masterPriceBean.setConversionCalc(CommonConstants.CONVERSION_CALC);
			masterPriceBean.setCreatedBy(CommonConstants.QUANDL_API);
			masterPriceBean.setCreatedOn(new SimpleDateFormat("yyyy-MM-dd").parse(itemPriceModel.getNewestAvailableDate()));
			masterPriceBean.setModifiedBy(CommonConstants.QUANDL_API);
			masterPriceBean.setModifiedOn(CommonUtility.getDateAndTime());
			masterPriceBean.setMetalsType(ItemComponentHelper.METAL_NAME_MAP.get(itemPriceModel.getMetalType()));
			masterPriceBean.setMetalsTypeQuality(itemPriceModel.getMetalsTypeQuality());
			masterPriceBean.setPriceUnit(CommonConstants.PRICE_UNIT);
			masterPriceBean.setStatus(CommonConstants.STATUS_ACTIVE.byteValue());
			masterPriceBean.setUnitPrice(itemPriceModel.getPriceAMorPM());
			masterPriceBean.setValouxUnit(itemPriceModel.getPriceAMorPM());
		}
	}

	/**
	 * @paparam itemModel
	 * @paparam reqObject
	 * @throws Exception
	 */
	public static void populateValouxItemPricePropertyModel(
			ValouxItemModel itemModel, JSONObject reqObject) throws Exception{
		
		LOGGER.info("ItemHelper : Enter Method populateValouxItemPricePropertyModel");

		Integer itemId = JSONUtility.getSafeInteger(reqObject, "itemId");
		JSONObject priceProperty = reqObject.optJSONObject("priceProperty");
		
		if(itemId != null && priceProperty != null){
			
			itemModel.setItemId(itemId);
			itemModel.setrKey(JSONUtility.getSafeString(reqObject, "userPublicKey"));
			itemModel.setModifiedBy(JSONUtility.getSafeString(reqObject, "publicKey"));
			itemModel.setModifiedOn(CommonUtility.getDateAndTime());
			String adjustmentPrice = JSONUtility.getSafeString(priceProperty, "adjustmentPrice");
			if(CommonUserUtility.paparameterNullCheckStringObject(adjustmentPrice)){
				itemModel.setAdjustmentPrice(Double.valueOf(adjustmentPrice));
			}
			
			itemModel.setBrandPriceAdjustmentNotes(JSONUtility.getSafeString(priceProperty, "brandPriceAdjustmentNotes"));
			
			String brandPriceAdjustmentOperator = JSONUtility.getSafeString(priceProperty, "brandPriceAdjustmentOperator");
			if(CommonUserUtility.paparameterNullCheckStringObject(brandPriceAdjustmentOperator)){
				itemModel.setBrandPriceAdjustmentOperator(Byte.valueOf(brandPriceAdjustmentOperator));
			}
			
			String brandPriceAdjustmentType = JSONUtility.getSafeString(priceProperty, "brandPriceAdjustmentType");
			if(CommonUserUtility.paparameterNullCheckStringObject(brandPriceAdjustmentOperator)){
				itemModel.setBrandPriceAdjustmentType(Byte.valueOf(brandPriceAdjustmentType));
			}
			
			String brandPriceAdjustmentValue = JSONUtility.getSafeString(priceProperty, "brandPriceAdjustmentValue");
			if(CommonUserUtility.paparameterNullCheckStringObject(brandPriceAdjustmentValue)){
				itemModel.setBrandPriceAdjustmentValue(Double.valueOf(brandPriceAdjustmentValue));
			}
			
			String finalPrice = JSONUtility.getSafeString(priceProperty, "finalPrice");
			if(CommonUserUtility.paparameterNullCheckStringObject(finalPrice)){
				itemModel.setFinalPrice(Double.valueOf(finalPrice));
			}
			
			itemModel.setGeneralPriceAdjustmentNotes(JSONUtility.getSafeString(priceProperty, "generalPriceAdjustmentNotes"));
			
			String generalPriceAdjustmentOperator = JSONUtility.getSafeString(priceProperty, "generalPriceAdjustmentOperator");
			if(CommonUserUtility.paparameterNullCheckStringObject(generalPriceAdjustmentOperator)){
				itemModel.setGeneralPriceAdjustmentOperator(Byte.valueOf(generalPriceAdjustmentOperator));
			}
			
			String generalPriceAdjustmentType = JSONUtility.getSafeString(priceProperty, "generalPriceAdjustmentType");
			if(CommonUserUtility.paparameterNullCheckStringObject(generalPriceAdjustmentType)){
				itemModel.setGeneralPriceAdjustmentType(Byte.valueOf(generalPriceAdjustmentType));
			}
			
			String generalPriceAdjustmentValue = JSONUtility.getSafeString(priceProperty, "generalPriceAdjustmentValue");
			if(CommonUserUtility.paparameterNullCheckStringObject(generalPriceAdjustmentValue)){
				itemModel.setGeneralPriceAdjustmentValue(Double.valueOf(generalPriceAdjustmentValue));
			}
			
			String lastAppraisaedPrice = JSONUtility.getSafeString(priceProperty, "lastAppraisaedPrice");
			if(CommonUserUtility.paparameterNullCheckStringObject(lastAppraisaedPrice)){
				itemModel.setLastAppraisaedPrice(Double.valueOf(lastAppraisaedPrice));
			}
			
			String marketValue = JSONUtility.getSafeString(priceProperty, "marketValue");
			if(CommonUserUtility.paparameterNullCheckStringObject(marketValue)){
				itemModel.setMarketValue(Double.valueOf(marketValue));
			}
			
			String purchasePrice = JSONUtility.getSafeString(priceProperty, "purchasePrice");
			if(CommonUserUtility.paparameterNullCheckStringObject(purchasePrice)){
				itemModel.setPurchasePrice(Double.valueOf(purchasePrice));
			}
			
			itemModel.setLastAppraiserId(JSONUtility.getSafeString(priceProperty, "lastAppraiserId"));
			
			String lastAppraisedDate = JSONUtility.getSafeString(priceProperty, "lastAppraisedDate");
			if(CommonUserUtility.paparameterNullCheckStringObject(lastAppraisedDate)){
				itemModel.setLastAppraisedDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((lastAppraisedDate)));
			}
		}
		LOGGER.info("ItemHelper : Enter Method populateValouxItemPricePropertyModel");
	}

	/**
	 * @paparam itemBean
	 * @paparam itemModel
	 * @throws Exception
	 */
	public static void populateItemPriceBeanFromModel(ValouxItemBean itemBean,
			ValouxItemModel itemModel) throws Exception{
		
		if(itemModel != null){			
			itemBean.setItemId(itemModel.getItemId());
			itemBean.setrKey(itemModel.getrKey());
			itemBean.setModifiedOn(itemModel.getModifiedOn());
			itemBean.setModifiedBy(itemModel.getModifiedBy());
			
			if(CommonUserUtility.paparameterNullCheckObject(itemModel.getAdjustmentPrice())) {
				itemBean.setAdjustmentPrice(itemModel.getAdjustmentPrice());
			} else {
				itemBean.setAdjustmentPrice(null);
			}
			
			itemBean.setBrandPriceAdjustmentNotes(itemModel.getBrandPriceAdjustmentNotes());
			
			if(CommonUserUtility.paparameterNullCheckObject(itemModel.getBrandPriceAdjustmentOperator())) {
				itemBean.setBrandPriceAdjustmentOperator(itemModel.getBrandPriceAdjustmentOperator());
			} else {
				itemBean.setBrandPriceAdjustmentOperator(null);
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemModel.getBrandPriceAdjustmentType())) {
				itemBean.setBrandPriceAdjustmentType(itemModel.getBrandPriceAdjustmentType());
			} else {
				itemBean.setBrandPriceAdjustmentType(null);
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemModel.getBrandPriceAdjustmentValue())) {
				itemBean.setBrandPriceAdjustmentValue(itemModel.getBrandPriceAdjustmentValue());
			} else {
				itemBean.setBrandPriceAdjustmentValue(null);
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemModel.getFinalPrice())) {
				itemBean.setFinalPrice(itemModel.getFinalPrice());
			} else {
				itemBean.setFinalPrice(null);
			}
			
			itemBean.setGeneralPriceAdjustmentNotes(itemModel.getGeneralPriceAdjustmentNotes());
			
			if(CommonUserUtility.paparameterNullCheckObject(itemModel.getGeneralPriceAdjustmentOperator())) {
				itemBean.setGeneralPriceAdjustmentOperator(itemModel.getGeneralPriceAdjustmentOperator());
			} else {
				itemBean.setGeneralPriceAdjustmentOperator(null);
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemModel.getGeneralPriceAdjustmentType())) {
				itemBean.setGeneralPriceAdjustmentType(itemModel.getGeneralPriceAdjustmentType());
			} else {
				itemBean.setGeneralPriceAdjustmentType(null);
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemModel.getGeneralPriceAdjustmentValue())) {
				itemBean.setGeneralPriceAdjustmentValue(itemModel.getGeneralPriceAdjustmentValue());
			} else {
				itemBean.setGeneralPriceAdjustmentValue(null);
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemModel.getItemStatus())) {
				itemBean.setItemStatus(itemModel.getItemStatus());
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemModel.getLastAppraisaedPrice())) {
				itemBean.setLastAppraisaedPrice(itemModel.getLastAppraisaedPrice());
			} else {
				itemBean.setLastAppraisaedPrice(null);
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemModel.getLastAppraisedDate())) {
				itemBean.setLastAppraisedDate(itemModel.getLastAppraisedDate());
			}
			if(CommonUserUtility.paparameterNullCheckStringObject(itemModel.getLastAppraiserId())) {
				itemBean.setLastAppraiserId(itemModel.getLastAppraiserId());
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemModel.getMarketValue())) {
				itemBean.setMarketValue(itemModel.getMarketValue());
			} else {
				itemBean.setMarketValue(null);
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemModel.getPurchasePrice())) {
				itemBean.setPurchasePrice(itemModel.getPurchasePrice());
			} else {
				itemBean.setPurchasePrice(null);
			}
		}
		
	}

	/**
	 * @paparam itemService
	 * @paparam resJson
	 * @paparam itemId
	 * @paparam userPublicKey 
	 */
	public static void getUpdatedItemPriceProperties(ItemService itemService,
			JSONObject resJson, Integer itemId) throws Exception{
		
		JSONObject priceProperty = new JSONObject();
		ValouxItemModel itemModel = itemService.getUpdatedItemPriceProperties(itemId);
		if(itemModel != null){
			priceProperty.put("modifiedOn",itemModel.getModifiedOn());
			priceProperty.put("modifiedBy",itemModel.getModifiedBy());
			priceProperty.put("adjustmentPrice",itemModel.getAdjustmentPrice());
			priceProperty.put("brandPriceAdjustmentNotes",itemModel.getBrandPriceAdjustmentNotes());
			priceProperty.put("brandPriceAdjustmentOperator",itemModel.getBrandPriceAdjustmentOperator());
			priceProperty.put("brandPriceAdjustmentType",itemModel.getBrandPriceAdjustmentType());
			priceProperty.put("brandPriceAdjustmentValue",itemModel.getBrandPriceAdjustmentValue());
			priceProperty.put("finalPrice",itemModel.getFinalPrice());
			priceProperty.put("generalPriceAdjustmentNotes",itemModel.getGeneralPriceAdjustmentNotes());
			priceProperty.put("generalPriceAdjustmentOperator",itemModel.getGeneralPriceAdjustmentOperator());
			priceProperty.put("generalPriceAdjustmentType",itemModel.getGeneralPriceAdjustmentType());
			priceProperty.put("generalPriceAdjustmentValue",itemModel.getGeneralPriceAdjustmentValue());
			priceProperty.put("itemStatus",itemModel.getItemStatus());
			priceProperty.put("lastAppraisaedPrice",itemModel.getLastAppraisaedPrice());
			priceProperty.put("lastAppraisedDate",itemModel.getLastAppraisedDate());
			priceProperty.put("lastAppraiserId",itemModel.getLastAppraiserId());
			priceProperty.put("marketValue",itemModel.getMarketValue());
			priceProperty.put("purchasePrice",itemModel.getPurchasePrice());
			priceProperty.put("quantity",itemModel.getQuantity());
		}
		
		resJson.put("priceProperty", priceProperty);
	}

	/**
	 * @paparam itemBean
	 * @paparam itemModel
	 * @throws Exception
	 */
	public static void populateItemPriceModelFromBean(ValouxItemBean itemBean,
			ValouxItemModel itemModel) throws Exception{
		
		if(itemBean != null){
			
			itemModel.setItemId(itemBean.getItemId());
			itemModel.setrKey(itemBean.getrKey());
			itemModel.setModifiedOn(itemBean.getModifiedOn());
			itemModel.setModifiedBy(itemBean.getModifiedBy());
			
			if(CommonUserUtility.paparameterNullCheckObject(itemBean.getAdjustmentPrice())) {
				itemModel.setAdjustmentPrice(itemBean.getAdjustmentPrice());
			} else {
				itemModel.setAdjustmentPrice(null);
			}
			if(CommonUserUtility.paparameterNullCheckStringObject(itemBean.getBrandPriceAdjustmentNotes())) {
				itemModel.setBrandPriceAdjustmentNotes(itemBean.getBrandPriceAdjustmentNotes());
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemBean.getBrandPriceAdjustmentOperator())) {
				itemModel.setBrandPriceAdjustmentOperator(itemBean.getBrandPriceAdjustmentOperator());
			} else {
				itemModel.setBrandPriceAdjustmentOperator(null);
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemBean.getBrandPriceAdjustmentType())) {
				itemModel.setBrandPriceAdjustmentType(itemBean.getBrandPriceAdjustmentType());
			} else {
				itemModel.setBrandPriceAdjustmentType(null);
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemBean.getBrandPriceAdjustmentValue())) {
				itemModel.setBrandPriceAdjustmentValue(itemBean.getBrandPriceAdjustmentValue());
			} else {
				itemModel.setBrandPriceAdjustmentValue(null);
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemBean.getFinalPrice())) {
				itemModel.setFinalPrice(itemBean.getFinalPrice());
			} else {
				itemModel.setFinalPrice(null);
			}
			if(CommonUserUtility.paparameterNullCheckStringObject(itemBean.getGeneralPriceAdjustmentNotes())) {
				itemModel.setGeneralPriceAdjustmentNotes(itemBean.getGeneralPriceAdjustmentNotes());
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemBean.getGeneralPriceAdjustmentOperator())) {
				itemModel.setGeneralPriceAdjustmentOperator(itemBean.getGeneralPriceAdjustmentOperator());
			} else {
				itemModel.setGeneralPriceAdjustmentOperator(null);
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemBean.getGeneralPriceAdjustmentType())) {
				itemModel.setGeneralPriceAdjustmentType(itemBean.getGeneralPriceAdjustmentType());
			} else {
				itemModel.setGeneralPriceAdjustmentType(null);
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemBean.getGeneralPriceAdjustmentValue())) {
				itemModel.setGeneralPriceAdjustmentValue(itemBean.getGeneralPriceAdjustmentValue());
			} else {
				itemModel.setGeneralPriceAdjustmentValue(null);
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemBean.getItemStatus())) {
				itemModel.setItemStatus(itemBean.getItemStatus());
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemBean.getLastAppraisaedPrice())) {
				itemModel.setLastAppraisaedPrice(itemBean.getLastAppraisaedPrice());
			} else {
				itemModel.setLastAppraisaedPrice(null);
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemBean.getLastAppraisedDate())) {
				itemModel.setLastAppraisedDate(itemBean.getLastAppraisedDate());
			}
			if(CommonUserUtility.paparameterNullCheckStringObject(itemBean.getLastAppraiserId())) {
				itemModel.setLastAppraiserId(itemBean.getLastAppraiserId());
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemBean.getMarketValue())) {
				itemModel.setMarketValue(itemBean.getMarketValue());
			} else {
				itemModel.setMarketValue(null);
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemBean.getPurchasePrice())) {
				itemModel.setPurchasePrice(itemBean.getPurchasePrice());
			} else {
				itemModel.setPurchasePrice(null);
			}
			if(CommonUserUtility.paparameterNullCheckObject(itemBean.getFinalPrice())) {
				itemModel.setFinalPrice(itemBean.getFinalPrice());
			} else {
				itemModel.setFinalPrice(null);
			}
			itemModel.setQuantity(itemBean.getQuantity());
		}
		
	}
	
	/**
	 * @paparam itemModel
	 * @paparam jObject
	 * @throws Exception
	 */
	public static void addPricesOfItemToJson(ValouxItemModel itemModel,JSONObject jObject,Boolean isAgent) throws Exception{
		
		if(itemModel != null){
			if(itemModel.getItemStatus() != null) {
				jObject.put("marketValue", 0.00);
				jObject.put("appraisedValue", 0.00);
				jObject.put("lastAppraisedDate", "");
				jObject.put("marketValueDate", "");
				jObject.put("finalPrice", 0.00);
				jObject.put("priceChange", CommonConstants.PERCENTAGE_NO_CHANGE);
				
				jObject.put("changePercentage", 0);
				Integer itemStatus = itemModel.getItemStatus();
				
				jObject.put("itemStatus", itemStatus);
				if(isAgent){
					if(itemModel.getMarketValue()!=null){
					jObject.put("marketValue", itemModel.getMarketValue()*itemModel.getQuantity());
					}
					if(itemModel.getFinalPrice()!=null){
						jObject.put("finalPrice", itemModel.getFinalPrice()*itemModel.getQuantity());
					}
				}
				if(itemStatus.equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
					
					Double marketValue = 0.00;
					Double appraisedValue = 0.00;
					
					if(itemModel.getMarketValue() != null && itemModel.getQuantity() !=null) {
						marketValue = itemModel.getMarketValue()*itemModel.getQuantity();
					}
					
					if(itemModel.getLastAppraisaedPrice() != null){
						appraisedValue = itemModel.getLastAppraisaedPrice();
					}
					
					if(itemModel.getFinalPrice()!=null){
						jObject.put("finalPrice", itemModel.getFinalPrice()*itemModel.getQuantity());
					}
					
					Date appraisedDate = itemModel.getLastAppraisedDate();
					Date currentDat = CommonUtility.getDateAndTime();
					jObject.put("marketValue", marketValue);
					jObject.put("appraisedValue", appraisedValue);
					jObject.put("lastAppraisedDate", appraisedDate);
					jObject.put("marketValueDate", currentDat);
					
					if(marketValue != null && appraisedValue != null){
						Double diff = marketValue-appraisedValue;
						
						Double percentage = 0.0;
						
						if(appraisedValue > 0){
							percentage = (diff*100)/appraisedValue;
						}
						if(diff>0){
						jObject.put("priceChange", CommonConstants.PERCENTAGE_INC);
						}
						if(diff<0){
						jObject.put("priceChange", CommonConstants.PERCENTAGE_DEC);
						}
						if(diff==0){
							jObject.put("priceChange", CommonConstants.PERCENTAGE_NO_CHANGE);
						}
						jObject.put("changePercentage", percentage);
					}
				}
			}
		}
	}
	
	/**
	 * @paparam itemModel
	 * @paparam jObject
	 * @throws Exception
	 */
	public static void addPricesOfAppraisalItemToJson(ValouxItemModel itemModel,JSONObject jObject,Boolean isAgent,AppraisalService appraisalService,Integer appraisaId) throws Exception{
		
		if(itemModel != null){
			if(itemModel.getItemStatus() != null) {
				jObject.put("marketValue", 0.00);
				jObject.put("appraisedValue", 0.00);
				jObject.put("lastAppraisedDate", "");
				jObject.put("marketValueDate", "");
				jObject.put("finalPrice", 0.00);
				jObject.put("priceChange", CommonConstants.PERCENTAGE_NO_CHANGE);
				
				jObject.put("changePercentage", 0);
				Integer itemStatus = itemModel.getItemStatus();
				
				jObject.put("itemStatus", itemStatus);
				if(isAgent){
					if(itemModel.getMarketValue()!=null){
					jObject.put("marketValue", itemModel.getMarketValue()*itemModel.getQuantity());
					}
					if(itemModel.getFinalPrice()!=null){
						jObject.put("finalPrice", itemModel.getFinalPrice()*itemModel.getQuantity());
					}
				}
				if(itemStatus.equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
					
					Double marketValue = 0.00;
					Double appraisedValue = 0.00;
					
					if(itemModel.getMarketValue() != null && itemModel.getQuantity() !=null) {
						marketValue = itemModel.getMarketValue()*itemModel.getQuantity();
					}
					
					ValouxAppraisalItemsPriceBean appraisalItempriceBean = appraisalService.getAppraisedItemPriceOfAppraisal(itemModel.getItemId(), appraisaId);
					if(appraisalItempriceBean!=null){
						appraisedValue = appraisalItempriceBean.getFinalPrice();
						jObject.put("lastAppraisedDate", appraisalItempriceBean.getAppraisedDate());
						
					}
					
					if(itemModel.getFinalPrice()!=null){
						jObject.put("finalPrice", itemModel.getFinalPrice()*itemModel.getQuantity());
					}
					
					Date currentDat = CommonUtility.getDateAndTime();
					jObject.put("marketValue", marketValue);
					jObject.put("appraisedValue", appraisedValue);
					jObject.put("marketValueDate", currentDat);
					
					if(marketValue != null && appraisedValue != null){
						Double diff = marketValue-appraisedValue;
						Double percentage = 0.0;
						
						if(appraisedValue > 0){
							percentage = (diff*100)/appraisedValue;
						}
						if(diff>0){
						jObject.put("priceChange", CommonConstants.PERCENTAGE_INC);
						}
						if(diff<0){
						jObject.put("priceChange", CommonConstants.PERCENTAGE_DEC);
						}
						if(diff==0){
							jObject.put("priceChange", CommonConstants.PERCENTAGE_NO_CHANGE);
						}
						jObject.put("changePercentage", percentage);
					}
				}
			}
		}
	}


	/**
	 * @paparam itemBean
	 * @paparam marketValue 
	 * @return
	 * @throws Exception
	 */
	public static Double getNewFinalAdjustedPrice(ValouxItemBean itemBean, Double marketValue) throws Exception{
		
		Double brandPrice = 0.0;
		Double generalPrice = 0.0;
		Double newFinalValue = 0.0;
		
		if(itemBean.getPurchasePrice() != null){
			newFinalValue = itemBean.getPurchasePrice();
		} else if((itemBean.getBrandPriceAdjustmentOperator() != null && itemBean.getBrandPriceAdjustmentType() != null && itemBean.getBrandPriceAdjustmentValue() != null) 
				&& (itemBean.getGeneralPriceAdjustmentOperator() == null && itemBean.getGeneralPriceAdjustmentType() == null && itemBean.getGeneralPriceAdjustmentValue() == null)){
			
			//brand price
			brandPrice = itemBean.getBrandPriceAdjustmentValue();
			if(Integer.valueOf(itemBean.getBrandPriceAdjustmentOperator()).equals(CommonConstants.TWO)){
				brandPrice = -brandPrice;
			} 
			if(Integer.valueOf(itemBean.getBrandPriceAdjustmentType()).equals(CommonConstants.TWO)){
				brandPrice = brandPrice / CommonConstants.HUNDRED;
			}
			newFinalValue = brandPrice + marketValue;
		} else if((itemBean.getBrandPriceAdjustmentOperator() == null && itemBean.getBrandPriceAdjustmentType() == null && itemBean.getBrandPriceAdjustmentValue() == null) 
				&& (itemBean.getGeneralPriceAdjustmentOperator() != null && itemBean.getGeneralPriceAdjustmentType() != null && itemBean.getGeneralPriceAdjustmentValue() != null)){
			//general price
			generalPrice = itemBean.getGeneralPriceAdjustmentValue();
			if(Integer.valueOf(itemBean.getGeneralPriceAdjustmentOperator()).equals(CommonConstants.TWO)){
				generalPrice = -generalPrice;
			} 
			if(Integer.valueOf(itemBean.getGeneralPriceAdjustmentType()).equals(CommonConstants.TWO)){
				generalPrice = generalPrice / CommonConstants.HUNDRED;
			}
			newFinalValue = generalPrice + marketValue;
		} else if((itemBean.getBrandPriceAdjustmentOperator() != null && itemBean.getBrandPriceAdjustmentType() != null && itemBean.getBrandPriceAdjustmentValue() != null) 
				&& (itemBean.getGeneralPriceAdjustmentOperator() != null && itemBean.getGeneralPriceAdjustmentType() != null && itemBean.getGeneralPriceAdjustmentValue() != null)){
			
			//brand price
			brandPrice = itemBean.getGeneralPriceAdjustmentValue();
			if(Integer.valueOf(itemBean.getGeneralPriceAdjustmentOperator()).equals(CommonConstants.TWO)){
				brandPrice = -brandPrice;
			} 
			if(Integer.valueOf(itemBean.getGeneralPriceAdjustmentType()).equals(CommonConstants.TWO)){
				brandPrice = brandPrice / CommonConstants.HUNDRED;
			}
			//general price
			generalPrice = itemBean.getGeneralPriceAdjustmentValue();
			if(Integer.valueOf(itemBean.getGeneralPriceAdjustmentOperator()).equals(CommonConstants.TWO)){
				generalPrice = -generalPrice;
			} 
			if(Integer.valueOf(itemBean.getGeneralPriceAdjustmentType()).equals(CommonConstants.TWO)){
				generalPrice = generalPrice / CommonConstants.HUNDRED;
			}
			newFinalValue = generalPrice + brandPrice + marketValue;
		} else {
			newFinalValue = marketValue;
		}
		return newFinalValue;
	}

	/**
	 * @paparam appraisalModels
	 * @paparam resData
	 * @throws Exception
	 */
	public static void getAllAppraisalsList(
			List<AppraisalModel> appraisalModels, JSONObject resData) throws Exception{

		Gson gson = new Gson();
		JSONArray jsonArray = new JSONArray();
		if(appraisalModels != null && appraisalModels.size() > 0){
			for (AppraisalModel appraisalModel : appraisalModels) {
				String jsonInString = gson.toJson(appraisalModel);
				jsonArray.put(new JSONObject(jsonInString));
			}
		}
		resData.put("appraisalList", jsonArray);
	}

	/**
	 * @paparam itemModel
	 * @paparam itemBean
	 * @paparam itemTypeMap
	 * @throws Exception
	 */
	public static void populateItemModelDetailsFromBean(
			ValouxItemModel itemModel, ValouxItemBean itemBean, Map<Integer, String> itemTypeMap) throws Exception{
		
		if (itemBean != null) {
			itemModel.setItemId(itemBean.getItemId());
			itemModel.setId(itemBean.getItemId());
			itemModel.setDesignerPrice(itemBean.getDesignerPrice());
			itemModel.setDesignerPriceType(itemBean.getDesignerPriceType());
			itemModel.setDesignType(itemBean.getDesignType());
			itemModel.setGender(itemBean.getGender());
			itemModel.setItemTypeIt(itemBean.getValouxItemTypeBean().getItemTypeId());
			itemModel.setName(itemBean.getName());
			itemModel.setQuantity(itemBean.getQuantity());
			itemModel.setSalesPrice(itemBean.getSalesPrice());
			itemModel.setSalesTax(itemBean.getSalesTax());
			itemModel.setsDescription(itemBean.getsDescription());
			itemModel.setStoreId(itemBean.getStoreId());
			itemModel.setValouxMarketValue(itemBean.getValouxMarketValue());
			itemModel.setCreatedBy(itemBean.getCreatedBy());
			itemModel.setCreatedOn(itemBean.getCreatedOn());
			itemModel.setModifiedBy(itemBean.getModifiedBy());
			itemModel.setModifiedOn(itemBean.getModifiedOn());
			itemModel.setItemTypeName(itemTypeMap.get(itemBean.getValouxItemTypeBean().getItemTypeId()));
			itemModel.setrKey(itemBean.getrKey());
			itemModel.setItemStatus(itemBean.getItemStatus());
			itemModel.setStatus(itemBean.getItemStatus());
			itemModel.setLastAppraisaedPrice(itemBean.getLastAppraisaedPrice());
			itemModel.setLastAppraisedDate(itemBean.getLastAppraisedDate());
			itemModel.setMarketValue(itemBean.getMarketValue());
			itemModel.setFinalPrice(itemBean.getFinalPrice());
			itemModel.setDescription(itemBean.getsDescription());
			
			List<ItemImageModel> imageModels = new ArrayList<ItemImageModel>();
			if(itemBean.getItemImagesBean() != null && itemBean.getItemImagesBean().size() > 0){
				
				List<ItemImageBean> itemImagesBean = itemBean.getItemImagesBean();
				
				for (ItemImageBean itemImageBean : itemImagesBean) {
					if(itemImageBean != null && itemImageBean.getImageType()!=null && itemImageBean.getImageType().equals(CommonConstants.ONE)) {
						ItemImageModel itemImageModel = CommonUserUtility.prepareItemImageModelFromBean(itemImageBean);
						imageModels.add(itemImageModel);
					}
				}
			}
			itemModel.setImageModels(imageModels);
		}
	}

	/**
	 * @paparam itemBean
	 * @paparam marketValue
	 * @return
	 * @throws Exception
	 */
	public static Double getNewFinalAdjustedComponentPrice(
			ValouxItemComponentBean itemBean,
			Double marketValue) throws Exception{
		
		Double brandPrice = 0.0;
		Double generalPrice = 0.0;
		Double newFinalValue = 0.0;
		
		if(itemBean.getPurchasePrice() != null){
			newFinalValue = itemBean.getPurchasePrice();
		} else if((itemBean.getBrandPriceAdjustmentOperator() != null && itemBean.getBrandPriceAdjustmentType() != null && itemBean.getBrandPriceAdjustmentValue() != null) 
				&& (itemBean.getGeneralPriceAdjustmentOperator() == null && itemBean.getGeneralPriceAdjustmentType() == null && itemBean.getGeneralPriceAdjustmentValue() == null)){
			
			//brand price
			brandPrice = itemBean.getBrandPriceAdjustmentValue();
			if(Integer.valueOf(itemBean.getBrandPriceAdjustmentOperator()).equals(CommonConstants.TWO)){
				brandPrice = -brandPrice;
			} 
			if(Integer.valueOf(itemBean.getBrandPriceAdjustmentType()).equals(CommonConstants.TWO)){
				brandPrice = brandPrice / CommonConstants.HUNDRED;
			}
			newFinalValue = brandPrice + marketValue;
		} else if((itemBean.getBrandPriceAdjustmentOperator() == null && itemBean.getBrandPriceAdjustmentType() == null && itemBean.getBrandPriceAdjustmentValue() == null) 
				&& (itemBean.getGeneralPriceAdjustmentOperator() != null && itemBean.getGeneralPriceAdjustmentType() != null && itemBean.getGeneralPriceAdjustmentValue() != null)){
			//general price
			generalPrice = itemBean.getGeneralPriceAdjustmentValue();
			if(Integer.valueOf(itemBean.getGeneralPriceAdjustmentOperator()).equals(CommonConstants.TWO)){
				generalPrice = -generalPrice;
			} 
			if(Integer.valueOf(itemBean.getGeneralPriceAdjustmentType()).equals(CommonConstants.TWO)){
				generalPrice = generalPrice / CommonConstants.HUNDRED;
			}
			newFinalValue = generalPrice + marketValue;
		} else if((itemBean.getBrandPriceAdjustmentOperator() != null && itemBean.getBrandPriceAdjustmentType() != null && itemBean.getBrandPriceAdjustmentValue() != null) 
				&& (itemBean.getGeneralPriceAdjustmentOperator() != null && itemBean.getGeneralPriceAdjustmentType() != null && itemBean.getGeneralPriceAdjustmentValue() != null)){
			
			//brand price
			brandPrice = itemBean.getGeneralPriceAdjustmentValue();
			if(Integer.valueOf(itemBean.getGeneralPriceAdjustmentOperator()).equals(CommonConstants.TWO)){
				brandPrice = -brandPrice;
			} 
			if(Integer.valueOf(itemBean.getGeneralPriceAdjustmentType()).equals(CommonConstants.TWO)){
				brandPrice = brandPrice / CommonConstants.HUNDRED;
			}
			//general price
			generalPrice = itemBean.getGeneralPriceAdjustmentValue();
			if(Integer.valueOf(itemBean.getGeneralPriceAdjustmentOperator()).equals(CommonConstants.TWO)){
				generalPrice = -generalPrice;
			} 
			if(Integer.valueOf(itemBean.getGeneralPriceAdjustmentType()).equals(CommonConstants.TWO)){
				generalPrice = generalPrice / CommonConstants.HUNDRED;
			}
			newFinalValue = generalPrice + brandPrice + marketValue;
		} else {
			newFinalValue = marketValue;
		}
		return newFinalValue;
	}

	/**
	 * @paparam valouxItemDao
	 * @paparam valouxItemBean
	 * @throws Exception
	 */
	public static void deleteAllItemDetailsOfItem(ValouxItemDao valouxItemDao,
			ValouxItemBean valouxItemBean) throws Exception{
		
		if(valouxItemBean != null) {
			//Item image beans delete
			if(valouxItemBean.getItemImagesBean() != null && valouxItemBean.getItemImagesBean().size() > 0) {
				List<ItemImageBean> imageBeans = valouxItemBean.getItemImagesBean();
				for (ItemImageBean itemImageBean : imageBeans) {
					valouxItemDao.deleteAnyBeanByObject(itemImageBean);
				}
			}
			
			if(valouxItemBean.getValouxItemComponents() != null && valouxItemBean.getValouxItemComponents().size() > 0){
				
				List<ValouxItemComponentBean> componentBeans = valouxItemBean.getValouxItemComponents();
				for (ValouxItemComponentBean valouxItemComponentBean : componentBeans) {
					
					//Delete component images
					if(valouxItemComponentBean.getValouxComponentsImages() != null && valouxItemComponentBean.getValouxComponentsImages().size() > 0){
						List<ValouxComponentsImageBean> componentsImageBeans = valouxItemComponentBean.getValouxComponentsImages();
						for (ValouxComponentsImageBean valouxComponentsImageBean : componentsImageBeans) {
							valouxItemDao.deleteAnyBeanByObject(valouxComponentsImageBean);
						}
					}
						
					//Delete component certificates
					if(valouxItemComponentBean.getItemComponentCertificate() != null && valouxItemComponentBean.getItemComponentCertificate().size() > 0){
						List<ItemComponentCertificateBean> certificateBeans = valouxItemComponentBean.getItemComponentCertificate();
						for (ItemComponentCertificateBean itemComponentCertificateBean : certificateBeans) {
							valouxItemDao.deleteAnyBeanByObject(itemComponentCertificateBean);
						}
					}
					
					//Delete component diamond
					if(valouxItemComponentBean.getValouxDiamonds() != null && valouxItemComponentBean.getValouxDiamonds().size() > 0) {
						List<ValouxDiamondBean> diamondBeans = valouxItemComponentBean.getValouxDiamonds();
						for (ValouxDiamondBean valouxDiamondBean : diamondBeans) {
							valouxItemDao.deleteAnyBeanByObject(valouxDiamondBean);
						}
					}
					
					//Delete component gemstone
					if(valouxItemComponentBean.getValouxGemstones() != null && valouxItemComponentBean.getValouxGemstones().size() > 0) {
						List<ValouxGemstoneBean> gemstoneBeans = valouxItemComponentBean.getValouxGemstones();
						for (ValouxGemstoneBean valouxGemstoneBean : gemstoneBeans) {
							valouxItemDao.deleteAnyBeanByObject(valouxGemstoneBean);
						}
					}
					
					//Delete component metal
					if(valouxItemComponentBean.getValouxMetals() != null && valouxItemComponentBean.getValouxMetals().size() > 0){
						List<ValouxMetalBean> metalBeans = valouxItemComponentBean.getValouxMetals();
						for (ValouxMetalBean valouxMetalBean : metalBeans) {
							valouxItemDao.deleteAnyBeanByObject(valouxMetalBean);
						}
					}
					
					//Delete component pearl
					if(valouxItemComponentBean.getValouxPearls() != null && valouxItemComponentBean.getValouxPearls().size() > 0) {
						List<ValouxPearlBean> pearlBeans = valouxItemComponentBean.getValouxPearls();
						for (ValouxPearlBean valouxPearlBean : pearlBeans) {
							valouxItemDao.deleteAnyBeanByObject(valouxPearlBean);
						}
					}
					
					//Delete component inclusion
					if(valouxItemComponentBean.getValouxInclusions() != null && valouxItemComponentBean.getValouxInclusions().size() > 0){
						List<ValouxInclusionBean> inclusionBeans = valouxItemComponentBean.getValouxInclusions();
						for (ValouxInclusionBean valouxInclusionBean : inclusionBeans) {
							valouxItemDao.deleteAnyBeanByObject(valouxInclusionBean);
						}
					}
					
					//Delete component appraisal price
					if(valouxItemComponentBean.getValouxAppraisalItemsComponentPrices() != null && valouxItemComponentBean.getValouxAppraisalItemsComponentPrices().size() > 0){
						List<ValouxAppraisalItemsComponentPriceBean> componentPriceBeans = valouxItemComponentBean.getValouxAppraisalItemsComponentPrices();
						for (ValouxAppraisalItemsComponentPriceBean valouxAppraisalItemsComponentPriceBean : componentPriceBeans) {
							valouxItemDao.deleteAnyBeanByObject(valouxAppraisalItemsComponentPriceBean);
						}
					}
					
					//Delete component
					valouxItemDao.deleteAnyBeanByObject(valouxItemComponentBean);
				}
			}
			
			//Delete item appraisals
			if(valouxItemBean.getAppraisalItemsBean() != null && valouxItemBean.getAppraisalItemsBean().size() > 0){
				List<AppraisalItemsBean> itemsBeans = valouxItemBean.getAppraisalItemsBean();
				for (AppraisalItemsBean appraisalItemsBean : itemsBeans) {
					valouxItemDao.deleteAnyBeanByObject(appraisalItemsBean);
				}
			}
			
			//Delete item collections
			if(valouxItemBean.getValouxCollectionItemsBean() != null && valouxItemBean.getValouxCollectionItemsBean().size() > 0){
				List<ValouxCollectionItemBean> collectionItemsBeans = valouxItemBean.getValouxCollectionItemsBean();
				for (ValouxCollectionItemBean valouxCollectionItemBean : collectionItemsBeans) {
					valouxItemDao.deleteAnyBeanByObject(valouxCollectionItemBean);
				}
			}
			
			//Delete item appraisal price property
			if(valouxItemBean.getValouxAppraisalItemsPrices() != null && valouxItemBean.getValouxAppraisalItemsPrices().size() > 0){
				List<ValouxAppraisalItemsPriceBean> appraisalItemsPrices = valouxItemBean.getValouxAppraisalItemsPrices();
				for (ValouxAppraisalItemsPriceBean valouxAppraisalItemsPriceBean : appraisalItemsPrices) {
					valouxItemDao.deleteAnyBeanByObject(valouxAppraisalItemsPriceBean);
				}
			}
			
			//Delete item
			valouxItemDao.deleteAnyBeanByObject(valouxItemBean);
		}
	}
	
}
