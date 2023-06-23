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
                templateUrl : 'include/shared-list.html',
                controller  : 'storeManagement'
            })
            
           
            .when('/grid', {
                templateUrl : 'include/shared-grid.html',
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
                                    
     