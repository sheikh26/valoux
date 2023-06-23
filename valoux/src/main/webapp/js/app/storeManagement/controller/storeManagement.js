baseController.controller('storeManagement',['$scope', 'valouxService', '$timeout','$location','RESPONSE_CODE', 'masterDataService','$route', '$routePaparams','$window', function($scope, valouxService, $timeout,$location, RESPONSE_CODE, masterDataService, $route, $routePaparams, $window){
/*Define store Object*/
$scope.store = new Object();
$scope.store.itemImages = [];
$scope.counterImage = [1]; 
$scope.noItem = false;
$scope.stepButton = true;
$scope.getItemList = false;
$scope.inValidimg=[];
$scope.inValidimgText=[];
$scope.inValidReceipt = false;
$scope.inValidCertificate = false;
$scope.isItemImage = false;
$scope.component = new Object();
$scope.inviteNewConsumer = true;

$scope.$on("$locationChangeStart", function(event) {
   $('.tooltipped').tooltip('remove');
});

this.reloadPage = function() { 
    $('.tooltipped').tooltip('remove');
     $route.reload();
}

/*appraisal listed selected pop up items in Shared */
$scope.appraisalListedItemShared = function(cid) {
	
 
  $scope.collectionId = cid;
  
  
    angular.element('#modal-appraisal').openModal();

    var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    var postData = new Object();
    postData.reqPaparam = { 
      "userPublicKey" : savedData.memberShipKey,
      "cId" : cid,
    }
    
    var url = valouxService.getWebServiceUrl("getAppraisalsOfCollection");
    //post data and get response
    var submitCollection = valouxService.getData(url,postData);
    submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.appraisalTotal = data.resData.appraisalList.length;
      $scope.appraisalPopups = data.resData.appraisalList;
      $timeout(function(){ angular.element('.tooltipped').tooltip(); }, 500);
      if(data.resData.appraisalList.length == 0){
        angular.element('#modal-noAppraisallist').openModal();
        angular.element('#modal-appraisal').closeModal();
      }
    }else{
      angular.element('#modal-noAppraisallist').openModal();
    }
  },function(){
    console.log("Error in get appraisal list from collection listing");
  });

  
}


/*Open model item listing in shared pages*/
$scope.modelItemListing = function(appraisalId) {
  $scope.appraisalId = appraisalId;

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  var postData = new Object();
  postData.reqPaparam = { 
    "userPublicKey" : savedData.memberShipKey,
    "appraisalId" : appraisalId
  };
  
  var url = valouxService.getWebServiceUrl("getItemsOfAppraisal");
  //post create appraisal and get response
  var submitAppraisal = valouxService.getData(url,postData);
  submitAppraisal.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      if(data.resData.itemsList.length == 0){
        angular.element('#modal-noitems-appraisal').openModal();
      }else{
        angular.element('#modal-items-appraisal').openModal();
        $scope.itemLists = data.resData.itemsList;
        $timeout(function(){$('.mCustomScrollbar').mCustomScrollbar(); angular.element('.tooltipped').tooltip(); }, 500);
      }
    }else{
      $window.location = 'appraisal.html#/'
    }
  },function(){
    console.log("Error in item listing appraisal");
  });
}

/*Open model collection in shared with me and shared page */
$scope.modelCollectionListing = function(appraisalId) {
  $scope.appraisalId = appraisalId;

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  var postData = new Object();
  postData.reqPaparam = { 
    "userPublicKey" : savedData.memberShipKey,
    "appraisalId" : appraisalId
  };
  
  var url = valouxService.getWebServiceUrl("getCollectionOfAppraisal");
  //post create appraisal and get response
  var submitAppraisal = valouxService.getData(url,postData);
  submitAppraisal.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      if(data.resData.collectionList.length == 0){
        angular.element('#modal-collection-nolist').openModal();
      }else{
        angular.element('#modal-collection-list').openModal();
        $scope.collectionLists = data.resData.collectionList;
        $timeout(function(){ $('.mCustomScrollbar').mCustomScrollbar(); angular.element('.tooltipped').tooltip(); }, 500);
      }
    }else{
      $window.location = 'appraisal.html#/'
    }
  },function(){
    console.log("Error in listing appraisal");
  });
}

/*Shared items Consumer listing for an item */
$scope.consumerListingItem = function(sharedBy,sharedItemId, sharedItemType) {
 
  $scope.sharedItemId = sharedItemId;
  $scope.sharedTypeItemId = sharedItemType;
  angular.element('#modal-items-consumers').openModal();

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var postData = new Object();
   // check if item shared by other user
	 if(sharedBy)
	 {
		 postData.reqPaparam = { 
		  "sharedBy" : sharedBy,
		  "sharedItemId" : sharedItemId,
		  "sharedItemType" : sharedItemType
		}
	 }else
	 {
		 postData.reqPaparam = { 
		  "sharedBy" : savedData.memberShipKey,
		  "sharedItemId" : sharedItemId,
		  "sharedItemType" : sharedItemType
		}
		
	 }
	if(sharedItemType==1){
		$scope.sharedItemType = "Item";
	}else if(sharedItemType==2){
		$scope.sharedItemType = "Collection";
	}else
	{
		$scope.sharedItemType = "Appraisal";
	}
    
    var url = valouxService.getWebServiceUrl("getConsumerListSharedWithItem");
    //post data and get response
    var submitCollection = valouxService.getData(url,postData);
    submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.collectionItemTotal = data.consumerList.length;
      $scope.consumerPopupItems = data.consumerList;
       
      $timeout(function(){ $('.mCustomScrollbar').mCustomScrollbar(); angular.element('.tooltipped').tooltip(); }, 500);

      if(data.consumerList.length == 0){
        angular.element('#modal-noitems').openModal();
        angular.element('#modal-items-consumers').closeModal();
      }
    }else{
      angular.element('#modal-noitems').openModal();
    }
  },function(){
    console.log("Error in get item list from collection listing");
  });

  
}

/*item detail page for agent*/
$scope.detailItemAgent = function(agentInfo,itemId) {
  if(agentInfo){
    sessionStorage.setItem("agentInformation", agentInfo);
    $scope.consumerPublicKey = agentInfo; 
    
    $window.location = "item-agent.html#/detail/"+itemId;

  }
}

/*item edit page for agent*/
$scope.editItemAgent = function(agentInfo,itemId) {
  if(agentInfo){
    sessionStorage.setItem("agentInformation", agentInfo);
    $scope.consumerPublicKey = agentInfo; 
    $window.location = "item-agent.html#/edit/"+itemId;
  }
}

/*Shared items un registered userrs  listing for an item */
$scope.sharedWithNumberOfUnregisteredUsersListing = function(sharedBy,sharedItemId, sharedItemType) {
 
  $scope.sharedItemId = sharedItemId;
  $scope.sharedTypeItemId = sharedItemType;
  

 
    angular.element('#modal-items-nonregisterd').openModal();

    var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    var postData = new Object();
   // check if item shared by other user
	 if(sharedBy)
	 {
		 postData.reqPaparam = { 
		  "sharedBy" : sharedBy,
		  "sharedItemId" : sharedItemId,
		  "sharedItemType" : sharedItemType
		}
	 }else
	 {
		 postData.reqPaparam = { 
		  "sharedBy" : savedData.memberShipKey,
		  "sharedItemId" : sharedItemId,
		  "sharedItemType" : sharedItemType
		}
		
	 }
	if(sharedItemType==1){
		$scope.sharedItemType = "Item";
	}else if(sharedItemType==2){
		$scope.sharedItemType = "Collection";
	}else
	{
		$scope.sharedItemType = "Appraisal";
	}
    
    var url = valouxService.getWebServiceUrl("getUnRegisteredUserListSharedWithItem");
    //post data and get response
    var submitCollection = valouxService.getData(url,postData);
    submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.unRegisteredTotal = data.unRegisteredUserList.length;
      $scope.unRegisteredItems = data.unRegisteredUserList;
       
      $timeout(function(){ $('.mCustomScrollbar').mCustomScrollbar(); angular.element('.tooltipped').tooltip(); }, 500);

      if(data.unRegisteredUserList.length == 0){
        angular.element('#modal-noitems').openModal();
        angular.element('#modal-items-nonregisterd').closeModal();
      }
    }else{
      angular.element('#modal-noitems').openModal();
    }
  },function(){
    console.log("Error in get non registered list from shared listing");
  });

  
}

/*Shared items Agent listing for an item */
$scope.agentListingItem = function(sharedBy,sharedItemId, sharedItemType) {
 
  $scope.sharedItemId = sharedItemId;
  $scope.sharedTypeItemId = sharedItemType;
 
    

    var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    var postData = new Object();
	// check if item shared by other user
	 if(sharedBy)
	 {
		 postData.reqPaparam = { 
		  "sharedBy" : sharedBy,
		  "sharedItemId" : sharedItemId,
		  "sharedItemType" : sharedItemType
		}
	 }else
	 {
		 postData.reqPaparam = { 
		  "sharedBy" : savedData.memberShipKey,
		  "sharedItemId" : sharedItemId,
		  "sharedItemType" : sharedItemType
		}
		
	 }
   
	if(sharedItemType==1){
		$scope.sharedItemType = "Item";
	}else if(sharedItemType==2){
		$scope.sharedItemType = "Collection";
	}else
	{
		$scope.sharedItemType = "Appraisal";
	}
    
    var url = valouxService.getWebServiceUrl("getAgentListSharedWithItem");
    //post data and get response
    var submitCollection = valouxService.getData(url,postData);
    submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.collectionItemTotal = data.agentList.length;
      $scope.agentPopupItems = data.agentList;
       angular.element('#modal-items-agents').openModal();
      $timeout(function(){ $('.mCustomScrollbar').mCustomScrollbar(); angular.element('.tooltipped').tooltip(); }, 500);

      if(data.agentList.length == 0){
        angular.element('#modal-noitems').openModal();
        angular.element('#modal-items-agents').closeModal();
      }
    }else{
	 $scope.errorMessage = data.errorMessage;	
     // angular.element('#modal-noitems').openModal();
    }
  },function(){
    console.log("Error in get item list from collection listing");
  });

  
}


