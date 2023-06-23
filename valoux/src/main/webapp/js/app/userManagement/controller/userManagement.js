baseController.controller('userManagement',['$scope', 'valouxService','masterDataService', '$timeout','RESPONSE_CODE','$location','$window', function($scope, valouxService,masterDataService, $timeout, RESPONSE_CODE,$location,$window ){
/*Define user Object*/
$scope.user = new Object();

$scope.registerFormSuccess = false;
$scope.registerForm = true;
$scope.isForgetQuestion = false;
$scope.isEmailID = true;
$scope.hideDetailsPersonal = false;

/*init dropdown option for consumer form by valoux master data service*/
$scope.initRegistrationConsumer= function() {
     $scope.status_categories = masterDataService.relationshipCategories();
     $scope.incomeRange_categories = masterDataService.incomeRange_categories();
     $scope.jewelryPurchases = masterDataService.jewelryPurchases();
     $scope.jewelryInsurance = masterDataService.jewelryInsurances();
     $scope.jewelryService = masterDataService.jewelryService();
     $scope.jewelryDocumentation = masterDataService.jewelryDocumentation();
     $scope.metals = masterDataService.metals();
     $scope.gemstones = masterDataService.gemstones();
     $scope.diamonds = masterDataService.diamonds(); 
     var postData = new Object();
     if(typeof  $location.search().invitecode !='undefined')
     { 
     
     
      postData.reqPaparam = {  
    "authLoginCode":$location.search().invitecode
  };
  
  
      /*get service url userRegistration*/
  var url = valouxService.getWebServiceUrl("inviteUserDetail");
  //post Registration form consumer data and get response
  var submitReg = valouxService.getData(url,postData);
  submitReg.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
 
 if(typeof data.resData.userPublicKey !='undefined')
 { 
 $scope.inviteuser = true;
 $scope.class = "active";
 $scope.user = data.resData;
 $scope.successMessage="You have successfully verified your email address, please fill below information to complete your profile.";
}else{
  $scope.errorMessage="User already registered";
  $scope.registerFormError=true;
  $window.location = "index.html"; 
 
}
    }//$timeout(function () { $scope.errorMessage = false; }, 5000);

  },function(){
    console.log("Error in checking Registration form consumer");
  });
     }

  
}

/*Master data country code*/
$scope.initCountryCode = function() {
  $scope.countryCodes = masterDataService.countryCode();     
}

$scope.checkValidForm = function() {
    $('#modal-detail').openModal();
}

/*Check valid form for agent*/
$scope.checkValidFormAgent = function() {
  if(!$scope.inValidimg){
      $('#modal-detail').openModal();
  }
}

/* Registration form consumer */
$scope.submitRegistrationConsumer = function() {
$scope.$parent.fullPageBlockLoading = true;
  if($scope.user.maritalStatus == null){
    $scope.user.maritalStatus = 0;
  }
  if($scope.user.incomeRange == null){
    $scope.user.incomeRange = 0;
  }

  var postData = new Object();
  postData.reqPaparam = {  
      "registrationData":$scope.user
    };

  /*Get date in consumer form*/
  var getdate = '';

  if($scope.birthday){
    var dob = $scope.birthday;
    date = new Date(dob);
    getdate = (date.getMonth() + 1) + '/' + date.getDate() + '/' +  date.getFullYear();
  }
  //Post data birthday
  
  if(getdate){
    postData.reqPaparam.registrationData.birthday = getdate;
  }else{
    postData.reqPaparam.registrationData.birthday = "";
  }
  
  /*Store value in array interestedIn*/
  var interestedInArray = [];
  angular.forEach($scope.interestedIn, function(interestIn){
    if (interestIn.selected) interestedInArray.push(interestIn.interestId);
  })
  
    /*Define array consumer about section*/
  
  var consumerAbout = {}
  if($scope.jewelryPurchase){
    consumerAbout.JewelryPurchases = [$scope.jewelryPurchase];
  }
  else{
    consumerAbout.JewelryPurchases = [];
  }
  if($scope.jewelryInsuranceType){
    consumerAbout.JewelryInsurance = [$scope.jewelryInsuranceType];
  }
  else{
    consumerAbout.JewelryInsurance = [];
  }
  if($scope.jewelryServices){
    consumerAbout.JewelryService = [$scope.jewelryServices];
  }
  else{
    consumerAbout.JewelryService = [];
  }
  if($scope.jewelryDocumentations){
    consumerAbout.JewelryDocumentation = [$scope.jewelryDocumentations];
  }
  else{
    consumerAbout.JewelryDocumentation = [];
  }
  /*Define array consumer personal Preferences section*/

  var personalPreferences = {
      JewelryTypes : $scope.jewelryTypes,
      JewelryDesign : $scope.jewelryDesign,
      JewelryStyle : $scope.jewelryStyle,
    };


  /*Define array consumer jewelry Components section*/
  
  var jewelryComponents = {}

  if($scope.metal){
    jewelryComponents.metals = $scope.metal;
  }
  else{
    consumerAbout.metals = [];
  }
  if($scope.gemstone){
    jewelryComponents.gemstones = $scope.gemstone;
  }
  else{
    consumerAbout.gemstones = [];
  }
  if($scope.diamond){
    jewelryComponents.diamonds = $scope.diamond;
  }
  else{
    consumerAbout.diamonds = [];
  }

  countryCode = $scope.countryCode;
  //countryCode = countryCode.replace(/[^0-9]/g, '');
  countryCode = parseInt(countryCode);
  countryCode = "+"+countryCode;
 
  mobilePhone = countryCode+$scope.mobilePhone;
  /*get and define variable for address*/
  var userOrAgentAddressArray ={
    addressLine1: $scope.storeData.addressLine1,
    addressLine2 : $scope.storeData.addressLine2,
    country : $scope.storeData.country,
    state : $scope.storeData.state,
    zipCode : $scope.storeData.zipCode,
    city : $scope.storeData.city
  };
  
  /*Post data mobilePhone*/

  postData.reqPaparam.registrationData.mobilePhone = mobilePhone;
  
  /*Post data consumerAbout*/
  postData.reqPaparam.registrationData.consumerAbout = consumerAbout;
  
  /*Post data personalPreferences*/
  postData.reqPaparam.registrationData.personalPreferences = personalPreferences;
  
  /*Post data jewelryComponents*/
  postData.reqPaparam.registrationData.jewelryComponents = jewelryComponents;

  /*Post data interestedIn checkbox*/
  postData.reqPaparam.registrationData.interestedIn = interestedInArray;
  /*Post data userOrAgentAddress*/
  postData.reqPaparam.registrationData.userAddress = userOrAgentAddressArray;
  

  /*get service url userRegistration*/
  if(typeof $scope.inviteuser !="undefined"){
   var url = valouxService.getWebServiceUrl("inviteUserRegistration");
  }else{
  var url = valouxService.getWebServiceUrl("userRegistration");
}
  //post Registration form consumer data and get response
  var submitReg = valouxService.getData(url,postData);
  submitReg.then(function(data){
      $('#modal-detail').closeModal();
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.registerFormSuccess = true;
      window.scrollTo(500, 0);
      $scope.registerFormError= false;
      $scope.registerForm = false;
      }else{
      $scope.errorMessage = data.errorMessage;
      window.scrollTo(500, 0);
      $scope.registerFormError= true;
      $scope.registerFormSuccess = false;
      $scope.registerForm = true;
    }
  },function(){
    console.log("Error in checking Registration form consumer");
  });
};


