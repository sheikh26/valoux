var baseController = angular.module('valouxApp.controllers', []).
controller('BaseController',['$scope', 'valouxService', 'masterDataService', '$location', 'RESPONSE_CODE', 'ROLE_TYPE', '$timeout', '$window','$controller',function($scope, valouxService, masterDataService, $location, RESPONSE_CODE, ROLE_TYPE,$timeout, $window,$controller){

$scope.isAgentLogin = false;
$scope.isSuccess = false;
$scope.fullPageBlockLoading = true;
$scope.allCountries = [];

$scope.parseFloat = function(value)
{
    return parseFloat(value);
}
 // check user are logged in or not
$scope.checkIsloggedIn = function () {
    var flag = false;
    var sItem = sessionStorage.getItem("saveValouxData");
    if( sItem ){
      flag = true;
    }
    return flag;
 }

// open login popup
$scope.loginPopup = function () {
  angular.element('#loginModal').openModal();
  $scope.user.emailId = '';
  $scope.user.password = '';
  $scope.loginFormError = false;
  $scope.loginForm.$setPristine();
  angular.element("#emailAddress").removeClass('active');
  angular.element("#passwordField").removeClass('active');
}

// function for footer terms 
$scope.termsOfUseModal = function () {
  angular.element('#terms-of-use-modal').openModal();
  
}
// function for footer privacy 
$scope.privacyModal = function () {
  angular.element('#privacy-modal').openModal();
  
}

// Manage links in index page session based
$scope.sessionAgentConsumer = function () {
  var sItem = sessionStorage.getItem("saveValouxData");                
  if( sItem ){
    var savedData = JSON.parse(sItem);
    /*Check session agent is login*/
    if(savedData.roleType == ROLE_TYPE.AGENT){
      $scope.isAgent = true;
    }else{
      $scope.isConsumer = true;
    }
  }
}

/*Logout function define*/
$scope.logout = function() {
    var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    /*Check session agent is login*/
    if(savedData.roleType == ROLE_TYPE.SUPERADMIN){
	sessionStorage.removeItem("saveValouxData");
	window.location = "../index.html";
    }else{
        sessionStorage.removeItem("saveValouxData");
	window.location = "index.html";
    }
}

/* to show selected shared menu on shared with me page */

$scope.showSelected = function()
{
		if($scope.showSelectedSharedWithMe)
		{
				$scope.showSelectedSharedWithMe = false;
		}else
		{
				$scope.showSelectedSharedWithMe = true;
		}
}

/*Activate user on click email link*/
$scope.emailVparamication = function () {
  var tokenObj = $location.search().ecptycode;
  
    var url = valouxService.getWebServiceUrl("activateUser");
    var postEmailVparamicationData = new Object();  
    postEmailVparamicationData.reqPaparam = {
      "token" : tokenObj,
      "otp" : $scope.user.otpCode,
  };
    var promiseCheckEmail = valouxService.getData(url,postEmailVparamicationData);
    promiseCheckEmail.then(function(data){
      if(data.sCode == RESPONSE_CODE.SUCCESS){
       $scope.isSuccess = true;
       $scope.isError = false;
       $scope.isResendOtp = false
       window.scrollTo(500, 0);
      }
      else{
        window.scrollTo(500, 0);
        $scope.isError = true;
        $scope.isResendOtp = false
        $scope.isSuccess = false;
        $scope.emailVparamicationText = data.errorMessage; 
      }
    },function(){
      $scope.isError = true;
      $scope.isSuccess = false;
      $scope.isResendOtp = false
      $scope.emailVparamicationText  = "Invalid Link !! Please try again or contact your system Administrator";
    });
  
}  

/*Resend otp email verification*/
$scope.resendOtp = function() {

var tokenObj = $location.search().ecptycode;
var url = valouxService.getWebServiceUrl("resendOTP");
    var postEmailVparamicationData = new Object();  
    postEmailVparamicationData.reqPaparam = {
      "authLoginCode" : tokenObj,
  };
    var promiseCheckEmail = valouxService.getData(url,postEmailVparamicationData);
    promiseCheckEmail.then(function(data){
      if(data.sCode == RESPONSE_CODE.SUCCESS){
        $scope.isError = false;
        $scope.isResendOtp = true
        $scope.isSuccess = false;
        window.scrollTo(500, 0);
        $scope.emailVparamicationText = 'Successfully resend OTP to your mobile number.';
      }
      else{
        window.scrollTo(500, 0);
        $scope.isError = true;
        $scope.isResendOtp = false
        $scope.isSuccess = false;
        $scope.emailVparamicationText = 'Invalid request !! Please try or contact your system Administrator'; 
      }
    },function(){
      $scope.isError = true;
      $scope.isSuccess = false;
      $scope.isResendOtp = false
      $scope.emailVparamicationText  = "Invalid Link !! Please try or contact your system Administrator";
    });

};

/* logged user name */
$scope.loggedUsername = function() {
    var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    valouxService.checkSession();
    $scope.firstName = savedData.resData.loginInfo.firstName;
    $scope.lastName = savedData.resData.loginInfo.lastName;
    $scope.userRoleType = savedData.roleType;
    $scope.userRole = savedData.role;
    $scope.roleType = ROLE_TYPE;
    /*Check session agent is login*/
    if(savedData.roleType == ROLE_TYPE.AGENT){
      $scope.isAgentLogin = true;
      if(savedData.role==1)
      {
        $scope.agentowner = true;
      }
      if(savedData.role==5)
      {
        $scope.isAppraiserLogin=true;
      }
      var postData = new Object();
      postData.reqPaparam = { "userPublicKey": savedData.memberShipKey};
  
  var url = valouxService.getWebServiceUrl("sharedRequestCount");
  //post request to get shared with me data response
  var submitSharedWithMe = valouxService.getData(url,postData);
  submitSharedWithMe.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
     if(typeof data.resData == "undefined" ){
        $scope.RequestCount = 0;
 
      }else{
           $scope.RequestCount=data.resData.sharedRequestCount;
       }
      }else{
      $scope.errorMessage = data.errorMessage;
    }
  },function(){
    console.log("Error in shared with Me listing");
  });

      
    }
};

$scope.checkIsAgent = function() {
    var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    /*Check session agent is login*/
    if(savedData.roleType != ROLE_TYPE.AGENT){
        window.location = "index.html";
    }
};

$scope.checkIsConsumer = function() {
    var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    /*Check session agent is login*/
    if(savedData.roleType != ROLE_TYPE.CONSUMER){
        window.location = "index.html";
    }
};

// start autosuggest add or existing store auto fill location
// get all store for show in autosuggest 
$scope.getStore = function() {

  /*After refresh clear uploaded signature */
  delete $scope.signatureImage;
  delete $scope.signatureImageName;
  angular.element(document.getElementById('fileSignature')).val('');

  $scope.stores = [];
  var postData = new Object();
  
  /*get service url userRegistration*/
  var url = valouxService.getWebServiceUrl("getAllStore");
  //post Registration form consumer data and get response
  var storeDate = valouxService.getData(url,postData);
  storeDate.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
       var resp = data.resData.storeData;
       var newArry = [];
       for(var i=0; i<resp.length;i++){
        var addressConcate = '';
          addressConcate+= resp[i].storeAddress.addressLine1;
          if(typeof resp[i].storeAddress.addressLine2 == 'undefined' || resp[i].storeAddress.addressLine2 == ''){
            addressConcate+= '';
          }
          else{
            addressConcate+= ', '+resp[i].storeAddress.addressLine2;
          }
          
          addressConcate+= ', '+resp[i].storeAddress.city;
          addressConcate+= ', '+resp[i].storeAddress.state;
          addressConcate+= ', '+resp[i].storeAddress.country;
          addressConcate+= ', '+resp[i].storeAddress.zipCode;
          var obj = {
               storeId : resp[i].storeId,
               storeName : resp[i].storeName,
               storeAddress : resp[i].storeAddress,
               storePhone : resp[i].phone,
               gaddress: addressConcate
           }
           newArry.push(obj)
           
       }
     $scope.stores = newArry;
     
     
    }
  },function(){
    console.log("Error in checking Registration form consumer");
  });
  
}

// store autocomplete addad add new option 
$scope.setAddnewOption = function(str) {
      var matches = [{
               storeId : 0,
               storeName : 'Add new store',
               storeAddress : '',
               storePhone : '',
               gaddress: ''
           }];
      $scope.stores.forEach(function(store) {
        var fullName = store.firstName + ' ' + store.surname;
        if ((store.storeName.toLowerCase().indexOf(str.toString().toLowerCase()) >= 0)) {
          matches.push(store);
        }
      });
      return matches;
    };


