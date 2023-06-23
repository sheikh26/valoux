baseController.controller('adminManagement',['$scope', 'valouxService', '$timeout','$location','RESPONSE_CODE','DTOptionsBuilder', 'DTColumnDefBuilder','$route', '$routePaparams','$window', function($scope, valouxService, $timeout,$location, RESPONSE_CODE,DTOptionsBuilder, DTColumnDefBuilder,$route, $routePaparams,  $window){

// listing of all images of store

$scope.$on("$locationChangeStart", function(event) {
   $('.tooltipped').tooltip('remove');
});
// filter store to merge apart from parent
$scope.storeFilter= function(itm) {
	if(itm.storeId != $scope.primaryStoreId)
	{
		return itm;
	}
}

// listing of all store in datatable
$scope.storelistingMerge= function() {
	
    $scope.allStores = [];
	$scope.storeMergeArray = [];
	$scope.storeIdsToBeMerged = {};
    var postData = new Object();
    var url = valouxService.getWebServiceUrl("getAllStoreDetailWithUserCount");
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {

        $scope.allStores = data.resData.storeData;
        if($scope.allStores.length == 1){
        	$scope.formError = true;
        	$scope.errorMessage = 'No store available to merge';
        }
        $window.scrollTo(500, 0);
		$timeout(function(){$('.tooltipped').tooltip({delay: 50});angular.element("#primaryStoreId").material_select();angular.element("#storeIdsToBeMerged").material_select(); }, 500);
    }
  },function(){
    console.log("Error in get store data");
  });
    $scope.dtOptionsStoreList = DTOptionsBuilder.newOptions().withOption('paging', false).withOption('responsive', true);
    
     
   
}

// listing of all store in datatable
$scope.storelistingImages= function() {
	sessionStorage.removeItem("storeImageId");
	sessionStorage.removeItem("storeId");
	$('.tooltipped').tooltip('remove');
	var sMsg = sessionStorage.getItem("formSuccess");
	if(sMsg)
	{
		$scope.successMessage	= sessionStorage.getItem("successMessage");
		$scope.formSuccess	= true;
		$scope.formError	= false;
		// remove msg from session
		sessionStorage.removeItem("successMessage");
		sessionStorage.removeItem("formSuccess");
		sessionStorage.removeItem("formError");
	}
	var eMsg = sessionStorage.getItem("formError");
	if(eMsg)
	{
		$scope.successMessage	= sessionStorage.getItem("errorMessage");
		$scope.formSuccess	= false;
		$scope.formError	= true;
		// remove msg from session
		sessionStorage.removeItem("errorMessage");
		sessionStorage.removeItem("formSuccess");
		sessionStorage.removeItem("formError");
	}
    $scope.allStores = [];
    var postData = new Object();
    var url = valouxService.getWebServiceUrl("getAllStoreAdvDetail");
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
        $scope.allStores = data.storeDetail;
    }
  },function(){
    console.log("Error in get store data");
  });
    $scope.dtOptionsStoreListImages = DTOptionsBuilder.newOptions().withPaginationType('full_numbers').withDisplayLength(10).withOption('responsive', true);
    $scope.dtColumnDefsStoreListImages = [DTColumnDefBuilder.newColumnDef(0).notSortable(),DTColumnDefBuilder.newColumnDef(2).notSortable(),DTColumnDefBuilder.newColumnDef(5).notSortable()];
     $timeout(function(){
      $('.tooltipped').tooltip({delay: 50});
      }, 500);
    /*$scope.dtColumnDefs = [
        DTColumnDefBuilder.newColumnDef(0),
        DTColumnDefBuilder.newColumnDef(1).notVisible(),
        DTColumnDefBuilder.newColumnDef(10).notSortable()
    ];*/
}