/*initialize variable for add/edit item form*/
$scope.initAddEditStore= function() {
    $scope.store.priceProperty = new Object();
  var agentInfo = sessionStorage.getItem("agentInformation")
  if(agentInfo){
    $scope.inviteNewConsumer = true;
    $scope.agentSession = true;
    $scope.masterAdjustmentType = masterDataService.priceAdjustmentType();
  }
  else{
    if($scope.isAgentLogin){
      $scope.inviteNewConsumer = false;
    }
    
  }

    $scope.quantityArray = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50];
    $scope.$parent.storeData.isNewStore = 0;
    $scope.$parent.storeData.store_name =  "";
    $scope.$parent.storeData.store_location =  "";
    $scope.$parent.storeData.storePhoneNumber = "";
    $scope.$parent.storeData.storeId = 0;
    $scope.$parent.storeData.addressLine1 =  "";
    $scope.$parent.storeData.addressLine2 =  "";
    $scope.$parent.storeData.city =  "";
    $scope.$parent.storeData.state =  "";
    $scope.$parent.storeData.country =  "";
    $scope.$parent.storeData.zipCode = "";
    $('#gray-address1 input:not("#location")').removeClass('active');
    $('#gray-address1 input:not("#location")').next('label').removeClass('active');
    
    $scope.storeInitialValue = null;
    var itemId = $route.current.paparams.itemId;
    $scope.store.jewelryGender = 1;
    $scope.store.itemId = 0;
    // check item add or update 
    if(typeof itemId !='undefined'){  
        
        getItemInfoByItemId(itemId);
        
        $scope.store.itemId = itemId;
        checkAddedItemComponents(itemId);
    }
     $scope.allDesignType = masterDataService.allDesignType();
                    
  /*get service url getMasterDataForItemType*/
    var url = valouxService.getWebServiceUrl("getMasterDataForItemType");
    //post formAddItem data and get response
    var submitItem = valouxService.getData(url);
    submitItem.then(function(data){
       if (data.sCode == RESPONSE_CODE.SUCCESS) {
        $scope.allItemType   = data.resData.itemTypeList
            $scope.$parent.fullPageBlockLoading = false;
        window.scrollTo(500, 0);
        $('.tooltipped').tooltip('remove');
        $timeout(function(){
            angular.element("select").material_select();
        }, 500);
    }else{
        console.log("Error in get item type");  
      }
    },function(){
      console.log("Error in get item type");
    });
    
    initAutocomplete();
}


/*Click to scroll page item section in step2 component*/
$scope.scrollToStep = function(id) {
  $scope.scrollToAnchor(id);  
  
}


/*Scroll page*/
$scope.scrollToAnchor = function(aid) {
  var aTag = $("div[name='"+ aid +"']");
  $('html,body').animate({scrollTop: aTag.offset().top},'slow');
}

/* get record of item by itemId for edit item*/
function getItemInfoByItemId(itemId){ 
    /*get service url of item detail */
    var url = valouxService.getWebServiceUrl("getItemInfoByItemId");
    $scope.inviteNewConsumerHide = true;
    var postData = new Object();
    postData.reqPaparam = {  
      "itemId":itemId
    };
    
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
       if (data.sCode == RESPONSE_CODE.SUCCESS) { 
        $scope.class = "active";
           
        var itemData  = data.resData.itemData;
        $scope.itemAllData = itemData;
        $scope.store.itemId = itemData.itemId;
        $scope.store.sdescription = itemData.sdescription;
        $scope.store.name = itemData.name;
        $scope.store.jewelryGender = parseInt(itemData.jewelryGender);
        $scope.store.designType = parseInt(itemData.designType);
        $scope.store.quantity = itemData.quantity;
        $scope.store.itemType = itemData.itemType;
        $scope.store.priceProperty = itemData.priceProperty;

        if(typeof itemData.storeData!=='undefined'){
          var addressConcate = '';
          addressConcate+= itemData.storeData.storeAddress.addressLine1;
          if(typeof itemData.storeData.storeAddress.addressLine2 == 'undefined' || itemData.storeData.storeAddress.addressLine2 == ''){
            addressConcate+= '';
          }
          else{
            addressConcate+= ', '+itemData.storeData.storeAddress.addressLine2;
          }
          
          addressConcate+= ', '+itemData.storeData.storeAddress.city;
          addressConcate+= ', '+itemData.storeData.storeAddress.state;
          addressConcate+= ', '+itemData.storeData.storeAddress.country;
          addressConcate+= ', '+itemData.storeData.storeAddress.zipCode;


            $scope.storeInitialValue = {
               storeId : itemData.storeData.storeId,
               storeName : itemData.storeData.storeName,
               storeAddress : itemData.storeData.storeAddress,
               storePhone : itemData.storeData.phone,
               gaddress: addressConcate
            };
            
        }
        if(typeof itemData.itemImages!=='undefined'){
            $scope.itemImages =  itemData.itemImages;
        }
       
        // set time out function for dropdown trigger select
      $timeout(function(){
        angular.element("#designType").material_select();
        angular.element("#quantity").material_select();
        angular.element("#itemType").material_select();
      }, 500);

      }else{
        console.log("Error in get item");  
      }
    },function(){
      console.log("Error in get item");
    });
    
}

/* get record of item by itemId  for success*/
$scope.itemAddedSuccess = function() {
	window.scrollTo(500, 0);
	var savedItem = sessionStorage.getItem("saveValouxData");
  	var savedData = JSON.parse(savedItem);
  	$scope.userKey = savedData.memberShipKey;
  	
    var itemSuccessmsg = sessionStorage.getItem("itemSuccessmsg");
    if(itemSuccessmsg==1){
         $scope.itemSuccessmsg = 'Item Added Successfully!';
    }else{
         $scope.itemSuccessmsg = 'Item updated Successfully!';
    }
    
    var itemId = $route.current.paparams.itemId;
    $scope.store.itemId = itemId;
    checkAddedItemComponents(itemId);
    /*get service url of item detail */
    var url = valouxService.getWebServiceUrl("getItemInfoByItemId");
    var postData = new Object();
    postData.reqPaparam = {  
      "itemId":itemId
    };
    
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
       if (data.sCode == RESPONSE_CODE.SUCCESS) { 
        $scope.itemData  = data.resData.itemData;
        $scope.store.name = data.resData.itemData.name;
        $scope.store.quantity = data.resData.itemData.quantity;
        var allDesignType = masterDataService.allDesignType();
        for (var i=0; i < allDesignType.length; i++){
            if (allDesignType[i]['value'] == $scope.itemData.designType){
              $scope.designTypetext =  allDesignType[i].name;
              break;
            }
        }
      }else{
        console.log("Error in get item");  
      }
      $timeout(function(){ $('.mCustomScrollbar').mCustomScrollbar();  angular.element("select").material_select(); }, 500);
    },function(){
      console.log("Error in get item");
    });
}


// get Item Components by item id
$scope.getItemComponents = function() {
    $scope.itemAllComponents = [];
    var postData = new Object();
    var itemId = $route.current.paparams.itemId;
    postData.reqPaparam = {  
        "itemId":itemId
      };
    var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    var savedItemAgent = sessionStorage.getItem("agentInformation");
    if(savedItemAgent){
         postData.reqPaparam.userPublicKey = savedItemAgent;
    }else{
         postData.reqPaparam.userPublicKey = savedData.memberShipKey;
    }
    var url = valouxService.getWebServiceUrl("getItemComponents");
    var getItem = valouxService.getData(url,postData);
    getItem.then(function(data){
      if (data.sCode == RESPONSE_CODE.SUCCESS) {
        $scope.itemAllComponents = data.resData.componentList
      }
       $timeout(function(){ $('.mCustomScrollbar').mCustomScrollbar(); }, 1000);
    },function(){
      console.log("Error in get item list");
    });
}


// show multipal file upload input
$scope.addMoreImage = function() {
    $scope.counterImage.push($scope.counterImage.length+1);
}


/*for add/edit item*/
$scope.submitAddEditItem = function() {
 $scope.$parent.fullPageBlockLoading = true;
 if($scope.inValidReceipt == false && $scope.inValidCertificate == false && $scope.isItemImage == false){
    
    var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    var agentInfo = sessionStorage.getItem("agentInformation")
    $scope.globl = new Object(); 
    var postData = new Object();
    postData.reqPaparam = {  
      "itemInfo":$scope.store,
      
    };
    if(agentInfo){
      postData.reqPaparam.userPublicKey = agentInfo;
    }
    else if(!$scope.inviteNewConsumer)
    {
      postData.reqPaparam.userPublicKey = '';
      postData.reqPaparam.itemInfo.newConsumerData = {
        "firstName" : $scope.store.fname,
        "lastName" : $scope.store.lname,
        "emailId" : $scope.store.emailId
      }

    }
    else{
      postData.reqPaparam.userPublicKey = savedData.publicKey;

    }
    postData.reqPaparam.action =  "add";
    sessionStorage.setItem("itemSuccessmsg", 1);

    if($scope.store.itemId){  
        postData.reqPaparam.itemInfo.itemId = $scope.store.itemId;
        postData.reqPaparam.action =  "update";
        sessionStorage.setItem("itemSuccessmsg", 2);
    }

   if(!$scope.storeData.storeId){   
            /*get and define variable for address*/
            var storeAddress ={
              addressLine1: $scope.storeData.addressLine1,
              addressLine2:$scope.storeData.addressLine2,
              country:$scope.storeData.country,
              state :$scope.storeData.state,
              zipCode : $scope.storeData.zipCode,
              city : $scope.storeData.city
            };
            postData.reqPaparam.itemInfo.storeExist = false;
            postData.reqPaparam.itemInfo.storeData = {  
                  storeName:$scope.storeData.store_name,
                  storeAddress:storeAddress,
                  storePhone :$scope.storeData.storePhoneNumber
            };
     }else{
         postData.reqPaparam.itemInfo.storeExist = true;
         postData.reqPaparam.itemInfo.storeData = {  
                  storeId :$scope.storeData.storeId
         }
     }
  
    /*get service url additem*/
    var url = valouxService.getWebServiceUrl("addOrUpdateItem");
    //post Registration form agent data and get response
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
       if (data.sCode == RESPONSE_CODE.SUCCESS) {
        $scope.formError = false;
        $scope.formSuccess= true;
        if(!$scope.inviteNewConsumer){
          sessionStorage.removeItem("agentInformation");
          sessionStorage.setItem("agentInformation",data.resData.userPublicKey)
          $scope.consumerUserPublicKey = data.resData.userPublicKey;
        }
        $location.path('/success/'+data.resData.itemId);
        window.scrollTo(500, 0);
        

      }else{
        $scope.formSuccess= false;
        $scope.formError = true;
        window.scrollTo(500, 0);
        $scope.errorMessage = data.errorMessage;
      }
    },function(){
      console.log("Error in add item");
    });
}else{
         $scope.formSuccess= false;
         $scope.formError = true;
         $scope.errorMessage = "Please reslove below errors.";
         window.scrollTo(500, 0);
    }
}

