baseController.controller('appraisalManagement',['$scope', 'valouxService', '$timeout','$location','RESPONSE_CODE', 'masterDataService', '$route', '$routePaparams', '$window', function($scope, valouxService, $timeout,$location, RESPONSE_CODE, masterDataService,$route, $routePaparams, $window){

/*Define appraisal Object*/

$scope.storeAppraisal = new Object();
$scope.stepButton = true;
$scope.noItem = false;
$scope.itemCollection = false;
$scope.addedCollections = [];
$scope.addedItems = [];
$scope.blankCollection = true;
$scope.imgNotFound = false;
$scope.addButton = false;


/* focus Update collection form */
$scope.focus = function() {
  $window.scrollTo(500, 0);
}


/******************************************************* added for share listing on apppraisal page ***********************/

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
	   
	   $scope.initAppraisalData();
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



/***************************************************** end of code of share listing on appraisal page *********************/

/*Create appraisal step 1*/
$scope.createAppraisalStep1 = function () {
	
	// check if user came from agent add appraisal page
	if($scope.newConsumerSubmit){

	  
	  // code to send request to create appraisal and user
	  $scope.$parent.fullPageBlockLoading = true;

	  var savedItem = sessionStorage.getItem("saveValouxData");
	  var savedData = JSON.parse(savedItem);
	  
	  
	  
	  /*collection added in appraisal*/
	  var collectionArray = [];
	 
	  /*Item added in appraisal*/
	  var itemArray = [];
	 
	  var postData = new Object();
	  postData.reqPaparam = {"appraisalItemOrCollectionData" :  $scope.storeAppraisal};
	
	  postData.reqPaparam.appraisalItemOrCollectionData.userPublicKey = "";
	  postData.reqPaparam.appraisalItemOrCollectionData.requestType = "Add";
	  postData.reqPaparam.appraisalItemOrCollectionData.collectionId = collectionArray;
	  postData.reqPaparam.appraisalItemOrCollectionData.itemId = itemArray;
	  postData.reqPaparam.appraisalItemOrCollectionData.newConsumerData = $scope.newConsumerData;
	
	  var url = valouxService.getWebServiceUrl("createOrUpdateAppraisalForItemsOrCollection");
	  //post create appraisal and get response
	  var submitAppraisal = valouxService.getData(url,postData);
	  submitAppraisal.then(function(data){
		 if (data.sCode == RESPONSE_CODE.SUCCESS) {
			 		
			 	sessionStorage.removeItem("agentInformation");
				sessionStorage.setItem("agentInformation",data.resData.userPublicKey);
				$scope.userPublicKeyStore = data.resData.userPublicKey;
				$window.location = "appraisal-agent.html";
		}else{
		  $scope.appraisalFormError = true;
		  $scope.errorMessage = data.errorMessage;
		}
	  },function(){
		console.log("Error in create appraisal");
	  });
	  // end of code to create appraisal and user
    }else
	{
		// continue with relgular process
		$scope.stepButton = false;
		$scope.initCollectionItemListingCreateAppraisal();
	}
}

$scope.$on("$locationChangeStart", function(event) {
   $('.tooltipped').tooltip('remove');
});

/* cancel Button*/
$scope.cancelAgent= function() {
  $window.scrollTo(500,0);
  $scope.$parent.fullPageBlockLoading = true;
  $window.location = "appraisal-agent.html#/";
}

/* cancel Button consumer*/
$scope.cancelConsumer= function() {
  $window.scrollTo(500,0);
  $scope.$parent.fullPageBlockLoading = true;
  $window.location = "appraisal.html#/";
}

/* to edit appraisal of consumer by agent */

$scope.editAppraisalAgent = function(agentInfo,appraisalId) {
	
  if(agentInfo){
    sessionStorage.setItem("agentInformation", agentInfo);
  }
  $scope.consumerPublicKey = agentInfo;	
  $timeout(function(){ angular.element('ul.tabs').tabs();}, 100);
  $window.location = "appraisal-agent.html#/edit/"+appraisalId;
}

/*view all data  in agent */
$scope.viewAllData = function() {
  $route.reload()
  sessionStorage.removeItem("agentInformation");
}


/*invite new consumer in agent */
$scope.inviteNewConsumerAgent = function(nviteFrom) {
	
  $scope.inviteNewConsumer = true;
  $scope.newConsumerSubmit = true;
  sessionStorage.removeItem("agentInformation");
  if(nviteFrom)
  {
  	$window.location = "appraisal-agent.html#/add/";
  }else
  {
	 $route.reload();
  }
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
// to redirect agent to listing page if other user selected while add/edit appraisal records
$scope.storeAgentInfoDropdown = function(agentInfo) {
  $scope.$parent.fullPageBlockLoading = true;
	
  if(agentInfo){
	  
    sessionStorage.removeItem("agentInformation");
    sessionStorage.setItem("agentInformation", agentInfo);
    $window.location = 'appraisal-agent.html#/'
    //$scope.initConsumerInformation();
  }else{
    sessionStorage.removeItem("agentInformation");
    $window.location = 'appraisal-agent.html#/'
  }
  //$timeout(function(){ angular.element('ul.tabs').tabs();}, 100);
  
}

/*appraisal listing for agent*/
$scope.initAgentAppraisalData = function() {

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var agentInfo = sessionStorage.getItem("agentInformation");
  /*Sorting element according to latest & alphabatical order*/
  $scope.sortorder = '-id';
  var postData = new Object();
  postData.reqPaparam = { "sharedTo" : savedData.memberShipKey,
  "sharedItemType" : "3",
  "approvedStatus" : "2"
  };
  var url = valouxService.getWebServiceUrl("getUserSharedWithMeList");
  //post create collection and get response
  var submitAppraisalAgent = valouxService.getData(url,postData);
  submitAppraisalAgent.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.animated();
      if(data.sharedItemList.length == 0){
        $scope.noItem = true;
        $scope.appraisalList = true;
        $scope.appraisalListed = false;
		 $timeout(function(){ $('ul.tabs').tabs(); angular.element("#viewedItemsBy").material_select();angular.element("#sortBy").material_select(); angular.element('.dropdown-button').dropdown(); }, 500);
		 $scope.initConsumerInformation();
      }else{
        $scope.noItem = false;
        $scope.appraisalList = true;
        $scope.appraisalListed =  data.sharedItemList;
		if(agentInfo){
          $scope.viewItemBy = agentInfo;
		  
        }
        
		  $scope.initConsumerInformation();
        $timeout(function(){ $('ul.tabs').tabs(); angular.element("#viewedItemsBy").material_select();angular.element("#sortBy").material_select(); angular.element('.dropdown-button').dropdown(); }, 500);
		
        $scope.appraisalListTotal = data.sharedItemList.length;
      } 
      $scope.successMessage = sessionStorage.getItem("successMessage");
      $timeout(function(){ angular.element("#success").hide('slow');}, 2000);
      sessionStorage.removeItem("successMessage");
     }else{
		 
      $scope.appraisalFormError = true;
      $scope.errorMessage = data.errorMessage;
    }
  },function(){
    console.log("Error in Agent Appraisal listing");
  });
}

/*Init collections & items listing in appraisal*/
$scope.initCollectionItemListingCreateAppraisal = function () {

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var savedItemAgent = sessionStorage.getItem("agentInformation");
  var postData = new Object();
  if(savedItemAgent){
	    
		postData.reqPaparam = {"userPublicKey" : savedItemAgent};

	  }else{
	   postData.reqPaparam = {"userPublicKey" : savedData.memberShipKey};
  }
  
  var url = valouxService.getWebServiceUrl("getAllItemAndCollectionList");
  //post create appraisal and get response
  var submitAppraisal = valouxService.getData(url,postData);
  submitAppraisal.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      if(typeof data.resData.itemData == 'undefined' && typeof data.resData.collectionData == 'undefined'){
        $scope.noItem = true;
      }else{
        $scope.itemDataLists = data.resData.itemData;
        $scope.collectionDataLists = data.resData.collectionData;
        $scope.itemCollection = true;
      }
    }else{
      $scope.appraisalFormError = true;
      $scope.errorMessage = data.errorMessage;
    }
  },function(){
    console.log("Error in collection listing in appraisal");
  });
}