/* Registration form agent */
$scope.submitRegistrationAgent = function() {
    $scope.$parent.fullPageBlockLoading = true;
  /*get and define variable for address*/
 
  var postData = new Object();
   postData.reqPaparam = {  
      "registrationData":$scope.user
    };
    
   countryCode = $scope.countryCode;
  //countryCode = countryCode.replace(/[^0-9]/g, '');
  countryCode = parseInt(countryCode);
  countryCode = "+"+countryCode;
  mobilePhone = countryCode+$scope.mobilePhone;
  
  postData.reqPaparam.registrationData.mobilePhone = mobilePhone;
  postData.reqPaparam.registrationData.imageContent = $scope.signatureImage;
  postData.reqPaparam.registrationData.imageName = $scope.signatureImageName;
  
  
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

            postData.reqPaparam.registrationData.storeData = {  
                  storeName:$scope.storeData.store_name,
                  storeAddress:storeAddress,
                  storePhone :$scope.storeData.storePhoneNumber
            };
     }else{
         postData.reqPaparam.registrationData.storeData = {  
                  storeId :$scope.storeData.storeId
         }
     }
  
  /*get service url userRegistration*/
  var url = valouxService.getWebServiceUrl("agentRegistration");
  //post Registration form agent data and get response
  var submitReg = valouxService.getData(url,postData);
  submitReg.then(function(data){
    
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.registerFormAgentError = false;
      $scope.registerFormSuccess= true;
      window.scrollTo(500, 0);
      $scope.registerForm = false;
      
    }else{
      $scope.registerFormSuccess= false;
      window.scrollTo(500, 0);
      $scope.registerForm = true;
      $scope.registerFormAgentError = true;
      $scope.errorMessage = data.errorMessage;
    }
  },function(){
    console.log("Error in checking Registration form agent");
  });
};

/* Forget password */
$scope.forgetPassword = function() {

  var postData = new Object();
  postData.reqPaparam = {  
      "getSecurityQuestionRequest":$scope.user
    };
  
  /*get service url forget password*/
  var url = valouxService.getWebServiceUrl("getSecurityQuestion");
  //post forget password data and get response 
  var submitReg = valouxService.getData(url,postData);
  submitReg.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.isForgetQuestion = true;
      window.scrollTo(500, 0);
      $scope.user.securityQuestion = data.resData.securityQuestion;
      $scope.registerFormError = false;
    }
    else{
      $scope.errorMessage = data.errorMessage;
      window.scrollTo(500, 0);
      $scope.isForgetQuestion = false;
      $scope.registerFormError = true;
    }
  },function(){
    console.log("Error in checking forget password");
  });
};

/* Forget password verify question*/
$scope.forgetPasswordVerify = function() {
  if($scope.user.securityAnswer){
  var postData = new Object();
  postData.reqPaparam = {  
      "verifySecurityAnswer":$scope.user
    };
  
  /*get service url forget password*/
  var url = valouxService.getWebServiceUrl("verifySecurityAnswer");
  //post forget password data and get response 
  var submitReg = valouxService.getData(url,postData);
  submitReg.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.registerFormSuccess = true;
      window.scrollTo(500, 0);
      $scope.registerFormError = false;
      $scope.isForgetQuestion = false;
      $scope.isEmailID = false;
    }
    else{
      $scope.errorMessage = data.errorMessage;
      $scope.isForgetQuestion = true;
      window.scrollTo(500, 0);
      $scope.registerFormError = true;
      $scope.registerFormSuccess = false;

    }
  },function(){
    console.log("Error in checking forget password");
  });
}
else{
  $scope.registerFormError = false;
}
};