/* set value to show active class in view by shared */
$scope.viewSharedListingBy = function(vlu) {
	if(vlu==4)
	{
		$scope.sortby="";
	}
	$scope.sharedListingBy = vlu;
	$scope.initSharedData(vlu);
}

/* function to redirect user from shared by me page for view by */
$scope.viewSharedbymeListing = function(rUrl){
	location.href=rUrl;
}

/*Init Shared data in shared dashboard*/
$scope.initSharedData = function(viewType) {
	
	$scope.globl = new Object();

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  $timeout(function(){angular.element('ul.tabs').tabs(); angular.element('.dropdown-button').dropdown(); angular.element("#sortBy").material_select(); angular.element("#viewBy").material_select();}, 500);
  
  /*Sorting element according to latest & alphabatical order*/
  
  
  $scope.globl.sortorder = '-id';
  var postData = new Object();
	if(viewType==1)
	{
  		postData.reqPaparam = { "sharedBy" : savedData.memberShipKey,"sharedWith":viewType};
	}else if(viewType==2){
		postData.reqPaparam = { "sharedBy" : savedData.memberShipKey,"sharedWith":viewType};
	}else if(viewType==3){
		postData.reqPaparam = { "sharedBy" : savedData.memberShipKey,"sharedWith":viewType};
	}else if(viewType==5){
		location.href="sharedwithme.html";
	}else
	{
		postData.reqPaparam = { "sharedBy" : savedData.memberShipKey};	
	}
  
  var url = valouxService.getWebServiceUrl("getUserSharedList");
  //post request to get shared data response
  var submitShared = valouxService.getData(url,postData);
  submitShared.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.animated();
      $timeout(function(){angular.element('ul.tabs').tabs(); angular.element('.dropdown-button').dropdown(); angular.element("#sortBy").material_select(); angular.element("#viewBy").material_select();}, 500);
      if(typeof data.sharedItemList == "undefined"){
        $scope.noItem = true;
      }else{
        $scope.noItem = false;
        $scope.sharedList =  data.sharedItemList;
		    $timeout(function(){angular.element('.tooltipped').tooltip(); }, 500);
      }
     }else{
      $scope.collectionFormError = true;
      $scope.errorMessage = data.errorMessage;
    }
  },function(){
    console.log("Error in shared listing");
  });
}

/* funcation tounshare item confirmation */

$scope.UnshareItemConform = function(itemId,ItemType,sharedTo) {
	$scope.itemId = itemId;
	$scope.ItemType = ItemType;
	$scope.sharedTo = sharedTo;
	$('#modal-unshare').openModal();
}

/* to unshare item without confirmation */
$scope.UnshareItemWithoutConform = function(itemId,ItemType,sharedTo) {
	$scope.itemId = itemId;
	$scope.ItemType = ItemType;
	$scope.sharedTo = sharedTo;
	$scope.UnshareItem();
}

/*function to unshare data from shared listing page*/
$scope.UnshareItem = function() {
	$scope.animated();
	 $timeout(function(){ $('#modal-unshare').closeModal();$('#modal-items-agents').closeModal();$('#modal-items-consumers').closeModal();}, 100);
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  /*Sorting element according to latest & alphabatical order*/
  
  var postData = new Object();
	if($scope.sharedTo)
	{
  		postData.reqPaparam = { "userPublicKey" : savedData.memberShipKey,"sharedItemId":$scope.itemId ,"sharedItemType":$scope.ItemType,"sharedTo":$scope.sharedTo};
	}else
	{
		postData.reqPaparam = { "userPublicKey" : savedData.memberShipKey,"sharedItemId":$scope.itemId,"sharedItemType":$scope.ItemType};	
	}
  
  var url = valouxService.getWebServiceUrl("UnShareItem");
  //post request to un shar data response
  var submitUnShared = valouxService.getData(url,postData);
  submitUnShared.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      
       $scope.itemSuccessmsg = data.successMessage;
	  
		 $timeout(function(){ $('#modal-unshare-thankyou').openModal();}, 100);
	   
	   $scope.initSharedData();
     }else{
      $scope.collectionFormError = true;
      $scope.errorMessage = data.errorMessage;
    }
							
  },function(){
    console.log("Error in Unshare calling");
  });
  
     
}



/*Init Shared with Me data in shared dashboard*/
$scope.initSharedWithMeData = function() {
	$scope.globl = new Object();
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  $timeout(function(){$('.dropdown-button').dropdown(); $('ul.tabs').tabs();angular.element("#viewBy").material_select();angular.element("#sortBy").material_select();}, 100);
  /*Sorting element according to latest & alphabatical order*/
  
   
  $scope.globl.sortorder = '-id';
  var postData = new Object();

if(typeof $scope.approvedStatus !="undefined"){
  postData.reqPaparam = { "sharedTo" : savedData.memberShipKey,"approvedStatus":$scope.approvedStatus
  };
  }else{
   postData.reqPaparam = { "sharedTo" : savedData.memberShipKey}; 
  }
  var url = valouxService.getWebServiceUrl("getUserSharedWithMeList");
  //post request to get shared with me data response
  var submitSharedWithMe = valouxService.getData(url,postData);
  submitSharedWithMe.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      if(typeof data.sharedItemList == "undefined"){
        $scope.noItem = true;
      }else{
        $scope.noItem = false;
        $scope.sharedList =  data.sharedItemList;
     // create unqiue list of users shared the items
		var uniqueNames = {};
			// create array with key and value
			for(i = 0; i< data.sharedItemList.length; i++){ 
			uniqueNames[data.sharedItemList[i].sharedBy.consumerPublicKey]=data.sharedItemList[i].sharedBy.firstName + " " +data.sharedItemList[i].sharedBy.lastName;	        
			}
			// converting it to angular object from js array
			var uniqueNamesAJ = [];
			$.each(uniqueNames, function(key, value) {
				uniqueNamesAJ.push({cKey:key,name:value});
			});
			
		 $scope.uniqueNames = uniqueNamesAJ ;
      }
     }else{
      $scope.collectionFormError = true;
      $scope.errorMessage = data.errorMessage;
    }
    $timeout(function(){ angular.element("#viewItemsBy").material_select();}, 500);
    $timeout(function(){ angular.element("select").material_select();}, 500);

  },function(){
    console.log("Error in shared with Me listing");
  });
}


/*invite new consumer in agent */
$scope.inviteNewConsumerAgent = function() {
  $scope.inviteNewConsumer = true;
  sessionStorage.removeItem("agentInformation");
  $route.reload();
  $window.location = "item-agent.html#/add"
}


/*Init Shared status data in shared dashboard*/
$scope.shareStatus = function(sharedstatus,sharedlist) {

 var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  $scope.sharedStatus=sharedstatus;
  /*Sorting element according to latest & alphabatical order*/
  var RequestCount= sessionStorage.getItem("RequestCount");
   
  $scope.sortorder = '-id';
  var postData = new Object();

  postData.reqPaparam = { "sharedRequestId" : sharedlist.sharedRequestId, "approvedStatus" :sharedstatus};
  
  var url = valouxService.getWebServiceUrl("acceptOrRejectShareRequest");
  //post request to get shared with me data response
  var submitSharedWithMe = valouxService.getData(url,postData);
  submitSharedWithMe.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.sharedList.splice($scope.sharedList.indexOf(sharedlist), 1)
      $scope.animated();
      $scope.successMessage=false;
      $timeout(function(){ angular.element("#sortBy").material_select();}, 500);
      $timeout(function(){ angular.element("select").material_select();}, 500);
      if( $scope.sharedStatus==2){ 
         $scope.successMessage="Request has been accepted";
        }else{
           
          $scope.successMessage="Request has been rejected";
        }
       var uniqueNames = {};
      // create array with key and value
      for(i = 0; i< $scope.sharedList.length; i++){ 
      uniqueNames[$scope.sharedList[i].sharedBy.consumerPublicKey]=$scope.sharedList[i].sharedBy.firstName + " " +$scope.sharedList[i].sharedBy.lastName;         
      }
      // converting it to angular object from js array
      var uniqueNamesAJ = [];
      $.each(uniqueNames, function(key, value) {
        uniqueNamesAJ.push({cKey:key,name:value});
      });
     $scope.uniqueNames2 = uniqueNamesAJ ;
     if($scope.uniqueNames2.length != $scope.uniqueNames.length){
      $scope.uniqueNames = uniqueNamesAJ ;
     }
        angular.element(document.getElementById('request-count')).html($scope.sharedList.length);
        angular.element(document.getElementById('side-request-count')).html($scope.sharedList.length);
      $route.reload();
     }else{
      $scope.collectionFormError = true;
      $scope.errorMessage = data.errorMessage;
    }$timeout(function () { $scope.successMessage = false; }, 3000);
  },function(){
    console.log("Error in shared with Me listing");
  });
}