// listing of all store in datatable
$scope.storelisting= function() {
	var sMsg = sessionStorage.getItem("formSuccess");
	if(sMsg)
	{
		$scope.successMessage	= sessionStorage.getItem("successMessage");
		$scope.formSuccess	= true;
		$scope.formError	= false;
		// remove msg from session
		sessionStorage.removeItem("successMessage");
		sessionStorage.removeItem("formSuccess");
		sessionStorage.removeItem("formError");
	}
	var eMsg = sessionStorage.getItem("formError");
	if(eMsg)
	{
		$scope.successMessage	= sessionStorage.getItem("errorMessage");
		$scope.formSuccess	= false;
		$scope.formError	= true;
		// remove msg from session
		sessionStorage.removeItem("errorMessage");
		sessionStorage.removeItem("formSuccess");
		sessionStorage.removeItem("formError");
	}
    $scope.allStores = [];
    var postData = new Object();
    var url = valouxService.getWebServiceUrl("getAllStoreDetailWithUserCount");
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
        $scope.allStores = data.resData.storeData;
        $window.scrollTo(500, 0);
    }
  },function(){
    console.log("Error in get store data");
  });
    $scope.dtOptionsStoreList = DTOptionsBuilder.newOptions().withPaginationType('full_numbers').withDisplayLength(10).withOption('responsive', true);
    $scope.dtColumnDefsStoreList = [DTColumnDefBuilder.newColumnDef(7).notSortable()];
     $timeout(function(){$('.tooltipped').tooltip({delay: 50});angular.element("#primaryStoreId").material_select();angular.element("#storeIdsToBeMerged").material_select(); }, 500);
   
}

// to edit store image

$scope.editStoreImage= function(storeId,storeImageId) {
	
	sessionStorage.removeItem("storeImageId");
	sessionStorage.removeItem("storeId");
	sessionStorage.setItem("storeImageId", storeImageId);
	sessionStorage.setItem("storeId", storeId)
	$window.location = "ads.html#/edit/"+storeImageId;
	
}

$scope.storeAddEdit= function() {
	
	$scope.storeImageId = sessionStorage.getItem("storeImageId");
	$scope.storeId = sessionStorage.getItem("storeId");
	if($scope.storeImageId)
	{
		// get details of the image and send to view
		
			var postData = new Object();
				postData.reqPaparam = {  
				   "storeAdvertisementId" :$scope.storeImageId
				   
				};
		    var url = valouxService.getWebServiceUrl("getStoreDetailWithUserCount");
		 
			var submitItem = valouxService.getData(url,postData);
			submitItem.then(function(data){
			   if (data.sCode == RESPONSE_CODE.SUCCESS) {
			    $scope.storeId = $scope.storeId;
				$scope.title =  data.resData.storeData.advertisementImageDetail[0].tittle;
				$scope.storeImageDetails = data.resData.storeData.advertisementImageDetail[0];
				console.log($scope.storeImageDetails);
				
			  }else{
				$scope.formSuccess= false;
				$scope.formError = true;
				window.scrollTo(500, 0);
				$scope.errorMessage = data.errorMessage;
			  }
			},function(){
			  console.log("Error in Get store image details");
   			 });
		// clear the session now as we dont want to save it for add image
		//sessionStorage.removeItem("storeImageId");
	}else
	{
		/*get store list not having images*/
		var url = valouxService.getWebServiceUrl("getAllStoreDetailWithNoAdvertisement");
		//post Registration form agent data and get response
		var submitItem = valouxService.getData(url);
		submitItem.then(function(data){
		   if (data.sCode == RESPONSE_CODE.SUCCESS) {
		  // console.log(data);
			$scope.storeListNoImage = data.resData.storeData;
			
			$timeout(function(){  angular.element('.tooltipped').tooltip(); angular.element("#storeId").material_select(); }, 500);
			
		  }else{
			$scope.formSuccess= false;
			$scope.formError = true;
			window.scrollTo(500, 0);
			$scope.errorMessage = data.errorMessage;
		  }
		},function(){
		  console.log("Error in Get without image store list");
		});
	}
	$scope.store = new Object();
	$scope.store.itemImages = [];
	$scope.counterImage = [1]; 
	$scope.noItem = false;
	$scope.stepButton = true;
	$scope.getItemList = false;
	$scope.inValidimg=[];
	$scope.inValidimgText=[];
	
	$scope.isItemImage = false;
	var storeInfo = sessionStorage.getItem("storeInformation");
	

	
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
      $('input.file-path').val('');
      $scope.inValidimg[indId]=false;
      $scope.isItemImage = false
    }
}