/* Forget password verify email*/
$scope.resetPasswordVerificationEmail = function() {

  var tokenObj = $location.search().ecptycode;

  var postData = new Object();
  postData.reqPaparam = {  
    "verifyKeyAndChangePasswordRequest":$scope.user
  };
  
  /*get service url forget password*/
  var url = valouxService.getWebServiceUrl("verifyKeyAndChangePassword");

  postData.reqPaparam.verifyKeyAndChangePasswordRequest.forgetPasswordKey = tokenObj;
  //post forget password data and get response 
  var submitReg = valouxService.getData(url,postData);
  submitReg.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.registerFormSuccess = true;
      window.scrollTo(500, 0);
      $scope.registerFormError = false;
      $scope.successMessage = data.successMessage;
    }
    else{
      $scope.registerFormError = true;
      window.scrollTo(500, 0);
      $scope.registerFormSuccess = false;
      $scope.errorMessage = data.errorMessage;
    }
  },function(){
    console.log("Error in checking forget password");
  });
};

/* Change password */
$scope.changePassword = function() {
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var postData = new Object();
  postData.reqPaparam = {  
      "changePasswordRequest":$scope.user
    };
  
  /*get service url Change password*/
  var url = valouxService.getWebServiceUrl("changePassword");
  postData.reqPaparam.changePasswordRequest.userPublicKey = savedData.memberShipKey;

  //post Change password data and get response 
  var submitReg = valouxService.getData(url,postData);
  submitReg.then(function(data){
    console.log(data);
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.registerFormSuccess = true;
      window.scrollTo(500, 0);
      $scope.registerFormError= false;
      $scope.successMessage = data.successMessage;
    }
    else if(data.sCode == RESPONSE_CODE.ERROR) {
      $scope.registerFormSuccess = false;
      window.scrollTo(500, 0);
      $scope.registerFormError= true;
      $scope.errorMessage = "Please enter correct old password";
    }
    else{
      $scope.registerFormSuccess = false;
      $scope.registerFormError= true;
      $scope.errorMessage = data.errorMessage;
    }
  },function(){
    console.log("Error in checking Change password");
  });
};



/*Edit consumer profile*/
 
$scope.editConsumerForm = function(){
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var postData = new Object();

  postData.reqPaparam = { "userPublicKey" : savedData.memberShipKey};
  
  var url = valouxService.getWebServiceUrl("getConsumerInfo");
  var consumerInfo = valouxService.getData(url,postData);
  
  consumerInfo.then(function(data){
   
    if(data.sCode == RESPONSE_CODE.SUCCESS){

      $scope.class = "active";
      //get date of birth

      var consumerBirthday = data.resData.userData.birthday;
      if(consumerBirthday){
        month = masterDataService.month();
        var date = new Date(consumerBirthday);
        var month = month[date.getMonth()];
        getdate =  month + ' ' + date.getDate() + ', ' +  date.getFullYear();
        $scope.birthday = getdate;
      }else{
        $scope.birthday = "";
      }
      
      
      $scope.emailId  = data.resData.userData.emailId;
      $scope.mobilePhone  = data.resData.userData.mobilePhone;
     
      /*Store value in array personalPreferences*/
      
      $scope.user ={
        firstName : data.resData.userData.firstName,
        lastName  : data.resData.userData.lastName,
        gender : data.resData.userData.gender,
        incomeRange  : data.resData.userData.incomeRange,
        maritalStatus : data.resData.userData.maritalStatus,
        middleName : data.resData.userData.middleName
      }

      
      jewelryPurchase = [];
      jewelryInsurance = [];
      jewelryServices = [];
      jewelryDocumentation = [];
      if(data.resData.userData.consumerAbout){
        angular.forEach(data.resData.userData.consumerAbout, function(value, key) {
      
        if(value.aboutType == 1){
          jewelryPurchase.push(value.userAbout);
        }
        else if(value.aboutType == 2){
          jewelryInsurance.push(value.userAbout);
        }
        else if(value.aboutType == 3){
          jewelryServices.push(value.userAbout);
        }
        else if(value.aboutType == 4){
          jewelryDocumentation.push(value.userAbout);
        }
       });
      }
      
      var names = findElement(masterDataService.jewelryPurchases(), "value", jewelryPurchase); // x is {"name":"k2", "value":"hi"}
      
      if(typeof names == "undefined"){
        $scope.jewelryPurchase = "";  
      }else{
        $scope.jewelryPurchase = names["value"];    
      }

      var names = findElement(masterDataService.jewelryInsurances(), "value", jewelryInsurance); // x is {"name":"k2", "value":"hi"}
      
      if(typeof names == "undefined"){
        $scope.jewelryInsuranceType = "";  
      }else{
        $scope.jewelryInsuranceType = names["value"];    
      }

      var names = findElement(masterDataService.jewelryService(), "value", jewelryServices); // x is {"name":"k2", "value":"hi"}
      
      if(typeof names == "undefined"){
        $scope.jewelryServices = "";  
      }else{
        $scope.jewelryServices = names["value"];    
      }

      var names = findElement(masterDataService.jewelryDocumentation(), "value", jewelryDocumentation); // x is {"name":"k2", "value":"hi"}
      
      if(typeof names == "undefined"){
        $scope.jewelryDocumentations = "";  
      }else{
        $scope.jewelryDocumentations = names["value"];    
      }

      $scope.metal = [];
      $scope.gemstone = [];
      $scope.diamond = [];
      
      if(data.resData.userData.jewelryComponents){
        angular.forEach(data.resData.userData.jewelryComponents, function(value, key) {
          
        if(value.componentsType == 1){
          $scope.metal.push(value.components);
        }
        else if(value.componentsType == 2){
          $scope.gemstone.push(value.components);
        }
        else if(value.componentsType == 3){
          $scope.diamond.push(value.components);
        }
        
       });
      }
      
      $scope.jewelryTypes = [];
      $scope.jewelryDesign = [];
      $scope.jewelryStyle = [];
      
      if(data.resData.userData.personalPreferences){
        angular.forEach(data.resData.userData.personalPreferences, function(value, key) {
        
        if(value.personalType == 1){
          $scope.jewelryTypes.push(value.personalId);
        }
        else if(value.personalType == 2){
          $scope.jewelryDesign.push(value.personalId);
        }
        else if(value.personalType == 3){
          $scope.jewelryStyle.push(value.personalId);
        }
        
       });
      }

      /*Push value in array of interested in*/
      $scope.user.interestedIn = [];
      if(data.resData.userData.interestedIn){
        angular.forEach(data.resData.userData.interestedIn, function(value, key) {
          $scope.user.interestedIn.push(value.interestdId);
          });
      }

      $scope.storeData.addressLine1 = data.resData.userData.userAddress.addressLine1;
      $scope.storeData.addressLine2 = data.resData.userData.userAddress.addressLine2;
      $scope.storeData.city = data.resData.userData.userAddress.city;
      $scope.storeData.zipCode = data.resData.userData.userAddress.zipCode;
      $scope.storeData.state = data.resData.userData.userAddress.state;
      $scope.storeData.country = data.resData.userData.userAddress.country;
      
      var addressConcate = '';
      addressConcate+= data.resData.userData.userAddress.addressLine1;
      if(typeof data.resData.userData.userAddress.addressLine2 == 'undefined' || data.resData.userData.userAddress.addressLine2 == ''){
        addressConcate+= '';
      }
      else{
        addressConcate+= ', '+data.resData.userData.userAddress.addressLine2;
      }
      
      addressConcate+= ', '+data.resData.userData.userAddress.city;
      addressConcate+= ', '+data.resData.userData.userAddress.state;
      /*addressConcate+= ' - '+data.resData.userData.userAddress.zipCode;*/
      addressConcate+= ', '+data.resData.userData.userAddress.country;

      $scope.userAddress  = addressConcate;

      // set time out function for dropdown trigger select
      $timeout(function(){
        angular.element("#maritalStatus").material_select();
        angular.element("#incomeRange").material_select();
        angular.element("#jewelryPurchase").material_select();
        angular.element("#jewelryServices").material_select();
        angular.element("#jewelryDocumentations").material_select();
        angular.element("#jewelryInsuranceType").material_select();
        angular.element("#gemstone").material_select();
        angular.element("#diamond").material_select();
        angular.element("#metal").material_select();
      }, 500);
      

    }
    else{
      console.log("Error in checking editing consumerInfo");
    }

  },function(){
    console.log("Error in checking login");
  });
};


