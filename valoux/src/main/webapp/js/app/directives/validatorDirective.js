/* All coustom validation define here */
baseController.directive('numbersOnly', function() {
  return {
    //only number enter in fields
    require: 'ngModel',
    link: function(scope, element, attr, ngModelCtrl) {
      function fromUser(text) {
        if (text) {
          var transformedInput = text.replace(/[^0-9]/g, '');

          if (transformedInput !== text) {
            ngModelCtrl.$setViewValue(transformedInput);
            ngModelCtrl.$render();
          }
          return transformedInput;
        }
        return undefined;
      }
      ngModelCtrl.$parsers.push(fromUser);
    }
  };
//
}).directive('limitDouble5', function() {
  return {
    //only number enter in fields
    require: 'ngModel',
    link: function(scope, element, attr, ngModelCtrl) {
      function myValidation(value) {
          if(!isNaN(value)){
                if (value<1000) {
                  ngModelCtrl.$setValidity('tooLong', true);
                } else {
                  ngModelCtrl.$setValidity('tooLong', false);
                }
          }
        return value;
      }
      ngModelCtrl.$parsers.push(myValidation);
    }
  };
//created directive for allow multiple email addresses with comma seprated 
})
.directive('greaterZero', function() {
  return {
    //only number enter in fields
    require: 'ngModel',
    link: function(scope, element, attr, ngModelCtrl) {
      function myValidation(value) {
          if(!isNaN(value)){
                if (value<=0) {
                  ngModelCtrl.$setValidity('lessZero', false);
                } else {
                  ngModelCtrl.$setValidity('lessZero', true);
                }
          }
        return value;
      }
      ngModelCtrl.$parsers.push(myValidation);
    }
  };
//created directive for allow multiple email addresses with comma seprated 
})
.directive('multipleEmails', function () {
  return {
    require: 'ngModel',
    link: function(scope, element, attrs, ctrl) {
      ctrl.$parsers.unshift(function(viewValue) {

        var emails = viewValue.split(',');
		
        // loop that checks every email, returns undefined if one of them fails.
        var re = /^[a-zA-Z0-9]([a-zA-Z0-9_\-])+([\.][a-zA-Z0-9_]+)*\@((([a-zA-Z0-9\-])+\.){1,2})([a-zA-Z0-9]{2,40})$/;
		var counters = {};
        // angular.foreach(emails, function() {
          var validityArr = emails.map(function(str){
				// count number of occurance of email		
				counters[str] = counters[str] ? counters[str] + 1 : 1;		
				return re.test(str.trim());
          }); // sample return is [true, true, true, false, false, false]
		  
		   var atLeastOneInvalid = false;
		   scope.messageDuplicate = "";
		   $.each(counters, function(emlKey, emlCnt) {
				// if email address coming more then one time					 
   				if(emlCnt>1)
				{
					var str = "you have entered "+ emlKey +" "+ emlCnt +" times.";
					scope.messageDuplicate = str;
					atLeastOneInvalid = true;
				}
  			});
		   
	
          if(emails == '') {
            validityArr = true
          }
         
          angular.forEach(validityArr, function(value) {
            if(value === false)
              atLeastOneInvalid = true; 
          }); 
          if(!atLeastOneInvalid) { 
            // ^ all I need is to call the angular email checker here, I think.
            ctrl.$setValidity('multipleEmails', true);
            return viewValue;
          } else {
            ctrl.$setValidity('multipleEmails', false);
            return undefined;
          }
        // })
      });
    }
  };
})
.directive('matchFields', [function () {
    return {
      require: 'ngModel',
      link: function (scope, elem, attrs, ctrl) {
        var firstPassword = '#' + attrs.matchFields;
        elem.add(firstPassword).on('keyup', function () {
          scope.$apply(function () {
            var v = elem.val()===$(firstPassword).val();
            ctrl.$setValidity('matchFields', v);
          });
        });
      }
    }
  }]).directive('isemailExists', function($q, $timeout,valouxService) {
 //  check email is already exists or not
  return {
    require: 'ngModel',
    link: function(scope, elm, attrs, ctrl) { 
      ctrl.$asyncValidators.emailId = function(modelValue, viewValue) { 

        if (ctrl.$isEmpty(modelValue)) {
          // consider empty model valid
          return $q.when();
        }

        var def = $q.defer();

        $timeout(function() {
            scope.$parent.fullPageBlockLoading = false;
            var url = valouxService.getWebServiceUrl("checkEmail");
            var postData = new Object();
            postData.reqPaparam = new Object();
            postData.reqPaparam.emailId = modelValue;        
            var promiseCheckEmail = valouxService.getData(url,postData);
            promiseCheckEmail.then(function(data){ 
                if(data.resData.isEmailExist){
                   def.reject();
                }else{
                     def.resolve(); 
                } 
            },function(){
              console.log("Error in checking email");
            });
           
        }, 2000);

        return def.promise;
      };
    }
  };
}).directive('isitemnameExists', function($q, $timeout,valouxService) {
 //  check item name already exists or not
  return {
    require: 'ngModel',
    scope: {
        isitemnameExists: '='
      },
    link: function(scope, elm, attrs, ctrl) { 
      ctrl.$asyncValidators.name = function(modelValue, viewValue) { 

        if (ctrl.$isEmpty(modelValue)) {
          // consider empty model valid
          return $q.when();
        }

        var def = $q.defer();

        $timeout(function() {
            scope.$parent.fullPageBlockLoading = false;
            var url = valouxService.getWebServiceUrl("checkItemNameExistForUser");
            var postData = new Object();
            postData.reqPaparam = new Object();
            var savedItemAgent = sessionStorage.getItem("agentInformation");
            var savedItem = sessionStorage.getItem("saveValouxData");
            var savedData = JSON.parse(savedItem);
            
            if(savedItemAgent){
              postData.reqPaparam.userPublicKey = savedItemAgent;
            }else{
              postData.reqPaparam.userPublicKey = savedData.memberShipKey;
            }
            postData.reqPaparam.itemName = modelValue;  
            if(scope.isitemnameExists){
                postData.reqPaparam.itemId = scope.isitemnameExists;
            }
            var promiseCheckItemName = valouxService.getData(url,postData);
            promiseCheckItemName.then(function(data){ 
                if(data.resData.isitemNameExist){
                   def.reject();
                }else{
                     def.resolve(); 
                } 
            },function(){
              console.log("Error in checking Item name");
            });
           
        }, 2000);

        return def.promise;
      };
    }
  };
}).directive('iscollectionnameExists', function($q, $timeout,valouxService) {
 //  check collection name already exists or not
  return {
    require: 'ngModel',
    scope: {
        iscollectionnameExists: '='
      },
    link: function(scope, elm, attrs, ctrl) { 
      ctrl.$asyncValidators.cName = function(modelValue, viewValue) { 

        if (ctrl.$isEmpty(modelValue)) {
          // consider empty model valid
          return $q.when();
        }

        var def = $q.defer();

        $timeout(function() {
          scope.$parent.fullPageBlockLoading = false;
          var url = valouxService.getWebServiceUrl("checkCollectionNameExistForUser");
          var postData = new Object();
          postData.reqPaparam = new Object();
          var savedItemAgent = sessionStorage.getItem("agentInformation");
          var savedItem = sessionStorage.getItem("saveValouxData");
          var savedData = JSON.parse(savedItem);
          
          if(savedItemAgent){
            postData.reqPaparam.userPublicKey = savedItemAgent;
          }else{
            postData.reqPaparam.userPublicKey = savedData.memberShipKey;
          }
          postData.reqPaparam.cName = modelValue;
          if(scope.iscollectionnameExists){
              postData.reqPaparam.cId = scope.iscollectionnameExists;
          }
          var promiseCheckItemName = valouxService.getData(url,postData);
          promiseCheckItemName.then(function(data){ 
              if(data.resData.isCollectionNameExist){
                 def.reject();
              }else{
                   def.resolve(); 
              } 
          },function(){
            console.log("Error in checking collection name");
          });
         
        }, 2000);

        return def.promise;
      };
    }
  };
}).directive('validFile',function(){
	   return {
	        require:'ngModel',
	        link:function(scope,el,attrs,ctrl){
	            ctrl.$setValidity('validFile', el.val() != '');
	            //change event is fired when file is selected
	            el.bind('change',function(){
	                ctrl.$setValidity('validFile', el.val() != '');
	                scope.$apply(function(){
	                    ctrl.$setViewValue(el.val());
	                    ctrl.$render();
	                });
	            });
	        }
	    }  
}).directive('isappraisalnameExists', function($q, $timeout,valouxService) {
 //  check Appraisal name already exists or not
  return {
    require: 'ngModel',
    scope: {
        isappraisalnameExists: '='
      },
    link: function(scope, elm, attrs, ctrl) { 
      ctrl.$asyncValidators.name = function(modelValue, viewValue) { 

        if (ctrl.$isEmpty(modelValue)) {
          // consider empty model valid
          return $q.when();
        }
        var def = $q.defer();
        $timeout(function() {
            scope.$parent.fullPageBlockLoading = false;
            var url = valouxService.getWebServiceUrl("checkAppraisalNameExistForUser");
            var savedItem = sessionStorage.getItem("saveValouxData");
            var savedData = JSON.parse(savedItem);
            var postData = new Object();
            postData.reqPaparam = new Object();
			var savedItemAgent = sessionStorage.getItem("agentInformation");
          var savedItem = sessionStorage.getItem("saveValouxData");
          var savedData = JSON.parse(savedItem);
          
          if(savedItemAgent){
            postData.reqPaparam.userPublicKey = savedItemAgent;
          }else{
            postData.reqPaparam.userPublicKey = savedData.memberShipKey;
          }
		  
            postData.reqPaparam.appraisalName = modelValue;
            

            if(scope.isappraisalnameExists){
                postData.reqPaparam.appraisalId = scope.isappraisalnameExists;
            }
            var promiseCheckAppraisalName = valouxService.getData(url,postData);
            promiseCheckAppraisalName.then(function(data){ 
                if(data.resData.isAppraisalExist){
                   def.reject();
                }else{
                     def.resolve(); 
                } 
            },function(){
              console.log("Error in checking appraisal name");
            });
           
        }, 2000);

        return def.promise;
      };
    }
  };
})