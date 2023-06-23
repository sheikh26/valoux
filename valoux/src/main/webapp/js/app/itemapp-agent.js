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
                templateUrl : 'include/item-list-agent.html',
                controller  : 'storeManagement'
            })
            .when('/add', {
                templateUrl : 'include/add-item.html',
                controller  : 'storeManagement'
            })
            .when('/edit/:itemId', {
                templateUrl : 'include/add-item.html',
                controller  : 'storeManagement'
            })
            .when('/success/:itemId', {
                templateUrl : 'include/add-item-success.html',
                controller  : 'storeManagement'
            })
            .when('/detail/:itemId', {
                templateUrl : 'include/item-details.html',
                controller  : 'storeManagement'
            })
            .when('/grid', {
                templateUrl : 'include/item-list-grid-agent.html',
                controller  : 'storeManagement'
            })
            .otherwise({
                redirectTo: '/'
            });
            
    });
                                    
     