/*create appraisal step2*/
$scope.createAppraisalStep2 = function() {
  $scope.$parent.fullPageBlockLoading = true;

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  var savedItemAgent = sessionStorage.getItem("agentInformation");
  
  
  /*collection added in appraisal*/
  var collectionArray = [];
  angular.forEach($scope.addedCollections, function(addedCollection){
    collectionArray.push(addedCollection.vcid);
  })
  /*Item added in appraisal*/
  var itemArray = [];
  angular.forEach($scope.addedItems, function(addeditems){
    itemArray.push(addeditems.itemId);
  })

  var postData = new Object();
  postData.reqPaparam = {"appraisalItemOrCollectionData" :  $scope.storeAppraisal};
  if(savedItemAgent){
	    postData.reqPaparam.appraisalItemOrCollectionData.userPublicKey = savedItemAgent;
	  }else{
	    postData.reqPaparam.appraisalItemOrCollectionData.userPublicKey = savedData.memberShipKey;
	  }
  
  postData.reqPaparam.appraisalItemOrCollectionData.requestType = "Add";
  postData.reqPaparam.appraisalItemOrCollectionData.collectionId = collectionArray;
  postData.reqPaparam.appraisalItemOrCollectionData.itemId = itemArray;

  var url = valouxService.getWebServiceUrl("createOrUpdateAppraisalForItemsOrCollection");
  //post create appraisal and get response
  var submitAppraisal = valouxService.getData(url,postData);
  submitAppraisal.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
    	 if(savedItemAgent){
    	        $window.location = "appraisal-agent.html";
    	      }else{
    	        $window.location = "appraisal.html";
    	      }
    }else{
      $scope.appraisalFormError = true;
      $scope.errorMessage = data.errorMessage;
    }
  },function(){
    console.log("Error in create appraisal");
  });
}

/*add collection & items in appraisal*/
$scope.collectionsItemsAddInAppraisal = function() {
 
  if(typeof $scope.collectionDataLists != "undefined"){
  var len = $scope.collectionDataLists.length;
  while (len--) {
    var collection = $scope.collectionDataLists[len];
    if (collection.selected) {
       $scope.addedCollections.push(collection);
       $scope.collectionDataLists.splice(len, 1);
    };
  }
  }
  
  if(typeof $scope.itemDataLists != 'undefined'){
  var len = $scope.itemDataLists.length;
  while (len--) {
    var items = $scope.itemDataLists[len];
    if (items.selected) {
       $scope.addedItems.push(items);
       $scope.itemDataLists.splice(len, 1);
    };
  }}

  $scope.total = $scope.addedCollections.length + $scope.addedItems.length;
  
  if($scope.addedCollections != "" || $scope.addedItems != ""){
    $scope.blankCollection = false;
  }
}

/*Remove collection in appraisal*/

$scope.removeCollectionAppraisal = function(index){
    
  $scope.collectionDataLists.push($scope.addedCollections.splice(index, 1)[0]);
  
  $scope.total = $scope.addedCollections.length + $scope.addedItems.length;
  if($scope.addedCollections == '' && $scope.addedItems == ''){
    $scope.blankCollection = true;
  }
}

/*Remove items in appraisal*/

$scope.removeItemsAppraisal = function(index){
    
  $scope.itemDataLists.push($scope.addedItems.splice(index, 1)[0]);
  
  $scope.total = $scope.addedCollections.length + $scope.addedItems.length;
  if($scope.addedCollections == '' && $scope.addedItems == ''){
    $scope.blankCollection = true;
  }
}