$scope.addRemoveItem = function (checked,value) {
	
  if (checked) {
    $scope.storeMergeArray.push(value);
  }
  else {
    var index = $scope.storeMergeArray.indexOf(value);
    $scope.storeMergeArray.splice(index);
  }
}

// function to handle submit request of merge store


$scope.submitMergeStore = function() {
	$scope.$parent.fullPageBlockLoading = true;
	
	// code to check if parent store exists in child store or not
	var length = $scope.storeMergeArray.length;
	var jsonStores = [];
	for(var i = 0; i < length; i++) {
		
		jsonStores.push({'storeId': $scope.storeMergeArray[i]});
		
    }
	if(jsonStores.length>0)
	{
		// code to send request to merge stores
					
					var postData = new Object();
					postData.reqPaparam = {  
					  "primaryStoreId": $scope.primaryStoreId,
					  "storeIdsToBeMerged":jsonStores
					};
				   
					var url = valouxService.getWebServiceUrl("mergeStores");
					var submitItem = valouxService.getData(url,postData);
					submitItem.then(function(data){
						if (data.sCode == RESPONSE_CODE.SUCCESS) {
						   
							 $scope.successMessage =data.successMessage;
							 $scope.formSuccess = true;
							 $scope.formError = false;
							 sessionStorage.setItem("formSuccess",true);
							 sessionStorage.setItem("successMessage",$scope.successMessage);
							 $window.location = "store.html#/"
							 $timeout(function(){angular.element('#showMessage').hide('slow');  
        	}, 2000);  
							 
						  }else
						{
							$scope.errorMessage =data.errorMessage;
							$scope.formError = true;
							$scope.formSuccess = false;
							sessionStorage.setItem("formError",true);
							sessionStorage.setItem("errorMessage",$scope.errorMessage);
							$window.location = "store.html#/"
						}
						 $timeout(function(){
								$('.tooltipped').tooltip({delay: 50});
							 }, 500);
						 //$route.reload();
						   
					},function(){
					  console.log("Error in merging stores.");
					});
	}else
	{
							$scope.errorMessage = "Atleast select one store to merge with parent store.";
							$scope.formError = true;
							$scope.formSuccess = false;
	}
			
}

// to handle form submit request for add/edit advertisement image

$scope.submitAddEditImage = function() {
	$('.tooltipped').tooltip('remove');
	 $scope.$parent.fullPageBlockLoading = true;
 if($scope.isItemImage == false){
    
    var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    var agentInfo = sessionStorage.getItem("agentInformation")
    $scope.globl = new Object(); 
    var postData = new Object();
    
   

    if(sessionStorage.getItem("storeImageId")){ 
		if($scope.store.itemImages.length>0)
		{
			  postData.reqPaparam = {  
			  "userPublicKey":savedData.publicKey,
			   "storeAdvertisementId" :sessionStorage.getItem("storeImageId"),
			   "advImages":$scope.store.itemImages,
			   "action":"update"
			};
		}else
		{
			 $scope.store.itemImages.push({'advImageTitle':$scope.title});
			 postData.reqPaparam = {  
			  "userPublicKey":savedData.publicKey,
			   "storeAdvertisementId" :sessionStorage.getItem("storeImageId"),
			    "advImages":$scope.store.itemImages,
			   "action":"update"
			};
			
		}
    }else
	{
		postData.reqPaparam = {  
		  "userPublicKey":savedData.publicKey,
		   "storeId" :$scope.store.storeId,
		   "advImages":$scope.store.itemImages,
		    "action":"add"
		};
    	
	}

  
        
  
    /*get service url additem*/
    var url = valouxService.getWebServiceUrl("saveOrUpdateStoreAdvInfo");
    //post Registration form agent data and get response
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
       if (data.sCode == RESPONSE_CODE.SUCCESS) {
        
		sessionStorage.setItem("formError", false);
       
		sessionStorage.setItem("formSuccess", true);
       
		sessionStorage.setItem("successMessage", data.successMessage);

      }else{
        sessionStorage.setItem("formError", true);
       
		sessionStorage.setItem("formSuccess", false);
       
		sessionStorage.setItem("errorMessage", data.errorMessage);
      }
	  $route.reload();
  	  $window.location = "ads.html#/"
    },function(){
      console.log("Error in add Image");
    });
}else{
         $scope.formSuccess= false;
         $scope.formError = true;
         $scope.errorMessage = "Please reslove below errors.";
         window.scrollTo(500, 0);
    }
}


