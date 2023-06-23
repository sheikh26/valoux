// This file contain all javascript common code.
// 
// This example displays an address form, using the autocomplete feature
// of the Google Places API to help users fill in the information.

// This example requires the Places library. Include the libraries=places
// paparameter when you first load the API. For example:
// <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY&libraries=places">
// start autocomplete feature with map 
var placeSearch, autocomplete;
var componentForm = {
  street_number: 'long_name',
  sublocality_level_1: 'long_name',
  route: 'long_name',
  administrative_area_level_1: 'long_name',
  country: 'long_name',
  postal_code: 'long_name',
  administrative_area_level_3:'long_name',
  locality:'long_name'
};

var addressArray ={
  street_number:'streetNo',
  route:'addressLine1',
  sublocality_level_1:'addressLine2',
  country:'country',
  administrative_area_level_1:'state',
  postal_code:'zipCode' ,
  locality:'city',
  administrative_area_level_3 : 'city',

}

function initAutocomplete() {
    
    var input = (document.getElementById('location')); 
    // Create the autocomplete object, restricting the search to geographical
  // location types.
    autocomplete = new google.maps.places.Autocomplete(
      (input));
  if(document.getElementById('map')){
      geocoder = new google.maps.Geocoder();
        map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 42.3132882, lng: -71.1972408},
        zoom: 3
      });

      if(navigator.geolocation) {
        browserSupportFlag = true;
        navigator.geolocation.getCurrentPosition(function(position) {
          initialLocation = new google.maps.LatLng(position.coords.latitude,position.coords.longitude);
          map.setCenter(initialLocation);
        }, function() {
          handleNoGeolocation(browserSupportFlag);
        });
      }
      // Browser doesn't support Geolocation
      else {
        browserSupportFlag = false;
        handleNoGeolocation(browserSupportFlag);
      }
      
      
      autocomplete.bindTo('bounds', map);
      marker = new google.maps.Marker({
        map: map,
        anchorPoint: new google.maps.Point(0, -29)
      });
  }
    //Blank store value in all address fields after refresh form
  for (var component in addressArray) { 
      if(document.getElementById(addressArray[component])){
            document.getElementById(addressArray[component]).value = '';
      }
  }
  // When the user selects an address from the dropdown, populate the address
  // fields in the form.
  autocomplete.addListener('place_changed', fillInAddress);
}

