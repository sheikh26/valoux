baseController.controller('collectionManagement',['$scope', 'valouxService', '$timeout','$location','RESPONSE_CODE', 'masterDataService', '$route', '$routePaparams', '$window', function($scope, valouxService, $timeout,$location, RESPONSE_CODE, masterDataService,$route, $routePaparams, $window){

/*Define store Object*/

$scope.storeCollection = new Object();
$scope.storeCollection.cImages = [];
$scope.noItem = false;
$scope.stepButton = true;
$scope.getItemList = false;
$scope.collectionFormError = false;
$scope.itemBlank = true;
$scope.inValidimg=[];
$scope.isSubmit = true;
$scope.edit = false;
$scope.collectionId = "" ;
$scope.addedItems = [];
$scope.itemsList = [];
$scope.imgId = "" ;
$scope.addButton = false;
$scope.agentHeaderEnable = false;

$scope.$on("$locationChangeStart", function(event) {
   $('.tooltipped').tooltip('remove');
});

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
	   
	   $scope.initCollectionData();
     }else{
      $scope.collectionFormError = true;
      $scope.errorMessage = data.errorMessage;
    }
							
  },function(){
    console.log("Error in Unshare calling");
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


/* details page redirect*/
$scope.detailPage= function(cid) {
  var path = 'collection.html#/detail/'+cid;
  $window.location = path;  
}

/*agent details page redirect*/
$scope.detailAgentPage= function(cid) {
  var path = 'collection-agent.html#/detail/'+cid;
  $window.location = path;  
}
/*Init items listing create collection*/
$scope.initItemlisting= function() {
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var savedItemAgent = sessionStorage.getItem("agentInformation");
  var postData = new Object();
  if(savedItemAgent){
    postData.reqPaparam = { "userPublicKey" :  savedItemAgent};
  }else{
    postData.reqPaparam = { "userPublicKey" : savedData.memberShipKey};

  }
  var url = valouxService.getWebServiceUrl("itemList");
  //post data and get response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      
      if(typeof data.resData.itemData == "undefined"){
        $scope.noItem = true;
        $scope.stepButton = false;
        $scope.getItemList = false;
      }else{
        $scope.noItem = false;
        $scope.itemData = data.resData.itemData;
        $scope.stepButton = false;
        $scope.getItemList = true;
      }
    }else{
      $scope.noItem = false;
      $scope.getItemList = false;
    }
  },function(){
    console.log("Error in get item list");
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

/*create collection step1*/
$scope.createCollectionStep1 = function() {
 
if($scope.isSubmit == true){
  $scope.$parent.fullPageBlockLoading = true;
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);

  var savedItemAgent = sessionStorage.getItem("agentInformation");
  var postData = new Object();
  postData.reqPaparam = { "collectionInfo" : $scope.storeCollection};
  

  if(savedItemAgent){
    postData.reqPaparam.collectionInfo.userPublicKey = savedItemAgent;

  }else{
    if($scope.newConsumerSubmit){

      /*postData.reqPaparam = { "collectionInfo" : $scope.storeCollection};*/
      postData.reqPaparam.collectionInfo.cName = $scope.globl.cNameNew;
      postData.reqPaparam.collectionInfo.newConsumerData = {
      "firstName" : $scope.globl.fname,
      "lastName" : $scope.globl.lname,
      "emailId" : $scope.globl.emailId
    }
    postData.reqPaparam.collectionInfo.userPublicKey = "";
  }else{
    postData.reqPaparam.collectionInfo.userPublicKey = savedData.memberShipKey;

  }    
  }

  /*Post request type add*/
  postData.reqPaparam.collectionInfo.requestType = "add"

  var url = valouxService.getWebServiceUrl("addUpdateCollectionDetails");
  //post create collection and get response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      
      if($scope.newConsumerSubmit){
        sessionStorage.removeItem("agentInformation");
        sessionStorage.setItem("agentInformation",data.resData.consumerPublicKey)
        $scope.consumerUserPublicKey = data.resData.consumerPublicKey;
        $window.location = "collection-agent.html#/";
      }
      $scope.initItemlisting();
      $scope.inValidimg = false;
      $scope.close = false;
      var imgArray = [];
      angular.forEach(data.resData.cImages, function(img){
        imgArray.push(img.imageUrl);
        $scope.imgId = img.imageId;
        
        if(imgArray == img.imageUrl){
          $scope.imageDisplay = true;
          angular.element('#fileCollection').val('');
          $scope.cImages = img.imageUrl;
        }else{
          $scope.imageDisplay = false;
        }
      })
      $scope.collectionId = data.resData.cId;
      
    }else{
     
      $scope.collectionFormError = true;
      $scope.errorMessage = data.errorMessage;
    }
  },function(){
    console.log("Error in add collection");
  });
  }
}

/*add items in collection*/
$scope.itemAddcollections = function() {
  
  var len = $scope.itemData.length;
  while (len--) {
    var item = $scope.itemData[len];
    if (item.selected) {
       $scope.addedItems.push(item);
       $scope.itemData.splice(len, 1);
    };
  }
  $scope.total = $scope.addedItems.length;
  
  if($scope.addedItems != ""){
    $scope.itemBlank = false;
  }
}


/*Remove collection item*/

$scope.removeItemsCollection = function(index){
    
  $scope.itemData.push($scope.addedItems.splice(index, 1)[0]);
  
  $scope.total = $scope.addedItems.length;
  if($scope.addedItems == ''){
    $scope.itemBlank = true;
  }
}