/*Init Shared with Agent data in shared dashboard*/
$scope.initSharedWithAgent = function() {

 var savedItemAgent = sessionStorage.getItem("agentInformation");
 $timeout(function(){ angular.element("select").material_select();}, 500);
  /*Sorting element according to latest & alphabatical order*/
  $scope.approvedStatus="1";

  $scope.animated();
  $scope.initSharedWithMeData();  
}

/*collection listed selected pop up items */
$scope.collectionListedItemNew = function(cid, itemCount, items) {
 
  $scope.collectionId = cid;
  $scope.itemsList = items;

  if(itemCount == 0){
    angular.element('#modal-noitems').openModal();
  }
  else{
    angular.element('#modal-items').openModal();

    var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    
    var postData = new Object();
    postData.reqPaparam = { "cId" : cid }

    
      postData.reqPaparam.userPublicKey = savedData.memberShipKey;
    
    var url = valouxService.getWebServiceUrl("getItemsOfCollection");
    //post data and get response
    var submitCollection = valouxService.getData(url,postData);
    submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
		
      $scope.collectionItemTotal = data.resData.items.length;
      $scope.collectionPopupItems = data.resData.items;
       
      $timeout(function(){ $('.mCustomScrollbar').mCustomScrollbar(); angular.element('.tooltipped').tooltip(); }, 500);

      if(data.resData.items.length == 0){
        angular.element('#modal-noitems').openModal();
        angular.element('#modal-items').closeModal();
      }
    }else{
      angular.element('#modal-noitems').openModal();
    }
  },function(){
    console.log("Error in get item list from collection listing");
  });

  }
}

/*Init Shared with Me data in shared dashboard*/
$scope.sharedItemlist = function(id,type,itemcount) {

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
    var postData = new Object();
    if(type==2){
    postData.reqPaparam = { "cId" : id ,"userPublicKey" :savedData.memberShipKey}
   // postData.reqPaparam.userPublicKey = savedData.memberShipKey;   
    var url = valouxService.getWebServiceUrl("getItemsOfCollection");
    //post data and get response
    var submitCollection = valouxService.getData(url,postData);
    submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.collectionItemTotal = data.resData.items.length;
      $scope.collectionPopupItems = data.resData.items;
       $scope.modal=true;
       angular.element('#modal-add-new-items').openModal();
    $timeout(function(){ $('.mCustomScrollbar').mCustomScrollbar(); angular.element('.tooltipped').tooltip(); }, 500);   
    }else{
       console.log("Error in get item list from collection listing");
    }
  },function(){
    console.log("Error in get item list from collection listing");
  });
  }
  if(type==3)
   {
    postData.reqPaparam = { 
    "userPublicKey" : savedData.memberShipKey,
    "appraisalId" : id
  };
  var url = valouxService.getWebServiceUrl("getItemsOfAppraisal");
  //post data and get response
  var submitCollection = valouxService.getData(url,postData);
    submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.appraiselItemTotal = data.resData.itemsList.length;
      $scope.appraiselPopupItems = data.resData.itemsList;
       angular.element('#modal-add-new-appraisel-items').openModal();
    $timeout(function(){ $('.mCustomScrollbar').mCustomScrollbar(); angular.element('.tooltipped').tooltip(); }, 500);   
    }else{
       console.log("Error in get item list from collection listing");
    }
  },function(){
    console.log("Error in get item list from collection listing");
  });
   }
}



/*Init Shared with Me data in shared dashboard*/
$scope.sharedCollectionlist = function(id,type) {

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
    var postData = new Object();
    
    postData.reqPaparam = { "appraisalId" : id ,"userPublicKey" :savedData.memberShipKey}
   // postData.reqPaparam.userPublicKey = savedData.memberShipKey;   
    var url = valouxService.getWebServiceUrl("getCollectionOfAppraisal");
    //post data and get response
    var submitCollection = valouxService.getData(url,postData);
    submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.collectionAppraisalTotal = data.resData.collectionList.length;
      $scope.appraisalPopupCollections = data.resData.collectionList;
       $scope.modal=true;
       angular.element('#modal-add-new-appraisel-collection').openModal();
    $timeout(function(){ $('.mCustomScrollbar').mCustomScrollbar(); angular.element('.tooltipped').tooltip(); }, 500);   
    }else{
       console.log("Error in get item list from collection listing");
    }
  },function(){
    console.log("Error in get item list from collection listing");
  });
  }

/*load listing gallary set agent info in session*/
$scope.requestConsumerInfo = function(agentInfo ,event) { 
  if(agentInfo){
   // sessionStorage.setItem("agentInformation", JSON.stringify(agentInfo.cKey));
  }
 // sessionStorage.getItem("agentInformation");
  $timeout(function(){ angular.element('ul.tabs').tabs();}, 100);
  
}


/*sortbyfilter for share function */
$scope.itemFilterToggle = function(itm) {
	$timeout(function(){ angular.element('.dropdown-button').dropdown(); }, 500);
    if(itm==1)
	{
			$scope.sortby=1;
	}else if(itm==2)
	{
			$scope.sortby=2;
	}else if(itm==3)
	{
			$scope.sortby=3;
	}else if(itm==4)
  	{
      $scope.sortby='';
  	}else
	{
		$scope.sortby='';
		if(itm=='shared')
		{
			$scope.initSharedData();
		}else
		{
			$scope.initSharedWithMeData();	
		}
		
			
	}
}


$scope.removeItemimages = function(itemId,imageId,event){
    var postData = new Object();
    postData.reqPaparam = {  
      "itemId":itemId,
      "imageId":imageId
    };
    /*get service url additem*/
    var url = valouxService.getWebServiceUrl("deleteImageByImageId");
    //post Registration form agent data and get response
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
       if (data.sCode == RESPONSE_CODE.SUCCESS) {
           angular.element(event.target).parent().parent().remove();
      }
    },function(){
      console.log("Error in delete image");
    });
}
// file upload Item Receipt 
$scope.uploadItemReceipt = function(e){
    var imageObj = e.target;

    $scope.store.itemReceiptContent = "";
    $scope.store.itemReceiptName = "";
    
    $scope.inValidReceiptText = "";
    $scope.store.itemReceiptName = imageObj.files[0].name;

    if ( imageObj.files && imageObj.files[0] ) {
        var allowed = ["txt", "doc","pdf","jpeg", "png"];
        var fileType = imageObj.files[0].type;
        var fileSize = imageObj.files[0].size;
        fileSize = (fileSize/1024/1024);
        var returnValue = true;
        allowed.forEach(function(extension) { 
            if (imageObj.files[0].type.match('text/plain') || imageObj.files[0].type.match('application/pdf') || imageObj.files[0].type.match('image/'+extension) || imageObj.files[0].type.match('application/msword')) {
                returnValue = false;
                $scope.inValidReceipt = false;
            }
        })
        if(returnValue)
        {
            $scope.inValidReceipt = true;
            $scope.inValidReceiptText = "Please select [txt, doc, pdf, jpeg, png] image.";
            $scope.$apply();
            return;

        }else if(fileSize > 2)
        {   
            $scope.inValidReceipt = true;
            $scope.inValidReceiptText = "Please select image less or equal to 2 MB.";
            $scope.$apply();
            return;
        }
         
    }

    
    // !$scope.validateimage(imageObj) &&
    if ( imageObj.files && imageObj.files[0] ) {   
        var FR= new FileReader();                
        FR.onload = function(e) { 

            var newImageObj = e.target.result;
            $scope.imageSrc = e.target.result;
            $scope.$apply();
            newImageObj = newImageObj.substr(newImageObj.indexOf(',') + 1);
            $scope.store.itemReceiptContent = newImageObj;     
        };       
        FR.readAsDataURL( imageObj.files[0] );                
    }
}

// upload Item Certificate 
$scope.uploadItemCertificate = function(e){
    var imageObj = e.target;

    $scope.store.itemCertificateContent = "";
    $scope.store.itemCertificateName = "";
    
    $scope.inValidCertificateText = "";
    $scope.store.itemCertificateName = imageObj.files[0].name;
    if ( imageObj.files && imageObj.files[0] ) {
        var allowed = ["txt", "doc","pdf","jpeg", "png"];
        var fileType = imageObj.files[0].type;
        var fileSize = imageObj.files[0].size;
        fileSize = (fileSize/1024/1024);
        var returnValue = true;
        allowed.forEach(function(extension) {
            if (imageObj.files[0].type.match('text/plain') || imageObj.files[0].type.match('application/pdf') || imageObj.files[0].type.match('image/'+extension) || imageObj.files[0].type.match('application/msword')) {
                $scope.inValidCertificate = false;
                returnValue = false;
            }
        })
        if(returnValue)
        {   
            $scope.inValidCertificate = true;
            $scope.inValidCertificateText = "Please select [txt, doc, pdf, jpeg, png] image.";
            $scope.$apply();
            return;

        }else if(fileSize > 2)
        {   
            
            $scope.inValidCertificate = true;
            $scope.inValidCertificateText = "Please select image less or equal to 2 MB.";
            $scope.$apply();
            return;
        }
    }
    // !$scope.validateimage(imageObj) &&
    if ( imageObj.files && imageObj.files[0] ) {
        var FR= new FileReader();                
        FR.onload = function(e) { 

            var newImageObj = e.target.result;
            $scope.imageSrc = e.target.result;
            $scope.$apply();
            newImageObj = newImageObj.substr(newImageObj.indexOf(',') + 1);
            $scope.store.itemCertificateContent = newImageObj;     
        };       
        FR.readAsDataURL( imageObj.files[0] );                
    }
}