/*Init appraisal data in appraisal dashboard*/
$scope.initAppraisalData = function() {

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  /*Sorting element according to latest & alphabatical order*/
  $scope.sortorder = '-appraisalId';
  var postData = new Object();

  postData.reqPaparam = {"userPublicKey" : savedData.memberShipKey};
  
  var url = valouxService.getWebServiceUrl("getUserAppraisalList");
  //post create appraisal and get response
  var submitAppraisal = valouxService.getData(url,postData);
  submitAppraisal.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.animated();
      $timeout(function(){$('.dropdown-button').dropdown(); $('ul.tabs').tabs(); angular.element("#sortBy").material_select();}, 500);
      if(typeof data.resData.appraisal == "undefined"){
        $scope.noItem = true;
        $scope.appraisalListed =  false;
      }else{
        $scope.noItem = false;
        $scope.appraisalListed =  data.resData.appraisal;
        $scope.appraisalListedTotal = data.resData.appraisal.length;
      }
      $scope.successMessage = sessionStorage.getItem("successMessage");
      $timeout(function(){ angular.element("#success").hide('slow');}, 2000);
      sessionStorage.removeItem("successMessage");
     }else{
      $scope.collectionFormError = true;
      $scope.errorMessage = data.errorMessage;
    }
  },function(){
    console.log("Error in listing appraisal");
  });
}

/*Open model collection in appraisal dashboard */
$scope.modelCollectionListing = function(appraisalId,sharedBy, status) {
  if(status == 3){
    $scope.appraisalStatus = true;
  }else{
    $scope.appraisalStatus = false;

  }
  $scope.appraisalId = appraisalId;
  $scope.consumerPublicKey = sharedBy;
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  var postData = new Object();
  if($scope.consumerPublicKey)
  {
	  postData.reqPaparam = { 
		"userPublicKey" : $scope.consumerPublicKey,
		"appraisalId" : appraisalId
	  };
  }else
  {
	  postData.reqPaparam = { 
		"userPublicKey" : savedData.memberShipKey,
		"appraisalId" : appraisalId
	  };
  }
  
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
		    $scope.appraisalId = appraisalId;
        $timeout(function(){  $('.mCustomScrollbar').mCustomScrollbar(); angular.element('.tooltipped').tooltip(); }, 500);
      }
    }else{
		if($scope.isAgentLogin)
		{
      		$window.location = 'appraisal-agent.html#/'
		}else
		{
			$window.location = 'appraisal.html#/'
		}
    }
  },function(){
    console.log("Error in listing appraisal");
  });
}
/*Open model add collection in appraisal listing */
$scope.addNewCollectionInAppraisal = function(appraisalId,consumerPublicKey) {
	
  $scope.addButton = true;
  $scope.addCollectionInAppraisals(appraisalId,consumerPublicKey);
}

/* create customer session and add item  */
$scope.addItemInAppraisalsForConsumer = function(consumerPublicKey) {
				
				sessionStorage.removeItem("agentInformation");
				sessionStorage.setItem("agentInformation",consumerPublicKey);
				$scope.userPublicKeyStore = consumerPublicKey;
				$window.location = "item-agent.html#/add";
}

/* create customer session and add collection  */
$scope.addCollectionInAppraisalsForConsumer = function(consumerPublicKey) {
				sessionStorage.removeItem("agentInformation");
				sessionStorage.setItem("agentInformation",consumerPublicKey);
				$scope.userPublicKeyStore = consumerPublicKey;
	$window.location = "collection-agent.html#/add";
}
/*Open model add collection in appraisal listing */
$scope.addCollectionInAppraisals = function(appraisalId,consumerPublicKey) {
	
  $scope.appraisalId = appraisalId;
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  $scope.errorSelected = '';
  var postData = new Object();
  var savedItemAgent = sessionStorage.getItem("agentInformation");
  
  if(savedItemAgent)
  {
	  postData.reqPaparam = { "collectionInfo" : { 
		"userPublicKey" : savedItemAgent,
		"appraisalId" : $scope.appraisalId
	  }};
  }else
  {
	   if(consumerPublicKey)	
	  {
		  var sendKey = consumerPublicKey;
	  }else
	  {
		  var sendKey = savedData.memberShipKey;
	  }
	  
	   postData.reqPaparam = { "collectionInfo" : { 
		"userPublicKey" : sendKey,
		"appraisalId" : $scope.appraisalId
	  }};

  }
  
  var url = valouxService.getWebServiceUrl("getCollectionListNotInAppraisal");
  //post create appraisal and get response
  var submitAppraisal = valouxService.getData(url,postData);
  submitAppraisal.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
        angular.element('#modal-collection-nolist').closeModal();
        angular.element('#modal-collection-list').closeModal();
        if(typeof data.resData.collectionData == "undefined"){
          if($scope.addButton){
            angular.element('#modal-collection-noData').openModal();
          }else{
			if(savedItemAgent)
  			{			  
            	$window.location = 'collection.html#/add';
			}
          }
          angular.element('#modal-collection-noData').openModal();
          
        }
        else{
          angular.element('#modal-add-new-collection').openModal();
          $scope.addCollectionInAppraisal = data.resData.collectionData;
          $scope.collectionsInAppraisal = data.resData.collectionsInAppraisal;

        }
        
    }else{
		if(savedItemAgent)
  		{
      		$window.location = 'appraisal.html#/'
		}else
		{
			$window.location = 'appraisal-agent.html#/'	
		}
    }
  },function(){
    console.log("Error in listing collection in appraisal listing");
  });
}