/*create collection with item submit*/
$scope.createCollectionStep2 = function() {
  $scope.$parent.fullPageBlockLoading = true;
if($scope.isSubmit == true){
  var savedTempData = $scope.collectionId;
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedItemAgent = sessionStorage.getItem("agentInformation");
  
  var savedData = JSON.parse(savedItem);
  var collectionArray = [];
  angular.forEach($scope.addedItems, function(addedItems){
    collectionArray.push(addedItems.itemId);
  })
  var postData = new Object();
  postData.reqPaparam = {"collectionInfo" :  $scope.storeCollection};
  if(savedItemAgent){
    postData.reqPaparam.collectionInfo.userPublicKey = savedItemAgent;
  }
  else if($scope.consumerUserPublicKey){
    postData.reqPaparam.collectionInfo.userPublicKey = $scope.consumerUserPublicKey;
    
  }
  else{
    postData.reqPaparam.collectionInfo.userPublicKey = savedData.memberShipKey;
  }
  postData.reqPaparam.collectionInfo.items = collectionArray;
  postData.reqPaparam.collectionInfo.cId = savedTempData;
  postData.reqPaparam.collectionInfo.requestType = "update";
  
  var url = valouxService.getWebServiceUrl("addUpdateCollectionDetails");
  //post create collection and get response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      if(savedItemAgent || $scope.consumerUserPublicKey){
        $window.location = "collection-agent.html";
      }else{
        $window.location = "collection.html";
      }
    }else{
      $scope.collectionFormError = true;
      $scope.errorMessage = data.errorMessage;
    }
  },function(){
    console.log("Error in add collection");
  });
}}

/*On key press search reinitialize tabs js*/
$scope.keyPressSearch = function(){
  $timeout(function(){ angular.element('ul.tabs').tabs(); angular.element('.dropdown-button').dropdown();}, 100);   
}

/*collection listing*/
$scope.initCollectionData = function() {

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  /*Sorting element according to latest & alphabatical order*/
  $scope.sortorder = '-cId';
  var postData = new Object();
  postData.reqPaparam = { "userPublicKey" : savedData.memberShipKey};
  
  var url = valouxService.getWebServiceUrl("getUserCollectionsList");
  //post create collection and get response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.animated();
      if(typeof data.resData.collections == "undefined"){
        $scope.noItem = true;
        $scope.collectionList = false;
      }else{
        $scope.noItem = false;
        $scope.collectionList = true;
        $scope.collectionListed =  data.resData.collections;
        $timeout(function(){ $('ul.tabs').tabs(); angular.element("#sortBy").material_select(); $('.dropdown-button').dropdown(); angular.element('.tooltipped').tooltip(); }, 500);
        $scope.collectionListTotal = data.resData.collections.length;
        
      }
      $scope.successMessage = sessionStorage.getItem("successMessage");
      $timeout(function(){ angular.element("#success").hide('slow');}, 2000);
      sessionStorage.removeItem("successMessage");
     }else{
      $scope.collectionFormError = true;
      $scope.errorMessage = data.errorMessage;
    }
  },function(){
    console.log("Error in add collection");
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

/*collection listed selected pop up items */
$scope.collectionListedItem = function(cid, itemCount, items, userKeyInAgent, status) {
 
 if(status == 3){
  $scope.collectionStatus = true;
 }else{
  $scope.collectionStatus = false;

 }
  $scope.collectionId = cid;
  $scope.itemsList = items;
  $scope.consumerkey = userKeyInAgent;
  if(itemCount == 0){
    angular.element('#modal-noitem').openModal();
  }
  else{
    angular.element('#modal-items-collection').openModal();

    var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    
    var postData = new Object();
    postData.reqPaparam = { "cId" : cid }

    if(userKeyInAgent){
      postData.reqPaparam.userPublicKey = userKeyInAgent;
      $scope.userKeyInAgent = userKeyInAgent;
    }else{
      postData.reqPaparam.userPublicKey = savedData.memberShipKey;
    }
    
    var url = valouxService.getWebServiceUrl("getItemsOfCollection");
    //post data and get response
    var submitCollection = valouxService.getData(url,postData);
    submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.collectionItemTotal = data.resData.items.length;
      $scope.collectionPopupItems = data.resData.items;
       
      $timeout(function(){ $('.mCustomScrollbar').mCustomScrollbar(); angular.element('.tooltipped').tooltip(); }, 500);

      if(data.resData.items.length == 0){
        angular.element('#modal-noitem').openModal();
        angular.element('#modal-items-collection').closeModal();
      }
    }else{
      angular.element('#modal-noitem').openModal();
    }
  },function(){
    console.log("Error in get item list from collection listing");
  });

  }
}

/*collection edit page for agent*/
$scope.editCollectionAgent = function(agentInfo,collectionlId) {
  if(agentInfo){
    sessionStorage.setItem("agentInformation", agentInfo);
    $scope.consumerPublicKey = agentInfo; 
    $timeout(function(){ angular.element('ul.tabs').tabs();}, 100);
    $window.location = "collection-agent.html#/edit/"+collectionlId;
  }
}

/*collection detail page for agent*/
$scope.detailCollectionAgent = function(agentInfo,collectionlId) {
  if(agentInfo){
    sessionStorage.setItem("agentInformation", agentInfo);
    $scope.consumerPublicKey = agentInfo; 
    $timeout(function(){ angular.element('ul.tabs').tabs(); angular.element('.dropdown-button').dropdown();}, 100);
    $window.location = "collection-agent.html#/detail/"+collectionlId;

  }
}

/*appraisal listed selected pop up items */
$scope.appraisalListedItem = function(cid, appraisalCount) {
 
  $scope.collectionId = cid;
  
  if(appraisalCount == 0){
    angular.element('#modal-noAppraisallist').openModal();
  }
  else{
    angular.element('#modal-appraisal-col').openModal();

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
        angular.element('#modal-appraisal-col').closeModal();
      }
    }else{
      angular.element('#modal-noAppraisallist').openModal();
    }
  },function(){
    console.log("Error in get appraisal list from collection listing");
  });

  }
}

