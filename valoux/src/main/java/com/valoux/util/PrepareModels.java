package com.valoux.util;

import java.util.ArrayList;
import java.util.List;

import com.valoux.bean.AppraisalBean;
import com.valoux.bean.AppraisalCollectionBean;
import com.valoux.bean.AppraisalItemsBean;
import com.valoux.bean.ValouxCollectionItemBean;
import com.valoux.bean.ValouxItemBean;
import com.valoux.model.AppraisalCollectionModel;
import com.valoux.model.AppraisalItemsModel;
import com.valoux.model.AppraisalModel;
import com.valoux.model.ValouxCollectionItemModel;
import com.valoux.model.ValouxItemModel;

public class PrepareModels {
	
	/**
	 * This method use to create model from bean AppraisalCollectionBean.
	 */
	public static List<AppraisalCollectionModel> prepareAppraisalCollectionModelListFromBean(List<AppraisalCollectionBean> appraisalBeansList) {
		List<AppraisalCollectionModel> appraisalCollectionModelList = new ArrayList<AppraisalCollectionModel>();
		AppraisalCollectionModel appraisalModel = null;
		for (AppraisalCollectionBean appraisalCollectionBean : appraisalBeansList) {
			appraisalModel = new AppraisalCollectionModel();
			appraisalModel.setId(appraisalCollectionBean.getId());
			appraisalModel.setAppraisalId(appraisalCollectionBean.getAppraisalBean().getAppraisalId());
			appraisalModel.setValouxCollectionId(appraisalCollectionBean.getValouxCollectionBean().getVcid());
			appraisalModel.setStatus(appraisalCollectionBean.getStatus());
			appraisalCollectionModelList.add(appraisalModel);
		}
	
		return appraisalCollectionModelList;
	}
	
	/**
	 * This method use to create model from bean AppraisalItemsBean.
	 */
	public static List<AppraisalItemsModel> prepareAppraisalItemsModelListFromBean(List<AppraisalItemsBean> appraisalItemsBeanList) {
		List<AppraisalItemsModel> appraisalItemsModelList = new ArrayList<AppraisalItemsModel>();
		AppraisalItemsModel itemModel = null;
		if(appraisalItemsBeanList != null){
		
		for (AppraisalItemsBean appraisalItemsBean : appraisalItemsBeanList) {
			itemModel = new AppraisalItemsModel();
			itemModel.setId(appraisalItemsBean.getId());
			itemModel.setAppraisalId(appraisalItemsBean.getAppraisalBean().getAppraisalId());
			itemModel.setValouxItemId(appraisalItemsBean.getValouxItemBean().getItemId());
            itemModel.setStatus(appraisalItemsBean.getStatus());
			
			appraisalItemsModelList.add(itemModel);
		}
		}
		return appraisalItemsModelList;
	}
	
	public static AppraisalModel prepareAppraisalModelFromAppraisalBean(AppraisalBean appraisalBean) {
		AppraisalModel appraisalModel = new AppraisalModel();

		if (appraisalBean != null) {
			appraisalModel.setAppraisalId(appraisalBean.getAppraisalId());
			appraisalModel.setRelationKey(appraisalBean.getRelationKey());
			appraisalModel.setName(appraisalBean.getName());
			appraisalModel.setShortDescription(appraisalBean.getShortDescription());
			appraisalModel.setModifiedOn(appraisalBean.getModifiedOn());
			appraisalModel.setCreatedOn(appraisalBean.getCreatedOn());
			appraisalModel.setCreatedBy(appraisalBean.getCreatedBy());
			appraisalModel.setModifiedBy(appraisalBean.getModifiedBy());
			appraisalModel.setaStatus(appraisalBean.getaStatus());
		}
		return appraisalModel;
	}
	
	/**
	 * This method use to create model from bean ValouxCollectionItemBean.
	 */
	public static List<ValouxCollectionItemModel> prepareValouxCollectionItemModelListFromBean(List<ValouxCollectionItemBean> valouxCollectionItemBeanList) {
		List<ValouxCollectionItemModel> valouxCollectionItemModelList = new ArrayList<ValouxCollectionItemModel>();
		ValouxCollectionItemModel valouxCollectionItemModel = null;
		if (valouxCollectionItemBeanList != null) {

			for (ValouxCollectionItemBean valouxCollectionItemBean : valouxCollectionItemBeanList) {
				valouxCollectionItemModel = new ValouxCollectionItemModel();
				valouxCollectionItemModel.setVcid(valouxCollectionItemBean.getVcid());
				valouxCollectionItemModel.setCollectionId(valouxCollectionItemBean.getValouxCollectionBean().getVcid());
				valouxCollectionItemModel.setItemId(valouxCollectionItemBean.getValouxItemBean().getItemId());

				valouxCollectionItemModelList.add(valouxCollectionItemModel);
			}
		}
		return valouxCollectionItemModelList;
	}
	