$scope.required =false;
$scope.storeData = {
    storeId:0,
    store_name: '',
    storePhoneNumber:'',
    addressLine1:'',
    addressLine2:'',
    city:'',
    state:'',
    country:'',
    zipCode:''
    
};
// fuction call when store autosuggest selected
$scope.storeSelected = function(selected) {
      if (selected) {
          if(selected.originalObject.storeId!=0){
            $scope.storeData.store_name =  selected.originalObject.storeName;
            $scope.storeData.storePhoneNumber =  selected.originalObject.storePhone;
            $scope.storeData.store_location =  selected.originalObject.gaddress;
            $scope.storeData.storeId =  selected.originalObject.storeId;
            $scope.storeData.addressLine1 =  selected.originalObject.storeAddress.addressLine1;
            $scope.storeData.addressLine2 =  selected.originalObject.storeAddress.addressLine2;
            $scope.storeData.city =  selected.originalObject.storeAddress.city;
            $scope.storeData.state =  selected.originalObject.storeAddress.state;
            $scope.storeData.country =  selected.originalObject.storeAddress.country;
            $scope.storeData.zipCode =  selected.originalObject.storeAddress.zipCode;
            $('#gray-address1 input').addClass('active');
            $('#gray-address1 input').next('label').addClass('active');
          $scope.required =false;
            if($('#map').length){
              geocoder.geocode( {address:$scope.storeData.store_location}, function(results, status) 
              {
                if (status == google.maps.GeocoderStatus.OK) 
                {
                  map.setCenter(results[0].geometry.location);//center the map over the result
                  //place a marker at the location
                  var marker = new google.maps.Marker(
                  {
                      map: map,
                      position: results[0].geometry.location
                  });
                  map.setZoom(12);
                } else {
                  
               }
              });
            }
         }else{
            $scope.storeData.store_name =  "";
            $scope.storeData.store_location =  "";
            $scope.storeData.storePhoneNumber = "";
            $scope.storeData.storeId = 0;
            $scope.storeData.addressLine1 =  "";
            $scope.storeData.addressLine2 =  "";
            $scope.storeData.city =  "";
            $scope.storeData.state =  "";
            $scope.storeData.country =  "";
            $scope.storeData.zipCode = "";
            $scope.required =true;
          $('#gray-address1 input:not(#location)').removeClass('active');
          $('#gray-address1 input:not(#location)').next('label').removeClass('active');
      
         }
      
      } else {
            
      }
    };
    
 
$scope.clearStore = function() {
    $scope.$broadcast('angucomplete-alt:clearInput');
    $scope.storeData.store_name =  "";
    $scope.storeData.store_location =  "";
    $scope.storeData.storePhoneNumber = "";
    $scope.storeData.storeId = 0;
    $scope.storeData.addressLine1 =  "";
    $scope.storeData.addressLine2 =  "";
    $scope.storeData.city =  "";
    $scope.storeData.state =  "";
    $scope.storeData.country =  "";
    $scope.storeData.zipCode = "";
    $scope.required =true;
    $('#gray-address1 input:not(#location)').removeClass('active');
    $('#gray-address1 input:not(#location)').next('label').removeClass('active');
}
// function for check is address is chenged or not
$scope.isGoogleAddress = function() {
   var addObj = {};
    if($scope.addressLine1){
         addObj.addressLine1 = $scope.storeData.addressLine1;
    }
    if($scope.city){
         addObj.city = $scope.storeData.city;
    }
    if($scope.state){
         addObj.state = $scope.storeData.state;
    }
    if($scope.country){
         addObj.country = $scope.storeData.country;
    }
    if($scope.zipCode){
         addObj.zipCode = $scope.storeData.zipCode;
    }
    
    if(angular.equals($scope.fullAddress, addObj)){
        $("#location").removeClass('location_disable');
        $("#location-input").removeClass('location_disable');
    }
    else{
        $("#location").addClass('location_disable');
        $("#location-input").addClass('location_disable');
    }
           
}
// end autosuggest add or existing store auto fill location

// address concate with comma
$scope.concateAddress = function(addressobj) {
    var str = "";
    if(typeof addressobj.addressLine1!='undefined' && addressobj.addressLine1!=""){
        if(str!=""){
            str += ", ";
        }
        str += addressobj.addressLine1; 
        
    }
    if(typeof addressobj.addressLine2!='undefined' && addressobj.addressLine2!=""){
        str += ", "+addressobj.addressLine2; 
    }
    if(typeof addressobj.city!='undefined' && addressobj.city!=""){
        str += ", "+addressobj.city; 
    }
    if(typeof addressobj.state!='undefined' && addressobj.state!=""){
        str += ", "+addressobj.state; 
    }
    if(typeof addressobj.country!='undefined' && addressobj.country!=""){
        str += ", "+addressobj.country; 
    }
    if(typeof addressobj.zipCode!='undefined' && addressobj.zipCode!=""){
        str += ", "+addressobj.zipCode; 
    }
    return str;
}


$scope.getAllcountries = function() {
    var postData = new Object();
    var url = valouxService.getWebServiceUrl("getAllCountryDetails");
    var allcountry = valouxService.getData(url,postData);
    allcountry.then(function(data){
      if (data.sCode == RESPONSE_CODE.SUCCESS) {
           $scope.allCountries=data.resData.countryList;
           
           }else{
      $scope.errorMessage = data.errorMessage;
    }
  },function(){
    console.log("Error in get countries");
  });

      
}

$scope.propertyItem = new Object();
$scope.propertyAttribute = new Object();
$scope.propertyTab = new Object();

$scope.openPropertyPopup= function(itemId,itemName,compObj) {
       var componentId  = compObj.itemComponentId;
       var componentType = compObj.componentType;
       var componentQuantity = compObj.componentQuantity;

            $scope.diamondForm.$setPristine();
            $scope.gemstoneForm.$setPristine();
            $scope.pearlForm.$setPristine();
            $scope.metalForm.$setPristine();
            
            $('input.file-path').val('');
            
            $scope.itemName = itemName;
            $scope.propertyAttribute = {};
            $scope.propertyAttribute.componentCertificate = {};
            $scope.propertyAttribute.shape = 1;
            $scope.propertyAttribute.showRoundShape = 0;
            $scope.propertyAttribute.placement = 1;
            
            //pearl
            $scope.propertyAttribute.composition = 1;
            $scope.propertyAttribute.enhancements = 1;
            $scope.propertyAttribute.drilled = 1;
            
            $scope.propertyItem.itemId = itemId;
            $scope.propertyItem.itemComponentId = componentId;
            $scope.propertyItem.componentType = componentType;
            $scope.propertyItem.componentQuantity = componentQuantity;
            $scope.propertyItem.componentName = compObj.componentName;
            
            $scope.propertyItem.imgCounter = [1];
            $scope.propertyItem.receiptCounter = [1];
            $scope.propertyItem.certificateCounter = [1];
            
            $scope.propertyItem.propertyImages =[];
            $scope.propertyItem.propertyImageType =1;
            
            $scope.propertyFormError = false;
            $scope.propertyFormSuccess= false;
            
            $scope.inValidimgProperty = [];
            $scope.inValidimgPropertyText =  [];
            $scope.inValidimgReceipt =  [];
            $scope.inValidimgReceiptText =  [];
            $scope.inValidimgCertificate =  [];
            $scope.inValidimgCertificateText =  [];
            
            
            var postData = new Object();
            postData.reqPaparam = {
              "publicKey":$scope.user,
              "itemId":itemId,
              "itemComponentId":componentId,
              "componentType":componentType,
            };
            
            var savedItem = sessionStorage.getItem("saveValouxData");
            var savedData = JSON.parse(savedItem);
            var savedItemAgent = sessionStorage.getItem("agentInformation");
            if(savedItemAgent){
                 postData.reqPaparam.userPublicKey = savedItemAgent;
            }else{
                 postData.reqPaparam.userPublicKey = savedData.memberShipKey;
            }
            /*get service url user Login*/
            var url = valouxService.getWebServiceUrl("getItemComponentProperty");
            //post login data and get response
            var propertyData = valouxService.getData(url,postData);
            propertyData.then(function(data){
                if (data.sCode == RESPONSE_CODE.SUCCESS) {
                    var resp = data.resData.componentProperty; 
                    $scope.propertyAttribute = resp;
                    if($scope.propertyAttribute.shape){
                         $scope.changePropertyShapeDaimond();
                    }
                    
                    if($scope.propertyAttribute.componentCertificate && $scope.propertyAttribute.componentCertificate.lab  && $scope.propertyAttribute.componentCertificate.lab==3){
                         setGAIReadOnly(true);
                    }else{
                        setGAIReadOnly(false);
                    }
                    
                    
                    if(!$scope.propertyAttribute.placement){
                        $scope.propertyAttribute.placement = 1;
                    }
                    if(!$scope.propertyAttribute.composition){
                        $scope.propertyAttribute.composition = 1;
                    }
                    if(!$scope.propertyAttribute.enhancements){
                        $scope.propertyAttribute.enhancements = 1;
                    }
                    if(!$scope.propertyAttribute.drilled){
                        $scope.propertyAttribute.drilled = 1;
                    }
                    
                    if(!$scope.propertyAttribute.priceProperty.marketValue){
                        $scope.propertyAttribute.priceProperty.marketValue = 0;
                    }

                    if(!$scope.propertyAttribute.priceProperty.finalPrice){
                        $scope.propertyAttribute.priceProperty.finalPrice = 0;
                    }
                    
                    if(!$scope.propertyAttribute.priceProperty.generalPriceAdjustmentOperator){
                        $scope.propertyAttribute.priceProperty.generalPriceAdjustmentOperator = 1;
                    }
                    
                    if($scope.propertyAttribute.priceProperty.generalPriceAdjustmentValue){
                         if($scope.propertyAttribute.priceProperty.generalPriceAdjustmentOperator==1){
                             $scope.propertyAttribute.priceProperty.generalPriceOperatorValue = '+'+$scope.propertyAttribute.priceProperty.generalPriceAdjustmentValue;
                         }else if($scope.propertyAttribute.priceProperty.generalPriceAdjustmentOperator==2){
                             $scope.propertyAttribute.priceProperty.generalPriceOperatorValue = '-'+$scope.propertyAttribute.priceProperty.generalPriceAdjustmentValue;
                         }
                    }
                    
                    if(!$scope.propertyAttribute.priceProperty.generalPriceAdjustmentType){
                        $scope.propertyAttribute.priceProperty.generalPriceAdjustmentType = 1;
                    }
                    
                    if(!$scope.propertyAttribute.priceProperty.brandPriceAdjustmentOperator){
                        $scope.propertyAttribute.priceProperty.brandPriceAdjustmentOperator = 1;
                    }
                    
                    if($scope.propertyAttribute.priceProperty.brandPriceAdjustmentValue){
                         if($scope.propertyAttribute.priceProperty.brandPriceAdjustmentOperator==1){
                             $scope.propertyAttribute.priceProperty.brandPriceOperatorValue = '+'+$scope.propertyAttribute.priceProperty.brandPriceAdjustmentValue;
                         }else if($scope.propertyAttribute.priceProperty.brandPriceAdjustmentOperator==2){
                             $scope.propertyAttribute.priceProperty.brandPriceOperatorValue = '-'+$scope.propertyAttribute.priceProperty.brandPriceAdjustmentValue;
                         }
                    }
                    
                    if(!$scope.propertyAttribute.priceProperty.brandPriceAdjustmentType){
                        $scope.propertyAttribute.priceProperty.brandPriceAdjustmentType = 1;
                    }
                    
                    
                  
                        $timeout(function(){
                            angular.element("select").material_select();
                            angular.element('.tooltipped').tooltip();
                        }, 500);
                        
                if(componentType==1){ 
                        $('#daimondPopup').show(0,function(){ 
                        $(this).addClass('show-MatModal');
                        setTimeout(function(){
                            $('#daimondPopup').addClass('seeModal');
                            $('body').css('overflow','hidden');
                        },250);
                    });
                    
                    $scope.getDiamondSpecifyPrice();
                }
                if(componentType==2){
                    $('#gemstonePopup').show(0,function(){ 
                        $(this).addClass('show-MatModal');
                        setTimeout(function(){
                            $('#gemstonePopup').addClass('seeModal');
                            $('body').css('overflow','hidden');
                        },250);
                    });
                }
                if(componentType==3){
                    $('#pearlPopup').show(0,function(){ 
                        $(this).addClass('show-MatModal');
                        setTimeout(function(){
                            $('#pearlPopup').addClass('seeModal');
                            $('body').css('overflow','hidden');
                        },250);
                    });
                }
                if(componentType==4){
                    $('#metalPopup').show(0,function(){ 
                        $(this).addClass('show-MatModal');
                        setTimeout(function(){
                            $('#metalPopup').addClass('seeModal');
                            $('body').css('overflow','hidden');
                        },250);
                    });
                    $scope.getMetalSpecifyPrice();
                }
            }
                
            },function(){
              console.log("Error in checking login");
            });
            
                

        }