$scope.addAppraisalInCollection = function(cid, addAppraisalButton, consumerkey) {
  $scope.consumerUserPublicKey = consumerkey;
  $scope.collectionId = cid;
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var agentInfo = sessionStorage.getItem("agentInformation");

  var postData = new Object();
  postData.reqPaparam = { 
    "collectionId" : cid,
  }

  if(agentInfo){
    postData.reqPaparam.userPublicKey = agentInfo;
  }else{
    if($scope.consumerUserPublicKey){
      postData.reqPaparam.userPublicKey = $scope.consumerUserPublicKey;
    }else{
      postData.reqPaparam.userPublicKey = savedData.memberShipKey;
    }
  }
  
  var url = valouxService.getWebServiceUrl("getUserAppraisalsNotInCollection");
  //post data and get response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
  if (data.sCode == RESPONSE_CODE.SUCCESS) {
    
    if(typeof data.resData.appraisalData == 'undefined'){
      if(addAppraisalButton){
        $window.location = "appraisal.html#/add"
      }else{
        angular.element('#modal-noAppraisal').openModal();
      }
     
    }else{
      angular.element('#modal-add-to-appraisal-col').openModal();
      $scope.appraisalAddPopups = data.resData.appraisalData;
      $scope.appraisalsInCollection = data.resData.appraisalsInCollection;
      $timeout(function(){ angular.element("select").material_select(); }, 500);
      $scope.errorSelected = ''
    }
  }else{
    angular.element('#modal-noAppraisal').openModal();
  }
},function(){
  console.log("Error in get appraisal list from collection listing");
  });
}

$scope.addAppraisalInCollectionDetail = function(cid) {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var agentInfo = sessionStorage.getItem("agentInformation");

  var savedData = JSON.parse(savedItem);
  var postData = new Object();
  postData.reqPaparam = { 
    "collectionId" : cid,
  }
  if(agentInfo){
    postData.reqPaparam.userPublicKey = agentInfo;
  }else{
    if($scope.consumerUserPublicKey){
    postData.reqPaparam.userPublicKey = $scope.consumerUserPublicKey;
  }else{
    postData.reqPaparam.userPublicKey = savedData.memberShipKey;

  }
  }
  var url = valouxService.getWebServiceUrl("getUserAppraisalsNotInCollection");
  //post data and get response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
  if (data.sCode == RESPONSE_CODE.SUCCESS) {
    
    if(typeof data.resData.appraisalData == 'undefined'){
      angular.element('#modal-noAppraisal').openModal();
    }else{
      angular.element('#modal-add-appraisal-details').openModal();
      $scope.appraisalAddPopupsdetails = data.resData.appraisalData;
      $scope.appraisalsInCollection = data.resData.appraisalsInCollection;
    }
  }else{
    angular.element('#modal-noAppraisal').openModal();
  }
},function(){
  console.log("Error in get appraisal list from collection listing");
  });
}

/*Add new appraisal icon plus or add new appraisal button */
$scope.addNewAppraisalExistingCollection = function() {
  angular.element('#modal-noAppraisallist').closeModal();
  angular.element('#modal-appraisal-col').closeModal();
  $scope.addButton = true;
  $scope.addAppraisalInCollection($scope.collectionId, $scope.addButton);
}

/*remove tooltip click on list & grid view */
$scope.removeTooltip = function() {
  angular.element('.tooltipped').tooltip('remove');
}

/*Add new item icon plus or add new item button */
$scope.addNewItemExistingCollection = function() {
  angular.element('#modal-items-collection').closeModal();
  angular.element('#modal-add-new-items-collection').openModal();
  $scope.initItemlistingCollection();
}

/*Clear search data */
$scope.clearSearchData = function(event) {
  angular.element('#search2').val('');
  $timeout(function(){ angular.element('ul.tabs').tabs(); angular.element('.dropdown-button').dropdown();}, 100);
  event.searchKeyword = "";
}

/*view all data  in agent */
$scope.viewAllData = function() {
  $route.reload()
  sessionStorage.removeItem("agentInformation");
}

/*invite new consumer in agent */
$scope.inviteNewConsumerAgent = function() {
  $scope.inviteNewConsumer = true;
  $scope.newConsumerSubmit = true;
  sessionStorage.removeItem("agentInformation");
  $route.reload();
  $window.location = "collection-agent.html#/add"
}

/*load listing gallary set agent info in session*/
$scope.storeConsumerInfo = function(agentInfo, event) {
  if(agentInfo){
    sessionStorage.setItem("agentInformation", agentInfo);
    $scope.initConsumerInformation();
  }else{
    sessionStorage.removeItem("agentInformation");
    event.viewItemBy = '';
  }
  $timeout(function(){ angular.element('ul.tabs').tabs(); angular.element('.dropdown-button').dropdown();}, 100);
  
}

$scope.storeConsumerInfoDropdown = function(agentInfo) {
  $scope.$parent.fullPageBlockLoading = true;

  if(agentInfo){
    sessionStorage.removeItem("agentInformation");
    sessionStorage.setItem("agentInformation", agentInfo);
    $window.location = 'collection-agent.html#/'
    
  }else{
    sessionStorage.removeItem("agentInformation");
    $window.location = 'collection-agent.html#/'
  }
  
}
/*Add new item blank popup add new button */
$scope.addNewItemInExistingCollection = function() {
  $scope.errorSelected = '';
  $scope.addButton = true;
  $scope.consumerkey = '';
  $scope.initItemlistingCollection($scope.addButton, $scope.consumerkey);
}

$scope.addNewItemInExistingCollectionAgent = function() {
  $scope.errorSelected = '';
  $scope.addButton = true;
  $scope.initItemlistingCollectionAgent($scope.addButton, $scope.consumerkey);
}