// upload Item Images
$scope.uploadItemImages = function(e){
    var elementId = e.data.elementId; 
    var imageObj = e.target;
    var ItemImageContent = "";
    var ItemImageName = "";
    $scope.inValidImage = false;
    $scope.inValidImageText = "";
    ItemImageName = imageObj.files[0].name;
    if ( imageObj.files && imageObj.files[0] ) {
        var allowed = ["jpeg", "png"];
        var fileType = imageObj.files[0].type;
        var fileSize = imageObj.files[0].size;
        fileSize = (fileSize/1024/1024);
        var returnValue = true;
        allowed.forEach(function(extension) {
            if (imageObj.files[0].type.match('image/'+extension)) {
                returnValue = false;
                $scope.isItemImage = false
                $scope.inValidimg[elementId] = false;

            }
        })
        if(returnValue)
        {
            $scope.inValidimg[elementId] = true;
            $scope.isItemImage = true
            $scope.inValidimgText[elementId] = "Please select [jpeg, png] image.";
            $scope.$apply();
            return;

        }else if(fileSize > 2)
        {
            $scope.inValidimg[elementId] = true;
            $scope.isItemImage = true
            $scope.inValidimgText[elementId] = "Please select image less or equal to 2 MB.";
            $scope.$apply();
            return;
        }
    }
    // 
    if (imageObj.files && imageObj.files[0] ) {
        var FR= new FileReader();                
        FR.onload = function(e) { 

            var newImageObj = e.target.result;
            $scope.imageSrc = e.target.result;
            $scope.$apply();
            if(newImageObj.indexOf("data:image/jpeg;base64,") > -1)
            {
                newImageObj = newImageObj.replace("data:image/jpeg;base64,", "");
            }
            else if(newImageObj.indexOf("data:image/png;base64,") > -1)
            {
                newImageObj = newImageObj.replace("data:image/png;base64,", "");
            }
            else if(newImageObj.indexOf("data:image/gif;base64,") > -1)
            {
                newImageObj = newImageObj.replace("data:image/gif;base64,", "");
            }
            else if(newImageObj.indexOf("data:image/jpg;base64,") > -1)
            {
                newImageObj = newImageObj.replace("data:image/jpg;base64,", "");
            }
            ItemImageContent= newImageObj;   
             $scope.store.itemImages[elementId] = {'itemImageContent' : ItemImageContent,'itemImageName':ItemImageName};
        };       
        FR.readAsDataURL( imageObj.files[0] );                
    }
}

// remove file upload input type
$scope.removeImage = function(indId) {
    if(indId){
        $scope.counterImage.splice(indId,1);
        $scope.store.itemImages.splice(indId,1);
        $scope.inValidimg[indId]=false;
        $scope.isItemImage = false
    }else{
      $scope.store.itemImages.splice(indId,1);
      $('#uploadImgfield_'+indId).parent().parent().find('input.file-path').val('');
      $scope.inValidimg[indId]=false;
      $scope.isItemImage = false
    }
}

// clear reciept file upload
$scope.clearReceipt = function() {
    delete $scope.store.itemReceiptContent;
    delete $scope.store.itemReceiptName;  
    $('#fileuploadReceipt').parent().parent().find('input.file-path').val('');
    $scope.inValidReceipt = false;
    
}
// clear Certificate file upload
$scope.clearCertificate = function() {
    delete $scope.store.itemCertificateContent;
    delete $scope.store.itemCertificateName; 
    $('#fileuploadCertificate').parent().parent().find('input.file-path').val('');
     $scope.inValidCertificate = false;
     
}

// validate image type
$scope.validateimage = function(imageObj) {
    var returnValue = false;
    if ( imageObj.files && imageObj.files[0] ) {
        var allowed = ["jpeg", "png"];
        var fileType = imageObj.files[0].type;
        var fileSize = imageObj.files[0].size;
        fileSize = (fileSize/1024/1024);
        returnValue = true;
        allowed.forEach(function(extension) {
            if (imageObj.files[0].type.match('image/'+extension)) {
                returnValue = false;
            }
        })
        if(returnValue)
        {
            $scope.inValidImageText = "Please select [jpeg, png] image.";
        }
        if(fileSize > 2)
        {
            returnValue = true;
            $scope.inValidImageText = "Please select image less or equal to 2 MB.";
        }
    }
    return returnValue;
}

$scope.getAllStoreAgentsForAdmin = function() {
    
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);

    var postData = new Object();
    postData.reqPaparam = { "agentPublicKey" : savedData.memberShipKey};
  
    /*get service url userRegistration*/
    var url = valouxService.getWebServiceUrl("getAllStoreAgentsForAdmin",postData);
    //post Registration form agent data and get response
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
       if (data.sCode == RESPONSE_CODE.SUCCESS) {
        $scope.getAllStoreUser = data.resData.agentsData.storeAgents
    }else{
        
      }
    },function(){
      console.log("Error in add item");
    });
    
}

// Animated collection listing
$scope.animated = function() {
  $timeout(function(){
    var speed = 900,
    containers = document.getElementsByClassName("u-fancy-load");
  for(var c = 0; c < containers.length; c++){
    var container = containers[c],
    children = container.children;
    
    if(!container.classList.contains("delay-set")){
      container.classList.add("delay-set");
      
      for(var i = 0; i < children.length; i++){
        var child = children[i],
            childOffset = child.getBoundingClientRect(),
            offset = childOffset.left*0.001 + childOffset.top,
            delay = parseFloat(offset/speed).toFixed(2);
        
        child.style.webkitTransitionDelay = delay + "s";
        child.style.transitionDelay = delay + "s";
      }
    }
    container.classList.add("u-fancy-load--in");
 }
}, 500);
}

// show items in  list page 
$scope.itemlisting= function() {
  $scope.globl = new Object();
  //$scope.globl.searchKeyword = new Object();
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  $scope.globl.sortorder='-itemId';
  $scope.userKey = savedData.memberShipKey;
  var postData = new Object();
  postData.reqPaparam = { "userPublicKey" : savedData.memberShipKey};
  var url = valouxService.getWebServiceUrl("itemList");
  var submitItem = valouxService.getData(url,postData);
  submitItem.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      if(typeof data.resData.itemData == "undefined"){
        $scope.itemData = false;
        $scope.stepButton = false;
        $scope.getItemList = false;
      }else{
        $scope.noItem = false;
        $scope.itemData = data.resData.itemData;        
        $scope.stepButton = false;
        $scope.getItemList = true;
      }
      $scope.successMessage = sessionStorage.getItem("successMessage");
      $timeout(function(){angular.element('#success').hide('slow');  }, 2000);
      sessionStorage.removeItem("successMessage");
      $scope.animated();
      $timeout(function(){ 
          $('ul.tabs').tabs(); 
          angular.element("#sortBy").material_select(); 
          angular.element('.dropdown-button').dropdown();
      }, 500);
    }else{
      $scope.noItem = false;
      $scope.getItemList = false;
    }
  },function(){
    console.log("Error in get item list");
  });
}

// show items in  list in agent page 
$scope.initItemlistAgent= function() {
  $scope.globl = new Object();
  //$scope.globl.searchKeyword = new Object();
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  $scope.globl.sortorder='-itemId';
  var postData = new Object();
  
  postData.reqPaparam = { "sharedTo" : savedData.memberShipKey,
  "sharedItemType" : "1",
  "approvedStatus" : "2"
  };

  var url = valouxService.getWebServiceUrl("getUserSharedWithMeList");
  var submitItem = valouxService.getData(url,postData);
  submitItem.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      if(typeof data.sharedItemList == "undefined"){
        $scope.itemData = true;
        $scope.stepButton = false;
        $scope.getItemList = false;
      }else{
        $scope.noItem = false;
        $scope.itemData = data.sharedItemList;
        $scope.stepButton = false;
        $scope.getItemList = true;
      }
      $scope.successMessage = sessionStorage.getItem("successMessage");
      $timeout(function(){angular.element('#success').hide('slow');  }, 2000);
      sessionStorage.removeItem("successMessage");
      $scope.initConsumerInformation();
      $scope.animated();
      $timeout(function(){ 
          $('ul.tabs').tabs(); 
          angular.element("#sortBy").material_select();
          angular.element("#viewedItemsBy").material_select();
          angular.element('.dropdown-button').dropdown();
      }, 500);
    }else{
      $scope.noItem = false;
      $scope.getItemList = false;
    }
  },function(){
    console.log("Error in get item list");
  });
}

/*Init consumer information in agent session*/
$scope.initConsumerInformation= function() {
  var savedItemAgent = sessionStorage.getItem("agentInformation");
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var postData = new Object();
  postData.reqPaparam = { 
      "userPublicKey" : savedData.memberShipKey,
      
    };
  var url = valouxService.getWebServiceUrl("getConsumerListWhoSharedItemWithAgent");
  //post data and get response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.viewByConsumer = data.consumerList;
      angular.forEach(data.consumerList, function(info){
      if(info.consumerPublicKey == savedItemAgent){
        $scope.consumerList = info;
        $scope.viewItemBy = info.consumerPublicKey;
      }
      
    })
    $timeout(function(){angular.element("#viewedItemsBy").material_select(); }, 500);

    }else{
      $scope.collectionFormError = data.errorMessage;
    }
  },function(){
    console.log("Error in get item list");
  });
}

/*view all data  in agent */
$scope.viewAllData = function() {
  $route.reload()
  sessionStorage.removeItem("agentInformation");
}