/*added collection in appraisal listing*/
$scope.addedCollectionInAppraisal = function() {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);

  var collectionArray = [];
  for(i = 0; i < $scope.collectionsInAppraisal.length; i++){
    collectionArray.push($scope.collectionsInAppraisal[i]);
  }
  angular.forEach($scope.addCollectionInAppraisal, function(collection){
    if(collection.selected){
    collectionArray.push(collection.vcid);
    $scope.checked = true;
  }
  })   
  if($scope.checked){
  var postData = new Object();
  if($scope.consumerPublicKey)
  {
	  postData.reqPaparam = { "appraisalItemOrCollectionData" : {
		"userPublicKey" : $scope.consumerPublicKey,
		"requestType" : "update",
		"appraisalId" : $scope.appraisalId,
		"collectionId" : collectionArray
	  }};
  }else
  {
	 	  postData.reqPaparam = { "appraisalItemOrCollectionData" : {
		"userPublicKey" : savedData.memberShipKey,
		"requestType" : "update",
		"appraisalId" : $scope.appraisalId,
		"collectionId" : collectionArray
	  }};
 
  }
  
  var url = valouxService.getWebServiceUrl("createOrUpdateAppraisalForItemsOrCollection");
  //post create appraisal and get response
  var submitAppraisal = valouxService.getData(url,postData);
  submitAppraisal.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
        angular.element('#modal-add-new-collection').closeModal();
        $window.location.reload();
      }else{
		  if($scope.isAgentLogin){
				  $window.location = 'appraisal-agent.html#'
			 }else
			 {
				  $window.location = 'appraisal.html#'
			 }
     // $window.location = 'appraisal.html#/'
    }
  },function(){
    console.log("Error in listing collection in appraisal listing");
  });
}else{
  $scope.errorSelected = 'Please select atleast one'
}
}
/* hide error message*/
$scope.selected= function() {
  $scope.errorSelected = '';  
}

/*Open model item in appraisal listing */
$scope.modelItemListing = function(appraisalId,sharedBy,status) {
  
  if(status == 3){
    $scope.appraisalStatus = true;
  }else{
    $scope.appraisalStatus = false;

  }
  $scope.appraisalId = appraisalId;
  $scope.consumerPublicKey = sharedBy;	
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  var postData = new Object();
  if($scope.consumerPublicKey)
  {
	  postData.reqPaparam = { 
		"userPublicKey" : $scope.consumerPublicKey,
		"appraisalId" : appraisalId
	  };
  }else
  {
	  postData.reqPaparam = { 
		"userPublicKey" : savedData.memberShipKey,
		"appraisalId" : appraisalId
	  };
 
  }
  
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
		$scope.appraisalId = appraisalId;
        $timeout(function(){ $('.mCustomScrollbar').mCustomScrollbar(); angular.element('.tooltipped').tooltip(); }, 500);
      }
    }else{
       if(savedItemAgent){
    	        $window.location = "appraisal-agent.html";
    	      }else{
    	        $window.location = "appraisal.html";
    	      }
    }
  },function(){
    console.log("Error in item listing appraisal");
  });
}
/*Open model add items in appraisal listing */
$scope.addNewItemsInAppraisal = function(appraisalId) {
  $scope.addButton = true;
  $scope.addItemsInAppraisals(appraisalId);
}
/*Open model add items in appraisal listing */
$scope.addItemsInAppraisals = function(appraisalId) {

  $scope.appraisalId = appraisalId;
  var savedItem = sessionStorage.getItem("saveValouxData");
  var agentInfo = sessionStorage.getItem("agentInformation");
  
  var savedData = JSON.parse(savedItem);
  $scope.errorSelected = '';
  var postData = new Object();
  if($scope.consumerPublicKey)
  {
  postData.reqPaparam = { "itemsInfo" : { 
    "userPublicKey" : $scope.consumerPublicKey,
    "appraisalId" : $scope.appraisalId
  }};
  }else if(agentInfo){
    $scope.consumerPublicKey = agentInfo;
    postData.reqPaparam = { "itemsInfo" : { 
    "userPublicKey" : agentInfo,
    "appraisalId" : $scope.appraisalId
  }};
  }

  else
  {
	   postData.reqPaparam = { "itemsInfo" : { 
    "userPublicKey" : savedData.memberShipKey,
    "appraisalId" : $scope.appraisalId
	  }};
  }
  
  var url = valouxService.getWebServiceUrl("getItemsListNotInAppraisal");
  //post create appraisal and get response
  var submitAppraisal = valouxService.getData(url,postData);
  submitAppraisal.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
        angular.element('#modal-noitems-appraisal').closeModal();
        angular.element('#modal-items-appraisal').closeModal();
        if(typeof data.resData.itemData == "undefined"){
          if($scope.addButton){
            angular.element('#modal-noitems-appraisal-list').openModal();
          }
          else{
			 if($scope.isAgentLogin){
				  $window.location = 'item-agent.html#/add'
			 }else
			 {
				  $window.location = 'item.html#/add'
			 }
           
          }
        }
        else{
          angular.element('#modal-items-add-appraisal').openModal();
          $scope.addItemsInAppraisal = data.resData.itemData;
		  $scope.appraisalId = appraisalId;
          $scope.itemsInAppraisal = data.resData.itemsInAppraisal;
          
        }
        
    }else{
       if(savedItemAgent){
    	        $window.location = "appraisal-agent.html";
    	      }else{
    	        $window.location = "appraisal.html";
    	      }
    }
  },function(){
    console.log("Error in listing item in appraisal listing");
  });
}