$scope.closePropertyPopup= function() {
        $scope.propertyTab.showProperty = 1;
        $scope.propertyTab.showImages = 0;
        $scope.propertyTab.showReceipt = 0;
        $scope.propertyTab.showCertificate = 0;
        $scope.propertyItem.propertyImageType =1;
        $('#daimondPopup,#gemstonePopup,#pearlPopup,#metalPopup').removeClass('seeModal show-MatModal');
        setTimeout(function(){
            $('#daimondPopup,#gemstonePopup,#pearlPopup,#metalPopup').hide(0);
            $('body').css('overflow','auto');
            var storeMangCtrl = $controller('storeManagement',{$scope: $scope}); // passing current scope to commmon controller
            storeMangCtrl.reloadPage();
        },500);
}

$scope.initDaimondProperty= function() {
    $scope.propertyAttribute.componentCertificate = new Object();
    $scope.propertyTab.showProperty = 1;
    $scope.propertyTab.showImages = 0;
    $scope.propertyTab.showReceipt = 0;
    $scope.propertyTab.showCertificate = 0;
    $scope.componentShape = masterDataService.componentShape();
    $scope.propertyAttribute.shape = $scope.componentShape[0].value;
    $scope.propertyAttribute.priceProperty = new Object();
    $scope.propertyAttribute.priceProperty.generalPriceAdjustmentOperator = 1;
    $scope.propertyAttribute.priceProperty.generalPriceAdjustmentType = 1;
    $scope.propertyAttribute.priceProperty.brandPriceAdjustmentOperator = 1;
    $scope.propertyAttribute.priceProperty.brandPriceAdjustmentType = 1;
    
    $scope.diamondShapes = masterDataService.diamondShapes();
    $scope.certificateType = masterDataService.certificateType();
    $scope.clarityType = masterDataService.clarityType();
    $scope.diamondColor = masterDataService.diamondColor();
    $scope.diamondQualityType = masterDataService.qualityType();
    $scope.diamondCutlet = masterDataService.diamondCutlet();
    $scope.fancyColor = masterDataService.fancyColor();
    $scope.fluorescence = masterDataService.fluorescence();
    $scope.girdleThickness = masterDataService.girdleThickness();
    $scope.secondaryHue = masterDataService.secondaryHue();
    $scope.thickness = masterDataService.thickness();
    $scope.weightMeasure = masterDataService.weightMeasure();
    
    $scope.gemstoneType = masterDataService.gemstoneType();
    $scope.gemstoneCuts = masterDataService.gemstoneCuts();
    $scope.enhancement = masterDataService.enhancement();
    $scope.externalInclusions = masterDataService.externalInclusions();
    $scope.internalInclusions = masterDataService.internalInclusions();
    
    $scope.purityType = masterDataService.purityType();
    $scope.metalType = masterDataService.metalType();
    $scope.metalColor = masterDataService.metalColor();
    $scope.typeDetermined = masterDataService.typeDetermined();
    
    
    $scope.blemish = masterDataService.blemish();
    $scope.pearlColors = masterDataService.pearlColor();
    $scope.pearlType = masterDataService.pearlType();
    $scope.pearlsStyle = masterDataService.pearlsStyle();
    
    
    $scope.masterAdjustmentOperator = masterDataService.priceAdjustmentOperator();
    $scope.masterAdjustmentType = masterDataService.priceAdjustmentType();
    
    
    $scope.getAllcountries();
    
    $scope.readOnlyProperty = {
        girdleThicknessDescription : false,
        symmetry : false,
        clarityId : false,
        cutlet : false,
        fluorescence : false,
        depth : false,
        depthPercentage : false,
        diamondLength : false,
        diamondWidth : false,
        polish : false,
        
    }

    $timeout(function(){
        angular.element('.tooltipped').tooltip();
       // angular.element("select").material_select();
    }, 500);
}

$scope.showPropertyTab= function(tabNo) {
    $scope.propertyFormError = false;
    $scope.propertyFormSuccess= false;
    $scope.propertyTab.showProperty = 0;
    $scope.propertyTab.showImages = 0;
    $scope.propertyTab.showReceipt = 0;
    $scope.propertyTab.showCertificate = 0;
    if(tabNo==1){
       $scope.propertyTab.showProperty =1;
       $scope.propertyItem.propertyImageType =1;
    }
    if(tabNo==2){
       $scope.propertyTab.showImages =1; 
       $scope.propertyItem.propertyImageType =1;
    }
    if(tabNo==3){
       $scope.propertyTab.showReceipt =1; 
       $scope.propertyItem.propertyImageType =2;
    }
    if(tabNo==4){
       $scope.propertyTab.showCertificate =1; 
       $scope.propertyItem.propertyImageType =3;
    }
}

$scope.changePropertyShape= function(shapeVal) {
    $scope.propertyAttribute.shape = shapeVal;
}

$scope.changePropertyShapeDaimond= function() {
    $scope.propertyAttribute.showRoundShape = 0;
    var shpArry = masterDataService.diamondShapes();

    for (var i=0; i < shpArry.length; i++){
        if (shpArry[i]['value'] == $scope.propertyAttribute.shape && shpArry[i]['shapeType']=='round'){
           $scope.propertyAttribute.showRoundShape = 1;
           break;
        }
    }
    $scope.getDiamondSpecifyPrice();
}

$scope.addmorePropertyUploader= function(imageType) {
    if(imageType==1){
        $scope.propertyItem.imgCounter.push($scope.propertyItem.imgCounter.length+1);
    }
    if(imageType==2){
        $scope.propertyItem.receiptCounter.push($scope.propertyItem.receiptCounter.length+1);
    }
    if(imageType==3){
        $scope.propertyItem.certificateCounter.push($scope.propertyItem.certificateCounter.length+1);
    }
}

$scope.removeComponentImageuploader = function(indId,fileType) {
    if(indId){
        $scope.propertyItem.imgCounter.splice(indId,1);
        $scope.propertyItem.propertyImages.splice(indId,1);
        $scope.inValidimgProperty[indId] = false;
    }else{
        $scope.propertyItem.propertyImages.splice(indId,1);
        if(fileType==1){
            $('.daimondImageuploder'+$scope.propertyItem.itemComponentId+'_'+indId).parent().parent().find('input.file-path').val('');
            $scope.inValidimgProperty[indId] = false;
        }
        else if(fileType==2){
            $('.receiptUploder'+$scope.propertyItem.itemComponentId+'_'+indId).parent().parent().find('input.file-path').val('');
            $scope.inValidimgReceipt[indId] = false;
        }
        else if(fileType==3){
            $('.certificateUploder'+$scope.propertyItem.itemComponentId+'_'+indId).parent().parent().find('input.file-path').val('');
            $scope.inValidimgCertificate[indId] = false;
        }
      
    }
}