/*get profile detail responce from JSON file*/
 
$scope.getProfileDetailConsumer = function(){
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var postData = new Object();
  postData.reqPaparam = { "userPublicKey" : savedData.memberShipKey};
  
  var url = valouxService.getWebServiceUrl("getConsumerInfo");
  var consumerInfo = valouxService.getData(url,postData);
  
  consumerInfo.then(function(data){
    
    if(data.sCode == RESPONSE_CODE.SUCCESS){

      
      //get date of birth
      var getdate = "";
      if(data.resData.userData.birthday){
      var consumerBirthday = data.resData.userData.birthday;
      month = masterDataService.month();
      var date = new Date(consumerBirthday);
      var month = month[date.getMonth()];
      getdate = month + ' ' + date.getDate() + ', ' +  date.getFullYear();
      }

      if(getdate){
          $scope.birthday = getdate;
      }else{
        $scope.birthday = "";
      }
      
      $scope.emailId  = data.resData.userData.emailId;
      $scope.mobilePhone  = data.resData.userData.mobilePhone;
      /*Profile image*/
      $scope.profileImages  = data.resData.userData.imageUrl;

      $scope.user ={
        firstName : data.resData.userData.firstName,
        lastName  : data.resData.userData.lastName,
        middleName : data.resData.userData.middleName
      }

      /*Push value in array of personal prefrence in*/
      $scope.jewelryTypes = [];
      $scope.jewelryDesign = [];
      $scope.jewelryStyle = [];
      if(data.resData.userData.personalPreferences){
        angular.forEach(data.resData.userData.personalPreferences, function(value, key) {
          $scope.hideDetailsPersonal = true;
          if(value.personalType == 1){
          $scope.jewelryTypes.push(value.personalName);
          }
          if(value.personalType == 2){
          $scope.jewelryDesign.push(value.personalName);
          }
          if(value.personalType == 3){
          $scope.jewelryStyle.push(value.personalName);
          }
          });
      }

      /*Push value in array of interested in*/
      $scope.user.interestedIn = [];
      if(data.resData.userData.interestedIn){
        angular.forEach(data.resData.userData.interestedIn, function(value, key) {
          $scope.user.interestedIn.push(value.interestedName);
          });
      }

      /*condition for gender*/
      if(data.resData.userData.gender == 1){
        $scope.user.gender = "Male"
      }
      else if(data.resData.userData.gender == 2){
        $scope.user.gender = "Female"
      }
      else if(data.resData.userData.gender == 3){
        $scope.user.gender = "Other"
      }else{
        $scope.user.gender = ""
      }

      //find array maritalStatus name
      var names = findElement(masterDataService.relationshipCategories(), "value", data.resData.userData.maritalStatus); // x is {"name":"k2", "value":"hi"}
      if(typeof names == "undefined"){
      $scope.user.maritalStatus = "";
      }
      else{
        $scope.user.maritalStatus = names["name"];    
      }
      
      var names = findElement(masterDataService.incomeRange_categories(), "value", data.resData.userData.incomeRange); // x is {"name":"k2", "value":"hi"}
      if(typeof names == "undefined"){
        $scope.user.incomeRange = "";  
      }else{
        $scope.user.incomeRange = names["name"];    
      }
            
      jewelryPurchase = [];
      jewelryInsurance = [];
      jewelryServices = [];
      jewelryDocumentation = [];
      if(data.resData.userData.consumerAbout){
        angular.forEach(data.resData.userData.consumerAbout, function(value, key) {
          
        if(value.aboutType == 1){
          jewelryPurchase.push(value.userAbout);
        }
        else if(value.aboutType == 2){
          jewelryInsurance.push(value.userAbout);
        }
        else if(value.aboutType == 3){
          jewelryServices.push(value.userAbout);
        }
        else if(value.aboutType == 4){
          jewelryDocumentation.push(value.userAbout);
        }
       });
      }
      
      
      var names = findElement(masterDataService.jewelryPurchases(), "value", jewelryPurchase); // x is {"name":"k2", "value":"hi"}
      
      if(typeof names == "undefined"){
        $scope.user.jewelryPurchases = "";  
      }else{
        $scope.user.jewelryPurchases = names["name"];    
      }

      var names = findElement(masterDataService.jewelryInsurances(), "value", jewelryInsurance); // x is {"name":"k2", "value":"hi"}
      
      if(typeof names == "undefined"){
        $scope.user.jewelryInsurances = "";  
      }else{
        $scope.user.jewelryInsurances = names["name"];    
      }

      var names = findElement(masterDataService.jewelryService(), "value", jewelryServices); // x is {"name":"k2", "value":"hi"}
      
      if(typeof names == "undefined"){
        $scope.user.jewelryService = "";  
      }else{
        $scope.user.jewelryService = names["name"];    
      }

      var names = findElement(masterDataService.jewelryDocumentation(), "value", jewelryDocumentation); // x is {"name":"k2", "value":"hi"}
      
      if(typeof names == "undefined"){
        $scope.user.jewelryDocumentation = "";  
      }else{
        $scope.user.jewelryDocumentation = names["name"];    
      }

      metals = [];
      gemstones = [];
      diamonds = [];
      
      if(data.resData.userData.jewelryComponents){
        angular.forEach(data.resData.userData.jewelryComponents, function(value, key) {
          
        if(value.componentsType == 1){
          metals.push(value.components);
        }
        else if(value.componentsType == 2){
          gemstones.push(value.components);
        }
        else if(value.componentsType == 3){
          diamonds.push(value.components);
        }
        
       });
      }
      $scope.metalArray = []
      angular.forEach(metals, function(value1) {
        angular.forEach(masterDataService.metals(), function(value2){
          if(value1 === value2.value){
              $scope.metalArray.push(value2.name);
            }
          });
      });
      $scope.gemstoneArray = []
      angular.forEach(gemstones, function(value1) {
        angular.forEach(masterDataService.gemstones(), function(value2){
          if(value1 === value2.value){
              $scope.gemstoneArray.push(value2.name);
            }
          });
      });
      $scope.diamondArray = []
      angular.forEach(diamonds, function(value1) {
        angular.forEach(masterDataService.diamonds(), function(value2){
          if(value1 === value2.value){
              $scope.diamondArray.push(value2.name);
            }
          });
      });
      
      
      var addressConcate = '';
      addressConcate+= data.resData.userData.userAddress.addressLine1;
      if(typeof data.resData.userData.userAddress.addressLine2 == 'undefined' || data.resData.userData.userAddress.addressLine2 == ''){
        addressConcate+= '';
      }
      else{
        addressConcate+= ', '+data.resData.userData.userAddress.addressLine2;
      }
      
      addressConcate+= ', '+data.resData.userData.userAddress.city;
      addressConcate+= ', '+data.resData.userData.userAddress.state;
      addressConcate+= ' '+data.resData.userData.userAddress.zipCode;
      addressConcate+= ', '+data.resData.userData.userAddress.country;

      $scope.userAddress  = addressConcate;
    }
    else{
      console.log("Error in checking editing consumerInfo");
    }

  },function(){
    console.log("Error in checking login");
  });
};

