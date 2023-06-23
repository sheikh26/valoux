var valouxServices = angular.module('valouxApp.valouxServices', []);
valouxServices.service('valouxService', ['$http', '$q', '$rootScope', function ($http, $q, $rootScope) {
   
   function getLoggedInData(){
        var savedItem = sessionStorage.getItem("saveValouxData");
        if( !savedItem ){
           return false;
        }
        var savedData = JSON.parse(savedItem);
        return { "publicKey":savedData.memberShipKey, "token":savedData.memberShipKey, "memberShipKey":savedData.memberShipKey };
    }

   return {
        getData: function (apiUrl,postData) { //function for call webservice
                if(typeof postData == 'undefined'){
                    var postData = new Object();
                }
                
                if(typeof postData.reqPaparam == 'undefined'){
                    postData.reqPaparam = {};
                }
                var serviceData = $q.defer(); 
                var sessObj = getLoggedInData(); 
                if( sessObj ){
                    var req = {};
                    req.reqPaparam = sessObj;
                    postData.reqPaparam = angular.extend(postData.reqPaparam, req.reqPaparam);
                }
                $http({method: 'POST',
                    data: postData,
                    url: apiUrl,
                    headers: {'Content-Type': 'application/json'}}).
                        success(function (data, status, headers, config) {
                             
                            serviceData.resolve(data);
                        }).
                        error(function (data, status, headers, config) {
                            serviceData.reject();
                        });
                return serviceData.promise;
        },
        checkSession: function () {   // if not logged in redirect to index page
                var sItem = sessionStorage.getItem("saveValouxData");
                
                if( !sItem ){
                  window.location = "index.html";
                }
        },
        getWebServiceUrl: function( serviceNm  ){ //function for get webservice url by name.
            var basePath = "/vx/rest/"; 
            var webServiceUrl = {
                    "checkEmail": basePath+"user/checkExistingEmailId.dns",
                    "userLogin": basePath+"user/userLogin.dns",
                    "userRegistration": basePath+"user/userRegistration.dns",
                    "agentRegistration": basePath+"agent/agentRegistration.dns",                    
                    "getUserDetail": "../jsonTestFiles/login.json",
                    "forgetQuestion": "../jsonTestFiles/forgetQuestion.json",
                    "getUserInterestIn": basePath+"user/getUserInterestIn.dns",
                    "getUserPersonalPreference": basePath+"user/getUserPersonalPreference.dns",
		            "getAllStore": basePath+"agent/getAllStoreDetail.csv",
                    "activateUser": basePath+"user/activateUser.dns",
		            "getConsumerInfo": basePath+"user/getConsumerInfo.csv",
                    "verfyUserOTP": basePath+"agent/verifyUserOTP.dns",
                    "updateConsumerInfo": basePath+"user/updateConsumerInfo.csv",
                    "resendOTP": basePath+"user/resendOTP.dns",
                    "getAgentInfo": basePath+"agent/getAgentInfo.csv",
                    "updateAgentInfo": basePath+"agent/updateAgentInfo.csv",
                    "addOrUpdateItem": basePath+"item/addOrUpdateItem.csv",
                    "deleteImageByImageId": basePath+"item/deleteImageByImageId.csv",
                    "getItemInfoByItemId": basePath+"item/getItemListByItemId.csv",
                    "getMasterDataForItemType": basePath+"item/getMasterDataForItemType.csv",
                    "getAllStoreAgentsForAdmin": basePath+"agent/getAllStoreAgentsForAdmin.dns",
					"itemList": basePath+"item/getAllItemList.csv",                 
					"changePassword": basePath+"user/changePassword.csv",
                    "getSecurityQuestion": basePath+"user/getSecurityQuestion.csv",
                    "verifySecurityAnswer": basePath+"user/verifySecurityAnswer.csv",
                    "verifyKeyAndChangePassword": basePath+"user/verifyKeyAndChangePassword.csv",
                    "addUpdateCollectionDetails": basePath+"collection/addUpdateCollectionDetails.csv",
                    "changeProfieImage": basePath+"user/changeProfieImage.csv",
                    "getUserCollectionsList": basePath+"collection/getUserCollectionsList.csv",
                    "deleteItemFromCollection": basePath+"collection/deleteItemFromCollection.csv",
                    "getUserItemsNotInCollection": basePath+"collection/getUserItemsNotInCollection.csv",
                    "checkItemNameExistForUser": basePath+"item/checkItemNameExistForUser.csv",
                    "deleteImageByCollectionAndImageId": basePath+"collection/deleteImageByCollectionAndImageId.csv",
                    "checkCollectionNameExistForUser": basePath+"collection/checkCollectionNameExistForUser.csv",
                    "getAllStoreDetailWithUserCount": basePath+"agent/getAllStoreDetailWithUserCount.csv",
                    "activateOrDeactivateStore": basePath+"agent/activateOrDeactivateStore.csv", 
                    "getItemsOfCollection": basePath+"collection/getItemsOfCollection.csv",
                    "createOrUpdateAppraisal": basePath+"appraisal/createOrUpdateAppraisal.csv", 
                    "getAllItemAndCollectionList": basePath+"appraisal/getAllItemAndCollectionList.csv",                    
					"getComponentsList": basePath+"item/getComponentsList.csv",
                    "addUpdateItemComponents": basePath+"item/addUpdateItemComponents.csv",
                    "getItemComponents": basePath+"item/getItemComponents.csv",
                    "deleteComponentFromItem": basePath+"item/deleteComponentFromItem.csv",
                    "checkAppraisalNameExistForUser": basePath+"appraisal/checkAppraisalNameExistForUser.csv",
                    "getUserAppraisalList": basePath+"appraisal/getUserAppraisalList.csv",                    
                    "getCollectionOfAppraisal": basePath+"appraisal/getCollectionOfAppraisal.csv",
                    "createOrUpdateAppraisalForItemsOrCollection": basePath+"appraisal/createOrUpdateAppraisalForItemsOrCollection.csv",
                    "getItemsOfAppraisal": basePath+"appraisal/getItemsOfAppraisal.csv",
                    "getCollectionListNotInAppraisal": basePath+"appraisal/getCollectionListNotInAppraisal.csv",
                    "getItemsListNotInAppraisal" : basePath+"appraisal/getItemsListNotInAppraisal.csv",
                    "getAppraisalsOfCollection" : basePath+"collection/getAppraisalsOfCollection.csv",
                    "getUserAppraisalsNotInCollection" : basePath+"collection/getUserAppraisalsNotInCollection.csv",
                    "deleteAppraisalFromCollection" : basePath+"collection/deleteAppraisalFromCollection.csv",
                	"getListOfShareContacts":basePath+"item/getListOfSharedContact.csv",
					"itemShareSubmit":basePath+"item/shareItem.csv",
					"getAllConsumerDetail": basePath+"user/getAllConsumerInfo.csv",
					"getAllAppraisalDetail": basePath+"appraisal/getAllAppraisalListForSuperAdmin.csv",
                    "deleteCollectionOrItemFromAppraisal" : basePath+"appraisal/deleteCollectionOrItemFromAppraisal.csv",
                    "getCollectionsListAssociatedWithItem": basePath+"collection/getCollectionsListAssociatedWithItem.csv",
                    "getUserCollectionsNotInItem": basePath+"collection/getCollectionNotAssociatedWithItem.csv",
                    "addItemToCollection": basePath+"collection/addItemToCollection.csv",
                    "getAppraisalListAssociatedWithItem": basePath+"appraisal/getAppraisalListAssociatedWithItem.csv",
                    "getAppraisalListNotAssociatedWithItem": basePath+"appraisal/getAppraisalListNotAssociatedWithItem.csv",
                    "getUserSharedList": basePath+"item/getListOfItemsSharedByUser.csv",
                    "addItemsInAppraisal": basePath+"appraisal/addItemsInAppraisal.csv",
                    "deleteItemFromAppraisals": basePath+"appraisal/deleteItemFromAppraisals.csv",   
                    "getUserSharedWithMeList": basePath+"item/getListOfItemsSharedToUser.csv",
                    "UnShareItem": basePath+"item/unShareItem.csv",
                    "getConsumerListSharedWithItem": basePath+"item/getConsumerListSharedWithItem.csv",
                    "getAgentListSharedWithItem": basePath+"item/getAgentListSharedWithItem.csv",
                    "acceptOrRejectShareRequest":basePath+"item/acceptOrRejectShareRequest.csv",
                    "getListOfRequestedItemsSharedToAgent":basePath+"item/getListOfRequestedItemsSharedToAgent.csv",
                    "addUpdateItemComponentProperties": basePath+"item/addUpdateItemComponentProperties.csv",
                    "getItemComponentProperty": basePath+"item/getItemComponentProperty.csv",
                    "getUnRegisteredUserListSharedWithItem":basePath+"item/getUnRegisteredUserListSharedWithItem.csv",
                    "getConsumerListWhoSharedItemWithAgent":basePath+"item/getConsumerListWhoSharedItemWithAgent.csv",
                    "getAppraisalDetails":basePath+"appraisal/getAppraisalDetails.csv",
                    "deleteItemComponentImage":basePath+"item/deleteItemComponentImage.csv",
                    "getAgentConsumerList" :basePath+"appraisal/getAgentAccessUsersList.csv",
                    "getAgentInfoAssociatedWithStore":basePath+"agent/getAgentInfoAssociatedWithStore.csv",
                    "sharedRequestCount":basePath+"user/sharedRequestCount.csv",
                    "getAllCountryDetails":basePath+"user/getAllCountryDetails.csv",
					"activeInactiveAgent":basePath+"agent/activeInactiveAgent.csv",
                    "getUserListSharedToAgent":basePath+"agent/getUserListSharedToAgent.csv",
                    "globalSearch":basePath+"user/globalSearch.csv",
                    "inviteUserDetail":basePath+"user/getUserLoginDetailsByAuthCode.dns",
                    "inviteUserRegistration":basePath+"user/addUserDetailsViaInvitation.dns",
                    "getStoreAndAgentAssociatedWithConsumer":basePath+"agent/getStoreAndAgentAssociatedWithConsumer.csv",
					"checkGiaReport":basePath+"reportCheck/reportCheckWebServiceClient.csv",
					"saveOrUpdateStoreAdvInfo":basePath+"agent/saveOrUpdateStoreAdvInfo.csv",
					"getItemComponentDiamondSpecifyPrice":basePath+"item/getItemComponentDiamondSpecifyPrice.csv",
                    "getItemComponentMetalSpecifyPrice":basePath+"item/getItemComponentMetalSpecifyPrice.csv",
                    "getAllStoreDetailWithNoAdvertisement":basePath+"agent/getAllStoreDetailWithNoAdvertisement.csv",
                    "deleteAdvImage":basePath+"agent/deleteAdvImage.csv",
                    "activeInactiveStoreAdv":basePath+"agent/activeInactiveStoreAdv.csv",
                    "getAllStoreAdvDetail":basePath+"agent/getAllStoreAdvDetail.csv",
                    "getStoreDetailWithUserCount":basePath+"agent/getStoreDetailWithUserCount.csv",
                    "updateItemPriceProperties":basePath+"item/updateItemPriceProperties.csv",
                    "getItemPriceProperties":basePath+"item/getItemPriceProperties.csv",
                    "mergeStores":basePath+"agent/mergeStores.csv",
                    "changeStatusToAppraised":basePath+"appraisal/changeStatusToAppraised.csv",                    
                    "getAppraisalPdf":basePath+"appraisal/downloadAppraisalPdfFile.csv",
                    "getAllAppraisalsList":basePath+"appraisal/getAllAppraisalsList.csv",     
                    "getStoreDetailById":basePath+"agent/getStoreDetailById.csv",     
                    "updateStoreDetailById":basePath+"agent/updateStoreDetailById.csv",     
                    "changeStatusToUnAppraised":basePath+"appraisal/changeStatusToUnAppraised.csv",     
                    "reportImageServiceClient":basePath+"reportCheck/reportImageServiceClient.csv",
                    "recentActivityForDashboard":basePath+"user/recentActivityForDashboard.csv",
                    "deleteItemByItemId":basePath+"item/deleteItemByItemId.csv",
                    "deleteCollectionByCollectionId":basePath+"collection/deleteCollectionByCollectionId.csv",
                    "deleteAppraisalByAppraisalId":basePath+"appraisal/deleteAppraisalByAppraisalId.csv",
                    "getAppraiserListOfStore":basePath+"agent/getAppraiserListOfStore.csv",
                    "getAllAgentDetail":basePath+"agent/getAllAgentDetail.csv",
                    
                    };
            return webServiceUrl[serviceNm];
        }
   }     
}]);