$scope.addupdateItemComponentProperties= function() {
    $scope.fullPageBlockLoading = true;
    
    if($scope.propertyAttribute.priceProperty.generalPriceOperatorValue){
        var generalPriceAdjustmentValue = $scope.propertyAttribute.priceProperty.generalPriceOperatorValue; 
        if(generalPriceAdjustmentValue.indexOf("-")>-1){
            $scope.propertyAttribute.priceProperty.generalPriceAdjustmentOperator = '2';
            generalPriceAdjustmentValue = generalPriceAdjustmentValue.replace(/[^0-9.]/g, '');
           // generalPriceAdjustmentValue = parseInt(generalPriceAdjustmentValue);
            $scope.propertyAttribute.priceProperty.generalPriceAdjustmentValue = generalPriceAdjustmentValue;
        }else if(generalPriceAdjustmentValue.indexOf("+")>-1){
            $scope.propertyAttribute.priceProperty.generalPriceAdjustmentOperator = '1';
            generalPriceAdjustmentValue = generalPriceAdjustmentValue.replace(/[^0-9.]/g, '');
           // generalPriceAdjustmentValue = parseInt(generalPriceAdjustmentValue);
            $scope.propertyAttribute.priceProperty.generalPriceAdjustmentValue = generalPriceAdjustmentValue;
        }else{
            $scope.propertyAttribute.priceProperty.generalPriceAdjustmentValue = generalPriceAdjustmentValue;
        }
    }else{
       $scope.propertyAttribute.priceProperty.generalPriceAdjustmentOperator = '1'; 
       $scope.propertyAttribute.priceProperty.generalPriceAdjustmentValue = 0;
    }
    
    
    if($scope.propertyAttribute.priceProperty.brandPriceOperatorValue){
        var brandPriceAdjustmentValue = $scope.propertyAttribute.priceProperty.brandPriceOperatorValue;
        if(brandPriceAdjustmentValue.indexOf("-")>-1){
            $scope.propertyAttribute.priceProperty.brandPriceAdjustmentOperator = '2';
            brandPriceAdjustmentValue = brandPriceAdjustmentValue.replace(/[^0-9.]/g, '');
          //  brandPriceAdjustmentValue = parseInt(brandPriceAdjustmentValue);
            $scope.propertyAttribute.priceProperty.brandPriceAdjustmentValue = brandPriceAdjustmentValue;
        }else if(brandPriceAdjustmentValue.indexOf("+")>-1){
            $scope.propertyAttribute.priceProperty.brandPriceAdjustmentOperator = '1';
            brandPriceAdjustmentValue = brandPriceAdjustmentValue.replace(/[^0-9.]/g, '');
         //   brandPriceAdjustmentValue = parseInt(brandPriceAdjustmentValue);
            $scope.propertyAttribute.priceProperty.brandPriceAdjustmentValue = brandPriceAdjustmentValue;
        }
        else{
             $scope.propertyAttribute.priceProperty.brandPriceAdjustmentValue = brandPriceAdjustmentValue;
        }
    }else{
       $scope.propertyAttribute.priceProperty.brandPriceAdjustmentOperator = '1'; 
       $scope.propertyAttribute.priceProperty.brandPriceAdjustmentValue = 0;
    }
    
    var postData = new Object();
    postData.reqPaparam = { 
      "itemId":$scope.propertyItem.itemId, 
      "itemComponentId":$scope.propertyItem.itemComponentId,
      "componentType":$scope.propertyItem.componentType,
      "componentProperty":$scope.propertyAttribute
    };
    
    postData.reqPaparam.requestType =  "add";
 
    var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    var savedItemAgent = sessionStorage.getItem("agentInformation");
    if(savedItemAgent){
         postData.reqPaparam.userPublicKey = savedItemAgent;
    }else{
         postData.reqPaparam.userPublicKey = savedData.memberShipKey;
    }
    /*get service url additem*/
    var url = valouxService.getWebServiceUrl("addUpdateItemComponentProperties");
    //post Registration form agent data and get response
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
        if($scope.propertyItem.componentType ==1 && $scope.propertyAttribute.componentCertificate.lab==3 && $scope.propertyAttribute.componentCertificate.certificateNumber!="" && $scope.propertyAttribute.singleWeight){
            saveGiaImage();
        }
      $('.MatModal-content').scrollTop(0);
       if (data.sCode == RESPONSE_CODE.SUCCESS) {
        $scope.getItemPriceProperties($scope.propertyItem.itemId);
        $scope.propertyFormError = false;
        $scope.propertyFormSuccess= true;
        $scope.propertySuccessMessage = data.successMessage;
      }else{
        $scope.propertyFormSuccess= false;
        $scope.propertyFormError = true;
        $scope.propertyErrorMessage = data.errorMessage;

      }
      $scope.fullPageBlockLoading = false;
    },function(){
      console.log("Error in add component properties");
    });
}

function saveGiaImage(){
    $scope.fullPageBlockLoading = true;
    var postData = new Object();
    postData.reqPaparam = { 
      "itemId":$scope.propertyItem.itemId, 
      "itemComponentId":$scope.propertyItem.itemComponentId,
      "componentType":$scope.propertyItem.componentType,
      "reportNo": $scope.propertyAttribute.componentCertificate.certificateNumber,
      "reportWeight":$scope.propertyAttribute.singleWeight
    };
    
    var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    var savedItemAgent = sessionStorage.getItem("agentInformation");
    if(savedItemAgent){
         postData.reqPaparam.userPublicKey = savedItemAgent;
    }else{
         postData.reqPaparam.userPublicKey = savedData.memberShipKey;
    }
    
    var url = valouxService.getWebServiceUrl("reportImageServiceClient");
    //post Registration form agent data and get response
    var submitdata = valouxService.getData(url,postData);
    submitdata.then(function(data){
       if (data.sCode == RESPONSE_CODE.SUCCESS) {
           var resp = data.resData.componentProperty; 
           $scope.propertyAttribute = resp;
           $scope.fullPageBlockLoading = false;
       }
    },function(){
      console.log("Error in save Gia image");
    });
}


$scope.addedUpdateComponentPriceAdjustment= function() {
    $scope.fullPageBlockLoading = true;
    
    if($scope.componentAdj.priceProperty.generalPriceOperatorValue){
        var generalPriceAdjustmentValue = $scope.componentAdj.priceProperty.generalPriceOperatorValue; 
        if(generalPriceAdjustmentValue.indexOf("-")>-1){
            $scope.componentAdj.priceProperty.generalPriceAdjustmentOperator = '2';
            generalPriceAdjustmentValue = generalPriceAdjustmentValue.replace(/[^0-9.]/g, '');
           // generalPriceAdjustmentValue = parseInt(generalPriceAdjustmentValue);
            $scope.componentAdj.priceProperty.generalPriceAdjustmentValue = generalPriceAdjustmentValue;
        }else if(generalPriceAdjustmentValue.indexOf("+")>-1){
            $scope.componentAdj.priceProperty.generalPriceAdjustmentOperator = '1';
            generalPriceAdjustmentValue = generalPriceAdjustmentValue.replace(/[^0-9.]/g, '');
           // generalPriceAdjustmentValue = parseInt(generalPriceAdjustmentValue);
            $scope.componentAdj.priceProperty.generalPriceAdjustmentValue = generalPriceAdjustmentValue;
        }else{
            $scope.componentAdj.priceProperty.generalPriceAdjustmentValue = generalPriceAdjustmentValue;
        }
    }else{
       $scope.componentAdj.priceProperty.generalPriceAdjustmentOperator = '1'; 
       $scope.componentAdj.priceProperty.generalPriceAdjustmentValue = 0;
    }
    
    
    if($scope.componentAdj.priceProperty.brandPriceOperatorValue){
        var brandPriceAdjustmentValue = $scope.componentAdj.priceProperty.brandPriceOperatorValue;
        if(brandPriceAdjustmentValue.indexOf("-")>-1){
            $scope.componentAdj.priceProperty.brandPriceAdjustmentOperator = '2';
            brandPriceAdjustmentValue = brandPriceAdjustmentValue.replace(/[^0-9.]/g, '');
          //  brandPriceAdjustmentValue = parseInt(brandPriceAdjustmentValue);
            $scope.componentAdj.priceProperty.brandPriceAdjustmentValue = brandPriceAdjustmentValue;
        }else if(brandPriceAdjustmentValue.indexOf("+")>-1){
            $scope.componentAdj.priceProperty.brandPriceAdjustmentOperator = '1';
            brandPriceAdjustmentValue = brandPriceAdjustmentValue.replace(/[^0-9.]/g, '');
         //   brandPriceAdjustmentValue = parseInt(brandPriceAdjustmentValue);
            $scope.componentAdj.priceProperty.brandPriceAdjustmentValue = brandPriceAdjustmentValue;
        }
        else{
             $scope.componentAdj.priceProperty.brandPriceAdjustmentValue = brandPriceAdjustmentValue;
        }
    }else{
       $scope.componentAdj.priceProperty.brandPriceAdjustmentOperator = '1'; 
       $scope.componentAdj.priceProperty.brandPriceAdjustmentValue = 0;
    }
    
    var postData = new Object();
    postData.reqPaparam = { 
      "itemId":$scope.componentAdj.itemId, 
      "itemComponentId":$scope.componentAdj.itemComponentId,
      "componentType":$scope.componentAdj.componentType,
      "componentProperty":{}
    };
    postData.reqPaparam.componentProperty.priceProperty = $scope.componentAdj.priceProperty;
    
    
    postData.reqPaparam.requestType =  "update";
 
    var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    var savedItemAgent = sessionStorage.getItem("agentInformation");
    if(savedItemAgent){
         postData.reqPaparam.userPublicKey = savedItemAgent;
    }else{
         postData.reqPaparam.userPublicKey = savedData.memberShipKey;
    }
    /*get service url additem*/
    var url = valouxService.getWebServiceUrl("addUpdateItemComponentProperties");
    //post Registration form agent data and get response
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
      $('.MatModal-content').scrollTop(0);
      if (data.sCode == RESPONSE_CODE.SUCCESS) {
        setTimeout(function(){
            var storeMangCtrl = $controller('storeManagement',{$scope: $scope}); // passing current scope to commmon controller
            storeMangCtrl.reloadPage();
            $scope.getItemPriceProperties($scope.propertyItem.itemId);
            angular.element('#modal-compnent-price-adj').closeModal();
        },500);   
        
        
        
      }else{
      
      }
      $scope.fullPageBlockLoading = false;
    },function(){
      console.log("Error in add component properties");
    });
}