// [START region_fillform]
function fillInAddress() {
  // Get the place details from the autocomplete object.
    var place = autocomplete.getPlace();
    //console.log(place);
   
   if(document.getElementById('map')){
        marker.setVisible(false);
        if (!place.geometry) {
          window.alert("Autocomplete's returned place contains no geometry");
          return;
        }

        // If the place has a geometry, then present it on a map.
        if (place.geometry.viewport) {
          map.fitBounds(place.geometry.viewport);
        } else {
          map.setCenter(place.geometry.location);
          map.setZoom(15);  // Why 17? Because it looks good.
        }
        marker.setIcon(/** @type {google.maps.Icon} */({
          url: place.icon,
          size: new google.maps.Size(71, 71),
          origin: new google.maps.Point(0, 0),
          anchor: new google.maps.Point(17, 34),
          scaledSize: new google.maps.Size(35, 35)
        }));
        marker.setPosition(place.geometry.location);
        marker.setVisible(true);
   }
  //Blank store value in all address fields after filling address
  angular.element(document.getElementById('location')).scope().storeData.store_name = '';
  $('#Store').next('label').removeClass('active');
  
  angular.element(document.getElementById('location')).scope().storeData.storePhoneNumber = '';
  $('#store_phone').next('label').removeClass('active');
  
  for (var component in addressArray) { 
    if(document.getElementById(addressArray[component])){
       document.getElementById(addressArray[component]).value = '';
    }
    if(addressArray[component]=='addressLine1'){
      angular.element(document.getElementById('location')).scope().storeData.addressLine1 = '';
    }
    if(addressArray[component]=='addressLine2'){
      angular.element(document.getElementById('location')).scope().storeData.addressLine2 = '';
    }
    if(addressArray[component]=='city'){
      angular.element(document.getElementById('location')).scope().storeData.city = '';
    }
    if(addressArray[component]=='state'){
      angular.element(document.getElementById('location')).scope().storeData.state = '';
    }
    if(addressArray[component]=='country'){
      angular.element(document.getElementById('location')).scope().storeData.country = '';
    }
    if(addressArray[component]=='zipCode'){
      angular.element(document.getElementById('location')).scope().storeData.zipCode = '';
    }
    $('#'+addressArray[component]).next('label').removeClass('active');
    angular.element(document.getElementById('location')).scope().$apply();
  }

  // Get each component of the address from the place details
  // and fill the corresponding field on the form.
   var scopObj={};
    $("#location").removeClass('location_disable');
    $("#location-input").removeClass('location_disable');
    
  angular.element(document.getElementById('location')).scope().storeData.store_name = place.name;
  scopObj.store_name = place.name;
  $('#Store').next('label').addClass('active');
  
  angular.element(document.getElementById('location')).scope().storeData.storePhoneNumber = place.international_phone_number;
  $('#store_phone').next('label').addClass('active');
   var chkcity = 0 ; 
  for (var i = 0; i < place.address_components.length; i++) {
    var addressType = place.address_components[i].types[0];
    if (componentForm[addressType]) {
      var val = place.address_components[i][componentForm[addressType]];
      if(document.getElementById(addressArray[addressType])){
          document.getElementById(addressArray[addressType]).value = val;
      }
      if(addressArray[addressType]=='streetNo'){
        var streetNo = val;
      }
      if(addressArray[addressType]=='addressLine1'){
        if(typeof streetNo!='undefined' &&  streetNo!=''){
            angular.element(document.getElementById('location')).scope().storeData.addressLine1 = streetNo+', '+val;
            scopObj.addressLine1 = scopObj.streetNo+', '+val;
        }else{
            angular.element(document.getElementById('location')).scope().storeData.addressLine1 = val;
            scopObj.addressLine1 = val;
        }

      }
      if(addressArray[addressType]=='addressLine2'){
      angular.element(document.getElementById('location')).scope().storeData.addressLine2 = val;
      }
      if(addressArray[addressType]=='city'){
          if(!chkcity){
            angular.element(document.getElementById('location')).scope().storeData.city = val;
            scopObj.city = val;
            chkcity =1;
         } 
      }
      if(addressArray[addressType]=='state'){
        angular.element(document.getElementById('location')).scope().storeData.state = val;
        scopObj.state = val;
      }
      if(addressArray[addressType]=='country'){
        angular.element(document.getElementById('location')).scope().storeData.country = val;
        scopObj.country = val;
      }
      if(addressArray[addressType]=='zipCode'){
        angular.element(document.getElementById('location')).scope().storeData.zipCode = val;
        scopObj.zipCode = val;
      }
      $('#'+addressArray[addressType]).next('label').addClass('active');
    } 
  }
  angular.element(document.getElementById('location')).scope().storeData.fullAddress = scopObj;
  angular.element(document.getElementById('location')).scope().$apply();
}
// [END region_fillform]

// [START region_geolocation]
// Bias the autocomplete object to the user's geographical location,
// as supplied by the browser's 'navigator.geolocation' object.
function geolocate() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      var geolocation = {
        lat: position.coords.latitude,
        lng: position.coords.longitude
      };
      var circle = new google.maps.Circle({
        center: geolocation,
        radius: position.coords.accuracy
      });
      autocomplete.setBounds(circle.getBounds());
    });
  }
}

function handleNoGeolocation(errorFlag) {
  errorFlag == true; 
}
// start autocomplete feature with map 