//find name in array for incomeRange_categories, relationshipCategories
function findElement(arr, propName, propValue) {
  for (var i=0; i < arr.length; i++)
    if (arr[i][propName] == propValue){
      return arr[i];
    }
  // will return undefined if not found; you could return a default instead
}

/* save edit profile data */
$scope.editSubmitRegistration = function() {
  
  //get date of birth
  var getdate = "";

  if($scope.birthday){
  var dob = $scope.birthday;
  date = new Date(dob);
  getdate = (date.getMonth() + 1) + '/' + date.getDate() + '/' +  date.getFullYear();
  }
  /*get and define variable for address*/
  var userOrAgentAddressArray ={
    addressLine1: $scope.storeData.addressLine1,
    addressLine2 : $scope.storeData.addressLine2,
    country : $scope.storeData.country,
    state : $scope.storeData.state,
    zipCode : $scope.storeData.zipCode,
    city : $scope.storeData.city
  };
  
  /*Define array consumer about section*/
  
  var consumerAbout = {}
  if($scope.jewelryPurchase){
    consumerAbout.JewelryPurchases = [$scope.jewelryPurchase];
  }
  else{
    consumerAbout.JewelryPurchases = [];
  }
  if($scope.jewelryInsuranceType){
    consumerAbout.JewelryInsurance = [$scope.jewelryInsuranceType];
  }
  else{
    consumerAbout.JewelryInsurance = [];
  }
  if($scope.jewelryServices){
    consumerAbout.JewelryService = [$scope.jewelryServices];
  }
  else{
    consumerAbout.JewelryService = [];
  }
  if($scope.jewelryDocumentations){
    consumerAbout.JewelryDocumentation = [$scope.jewelryDocumentations];
  }
  else{
    consumerAbout.JewelryDocumentation = [];
  }
  

  
  /*Define array consumer personal Preferences section*/

  var personalPreferences = {
      JewelryTypes : $scope.jewelryTypes,
      JewelryDesign : $scope.jewelryDesign,
      JewelryStyle : $scope.jewelryStyle,
    };


  /*Define array consumer jewelry Components section*/

  var jewelryComponents = {}

  if($scope.metal){
    jewelryComponents.metals = $scope.metal;
  }
  else{
    consumerAbout.metals = [];
  }
  if($scope.gemstone){
    jewelryComponents.gemstones = $scope.gemstone;
  }
  else{
    consumerAbout.gemstones = [];
  }
  if($scope.diamond){
    jewelryComponents.diamonds = $scope.diamond;
  }
  else{
    consumerAbout.diamonds = [];
  }

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  var postData = new Object();
  
  if($scope.user.maritalStatus == null){
    $scope.user.maritalStatus = 0;
  }
  if($scope.user.incomeRange == null){
    $scope.user.incomeRange = 0;
  }
  
  postData.reqPaparam = {  
      "userDataToUpdate": $scope.user
    };
  
  /*Post data consumerAbout*/
  postData.reqPaparam.userDataToUpdate.consumerAbout = consumerAbout;
  
  /*Post data personalPreferences*/
  postData.reqPaparam.userDataToUpdate.personalPreferences = personalPreferences;
  
  /*Post data jewelryComponents*/
  postData.reqPaparam.userDataToUpdate.jewelryComponents = jewelryComponents;


  /*Post data userOrAgentAddress*/
  postData.reqPaparam.userDataToUpdate.userAddress = userOrAgentAddressArray;

  /*Post data user public key*/
  postData.reqPaparam.userDataToUpdate.userPublicKey = savedData.publicKey;

  //Post data birthday
  if(getdate){
    postData.reqPaparam.userDataToUpdate.birthday = getdate;
  }else{
    postData.reqPaparam.userDataToUpdate.birthday = "";
  }

  /*get service url userRegistration*/
  var url = valouxService.getWebServiceUrl("updateConsumerInfo");
  //post Registration form consumer data and get response
  var submitReg = valouxService.getData(url,postData);
  submitReg.then(function(data){
    console.log(data);
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      window.location = "consumer-details.html";
      savedData.resData.loginInfo.firstName = $scope.user.firstName
      savedData.resData.loginInfo.lastName = $scope.user.lastName
      sessionStorage.setItem("saveValouxData", JSON.stringify(savedData));
    }else{
      $scope.errorMessage = data.errorMessage;
      window.scrollTo(500, 0);
      $scope.registerFormSuccess = false;
      $scope.registerFormError= true;
    }
  },function(){
    console.log("Error in checking Registration form consumer");
  });
};