$scope.removePropertyImages = function(imageId,event){
    var postData = new Object();
    postData.reqPaparam = {  
      "itemId":$scope.propertyItem.itemId, 
      "itemComponentId":$scope.propertyItem.itemComponentId,
      "cid":imageId
    };
    /*get service url additem*/
    var url = valouxService.getWebServiceUrl("deleteItemComponentImage");
    //post Registration form agent data and get response
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
       if (data.sCode == RESPONSE_CODE.SUCCESS) {
           $timeout(function(){
                angular.element('.tooltipped').tooltip();
                angular.element(event.target).parent().parent().remove();
            }, 500);
      }
    },function(){
      console.log("Error in delete image");
    });
}

$scope.addPropertyImages= function() {
    $scope.fullPageBlockLoading = true;
    var postData = new Object();
    postData.reqPaparam = { 
      "itemId":$scope.propertyItem.itemId, 
      "itemComponentId":$scope.propertyItem.itemComponentId,
      "componentType":$scope.propertyItem.componentType,
      "componentProperty":{}
    };
    
    postData.reqPaparam.componentProperty.componentImages = $scope.propertyItem.propertyImages;
    
    postData.reqPaparam.requestType =  "update";
    
    var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    var savedItemAgent = sessionStorage.getItem("agentInformation");
    if(savedItemAgent){
         postData.reqPaparam.userPublicKey = savedItemAgent;
    }else{
         postData.reqPaparam.userPublicKey = savedData.memberShipKey;
    }
 
    /*get service url additem*/
    var url = valouxService.getWebServiceUrl("addUpdateItemComponentProperties");
    //post Registration form agent data and get response
    var submitItem = valouxService.getData(url,postData);
    submitItem.then(function(data){
        $scope.propertyItem.propertyImages = [];
       if (data.sCode == RESPONSE_CODE.SUCCESS) {
           $('input.file-path').val('');
           var resp = data.resData.componentProperty; 
           $scope.propertyAttribute.componentPhotosImages = resp.componentPhotosImages;
           $scope.propertyAttribute.componentReceiptsImages = resp.componentReceiptsImages;
           $scope.propertyAttribute.componentCertificatesImages = resp.componentCertificatesImages;

       
       }else{;
      //  $scope.propertyFormSuccess= false;
       // $scope.propertyFormError = true;
       // $scope.propertyErrorMessage = data.errorMessage;

      }
      $scope.fullPageBlockLoading = false;
        $timeout(function(){
            angular.element('.tooltipped').tooltip();
        }, 500);
    },function(){
      console.log("Error in add component properties");
    });
}

// upload Item Images
$scope.uploadTampPropertyImages = function(e){
    var elementId = e.data.elementId; 
    var imageObj = e.target;
    var ItemImageContent = "";
    var ItemImageName = "";
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
                $scope.inValidimgProperty[elementId] = false;

            }
        })
        if(returnValue)
        {
            $scope.inValidimgProperty[elementId] = true;
            $scope.inValidimgPropertyText[elementId] = "Please select [jpeg, png] image.";
            $scope.$apply();
            return;

        }else if(fileSize > 2)
        {
            $scope.inValidimgProperty[elementId] = true;
            $scope.inValidimgPropertyText[elementId] = "Please select image less or equal to 2 MB image.";
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
             $scope.propertyItem.propertyImages[elementId] ={'imageContent' : ItemImageContent,'imageName':ItemImageName,'fileType':$scope.propertyItem.propertyImageType};
        };       
        FR.readAsDataURL( imageObj.files[0] );                
    }
}

function refreshGIA(){
   // girdleThicknessDescription symmetry clarityId cutlet fluorescence fancyColor depth depthPercentage tablePercentage diamondLength diamondWidth polish
    delete $scope.propertyAttribute.color;
    delete $scope.propertyAttribute.girdleThicknessDescription;
    delete $scope.propertyAttribute.symmetry;
    delete $scope.propertyAttribute.clarityId;
    delete $scope.propertyAttribute.cutlet;
    delete $scope.propertyAttribute.fluorescence;
    delete $scope.propertyAttribute.fancyColor;
    delete $scope.propertyAttribute.depth;
    delete $scope.propertyAttribute.depthPercentage;
    delete $scope.propertyAttribute.tablePercentage;
    delete $scope.propertyAttribute.diamondLength;
    delete $scope.propertyAttribute.diamondWidth;
    delete $scope.propertyAttribute.polish;
    
    setGAIReadOnly(false);
}


function setGAIReadOnly(status){
    $scope.readOnlyProperty = {
        shape:status,
        cut :status,
        placement:status,
        girdleThicknessDescription : status,
        symmetry : status,
        clarityId : status,
        cutlet : status,
        fluorescence : status,
        depth : status,
        depthPercentage : status,
        diamondLength : status,
        diamondWidth : status,
        polish : status,
        tablePercentage : status,
        fancyColor : status,
        color : status,
        lengthWidthRatio: status,
        maxDiameter : status,
        minDiameter : status,
        secondaryHue : status,
        weightMeasure : status,
        internalInclusions : status,
        externalInclusions : status,
        thickness : status,
    }
}

$scope.callcertificateApi = function(){
    $scope.propertyFormError = false;
    $scope.propertyFormSuccess= false;
    if($scope.propertyAttribute.componentCertificate.lab==3 && $scope.propertyAttribute.componentCertificate.certificateNumber &&  $scope.propertyAttribute.singleWeight){
        refreshGIA();
        $scope.fullPageBlockLoading = true;
        var postData = new Object();
        postData.reqPaparam = { 
          "reportNo": $scope.propertyAttribute.componentCertificate.certificateNumber,
          "reportWeight": $scope.propertyAttribute.singleWeight
        };
        /*get service url global search data*/
        var url = valouxService.getWebServiceUrl("checkGiaReport");
        //post search data
        var checkGia = valouxService.getData(url,postData);
        checkGia.then(function(data){
           // alert(data.resData.REPORT_CHECK_RESPONSE.REPORT_DTLS.toSource())
            if (data.sCode == RESPONSE_CODE.SUCCESS) {
                var giaData = data.resData.REPORT_CHECK_RESPONSE.REPORT_DTLS.REPORT_DTL
                if(giaData.MESSAGE == ''){
                    
                    if(giaData.SHAPE!=""){
                        $scope.propertyAttribute.shape = findElementId(masterDataService.diamondShapes(), "scode", giaData.SHAPE);
                        $scope.readOnlyProperty.shape = true;
                    }
                    
                    var strColor = giaData.COLOR;
                    strColor = strColor.replace(/[^a-zA-Z]/g, "");
                    if(strColor!=""){
                        $scope.propertyAttribute.color = findElementId(masterDataService.diamondColor(), "name", strColor);
                        $scope.readOnlyProperty.color = true;
                    }
                    
                    if(giaData.GIRDLE_CONDITION!=""){
                        $scope.propertyAttribute.girdleThicknessDescription = findElementId(masterDataService.girdleThickness(), "scode", giaData.GIRDLE_CONDITION);
                        $scope.readOnlyProperty.girdleThicknessDescription = true;
                    }
                    if(giaData.SYMMETRY!=""){
                         $scope.propertyAttribute.symmetry = findElementId(masterDataService.qualityType(), "scode", giaData.SYMMETRY);
                         $scope.readOnlyProperty.symmetry = true;
                    }
                    if(giaData.CLARITY!=""){
                        $scope.propertyAttribute.clarityId = findElementId(masterDataService.clarityType(), "scode", giaData.CLARITY);
                        $scope.readOnlyProperty.clarityId = true;
                    }
                    if(giaData.CULET_SIZE!=""){
                        $scope.propertyAttribute.cutlet = findElementId(masterDataService.diamondCutlet(), "scode", giaData.CULET_SIZE);
                        $scope.readOnlyProperty.cutlet = true;
                    }
                    if(giaData.FLUORESCENCE_INTENSITY!=""){
                        $scope.propertyAttribute.fluorescence = findElementId(masterDataService.fluorescence(), "scode", giaData.FLUORESCENCE_INTENSITY);
                        $scope.readOnlyProperty.fluorescence = true;
                    }
                    if(giaData.FLUORESCENCE_COLOR!=""){ 
                        $scope.propertyAttribute.fancyColor = findElementId(masterDataService.fancyColor(), "name", giaData.FLUORESCENCE_COLOR);
                        $scope.readOnlyProperty.fancyColor = true;
                    }
                    
                    if(giaData.DEPTH!=""){
                        $scope.propertyAttribute.depth = giaData.DEPTH;
                        $scope.readOnlyProperty.depth = true;
                    }
                    if(giaData.DEPTH_PCT!=""){
                        $scope.propertyAttribute.depthPercentage = giaData.DEPTH_PCT;
                        $scope.readOnlyProperty.depthPercentage = true;
                    }
                    if(giaData.TABLE_PCT!=""){
                        $scope.propertyAttribute.tablePercentage = giaData.TABLE_PCT;
                        $scope.readOnlyProperty.tablePercentage = true;
                    }
                    if(giaData.LENGTH!=""){
                        $scope.propertyAttribute.diamondLength = giaData.LENGTH;
                        $scope.readOnlyProperty.diamondLength = true;
                    }
                    if(giaData.WIDTH!=""){
                        $scope.propertyAttribute.diamondWidth = giaData.WIDTH; 
                        $scope.readOnlyProperty.diamondWidth = true; 
                    }
                    if(giaData.POLISH!=""){
                        $scope.propertyAttribute.polish = findElementId(masterDataService.qualityType(), "scode", giaData.POLISH);
                        $scope.readOnlyProperty.polish = true;
                    }
                }else{
                     delete $scope.propertyAttribute.componentCertificate.lab;
                     delete $scope.propertyAttribute.componentCertificate.certificateNumber;
                     refreshGIA();
                     setGAIReadOnly(false);
                     $scope.propertyFormError = true;
                     $scope.propertyFormSuccess= false;
                     $scope.propertyErrorMessage = "Invalid certificate."
                }
            }
            $scope.getDiamondSpecifyPrice();
            $scope.fullPageBlockLoading = false;
            $timeout(function(){
                angular.element("select").material_select();
            }, 700);
        },function(){
          console.log("Error in GIA");
        });
    }else{  
        setGAIReadOnly(false);
        $scope.getDiamondSpecifyPrice();
        $timeout(function(){
                angular.element("select").material_select();
            }, 1000);
    }

}


