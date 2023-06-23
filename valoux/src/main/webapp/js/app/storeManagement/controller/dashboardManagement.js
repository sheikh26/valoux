baseController.controller('dashboardManagement',['$scope', 'valouxService', '$timeout','$location','RESPONSE_CODE', 'masterDataService','$route', '$routePaparams','$window','$filter', function($scope, valouxService, $timeout,$location, RESPONSE_CODE, masterDataService, $route, $routePaparams, $window, $filter){

/*Define dashboard Object*/

$scope.dashboardContent = new Object();

/*init dashboard data approved appraisal */
$scope.initDashboard = function () {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  var postData = new Object();
  postData.reqPaparam = {"userPublicKey" : savedData.memberShipKey};
  
  var url = valouxService.getWebServiceUrl("recentActivityForDashboard");
  //post data dashboard and get response
  var submitdashboard = valouxService.getData(url,postData);
  submitdashboard.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      if(data.resData.dataList.length == 0){
        $scope.noActivityFound = true;
      }else{
        $scope.dashboardListed =  data.resData.dataList;        
      }
     }else{
      $window.location = 'index.html'
    }
  },function(){
    console.log("Error in dashboard listing");
  });
}

/*init dashboard data for store */
$scope.initStoreInDashboard = function () {
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  var postData = new Object();
  postData.reqPaparam = {"userPublicKey" : savedData.memberShipKey};
  
  var url = valouxService.getWebServiceUrl("getStoreAndAgentAssociatedWithConsumer");
  //post data dashboard store info and get response
  var submitdashboard = valouxService.getData(url,postData);
  submitdashboard.then(function(data){
     if (data.sCode == RESPONSE_CODE.SUCCESS) {
      if(data.storeList.length == 0 && data.agentList == 0){
        $scope.noStoreFound = true;
      }else{
        $scope.storeListed =  data.storeList;
        $scope.agentListed =  data.agentList;

      }
     }else{
        $window.location = 'index.html'
    }
  },function(){
    console.log("Error in dashboard store listing");
  });
}

$scope.clearSearchData = function () {
  $scope.searchKeyword = '';
}

/*init dashboard data approved appraisal */
$scope.initAgentDashboard = function () {
  
  var savedItem = sessionStorage.getItem("saveValouxData");
  var savedData = JSON.parse(savedItem);
  
  var postData = new Object();
  postData.reqPaparam = { "sharedTo" : savedData.memberShipKey,
  "sharedItemType" : "3",
  "approvedStatus" : "2"
  };
  
  var url = valouxService.getWebServiceUrl("getUserSharedWithMeList");
  //post data dashboard and get response
  var submitdashboard = valouxService.getData(url,postData);
  submitdashboard.then(function(data){
    if (data.sCode == RESPONSE_CODE.SUCCESS) {
      if(data.sharedItemList.length == 0){
        $scope.agentDashboardNot = true;
      }else{
        $scope.agentDashboardListed =  data.sharedItemList;     
      }

    }else{
      $window.location = 'index.html'
    }
  },function(){
    console.log("Error in agent dashboard listing");
  });
}

/*appraisal detail page redirect in agent dashboard */
$scope.dashboardToAppraisalDetail = function(appraisalId, agentInfo) {
  if(agentInfo){
    sessionStorage.setItem("agentInformation", agentInfo);
    $window.location = "appraisal-agent.html#/detail/"+appraisalId;
  }
}

/*toggling effect*/
$scope.itemPurchased = function(storeId) {
  angular.element("#box_show"+storeId).slideToggle("show");
}

/*Filter appraisal status according to 1 || 2*/
$scope.appraisalStatus = function(status) {
  return status.appraisalStatus == 1 || status.appraisalStatus == 2;  
}

}]);

          