/*get responce for interested in data getUserInterestIn API*/
 
$scope.initInterestedIn = function(){
  $scope.$parent.fullPageBlockLoading = false;

  var url = valouxService.getWebServiceUrl("getUserInterestIn");
  var consumerInfo = valouxService.getData(url);
  consumerInfo.then(function(data){
    if(data.sCode == RESPONSE_CODE.SUCCESS){
      $scope.interestedIn  = data.resData.userInterestIn;
    }
    else{
      console.log("Error checking in interested in");
    }

  },function(){
    console.log("Error in checking login");
  });
};

/*get responce for personal preference in data API*/
 
$scope.initPersonalPrefrence = function(){
  //$scope.personalPreference = [];
  var url = valouxService.getWebServiceUrl("getUserPersonalPreference");
  var consumerInfo = valouxService.getData(url);
  consumerInfo.then(function(data){
    if(data.sCode == RESPONSE_CODE.SUCCESS){
      $scope.personalPreferencesJewelryTypes  = data.resData.allPreferenceData;
      $scope.personalPreferencesJewelryDesign  = data.resData.allPreferenceData;
      $scope.personalPreferencesJewelryStyle  = data.resData.allPreferenceData;
      $timeout(function(){
        angular.element("#jewelryTypes").material_select();
        angular.element("#jewelryDesign").material_select();
        angular.element("#jewelryStyle").material_select();
      }, 500);
    }
    else{
      console.log("Error in checking personal Preferences");
    }

  },function(){
    console.log("Error in checking login");
  });
};

// edit & add value in array for personal Preference in consumer form
$scope.personalPref = function (personalPreferences) { 

  var personal = $scope.user.personalPreferences.indexOf(personalPreferences.personalId);

  // is currently selected
  if (personal > -1) {
    $scope.user.personalPreferences.splice(personal, 1);
  }
  // is newly selected
  else {
    $scope.user.personalPreferences.push(personalPreferences.personalId);
  }
};

// edit & add value in array for interest in consumer form
$scope.interestedInConsumer = function (interestedIn) {
  var interest = $scope.user.interestedIn.indexOf(interestedIn.interestId);
  // is currently selected
  if (interest > -1) {
    $scope.user.interestedIn.splice(interest, 1);
  }
  // is newly selected
  else {
    $scope.user.interestedIn.push(interestedIn.interestId);
  }
};


/*Edit Agent profile get responce from JSON file*/
 
