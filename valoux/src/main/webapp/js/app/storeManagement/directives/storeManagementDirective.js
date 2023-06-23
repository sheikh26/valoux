valouxDirective.directive('collectionPopup', function() {
  return { // collectionPopup directive used in Collection 
    restrict: 'E',
    templateUrl:'include/collectionPopups.html' //include template url
  };
})
.directive('componentRightpanel', function() {
  return { // component Right panel 
    restrict: 'E',
    templateUrl:'include/component-right-panel.html' //include template url
  };
})
.directive('componentPopups', function() {
  return { //  component Popups 
    restrict: 'E',
    templateUrl:'include/component-popups.html' //include template url
  };
})
.directive('appraisalPopup', function() {
  return {  //  appraisal Popup
    restrict: 'E',
    templateUrl:'include/appraisalPopups.html' //include template url
  };
})
.directive('itemPopup', function() {
  return { // itemPop directive used in Store 
    restrict: 'E',
    templateUrl:'include/itemPopups.html' //include template url
  };
  })
 .directive('sharePopup', function() {
  return { // itemPop directive used in Store 
    restrict: 'E',
    templateUrl:'include/sharePopups.html' //include template url
  };
  }) 
.directive('agentPopup', function() {
  return { // agentSharePopup directive used in store 
    restrict: 'E',
    templateUrl:'include/agentSharePopups.html' //include template url
  };
})