/*load listing gallary set agent info in session*/
$scope.storeConsumerInfo = function(agentInfo, event) {
  $scope.$parent.fullPageBlockLoading = true;
  
  if(agentInfo){
    sessionStorage.setItem("agentInformation", agentInfo);
    $scope.initConsumerInformation();
  }else{
    $scope.viewAllData();
  }
  $timeout(function(){ angular.element('.dropdown-button').dropdown();}, 100);
  
}


// show items in  list page for agent section
$scope.itemlistingAgent= function() { 
  $scope.globl = new Object();
  $scope.globl.searchKeyword = new Object();
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  $scope.globl.sortorder='-itemId';
  var postData = new Object();
  postData.reqPaparam = { "sharedTo" : savedData.memberShipKey,"sharedItemType":1,"approvedStatus":1};
  var url = valouxService.getWebServiceUrl("getUserSharedWithMeList");
  var submitItem = valouxService.getData(url,postData);
  submitItem.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      if(typeof data.sharedItemList == "undefined"){
        $scope.noItem = true;
        $scope.stepButton = false;
        $scope.getItemList = false;
      }else{
        $scope.noItem = false;
        $scope.itemData = data.sharedItemList;
        $scope.stepButton = false;
        $scope.getItemList = true;
      }
      $scope.animated();
      $timeout(function(){ 
          $('ul.tabs').tabs(); 
          angular.element("#sortBy").material_select(); 
          angular.element("#consumerSelect").material_select(); 
          angular.element('.dropdown-button').dropdown();
      }, 500);
    }else{
      $scope.noItem = false;
      $scope.getItemList = false;
    }
  },function(){
    console.log("Error in get item list");
  });
}


$scope.keyPressSearch = function(){
  $timeout(function(){ angular.element('ul.tabs').tabs(); angular.element('.dropdown-button').dropdown();}, 100);
   
}

/*Clear search data */
$scope.clearSearchData = function(event) {
  angular.element('#search2').val('');
  //$('.tooltipped').tooltip('remove');
  $timeout(function(){ angular.element('ul.tabs').tabs(); angular.element('.dropdown-button').dropdown();}, 100);
  $scope.globl.searchKeyword = "";
}


//initilize  component panel variable
$scope.initcomponentPanel= function() {
    $scope.addCompType = [];
    $scope.itemComponents=[];
    $scope.selectComptypeRowIndex = 0;
    $scope.store.modelChkboxCompType = 0;
    
    $scope.getComponentType();
    $timeout(function(){
           $('.modal-trigger').leanModal();
   }, 500);
}

//Show Select Component
$scope.showSelectComponentList= function() { 
    $scope.addNowcomponents=0;
    $scope.showSelectComponent=1;
}

// get all component types
$scope.getComponentType= function() {
  var postData = new Object();
  var url = valouxService.getWebServiceUrl("getComponentsList");
  var submitItem = valouxService.getData(url,postData);
  submitItem.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
        $scope.componenttypes = data.resData.componentList;
    }
    
    $timeout(function(){
           $('.mCustomScrollbar').mCustomScrollbar();
   }, 500);
    
  },function(){
    console.log("Error in get item list");
  });
}

//locally add component type and show add component list
$scope.addComponentType= function() {
  $scope.showSelectComponent=0;
  $scope.showAddComponent=1;
  $scope.selectedCompType = [];
  $scope.addCompType = [];
  var cnt = 0;
  angular.forEach($scope.componenttypes, function(compType){
      if (compType.selected){
        $scope.selectedCompType.push(compType.componentId);
        $scope.itemComponents.push({"componentTypeName":compType.componentName,"componentType":compType.componentId,"componentQuantity":1});
        $scope.addCompType.push(cnt);
        cnt++;
      }
   }) 
  if(cnt==0){
      $scope.addCompType.push($scope.addCompType.length+1);
      var itemComponentsLength = $scope.itemComponents.length;
      $scope.itemComponents[itemComponentsLength] = {"componentTypeName":'Select',"componentType":0,"componentQuantity":1};
  }
   
   $timeout(function(){
           $('.modal-trigger').leanModal();
   }, 500);

}

//Quatity Plus Minus
$scope.QuatityPlusMinus= function(indexNum,type) {
   var currentVal =  $scope.itemComponents[indexNum].componentQuantity
    if (!isNaN(currentVal)) {
        if(type == 0) {
            if($scope.itemComponents[indexNum].componentQuantity>1){
                $scope.itemComponents[indexNum].componentQuantity = $scope.itemComponents[indexNum].componentQuantity - 1;
            }  
        } else if(type == 1) {
            $scope.itemComponents[indexNum].componentQuantity = $scope.itemComponents[indexNum].componentQuantity + 1;
        }
    } else {
        $scope.itemComponents[indexNum].componentQuantity = 1;
    }
}

//Quatity Plus Minus at time of edit component
$scope.QuatityPlusMinusEdit= function(type) {
   var currentVal =  $scope.component.componentQuantity
    if (!isNaN(currentVal)) {
        if(type == 0) {
            if($scope.component.componentQuantity>1){
                $scope.component.componentQuantity = $scope.component.componentQuantity - 1;
            }  
        } else if(type == 1) {
            $scope.component.componentQuantity = $scope.component.componentQuantity + 1;
        }
    } else {
        $scope.component.componentQuantity = 1;
    }
}

$scope.addmoreComponents= function() { 
    $scope.addCompType.push($scope.addCompType.length+1);
    var itemComponentsLength = $scope.itemComponents.length;
    $scope.itemComponents[itemComponentsLength] = {"componentTypeName":'Select',"componentType":0,"componentQuantity":1};
    $timeout(function(){
           $('.clscomponentType'+itemComponentsLength).leanModal();
           $('.mCustomScrollbar').mCustomScrollbar("scrollTo","bottom",{scrollInertia:200});
   }, 500);
}

$scope.hideAddmoreComponents = function(indId) {
        $scope.addCompType.splice(indId,1);
        $scope.itemComponents.splice(indId,1);
}

$scope.saveItemComponent= function() {
    $scope.$parent.fullPageBlockLoading = true;
    var chkValid = 0;
    var cnt = 0;
    angular.forEach($scope.itemComponents, function(compType){
      if (compType.componentType==0){
        chkValid = 1;
       var clsString = '.clscomponentType'+cnt;
        $timeout(function(){
           angular.element(clsString).css( 'border-bottom', '2px solid #f44336');
          
        }, 200);
      }
      cnt++;
    }) 
    
    if(chkValid){
        return;
    }
    
    var itemId = $route.current.paparams.itemId
    var postData = new Object();
    postData.reqPaparam = {  
        "itemId":itemId,
        "itemComponents":$scope.itemComponents
      };
   
    var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    var savedItemAgent = sessionStorage.getItem("agentInformation");
    if(savedItemAgent){
      postData.reqPaparam.userPublicKey = savedItemAgent;
    }else{
      postData.reqPaparam.userPublicKey = savedData.memberShipKey;

    }

    var url = valouxService.getWebServiceUrl("addUpdateItemComponents");
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
      if (data.sCode == RESPONSE_CODE.SUCCESS) {
          $scope.addCompType = [];
          $scope.itemComponents=[]
          $scope.showAddComponent=0;
          $scope.showComponentsAdded = 1;
          $scope.componentsAdded = data.resData.componentList;
          $scope.$parent.getItemPriceProperties(itemId);
          $timeout(function(){
           $('.modal-trigger').leanModal();
         }, 500);
          
      }
    },function(){
      console.log("Error in save component");
    });
}

$scope.selectCurrentRow= function(indexNo, element ) {
    $scope.selectComptypeRowIndex = indexNo;
    $scope.store.modelChkboxCompType = $scope.itemComponents[indexNo].componentType;
    $(element.target).blur();

}

$scope.setRowComponentType= function() {
    $scope.itemComponents[$scope.selectComptypeRowIndex].componentType = $scope.store.modelChkboxCompType;
    angular.forEach($scope.componenttypes, function(compType){
      if (compType.componentId == $scope.store.modelChkboxCompType){
        $scope.itemComponents[$scope.selectComptypeRowIndex].componentTypeName = compType.componentName;
        var clsString = '.clscomponentType'+$scope.selectComptypeRowIndex;
        $timeout(function(){
           angular.element(clsString).css( 'border-bottom', '1px solid #9e9e9e');
          
        }, 200);
         
      }
   }) 
 
}

// check component is added or not in item;
function checkAddedItemComponents(itemId){
    $scope.addNowcomponents=1;
    $scope.showComponentsAdded = 0;
    $scope.componentsAdded = [];
    var postData = new Object();
    postData.reqPaparam = {  
        "itemId":itemId
      };
    
    var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    var savedItemAgent = sessionStorage.getItem("agentInformation");
    if(savedItemAgent){
         postData.reqPaparam.userPublicKey = savedItemAgent;
    }else{
         postData.reqPaparam.userPublicKey = savedData.memberShipKey;
    }

    var url = valouxService.getWebServiceUrl("getItemComponents");
    var getItem = valouxService.getData(url,postData);
    getItem.then(function(data){
      if (data.sCode == RESPONSE_CODE.SUCCESS) {
        $scope.componentsAdded = data.resData.componentList
        if($scope.componentsAdded.length>0){
            $scope.addNowcomponents=0;
            $scope.showComponentsAdded = 1;
            
             $timeout(function(){
                 $('.modal-trigger').leanModal();
            }, 500);
        }
      }
    },function(){
      console.log("Error in get item list");
    });
}

$scope.deleteItemComponent = function(componentId,$event) {
    var itemId = $route.current.paparams.itemId;
    var postData = new Object();
    postData.reqPaparam = {  
        "itemId":itemId,
        "itemComponentId":componentId
      };
    var url = valouxService.getWebServiceUrl("deleteComponentFromItem");
    var deleteItem = valouxService.getData(url,postData);
    deleteItem.then(function(data){
      if (data.sCode == RESPONSE_CODE.SUCCESS) {
            angular.element($event.currentTarget).parent().parent().remove();
            $scope.$parent.getItemPriceProperties(itemId);
      }
    },function(){
      console.log("Error in get item list");
    });
}