/*init collection page item listing not added in collection*/
$scope.initItemlistingCollectionAgent= function(addCollectionButton, consumerkey) {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var postData = new Object();
  postData.reqPaparam = { "collectionInfo" : {
      "collectionId" : $scope.collectionId
    }
  };
 if(consumerkey){
    $scope.userKeyInAgent = consumerkey;
    postData.reqPaparam.collectionInfo.userPublicKey = consumerkey;
  }else{
    postData.reqPaparam.collectionInfo.userPublicKey = savedData.memberShipKey;
  }
  var url = valouxService.getWebServiceUrl("getUserItemsNotInCollection");
  //post data and get response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {

      if(typeof data.resData.itemData == "undefined"){
       if(addCollectionButton){
          $window.location = "item-agent.html#/add"
          $scope.storeConsumerInfo(consumerkey);
        }else{
          angular.element('#modal-noitem').openModal();
          
        }
        
      }else{
        /*$scope.noItemsAvaliable = false;*/
        angular.element('#modal-noitem').closeModal();
        angular.element('#modal-add-new-items-collection').openModal();
        $scope.item = data.resData.itemsInCollection;
        $scope.totalItem = data.resData.itemData.length;
        $scope.itemData = data.resData.itemData;
      }
    }else{
      angular.element('#modal-noitem').openModal();
      
    }
  },function(){
    console.log("Error in get item list");
  });
}
/*Add new item in collection*/
$scope.collectionAddItem = function(cid, consumerkey) {
  $scope.errorSelected = ''
  $scope.consumerkey = consumerkey;
  $scope.collectionId = cid;  
  $scope.addButton = false;
  $scope.initItemlistingCollection($scope.addButton, consumerkey);
}


/*Add new appraisal in collection*/
$scope.collectionAddAppraisal = function(cid) {
  $scope.collectionId = cid;  
  
}


/*init collection page item listing not added in collection*/
$scope.initItemlistingCollection= function(addCollectionButton, consumerkey) {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var postData = new Object();
  postData.reqPaparam = { "collectionInfo" : {
      "collectionId" : $scope.collectionId
    }
  };
 if(consumerkey){
    $scope.userKeyInAgent = consumerkey;
    postData.reqPaparam.collectionInfo.userPublicKey = consumerkey;
  }else{
    if($scope.consumerUserPublicKey){
      postData.reqPaparam.collectionInfo.userPublicKey = $scope.consumerUserPublicKey;
    }else{
    postData.reqPaparam.collectionInfo.userPublicKey = savedData.memberShipKey;
  }}
  var url = valouxService.getWebServiceUrl("getUserItemsNotInCollection");
  //post data and get response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {

      if(typeof data.resData.itemData == "undefined"){
       if(addCollectionButton){
          $window.location = "item.html#/add"
        }else{
          angular.element('#modal-noitem').openModal();
          
        }
        
      }else{
        angular.element('#modal-add-new-items-collection').openModal();
        angular.element('#modal-noitem').closeModal();
        $timeout(function(){ $('.mCustomScrollbar').mCustomScrollbar(); }, 500);
        $scope.item = data.resData.itemsInCollection;
        $scope.totalItem = data.resData.itemData.length;
        $scope.itemData = data.resData.itemData;
      }
    }else{
      angular.element('#modal-noitem').openModal();
      
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

/*add items collection page listing*/
$scope.addItemlistingCollection= function() {

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);

  var collectionArray = [];
  for(i = 0; i < $scope.item.length; i++){
    collectionArray.push($scope.item[i]);
  }
  angular.forEach($scope.itemData, function(item){
    if(item.selected){
      collectionArray.push(item.itemId);
      $scope.checked = true;
    }
  })
  if($scope.checked){
  var postData = new Object();
  postData.reqPaparam = {"collectionInfo" :  {
    "cId" : $scope.collectionId,
    "requestType" : "update",
    "items" : collectionArray
    }
  };
  if($scope.userKeyInAgent){
    postData.reqPaparam.collectionInfo.userPublicKey = $scope.userKeyInAgent;
  }
  else{
    postData.reqPaparam.collectionInfo.userPublicKey = savedData.publicKey;
  }
  var url = valouxService.getWebServiceUrl("addUpdateCollectionDetails");
  //post data and get response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      angular.element('#modal-add-new-items-collection').closeModal();
      $window.location.reload();
      
    }else{
        $window.location = "collection.html#";
      }
  },function(){
    console.log("Error in get item list");
  });
}else{
  $scope.errorSelected = 'Please select atleast one'
}
}

/*added items delete collection page listing*/
$scope.itemDeleteCollection= function(itemId, index) {
  $scope.$parent.fullPageBlockLoading = false;
  $scope.loaderPopup = true;

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var postData = new Object();
  postData.reqPaparam = {
    "collectionInfo" :{
    "itemId" : itemId,
    "collectionId" : $scope.collectionId
    }
  };
  if($scope.userKeyInAgent){
    postData.reqPaparam.collectionInfo.userPublicKey = $scope.userKeyInAgent;
  }else{
    postData.reqPaparam.collectionInfo.userPublicKey = savedData.memberShipKey;
  }
  var url = valouxService.getWebServiceUrl("deleteItemFromCollection");
  //post data and get response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
    $scope.$parent.fullPageBlockLoading = true;
    $scope.loaderPopup = false; 
    $scope.deleteItemCollection = true;
    angular.element('.tooltipped').tooltip('remove');
    $timeout(function(){ $scope.deleteItemCollection = false;  angular.element('#showMessage').fadeOut('slow');  }, 1000);
    $scope.collectionPopupItems.splice(index, 1)[0]
     angular.element('.tooltipped').tooltip();
    $scope.collectionItemTotal = $scope.collectionPopupItems.length;
    $scope.collectionItemTotalList = $scope.collectionPopupItems.length - 1
    
    angular.element(document.getElementById('collection_item_Col_'+$scope.collectionId)).html($scope.collectionItemTotal);
    angular.element(document.getElementById('itemList_'+$scope.collectionId)).html("+"+$scope.collectionItemTotalList);
    
    angular.forEach($scope.collectionListed, function(itemsLi, index){
      
      if($scope.collectionListed[index].cId == $scope.collectionId){
        $scope.collectionListed[index].items = $scope.collectionPopupItems;
      }
      if($scope.collectionListed[index].id == $scope.collectionId){
        $scope.collectionListed[index].itemFlag = true;
        
      }
      $timeout(function(){ angular.element('ul.tabs').tabs(); }, 100);
    })

    angular.forEach($scope.searchCollectionDetail, function(itemsLi, index){
      if($scope.searchCollectionDetail[index].id == $scope.collectionId){
        $scope.searchCollectionDetail[index].items = $scope.collectionPopupItems;
      }
    })
    
    if($scope.collectionItemTotal == 0){
      $scope.$parent.fullPageBlockLoading = true;
      angular.element('#modal-noitem').openModal();
      angular.element('#modal-items-collection').closeModal();

    }
    }else{
      $window.location = "collection.html";
    }
  },function(){
    console.log("Error in delete item");
  });
}