// show multipal file upload input
$scope.addMoreImage = function() {
    $scope.counterImage.push($scope.counterImage.length+1);
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
             $scope.store.itemImages.push({'advImageContent' : ItemImageContent,'advImageName':ItemImageName,'advImageTitle':$scope.title});
        };       
        FR.readAsDataURL( imageObj.files[0] );                
    }
}



// add image for store 
$scope.addStoreImage= function(storeId) {
	
	sessionStorage.removeItem("storeInformation");
	sessionStorage.setItem("storeInformation", storeId);
	
	
	$window.location = "ads.html#/add/";
}

//listing of all consumers in datatable
$scope.consumerslisting= function() {
    $scope.allConsumer = [];
    var postData = new Object();
    var url = valouxService.getWebServiceUrl("getAllConsumerDetail");
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
        $scope.allConsumer = data.resData.userDataList;

    }
  },function(){
    console.log("Error in get consumer data");
  });
    $scope.dtOptionsConsumerList = DTOptionsBuilder.newOptions().withPaginationType('full_numbers').withDisplayLength(10).withOption('responsive', true);
    $scope.dtColumnDefsConsumerList = [DTColumnDefBuilder.newColumnDef(4).notSortable()];
     $timeout(function(){
      $('.tooltipped').tooltip({delay: 50});
      }, 500);
   
}

//listing of all consumers in datatable
$scope.agentlisting= function() {
    $scope.allAgentList = [];
    var postData = new Object();
    var url = valouxService.getWebServiceUrl("getAllAgentDetail");
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
        $scope.allAgentList = data.agentList;

    }
  },function(){
    console.log("Error in get agent data");
  });
    $scope.dtOptionsConsumerList = DTOptionsBuilder.newOptions().withPaginationType('full_numbers').withDisplayLength(10).withOption('responsive', true);
    //$scope.dtColumnDefsConsumerList = [DTColumnDefBuilder.newColumnDef(4).notSortable()];
     $timeout(function(){
      $('.tooltipped').tooltip({delay: 50});
      }, 500);
   
}

// listing of all appraisal in datatable
$scope.appraisallisting= function() {
    $scope.allAppraisal = [];
    var postData = new Object();
    var url = valouxService.getWebServiceUrl("getAllAppraisalsList");
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
        $scope.allAppraisal = data.resData.appraisalList;
        $window.scrollTo(500, '');
        angular.element('#showMessage').show('slow');
    }
  },function(){
    console.log("Error in get appraisal data");
  });
    $scope.dtOptionsAppraisalList = DTOptionsBuilder.newOptions().withPaginationType('full_numbers').withDisplayLength(10).withOption('responsive', true);
   
     $timeout(function(){
      $('.tooltipped').tooltip({delay: 50});
      }, 500);
   
}

// function to Delete store images
$scope.deleteStoreImageUpdate= function(storeId,imgId,arryIndex) {
    $('.tooltipped').tooltip('remove');
    var postData = new Object();
    postData.reqPaparam = {  
      "storeId": storeId,
	  "storeAdvertisementId":imgId
    };
   
    var url = valouxService.getWebServiceUrl("deleteAdvImage");
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
        if (data.sCode == RESPONSE_CODE.SUCCESS) {
           
             $scope.successMessage =data.successMessage;
			 $scope.formSuccess = true;
			 $scope.formError = false;
          }else
		{
			$scope.errorMessage =data.errorMessage;
			$scope.formError = true;
			$scope.formSuccess = false;
		}
		 $timeout(function(){
                $('.tooltipped').tooltip({delay: 50});
             }, 500);
		 $route.reload();
		   
    },function(){
      console.log("Error in Delete image of store");
    });
}