$scope.showComponentsEditPopup = function(componentObj) {
    $scope.component.componentName = componentObj.componentName;
    $scope.component.componentType = componentObj.componentType;
    $scope.component.componentQuantity = componentObj.componentQuantity;
    $scope.component.itemComponentId = componentObj.itemComponentId;
    $scope.propertyFlag = componentObj.propertyFlag;
    $timeout(function(){
       angular.element("#componentdrop").material_select();
    }, 500);
}

$scope.updateItemComponent= function() {
    var itemId = $route.current.paparams.itemId
    var postData = new Object();
    postData.reqPaparam = {  
        "itemId":itemId,
        "itemComponents":[$scope.component]
      };
      
    var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    var savedItemAgent = sessionStorage.getItem("agentInformation");
    if(savedItemAgent){
         postData.reqPaparam.userPublicKey = savedItemAgent;
    }else{
         postData.reqPaparam.userPublicKey = savedData.memberShipKey;
    }

    var url = valouxService.getWebServiceUrl("addUpdateItemComponents");
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
      if (data.sCode == RESPONSE_CODE.SUCCESS) {
          $scope.componentsAdded = data.resData.componentList;
           $('#modal4').closeModal();
           $timeout(function(){
           $('.modal-trigger').leanModal();
         }, 500);
         $scope.$parent.getItemPriceProperties(itemId);
      }
    },function(){
      console.log("Error in update component");
    });
}

/* collection listed in shared item popup */
$scope.itemListedCollectionShared = function(itemid){
	
	$scope.itemId = itemid;
  	
  	var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    var postData = new Object();
    postData.reqPaparam = { 
      "userPublicKey" : savedData.memberShipKey,
      "itemId" : itemid,
    }
 
    var url = valouxService.getWebServiceUrl("getCollectionsListAssociatedWithItem");
    //post data and get response
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
     if(typeof data.resData.collections == "undefined")
	 {

      	  $scope.noItemsAvaliable = true;
          angular.element('#modal-noitems').openModal();
          angular.element('#modal-collection-item').closeModal();

     }else
	 {   
      $timeout(function(){ angular.element('.tooltipped').tooltip(); }, 500);
      $scope.itemCollectionTotal = data.resData.collections.length;
      $scope.itemPopupCollection = data.resData.collections;
      if(data.resData.collections.length == 0){
        angular.element('#modal-noitems').openModal();
        angular.element('#modal-collection-item').closeModal();
      }else
      {
        angular.element('#modal-collection-item').openModal();
      }
	 }
      
      
    }else{
      angular.element('#modal-noitems').openModal();
    }
  },function(){
    console.log("Error in get item list from collection listing");
  });

  }

/*collection listed selected pop up items */
$scope.itemListedCollection = function(itemid, collectionCount) {
 
  $scope.itemId = itemid;
  $scope.collectionList = collectionCount;
  var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    var postData = new Object();
    postData.reqPaparam = { 
      "userPublicKey" : savedData.memberShipKey,
      "itemId" : itemid,
    }
  if(collectionCount == 0 || $scope.itemCollectionTotal ==0){
 
     var collectionurl = valouxService.getWebServiceUrl("getUserCollectionsNotInItem");
    //post data and get response
    var submitCollectionItem = valouxService.getData(collectionurl,postData);
    submitCollectionItem.then(function(data){            
      if (data.sCode == RESPONSE_CODE.SUCCESS) {
         $scope.errorSelectedCollection=''; 
        if(typeof data.resData.collectionData == "undefined"){ 
          $scope.noItemsAvaliable = true;
          angular.element('#modal-create-to-collection').openModal();
          angular.element('#modal-items').closeModal();
        }
          else{ angular.element('#modal-noitems').openModal();
            angular.element('#modal-items').closeModal();
          }
      }
    },function(){
    console.log("Error in get item list from collection listing");
  });
    //angular.element('#modal-noitems').openModal();
  }
  else{
    var url = valouxService.getWebServiceUrl("getCollectionsListAssociatedWithItem");
    //post data and get response
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
     if(typeof data.resData.collections == "undefined"){

      $scope.noItemsAvaliable = true;
          angular.element('#modal-create-to-collection').openModal();
          angular.element('#modal-items').closeModal();

     }else{   
      $timeout(function(){ angular.element('.tooltipped').tooltip(); }, 500);
      $scope.itemCollectionTotal = data.resData.collections.length;
      $scope.itemPopupCollection = data.resData.collections;
      if(data.resData.collections.length == 0){
        angular.element('#modal-noitems').openModal();
        angular.element('#modal-items').closeModal();
      }else
      {
        angular.element('#modal-items').openModal();
      }
    }
      
      
    }else{
      angular.element('#modal-noitems').openModal();
    }
  },function(){
    console.log("Error in get item list from collection listing");
  });

  }
}

/*added collection delete associate with item page listing*/
$scope.DeleteCollectionAssoicatewithietms= function(CId, index) {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
//collection/deleteItemFromCollection.csv
  var postData = new Object();
  postData.reqPaparam = {
    "collectionInfo" :{
      "userPublicKey" :  savedData.memberShipKey,
      "itemId" : $scope.itemId,
      "collectionId" : CId
    }
  };

  var url = valouxService.getWebServiceUrl("deleteItemFromCollection");
  //post data and get response
  var submitItem = valouxService.getData(url,postData);
  submitItem.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) { 

    $scope.itemPopupCollection.splice(index, 1)[0]
    
    $scope.itemCollectionTotal = $scope.itemPopupCollection.length;
    $scope.collectionItemTotalList = $scope.itemPopupCollection.length - 1
   
    angular.element(document.getElementById('collection_item_'+$scope.itemId)).html($scope.itemCollectionTotal);
  
       
    if($scope.itemCollectionTotal == 0){
    angular.element('#modal-noitems').openModal();
    angular.element('#modal-items').closeModal();
    }
    }else{
      $window.location = "item.html";
    }
  },function(){
    console.log("Error in delete item");
  });
}
/*Add new item blank popup add new button */
$scope.addNewCollectionInExistingItem = function() {
  angular.element('#modal-noitems').closeModal();
  angular.element('#modal-items').closeModal();
 
  $scope.initCollectionlistingItem();
}


/*Add new item in collection*/
$scope.ItemAddcollection = function(itemid, consumerPublicKey) {
  
  $scope.itemId = itemid;  
  
  $scope.initCollectionlistingItem(consumerPublicKey);
}

/*init collection page item listing not added in collection*/
$scope.initCollectionlistingItem= function(consumerPublicKey) {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var postData = new Object();
  postData.reqPaparam =  {
    "itemId" : $scope.itemId
    
  };
  if(consumerPublicKey){
    $scope.consumerUserPublicKey = consumerPublicKey
    postData.reqPaparam.userPublicKey = consumerPublicKey;
  }else{
    postData.reqPaparam.userPublicKey = savedData.memberShipKey;
  }
  var url = valouxService.getWebServiceUrl("getUserCollectionsNotInItem");
  //post data and get response
  var submitItem = valouxService.getData(url,postData);
  submitItem.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) { 
      $scope.errorSelectedCollection='';
      if(typeof data.resData.collectionData == "undefined"){
        $scope.noItemsAvaliable = true;
        angular.element('#modal-add-to-collection').closeModal();
        angular.element('#modal-create-to-collection').openModal();
        
      }else{
        $scope.noItemsAvaliable = false;
       
       $timeout(function() { angular.element("select").material_select();}, 500);
        $scope.totalItem = data.resData.collectionData.length;
        $scope.collectionDatas = data.resData.collectionData;
        angular.element('#modal-add-to-collection').openModal();
      }
    }else{
      $scope.noItemsAvaliable = true;
    }
    $timeout(function(){ angular.element('.tooltipped').tooltip(); }, 500);
  },function(){
    console.log("Error in get item list");
  });
}

/*add items collection page listing*/
$scope.addCollectionlistingItem= function() {

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
 
  var collectionArray = [];

  if(typeof $scope.collectionData != 'undefined'){
  for(i = 0; i < $scope.collectionData.length; i++){
    collectionArray.push($scope.collectionData[i]);
   
  }
 
  
  var postData = new Object();
  postData.reqPaparam = {
    "itemId" : $scope.itemId,
  
    "collectionId" : collectionArray
   
  };
  if($scope.consumerUserPublicKey){
    postData.reqPaparam.userPublicKey = $scope.consumerUserPublicKey;
  }else{
    postData.reqPaparam.userPublicKey = savedData.publicKey;

  }
  var url = valouxService.getWebServiceUrl("addItemToCollection");
  //post data and get response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      angular.element('#modal-add-to-collection').closeModal();
      $window.location.reload();
    }
    $timeout(function(){ angular.element('.tooltipped').tooltip(); }, 500);
  },function(){
    console.log("Error in get item list");
  })
}else{
    $scope.errorSelectedCollection = 'Please select atleast one'; 
    return false;
  }
}

/*Store consumer dropdown*/
$scope.storeConsumerInfoDropdown = function(agentInfo) {
  if(agentInfo){
    sessionStorage.removeItem("agentInformation");
    sessionStorage.setItem("agentInformation", agentInfo);
    $window.location = 'item-agent.html#/'
    
  }else{
    sessionStorage.removeItem("agentInformation");
    $window.location = 'item-agent.html#/'
  }
  
  
}

