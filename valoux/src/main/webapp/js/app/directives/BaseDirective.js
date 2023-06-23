var valouxDirective = angular.module('valouxApp.valouxDirectives', []);

//Date picker directive
valouxDirective.directive('headerSection',function(){
    // header section for without loggedin pages
    return{
        restrict:'E',
        templateUrl:"include/header.html", 
        link: function(scope,element){
             
             
        }
    }
})
.directive('ngConfirmClick', [
  function(){
    return {
      priority: -1,
      restrict: 'A',
      link: function(scope, element, attrs){
        element.bind('click', function(e){
          var message = attrs.ngConfirmClick;
          if(message && !confirm(message)){
            e.stopImmediatePropagation();
            e.preventDefault();
          }
        });
      }
    }
  }
])
.directive('headerLoggedin',function(){
    // header section for loggedin pages
    return{
        restrict:'E',
        templateUrl:"include/header-loggedin.html", 
        link: function(scope,element){
             
             
        }
    }
})
.directive('headerAdmin',function(){
    // header section for loggedin pages
    return{
        restrict:'E',
        templateUrl:"header.html", 
        link: function(scope,element){
             
             
        }
    }
})
.directive('footerElement', function($parse) {
  return { // Footer directive globally used
    restrict: 'E',
    templateUrl:'include/footer.html' //include template url
  };
})
.directive('footerAdmin', function($parse) {
  return { // Footer directive globally used
    restrict: 'E',
    templateUrl:'footer.html' //include template url
  };
})
.directive('login', function($parse, RESPONSE_CODE, ROLE_TYPE) {
  //Login directive
  return {
    restrict: 'E',
    templateUrl:"include/login.html", //include template url
    controller: ['$scope','valouxService',function($scope, valouxService){
      $scope.user = new Object();

      /*Send otp for agent login*/
      $scope.submitLoginWithOtp = function() {
        var postData = new Object();
	//get temporary storage saveValouxDataAgent
	var savedItem = sessionStorage.getItem("saveValouxDataAgent");
        var savedData = JSON.parse(savedItem);
        postData.reqPaparam = {
          "authLoginCode" : $scope.user.otpVerification,
          "authUserID" : savedData.authUserID.toString()
        };

        /*get service url user Login*/
        var url = valouxService.getWebServiceUrl("verfyUserOTP");
        //post login data and get response
        var promiseCheckLogin = valouxService.getData(url,postData);
        promiseCheckLogin.then(function(data){
        
          if (data.sCode == RESPONSE_CODE.SUCCESS) {
	           sessionStorage.setItem("saveValouxData", savedItem);
	           sessionStorage.removeItem("saveValouxDataAgent", 'destroy temporary storage');
	          //redirect to dashboard
            window.location = "dashboard-agent.html";
            }else{
              $scope.errorMessage = 'Incorrect code. Please try again.';
              $scope.otpFormError = true;
          }
        },function(){
          console.log("Error in checking login");
        });
      };

      /*Login form*/
      $scope.submitLogin = function() {
        var postData = new Object();
        postData.reqPaparam = {
          "loginData":$scope.user
        };
        /*get service url user Login*/
        var url = valouxService.getWebServiceUrl("userLogin");
        //post login data and get response
        var promiseCheckLogin = valouxService.getData(url,postData);
        promiseCheckLogin.then(function(data){
            if (data.sCode == RESPONSE_CODE.SUCCESS && data.roleType == ROLE_TYPE.SUPERADMIN) {
                sessionStorage.setItem("saveValouxData", JSON.stringify(data));
                 window.location = "admin/store.html";
            }
            else if (data.sCode == RESPONSE_CODE.SUCCESS && data.roleType == ROLE_TYPE.AGENT) {
	           //set temporary storage saveValouxDataAgent
                sessionStorage.setItem("saveValouxDataAgent", JSON.stringify(data));
                $scope.otpFormError = false;
                $('#modal-verification').openModal({dismissible: false});
                $scope.user.otpVerification = '';
                $scope.otpForm.$setPristine();
                angular.element("#otplabel").removeClass('active');
                $('#loginModal').closeModal();
            }
            else if(data.sCode == RESPONSE_CODE.SUCCESS){
              sessionStorage.setItem("saveValouxData", JSON.stringify(data));
              $scope.loginFormError = false;
              window.location = "dashboard.html";
            }else{
            $scope.errorMessage = data.errorMessage;
            $scope.loginFormError = true;
          }
        },function(){
          console.log("Error in checking login");
        });
      };
                    
    }],
    link: function (scope, element, attrs) {

    }
  };

}).directive('share', function($parse, RESPONSE_CODE, ROLE_TYPE) {
	 
	  //Share directive
	  return {
	    restrict: 'E',
	    templateUrl:"include/share.html", //include template url
	    controller: ['$scope','valouxService','$timeout',function($scope, valouxService,$timeout){
	      $scope.share = new Object();

	      //on share model popup submit button click 
	  	$scope.submitForm = $scope.submitShare;
		
		// set share id for model popup which id is going to share and open model popup
		$scope.setShareId = function (shareItem,ShareItemType) {
			
		// unset variables already set for share html form	
		$scope.errorMessage = "";
	    $scope.shareFormError = false;
		$scope.noContact = true;
		$scope.shareContacts = "";	
		$scope.recipientEmail = "";
		$scope.shareItemObj = shareItem;	
		$scope.ShareItemType = ShareItemType;
		$scope.itemContactData=false;
		
			
				
	  var savedItem = sessionStorage.getItem("saveValouxData");
	  var savedData = JSON.parse(savedItem);
	  $scope.sharedByUser = savedData.memberShipKey;
	  var postData = new Object();
	  postData.reqPaparam = { 
	    "sharedBy" : savedData.memberShipKey
	  };
	 	// request to get share contact details
		
		var url = valouxService.getWebServiceUrl("getListOfShareContacts");
		
		
		  var submitItem = valouxService.getData(url,postData);
		
		  submitItem.then(function(data){
								   
			if (data.sCode == RESPONSE_CODE.SUCCESS) {
			  
			  if(typeof data.contactList == "undefined"){
				
					$scope.noContact = true;
			  		$scope.getItemList = false;
					
			  }else{
				  	
				// check how many records found
				if(Object.keys( data.contactList ).length>0)
				{
					$scope.noContact = false;
					$scope.itemContactData = data.contactList;
					$timeout(function(){
						$('#modal-share').openModal();			  
					   $("#shareExistingContactsSelect").material_select();
           }, 500);
								
				}else
				{
					$('#modal-share').openModal();	
					$scope.noContact = true;
			  		$scope.getItemList = false;
            $timeout(function(){
             $("#shareExistingContactsSelect").material_select();
           }, 500);
				}
			  }
			}else{
			 $('#modal-share').openModal();
			  $scope.noContact = true;
			  $scope.getItemList = false;
        $timeout(function(){
          $("#shareExistingContactsSelect").material_select();
        }, 500);
			 
			}
		  },function(){
			console.log("Error in get item share contact list");
		  });
	            
	      };
		  /* function to generate string of multiple select and set as request paparameter to share */
		  
		   $scope.selectionsChanged = function(itm){
         $scope.shareFormError = false; 

			   var itmLength = itm.length;
			   var loop= 1;
			   var shareKeyStr = ""
			   angular.forEach(itm, function(value, key) {
				if(loop==itmLength)
				{
					shareKeyStr =  shareKeyStr+value;
				}else
				{
				  shareKeyStr = shareKeyStr + value  + ",";
				}
				  loop++;
				});
			   
			   $scope.sharedTo= shareKeyStr;
			    
		   }
	      /*Share form submit functionality*/
	      $scope.submitShare = function() {
			 
			// check if email or already existing contacts submited
			if(!$scope.sharedTo && !$scope.recipientEmail)
			{
					
						$scope.errorMessage = "Please select/enter contacts to share.";
						$scope.shareFormError = true;	
			}else
			{
					var postData = new Object();
					postData.reqPaparam = {
					  "sharedBy":$scope.sharedByUser,
					  "sharedToEmail":$scope.recipientEmail,
					  "sharedItemId":$scope.shareItemObj,
					  "sharedItemType":$scope.ShareItemType,
					  "sharedTo":$scope.sharedTo
					};
					/*get service url item Share*/
					var url = valouxService.getWebServiceUrl("itemShareSubmit");
					//post share data and get response
					var checkShare = valouxService.getData(url,postData);
					checkShare.then(function(data){
						if(data.sCode == RESPONSE_CODE.SUCCESS){
						  // show thank you message
						  $scope.shareFormError = false;
						 
						 $timeout(function(){ $('#modal-share-agent').closeModal();$('#modal-share').closeModal();$('#modal-share-thankyou').openModal();}, 100);
						 
						 
						}else{
						$scope.errorMessage = data.successMessage;
						$scope.shareFormError = true;
						
					  }
					},function(){
					  console.log("Error in sharing item");
					});
			}
	      };
	                    
	    }],
	    link: function (scope, element, attrs) {

	    }
	  };

	}).directive('thankyoushare', function($parse) {
	  return { // thankyou directive used in consumer & agent form 
	    restrict: 'E',
	    templateUrl:'include/thankyoushare.html' //include template url
	  };
	}).directive('slideToggle', function() {
	  return { // slide toggle directive using for additional address field open in agent & cosumer 
	    restrict: 'A',
	    scope:{},
	    controller: function ($scope) {},
	    link: function(scope, element, attr) {
	      element.bind('click', function() {
	        var $slideBox = angular.element(attr.slideToggle);
	        var slideDuration = parseInt(attr.slideToggleDuration, 10) || 200;
	        $slideBox.stop().slideToggle(slideDuration);
	      });
	    }
	  };  
	}).directive('thankyouunshare', function($parse) {
		  return { // thankyou directive used in consumer & agent form 
		    restrict: 'E',
		    templateUrl:'include/thankyouunshare.html' //include template url
		  };	  
}).directive('thankyou', function($parse) {
return { // thankyou directive used in consumer & agent form 
  restrict: 'E',
  templateUrl:'include/thankyou.html' //include template url
};
}).directive('autofocusForm', function () {
  return {
    restrict: 'A',
    link: function (scope, elem) {
      // set up event handler on the form element
      elem.on('submit', function () {
        // find the first invalid element
        var firstInvalid = elem[0].querySelector('.ng-invalid');
        // if we find one, set focus
        if (firstInvalid) {
          firstInvalid.focus();
          window.scrollTo(0, firstInvalid.offsetTop); 
          //window.scrollTo(500, 100);
        }
      });
    }
  };
})
.directive('loading', ['$http' ,function ($http){
        // show loader whenever webservice call
        return {
            restrict: 'A',
            link: function (scope, elm, attrs) {

                scope.isLoading = function () {
                    return $http.pendingRequests.length > 0;
                };
                scope.$watch(scope.isLoading, function ( val ){ 
                    if( val ){
                        elm.show();
                    }else{
                        elm.hide();
                    }
                });
            }
        };

 }])
 .directive('customOnChange', function() {
  return {
    restrict: 'A',
    link: function (scope, element, attrs) {
      var onChangeFunc = scope.$eval(attrs.customOnChange);
      var elementId = scope.$eval(attrs.elementId);
      var dataobj = { elementId: elementId};
      if(attrs.elementType!='undefined'){
          dataobj.elementType = scope.$eval(attrs.elementType);
      } 
      element.bind('change',dataobj, onChangeFunc);      
    }
  };
}).filter('makephoneNo', function() { // add + sign if not in string for country code
  return function(input) {
    var phoneNo = input || '';
    //phoneNo = phoneNo.replace(/[^0-9]/g, '');
    phoneNo = parseInt(phoneNo);
    return "+"+phoneNo;
  };
}).directive('autoActive', ['$location', function ($location) {
  return { /* add active class on dashboard header current page*/
    restrict: 'A',
    scope: false,
    link: function (scope, element) {
      function setActive() {
        var path = $location.absUrl();
        var hash = window.location.hash;
        details = hash.substring(8, (hash.indexOf("#") == -1));
        /*Match path detail page*/
        if(details == '#/detail'){
          angular.element('#detail').addClass('header1-noshadow');
        }
        path = path.substring(0, (path.indexOf("#") == -1) ? path.length : path.indexOf("#"));
        var segments = path.split("/");
        var last = segments[segments.length-1];
       
        if(last == 'shared-request-agent-grid.html'){
          angular.element('.request').addClass('active');

        }
        if (last) { 
          angular.forEach(element.find('li'), function (li) {
            var anchor = li.querySelector('a');
            if (anchor.href.match(last + '(?=\\?|$)')) {
                angular.element(li).addClass('active');
            } else {
                angular.element(li).removeClass('active');
            }
          });
        }
      }
      setActive();
      scope.$on('$locationChangeSuccess', setActive);
    }
  }
}]).directive('fallbackSrc', function () {
  /*Default image for collection appraisal items*/
  var fallbackSrc = { 
    link: function postLink(scope, iElement, iAttrs) {
      iElement.bind('error', function() {
        angular.element(this).attr("src", iAttrs.fallbackSrc);
      });
      iAttrs.$observe('ngSrc', function(value) {
        if (!value && iAttrs.fallbackSrc) {
          iAttrs.$set('src', iAttrs.fallbackSrc);
        }
      });
    }
   }
   return fallbackSrc;
})
.directive('propertyDiamond', function() {
  return {  //  daimond component popup
    restrict: 'E',
    templateUrl:'include/property-daimond.html', //include template url
    controller: ['$scope','valouxService','$timeout', 'masterDataService',function($scope, valouxService,$timeout, masterDataService){
        
    }],
    link: function(scope, element, attrs){ 
       
      }
  };
})
.directive('propertyGemstone', function() {
  return {  //  daimond component popup
    restrict: 'E',
    templateUrl:'include/property-gemstone.html', //include template url
    link: function(scope, element, attrs){ 
       
      }
  };
})
.directive('propertyMetal', function() {
  return {  //  daimond component popup
    restrict: 'E',
    templateUrl:'include/property-metal.html', //include template url
    link: function(scope, element, attrs){ 
       
      }
  };
})
.directive('propertyPearl', function() {
  return {  //  daimond component popup
    restrict: 'E',
    templateUrl:'include/property-pearl.html', //include template url
    link: function(scope, element, attrs){ 
       
      }
  };
})
.directive('itempriceAdjustment', function() {
  return {  //  item price adjustment popup
    restrict: 'E',
    templateUrl:'include/itemprice-adjustment.html', //include template url
    link: function(scope, element, attrs){ 
       
      }
  };
})
.directive('componentpriceAdjustment', function() {
  return {  //  component price adjustment popup
    restrict: 'E',
    templateUrl:'include/componentprice-adjustment.html', //include template url
    link: function(scope, element, attrs){ 
       
      }
  };
})
.directive('openProperty', function() {
  return {
    restrict: 'A',
    scope : true,
    controller: ['$scope','valouxService','RESPONSE_CODE','$timeout',function($scope, valouxService,RESPONSE_CODE,$timeout){
        
        
    }],
    link: function (scope, element, attrs) {
            
    }
  };
}).directive('activeSearch', function() {

  return {
    restrict : 'A',

    link : function(scope, elem, attr) {

      elem.on('click', function() {
        angular.element("a.active").removeClass("active");
        angular.element(elem).addClass('active');
        
        if(attr.activeSearch == 'item'){
          angular.element("li.showItem").show();
          angular.element("li.showCollection").hide();
          angular.element("li.showAppraisal").hide();
          angular.element("li.showStore").hide();
          angular.element("li.showAgent").hide();          
        }else if(attr.activeSearch == 'collection'){
          angular.element("li.showAgent").hide();
          angular.element("li.showItem").hide();
          angular.element("li.showCollection").show();
          angular.element("li.showAppraisal").hide();
          angular.element("li.showStore").hide();

        }else if(attr.activeSearch == 'appraisal')
        { 
          angular.element("li.showAgent").hide();
          angular.element("li.showStore").hide();
          angular.element("li.showItem").hide();
          angular.element("li.showCollection").hide();
          angular.element("li.showAppraisal").show();
        }else if(attr.activeSearch == 'store')
        { 
          angular.element("li.showAgent").hide();
          angular.element("li.showStore").show();
          angular.element("li.showItem").hide();
          angular.element("li.showCollection").hide();
          angular.element("li.showAppraisal").hide();
        }
        else if(attr.activeSearch == 'agent')
        { 
          angular.element("li.showAgent").show();
          angular.element("li.showStore").hide();
          angular.element("li.showItem").hide();
          angular.element("li.showCollection").hide();
          angular.element("li.showAppraisal").hide();
        }else{
          angular.element("li.showItem").show();
          angular.element("li.showCollection").show();
          angular.element("li.showAppraisal").show();
          angular.element("li.showStore").show();
          angular.element("li.showAgent").show();

        }
      });
    }
  };
}).filter('clarityType', function (masterDataService) {
    return function (value) {
      var clarity = masterDataService.clarityType()
      if(!clarity) return;
      var clarityType = value;
      var len = clarity.length - 1;
      while(len >= 0){
        if(clarityType >= clarity[len].value){
          return clarity[len].name;
        }
        len--;
      }
    };
}).filter('diamondColor', function (masterDataService) {
    return function (value) {
      var color = masterDataService.diamondColor()
      if(!color) return;
      var diamondColor = value;
      var len = color.length - 1;
      while(len >= 0){
        if(diamondColor >= color[len].value){
          return color[len].name;
        }
        len--;
      }
    };
}).filter('qualityType', function (masterDataService) {
    return function (value) {
      var cut = masterDataService.qualityType()
      if(!cut) return;
      var qualityType = value;
      var len = cut.length - 1;
      while(len >= 0){
        if(qualityType >= cut[len].value){
          return cut[len].name;
        }
        len--;
      }
    };
}).filter('diamondCutlet', function (masterDataService) {
    return function (value) {
      var cutlet = masterDataService.diamondCutlet()
      if(!cutlet) return;
      var diamondCutlet = value;
      var len = cutlet.length - 1;
      while(len >= 0){
        if(diamondCutlet >= cutlet[len].value){
          return cutlet[len].name;
        }
        len--;
      }
    };
}).filter('fancyColor', function (masterDataService) {
    return function (value) {
      var fancyColors = masterDataService.fancyColor()
      if(!fancyColors) return;
      var fancyColor = value;
      var len = fancyColors.length - 1;
      while(len >= 0){
        if(fancyColor >= fancyColors[len].value){
          return fancyColors[len].name;
        }
        len--;
      }
    };
}).filter('fluorescence', function (masterDataService) {
    return function (value) {
      var fluorescences = masterDataService.fluorescence()
      if(!fluorescences) return;
      var fluorescence = value;
      var len = fluorescences.length - 1;
      while(len >= 0){
        if(fluorescence >= fluorescences[len].value){
          return fluorescences[len].name;
        }
        len--;
      }
    };
}).filter('secondaryHue', function (masterDataService) {
    return function (value) {
      var secondaryHues = masterDataService.secondaryHue()
      if(!secondaryHues) return;
      var secondaryHue = value;
      var len = secondaryHues.length - 1;
      while(len >= 0){
        if(secondaryHue >= secondaryHues[len].value){
          return secondaryHues[len].name;
        }
        len--;
      }
    };
}).filter('thickness', function (masterDataService) {
    return function (value) {
      var thick = masterDataService.thickness()
      if(!thick) return;
      var thickness = value;
      var len = thick.length - 1;
      while(len >= 0){
        if(thickness >= thick[len].value){
          return thick[len].name;
        }
        len--;
      }
    };
}).filter('certificateType', function (masterDataService) {
    return function (value) {
      var certificateTypes = masterDataService.certificateType()
      if(!certificateTypes) return;
      var certificateType = value;
      var len = certificateTypes.length - 1;
      while(len >= 0){
        if(certificateType >= certificateTypes[len].value){
          return certificateTypes[len].name;
        }
        len--;
      }
    };
}).filter('placement', function () {
    return function (value) {
      if(value == 1){
        return 'Mounted';
      }else if(value == 2){
        return 'Unmounted';
      }
    }
}).filter('girdleThickness', function (masterDataService) {
    return function (value) {
      var girdleThick = masterDataService.girdleThickness()
      if(!girdleThick) return;
      var girdleThickness = value;
      var len = girdleThick.length - 1;
      while(len >= 0){
        if(girdleThickness >= girdleThick[len].value){
          return girdleThick[len].name;
        }
        len--;
      }
    };
})
.filter('componentShape', function (masterDataService) {
    return function (value) {
      var componentShapes = masterDataService.componentShape()
      if(!componentShapes) return;
      var componentShape = value;
      var len = componentShapes.length - 1;
      while(len >= 0){
        if(componentShape >= componentShapes[len].value){
          return componentShapes[len].name;
        }
        len--;
      }
    };
}).filter('gemstoneType', function (masterDataService) {
    return function (value) {
      var gemstoneTypes = masterDataService.gemstoneType()
      if(!gemstoneTypes) return;
      var gemstoneType = value;
      var len = gemstoneTypes.length - 1;
      while(len >= 0){
        if(gemstoneType >= gemstoneTypes[len].value){
          return gemstoneTypes[len].name;
        }
        len--;
      }
    };
}).filter('gemstoneCuts', function (masterDataService) {
    return function (value) {
      var gemstoneCut = masterDataService.gemstoneCuts()
      if(!gemstoneCut) return;
      var gemstoneCuts = value;
      var len = gemstoneCut.length - 1;
      while(len >= 0){
        if(gemstoneCuts >= gemstoneCut[len].value){
          return gemstoneCut[len].name;
        }
        len--;
      }
    };
}).filter('enhancement', function (masterDataService) {
    return function (value) {
      var enhancements = masterDataService.enhancement()
      if(!enhancements) return;
      var enhancement = value;
      var len = enhancements.length - 1;
      while(len >= 0){
        if(enhancement >= enhancements[len].value){
          return enhancements[len].name;
        }
        len--;
      }
    };
}).filter('externalInclusions', function (masterDataService) {
    return function (value) {
      var externalInclusion = masterDataService.externalInclusions()
      if(!externalInclusion) return;
      var externalInclusions = value;
      var len = externalInclusion.length - 1;
      while(len >= 0){
        if(externalInclusions >= externalInclusion[len].value){
          return externalInclusion[len].name;
        }
        len--;
      }
    };
}).filter('internalInclusions', function (masterDataService) {
    return function (value) {
      var internalInclusion = masterDataService.internalInclusions()
      if(!internalInclusion) return;
      var internalInclusions = value;
      var len = internalInclusion.length - 1;
      while(len >= 0){
        if(internalInclusions >= internalInclusion[len].value){
          return internalInclusion[len].name;
        }
        len--;
      }
    };
}).filter('pearlType', function (masterDataService) {
    return function (value) {
      var pearlTypes = masterDataService.pearlType()
      if(!pearlTypes) return;
      var pearlType = value;
      var len = pearlTypes.length - 1;
      while(len >= 0){
        if(pearlType >= pearlTypes[len].value){
          return pearlTypes[len].name;
        }
        len--;
      }
    };
}).filter('composition', function () {
    return function (value) {
      if(value == 1){
        return 'Uniform';
      }else if(value == 2){
        return 'Graduated';
      }
    };
}).filter('enhancements', function () {
    return function (value) {
      if(value == 1){
        return 'Dyeing';
      }else if(value == 2){
        return 'Irradiation';
      }
    };
}).filter('drilled', function () {
    return function (value) {
      if(value == 1){
        return 'Side';
      }else if(value == 2){
        return 'Top';
      }
    };
}).filter('blemish', function (masterDataService) {
    return function (value) {
      var blemishs = masterDataService.blemish()
      if(!blemishs) return;
      var blemish = value;
      var len = blemishs.length - 1;
      while(len >= 0){
        if(blemish >= blemishs[len].value){
          return blemishs[len].name;
        }
        len--;
      }
    };
}).filter('pearlType', function (masterDataService) {
    return function (value) {
      var pearlTypes = masterDataService.pearlType()
      if(!pearlTypes) return;
      var pearlType = value;
      var len = pearlTypes.length - 1;
      while(len >= 0){
        if(pearlType >= pearlTypes[len].value){
          return pearlTypes[len].name;
        }
        len--;
      }
    };
}).filter('pearlsStyle', function (masterDataService) {
    return function (value) {
      var pearlsStyle = masterDataService.pearlsStyle()
      if(!pearlsStyle) return;
      var pearlValue = value;
      var len = pearlsStyle.length - 1;
      while(len >= 0){
        if(pearlValue >= pearlsStyle[len].value){
          return pearlsStyle[len].name;
        }
        len--;
      }
    };
}).filter('purityType', function (masterDataService) {
    return function (value) {
      var purityTypes = masterDataService.purityType()
      if(!purityTypes) return;
      var purityType = value;
      var len = purityTypes.length - 1;
      while(len >= 0){
        if(purityType >= purityTypes[len].value){
          return purityTypes[len].name;
        }
        len--;
      }
    };
}).filter('metalColor', function (masterDataService) {
    return function (value) {
      var metalColors = masterDataService.metalColor()
      if(!metalColors) return;
      var metalColor = value;
      var len = metalColors.length - 1;
      while(len >= 0){
        if(metalColor >= metalColors[len].value){
          return metalColors[len].name;
        }
        len--;
      }
    };
}).filter('typeDetermined', function (masterDataService) {
    return function (value) {
      var typeDetermineds = masterDataService.typeDetermined()
      if(!typeDetermineds) return;
      var typeDetermined = value;
      var len = typeDetermineds.length - 1;
      while(len >= 0){
        if(typeDetermined >= typeDetermineds[len].value){
          return typeDetermineds[len].name;
        }
        len--;
      }
    };
}).filter('metalType', function (masterDataService) {
    return function (value) {
      var metalTypes = masterDataService.metalType()
      if(!metalTypes) return;
      var metalType = value;
      var len = metalTypes.length - 1;
      while(len >= 0){
        if(metalType >= metalTypes[len].value){
          return metalTypes[len].name;
        }
        len--;
      }
    };
})
.filter('highlight', function($sce) {
    return function(text, phrase) {
      if (phrase) {
            text = text.replace(new RegExp('('+phrase+')', 'gi'),
            '<span class="highlighted">$1</span>')
        }

      return $sce.trustAsHtml(text)
    }
}).filter('strLimit', ['$filter', function($filter) {
   return function(input, limit) {
      if (! input) return;
      if (input.length <= limit) {
          return input;
      }
      return $filter('limitTo')(input, limit) + '...';
   };
}]).filter('myDateFormat', function myDateFormat($filter){
  return function(text){
    date = text.toString();    
    splitDate = (date.split(' ')[0]);    
    var  tempdate = new Date(splitDate.replace(/-/g,"/"));
    return $filter('date')(tempdate, "MMM dd, yyyy");
  }
}).filter('marketDateFormat', function myDateFormat(){
  return function(text){   
  if(text)     {
    splitMonth = (text.split(' ')[1]);
    splitDay = (text.split(' ')[2]);   
    splitYear = (text.split(' ')[5]);   
    return splitMonth + ' ' + splitDay +', ' + splitYear;
  }else{
    return
  }
  }
}).filter('marketTimeFormat', function marketTimeFormat($filter){
  return function(text){
  if(text){
    splitTime = (text.split(' ')[3]);
    splitTimeStamp = (splitTime.split(':'));
    var date =  new Date(0,0,0,splitTimeStamp[0],splitTimeStamp[1],splitTimeStamp[2])
    return $filter('date')(date, "h:mm a");
  }else{
    return    
  }
  }
}).filter('newLine', function ($sce) {
    return function(text){
        text = String(text).trim();
        return $sce.trustAsHtml(text.length > 0 ? '<p>' + text.replace(/[\r\n]+/g, '</p><p>') + '</p>' : null);
    }
}).filter('superAdminDateFormat', function superAdminDateFormat(){
  return function(text){   
  if(text){
    splitMonth = (text.split(' ')[0]);
    splitDay = (text.split(' ')[1]);   
    splitYear = (text.split(' ')[2]);   
    return splitMonth + ' ' + splitDay + ' ' + splitYear;
  }else{
    return
  }
  }
});