// function to Delete store images
$scope.deleteStoreImage= function(storeId,imgId,arryIndex) {
    $('.tooltipped').tooltip('remove');
    var postData = new Object();
    postData.reqPaparam = {  
      "storeId": storeId,
	  "storeAdvertisementId":imgId
    };
   
    var url = valouxService.getWebServiceUrl("deleteAdvImage");
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
        if (data.sCode == RESPONSE_CODE.SUCCESS) {
           
             $scope.successMessage =data.successMessage;
			 $scope.formSuccess = true;
			 $scope.formError = false;
          }else
		{
			$scope.errorMessage =data.errorMessage;
			$scope.formError = true;
			$scope.formSuccess = false;
		}
		 $timeout(function(){
                $('.tooltipped').tooltip({delay: 50});
             }, 500);
		    $route.reload();
  			$window.location = "ads.html#/"
    },function(){
      console.log("Error in Delete image of store");
    });
}
// function to active deactive store images
$scope.storeActivateOrDeactivateImages= function(storeId,storeAdvertisementId,status,arryIndex) {
    $('.tooltipped').tooltip('remove');
    var postData = new Object();
    postData.reqPaparam = {  
      "storeId": storeId,
	  "storeAdvertisementId":storeAdvertisementId,
	  "status":status
    };
   
    var url = valouxService.getWebServiceUrl("activeInactiveStoreAdv");
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
		 if (data) {
           
             $scope.successMessage =data.successMessage;
			 $scope.formSuccess = true;
			 $scope.formError = false;
          }else
		{
			$scope.errorMessage =data.successMessage;
			$scope.formError = true;
			$scope.formSuccess = false;
		}
		 $timeout(function(){
                $('.tooltipped').tooltip({delay: 50});
             }, 500);
		    $route.reload();
  			$window.location = "ads.html#/"
       
    },function(){
      console.log("Error in change status of store image");
    });
}
// funcation to active deactive store 
$scope.storeActivateOrDeactivate= function(storeId,status,arryIndex) {
    $('.tooltipped').tooltip('remove');
    var postData = new Object();
    postData.reqPaparam = {  
      "storeId": storeId 
    };
    if(status){
        postData.reqPaparam.action = "activate";
    }else{
        postData.reqPaparam.action = "inactivate";
    }
    var url = valouxService.getWebServiceUrl("activateOrDeactivateStore");
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
        if (data.sCode == RESPONSE_CODE.SUCCESS) {
            if(status){
                $scope.allStores[arryIndex].status = 'Active';
            }else{
                $scope.allStores[arryIndex].status = 'Inactive';
            }
             $scope.success =1;
             $timeout(function(){
                $('.tooltipped').tooltip({delay: 50});
             }, 500);
             $timeout(function(){
               $scope.success =0;
             }, 4000);
        }
    },function(){
      console.log("Error in change status");
    });
}


// funcation to edit store 
$scope.initstorelistingedit= function() {
	var storeId = $route.current.paparams.storeId;
	if(storeId){
	$scope.storeId = storeId;
	  var postData = new Object();
	   postData.reqPaparam = {  
	     "storeId": storeId 
	   };
	   
	   var url = valouxService.getWebServiceUrl("getStoreDetailById");
	   var submitItem = valouxService.getData(url,postData);
	   submitItem.then(function(data){
	   	if (data.sCode == RESPONSE_CODE.SUCCESS) {
	   
	   	 $scope.storeData.store_name = data.resData.storeData.name;
           
           $scope.storeData.addressLine1 = data.resData.storeData.addressLine1;
           $scope.storeData.addressLine2 = data.resData.storeData.addressLine2;
           $scope.storeData.city = data.resData.storeData.city;
           $scope.storeData.zipCode = data.resData.storeData.zipcode;
           $scope.storeData.storePhoneNumber = data.resData.storeData.phone;
           $scope.storeData.country = data.resData.storeData.countryName;
           $scope.storeData.state = data.resData.storeData.stateName;

	  
   		   
  		$window.scrollTo(500,'')
	  		var addressConcate = '';
		    addressConcate+= data.resData.storeData.addressLine1;
		    if(typeof data.resData.storeData.addressLine2 == 'undefined' || data.resData.storeData.addressLine2 == ''){
		        addressConcate+= '';
		    }
		    else{
		    	addressConcate+= ', '+data.resData.storeData.addressLine2;
		    }
		      
		    addressConcate+= ', '+data.resData.storeData.city;
			addressConcate+= ', '+data.resData.storeData.stateName;
		    addressConcate+= ', '+data.resData.storeData.countryName;
			$scope.storeData.store_location  = addressConcate;
		}
	},function(){
		console.log("Error in store edit");
	});
}else{
	$window.location = "store.html#/";
}
}