/*edit collection page*/
$scope.updateCollections= function() {
  $scope.globl = new Object();
  var savedItemAgent = sessionStorage.getItem("agentInformation");
  if(savedItemAgent){
    $scope.agentHeaderEnable = true;
    $scope.inviteNewConsumer = false;
  }else{
    if($scope.isAgentLogin){
      $scope.inviteNewConsumer = true;
      $scope.newConsumerSubmit = true;
    }else{
      $scope.consumerLogin = true;
    }
  }
  var collectionId = $route.current.paparams.collectionId;

  if(collectionId){

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);

  var postData = new Object();
  postData.reqPaparam = { "collectionInfo" : $scope.storeCollection};
  
  postData.reqPaparam.collectionInfo.cId = collectionId
  postData.reqPaparam.collectionInfo.requestType = "update"
  if(savedItemAgent){
    postData.reqPaparam.collectionInfo.userPublicKey = savedItemAgent;
  }
  else{
    if($scope.inviteNewConsumer){
      postData.reqPaparam.collectionInfo.userPublicKey = '';
    }else{
    postData.reqPaparam.collectionInfo.userPublicKey = savedData.memberShipKey;
  }
  }
  var url = valouxService.getWebServiceUrl("addUpdateCollectionDetails");
  //collection response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.class = "active";
      $window.scrollTo(500, 0);
      $scope.$parent.fullPageBlockLoading = false;
      $scope.storeCollection ={
        "cDescription" : data.resData.cDescription,
        "cName" : data.resData.cName,    
        
      }
      if($scope.inviteNewConsumer){
        $scope.consumerUserPublicKey = data.resData.consumerPublicKey;

      }
      
      if(savedItemAgent){
        $scope.viewItemBy = savedItemAgent;
       
      }
      $scope.agentHeaderEnable = true;
      $scope.inviteNewConsumer = false;
      
      $scope.imageDisplay = false
      $scope.storeCollection.cImages = [];
      
      var imgArray = [];
      angular.forEach(data.resData.cImages, function(img){
        imgArray.push(img.imageUrl);
        $scope.imgId = img.imageId;
        $scope.imageDisplay = true
        $scope.cImages = img.imageUrl;
      })

      $scope.itemBlank = true;

      angular.forEach(data.resData.items, function(item){
        $scope.addedItems = data.resData.items;
        $scope.total = data.resData.items.length;
        $scope.itemBlank = false;
      })
      
      $scope.noItem = false;
      $scope.stepButton = false;
      $scope.getItemList = true;
      $scope.edit = true;
      
      $scope.initItemlistingEditCollection(data.resData.cId, data.resData.items, savedItemAgent);
      
    }else{
      $scope.collectionFormError = true;
      $scope.errorMessage = data.errorMessage;
    }
  },function(){
    console.log("Error in edit collection");
  });
}else{
  $scope.$parent.fullPageBlockLoading = false;
}
}

/*init collection page item listing*/
$scope.initItemlistingEditCollection= function(cid, itemsBlank, savedItemAgent) {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var postData = new Object();
  postData.reqPaparam = { "collectionInfo" : $scope.storeCollection};
  if(savedItemAgent){
    postData.reqPaparam.collectionInfo.userPublicKey = savedItemAgent;

  }else{
    if($scope.consumerUserPublicKey){
      postData.reqPaparam.collectionInfo.userPublicKey = $scope.consumerUserPublicKey;

    }else{
      postData.reqPaparam.collectionInfo.userPublicKey = savedData.memberShipKey;

    }
  }
  postData.reqPaparam.collectionInfo.collectionId = cid;
  $scope.collectionId = cid
  
  var url = valouxService.getWebServiceUrl("getUserItemsNotInCollection");
  //post data and get response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      if(itemsBlank.length == 0 && typeof data.resData.itemData == "undefined")
      {
        $scope.noItem = true;
        $scope.getItemList = false;
      }
      else{
        $scope.noItem = false;
        $scope.getItemList = true;
      }
      if(typeof data.resData.itemData == "undefined"){
        $scope.itemData = []
      }else{
        $scope.itemData = data.resData.itemData;
      }
    }else{
      $window.location = "collection.html";
      
    }
  },function(){
    console.log("Error in get item list");
  });
}
// upload collection Images
$scope.uploadCollectionImage = function(e){
    
    var imageObj = e.target;
    var imageContent = "";
    var imageName = "";
    $scope.close = false;
    $scope.inValidImage = false;
    $scope.inValidImageText = "";
    imageName = imageObj.files[0].name;
    if ( imageObj.files && imageObj.files[0] ) {
        var allowed = ["jpeg", "png", "gif"];
        var fileType = imageObj.files[0].type;
        var fileSize = imageObj.files[0].size;
        fileSize = (fileSize/1024/1024);
        var returnValue = true;
        allowed.forEach(function(extension) {
            if (imageObj.files[0].type.match('image/'+extension)) {
              returnValue = false;
              $scope.isSubmit = true;
              $scope.close = true;
              $scope.inValidimg = false;
            }
          })
        
        if(returnValue)
        { 
          $scope.close = true;
          $scope.isSubmit = false
          $scope.inValidimg = true;
          $scope.inValidimgText = "Please select [jpg, png, gif] image.";
          $scope.$apply();
          return;
        }
        else if(fileSize > 2)
        {   
            $scope.close = true;
            $scope.isSubmit = false
            $scope.inValidimg = true;
            $scope.inValidimgText = "Please select image less or equal to 2 MB.";
            $scope.$apply();
            return;
        }
        

    }
    
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
          imageContent = newImageObj; 
          
          $scope.storeCollection.cImages.push({'imageContent' : imageContent,'imageName':imageName});
          
        };       
        FR.readAsDataURL( imageObj.files[0] );                
    }
}