$scope.getDiamondSpecifyPrice = function(){
    $scope.fullPageBlockLoading = true;
    $scope.propertyAttribute.priceProperty.marketValue = 0;
    if($scope.propertyAttribute.clarityId && $scope.propertyAttribute.color && $scope.propertyAttribute.shape && $scope.propertyAttribute.singleWeight){
    var postData = new Object();
        postData.reqPaparam = { 
          "clarityId": $scope.propertyAttribute.clarityId,
          "color": $scope.propertyAttribute.color,
          "shape": $scope.propertyAttribute.shape,
          "totalWeight": $scope.propertyAttribute.singleWeight
        };
        /*get service url global search data*/
        var url = valouxService.getWebServiceUrl("getItemComponentDiamondSpecifyPrice");
        //post search data
        var getPrice = valouxService.getData(url,postData);
        getPrice.then(function(data){
           $scope.propertyAttribute.priceProperty.marketValue =  data.resData.specifiedValue;
           if($scope.isAgentLogin){
                $scope.calculateFinalPrice();
           }
           $scope.fullPageBlockLoading = false;
            
        },function(){
          console.log("Error in GIA");
        });
    }else if($scope.isAgentLogin){
         $scope.calculateFinalPrice();
    }
}


$scope.getMetalSpecifyPrice = function(){
    $scope.fullPageBlockLoading = true;
    if(!$scope.propertyAttribute.purity){
        $scope.propertyAttribute.purity = "";
    }
    if($scope.propertyAttribute.metalsType && $scope.propertyAttribute.weight){
    var postData = new Object();
        postData.reqPaparam = { 
          "purity": $scope.propertyAttribute.purity,
          "metalsType": $scope.propertyAttribute.metalsType,
          "weight": $scope.propertyAttribute.weight
        };
        /*get service url global search data*/
        var url = valouxService.getWebServiceUrl("getItemComponentMetalSpecifyPrice");
        //post search data
        var getPrice = valouxService.getData(url,postData);
        getPrice.then(function(data){
           $scope.propertyAttribute.priceProperty.marketValue =  data.resData.specifiedValue;
           if($scope.isAgentLogin){
            $scope.calculateFinalPrice();
           }
           $scope.fullPageBlockLoading = false; 
        },function(){
          console.log("Error in GIA");
        });
    }
}

// using in component add/edit page
$scope.calculateFinalPrice = function(){
    if($scope.isAgentLogin){
        $scope.propertyAttribute.priceProperty.finalPrice = parseFloat($scope.propertyAttribute.priceProperty.marketValue);
        if(!$scope.propertyAttribute.priceProperty.purchasePrice){
            if($scope.propertyAttribute.priceProperty.generalPriceOperatorValue && !isNaN($scope.propertyAttribute.priceProperty.generalPriceOperatorValue)){ 
                if($scope.propertyAttribute.priceProperty.generalPriceAdjustmentType==1){
                    $scope.propertyAttribute.priceProperty.finalPrice = parseFloat($scope.propertyAttribute.priceProperty.marketValue) + parseFloat($scope.propertyAttribute.priceProperty.generalPriceOperatorValue);
                }else{
                    var parGeneralPrice = (parseFloat($scope.propertyAttribute.priceProperty.marketValue) * parseFloat($scope.propertyAttribute.priceProperty.generalPriceOperatorValue))/100;
                    $scope.propertyAttribute.priceProperty.finalPrice = parseFloat($scope.propertyAttribute.priceProperty.marketValue) + parseFloat(parGeneralPrice);
                }
                
                if($scope.propertyAttribute.priceProperty.brandPriceOperatorValue && !isNaN($scope.propertyAttribute.priceProperty.brandPriceOperatorValue)){
                    if($scope.propertyAttribute.priceProperty.brandPriceAdjustmentType==1){
                        $scope.propertyAttribute.priceProperty.finalPrice = parseFloat($scope.propertyAttribute.priceProperty.finalPrice) + parseFloat(($scope.propertyAttribute.priceProperty.brandPriceOperatorValue));
                    }else{
                        var parBrandPrice = (parseFloat($scope.propertyAttribute.priceProperty.marketValue) * parseFloat($scope.propertyAttribute.priceProperty.brandPriceOperatorValue))/100;
                        $scope.propertyAttribute.priceProperty.finalPrice = parseFloat($scope.propertyAttribute.priceProperty.finalPrice) + parseFloat(parBrandPrice);
                    }
                }
            }else if($scope.propertyAttribute.priceProperty.brandPriceOperatorValue && !isNaN($scope.propertyAttribute.priceProperty.brandPriceOperatorValue)){
                if($scope.propertyAttribute.priceProperty.brandPriceAdjustmentType==1){
                    $scope.propertyAttribute.priceProperty.finalPrice = parseFloat($scope.propertyAttribute.priceProperty.marketValue) + parseFloat($scope.propertyAttribute.priceProperty.brandPriceOperatorValue);
                }else{
                    var parBrandPrice = (parseFloat($scope.propertyAttribute.priceProperty.marketValue) * parseFloat($scope.propertyAttribute.priceProperty.brandPriceOperatorValue))/100;
                    $scope.propertyAttribute.priceProperty.finalPrice = parseFloat($scope.propertyAttribute.priceProperty.marketValue) + parseFloat(parBrandPrice);
                }
            }
        }else{
          $scope.propertyAttribute.priceProperty.finalPrice = $scope.propertyAttribute.priceProperty.purchasePrice;  
        }
        
    }
}


// using in only component price adjustment with popup
$scope.calculateComponentFinalPrice = function(){ 
    if($scope.isAgentLogin){
        $scope.componentAdj.priceProperty.finalPrice = parseFloat($scope.componentAdj.priceProperty.marketValue);
        if(!$scope.componentAdj.priceProperty.purchasePrice){
            if($scope.componentAdj.priceProperty.generalPriceOperatorValue && !isNaN($scope.componentAdj.priceProperty.generalPriceOperatorValue)){ 
                if($scope.componentAdj.priceProperty.generalPriceAdjustmentType==1){
                    $scope.componentAdj.priceProperty.finalPrice = parseFloat($scope.componentAdj.priceProperty.marketValue) + parseFloat($scope.componentAdj.priceProperty.generalPriceOperatorValue);
                }else{
                    var parGeneralPrice = (parseFloat($scope.componentAdj.priceProperty.marketValue) * parseFloat($scope.componentAdj.priceProperty.generalPriceOperatorValue))/100;
                    $scope.componentAdj.priceProperty.finalPrice = parseFloat($scope.componentAdj.priceProperty.marketValue) + parseFloat(parGeneralPrice);
                }
                
                if($scope.componentAdj.priceProperty.brandPriceOperatorValue && !isNaN($scope.componentAdj.priceProperty.brandPriceOperatorValue)){
                    if($scope.componentAdj.priceProperty.brandPriceAdjustmentType==1){
                        $scope.componentAdj.priceProperty.finalPrice = parseFloat($scope.componentAdj.priceProperty.finalPrice) + parseFloat(($scope.componentAdj.priceProperty.brandPriceOperatorValue));
                    }else{
                        var parBrandPrice = (parseFloat($scope.componentAdj.priceProperty.marketValue) * parseFloat($scope.componentAdj.priceProperty.brandPriceOperatorValue))/100;
                        $scope.componentAdj.priceProperty.finalPrice = parseFloat($scope.componentAdj.priceProperty.finalPrice) + parseFloat(parBrandPrice);
                    }
                }
            }else if($scope.componentAdj.priceProperty.brandPriceOperatorValue && !isNaN($scope.componentAdj.priceProperty.brandPriceOperatorValue)){
                if($scope.componentAdj.priceProperty.brandPriceAdjustmentType==1){
                    $scope.componentAdj.priceProperty.finalPrice = parseFloat($scope.componentAdj.priceProperty.marketValue) + parseFloat($scope.componentAdj.priceProperty.brandPriceOperatorValue);
                }else{
                    var parBrandPrice = (parseFloat($scope.componentAdj.priceProperty.marketValue) * parseFloat($scope.componentAdj.priceProperty.brandPriceOperatorValue))/100;
                    $scope.componentAdj.priceProperty.finalPrice = parseFloat($scope.componentAdj.priceProperty.marketValue) + parseFloat(parBrandPrice);
                }
            }
        }else{
          $scope.componentAdj.priceProperty.finalPrice = $scope.componentAdj.priceProperty.purchasePrice;  
        }
        
    }
}