$scope.editAgentForm = function(){

  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var postData = new Object();
  postData.reqPaparam = { "agentPublicKey" : savedData.memberShipKey};

  var url = valouxService.getWebServiceUrl("getAgentInfo");
  var consumerInfo = valouxService.getData(url,postData);

  consumerInfo.then(function(data){
    if(data.sCode == RESPONSE_CODE.SUCCESS){
      $scope.class = "active";
      delete $scope.signatureImage;
      delete $scope.signatureImageName;
      angular.element(document.getElementById('fileSignature')).val('');
      $scope.user ={
        firstName : data.resData.agentData.firstName,
        lastName  : data.resData.agentData.lastName,
        middleName : data.resData.agentData.middeName,
        signName : data.resData.agentData.signName
      }
      if($scope.user.signName){
        $scope.signatureSelect = 2;
      }else{
        $scope.signatureSelect = 1;

      }
      $scope.uploadedSignature = false;
      $timeout(function(){ angular.element('.tooltipped').tooltip(); }, 500);
      $scope.signUrl = data.resData.agentData.signUrl;
      $scope.emailId  = data.resData.agentData.emailId;
      $scope.mobilePhone  = data.resData.agentData.mobilePhone;
    }
    else{
      console.log("Error in checking editing consumerInfo");
    }

  },function(){
    console.log("Error in checking editing consumerInfo");
  });
};

/* save edit profile agent data */
$scope.editSubmitAgentRegistration = function() {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  var postData = new Object();
  postData.reqPaparam = {  
      "agentDataToUpdate":$scope.user
    };
    if($scope.signatureImage){
        postData.reqPaparam.agentDataToUpdate.imageContent = $scope.signatureImage;

    }else{
      postData.reqPaparam.agentDataToUpdate.signName = $scope.user.signName;
      postData.reqPaparam.agentDataToUpdate.isImageDeleted = true;

    }if($scope.isImageDeleted){
      postData.reqPaparam.agentDataToUpdate.isImageDeleted = $scope.isImageDeleted;
    }
  postData.reqPaparam.agentDataToUpdate.imageName = $scope.signatureImageName;
    
  /*get service url userRegistration*/
  var url = valouxService.getWebServiceUrl("updateAgentInfo");
  //post Registration form consumer data and get response
  var submitReg = valouxService.getData(url,postData);

  postData.reqPaparam.agentDataToUpdate.agentPublicKey = savedData.publicKey;

  submitReg.then(function(data){
    
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.registerFormAgentSuccess = true;
      $scope.registerFormAgentError = false;
      $scope.successMessage = data.successMessage;
      window.scrollTo(500, 0);
      $timeout(function() { $scope.registerFormAgentSuccess = false }, 1500);      
      savedData.resData.loginInfo.firstName = $scope.user.firstName
      savedData.resData.loginInfo.lastName = $scope.user.lastName
      sessionStorage.setItem("saveValouxData", JSON.stringify(savedData));
      $scope.editAgentForm();
      }else{
      $scope.registerFormAgentSuccess = false;
      $scope.registerFormAgentError = true;
      window.scrollTo(500, 0);
      $scope.errorMessage = data.errorMessage;
    }
  },function(){
    console.log("Error in checking Registration form consumer");
  });
};

/* save edit profile agent data */
$scope.changeProfileImage = function() {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  var imageContants, imageNames = ""
  angular.forEach(profileImage, function(profileImage){
    imageContants= profileImage.imageContent
    imageNames = profileImage.imageName
  })
  
  var postData = new Object();
  postData.reqPaparam = {  
      "publicKey":savedData.publicKey,
      "imageContent" : imageContants,
      "imageName" : imageNames
    };
  
  /*get service url changeProfieImage*/
  var url = valouxService.getWebServiceUrl("changeProfieImage");
  //post Registration form changeProfieImage data and get response
  var submitReg = valouxService.getData(url,postData);

  submitReg.then(function(data){
    
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      $scope.profileImages = data.resData.imageUrl
      $scope.inValidimg = false;
    }else{
      $scope.inValidimg = true;
      $scope.inValidimgText = "Please try again.";       
    }
  },function(){
    console.log("Error in upload image");
  });
};
/* upload profile image*/
$scope.uploadProfileImage = function(e){
    //var elementId = e.data.elementId; 
    var imageObj = e.target;
    var imageContent = "";
    var imageName = "";
    profileImage = [];
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
            }
        })
        
        if(returnValue)
        { 
          $scope.inValidimg = true;
          $scope.inValidimgText = "Please select [jpeg,png] image.";
          $scope.$apply();
          return;
        }
        else if(fileSize > 2)
        {
          $scope.inValidimg = true;
          $scope.inValidimgText = "Please select image less or equal to 2 MB image.";
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
       imageContent = newImageObj;   
       profileImage.push({'imageContent' : imageContent,'imageName':imageName});
       $scope.changeProfileImage(profileImage);
           
        };       
        FR.readAsDataURL( imageObj.files[0] );                
    }
}

/* save edit profile agent data */
$scope.agentListing = function(successMessage) {
var savedItem = sessionStorage.getItem("saveValouxData");
var savedData = JSON.parse(savedItem);
var postData = new Object();
    postData.reqPaparam = { 
      "agentPublicKey" : savedData.memberShipKey, 
    }
     var url = valouxService.getWebServiceUrl("getAgentInfoAssociatedWithStore");
  //post data and get response
  var submitAgent = valouxService.getData(url,postData);
  submitAgent.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) { 
   
      if(typeof data.resData.agentData == "undefined" ){
        $scope.noAgentAvaliable = true;
 
      }else{
        $scope.noAgentAvaliable = false;
        $scope.agentkey= savedData.memberShipKey;
        $scope.totalItem = data.resData.agentData.length;
        $scope.agentDatas = data.resData.agentData;
        if(successMessage){
          window.scrollTo(500,0);
          $scope.successMessage = successMessage;
          $timeout(function(){ angular.element('#showMessage').hide('slow');  }, 2000);
        }

      }
    }else{
      $scope.noAgentAvaliable = true;
    }
    $timeout(function(){ angular.element('.tooltipped').tooltip(); }, 500);
  },function(){
    console.log("Error in get item list");
  });
}