/*init collection page item listing*/
$scope.removeCollectionImage= function() {
  
  var savedTempItem = $scope.collectionId;
  var savedTempData = JSON.parse(savedTempItem);
  var savedTempData = $scope.collectionId;

  var savedTempImgId = $scope.imgId
  var postData = new Object();
  postData.reqPaparam = { 
    "collectionInfo" : {
      "deleteImage" : {
        "cId" : savedTempData,
        "imageId" : savedTempImgId
      }
    }
  };
 
  var url = valouxService.getWebServiceUrl("deleteImageByCollectionAndImageId");
  //post data and get response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.imageDisplay = false;
    }else{
      $scope.imageDisplay = true;
    }
  },function(){
    console.log("Error in get item list");
  });
}
/*Clear collection image after upload on collection*/
$scope.clearImage = function() {
    delete $scope.storeCollection.cImages;
    $scope.storeCollection.cImages = [];
    $scope.close = false;
    $scope.isSubmit = true;
    $scope.inValidimg = false;
    angular.element(document.getElementById('fileCollection')).val('');
  }

/* focus Update collection form */
$scope.focus = function() {
  $window.scrollTo(500, 0);
}


/* Update collection form */
$scope.editCollectionSubmit = function() {

  $scope.$parent.fullPageBlockLoading = true;
  var agentInfo = sessionStorage.getItem("agentInformation");
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  if($scope.isSubmit == true){
  var collectionArray = [];
  angular.forEach($scope.addedItems, function(addedItems){
    collectionArray.push(addedItems.itemId);
  })
  var postData = new Object();
  postData.reqPaparam = {"collectionInfo" :  $scope.storeCollection};
  postData.reqPaparam.collectionInfo.items = collectionArray
  if(agentInfo){
    postData.reqPaparam.collectionInfo.userPublicKey = agentInfo;
  }else if($scope.consumerUserPublicKey){
    postData.reqPaparam.collectionInfo.userPublicKey = $scope.consumerUserPublicKey;
  }
  else{
    postData.reqPaparam.collectionInfo.userPublicKey = savedData.memberShipKey;
  }
  postData.reqPaparam.collectionInfo.cId = $scope.collectionId
  postData.reqPaparam.collectionInfo.requestType = "update"
  
  var url = valouxService.getWebServiceUrl("addUpdateCollectionDetails");
  //post update collection and get response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      if(agentInfo || $scope.consumerUserPublicKey){
        $window.location = "collection-agent.html";
      }else{
        $window.location = "collection.html";
      }

    }else{
      $scope.collectionFormError = true;
      $scope.errorMessage = data.errorMessage;
    }
  },function(){
    console.log("Error in update collection");
  });
  }   
}


/*collection details page*/
$scope.collectionsDetail= function() {
  	
var collectionId = $route.current.paparams.collectionId;

 if(collectionId){
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var savedItemAgent = sessionStorage.getItem("agentInformation");
  if(savedItemAgent){
    $scope.agentHeaderEnable = true;
    $scope.initConsumerInformation();
  }
  $scope.userKey = savedData.memberShipKey;
  var postData = new Object();
  postData.reqPaparam = { "collectionInfo" : $scope.storeCollection};
  if(savedItemAgent){
    $scope.userKeyInAgent = savedItemAgent;
    postData.reqPaparam.collectionInfo.userPublicKey = savedItemAgent;
  }else{
    postData.reqPaparam.collectionInfo.userPublicKey = '';
  }
  postData.reqPaparam.collectionInfo.cId = collectionId;
  postData.reqPaparam.collectionInfo.requestType = "view"

  var url = valouxService.getWebServiceUrl("addUpdateCollectionDetails");
  //collection post & response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      
      if($scope.isAgentLogin){
        $scope.consumerUserPublicKey = data.resData.consumerPublicKey;
      }   
      $window.scrollTo(500, 0);
      $scope.storeCollection = data.resData;
      if(savedItemAgent){
        $scope.storeCollection.itemFlag = data.resData.itemFlag;
        $scope.storeCollection.appraisalFlag = data.resData.appraisalFlag;
      }
      $scope.itemDetails = data.resData.items
      $scope.appraisalInCollectionDetail(collectionId, savedItemAgent);
      $scope.getItemList = true;
      if(data.resData.items.length == 0){
        $scope.noItem = true;
        $scope.getItemList = false;
      }
    }else{
      $window.location = "collection.html#/"
    }
  },function(){
    console.log("Error in edit collection");
  });
}else{
    $window.location = "collection.html#/"
}  
}