// funcation to edit store 
$scope.storeUpdate= function() {

	var postData = new Object();
    postData.reqPaparam = {  
      "storeData": {
      	"storeId" : $scope.storeId,
      	"storeName" : $scope.storeData.store_name,
      	"phone" : $scope.storeData.storePhoneNumber,
      	"storeAddress" : {
      		"addressLine1" : $scope.storeData.addressLine1,
      		"addressLine2" : $scope.storeData.addressLine2,
      		"country" : $scope.storeData.country,
      		"state" : $scope.storeData.state,
      		"zipCode" : $scope.storeData.zipCode,
      		"city" : $scope.storeData.city,
      	}
      }
    };
    
    var url = valouxService.getWebServiceUrl("updateStoreDetailById");
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
        if (data.sCode == RESPONSE_CODE.SUCCESS) {
        	sessionStorage.setItem("formError", false);
        	sessionStorage.setItem("formSuccess", true);
        	sessionStorage.setItem("successMessage", data.successMessage);
        	$window.location = 'store.html#/';
        	$timeout(function(){
        		angular.element('#showMessage').fadeOut('slow');  
        		sessionStorage.removeItem("formError");
	        	sessionStorage.removeItem("formSuccess");
	        	sessionStorage.removeItem("successMessage");

        	}, 2000);                   
        }else{
        	$scope.formError = true;
        	$scope.errorMessage = data.errorMessage;
        }
    },function(){
      console.log("Error in update store for admin");
    });
}

// Click to unappraise appraisal
$scope.clickToUnappraiseConfirm= function(appraisalId, userPublicKey) {
	
	angular.element('#confirmPopup').openModal();
	$scope.appraisalId = appraisalId;
	$scope.userPublicKey = userPublicKey;
}

// Click to unappraise appraisal
$scope.clickToUnappraise= function() {
	var postData = new Object();
    postData.reqPaparam = {  
      "userPublicKey": $scope.userPublicKey,
      "appraisalId" : $scope.appraisalId
    };
    
    var url = valouxService.getWebServiceUrl("changeStatusToUnAppraised");
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
        if (data.sCode == RESPONSE_CODE.SUCCESS) {
        	$scope.appraisallisting();
        	$scope.successMessage = data.successMessage;
        	$timeout(function(){angular.element('#showMessage').hide('slow');  
        	}, 2000);          
        }else{
        	$scope.formError = true;
        	$scope.errorMessage = data.errorMessage;
        }
    },function(){
      console.log("Error in to un-appraise appraisal");
    });
}


// function to active inactive agent 
$scope.agentActiveInactive= function(storeId,status,agentkey,arryIndex) {
  angular.element('#successMessage').show();
  var postData = new Object();
  if(status == 1){
    postData.reqPaparam = { 
  	  "userPublicKey" : agentkey,
  	  "status":2, 
  	} 
  }else{
  	postData.reqPaparam = { 
  		"userPublicKey" : agentkey,
  		"status":1,
  	}
  }
    
  var agentdata=new Object();
  var url = valouxService.getWebServiceUrl("activeInactiveAgent");
  //post data and get response
  var submitAgent = valouxService.getData(url,postData);
  submitAgent.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) { 
    	angular.element('.tooltipped').tooltip('remove');    	
    	if(status == 1){
    		$scope.allAgentList[arryIndex].agentStatus = 2;
    		$scope.success = "Agent inactivated successfully"
    	}else{
    		$scope.allAgentList[arryIndex].agentStatus = 1;
    		$scope.success = "Agent activated successfully"
    	}
    $timeout(function(){ angular.element('#successMessage').hide('slow'); }, 1500);

    }
    $timeout(function(){ angular.element('.tooltipped').tooltip(); }, 500);
  },function(){
    console.log("Error in get agent list");
  });
}
}]);


          