$scope.initConsumerInformation= function() {
	  var savedItemAgent = sessionStorage.getItem("agentInformation");
	  
	  var savedItem = sessionStorage.getItem("saveValouxData");
	  var savedData = JSON.parse(savedItem);
	  var postData = new Object();
	  postData.reqPaparam = { 
	      "userPublicKey" : savedData.memberShipKey
	    };
	  var url = valouxService.getWebServiceUrl("getConsumerListWhoSharedItemWithAgent");
	  //post data and get response
	  var submitCollection = valouxService.getData(url,postData);
	  submitCollection.then(function(data){
	    if (data.sCode == RESPONSE_CODE.SUCCESS) {
	      $scope.viewByConsumer = data.consumerList;
	      angular.forEach(data.consumerList, function(info){
	      if(info.consumerPublicKey == savedItemAgent){
			    $scope.sharedBy = info;
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

// Animated appraisal listing
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

/*Clear search data */
$scope.clearSearchData = function(event) {
  $('#search2').val('');
  //$('.tooltipped').tooltip('remove');
  $timeout(function(){ angular.element('.dropdown-button').dropdown();}, 100);

  event.searchKeyword = "";
}

/*item listing in appraisal on collection folder listing*/
$scope.collectionInApprasial = function (cid , itemCount) {

  //$scope.collectionId = cid;
  if(itemCount == 0){
    $('#modal-noitems').openModal();
  }
  else{    
  $('#modal-items').openModal();

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var postData = new Object();
  postData.reqPaparam = { 
    "userPublicKey" : savedData.memberShipKey,
    "cId" : cid,
  }
  
  var url = valouxService.getWebServiceUrl("getItemsOfCollection");

  //post create collection and get response
  var submitAppraisal = valouxService.getData(url,postData);
  submitAppraisal.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.collectionPopupItems = data.resData.items;
    }else{
      $scope.appraisalFormError = true;
      $scope.errorMessage = data.errorMessage;
    }
  },function(){
    console.log("Error in add collection on appraisal");
  });
}
}


/*added items in appraisal listing*/
$scope.addedItemsInAppraisal = function() {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);

  var itemArray = [];
  
  for(i = 0; i < $scope.itemsInAppraisal.length; i++){
    itemArray.push($scope.itemsInAppraisal[i]);
  }
  angular.forEach($scope.addItemsInAppraisal, function(item){
    if(item.selected){
      itemArray.push(item.itemId);
      $scope.checked = true;
    }
  })
  if($scope.checked){
  var postData = new Object();
  if($scope.consumerPublicKey)
  {
	  postData.reqPaparam = { "appraisalItemOrCollectionData" : { 
		"requestType" : "update",
		"userPublicKey" : $scope.consumerPublicKey,
		"appraisalId" : $scope.appraisalId,
		"itemId" : itemArray
	  }};
  }else
  {
	  	  postData.reqPaparam = { "appraisalItemOrCollectionData" : { 
		"requestType" : "update",
		"userPublicKey" : savedData.memberShipKey,
		"appraisalId" : $scope.appraisalId,
		"itemId" : itemArray
	  }};

  }
  
  var url = valouxService.getWebServiceUrl("createOrUpdateAppraisalForItemsOrCollection");
  //post create appraisal and get response
  var submitAppraisal = valouxService.getData(url,postData);
  submitAppraisal.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
        angular.element('#modal-items-add-appraisal').closeModal();
        $window.location.reload();
      }else{
       if(savedItemAgent){
    	        $window.location = "appraisal-agent.html";
    	      }else{
    	        $window.location = "appraisal.html";
    	      }
    }
  },function(){
    console.log("Error in listing items in appraisal listing");
  });
}else{
  $scope.errorSelected = 'Please select atleast one'
}
}

/*remove items in appraisal item listing*/
$scope.removeItemsInAppraisal = function(itemId, index) {
  
  var itemArray = [];
  var collectionArray = [];
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  /*Define array variable*/
  itemArray.push(itemId);

  var postData = new Object();
  postData.reqPaparam = { "appraisalInfo" : { 
    "userPublicKey" : savedData.memberShipKey,
    "appraisalId" : $scope.appraisalId,
    "itemId" : itemArray,
    "collectionId" : collectionArray
  }};
  
  var url = valouxService.getWebServiceUrl("deleteCollectionOrItemFromAppraisal");
  //post create appraisal and get response
  var submitAppraisal = valouxService.getData(url,postData);
  submitAppraisal.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.itemLists.splice(index, 1)[0];
      
      angular.element(document.getElementById('items_appraisal_app'+$scope.appraisalId)).html($scope.itemLists.length);
      angular.forEach($scope.appraisalListed, function(itemsLi, index){
      
      if($scope.appraisalListed[index].appraisalId == $scope.appraisalId){
        $scope.appraisalListed[index].itemImages = $scope.itemLists;
      }
    })
      if($scope.itemLists.length == 0){
        angular.element('#modal-items-appraisal').closeModal();
        angular.element('#modal-noitems-appraisal').openModal();
      }
      
    }else{
       if(savedItemAgent){
    	        $window.location = "appraisal-agent.html";
    	      }else{
    	        $window.location = "appraisal.html";
    	      }
    }
  },function(){
    console.log("Error in listing items in appraisal listing");
  });
}

/*remove collection in appraisal collection listing*/
$scope.removeCollectionInAppraisal = function(collectionId, index) {
  
  var itemArray = [];
  var collectionArray = [];
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  /*Define array variable*/
  collectionArray.push(collectionId);

  var postData = new Object();
  postData.reqPaparam = { "appraisalInfo" : { 
    "userPublicKey" : savedData.memberShipKey,
    "appraisalId" : $scope.appraisalId,
    "itemId" : itemArray,
    "collectionId" : collectionArray
  }};
  
  var url = valouxService.getWebServiceUrl("deleteCollectionOrItemFromAppraisal");
  //post create appraisal and get response
  var submitAppraisal = valouxService.getData(url,postData);
  submitAppraisal.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.collectionLists.splice(index, 1)[0];
      angular.element(document.getElementById('collection_appraisal_app'+$scope.appraisalId)).html($scope.collectionLists.length);
      angular.forEach($scope.appraisalListed, function(itemsLi, index){
      
      if($scope.appraisalListed[index].appraisalId == $scope.appraisalId){
        $scope.appraisalListed[index].collectionImages = $scope.collectionLists;
      }
      if($scope.appraisalListed[index].id == $scope.appraisalId){
        $scope.appraisalListed[index].collectionImages = $scope.collectionLists;
      }
    })
    angular.forEach($scope.searchAppraisalDetail, function(itemsLi, index){
      if($scope.searchAppraisalDetail[index].id == $scope.appraisalId){
        $scope.searchAppraisalDetail[index].collectionImages = $scope.collectionLists;
      }
    })
      
      if($scope.collectionLists.length == 0){
        angular.element('#modal-collection-list').closeModal();
        angular.element('#modal-collection-nolist').openModal();
      }
      
    }else{
      if(savedItemAgent){
    	        $window.location = "appraisal-agent.html";
    	      }else{
    	        $window.location = "appraisal.html";
    	      }
    }
  },function(){
    console.log("Error in listing items in appraisal listing");
  });
}