/*Appraisal in collection details page*/
$scope.appraisalInCollectionDetail= function(collectionId, savedItemAgent) {
  $scope.collectionId = collectionId;
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);

  var postData = new Object();
  postData.reqPaparam = {
    "cId" : collectionId
  };
  if($scope.userKeyInAgent){
    postData.reqPaparam.userPublicKey = $scope.userKeyInAgent;
  }else{
    if($scope.consumerUserPublicKey){
      postData.reqPaparam.userPublicKey = $scope.consumerUserPublicKey
    }else{
    postData.reqPaparam.userPublicKey = savedData.memberShipKey;
  }}
  
  var url = valouxService.getWebServiceUrl("getAppraisalsOfCollection");
  //post data and get response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) { 
      $scope.appraisalListInCollectiondetails = data.resData.appraisalList;

    }else{
      $window.location = "collection.html";
    }
  },function(){
    console.log("Error in delete item form collection detail");
  });
}

/*Remove items from collection details page*/
$scope.removeItemCollectionsDetail= function(cid, itemId, index) {
  
  angular.element('.tooltipped').tooltip('remove');

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var agentInfo = sessionStorage.getItem("agentInformation");

  var postData = new Object();
  postData.reqPaparam = {
    "collectionInfo" :{
    "itemId" : itemId,
    "collectionId" : cid
    }
  };
  if(agentInfo){
    postData.reqPaparam.collectionInfo.userPublicKey = agentInfo;
  }else{
    if($scope.consumerUserPublicKey){
      postData.reqPaparam.collectionInfo.userPublicKey = $scope.consumerUserPublicKey;
    }else{
      postData.reqPaparam.collectionInfo.userPublicKey = savedData.memberShipKey;
    }
  }
  var url = valouxService.getWebServiceUrl("deleteItemFromCollection");
  //post data and get response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) { 
      $scope.itemDetails.splice(index, 1)[0];
      $scope.storeCollection.itemFlag = true;
      angular.element('.tooltipped').tooltip({delay: 50});
      if($scope.itemDetails.length == 0){
        $scope.noItem = true;
      }
    }else{
      $window.location = "collection.html";
    }
  },function(){
    console.log("Error in delete item form collection detail");
  });
}

/*delete appraisal from collection page*/
$scope.deleteAppraisal= function(appraisalId, index) {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);

  var postData = new Object();
  postData.reqPaparam = {
    "publicKey" :  savedData.memberShipKey,
    "appraisalId" : appraisalId,
    "collectionId" : $scope.collectionId
  };
  
  var url = valouxService.getWebServiceUrl("deleteAppraisalFromCollection");
  //post data and get response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) { 
      $scope.appraisalPopups.splice(index, 1)[0]
      $scope.appraisalTotal = $scope.appraisalPopups.length;
      angular.element(document.getElementById('collection_appraisal_col_'+$scope.collectionId)).html($scope.appraisalTotal);
         
      if($scope.appraisalTotal == 0){
        angular.element('#modal-noAppraisallist').openModal();
        angular.element('#modal-appraisal-col').closeModal();
      }
    }else{
      $window.location = "collection.html";
    }
  },function(){
    console.log("Error in delete appraisal form collection");
  });
}

/*delete appraisal from collection page*/
$scope.deleteAppraisalInCollectionDetails= function(appraisalId, index) {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var agentInfo = sessionStorage.getItem("agentInformation");

  var postData = new Object();
  postData.reqPaparam = {
    "appraisalId" : appraisalId,
    "collectionId" : $scope.collectionId
  };
  if(agentInfo){
    postData.reqPaparam.userPublicKey = agentInfo;
  }else{
    if($scope.consumerUserPublicKey){
    postData.reqPaparam.userPublicKey = $scope.consumerUserPublicKey;
  }else{
    postData.reqPaparam.userPublicKey = savedData.memberShipKey;
  }

  }
  var url = valouxService.getWebServiceUrl("deleteAppraisalFromCollection");
  //post data and get response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) { 
      $scope.appraisalListInCollectiondetails.splice(index, 1)[0];
      $scope.storeCollection.appraisalFlag = true;
    }else{
      $window.location = "collection.html";
    }
  },function(){
    console.log("Error in delete appraisal form collection details");
  });
}

/*Add new appraisal from collection page*/
$scope.addNewAppraisalInCollection= function() {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var agentInfo = sessionStorage.getItem("agentInformation")
  var appraisal =  parseInt($scope.appraisalSelect)
  if(appraisal){
    $scope.appraisalsInCollection.push(appraisal);
    $scope.checked = true; 
  }
  
  if($scope.checked){
  var postData = new Object();
  postData.reqPaparam = { "collectionInfo" :{
    "appraisals" : $scope.appraisalsInCollection,
    "cId" : $scope.collectionId,
    "requestType" : "update"
  }};

  if(agentInfo){
    postData.reqPaparam.collectionInfo.userPublicKey = agentInfo;

  }else{
    if($scope.consumerUserPublicKey){
      postData.reqPaparam.collectionInfo.userPublicKey = $scope.consumerUserPublicKey;

    }else{
      postData.reqPaparam.collectionInfo.userPublicKey = savedData.memberShipKey;

    }
  }
  
  var url = valouxService.getWebServiceUrl("addUpdateCollectionDetails");
  //post data and get response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) { 
      angular.element('#modal-add-to-appraisal-col').closeModal();
      $window.location.reload();
      
    }else{
      $window.location = "collection.html";
    }
  },function(){
    console.log("Error in add new appraisal in collection listing");
  });
}else{
  $scope.errorSelected = 'Please select atleast one'
}
}

/* hide error message*/
$scope.selected= function() {
  $scope.errorSelected = '';  
}