function findElementId(arr, propName, propValue) {
  for (var i=0; i < arr.length; i++)
    if (arr[i][propName] == propValue){
      return arr[i].value;
    }
  // will return undefined if not found; you could return a default instead
}

$scope.itemInfo = new Object();
$scope.itemInfo.priceProperty = new Object();

$scope.getItemPriceProperties = function(itemid){ 
    if(itemid){
    var postData = new Object();
        postData.reqPaparam = { 
          "itemId": itemid
        };
        
        var savedItem = sessionStorage.getItem("saveValouxData");
        var savedData = JSON.parse(savedItem);
        var savedItemAgent = sessionStorage.getItem("agentInformation");
        if(savedItemAgent){
             postData.reqPaparam.userPublicKey = savedItemAgent;
        }else{
             postData.reqPaparam.userPublicKey = savedData.memberShipKey;
        }
            /*get service url global search data*/
        var url = valouxService.getWebServiceUrl("getItemPriceProperties");
        //post search data
        var getPrice = valouxService.getData(url,postData);
        getPrice.then(function(data){
           $scope.itemInfo.priceProperty =  data.resData.priceProperty;
            if(!$scope.itemInfo.priceProperty.marketValue){
               $scope.itemInfo.priceProperty.marketValue = 0;
            }
           
            if(!$scope.itemInfo.priceProperty.finalPrice){
               $scope.itemInfo.priceProperty.finalPrice = 0;
            }
            
            $scope.itemInfo.priceProperty.totalFinalPrice = $scope.itemInfo.priceProperty.finalPrice;
            
            if(!$scope.itemInfo.priceProperty.generalPriceAdjustmentType){
                $scope.itemInfo.priceProperty.generalPriceAdjustmentType =1;
            }
            if(!$scope.itemInfo.priceProperty.brandPriceAdjustmentType){
                $scope.itemInfo.priceProperty.brandPriceAdjustmentType =1;
            }
            if(!$scope.itemInfo.priceProperty.generalPriceAdjustmentOperator){
                $scope.itemInfo.priceProperty.generalPriceAdjustmentOperator = 1;
            }
            if(!$scope.itemInfo.priceProperty.brandPriceAdjustmentOperator){
                $scope.itemInfo.priceProperty.brandPriceAdjustmentOperator = 1;
            }
            
            if(data.resData.priceProperty.generalPriceAdjustmentValue){
                $scope.itemInfo.priceProperty.generalPriceOperatorValue = data.resData.priceProperty.generalPriceAdjustmentValue.toString();
            }else{
                $scope.itemInfo.priceProperty.generalPriceOperatorValue = "0";
            }
            
            if($scope.itemInfo.priceProperty.generalPriceAdjustmentOperator==2){
                $scope.itemInfo.priceProperty.generalPriceOperatorValue = "-"+data.resData.priceProperty.generalPriceAdjustmentValue;
            }
            
            if(data.resData.priceProperty.brandPriceAdjustmentValue){
                $scope.itemInfo.priceProperty.brandPriceOperatorValue = data.resData.priceProperty.brandPriceAdjustmentValue.toString();
            }else{
                $scope.itemInfo.priceProperty.brandPriceOperatorValue = 0;
            }
            
            
            if($scope.itemInfo.priceProperty.brandPriceAdjustmentOperator==2){
                $scope.itemInfo.priceProperty.brandPriceOperatorValue = "-"+data.resData.priceProperty.brandPriceAdjustmentValue;
            } 
            
            
        },function(){
          console.log("Error in GIA");
        });
    }
}


$scope.addedUpdateItemPriceAdjustment = function(itemId) {
    
    $scope.fullPageBlockLoading = true;
    if($scope.itemInfo.priceProperty.generalPriceOperatorValue){
        var generalPriceAdjustmentValue = $scope.itemInfo.priceProperty.generalPriceOperatorValue; 
        if(generalPriceAdjustmentValue.indexOf("-")>-1){
            $scope.itemInfo.priceProperty.generalPriceAdjustmentOperator = '2';
            generalPriceAdjustmentValue = generalPriceAdjustmentValue.replace(/[^0-9.]/g, '');
           // generalPriceAdjustmentValue = parseInt(generalPriceAdjustmentValue);
            $scope.itemInfo.priceProperty.generalPriceAdjustmentValue = generalPriceAdjustmentValue;
        }else if(generalPriceAdjustmentValue.indexOf("+")>-1){
            $scope.itemInfo.priceProperty.generalPriceAdjustmentOperator = '1';
            generalPriceAdjustmentValue = generalPriceAdjustmentValue.replace(/[^0-9.]/g, '');
           // generalPriceAdjustmentValue = parseInt(generalPriceAdjustmentValue);
            $scope.itemInfo.priceProperty.generalPriceAdjustmentValue = generalPriceAdjustmentValue;
        }else{
            $scope.itemInfo.priceProperty.generalPriceAdjustmentValue = generalPriceAdjustmentValue;
        }
    }else{
       $scope.itemInfo.priceProperty.generalPriceAdjustmentOperator = '1'; 
       $scope.itemInfo.priceProperty.generalPriceAdjustmentValue = 0;
    }
    
    
    if($scope.itemInfo.priceProperty.brandPriceOperatorValue){
        var brandPriceAdjustmentValue = $scope.itemInfo.priceProperty.brandPriceOperatorValue;
        if(brandPriceAdjustmentValue.indexOf("-")>-1){
            $scope.itemInfo.priceProperty.brandPriceAdjustmentOperator = '2';
            brandPriceAdjustmentValue = brandPriceAdjustmentValue.replace(/[^0-9.]/g, '');
          //  brandPriceAdjustmentValue = parseInt(brandPriceAdjustmentValue);
            $scope.itemInfo.priceProperty.brandPriceAdjustmentValue = brandPriceAdjustmentValue;
        }else if(brandPriceAdjustmentValue.indexOf("+")>-1){
            $scope.itemInfo.priceProperty.brandPriceAdjustmentOperator = '1';
            brandPriceAdjustmentValue = brandPriceAdjustmentValue.replace(/[^0-9.]/g, '');
         //   brandPriceAdjustmentValue = parseInt(brandPriceAdjustmentValue);
            $scope.itemInfo.priceProperty.brandPriceAdjustmentValue = brandPriceAdjustmentValue;
        }else{
            $scope.itemInfo.priceProperty.brandPriceAdjustmentValue = brandPriceAdjustmentValue;
        }
    }else{
       $scope.itemInfo.priceProperty.brandPriceAdjustmentOperator = '1';
       $scope.itemInfo.priceProperty.brandPriceAdjustmentValue = 0;
    }
    
    var postData = new Object();
    postData.reqPaparam = { 
      "itemId":itemId, 
      "priceProperty":$scope.itemInfo.priceProperty,
    };

    var savedItem = sessionStorage.getItem("saveValouxData");
    var savedData = JSON.parse(savedItem);
    var savedItemAgent = sessionStorage.getItem("agentInformation");
    if(savedItemAgent){
         postData.reqPaparam.userPublicKey = savedItemAgent;
    }else{
         postData.reqPaparam.userPublicKey = savedData.memberShipKey;
    }
    /*get service url additem*/
    var url = valouxService.getWebServiceUrl("updateItemPriceProperties");
    //post Registration form agent data and get response
    var submitItemPrice = valouxService.getData(url,postData);
    submitItemPrice.then(function(data){
      if (data.sCode == RESPONSE_CODE.SUCCESS) {
          $scope.getItemPriceProperties(itemId);
          angular.element('#modal-item-price').closeModal();
      }else{
       
      }
      $scope.fullPageBlockLoading = false;
    },function(){
      console.log("Error in item price submit");
    });
}

$scope.calculateItemFinalPrice = function(){
    if($scope.isAgentLogin){
        $scope.itemInfo.priceProperty.finalPrice = parseFloat($scope.itemInfo.priceProperty.marketValue);
        if(!$scope.itemInfo.priceProperty.purchasePrice){
            if($scope.itemInfo.priceProperty.generalPriceOperatorValue && !isNaN($scope.itemInfo.priceProperty.generalPriceOperatorValue)){ 
                if($scope.itemInfo.priceProperty.generalPriceAdjustmentType==1){
                    $scope.itemInfo.priceProperty.finalPrice = parseFloat($scope.itemInfo.priceProperty.marketValue) + parseFloat($scope.itemInfo.priceProperty.generalPriceOperatorValue);
                }else{
                    var parGeneralPrice = (parseFloat($scope.itemInfo.priceProperty.marketValue) * parseFloat($scope.itemInfo.priceProperty.generalPriceOperatorValue))/100;
                    $scope.itemInfo.priceProperty.finalPrice = parseFloat($scope.itemInfo.priceProperty.marketValue) + parseFloat(parGeneralPrice);
                }
                
                if($scope.itemInfo.priceProperty.brandPriceOperatorValue && !isNaN($scope.itemInfo.priceProperty.brandPriceOperatorValue)){
                    if($scope.itemInfo.priceProperty.brandPriceAdjustmentType==1){
                        $scope.itemInfo.priceProperty.finalPrice = parseFloat($scope.itemInfo.priceProperty.finalPrice) + parseFloat(($scope.itemInfo.priceProperty.brandPriceOperatorValue));
                    }else{
                        var parBrandPrice = (parseFloat($scope.itemInfo.priceProperty.marketValue) * parseFloat($scope.itemInfo.priceProperty.brandPriceOperatorValue))/100;
                        $scope.itemInfo.priceProperty.finalPrice = parseFloat($scope.itemInfo.priceProperty.finalPrice) + parseFloat(parBrandPrice);
                    }
                }
            }else if($scope.itemInfo.priceProperty.brandPriceOperatorValue && !isNaN($scope.itemInfo.priceProperty.brandPriceOperatorValue)){
                if($scope.itemInfo.priceProperty.brandPriceAdjustmentType==1){
                    $scope.itemInfo.priceProperty.finalPrice = parseFloat($scope.itemInfo.priceProperty.marketValue) + parseFloat($scope.itemInfo.priceProperty.brandPriceOperatorValue);
                }else{
                    var parBrandPrice = (parseFloat($scope.itemInfo.priceProperty.marketValue) * parseFloat($scope.itemInfo.priceProperty.brandPriceOperatorValue))/100;
                    $scope.itemInfo.priceProperty.finalPrice = parseFloat($scope.itemInfo.priceProperty.marketValue) + parseFloat(parBrandPrice);
                }
            }
        }else{
          $scope.itemInfo.priceProperty.finalPrice = $scope.itemInfo.priceProperty.purchasePrice;  
        }
        
    }
}