$scope.agentStatus = function(agentkey,status) {

  $scope.agentkey=agentkey;
  $scope.Status=status;
  var postData = new Object();
  if(status==1)
    {
     postData.reqPaparam = { 
      "userPublicKey" : agentkey,status:2, 
    } 
    }else{
    postData.reqPaparam = { 
      "userPublicKey" : agentkey,status:1, 
    }  
    }
  var agentdata=new Object();
  var url = valouxService.getWebServiceUrl("activeInactiveAgent");
  //post data and get response
  var submitAgent = valouxService.getData(url,postData);
  submitAgent.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) { 
   
      if(typeof data.resData == "undefined" ){
        $scope.noAgentAvaliable = true;
 
      }else{
       $scope.agentListing(data.successMessage);
       angular.element('#showMessage').show(); 
      }
    }else{
      $scope.noAgentAvaliable = true;
    }
    $timeout(function(){ angular.element('.tooltipped').tooltip(); }, 500);
  },function(){
    console.log("Error in get agent list");
  });
  
}

/* consumer Listing data */
$scope.consumerListing = function() {
var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);

  var consumerdata=new Object();

var postData = new Object();
    postData.reqPaparam = { 
      "publicKey" : savedData.memberShipKey,
      
    }
     var url = valouxService.getWebServiceUrl("getUserListSharedToAgent");
  //post data and get response
  var submitAgent = valouxService.getData(url,postData);
  submitAgent.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) { 
   
      if(typeof data.consumerList == "undefined" || data.consumerList=='' ){
        $scope.noUserAvaliable = true; 
 
      }else{ 
        $scope.noUserAvaliable = false;
       // $scope.item = data.resData.collectionData;
       $timeout(function() { angular.element("select").material_select();}, 500);
        $scope.consumerlength = data.consumerList.length;
        $scope.consumerdatas = data.consumerList;
        
      }
    }else{
      $scope.noUserAvaliable = true;
    }
    $timeout(function(){ angular.element('.tooltipped').tooltip(); }, 500);
  },function(){
    console.log("Error in get item list");
  });
}



$scope.addConsumerItem = function(consumerinfo,type) {
if(consumerinfo){ 
    sessionStorage.setItem("agentInformation", consumerinfo);
  }else{
    sessionStorage.removeItem("agentInformation");
   // event.viewItemBy = '';
  }
  $timeout(function(){ angular.element('ul.tabs').tabs(); angular.element('.dropdown-button').dropdown();}, 100);
   if(type==1){
   $window.location = "item-agent.html#/add";
  }if(type==2){
    $window.location = "collection-agent.html#/add";
  }if(type==3){
    $window.location = "appraisal-agent.html#/add"
  }
}

$scope.consumerPage = function(consumerinfo,type) {
if(consumerinfo){ 
    sessionStorage.setItem("agentInformation", consumerinfo);
  }else{
    sessionStorage.removeItem("agentInformation");
   // event.viewItemBy = '';
  }
  $timeout(function(){ angular.element('ul.tabs').tabs(); angular.element('.dropdown-button').dropdown();}, 100);
   if(type==1){
   $window.location = "item-agent.html#/";
  }if(type==2){
    $window.location = "collection-agent.html#/";
  }if(type==3){
    $window.location = "appraisal-agent.html#/";
  }
}


$scope.agentself = function() {
var savedItem = sessionStorage.getItem("saveValouxData");
var savedData = JSON.parse(savedItem);
 return function(agentdata) {
        return agentdata.rKey != savedData.memberShipKey;
    }
}

/*selected upload signature or name*/
$scope.signatureSelected = function(sign) {

 if(sign){
  $scope.user.signName = '';
  angular.element('#agentActive').removeClass();
 }else{
  delete $scope.signatureImage;
  delete $scope.signatureImageName;
  angular.element(document.getElementById('fileSignature')).val('');
  $scope.inValidimg = false;
  $scope.uploadedSignature = false;
 }

}

/*selected upload signature or name*/
$scope.removeSignatureImage = function() {
  delete $scope.signatureImage;
  delete $scope.signatureImageName;
  angular.element(document.getElementById('fileSignature')).val('');
  $scope.uploadedSignature = false;
  $scope.isImageDeleted = true;
  $scope.inValidimg = false;
  $scope.signUrl = false;
  $('.tooltipped').tooltip('remove');
}


/* upload signature for agent*/
$scope.uploadSignatureImage = function(e){
    //var elementId = e.data.elementId; 
    var imageObj = e.target;
    var imageContent = "";
    var imageName = "";
    profileImage = [];
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
                $scope.inValidimg = false;
                $scope.uploadedSignature = true;
            }
        })
        
        if(returnValue)
        { 
          $scope.inValidimg = true;
          $scope.inValidimgText = "Please select [jpeg,png] image.";
          $scope.$apply();
          return;
        }
        else if(fileSize > 2)
        {
          $scope.inValidimg = true;
          $scope.inValidimgText = "Please select image less or equal to 2 MB image.";
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
       imageContent = newImageObj;   
       
       $scope.signatureImage = imageContent;
       $scope.signatureImageName = imageName;
      };       
        FR.readAsDataURL( imageObj.files[0] );                
    }
}
}]);


          