/*edit appraisal items listing */
$scope.itemNotInAppraisal = function(appraisalId) {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var savedItemAgent = sessionStorage.getItem("agentInformation");
  var postData = new Object();
 
  $scope.consumerPublicKey = savedItemAgent;
  if($scope.consumerPublicKey)
  {
  postData.reqPaparam = { "itemsInfo" : { 
    "userPublicKey" : $scope.consumerPublicKey,
    "appraisalId" : appraisalId
  }};
  }else
  {
   postData.reqPaparam = { "itemsInfo" : { 
    "userPublicKey" : savedData.memberShipKey,
    "appraisalId" : appraisalId
  }};
  }
  
  
  var url = valouxService.getWebServiceUrl("getItemsListNotInAppraisal");
  //post create appraisal and get response
  var submitAppraisal = valouxService.getData(url,postData);
  submitAppraisal.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      if(data.resData.itemData){
        $scope.itemDataLists = data.resData.itemData;
      }else{
        $scope.itemDataLists = [];
        $scope.itemShow = true;
      }
      
    }else{
       if(savedItemAgent){
              $window.location = "appraisal-agent.html";
            }else{
              $window.location = "appraisal.html";
            }
    }
  },function(){
    console.log("Error in listing item in appraisal listing");
  });
}

/* edit appraisal collection listing not in appraisal*/
$scope.collectionNotInAppraisal = function(appraisalId, collectionBlank, itemsBlank) {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  var postData = new Object();
   var savedItemAgent = sessionStorage.getItem("agentInformation");
   
   if(savedItemAgent)
   {
    postData.reqPaparam = { "collectionInfo" : { 
    "userPublicKey" : savedItemAgent,
    "appraisalId" : appraisalId
    }};
   }else
   {
        postData.reqPaparam = { "collectionInfo" : { 
    "userPublicKey" : savedData.memberShipKey,
    "appraisalId" : appraisalId
    }};

     
   }
  
  
  var url = valouxService.getWebServiceUrl("getCollectionListNotInAppraisal");
  //post create appraisal and get response
  var submitAppraisal = valouxService.getData(url,postData);
  submitAppraisal.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      if(collectionBlank.length == 0 && itemsBlank.length == 0 && typeof data.resData.collectionData == 'undefined' && $scope.itemDataLists.length == 0){
        $scope.noItem = true;
        $scope.itemCollection = false;
      }else{
        $scope.noItem = false;
        $scope.itemCollection = true;
      }
      if(data.resData.collectionData){
        $scope.collectionDataLists = data.resData.collectionData;
      }else{
        $scope.collectionDataLists = [];
      }
      
    }else{
       if(savedItemAgent){
              $window.location = "appraisal-agent.html";
            }else{
              $window.location = "appraisal.html";
            }
    }
  },function(){
    console.log("Error in listing collection in appraisal listing");
  });
}

/*edit appraisal page*/
$scope.initAppraisalUpdate= function() {

	var savedItemAgent = sessionStorage.getItem("agentInformation");
	  if(savedItemAgent)
	  {
	     $scope.agentHeaderEnable = true;
		  $scope.inviteNewConsumer = false;
	  }
	  else
	  {
 		if($scope.isAgentLogin)
		{
		  $scope.inviteNewConsumer = true;
		  $scope.newConsumerSubmit = true;
		  $scope.newConsumerData = new Object();
		}else
		{
		  $scope.consumerLogin = true;
		}
    }

var appraisalId = $route.current.paparams.appraisalId;

 if(appraisalId){

  $scope.appraisalId = appraisalId;
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
 
  var postData = new Object();
  postData.reqPaparam = { "appraisalItemOrCollectionData" : $scope.storeAppraisal};
  postData.reqPaparam.appraisalItemOrCollectionData.appraisalId = appraisalId;
  postData.reqPaparam.appraisalItemOrCollectionData.requestType = "update";
  postData.reqPaparam.appraisalItemOrCollectionData.userPublicKey = savedData.memberShipKey;


  var url = valouxService.getWebServiceUrl("createOrUpdateAppraisalForItemsOrCollection");
  //collection response
  var submitAppraisal = valouxService.getData(url,postData);
  submitAppraisal.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.class = "active";
      $scope.itemCollection = true;
      $scope.stepButton = false;
      $window.scrollTo(500, 0);
      $scope.$parent.fullPageBlockLoading = false;
      $scope.storeAppraisal = data.resData;
      if(data.resData.itemsList && data.resData.collectionList){
        $scope.addedItems = data.resData.itemsList;
        $scope.addedCollections = data.resData.collectionList;
        $scope.total = $scope.addedCollections.length + $scope.addedItems.length;
        $scope.blankCollection = false;
      }else{
        $scope.blankCollection = true;

      }
      
      $scope.noItem = false;            
      $scope.edit = true; 
      $scope.itemNotInAppraisal(appraisalId);    
      $scope.collectionNotInAppraisal(appraisalId, data.resData.collectionList, data.resData.itemsList);
      
     
    }else{
      $scope.appraisalFormError = true;
      $scope.errorMessage = data.errorMessage;
    }
  },function(){
    console.log("Error in edit appraisal");
  });
}else{
  $scope.$parent.fullPageBlockLoading = false;
}
}




/*remove tooltip click on list & grid view */
$scope.removeTooltip = function() {
  angular.element('.tooltipped').tooltip('remove');
}