/*Global search items, collections, appraisals*/

$scope.searchData = function(){

  var getSearchKeyword = $window.location.search.substring(8)
  var searchKeyword = decodeURI(getSearchKeyword);
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  $scope.userKey = savedData.memberShipKey;
  if(searchKeyword){
    var postData = new Object();
    postData.reqPaparam = { 
      "keyword": searchKeyword
    };

    /*get service url global search data*/
    var url = valouxService.getWebServiceUrl("globalSearch");
    //post search data
    var submitSearchData = valouxService.getData(url,postData);
    submitSearchData.then(function(data){
      sessionStorage.removeItem('agentInformation');
      $scope.animated();
      angular.element('.MatModal').hide();
      $scope.searchItemDetail = data.itemDetail;
      $scope.searchCollectionDetail = data.collectionDetail;
      $scope.searchAppraisalDetail = data.appraisalDetail;
      $scope.searchStoreDetail = data.storeDetail;
      $scope.searchAgentDetail = data.agentDetail;
      $scope.keyword = searchKeyword;
      $scope.globalSearch = searchKeyword;
    },function(){
      console.log("Error in globalSearch submit");
    });
  }else{
    $scope.searchBlank = true;
  }
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


/*redirect to item page for agent*/

$scope.agentItemRedirect = function(id, consumerKey){
  if(consumerKey){
    $window.location = "item-agent.html#/detail/"+id;
    sessionStorage.setItem('agentInformation', consumerKey);
  }  
}

/*redirect to collection page for agent*/

$scope.agentCollectionRedirect = function(id, consumerKey){
  if(consumerKey){
    $window.location = "collection-agent.html#/detail/"+id;
    sessionStorage.setItem('agentInformation', consumerKey);
  }  
}

/*redirect to appraisal page for agent*/

$scope.agentAppraisalRedirect = function(id, consumerKey){
  if(consumerKey){
    $window.location = "appraisal-agent.html#/detail/"+id;
    sessionStorage.setItem('agentInformation', consumerKey);
  }  
}

/*Global search items, collections, appraisals*/

$scope.searchSubmit = function(){
  var searchResults = $scope.globalSearch;
  if(searchResults){
    $window.location = "search-results.html?search="+searchResults;
  }
}

/*clear search result*/

$scope.clearSearch = function(){
  $scope.globalSearch = "";
  $scope.keyword = "";
}

/*clear search result*/

$scope.filterSearch = function(length, name){
  if(length == 0){
    angular.element("li.noDataFound").show();
    if(name == 'item'){
      $scope.name = 'item';
    }else if(name == 'collection'){
      $scope.name = 'collection';
    }else if(name == 'appraisal'){
      $scope.name = 'appraisal';
    }else if(name == 'store'){
      $scope.name = 'store';
    }else if(name == 'agent'){
      $scope.name = 'agent';
    }
  }else{
    angular.element("li.noDataFound").hide();
  }
}

// upload Item Images
$scope.uploadTampPropertyImagesReceipt = function(e){
    $scope.propertyItem.propertyImages = [];
    var elementId = e.data.elementId; 
    var elementType = e.data.elementType; 
    var imageObj = e.target;
    var ItemImageContent = "";
    var ItemImageName = "";
    ItemImageName = imageObj.files[0].name;
    if ( imageObj.files && imageObj.files[0] ) {
        var allowed = ["txt", "doc","pdf","jpeg", "png"];
        var fileType = imageObj.files[0].type;
        var fileSize = imageObj.files[0].size;
        fileSize = (fileSize/1024/1024);
        var returnValue = true;
        allowed.forEach(function(extension) {
            if (imageObj.files[0].type.match('text/plain') || imageObj.files[0].type.match('application/pdf') || imageObj.files[0].type.match('image/'+extension) || imageObj.files[0].type.match('application/msword')) {
                returnValue = false;
                if(elementType==1){
                    $scope.inValidimgReceipt[elementId] = false;
                }else{
                    $scope.inValidimgCertificate[elementId] = false;
                }

            }
        })
        if(returnValue)
        {
            if(elementType==1){
                $scope.inValidimgReceipt[elementId] = true;
                $scope.inValidimgReceiptText[elementId] = "Please select [jpeg, png, txt, doc, pdf] image.";
            }else{
                $scope.inValidimgCertificate[elementId] = true;
                $scope.inValidimgCertificateText[elementId] = "Please select [jpeg, png, txt, doc, pdf] image.";
            }
            $scope.$apply();
            return;

        }else if(fileSize > 2)
        {
            if(elementType==1){
                $scope.inValidimgReceipt[elementId] = true;
                $scope.inValidimgReceiptText[elementId] = "Please select image less or equal to 2 MB image.";
            }else{
                $scope.inValidimgCertificate[elementId] = true;
                $scope.inValidimgCertificateText[elementId] = "Please select image less or equal to 2 MB image.";
            }
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
            newImageObj = newImageObj.substr(newImageObj.indexOf(',') + 1);
            ItemImageContent= newImageObj;   
             $scope.propertyItem.propertyImages.push({'imageContent' : ItemImageContent,'imageName':ItemImageName,'fileType':$scope.propertyItem.propertyImageType});
        };       
        FR.readAsDataURL( imageObj.files[0] );                
    }
}

$scope.changeMatelType = function(metalsType){ 
    $scope.getMetalSpecifyPrice();
    $scope.propertyAttribute.purity = "";
    $scope.propertyAttribute.color = "";
    $scope.propertyAttribute.typeSpecified = "";
    $timeout(function(){
        angular.element("#matelPurityType").material_select();
        angular.element("#metalColor").material_select();
        
    }, 300);
}

$scope.changeMatelTypePurity = function(metalsType){ 
    return function (purity) {
        if (metalsType == 1 && purity.group=='Gold'){
            return true;
        }else if(metalsType == 2 && purity.group=='Silver'){
            return true;
        }else if(metalsType == 3 && purity.group=='Platinum'){
            return true;
        }
        return false;
    };
    
}

$scope.componentPricePopup = function (priceProperty,itemId,componentId,componentType) {
    
    $scope.componentAdj = new Object();
    $scope.componentAdj.itemId = itemId;
    $scope.componentAdj.itemComponentId = componentId;
    $scope.componentAdj.componentType = componentType;
    $scope.componentAdj.priceProperty = angular.copy(priceProperty);
    if(!$scope.componentAdj.priceProperty.marketValue){
        $scope.componentAdj.priceProperty.marketValue = 0;
    }
    if(!$scope.componentAdj.priceProperty.finalPrice){
        $scope.componentAdj.priceProperty.finalPrice = 0;
    }
    if(priceProperty.generalPriceAdjustmentOperator == 2){
        $scope.componentAdj.priceProperty.generalPriceOperatorValue = '-'+$scope.componentAdj.priceProperty.generalPriceAdjustmentValue;
    }else{
        $scope.componentAdj.priceProperty.generalPriceOperatorValue = $scope.componentAdj.priceProperty.generalPriceAdjustmentValue;
    }
    
    if(priceProperty.generalPriceAdjustmentType == 2){
        $scope.componentAdj.priceProperty.generalPriceAdjustmentType = 2;
    }else{
        $scope.componentAdj.priceProperty.generalPriceAdjustmentType = 1;
    }
    
    if(priceProperty.brandPriceAdjustmentOperator == 2){
        $scope.componentAdj.priceProperty.brandPriceOperatorValue = '-'+$scope.componentAdj.priceProperty.brandPriceAdjustmentValue;
    }else{
        $scope.componentAdj.priceProperty.brandPriceOperatorValue = $scope.componentAdj.priceProperty.brandPriceAdjustmentValue;
    }
    if(priceProperty.brandPriceAdjustmentType == 2){
        $scope.componentAdj.priceProperty.brandPriceAdjustmentType = 2;
    }else{
        $scope.componentAdj.priceProperty.brandPriceAdjustmentType = 1;
    }
    $timeout(function(){
        angular.element("select").material_select();
        angular.element('#modal-compnent-price-adj').openModal();
    }, 300);
  
}


}])
.constant('RESPONSE_CODE', { /* constant define for webservice response code */
    'SUCCESS': 1,
    'ERROR': 2
})
.constant('ROLE_TYPE', { /* constant define for user Role type */
    'CONSUMER': 1,
    'AGENT': 2,
    'SUPERADMIN':5,

})