/*Add new appraisal from collection page*/
$scope.addNewAppraisalInCollectionDetails= function() {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var agentInfo = sessionStorage.getItem("agentInformation");
  
  
  angular.forEach($scope.appraisalAddPopupsdetails, function(appraisal){
    if(appraisal.selected){
      $scope.appraisalsInCollection.push(appraisal.appraisalId);
      $scope.checked = true; 
    }
  })
    
  if($scope.checked){
  var postData = new Object();
  postData.reqPaparam = { "collectionInfo" :{
    "appraisals" : $scope.appraisalsInCollection,
    "cId" : $scope.collectionId,
    "requestType" : "update"
  }};
  if(agentInfo){
    postData.reqPaparam.collectionInfo.userPublicKey = agentInfo;
  }else if($scope.consumerUserPublicKey){
    postData.reqPaparam.collectionInfo.userPublicKey = $scope.consumerUserPublicKey;

  }else{
    postData.reqPaparam.collectionInfo.userPublicKey = savedData.memberShipKey;

  }
  var url = valouxService.getWebServiceUrl("addUpdateCollectionDetails");
  //post data and get response
  var submitCollection = valouxService.getData(url,postData);
  submitCollection.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) { 
      angular.element('#modal-add-appraisal-details').closeModal();
      getCurrentPath = $route.current.originalPath;
      if(getCurrentPath == "/grid"){
        $window.location = "collection.html#/grid";
        $timeout(function(){ angular.element('.dropdown-button').dropdown();}, 100);
        $route.reload();
      }else{
        $route.reload();
      }
      
    }else{
      $window.location = "collection.html";
    }
  },function(){
    console.log("Error in add new appraisal in collection details");
  });
}else{
  $scope.errorSelected = 'Please select atleast one'
}
}

/* cancel Button*/
$scope.cancel= function() {
  $window.scrollTo(500,0);
  $scope.$parent.fullPageBlockLoading = true;
  $window.location = "collection.html#/";
}

/* cancel Button*/
$scope.cancelAgent= function() {
  $window.scrollTo(500,0);
  $scope.$parent.fullPageBlockLoading = true;
  $window.location = "collection-agent.html#/";
}

/*collection listing for agent*/
$scope.initAgentCollectionData = function() {

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var agentInfo = sessionStorage.getItem("agentInformation");
  /*Sorting element according to latest & alphabatical order*/
  $scope.sortorder = '-id';
  var postData = new Object();
  postData.reqPaparam = { "sharedTo" : savedData.memberShipKey,
  "sharedItemType" : "2",
  "approvedStatus" : "2"
  };
  var url = valouxService.getWebServiceUrl("getUserSharedWithMeList");
  //post create collection and get response
  var submitCollectionAgent = valouxService.getData(url,postData);
  submitCollectionAgent.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $window.scrollTo(500, 0);
      $scope.initConsumerInformation();
      $scope.animated();
      if(data.sharedItemList.length == 0){
        $scope.noItem = true;
        $scope.collectionList = false;
        $scope.collectionListed = false;
      }else{
        $scope.noItem = false;
        $scope.collectionList = true;
        $scope.collectionListed =  data.sharedItemList;
        $timeout(function(){ $('.dropdown-button').dropdown();$('ul.tabs').tabs(); angular.element("#viewedItemsBy").material_select();angular.element("#sortBy").material_select(); }, 500);
        $scope.collectionListTotal = data.sharedItemList.length;
      } 
        $scope.successMessage = sessionStorage.getItem("successMessage");
        $timeout(function(){ angular.element("#success").hide('slow');}, 2000);
        
        sessionStorage.removeItem("successMessage");
     }else{
      $scope.collectionFormError = true;
      $scope.errorMessage = data.errorMessage;
    }
  },function(){
    console.log("Error in add collection");
  });
}


/*delete collection by listing*/
$scope.deleteCollectionByListing = function(collectionId,consumerkey) {

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  var postData = new Object();
  if(consumerkey){
    postData.reqPaparam = { "userPublicKey" : consumerkey,
  
  "collectionId" : collectionId
  };
}else{
  postData.reqPaparam = { "userPublicKey" : savedData.memberShipKey,
  
  "collectionId" : collectionId
  };
}
  
  var url = valouxService.getWebServiceUrl("deleteCollectionByCollectionId");
  //post create collection and get response
  var submitCollectionAgent = valouxService.getData(url,postData);
  submitCollectionAgent.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      angular.element("#success").show();
      $scope.successMessage = data.successMessage;
      if(consumerkey){
        $scope.initAgentCollectionData();
      }else{
        $scope.initCollectionData();

      }
      $window.scrollTo(500, 0);      
     }else{
      $route.reload();      
    }
  },function(){
    console.log("Error in delete collection");
  });
}

/*delete collection by detail page*/
$scope.deleteCollectionByDetails = function(collectionId,consumerkey) {

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  var postData = new Object();
  if(consumerkey){
    postData.reqPaparam = { "userPublicKey" : consumerkey,
  
  "collectionId" : collectionId
  };
  }
  else{
    postData.reqPaparam = { "userPublicKey" : savedData.memberShipKey,
  
  "collectionId" : collectionId
  };
  }
  var url = valouxService.getWebServiceUrl("deleteCollectionByCollectionId");
  //post create collection and get response
  var submitCollectionAgent = valouxService.getData(url,postData);
  submitCollectionAgent.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      if(consumerkey){
        $window.location= 'collection-agent.html#/';
      }else{
        $window.location= 'collection.html#/';
      }
        sessionStorage.setItem("successMessage", data.successMessage); 
        
     }else{
      $route.reload();      
    }
  },function(){
    console.log("Error in delete collection");
  });
}

}]).filter('unique', function() {
    return function(input, key) {
        var unique = {};
        var uniqueList = [];        
        for(var i = 0; i < input.length; i++){
            if(typeof unique[input[i][key]] == "undefined"){
                unique[input[i][key]] = "";
                uniqueList.push(input[i]);
            }
        }
        
        return uniqueList;
    };
});

          