/*appraisal details page*/
$scope.appraisalDetail= function() {

var appraisalId = $route.current.paparams.appraisalId;

 if(appraisalId){
  var savedItem = sessionStorage.getItem("saveValouxData");
  var agentInfo = sessionStorage.getItem("agentInformation");

  var savedData = JSON.parse(savedItem);
  var postData = new Object();
  postData.reqPaparam = { 
    "appraisalId" : appraisalId
  };
  
  if(agentInfo){
    postData.reqPaparam.userPublicKey = agentInfo;
  }else{
    postData.reqPaparam.userPublicKey = savedData.memberShipKey;
  }

  var url = valouxService.getWebServiceUrl("getAppraisalDetails");
  //appraisal post & response
  var submitAppraisal = valouxService.getData(url,postData);
  submitAppraisal.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $window.scrollTo(500, 0);
      $scope.appraisalDetail =data.resData;
      $scope.userKey = savedData.memberShipKey;
      $timeout(function(){angular.element('.dropdown-button').dropdown();$('.mCustomScrollbar').mCustomScrollbar();}, 100);
      
    }else{
      if(savedItemAgent){
    	        $window.location = "appraisal-agent.html";
    	      }else{
    	        $window.location = "appraisal.html";
    	      }
    }
  },function(){
    console.log("Error in detail appraisal");
  });
}else{
    //$window.location = "appraisal.html#/"
}  
}
/*edit appraisal submit*/
$scope.editAppraisalSubmit = function() {
  $scope.$parent.fullPageBlockLoading = true;

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
   var savedItemAgent = sessionStorage.getItem("agentInformation");
   
  /*collection added in appraisal*/
  var collectionArray = [];
  angular.forEach($scope.addedCollections, function(addedCollection){
    collectionArray.push(addedCollection.vcid);
  })
  
  /*Item added in appraisal*/
  var itemArray = [];
  angular.forEach($scope.addedItems, function(addeditems){
    itemArray.push(addeditems.itemId);
  })
  if(savedItemAgent)
  {
	  var userConsumerKey = savedItemAgent;
  }else
  {
	  var userConsumerKey = savedData.memberShipKey;
  }
  
  var postData = new Object();
  postData.reqPaparam = {
    "appraisalItemOrCollectionData" : {
      "userPublicKey" : userConsumerKey,
      "requestType" : "update",
      "collectionId" : collectionArray,
      "appraisalId" : $scope.appraisalId,
      "itemId" : itemArray,
      "name" : $scope.storeAppraisal.name,
      "shortDescription" : $scope.storeAppraisal.shortDescription,
    }
  };
  
  var url = valouxService.getWebServiceUrl("createOrUpdateAppraisalForItemsOrCollection");
  //post create appraisal and get response
  var submitAppraisal = valouxService.getData(url,postData);
  submitAppraisal.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
		if(savedItemAgent)
		{
      		$window.location = "appraisal-agent.html";
		}else
		{
			$window.location = "appraisal.html";	
		}
    }else{
      $scope.appraisalFormError = true;
      $scope.errorMessage = data.errorMessage;
    }
  },function(){
    console.log("Error in create appraisal");
  });
}

/*Click to scroll page collection section in appraisal detail*/
$scope.scrollToCollection = function(collectionCount, id) {
  if(collectionCount > 0){
    $scope.scrollToAnchor(id);
  }
}

/*Click to scroll page item section in appraisal detail*/
$scope.scrollToItem = function(itemsCount, id) {
  if(itemsCount > 0){
      $scope.scrollToAnchor(id);   
  }   
}

/*Scroll page*/
$scope.scrollToAnchor = function(aid) {
  var aTag = $("span[name='"+ aid +"']");
  $('html,body').animate({scrollTop: aTag.offset().top},'slow');
}

/*appraisal detail page in agent */
$scope.agentAppraisalDetail = function(appraisalId, agentInfo) {
  if(agentInfo){
    sessionStorage.setItem("agentInformation", agentInfo);
    $scope.consumerPublicKey = agentInfo; 
    $timeout(function(){ angular.element('.dropdown-button').dropdown();}, 100);
    $window.location = "appraisal-agent.html#/detail/"+appraisalId;

  }
}


/*appraisal detail page request to appraised by appraiser confirm*/
$scope.requestToAppraisedConfirm = function(appraisalId) {
  angular.element('#request-to-appraised').openModal();
  $scope.appraisalId = appraisalId;
  
}


/*appraisal detail page request to appraised by appraiser */
$scope.requestToAppraised = function() {
  
  var postData = new Object();
  var agentInfo = sessionStorage.getItem("agentInformation");
  
  postData.reqPaparam = { 
    "appraisalId" : $scope.appraisalId,
    "userPublicKey" : agentInfo
  }
  var url = valouxService.getWebServiceUrl("changeStatusToAppraised");
  //post create appraisal and get response
  var submitAppraisal = valouxService.getData(url,postData);
  submitAppraisal.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
        $scope.successMessage = 'Appraised successfully!!';
        $scope.appraisalDetailAppraised();
        $window.scrollTo(500, 0);

     }else{
      $window.location = "appraisal-agent.html#/"
    }
  },function(){
    console.log("Error in request to appraise");
  });
  
}

/*appraisal details page*/
$scope.appraisalDetailAppraised= function() {

  var savedItem = sessionStorage.getItem("saveValouxData");
  var agentInfo = sessionStorage.getItem("agentInformation");

  var savedData = JSON.parse(savedItem);
  var postData = new Object();
  postData.reqPaparam = { 
    "appraisalId" : $scope.appraisalId
  };
  
  if(agentInfo){
    postData.reqPaparam.userPublicKey = agentInfo;
  }else{
    postData.reqPaparam.userPublicKey = savedData.memberShipKey;
  }

  var url = valouxService.getWebServiceUrl("getAppraisalDetails");
  //appraisal post & response
  var submitAppraisal = valouxService.getData(url,postData);
  submitAppraisal.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $timeout(function(){ angular.element('#successAppraised').fadeOut('slow');  }, 2500);
      $scope.successfullyAppraised = true;
      
      $scope.appraisalDetail = data.resData;
      $scope.userKey = savedData.memberShipKey;
      $timeout(function(){angular.element('.dropdown-button').dropdown();$('.mCustomScrollbar').mCustomScrollbar();}, 100);
      
    }else{
      if(savedItemAgent){
              $window.location = "appraisal-agent.html";
            }else{
              $window.location = "appraisal.html";
            }
    }
  },function(){
    console.log("Error in detail appraisal");
  });
  
}

