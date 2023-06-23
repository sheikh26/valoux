var app = angular.module('valouxApp', [
                                    'valouxApp.controllers',
                                    'valouxApp.valouxServices',
                                    'valouxApp.valouxDirectives',
                                    'ngRoute'
                                    ]);
     app.config(function($routeProvider) {
        $routeProvider
            // route for the about page
            .when('/', {
                templateUrl : 'include/sharedwithme-list.html',
                controller  : 'storeManagement'
            })
            
           
            .when('/grid', {
                templateUrl : 'include/sharedwithme-grid.html',
                controller  : 'storeManagement'
            })
            
            /*.when('/success/:itemId', {
                templateUrl : 'include/add-item-success.html',
                controller  : 'collectionManagement'
            })*/
            .otherwise({
                redirectTo: '/'
            });
    });
                                    
     