	/**
	 * This method create items model from items bean
	 * 
	 * @paparam itemsBean
	 * @return
	 */
	public static ValouxItemModel prepareitemsModelFromItemsBean(ValouxItemBean itemsBean) {

		ValouxItemModel itemsModel = new ValouxItemModel();
		if (itemsBean != null) {

			itemsModel.setCreatedBy(itemsBean.getrKey());
			itemsModel.setCreatedOn(itemsBean.getModifiedOn());
			itemsModel.setDesignerPrice(itemsBean.getDesignerPrice());
			itemsModel.setDesignerPriceType(itemsBean.getDesignerPriceType());
			itemsModel.setDesignType(itemsBean.getDesignType());
			itemsModel.setGender(itemsBean.getGender());
			itemsModel.setModifiedBy(itemsBean.getrKey());
			itemsModel.setModifiedOn(itemsBean.getModifiedOn());
			itemsModel.setItemId(itemsBean.getItemId());
			// itemsModel.setItemTypeIt(itemsBean.getItemTypeIt());
			itemsModel.setItemTypeIt(itemsBean.getValouxItemTypeBean().getItemTypeId());
			itemsModel.setQuantity(itemsBean.getQuantity());
			itemsModel.setStoreId(itemsBean.getStoreId());
			itemsModel.setName(itemsBean.getName());
			itemsModel.setSalesPrice(itemsBean.getSalesPrice());
			itemsModel.setSalesTax(itemsBean.getSalesTax());
			itemsModel.setsDescription(itemsBean.getsDescription());
			itemsModel.setItemStatus(itemsBean.getItemStatus());
			itemsModel.setLastAppraisaedPrice(itemsBean.getLastAppraisaedPrice());
			itemsModel.setLastAppraisedDate(itemsBean.getLastAppraisedDate());
			itemsModel.setMarketValue(itemsBean.getMarketValue());
			itemsModel.setFinalPrice(itemsBean.getFinalPrice());
		}
		return itemsModel;
	}
	
	/**
	 * This method use to create model from bean ValouxCollectionItemBean.
	 */
	public static List<ValouxItemModel> prepareValouxItemBeanModelListFromBean(List<ValouxItemBean> valouxItemBeanList) {
		List<ValouxItemModel> valouxItemModelList = new ArrayList<ValouxItemModel>();
		ValouxItemModel itemModel = null;
		if (valouxItemBeanList != null) {

			for (ValouxItemBean itemBean : valouxItemBeanList) {
				itemModel = new ValouxItemModel();
				
				itemModel.setCreatedBy(itemBean.getCreatedBy());// to be updated
				itemModel.setCreatedOn(itemBean.getModifiedOn());
				itemModel.setDesignerPrice(itemBean.getDesignerPrice());
				itemModel.setDesignerPriceType(itemBean.getDesignerPriceType());
				itemModel.setDesignType(itemBean.getDesignType());
				itemModel.setGender(itemBean.getGender());
				itemModel.setItemId(itemBean.getItemId());
//				itemModel.setItemTypeIt(itemBean.getItemTypeIt());
				itemModel.setItemTypeIt(itemBean.getValouxItemTypeBean().getItemTypeId());
						
				itemModel.setModifiedBy(itemBean.getModifiedBy());
				itemModel.setModifiedOn(itemBean.getModifiedOn());
				itemModel.setName(itemBean.getName());
				itemModel.setQuantity(itemBean.getQuantity());
				itemModel.setrKey(itemBean.getrKey());
				itemModel.setSalesPrice(itemBean.getSalesPrice());
				itemModel.setSalesTax(itemBean.getSalesTax());
				itemModel.setsDescription(itemBean.getsDescription());
				itemModel.setItemStatus(itemBean.getItemStatus());
				itemModel.setLastAppraisaedPrice(itemBean.getLastAppraisaedPrice());
				itemModel.setLastAppraisedDate(itemBean.getLastAppraisedDate());
				itemModel.setMarketValue(itemBean.getMarketValue());
				itemModel.setFinalPrice(itemBean.getFinalPrice());
				// itemBean.setStoreExist(itemBean.getStoreExist());
				itemModel.setStoreId(itemBean.getStoreId());
				itemModel.setValouxMarketValue(itemModel.getValouxMarketValue());

				valouxItemModelList.add(itemModel);
			}
		}
		return valouxItemModelList;
	}

}