/*Open model add collection in appraisal listing */
$scope.addCollectionInAppraisalsAgent = function(appraisalId) {
  $scope.appraisalId = appraisalId;
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  $scope.errorSelected = '';
  var postData = new Object();
  var agentInfo = sessionStorage.getItem("agentInformation");
  $scope.consumerPublicKey = agentInfo;
  postData.reqPaparam = { "collectionInfo" : { 
    "appraisalId" : appraisalId
  }};

  if(agentInfo)
  {
    postData.reqPaparam.collectionInfo.userPublicKey = agentInfo;
  }else
  {
    postData.reqPaparam.collectionInfo.userPublicKey = savedData.memberShipKey;
  }
  
  var url = valouxService.getWebServiceUrl("getCollectionListNotInAppraisal");
  //post create appraisal and get response
  var submitAppraisal = valouxService.getData(url,postData);
  submitAppraisal.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
        angular.element('#modal-collection-nolist').closeModal();
        angular.element('#modal-collection-list').closeModal();
        if(typeof data.resData.collectionData == "undefined"){
          $window.location = "collection-agent.html#/add"
        } 
        else{
          angular.element('#modal-add-new-collection').openModal();
          $scope.addCollectionInAppraisal = data.resData.collectionData;

          $scope.collectionsInAppraisal = data.resData.collectionsInAppraisal;

        }
        
    }else{
      $window.location = "appraisal-agent.html#/"
    }
  },function(){
    console.log("Error in listing collection in appraisal listing");
  });
}
/*edit appraisal items listing */
$scope.downloadpdf = function(appraisalId) {
  var savedItem = sessionStorage.getItem("saveValouxData");
  var agentInfo = sessionStorage.getItem("agentInformation");

  var savedData = JSON.parse(savedItem);
  var postData = new Object();
  postData.reqPaparam = { 
    "appraisalId" : appraisalId
  };
  
  if(agentInfo){
    postData.reqPaparam.userPublicKey = agentInfo;
  }else{
    postData.reqPaparam.userPublicKey = savedData.memberShipKey;
  }

  var url = valouxService.getWebServiceUrl("getAppraisalPdf");
  //appraisal post & response
  var submitAppraisal = valouxService.getData(url,postData);
  submitAppraisal.then(function(data){ 
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      
     // $scope.appraisalDetail =data.resData;
      $scope.userKey = savedData.memberShipKey;
      $timeout(function(){angular.element('.dropdown-button').dropdown();$('.mCustomScrollbar').mCustomScrollbar();}, 100);
    
     // alert(data.resData.pdfFile);
    window.open(data.resData.pdfFile, '_blank');
    }else{
     /* if(savedItemAgent){
              $window.location = "appraisal-agent.html";
            }else{
              $window.location = "appraisal.html";
            }*/
    }
  },function(){
    console.log("Error in detail appraisal");
  });
  
}

/*get total appraised value */
$scope.getTotalCountItems = function(list) {
  var total = 0;
  angular.forEach(list, function(appraised){
    total += appraised.appraisedValue;
  })
  return total;

}

/*appraisal delete*/
$scope.deleteAppraisalByListing = function(appraisalId,consumerKey) {

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  var postData = new Object();
  if(consumerKey){
    postData.reqPaparam = { 
  "userPublicKey" : consumerKey,  
  "appraisalId" : appraisalId
  };  
  }else{postData.reqPaparam = { 
  "userPublicKey" : savedData.memberShipKey,  
  "appraisalId" : appraisalId
  };}
  
  var url = valouxService.getWebServiceUrl("deleteAppraisalByAppraisalId");
  //post create collection and get response
  var submitCollectionAgent = valouxService.getData(url,postData);
  submitCollectionAgent.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      angular.element("#success").show();
      $scope.successMessage = data.successMessage;
      if(consumerKey){
      $scope.initAgentAppraisalData();

      }else{
      $scope.initAppraisalData();

      }
      $window.scrollTo(500, 0);      
     }else{
      $route.reload();      
    }
  },function(){
    console.log("Error in delete appraisal");
  });
}

/*appraisal delete*/
$scope.deleteAppraisalByDetails = function(appraisalId,consumerKey) {

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  var postData = new Object();
  if(consumerKey){
    postData.reqPaparam = { 
  "userPublicKey" : consumerKey,  
  "appraisalId" : appraisalId
  };
  }
  else
  {
    postData.reqPaparam = { 
  "userPublicKey" : savedData.memberShipKey,  
  "appraisalId" : appraisalId
  };
  }
  var url = valouxService.getWebServiceUrl("deleteAppraisalByAppraisalId");
  //post create collection and get response
  var submitCollectionAgent = valouxService.getData(url,postData);
  submitCollectionAgent.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      sessionStorage.setItem("successMessage", data.successMessage);
      if(consumerKey){
      $window.location= 'appraisal-agent.html#/';

      } else{
      $window.location= 'appraisal.html#/';

      }     
      
     }else{
      $route.reload();      
    }
  },function(){
    console.log("Error in delete appraisal");
  });
}


/*appraisal detail agent sharing*/
$scope.agentSharingPopup = function(appraisalId, consumerKey) {

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  // unset variables already set for share html form
  $scope.shareExistingContacts = '';
  $scope.sharedTo = '';
  /*Assign values for sharing*/
  $scope.shareItemObj = appraisalId;
  $scope.ShareItemType = 3;
  $scope.sharedByUser = consumerKey;
  
  var postData = new Object();
  postData.reqPaparam = { 
    "publicKey" : savedData.memberShipKey,  
    "appraisalId" : appraisalId
  }; 
  
  var url = valouxService.getWebServiceUrl("getAppraiserListOfStore");
  //post create share agent and get response
  var submitCollectionAgent = valouxService.getData(url,postData);
  submitCollectionAgent.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
        $('#modal-share-agent').openModal();  
        $scope.shareFormError = false;
        $scope.itemContactDataAppraisal = data.contactList;
        $timeout(function(){  angular.element("#shareAgentWithAppraiser").material_select();}, 500);
     }else{
      $route.reload();      
    }
  },function(){
    console.log("Error in agent sharing");
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
})    