/*appraisal listed selected pop up items */
$scope.itemListedAppraisalShared = function(itemid) {
 

  $scope.itemId = itemid;
  
  var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    var postData = new Object();
    postData.reqPaparam = { 
      "userPublicKey" : savedData.memberShipKey,
      "itemId" : itemid,
    }
 
 

    var url = valouxService.getWebServiceUrl("getAppraisalListAssociatedWithItem");
    //post data and get response
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.appraisalTotal = data.resData.appraisals.length;
      $scope.appraisalPopups = data.resData.appraisals;

      if(data.resData.appraisals.length == 0){
      angular.element('#modal-noAppraisallist').openModal();
      angular.element('#modal-appraisal-item').closeModal();
  
      }else{
        angular.element('#modal-appraisal-item').openModal();
      }
    }else{
      angular.element('#modal-noAppraisallist').openModal();
    }
    $timeout(function(){ angular.element('.tooltipped').tooltip(); }, 500);
  },function(){
    console.log("Error in get appraisal list from item listing");
  });

  
}
/*appraisal listed selected pop up items */
$scope.itemListedAppraisal = function(itemid, appraisalCount) {
 

  $scope.itemId = itemid;
  $scope.appraisalList = appraisalCount;
  var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    var postData = new Object();
    postData.reqPaparam = { 
      "userPublicKey" : savedData.memberShipKey,
      "itemId" : itemid,
    }
  if(appraisalCount == 0){
    var appraiselurl = valouxService.getWebServiceUrl("getAppraisalListNotAssociatedWithItem");
  //post data and get response
  var submitAppraisalurlItem = valouxService.getData(appraiselurl,postData);
  submitAppraisalurlItem.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
        if(typeof data.resData.appraisals == "undefined"){ 
          $scope.noItemsAvaliable = true;
          angular.element('#modal-create-to-appraisal').openModal();
          angular.element('#modal-appraisal').closeModal();
        }
          else{  angular.element('#modal-noAppraisallist').openModal();
        angular.element('#modal-appraisal').closeModal();
          }
      } 
  },function(){});
}
  else{

    var url = valouxService.getWebServiceUrl("getAppraisalListAssociatedWithItem");
    //post data and get response
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.appraisalTotal = data.resData.appraisals.length;
      $scope.appraisalPopups = data.resData.appraisals;

      if(data.resData.appraisals.length == 0){
      angular.element('#modal-noAppraisallist').openModal();
      angular.element('#modal-appraisal').closeModal();
  
      }else{
        angular.element('#modal-appraisal').openModal();
      }
    }else{
      angular.element('#modal-noAppraisallist').openModal();
    }
    $timeout(function(){ angular.element('.tooltipped').tooltip(); }, 500);
  },function(){
    console.log("Error in get appraisal list from collection listing");
  });

  }
}
$scope.addAppraisalInItem = function(itemid, consumerKey) {
  $scope.itemId = itemid;
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var postData = new Object();
  postData.reqPaparam = { 
    "itemId" : itemid,
  }
  if(consumerKey){
    $scope.consumerUserPublicKey = consumerKey
    postData.reqPaparam.userPublicKey = consumerKey;
  }else{
    postData.reqPaparam.userPublicKey = savedData.memberShipKey;

  }
  var url = valouxService.getWebServiceUrl("getAppraisalListNotAssociatedWithItem");
  //post data and get response
  var submitItem = valouxService.getData(url,postData);
  submitItem.then(function(data){
  if (data.sCode == RESPONSE_CODE.SUCCESS) {
    $scope.selected();
    if(typeof data.resData.appraisals == 'undefined'){
      angular.element('#modal-create-to-appraisal').openModal();
    }else{
      angular.element('#modal-add-to-appraisal').openModal();
      $scope.appraisalAddPopups = data.resData.appraisals;
      $scope.appraisalsInItem = data.resData.appraisalsInItem;
      $timeout(function(){ angular.element("select").material_select(); }, 500);
    }
  }else{
    angular.element('#modal-noAppraisal').openModal();
  }
},function(){
  console.log("Error in get appraisal list from collection listing");
  });
}
$scope.addAppraisalInItemDetail = function(itemid) {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var postData = new Object();
  postData.reqPaparam = { 
    "userPublicKey" : savedData.memberShipKey,
    "itemId" : itemid,
  }
  
  var url = valouxService.getWebServiceUrl("getAppraisalListNotAssociatedWithItem");
  //post data and get response
  var submitItem = valouxService.getData(url,postData);
  submitItem.then(function(data){
  if (data.sCode == RESPONSE_CODE.SUCCESS) {
    $scope.selected();
    if(typeof data.resData.appraisals == 'undefined'){
      angular.element('#modal-noAppraisal').openModal();
    }else{
      angular.element('#modal-add-appraisal-details').openModal();
      $scope.appraisalAddPopupsdetails = data.resData.appraisals;
      $scope.appraisalsInItem = data.resData.appraisalsInItem;
    }
  }else{
    angular.element('#modal-noAppraisal').openModal();
  }
},function(){
  console.log("Error in get appraisal list from collection listing");
  });
}

/*Add new appraisal icon plus or add new appraisal button */
$scope.addNewAppraisalExistingItem = function() {

  angular.element('#modal-noAppraisallist').closeModal();
  angular.element('#modal-appraisal').closeModal();
  $scope.addAppraisalInItem($scope.itemId);
}
/*Add new appraisal from collection page*/
$scope.addNewAppraisalInItem= function() {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  var appraisalsInItem = [];

  if(typeof $scope.appraisalSelect != 'undefined'){
  for(i = 0; i < $scope.appraisalSelect.length; i++){
    appraisalsInItem.push($scope.appraisalSelect[i]);
   
  }
  
  
  var postData = new Object();
  postData.reqPaparam = {  
    "appraisalId" :appraisalsInItem,
    "itemId" : $scope.itemId,
    
  };
  if($scope.consumerUserPublicKey){
    postData.reqPaparam.userPublicKey = $scope.consumerUserPublicKey;
  }else{
    postData.reqPaparam.userPublicKey = savedData.memberShipKey;

  }
  var url = valouxService.getWebServiceUrl("addItemsInAppraisal");
  //post data and get response
  var submitItem = valouxService.getData(url,postData);
  submitItem.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) { 
      angular.element('#modal-add-to-appraisal').closeModal();
      $timeout(function(){ angular.element('.dropdown-button').dropdown();}, 100);
      $window.location.reload();
      
    }else{
      $scope.errorSelected = 'Please select atleast one';
    }
  },function(){
    console.log("Error in add new appraisal in collection listing");
  });}else{
    $scope.errorSelected = 'Please select atleast one';
}
}
/*delete appraisal from collection page*/
$scope.deleteAppraisal= function(appraisalId, index) {
    $scope.appraisalId=appraisalId;
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var appraisalArray = [];
  
    appraisalArray.push($scope.appraisalId);
   
  
  var postData = new Object();
  postData.reqPaparam = {
    "publicKey" :  savedData.memberShipKey,
    "appraisalId" : appraisalArray,
    "itemId" : $scope.itemId
  };
  
  var url = valouxService.getWebServiceUrl("deleteItemFromAppraisals");
  //post data and get response
  var submitItem = valouxService.getData(url,postData);
  submitItem.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) { 
      $scope.appraisalPopups.splice(index, 1)[0]
      $scope.appraisalTotal = $scope.appraisalPopups.length;
      angular.element(document.getElementById('appraisal_item_'+$scope.itemId)).html($scope.appraisalTotal);
         
      if($scope.appraisalTotal == 0){
        angular.element('#modal-noAppraisallist').openModal();
        angular.element('#modal-appraisal').closeModal();
      }
    }else{
      $route.reload();
    }
  },function(){
    console.log("Error in delete appraisal form item");
  });
}

/* hide error message*/
$scope.selected= function() {
  $scope.errorSelected = '';  
}

/* hide error message collection*/
$scope.selectedCollection= function() {
  $scope.errorSelectedCollection = '';  
}

/* cancel Button*/
$scope.cancel= function() {
  $window.scrollTo(500,0);
  $scope.$parent.fullPageBlockLoading = true;
  $window.location = "item.html#/";
}

/* cancel Button*/
$scope.cancelAgent= function() {
  $window.scrollTo(500,0);
  $scope.$parent.fullPageBlockLoading = true;
  $window.location = "item-agent.html#/";
}

/*delete item from listing page*/
$scope.deleteItemByListing= function(itemId, consumerKey) {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  var postData = new Object();
 
  if(consumerKey){
    postData.reqPaparam = { "itemId" : itemId,"userPublicKey" :  consumerKey};
  }
  else{
    postData.reqPaparam = { "itemId" : itemId,"userPublicKey" :  savedData.memberShipKey};
  }
  var url = valouxService.getWebServiceUrl("deleteItemByItemId");
  //post data and get response
  var submitItem = valouxService.getData(url,postData);
  submitItem.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) { 
      angular.element("#success").show();
      $scope.successMessage = data.successMessage;
      $window.scrollTo(500, 0);
      if(consumerKey){
        $scope.initItemlistAgent();
      }else{
        $scope.itemlisting();
      }
    }else{
      $route.reload();
    }
  },function(){
    console.log("Error in delete item");
  });
}

/*delete item from listing page*/
$scope.deleteItemBydetail= function(itemId,consumerKey) {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  var postData = new Object();
  if(consumerKey){
    postData.reqPaparam = {
      "userPublicKey" :  consumerKey,
      "itemId" : itemId,    
    };
  }else{
    postData.reqPaparam = {"userPublicKey" :  savedData.memberShipKey,"itemId" : itemId}   
  }
  
  var url = valouxService.getWebServiceUrl("deleteItemByItemId");
  //post data and get response
  var submitItem = valouxService.getData(url,postData);
  submitItem.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) { 
      sessionStorage.setItem("successMessage", data.successMessage);
      if(consumerKey) {
        $window.location = "item-agent.html#/";
      }else{
        $window.location = "item.html#/";
      }

    }else{
      $window.location = "item.html#/";
    }
  },function(){
    console.log("Error in delete item");
  });
}


/*detail display links of insurance & finanace*/
$scope.requestInsurance= function() {
  var path = $location.absUrl();  
  return path; 
}
}]);

          